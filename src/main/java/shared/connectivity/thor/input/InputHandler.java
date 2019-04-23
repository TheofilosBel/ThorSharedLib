package shared.connectivity.thor.input;

import java.util.Scanner;

/**
 * This class handles the input for the systems connected to thor.
 * The input is read from the standard input, line by line.
 * Every system must receive a query, the schema name, and the maximum number of results to return.
 * 
 * The systems must remain open after answering a query, and be ready for the next one.
 * The process stops when the input handler receives a shutdown message by our application.
 */
public class InputHandler {

    private String query;
    private String schemaName;
    private Boolean shutDownSystem; // Indicates whether the application sent a shutdown message.

    /**
     * Constructor.
     */
    public InputHandler() {
        this.query = null;
        this.schemaName = null;
        this.shutDownSystem = false;
    }
  
    /**
     * Reads the input line by line, and stores it.
     */
    public void readInput() {
        // Create a Scanner.
        Scanner console = new Scanner(System.in);
        System.out.println("<input>");

        this.query = console.nextLine(); // Read the query.

        // The '%exit' query is used to signal the shutdown.
        if (this.query.equals("%exit%")) {
            this.shutDownSystem = false;
        }
        else {
            this.schemaName = console.nextLine(); // Read the schema name.
        }

        // Print a line to indicate that the input to the program ends here.
        System.out.println("</input>");
        console.close(); // delete when using loop      
    }

    /**
     * @return The query to execute.
     */
    public String getQuery() {
        return query;
    }

    /**
     * @return The name of the schema the query will be executed against.
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * @return True if the application ordered the system to shutdown.
     */
    public Boolean shutDownSystem() {
        return shutDownSystem;
    }

}