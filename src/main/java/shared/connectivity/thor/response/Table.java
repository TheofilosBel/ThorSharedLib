package shared.connectivity.thor.response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a Table containing component statistics.
 */
public class Table {

    /**
     * This class represents a {@link Table} Row.
     */
    public static class Row {
        List<String> values;
        
        /** Constructor */
        public Row(List<String> values) {
            this.values = values;
        }

        /**
         * @return the values of this row.
         */
        public List<String> getValues() {
            return values;
        }

    }

    private String title;                 // The table Title 
    private List<String> columnTitles;    // The column Titles
    private List<Row> rows;               // The table rows.

    /** 
     * Use this constructor if only each row contains 2 values.
     * The columnTitles will be initialized to "Description" ,"Value"
     * The table title will be left empty
     */
    public Table(List<Row> rows) {
        this.title = null;
        this.columnTitles = new ArrayList<>(Arrays.asList("Description", "Value"));
        this.rows = rows;
    }

    /** 
     * Use this constructor if only each row contains 2 values.
     * The columnTitles will be initialized to "Description" ,"Value"     
     */
    public Table(String title, List<Row> rows) {
        this.title = title;
        this.columnTitles = new ArrayList<>(Arrays.asList("Description", "Value"));
        this.rows = rows;
    }

    /** 
     * Use this constructor in case you want to create a custom table.
     * Make sure that the number of columnTitles equals to the number of values 
     * each row contains.
     * The title will be left empty.
     */    
    public Table(List<String> columnTitles, List<Row> rows) {
        this.title = null;
        this.columnTitles = columnTitles;
        this.rows = rows;
    }

    /** 
     * Use this constructor in case you want to create a custom table.
     * Make sure that the number of columnTitles equals to the number of values 
     * each row contains.     
     */    
    public Table(String title, List<String> columnTitles, List<Row> rows) {
        this.title = title;
        this.columnTitles = columnTitles;
        this.rows = rows;
    }



    /***********************
     * Getters And Setters *
     ***********************/

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the columnTitles
     */
    public List<String> getColumnTitles() {
        return columnTitles;
    }

    /**
     * @return the rows
     */
    public List<Row> getRows() {
        return rows;
    }
    
}