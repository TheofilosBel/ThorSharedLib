package shared.connectivity.thor.response;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an element that is part of the Component Statistics.
 * Each element contains a Description and a list of Values. 
 */
public class InfoElement implements InfoValue {

    private String description;          // Describes the values.
    private List<InfoValue> values;      // A list of values.

    /**
     * Single value constructor.
     */
    public InfoElement(String description, InfoValue value) {
        this.description = description;
        this.values = new ArrayList<>();
        this.values.add(value);
    }

    /**
     * Multiple value constructor
     */
    public InfoElement(String description, List<InfoValue> values) {
        this.description = description;        
        this.values = values;
    }

    /***********************
     * Getters and Setters *
     ***********************/

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return the values
     */
    public List<InfoValue> getValues() {
        return values;
    }

}