package shared.database.connectivity;

import java.util.List;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import shared.database.model.PostgreSQLDatabase;
import shared.database.model.PostgreSQLQueries;
import shared.database.model.SQLColumn;
import shared.database.model.SQLQueries;

/**
 * Create full-text indexes in the underlying database.
 */
public class DatabaseIndexCreator {

    public final static String INDEXED_COLUMN_PREFIX = "ts_vec_";
    private final static String VEC_NAME = INDEXED_COLUMN_PREFIX + "%s";
    private final static String IDX_NAME = "ts_idx_%s_%s";


    /**
     * Create full-text indexes for all textual attributes in a psql database excluding some columns.
     * How indexes are crated:
     * - Add a new column to the table, for each textual attribute.
     * - This column contains the column values tokenized & stemmed ( to_tsvector() ), named like: ts_vec_<column>
     * - Create a GIN index named with the format: ts_idx_<table>_<column>
     * 
     * @param columnsToExclude
     * @param database
     */
    public static void createIndex(List<String> columnsToExclude, PostgreSQLDatabase database) {

        // Initialize connection variables
        Connection con = null;
        Statement stmt = null;        
        try {
            // Get the connection
            con = DataSourceFactory.getConnection();
            con.setAutoCommit(false);

            // Loop all column in the database
            for (SQLColumn col: database.getAllColumns()) {
                // Skip non textual columns or columns to exclude
                if (!col.getType().isTextual() || columnsToExclude.contains(col.getName())) continue;
                System.out.println("[INFO] Creating index for: " + col.toString());
                
                // Initialize names
                String newColName = String.format(VEC_NAME, col.getName());
                String idxName = String.format(IDX_NAME, col.getTableName(), col.getName());

                // Create Alter table & Update & Create Index queries
                String alterTableSQL = String.format(SQLQueries.SQL_ADD_COLUMN_QUERY,
                    col.getTableName(), newColName, "tsvector");
                String updateTableSQL = String.format(PostgreSQLQueries.UPDATE_TABLE_SET_TSVECTOR,
                    col.getTableName(), newColName, col.getName());
                String createIndexSQL = String.format(PostgreSQLQueries.CREATE_GIN_INDEX, 
                    idxName, col.getTableName(), newColName);
                
                // Issue the queries.
                stmt = con.createStatement();
                stmt.executeUpdate(alterTableSQL);                
                DatabaseUtil.close(stmt);

                stmt = con.createStatement();
                stmt.executeUpdate(updateTableSQL);
                DatabaseUtil.close(stmt);

                stmt = con.createStatement();
                stmt.executeUpdate(createIndexSQL);
                DatabaseUtil.close(stmt);
            }
            con.commit();          
        } catch (SQLException e) {
            e.printStackTrace();
            try {              
                System.out.println("Rolling back...");
                con.rollback();
            } catch(SQLException excep) {
                e.printStackTrace();
            }        
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {                
                e.printStackTrace();
            }
            DatabaseUtil.close(con, stmt);
        }
    }    

}