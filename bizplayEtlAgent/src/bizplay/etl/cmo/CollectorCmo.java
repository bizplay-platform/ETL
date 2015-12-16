package bizplay.etl.cmo;

public class CollectorCmo {
    private String   target		= null;
    private String   cls     	= null;
    private String   readSql	= null;
    private String   writeSql	= null;
    
	public String getTarget() {
		return target;
	}
	public String getCls() {
		return cls;
	}
	public String getReadSql() {
		return readSql;
	}
	public String getWriteSql() {
		return writeSql;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public void setCls(String cls) {
		this.cls = cls;
	}
	public void setReadSql(String readSql) {
		this.readSql = readSql;
	}
	public void setWriteSql(String writeSql) {
		this.writeSql = writeSql;
	}

}
