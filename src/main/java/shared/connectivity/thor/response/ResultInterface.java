package shared.connectivity.thor.response;

import java.util.Collection;
import java.util.List;

/**
 * This class must be implemented by the "Result" Class produced by each system
 * that wants to get added to Thor. The "Result" Class is whatever the system
 * wants to display on Thor Results page
 */
public interface ResultInterface {

    /**
     * The List<Response.ColumnValuePair> is a field in the Response.Result
     * Class. Thus each Result must be able to fill that. 
     */
    public Collection<ColumnValuePair> getColumnValuePairs();


    /**
     * Networks is a field in the Response.Result Class.
     * Thus each Result must be able to fill that. Networks is 
     * a list of Table Names used to join and create this result.
     */
    public Collection<String> getNetworks();


    /**
     * Networks is a field in the Response.Result Class.
     * Thus each Result must be able to fill that.
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