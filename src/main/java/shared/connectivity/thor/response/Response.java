package shared.connectivity.thor.response;

import java.util.List;
import java.util.ArrayList;
import java.text.DecimalFormat;

// Represents the response of a system to a query.
public class Response<R extends ResultInterface> {
    
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