package bizplay.etl.cmo;

public class DwDbCmo {
    private String   driver  	= null;
    private String   url		= null;
    private String   user		= null;
    private String   password  = null;
    
	public String getDriver() {
		return driver;
	}
	public String getUrl() {
		return url;
	}
	public String getUser() {
		return user;
	}
	public String getPassword() {
		return password;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
