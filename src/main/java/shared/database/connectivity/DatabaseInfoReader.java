package shared.database.connectivity;

import shared.database.model.SQLDatabase;
import shared.database.model.SQLQueries;
import shared.database.model.SQLTuple;
import shared.database.model.SQLTupleList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import shared.database.connectivity.DatabaseConfigurations.DatabaseType;


// This class has the required functionality to get information
// about a database such as table names, column names and column types.
// It queries the INFORMATION_SCHEMA table.
public interface DatabaseInfoReader {

    // Gets all the information needed from the database.
    public static void getTableAndColumnNames(SQLDatabase database) {}
    public static void getFKConstraints(SQLDatabase database) {}
    public static void getIndexedColumns(SQLDatabase database) {}
    public static void getTableAndColumnStatistics(SQLDatabase database) {}
    


    public static void main(String[] args) {
        SQLDatabase database = SQLDatabase.InstantiateDatabase("cordis", "app");
        
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = DataSourceFactory.getConnection();
            stmt = con.prepareStatement(String.format( SQLQueries.SQL_SELECT_QUERY + " " + SQLQueries.LIMIT_STATEMENT, "*", "projects", 5));
            rs = stmt.executeQuery();

            List<SQLTuple> tuples = new ArrayList<>();
            while(rs.next()) {
                SQLTuple tuple = new SQLTuple();
                tuple.fill(database, rs);
                tuples.add(tuple);
            }
            SQLTupleList list = new SQLTupleList(tuples);
            list.print();

        } catch (Exception e) {
            e.printStackTrace();
        }


        // System.out.println(d.toString());
    }
}
