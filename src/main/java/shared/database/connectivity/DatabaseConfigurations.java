package shared.database.connectivity;

import java.util.Objects;
import java.util.ResourceBundle;

import shared.database.config.PropertiesSingleton;

/**
 * This class contains the Database configuration properties
 */
public class DatabaseConfigurations {

    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://%s:%s/%s?useSSL=%s";

    private String schemaName = null;           // The schema Name
    private String userName = null;             // The user Name
    private String password = null;             // The password
    private String hostName = "localhost";      // The hostName
    private String portNumber = "3306";         // The port number
    private Boolean useSSL = false;             // The useSSL boolean


    public DatabaseConfigurations(String propertiedFileName, String schemaName) {
        // Get the app properties file.
        ResourceBundle resource = PropertiesSingleton.getBundle(propertiedFileName);

        // Initialize the configs
        try {
            this.schemaName(schemaName)                    
              .userName(resource.getString("database.mysql.username"))
              .password(resource.getString("database.mysql.password"))
              .hostName(resource.getString("database.mysql.hostname"))
              .portNumber(resource.getString("database.mysql.portnumber"));
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERR] Could not initialize Configurations with properties file: " + propertiedFileName);
        }
    }

    /**
     * Returns the URL formated with the parameters hostName, portNumber, schemaName, useSSL
     */
    public String getFormatedURL() {
        return String.format(URL, this.hostName, this.portNumber, this.schemaName, String.valueOf(this.useSSL));
    }

    /**
     * Returns a boolean indicating if the DatabaseConfiguration object is Assigned with all the 
     * needed information to be used as to configure a database.
     */
    public boolean isAssigned() {
        return this.schemaName != null && this.userName != null && this.password != null &&
            this.hostName != null &&  this.portNumber != null && this.useSSL != null;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DatabaseConfigurations)) {
            return false;
        }
        DatabaseConfigurations databaseProperties = (DatabaseConfigurations) o;
        return Objects.equals(schemaName, databaseProperties.schemaName) && Objects.equals(userName, databaseProperties.userName) && Objects.equals(password, databaseProperties.password) && Objects.equals(hostName, databaseProperties.hostName) && Objects.equals(portNumber, databaseProperties.portNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(schemaName, userName, password, hostName, portNumber);
    }

    @Override
    public String toString() {
        return "{" +
            " schemaName='" + getSchemaName() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", hostName='" + getHostName() + "'" +
            ", portNumber='" + getPortNumber() + "'" +
            "}";
    }


    // Getters and Setters

    public String getSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHostName() {
        return this.hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getPortNumber() {
        return this.portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public DatabaseConfigurations schemaName(String schemaName) {
        this.schemaName = schemaName;
        return this;
    }

    public DatabaseConfigurations userName(String userName) {
        this.userName = userName;
        return this;
    }

    public DatabaseConfigurations password(String password) {
        this.password = password;
        return this;
    }

    public DatabaseConfigurations hostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public DatabaseConfigurations portNumber(String portNumber) {
        this.portNumber = portNumber;
        return this;
    }

    /**
     * @return the driver
     */
    public static String getDriver() {
        return driver;
    }    
}