package shared.connectivity.thor.response;

import java.util.List;

/**
 * This class stores Statistics for from the System's Components. The statistics
 * are a part of the Response Class.
 */
public class ComponentStatistics {

    /**
     * This Class stores the Statistic Description with it's respective value.
     */
    // TODO Make value List.
    public static class DescriptionValuePair {
        private String description;    // Statistic Description 
        private Object value;          // Statistic Value

        /**
         * Constructor
         */
        public DescriptionValuePair(String description, Object value) {
            this.description = description;
            this.value = value;
        }

        /**
         * @return the type
         */
        public String getDescription() {
            return description;
        }

        /**
         * @return the value
         */
        public Object getValue() {
            return value;
        }

    }


    private String componentName;                               // Component Name
    private List<DescriptionValuePair> descriptionValuePairs;   // Description Value pairs


    /**
     * Constructor
     */
    public ComponentStatistics(String componentName, List<DescriptionValuePair> descriptionValuePairs) {
        this.componentName = componentName;
        this.descriptionValuePairs = descriptionValuePairs;
    }
}