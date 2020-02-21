package shared.database.model;

import shared.database.connectivity.PostgreSQLInformationReader;

public class PostgreSQLDatabase extends SQLDatabase {

    /** Public constructor with name */
    public PostgreSQLDatabase(String name) {
        super(name);
    }

    @Override
    public void fillDatabase() {
        PostgreSQLInformationReader.getTableAndColumnNames(this);
        PostgreSQLInformationReader.getFKConstraints(this);
        PostgreSQLInformationReader.getIndexedColumns(this);
        PostgreSQLInformationReader.getTableAndColumnStatistics(this);
    }
 
    

}