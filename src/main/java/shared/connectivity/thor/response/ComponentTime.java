package shared.connectivity.thor.response;

import java.text.DecimalFormat;

// Represents the time taken by a component of the system.
public class ComponentTime {

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