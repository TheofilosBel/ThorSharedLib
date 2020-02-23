package shared.database.connectivity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.apache.commons.dbcp.BasicDataSource;

import shared.database.connectivity.DatabaseConfigurations.DatabaseType;

/**
 * This class is the main Connection provider for our Systems.
 * First loadDBConfigurations and then getConnection from the pool.
 */
public class DataSourceFactory {    
    private static final Logger LOGGER = Logger.getLogger(DataSourceFactory.class.getName());  // The LOGGER
    private static BasicDataSource ds = null;                                                  // The DataSource Object
    private static DatabaseType type = null;                                                          // The database type {psql, mysql}
        
    /**
     * Loads tha database configurations
     */
    public static void loadDbConfigurations(DatabaseConfigurations config) {
        // Get the connection parameters.
        if (config.isAssigned()) {
            ds = new BasicDataSource();
            ds.setUrl(config.getFormatedURL());
            ds.setDriverClassName(config.getDriver());
            ds.setUsername(config.getUserName());
            ds.setPassword(config.getPassword());
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
            type = config.getType();
        }
        else {        
            LOGGER.info("[ERR] Configuration Object not assigned");
        } 
    }    

    /**
     * Return a connection with the database specified by the configurations loaded.
     * 
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     * @return the Database type {psql, mysql}
     */
    public static DatabaseType getType() {
        return type;
    }
}
