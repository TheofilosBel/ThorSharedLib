package shared.connectivity.thor.input;

import java.util.Scanner;

/**
 * This Class handles input for the systems connected with thor. Each system
 * must input a Query and A Schema Name.
 */
public class InputHandler {

    private String query;             // The query passed by Thor via System.in
    private String schemaName;        // The schema Name passed by Thor via System.in
    private Boolean shutDownSystem;   // Indicates if Thor send us a signal to shut down

    /**
     * Constructor
     */
    public InputHandler() {
        this.query = null;
        this.schemaName = null;
        this.shutDownSystem = false;
    }

    /**
     * Reads the query and schema name and stores them in the Class's fields
     */
    public void readInput() {
        Scanner console = new Scanner(System.in); // create a Scanner
        System.out.println("<input>");

        // Read the parameters needed for the execution from the stdin.            
        this.query = console.nextLine();
        this.schemaName = console.nextLine();            

        // Print a line to indicate that the input to the program ends here.
        System.out.println("</input>");
        console.close(); // delete when using loop

        // Keep the system open for one query only
        this.shutDownSystem = true;
    }

    /**
     * @return the query
     */
    public String getQuery() {
        return query;
    }

    /**
     * @return the schemaName
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * @return the closeSystem
     */
    public Boolean shutDownSystem() {
        return shutDownSystem;
    }

}