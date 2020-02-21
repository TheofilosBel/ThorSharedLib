package shared.database.model;

import shared.database.connectivity.MySQLInformationReader;

public class MySqlDatabase extends SQLDatabase {

    /** Public constructor with name */
    public MySqlDatabase(String name) {
        super(name);
    }

    @Override
    public void fillDatabase() {
        MySQLInformationReader.getTableAndColumnNames(this);
        MySQLInformationReader.getFKConstraints(this);
        MySQLInformationReader.getIndexedColumns(this);
        MySQLInformationReader.getTableAndColumnStatistics(this);
    }
}