package shared.connectivity.thor.response;

import java.util.Collection;


/**
 * This class must be implemented by the "Result" Class produced by each system
 * that wants to get added to Thor. The "Result" Class is whatever the system
 * wants to display on Thor Results page
 */
public interface ResultInterface {

    /**
     * The List<ColumnValuePair> is a list containing the 
     * results of the system in pairs of :
     *  <column name containing value> : <value>
     */
    public Collection<ColumnValuePair> getColumnValuePairs();


    /**
	 * Networks is a list of Table Names used to join and create this result.
     */
    public Collection<String> getNetworks();


    /**
     * The Query used to create this result.
     */
    public String getQuery();


    /**
     * Returns true if the Result has a score field separate from 
     * the column Value Pairs
     */
    public boolean hasScoreField();


    /**
     * Is hasScoreField is true then getScore must return the Results score
     */
    public double getResultScore();

}