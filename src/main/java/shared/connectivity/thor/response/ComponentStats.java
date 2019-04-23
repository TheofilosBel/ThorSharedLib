package shared.connectivity.thor.response;

import java.util.List;

/**
 * 
 */
public class ComponentStats {
    public String componentName;
    public List<Table> componentInfo;

    public ComponentStats(String name, List<Table> componentInfo) {
        this.componentName = name;
        this.componentInfo = componentInfo;
    }

}