package shared.connectivity.thor.input;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * This Class handles input for the systems connected with thor. Each system
 * must input a Query and A Schema Name.
 */
public class InputHandler {

    private String query;             // The query passed by Thor via System.in
    private String schemaName;        // The schema Name passed by Thor via System.in
    private Integer resultsNum;        // The number of results asked by the user.
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
     * Reads the query and schema name and stores them.
     */
    public void readInput() {
        InputStreamReader r = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(r);

        System.out.println("<input>");

        // Read the parameters needed for the execution from the stdin.
        try {
            this.query = br.readLine();
            // If the query is equal to '%exit%' then we need to stop the 
            // execution of the program using the handler.
            if (this.query.equals("%exit%"))
                this.shutDownSystem = true;
            else {
                this.schemaName = br.readLine();
                this.resultsNum = br.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
            this.query = "";
            this.schemaName = "";
            this.shutDownSystem = true;
        }

        // Print a line to indicate that the input to the program ends here.
        System.out.println("</input>");
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
     * @return True if Thor ordered a systemShutdown
     */
    public Boolean shutDownSystem() {
        return shutDownSystem;
    }

    /**
     * @return the resultsNum
     */
    public Integer getResultsNumber() {
        return resultsNum;
    }

}