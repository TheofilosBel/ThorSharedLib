package shared.connectivity.thor.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import shared.database.model.DatabaseType;

/**
 * This class handles the input for the systems connected to thor.
 * The input is read from the standard input, line by line.
 * Every system must receive a query, the schema name, and the maximum number of results to return.
 * 
 * The systems must remain open after answering a query, and be ready for the next one.
 * The process stops when the input handler receives a shutdown message by our application.
 */
public class InputHandler {

    private String query;           // The free form query.
    private String databaseName;    // The database name.
    private DatabaseType databaseType;    // The Implementation of the database: Mysql or PostgreSQL
    private Integer resultsNum;     // The maximum number of results to be returned by the system.
    private Boolean shutDownSystem; // Indicates whether the application ordered a shutdown.

    /**
     * Constructor.
     */
    public InputHandler() {
        this.query = null;
        this.databaseName = null;
        this.shutDownSystem = false;
    }

    /**
     * Reads the input line by line, and stores it.
     */
    public void readInput() {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);

        System.out.println("<input>");

        // Read the parameters needed for the execution from the standard input.
        try {
            this.query = br.readLine();

            // The '%exit' query signals the systems to shutdown.
            if (this.query.equals("%exit%")) {
                this.shutDownSystem = true;
            }
            else {
                String dbNameAndType = br.readLine();  // This is formated like <type>.<dbname> , for example mysql.IMDB

                // If dbNameAndType does not contain a "." use MySQL as database type.
                // Else split the dbNameAndType to ".""
                if (!dbNameAndType.contains(".")) {                                        
                    this.databaseType = DatabaseType.getTypeFromString("mysql");
                    this.databaseName = dbNameAndType;
                } else {
                    this.databaseType = DatabaseType.getTypeFromString(dbNameAndType.split("\\.")[0]);
                    this.databaseName = dbNameAndType.split("\\.")[1];
                }
                this.resultsNum = Integer.parseInt(br.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.query = "";
            this.databaseName = "";
            this.databaseType = null;
            this.shutDownSystem = true;
        }

        // Print a line to indicate that the input to the program ends here.
        System.out.println("</input>");
    }

    /**
     * @return The query to execute.
     */
    public String getQuery() {
        return query;
    }

    /**
     * @return the databaseType
     */
    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    /**
     * @return The name of the schema the query will be executed against.
     */
    public String getDatabaseName() {
        return databaseName;
    }

    /**
     * @return True if the application ordered the system to shutdown.
     */
    public Boolean shutDownSystem() {
        return shutDownSystem;
    }

    /**
     * @return The maximum number of results to be returned by the system.
     */
    public Integer getResultsNumber() {
        return resultsNum;
    }

}