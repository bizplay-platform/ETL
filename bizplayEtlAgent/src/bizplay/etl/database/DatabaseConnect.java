package bizplay.etl.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bizplay.etl.Config;
import bizplay.etl.cmo.DwDbCmo;
import bizplay.etl.cmo.TargetDbCmo;
import bizplay.etl.com.etlLogManager;

public class DatabaseConnect {
	private static DatabaseConnect    instance    = null;
	private List    <ConnectionObject> connections = null;
    public static synchronized DatabaseConnect getInstance(){
        if( instance == null ){
        	instance = new DatabaseConnect();
        }
        return instance ;
    }
    
    /**
     * 접속 대상 Database에 connection을 생성 합니다. 
     */
    public void init(){
    	List<TargetDbCmo> tdb         = Config.getInstance().getTargetDbCmo();
    	DwDbCmo           ddb         = Config.getInstance().getDwDbCmo();
    	Connection        c           = null;
    	ConnectionObject  co          = null;
    	
    	destroy();
    	
    	connections =  new ArrayList<ConnectionObject>();	
    	
    	for(TargetDbCmo item : tdb){
	    	try {
	    		Class.forName(item.getDriver());
	    		c = DriverManager.getConnection(item.getUrl(),item.getUser(),item.getPassword());
	    		co = new ConnectionObject(c , item.getAlias());
	    		connections.add(co);
	   		} catch (Exception e) {
	   			e.printStackTrace();
	   		}    		
    	}
    	
    	try {
			Class.forName(ddb.getDriver());
			c = DriverManager.getConnection(ddb.getUrl(),ddb.getUser(),ddb.getPassword());
			co = new ConnectionObject(c , "DW");
			connections.add(co);    	
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
    }
    
    /**
     * connections을 소멸 시킵니다.
     */
    public void destroy(){
    	if(connections != null){
    		for(ConnectionObject item : connections){
    			freeConnection(item.connection);
    			item = null;
    		}
    		connections = null;
    	}
    }
    
    /**
     * Connection을 취득 합니다.
     * @param databaseName
     * @return Connection
     */
	public Connection getConnection(String databaseName) {
		Connection c = null;
    	if(connections != null){
    		for(ConnectionObject item : connections){
    			if( item.name.equals(databaseName) ){
    				c = item.connection;
    			}
    		}
    	}
    	if(c != null){
    		try {
				c.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    	return c;
	}
	
	/**
	 * Connection을 반납 합니다.
	 * @param Connection
	 */
	public void freeConnection(Connection con) {
		try {
			if (con != null) 
			{
				if (!con.getAutoCommit())
				{
					con.setAutoCommit(true);
				}
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
}
