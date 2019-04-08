package shared.database .model;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

// This class models a SQL database.
public class SQLDatabase {

    private String name; // The database name.
    private List<SQLTable> tables; // List of tables in the database.
    private List<SQLForeignKeyConstraint> fkConstraints; // List of foreign key constraints between tables.

    public SQLDatabase() {
        this.tables = new ArrayList<SQLTable>();
        this.fkConstraints = new ArrayList<SQLForeignKeyConstraint>();
    }

    public SQLDatabase(String name) {
        this.name = name;
        this.tables = new ArrayList<SQLTable>();
        this.fkConstraints = new ArrayList<SQLForeignKeyConstraint>();
    }

    /**
     * @param types The types that we are searching for.
     * @return A List ot SQLColumns with the above types. No pks or fks are returned
     */
    public List<SQLColumn> getColumnsByType(SQLType... types) {
        List<SQLColumn> matchedColumns = new ArrayList<>(); // The Matches columns.

        // Loop all the columns in the Database.
        for (SQLColumn column : this.getAllColumns()) {
            if (column.isPrimary() || column.isForeign())
                continue; // Skip fks and pks.
            for (int i = 0; i < types.length; i++)
                if (column.getType().equals(types[i]))
                    matchedColumns.add(column);
        }

        // Return the Matches Columns.
        return matchedColumns;
    }

    public List<SQLColumn> getAllColumns() {
        List<SQLColumn> columns = new ArrayList<>();
        for (SQLTable table : this.tables)
            columns.addAll(table.getColumns());
        return columns;
    }

    // Returns the table with the given name, or null if not found.
    public SQLTable getTableByName(String tableName) {
        for (SQLTable table : this.tables) {
            if (table.getName().equals(tableName)) {
                return table;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        // Add the name.
        String str = new String("Database name: " + this.name + "\n");

        // Add the tables and columns.
        for (SQLTable table : this.tables) {
            str += "\t" + table.getName() + " ( ";
            for (SQLColumn column : table.getColumns()) {
                str += column.getName() + ((column.isIndexed()) ? ("*") : ("")) + " ";
            }
            str += ")\n";
        }

        // Add the constraints.
        str += "\nForeign Key constraints (PK->FK):\n";
        for (SQLForeignKeyConstraint constraint : this.fkConstraints) {
            str += "\t" + constraint.getPrimaryKeyColumn() + " --> " + constraint.getForeignKeyColumn() + "\n";
        }

        return str;
    }

    // Getters and Setters.
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SQLTable> getTables() {
        return this.tables;
    }

    public void setTables(List<SQLTable> tables) {
        this.tables = tables;
    }

    public List<SQLForeignKeyConstraint> getFKConstrains() {
        return this.fkConstraints;
    }

    public void setFKConstrains(List<SQLForeignKeyConstraint> fkConstraints) {
        this.fkConstraints = fkConstraints;
    }

    // Adds a new table.
    public void addTable(SQLTable table) {
        this.tables.add(table);
    }

    // Adds a new constraint.
    public void addFKConstraint(SQLForeignKeyConstraint constraint) {
        this.fkConstraints.add(constraint);
    }

    // Returns true if the database object is empty.
    public boolean isEmpty() {
        return this.tables.isEmpty();
    }

    // Return ta column by it's name
    public SQLColumn getColumnByName(String columnName) {
        for (SQLTable table: this.tables) {
            SQLColumn col = table.getColumnByName(columnName);
            if (col != null)
                return col;
        }
		return null;
	}

}
