package shared.connectivity.thor.response;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Component and its used to send info related to a
 * component directly to THOR.
 */
public class Component {
    private static Integer COMPONENT_COUNT = 0;

    private Integer id;                                // Component Id.
    private String name;                           // Component Name.
    private Double time;                           // Time it took to execute.
    private List<InfoElement> componentInfo;       // A list of Component Information.
    private List<Component> outGoingConnections;   // A list of Components which are connected with this Component on the systems workflow.
    private List<String> outGoingLabels;           // A list ot labels for the above connections.

    /** 
     * Constructor 
     */
    public Component(String name) {
        if (name.isEmpty()) 
            this.name = "unknown";
        else 
            this.name = name;

        this.id = COMPONENT_COUNT++;
        this.componentInfo = new ArrayList<>();
        this.outGoingConnections = new ArrayList<>();
    }

    /**
     * Connect with this component with destination component. The direction of the arrow 
     * in the systems architecture graph will be : this -[label]-> destination 
     * 
     * @param destination The destination Component.
     * @param label The label to put on this connection.
     */
    public void connectComponent(Component destination, String label) {
        this.outGoingConnections.add(destination);
        this.outGoingLabels.add(label);
    }


    /***********************
     * Getters And Setters *
     ***********************/

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the time
     */
    public Double getTime() {
        return time;
    }

    /**
     * @return the componentInfo
     */
    public List<InfoElement> getComponentInfo() {
        return componentInfo;
    }

    /**
     * @return the outGoingConnections
     */
    public List<Component> getOutGoingConnections() {
        return outGoingConnections;
    }

    /**
     * @return the outGoingLabels
     */
    public List<String> getOutGoingLabels() {
        return outGoingLabels;
    }

    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Double time) {
        DecimalFormat df = new DecimalFormat("#0.##");
        this.time = Double.valueOf(df.format(time));
    }

    /**
     * @param componentInfo the componentInfo to set
     */
    public void setComponentInfo(List<InfoElement> componentInfo) {
        this.componentInfo = componentInfo;
    }

    /**
     * @return the id
     */
    public String getId() {
        return this.name.charAt(0) + id.toString();
    }

}