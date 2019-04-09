package shared.connectivity.thor.response;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

// Represents a single result tuple with its values and info.
public class Result<R extends ResultInterface> {

    private List<ColumnValuePair> data;
    private List<String> network;
    private String query;

    public Result(R systemsResult) {
        // Fill the Column Value Pair
        this.data = new ArrayList<>(systemsResult.getColumnValuePairs());

        // Insert the score column independently since it is not part of the actual attributes.
        if (systemsResult.hasScoreField()) {
            DecimalFormat df = new DecimalFormat("#0.##");
            this.data.add(new ColumnValuePair("score", Double.valueOf( df.format(systemsResult.getResultScore()))) );
        }
        
        // Fill the Networks
        this.network = new ArrayList<>(systemsResult.getNetworks());

        // Fill the Query
        this.query = systemsResult.getQuery();
    }

    public List<ColumnValuePair> getData() {
        return this.data;
    }

    public List<String> getNetwork() {
        return this.network;
    }

    public String getQuery() {
        return this.query;
    }
}