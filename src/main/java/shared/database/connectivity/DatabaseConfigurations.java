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

    // The Singleton Pattern
    private static DatabaseConfigurations instance;

    private String databaseName = null;         // The database Name
    private String userName = null;             // The user Name
    private String password = null;             // The password
    private String hostName = "localhost";      // The hostName
    private String portNumber = "3306";         // The port number
    private Boolean useSSL = false;             // The useSSL boolean
    private DatabaseType type = null;           // The database type {psql, mysql}


    /**
     * Fill the configuration using the configuration file name and the database name.
     * 
     * @param propertiesFileName
     * @param databaseName
     */
    public static void fill(String propertiesFileName) {
        if (instance == null)
            instance = new DatabaseConfigurations(propertiesFileName);
    }    

    /**
     * This function updates the database name on the current configurations and reload 
     * the configurations used on {@link DataSourceFactory}.
     * 
     * @param databaseName
     */
    public static void useDatabase(String databaseName) {
        if (instance != null) {
            instance.databaseName = databaseName;
            DataSourceFactory.loadDbConfigurations(instance);
        }
        else {
            throw new RuntimeException("[ERR] Uninitialized Configurations. Please call DatabaseConfigurations.fill() to initialize them");
        }
    }

    /**
     * @return the Singleton Database Instance
     */
    public static DatabaseConfigurations getInstance() {
        return instance;        
    }


    private DatabaseConfigurations(String propertiesFileName) {
        // Get the app properties file.
        ResourceBundle resource = PropertiesSingleton.getBundle(propertiesFileName);

        // Initialize the configs
        try {
            this.databaseName(null)        
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

    public DatabaseConfigurations(String databaseName, String userName, String password) {
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
    }

    public DatabaseConfigurations(String databaseName, String userName, String password, String host, String port) {
        this.databaseName = databaseName;
        this.userName = userName;
        this.password = password;
        this.hostName = host;
        this.portNumber = port;
    }

    /**
     * Returns the URL formated with the parameters hostName, portNumber, databaseName, useSSL
     */
    public String getFormatedURL() {
        if (this.type == DatabaseType.psql)
            return String.format(psqlURL, this.hostName, this.portNumber, this.databaseName);
        else if (this.type == DatabaseType.mysql)
            return String.format(mysqlURL, this.hostName, this.portNumber, this.databaseName, String.valueOf(this.useSSL));
        else 
            throw new RuntimeException("Database type unspecified");
    }

    /**
     * Returns a boolean indicating if the DatabaseConfiguration object is Assigned with all the 
     * needed information to be used as to configure a database.
     */
    public boolean isAssigned() {
        return this.databaseName != null && this.userName != null && this.password != null &&
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
        return Objects.equals(databaseName, databaseProperties.databaseName) && Objects.equals(userName, databaseProperties.userName) && Objects.equals(password, databaseProperties.password) && Objects.equals(hostName, databaseProperties.hostName) && Objects.equals(portNumber, databaseProperties.portNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(databaseName, userName, password, hostName, portNumber);
    }

    @Override
    public String toString() {
        return "{" +
            " databaseName='" + getDatabaseName() + "'" +
            ", userName='" + getUserName() + "'" +
            ", password='" + getPassword() + "'" +
            ", hostName='" + getHostName() + "'" +
            ", portNumber='" + getPortNumber() + "'" +
            "}";
    }


    // Getters and Setters

    public String getDatabaseName() {
        return this.databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
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

    public DatabaseConfigurations databaseName(String databaseName) {
        this.databaseName = databaseName;
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