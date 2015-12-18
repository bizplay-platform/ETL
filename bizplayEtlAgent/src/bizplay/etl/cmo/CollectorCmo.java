package bizplay.etl.cmo;

public class CollectorCmo {
	private String   name		= null;
    private String   eTarget	= null;
    private String   eQuery    	= null;
    private String   tlClass	= null;
    private String   tlQuery	= null;
    
	public String getName() {
		return name;
	}
	public String geteTarget() {
		return eTarget;
	}
	public String geteQuery() {
		return eQuery;
	}
	public String getTlClass() {
		return tlClass;
	}
	public String getTlQuery() {
		return tlQuery;
	}
	public void setName(String name) {
		this.name = name;
	}	
	public void seteTarget(String eTarget) {
		this.eTarget = eTarget;
	}
	public void seteQuery(String eQuery) {
		this.eQuery = eQuery;
	}
	public void setTlClass(String tlClass) {
		this.tlClass = tlClass;
	}
	public void setTlQuery(String tlQuery) {
		this.tlQuery = tlQuery;
	}
    

}
