package shared.database.connectivity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.apache.commons.dbcp.BasicDataSource;


/**
 * This class is the main Connection provider for our Systems.
 * First loadDBConfigurations and then getConnection from the pool.
 */
public class DataSourceFactory {    
    private static final Logger LOGGER = Logger.getLogger(DataSourceFactory.class.getName());  // The LOGGER
    private static BasicDataSource ds = null;                                                  // The DataSource Object
        
    /**
     * Loads tha database configurations         
     */
    public static void loadDbConfigurations(DatabaseConfigurations config) {
        // Get the connection parameters.
        if (config.isAssigned()) {
            ds = new BasicDataSource();
            ds.setUrl(config.getFormatedURL());
            ds.setDriverClassName(DatabaseConfigurations.getDriver());
            ds.setUsername(config.getUserName());
            ds.setPassword(config.getPassword());
            ds.setMinIdle(5);
            ds.setMaxIdle(10);
            ds.setMaxOpenPreparedStatements(100);
        }
        else {        
            LOGGER.info("[ERR] Configuration Object not assigned");
        } 
    }    

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
         
}
