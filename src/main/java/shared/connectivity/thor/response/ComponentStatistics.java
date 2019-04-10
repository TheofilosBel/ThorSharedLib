package shared.connectivity.thor.response;

import java.util.List;

/**
 * This class stores Statistics for from the System's Components. The statistics
 * are a part of the Response Class.
 */
public class ComponentStatistics {
   
    private String componentName;               // Component Name.
    private List<InfoElement> componentInfo;    // A list of Component Information.


    /**
     * Constructor
     */
    public ComponentStatistics(String componentName, List<InfoElement> componentInfo) {
        this.componentName = componentName;
        this.componentInfo = componentInfo;
    }
}