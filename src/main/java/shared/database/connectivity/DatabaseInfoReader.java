package shared.database.connectivity;

import shared.database.model.SQLDatabase;
import shared.database.model.SQLIndexResult;
import shared.database.model.SQLTupleList;



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
        DatabaseConfigurations.fill("app");
        SQLDatabase database = SQLDatabase.InstantiateDatabase("cordis");
        
        // Create indexes
        System.out.println(database); 

        // Search columns.
        SQLIndexResult results = database.searchColumn(database.getTableByName("countries").getColumnByName("name"), "Spain");
        SQLTupleList nl = new SQLTupleList(results.getTuples());
        nl.print();
    }
}
