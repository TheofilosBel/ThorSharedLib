package shared.database.connectivity;

import shared.database.model.SQLDatabase;
import shared.database.model.SQLTable;
import shared.database.model.SQLType;
import shared.util.Timer;
import shared.database.model.SQLColumn;
import shared.database.model.SQLForeignKeyConstraint;
import shared.database.model.SQLQueries;
import shared.database.connectivity.DataSourceFactory;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// This class has the required functionality to get information
// about a database such as table names, column names and column types.
// It queries the INFORMATION_SCHEMA table.
public class DatabaseInfo {

    private SQLDatabase database;  // A database object.
    private String schemaName;     // The schema name.

    // Statistics
    private Double timeGettingInfo;


    public DatabaseInfo(String name) {
        this.database = new SQLDatabase();
        this.schemaName = name;
    }

    public SQLDatabase getDatabase() {
        return this.database;
    }

    // Gets all the information needed from the database.
    public void getDatabaseInfo() {
        Timer timer = new Timer();
        timer.start(); // Start the timer
        
        this.getTableAndColumnNames();
        this.getFKConstraints();
        this.getIndexedColumns();
        this.getTableAndColumnStatistics(); 
        
        this.timeGettingInfo = timer.stop(); // Stop the timer.
    }

    // Uses the INFORMATION_SCHEMA to get the tables and columns of a database.
    // Saves the information in the requested database object.
    private void getTableAndColumnNames() {        
        // Get the database name from the properties bundle.        
        this.database.setName(this.schemaName);

        // A hash map that maps table names to SQLTable objects.
        Map<String, SQLTable> tablesMap = new HashMap<String, SQLTable>();

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // This query returns all the columns in the database.
            // From the information of the columns we will extract the table names, too.
            con = DataSourceFactory.getConnection();
            stmt = con.prepareStatement(SQLQueries.INFORMATION_SCHEMA_COLUMNS_QUERY);
            stmt.setString(1, this.database.getName());
            rs = stmt.executeQuery();

            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String columnName = rs.getString("COLUMN_NAME");
                String columnType = rs.getString("DATA_TYPE");
                String columnKey = rs.getString("COLUMN_KEY");
                Long columnsCharacterMaximumLength = rs.getLong("CHARACTER_MAXIMUM_LENGTH");
                
                // Skip the "avg_length" table since it is a temporary table that we use to save statistics.
                if (tableName.equals("avg_length")) { continue; }
                if (tableName.equals("size")) { continue; }
                if (tableName.equals("history")) { continue; }
                if (tableName.contains("soda")) { continue; }


                // If an SQLTable with the same name exists, just add the column.
                if (tablesMap.containsKey(tableName)) {
                    // Create a column.
                    SQLType columnSqlType = new SQLType(columnType, columnsCharacterMaximumLength);
                    SQLColumn column = new SQLColumn(tablesMap.get(tableName), columnName, columnSqlType, columnKey);

                    // Add it to the table.
                    tablesMap.get(tableName).addColumn(column);
                }
                else {
                    // Create a new SQLTable and then add the column.
                    SQLTable newTable = new SQLTable(tableName);

                    // Create column
                    SQLType columnSqlType = new SQLType(columnType, columnsCharacterMaximumLength);
                    SQLColumn column = new SQLColumn(newTable, columnName, columnSqlType, columnKey);
                    newTable.addColumn(column);
                    tablesMap.put(tableName, newTable);
                }
            }
        }
        catch (SQLException e) {
			e.printStackTrace();
        }
        finally {
            // Close the connection
            DatabaseUtil.close(con, stmt, rs);
        }

        // Add all the tables to the database.
        for (SQLTable table : tablesMap.values()) {
            this.database.addTable(table);

            // Each table that has no primary keys will be forced 
            // with a primary key matching to those columns with a MUL keyType
            if (table.getPrimaryKey().isEmpty()) {
                Set<SQLColumn> newPks = new HashSet<>();
                for (SQLColumn col: table.getColumns())
                    if (col.isPartOfMulIndex()) {
                        newPks.add(col);
                        col.addKey(SQLColumn.PK_IDENTIFIER);
                    }
                table.setPrimaryKey(newPks);
            }
        }

    }

    // Uses the INFORMATION_SCHEMA to get the foreign key constraints of a database.
    // Saves the information in the requested database object.
    private void getFKConstraints() {
        // Get the Schema name from the properties bundle.        
        this.database.setName(this.schemaName);

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // This query returns all the foreign key constraints in the schema.
            con = DataSourceFactory.getConnection();
            stmt = con.prepareStatement(SQLQueries.INFORMATION_SCHEMA_KEY_USAGE_QUERY);
            stmt.setString(1, this.database.getName());
            rs = stmt.executeQuery();

            // Loop through every constraint.
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String columnName = rs.getString("COLUMN_NAME");
                String referencedTableName = rs.getString("REFERENCED_TABLE_NAME");
                String referencedColumnName = rs.getString("REFERENCED_COLUMN_NAME");

                // Create a foreign key constraint and add it to the
                // database's list of foreign key constraints.
                SQLForeignKeyConstraint constraint = new SQLForeignKeyConstraint();
                constraint.fill(this.database, tableName, columnName, referencedTableName, referencedColumnName);
                this.database.addFKConstraint(constraint);

                // Also Add the column and the constrain in the Tables involving in the constrain.
                this.database.getTableByName(tableName).getColumnByName(columnName).addKey(SQLColumn.FK_IDENTIFIER);
                this.database.getTableByName(tableName).addForeignKey(this.database.getTableByName(tableName).getColumnByName(columnName));
                this.database.getTableByName(tableName).addReferencingForeignKeyConstrain(constraint);
                this.database.getTableByName(referencedTableName).addReferencedForeignKeyConstrain(constraint);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            // Close the connection
            DatabaseUtil.close(con, stmt, rs);
        }
    }

    // Uses the INFORMATION_SCHEMA to get the columns which are indexed with a 'FULLTEXT' index.
    private void getIndexedColumns() {        
        // Get the database name from the properties bundle.        
        this.database.setName(this.schemaName);

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // This query returns all the columns indexed with a FULLTEXT index.
            con = DataSourceFactory.getConnection();
            stmt = con.prepareStatement(SQLQueries.INFORMATION_SCHEMA_STATISTICS_QUERY);
            stmt.setString(1, this.database.getName());
            rs = stmt.executeQuery();

            // Loop through every column.
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String columnName = rs.getString("COLUMN_NAME");

                // Search the column in the database and mark it as indexed.
                this.database.getTableByName(tableName).getColumnByName(columnName).setIsIndexed(true);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            // Close the connection
            DatabaseUtil.close(con, stmt, rs);
        }
    }

    // Uses the INFORMATION_SCHEMA to get some statistics for the tables and the columns with a FULLTEXT index.
    private void getTableAndColumnStatistics() {        
        this.database.setName(this.schemaName);

        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Connect to the database.
            con = DataSourceFactory.getConnection();

            // This query returns the number of rows of all tables in the schema.            
            stmt = con.prepareStatement(SQLQueries.INFORMATION_SCHEMA_TABLE_ROWS_QUERY);
            stmt.setString(1, this.database.getName());
            rs = stmt.executeQuery();

            // Loop through every table and save its number of rows.
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                int tableRows = rs.getInt("TABLE_ROWS");

                // Skip the "avg_length" table since it is a temporary table that we use to save statistics.
                if (tableName.equals("avg_length")) { continue; }
                if (tableName.equals("size")) { continue; }
                if (tableName.equals("history")) { continue; }
                if (tableName.contains("soda")) { continue; }


                this.database.getTableByName(tableName).setRowsNum(tableRows);
            }

            // This query returns the average length in words of all columns
            // with a FULLTEXT index in the current database schema.
            try {
                stmt = con.prepareStatement(SQLQueries.COLUMN_AVERAGE_LENGTH_QUERY);
                rs = stmt.executeQuery();
    
                // Loop through every column and get its average length.
                while (rs.next()) {
                    String tableName = rs.getString("TABLE_NAME");
                    String columnName = rs.getString("COLUMN_NAME");
                    double averageLength = rs.getDouble("AVG_LENGTH");
                    this.database.getTableByName(tableName).getColumnByName(columnName).setAverageLength(averageLength);
                }   
            } catch (SQLException e) {
                System.out.println("[INFO] Could not find an avg_length table in the database");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            // Close the connection
            DatabaseUtil.close(con, stmt, rs);
        }

        // for (SQLTable table : this.database.getTables()) {
        //     System.out.println(table.getName() + " " + table.getRowsNum());
        //     for (SQLColumn column : table.getColumns()) {
        //         if (column.isIndexed()) {
        //             System.out.println("\t" + column.getName() + " " + column.getAverageLength());
        //         }
        //     }
        // }

    }

    // Print the statistics
    public void printStats() {
        System.out.println("DATABASE INFO STATS:");
        System.out.println("\tTime to get Info: " + this.timeGettingInfo);
    }
}
