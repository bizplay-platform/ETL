package bizplay.etl;

import java.util.Properties;

public class Config{

	private static Config     config     = null;
	private static Properties properties = null;

    public static synchronized Config getInstance(){
        if( config == null ){
        	config = new Config();
        }
        return config ;
    }
	
	public void setConfig(Properties p) {
		properties = p;
	}

	public Properties getConfig() {
		return properties;
	}
}
