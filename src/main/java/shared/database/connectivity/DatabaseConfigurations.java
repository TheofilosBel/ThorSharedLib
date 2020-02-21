package shared.database.connectivity;

import java.util.Objects;
import java.util.ResourceBundle;

import shared.database.config.PropertiesSingleton;

/**
 * This class contains the Database configuration properties
 */
public class DatabaseConfigurations {
    public enum DatabaseType { psql, mysql};   // The database type.

    private static final String mysqlDriver = "com.mysql.jdbc.Driver";
    private static final String psqlDriver = "org.postgresql.Driver";
    

    private static final String mysqlURL = "jdbc:mysql://%s:%s/%s?useSSL=%s";
    private static final String psqlURL = "jdbc:postgresql://%s:%s/%s";


    private String schemaName = null;           // The schema Name
    private String userName = null;             // The user Name
    private String password = null;             // The password
    private String hostName = "localhost";      // The hostName
    private String portNumber = "3306";         // The port number
    private Boolean useSSL = false;             // The useSSL boolean
    private DatabaseType type = null;           // The database type {psql, mysql}

    public DatabaseConfigurations(String propertiesFileName, String schemaName) {
        // Get the app properties file.
        ResourceBundle resource = PropertiesSingleton.getBundle(propertiesFileName);

        // Initialize the configs
        try {
            this.schemaName(schemaName)        
              .userName(resource.getString("database.username"))
              .password(resource.getString("database.password"))
              .hostName(resource.getString("database.hostname"))
              .portNumber(resource.getString("database.portnumber"))
              .databaseType((resource.getString("database.type").equals("psql"))? DatabaseType.psql : DatabaseType.mysql );
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[ERR] Could not initialize Configurations with properties file: " + propertiesFileName);
        }
    }

    public DatabaseConfigurations(String schemaName, String userName, String password) {
        this.schemaName = schemaName;
        this.userName = userName;
        this.password = password;
    }

    public DatabaseConfigurations(String schemaName, String userName, String password, String host, String port) {
        this.schemaName = schemaName;
        this.userName = userName;
        this.password = password;
        this.hostName = host;
        this.portNumber = port;
    }

    /**
     * Returns the URL formated with the parameters hostName, portNumber, schemaName, useSSL
     */
    public String getFormatedURL() {
        if (this.type == DatabaseType.psql)
            return String.format(psqlURL, this.hostName, this.portNumber, this.schemaName);
        else if (this.type == DatabaseType.mysql)
            return String.format(mysqlURL, this.hostName, this.portNumber, this.schemaName, String.valueOf(this.useSSL));
        else 
            throw new RuntimeException("Database type unspecified");
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

    /**
     * @return the type
     */
    public DatabaseType getType() {
        return type;
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

    public DatabaseConfigurations databaseType(DatabaseType type) {
        this.type = type;
        return this;
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        if (this.type == DatabaseType.psql)
            return psqlDriver;
        else if (this.type == DatabaseType.mysql)
            return mysqlDriver;
        else 
            throw new RuntimeException("Database type unspecified");
    }
}