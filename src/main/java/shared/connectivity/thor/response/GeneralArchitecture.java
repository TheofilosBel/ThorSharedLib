package shared.connectivity.thor.response;

/**
 * Based on the Paper "...", the General Architecture of an NL to SQL System consists of 4 components:
 *  - Parser: Parses the input user query and produces a structured representation out of it (eg ParseTree)
 *  - Entity Mapper: Find the entities refered by the user in the query in the underlying database.
 *  - Interpretation Generator: Generate the query from the system perspective, like a query graph containing joins and filters.
 *  - SQL Translator & Executor: Translates the above interpretations in SQL queries and executes them against an RDBMS.
 * 
 *  Each of the above outputs a number of structures, by setting the information bellow THOR 
*   will be able to display and compare the database search System equally:
 *  - Parser outputs different (or even one) possible Parses (like parse Trees). Set the number of parses.
 *  - Entity Mapper outputs Database Mappings per Term found in the user query. Set the average number of columns where keyword were found.
 *  - Interpretation Generator outputs structured representation of the query from the System/Db perspective, capturing joins/filters/... . Set the number of those representations
 *  - For the SQL Translator $ Executor component simply set the number of SQL queries issued to the RDBMS.
 */
public class GeneralArchitecture {

    // public Component parser = new Component("Parser");
    // public Component mapper = new Component("EntityMapper");
    // public Component irGenerator = new Component("Interpretation Generator");
    // public Component sqlTransAndExec = new Component("SQL Translator & Executor");

    private Double numOfParses;
    private Double numOfMappings;
    private Double numOfInterpretations;
    private Double numOfSQLQueries;
    


    /**
     * @param numOfParses the numOfParses to set
     */
    public void setNumOfParses(Double numOfParses) {
        this.numOfParses = numOfParses;
    }

    /**
     * @return the numOfParses
     */
    public Double getNumOfParses() {
        return numOfParses;
    }

    /**
     * @param numOfInterpretations the numOfInterpretations to set
     */
    public void setNumOfInterpretations(Double numOfInterpretations) {
        this.numOfInterpretations = numOfInterpretations;
    }

    /**
     * @return the numOfInterpretations
     */
    public Double getNumOfInterpretations() {
        return numOfInterpretations;
    }

    /**
     * @param numOfMappings the numOfMappings to set
     */
    public void setNumOfMappings(Double numOfMappings) {
        this.numOfMappings = numOfMappings;
    }

    /**
     * @return the numOfMappings
     */
    public Double getNumOfMappings() {
        return numOfMappings;
    }


    /**
     * @param numOfSQLQueries the numOfSQLQueries to set
     */
    public void setNumOfSQLQueries(Double numOfSQLQueries) {
        this.numOfSQLQueries = numOfSQLQueries;
    }

    /**
     * @return the numOfSQLQueries
     */
    public Double getNumOfSQLQueries() {
        return numOfSQLQueries;
    }
}