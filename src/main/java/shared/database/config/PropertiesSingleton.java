package shared.database.config;

import java.util.ResourceBundle;

/*
 * A singleton class that provides the application with resources
 * like database connectivity properties.
 */
public class PropertiesSingleton {

    private static ResourceBundle resource = null;

    public static ResourceBundle getBundle(String resourcePath) {
        // Lazy load the Singleton Class
        if (resource == null) {
            resource = ResourceBundle.getBundle(resourcePath);
            return resource;
        }

        return resource;
    }
    
}
