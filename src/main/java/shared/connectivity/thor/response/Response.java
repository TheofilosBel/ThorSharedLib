package shared.connectivity.thor.response;

import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;

// Represents the response of a system to a query.
public class Response<R extends ResultInterface> {

    // Represents a node in the architecture of the system.
    public static class ArchitectureNode {

        private String id;
        private String label;

        public ArchitectureNode(String id, String label) {
            this.id = id;
            this.label = label;
        }

        public String getId() {
            return this.id;
        }

        public String getLabel() {
            return this.label;
        }

    }

    // Represents a link between two nodes in the architecture of the system.
    public static class ArchitectureLink {

        private String source;
        private String target;
        private String label;

        public ArchitectureLink(String source, String target, String label) {
            this.source = source;
            this.target = target;
            this.label = label;
        }

        public String getSource() {
            return this.source;
        }

        public String getTarget() {
            return this.target;
        }

        public String getLabel() {
            return this.label;
        }

    }

    // Represents the architecture of the system.
    public static class Architecture {

        private List<ArchitectureNode> nodes;
        private List<ArchitectureLink> links;

        public Architecture(List<ArchitectureNode> nodes, List<ArchitectureLink> links) {
            this.nodes = nodes;
            this.links = links;
        }

        public List<ArchitectureNode> getNodes() {
            return this.nodes;
        }

        public List<ArchitectureLink> getLinks() {
            return this.links;
        }

    }

    // Represents the time taken by a component of the system.
    public static class ComponentTime {

        private String component;
        private Double time;
        // private Double percentage;

        public ComponentTime(String component, Double time) {
            this.component = component;

            DecimalFormat df = new DecimalFormat("#0.##");
            this.time = Double.valueOf(df.format(time));
        }

        public String getComponent() {
            return this.component;
        }
        
        public Double getTime() {
            return this.time;
        }

        // public Double getPercentage() {
        //     return this.percentage;
        // }

        // public void setPercentage(Double percentage) {
        //     this.percentage = percentage;
        // }
    
    }

    // Represents a column-value pair of a tuple.
    public static class ColumnValuePair {

        private String column;
        private Object value;

        public ColumnValuePair(String column, Object value) {
            this.column = column;
            this.value = value;
        }

        public String getColumn() {
            return this.column;
        }

        public Object getValue() {
            return this.value;
        }

    }

    // Represents a single result tuple with its values and info.
    public static class Result<R extends ResultInterface> {

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

    private String id;                                      // The id of the current system (the name in lowercase ans without spaces).
    private String name;                                    // The name of the current system.
    private Architecture architecture;                      // The architecture of the system.
    private List<ComponentTime> componentsTime;             // Saves the time taken by every component of the system.
    private List<ComponentStatistics> componentStatistics;  // Saves the statistics that each component want to present to the user. 
    private double totalTime;                               // The total running time of the system.
    private List<Result<R>> results;                        // The top results returned by the system.

    /**
     * Constructor without {@link ComponentStatistics}.
     */
    public Response(String id, String name, Architecture architecture, List<ComponentTime> componentsTime,
            double totalTime, List<R> topResults) {
        this.id = id;
        this.name = name;
        this.architecture = architecture;
        this.componentsTime = componentsTime;
        this.componentStatistics = null;
        this.totalTime = totalTime;

        // // Update the percentage property of the componentsTime list.
        // for (ComponentTime componentTime : this.componentsTime) {
        //     componentTime.setPercentage(componentTime.getTime() / this.totalTime);
        // }

        // Create a Result object for every tuple.
        this.results = new ArrayList<Result<R>>();
        for (R systemResult : topResults) {
            this.results.add(new Result<>(systemResult));
        }
    }

    /**
     * Constructor with {@link ComponentStatistics}.
     */
    public Response(
      String id, String name, Architecture architecture,
      List<ComponentTime> componentsTime, List<ComponentStatistics> componentStatistics,
      double totalTime, List<R> topResults
    ) {
        this.id = id;
        this.name = name;
        this.architecture = architecture;
        this.componentsTime = componentsTime;
        this.componentStatistics = componentStatistics;
        this.totalTime = totalTime;

        // // Update the percentage property of the componentsTime list.
        // for (ComponentTime componentTime : this.componentsTime) {
        //     componentTime.setPercentage(componentTime.getTime() / this.totalTime);
        // }

        // Create a Result object for every tuple.
        this.results = new ArrayList<Result<R>>();
        for (R systemResult : topResults) {
            this.results.add(new Result<>(systemResult));
        }
    }



    // Getters and Setters.
    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Architecture getArchitecture() {
        return this.architecture;
    }

    public List<ComponentTime> getComponentsTime() {
        return this.componentsTime;
    }

    public double getTotalTime() {
        return this.totalTime;
    }

    public List<Result<R>> getResults() {
        return this.results;
    }

    

}