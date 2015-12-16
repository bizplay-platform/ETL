package bizplay.etl.cmo;

public class TargetDbCmo {
    private String   name		= null;
    private String   driver  	= null;
    private String   url		= null;
    private String   user		= null;
    private String   password  = null;
    
	public String getName() {
		return name;
	}
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
	public void setName(String name) {
		this.name = name;
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
