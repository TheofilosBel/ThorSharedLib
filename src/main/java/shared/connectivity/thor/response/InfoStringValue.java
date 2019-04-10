package shared.connectivity.thor.response;


/**
 * Represents an Information Value specifically a String type of value. 
 */
public class InfoStringValue implements InfoValue {

    private String stringValue;

    /**
     * Constructor
     */
    public InfoStringValue(String value) {
        this.stringValue = value;
    }


    /**
     * @return the stringValue
     */
    public String getStringValue() {
        return stringValue;
    }

}