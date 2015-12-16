package bizplay.etl.database;

import java.sql.Connection;

public class ConnectionObject {
    public Connection connection = null;              
    public String     name       = "";
    public ConnectionObject(Connection connection, String name) {
    	this.connection = connection;
    	this.name       = name;
    }
}
