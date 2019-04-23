package shared.connectivity.thor.response;

import com.google.gson.Gson;

import shared.connectivity.thor.response.Architecture.ArchitectureLink;
import shared.connectivity.thor.response.Architecture.ArchitectureNode;

import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;

/** 
 * This class represents the response of a system to a query.
 */
public class Response<R extends ResultInterface> {
    
    private static class ComponentStats {

        public String componentName;
        public List<Table> componentInfo;

        public ComponentStats(String name, List<Table> componentInfo) {
            this.componentName = name;
            this.componentInfo = componentInfo;
        }

    }

    private String id;                                      // The id of the current system (the name in lowercase ans without spaces).
    private String name;                                    // The name of the current system.
    private Architecture architecture;                      // The architecture of the system.
    private List<ComponentTime> componentsTime;             // Saves the time taken by every component of the system.
    private List<ComponentStats> componentStats;           // Saves the statistics taken by every component of the system
    private double totalTime;                               // The total running time of the system.
    private List<Result<R>> results;                        // The top results returned by the system.

    /**
     * Constructor without {@link ComponentStatistics}.
     */
    public Response(String id, String name, List<Component> components, List<R> topResults) {
        this.id = id;
        this.name = name;        
        this.componentsTime = new ArrayList<>();
        this.componentsStats = new ArrayList<>();

        // Loop the Components and :
        //  - Create ComponentTime object for each component.
        //  - Compute the total time of execution.
        //  - Store component statistics.
        //  - Create the Architecture.
        this.totalTime = 0.0;
        List<ArchitectureNode> nodes = new ArrayList<>();
        List<ArchitectureLink> links = new ArrayList<>();
        for (Component comp: components) {
            // Times.
            this.componentsTime.add(new ComponentTime(comp.getName(), comp.getTime()));
            this.totalTime += comp.getTime();

            // Stats
            this.componentsStats.add(new ComponentStats(comp.getName(), comp.getComponentInfo()));

            // Architecture.
            nodes.add(new ArchitectureNode(comp.getId(), comp.getName()));
            List<Component> compsConnections = comp.getOutGoingConnections();  // The component's outgoing connections.
            List<String> connLabels = comp.getOutGoingLabels();                // The labels for the above connections.
            for (int idx = 0; idx < connLabels.size(); idx++) {
                links.add(new ArchitectureLink(
                    comp.getId(), compsConnections.get(idx).getId(), connLabels.get(idx)
                ));
            }
        }
        this.architecture = new Architecture(nodes, links);

        // Format the total time (round to 2 decimal points).
        DecimalFormat df = new DecimalFormat("#0.##");
        this.totalTime = Double.valueOf(df.format(this.totalTime));

        // ---------------------------
        // TODO UPDATE COMPONENT STATS
        // ---------------------------
                
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
     * Converts this response to JSON. Then output it between 
     * two lines that contain the keyword: <json>. THOR will read the 
     * response and render it.
     */
    public void sendToTHOR() {
        Gson gson = new Gson();              // Create a JSON 
        String json = gson.toJson(this);     // Convert this to JSON.

        // Print json in a way thor can read it.
        System.out.println("<json>");
        System.out.println(json);
        System.out.println("<json>");
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
    
    public List<ComponentStats> getComponentsStats() {
        return componentsStats;
    }

    public double getTotalTime() {
        return this.totalTime;
    }

    public List<Result<R>> getResults() {
        return this.results;
    }

}