package shared.database.config;

import java.util.Enumeration;
import java.util.Properties;
import java.util.ResourceBundle;

/*
 * A singleton class that provides the application with resources
 * like database connectivity properties.
 */
public class PropertiesSingleton {

    private static ResourceBundle resource = null;

    public static void loadPropertiesFile(String resourcePath) {
        // Lazy load the Singleton Class
        if (resource == null)
            resource = ResourceBundle.getBundle(resourcePath);
    }

    public static ResourceBundle getBundle() {
        return resource;
    }

    /**
     * From ResourceBundle to Properties
     */
    public static Properties convertResourceBundleToProperties(ResourceBundle resource) {
        Properties properties = new Properties();
        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            properties.put(key, resource.getString(key));
        }
        return properties;
    }
    
}
