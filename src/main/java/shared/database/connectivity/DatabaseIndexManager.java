package shared.database.connectivity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import shared.database.model.SQLColumn;
import shared.database.model.SQLIndexResult;
import shared.database.model.SQLQueries;
import shared.database.model.SQLTable;
import shared.database.model.SQLTuple;

/**
 * This class manages the inverted indexes located in a database.
 */
public class DatabaseIndexManager {

    /**
     * Get the {@link SQLIndexResult} for the Parameter Keyword. Search only for Tuple Values that contain 
     * the keyword from the tuples of the param attributes (if attr not of textual type we will just skip it).
     * 
     * @param keyword The keyword to search for.
     * @param attributes The attributes whose tuples we are going to search.
     * @return A List of {@link SQLIndexResult}.
     */
    public static List<SQLIndexResult> searchKeyword(String keyword, List<SQLColumn> attributes) {
        List<SQLIndexResult> indexResults = new ArrayList<>();  // The mapped elements.

        // Initialize the connection Variables
        try (Connection con = DataSourceFactory.getConnection()) {
            // Loop each column and get its text mapping
            for (SQLColumn attr: attributes) {

                // Get the tuples where the element's value is contained.
                List<SQLTuple> tuples = getInAttrOccurrences(keyword, attr, con);
                if (tuples != null && !tuples.isEmpty())
                    indexResults.add(new SQLIndexResult(keyword, tuples));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }        

        // Returned the Mapped Elements.
        return indexResults;
    }


    /** 
     * Returns the SQLTuples found in the parm attribute contain the parm keyword
     */
    public static List<SQLTuple> getInAttrOccurrences(String keyword, SQLColumn attribute, Connection con) 
      throws SQLException
    {                        
        // If the attribute is not Textual the return an empty list.
        if (!attribute.getType().isTextual()) return null;

        List<SQLTuple> tuples = new ArrayList<>();               // The List of Tuples where the keyword is contained.
        String query = null;                                     // The query to execute.
        SQLTable table = attribute.getTable();                   // The attribute's SQLTable.
        SQLColumn pk = table.getPrimaryKey().iterator().next();  // Get the pk from the Table

        // Get the Value's attribute and the values ID
        String attrsToSelect = pk.toString() + ", " + attribute.toString();
        
        // If the Attribute has a fulltext index then use it.
        if (attribute.isIndexed())
            query = String.format(SQLQueries.INV_INDEX_QUERY, attrsToSelect, table.getName(), attribute.getName());                    
        // Else use a LIKE '%..%' query to search the attribute if table.rows are not many
        else if (table.getRowsNum() < 100000)
            query = String.format(SQLQueries.LIKE_QUERY, attrsToSelect, table.getName(), attribute.getName());                    
        // Else return null.        
        else 
            return null;
        
        
        try(PreparedStatement stmt = con.prepareStatement(query)) {
            // Parameterized the stmt in the right way.
            if (attribute.isIndexed()) 
                stmt.setString(1, DatabaseUtil.prepareForAndBooleanSearch(keyword)); 
            else 
                stmt.setString(1, "%\"" + keyword + "\"%");

            // DEBUG PRINT
            System.out.println("SQLQuery: " + stmt.toString());
            // ============
            
            // Execute the query and store the result.
            try (ResultSet rs = stmt.executeQuery() ) {
                while(rs.next()) {
                    SQLTuple tup = new SQLTuple();
                    tup.fill(Arrays.asList(pk, attribute), rs);
                    tuples.add(tup);
                } 
            }        
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the matched tuples.
        return tuples;
    }

}