package shared.database.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The database type, represents the underlying implementation of the database we want to read.
 */
public enum DatabaseType {
    
    PostgreSQL("psql"),
    MySQL("mysql");
    

    private final static HashMap<DatabaseType, String> typeToString = new HashMap<DatabaseType, String>(){{ 
        put(DatabaseType.PostgreSQL, "psql");
        put(DatabaseType.MySQL, "mysql");
     }};


    private final String type;
    private DatabaseType(final String type) { 
        this.type = type;        
    }

    public boolean isPostgreSQL() { return this.type.equals(typeToString.get(PostgreSQL));}
    public boolean isMySQL() { return this.type.equals(typeToString.get(MySQL)); }
    public String getType() { return this.type;}
    public static DatabaseType getTypeFromString(String type) {
        for (Map.Entry<DatabaseType, String> entry: typeToString.entrySet())
            if (type.equals(entry.getValue()))
                return entry.getKey();
        return null;
    }
}
