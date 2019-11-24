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

    private Double parserOutput;
    private Double entityMapperOutput;
    private Double interpretationGeneratorOutput;
    private Double translatorAndExecutorOutput;
    


    /**
     * @param parserOutput the parserOutput to set
     */
    public void setParserOutput(Double parserOutput) {
        this.parserOutput = parserOutput;
    }

    /**
     * @return the ParserOutput
     */
    public Double getParserOutput() {
        return parserOutput;
    }

    /**
     * @param entityMapperOutput the entityMapperOutput to set
     */
    public void setEntityMapperOutput(Double entityMapperOutput) {
        this.entityMapperOutput = entityMapperOutput;
    }

    /**
     * @return the entityMapperOutput
     */
    public Double getEntityMapperOutput() {
        return entityMapperOutput;
    }

    /**
     * @param interpretationGeneratorOutput the interpretationGeneratorOutput to set
     */
    public void setInterpretationGeneratorOutput(Double interpretationGeneratorOutput) {
        this.interpretationGeneratorOutput = interpretationGeneratorOutput;
    }

    /**
     * @return the interpretationGeneratorOutput
     */
    public Double getInterpretationGeneratorOutput() {
        return interpretationGeneratorOutput;
    }


    /**
     * @param translatorAndExecutorOutput the translatorAndExecutorOutput to set
     */
    public void setTranslatorAndExecutorOutput(Double translatorAndExecutorOutput) {
        this.translatorAndExecutorOutput = translatorAndExecutorOutput;
    }

    /**
     * @return the translatorAndExecutorOutput
     */
    public Double getTranslatorAndExecutorOutput() {
        return translatorAndExecutorOutput;
    }
}