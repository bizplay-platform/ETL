package bizplay.etl;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import bizplay.etl.cmo.CollectorCmo;
import bizplay.etl.cmo.DwDbCmo;
import bizplay.etl.cmo.TargetDbCmo;

public class Config{

	private static Config             config = null;
	private static List<TargetDbCmo>  tdb    = null;
	private static DwDbCmo            ddb    = null;
	private static List<CollectorCmo> col    = null;
	private static String             cst    = null;

	static{
		tdb    = new ArrayList<TargetDbCmo>();         
		ddb    = new DwDbCmo();
		col    = new ArrayList<CollectorCmo>();
	}
	
    public static synchronized Config getInstance(){
        if( config == null ){
        	config = new Config();
        }
        return config ;
    }
	
    /**
     * 타겟 DB정보를 리턴 합니다.
     * @param target
     * @return
     */
    public TargetDbCmo getTargetDbCmo(String target) {
    	TargetDbCmo cmo = null;
    	for(Object item : tdb){
    		if(target.equals(((TargetDbCmo)item).getName())){
    			cmo = (TargetDbCmo)item;
    		}
    	}
    	return cmo;
    }
    
    /**
     * ETL DB정보를 리턴 합니다.
     * @return
     */
    public DwDbCmo getEtlDbCmo() {
    	return ddb;
    }    
    
    /**
     * Collector Object를 리턴 합니다.
     * @param target
     * @return
     */
    public List<CollectorCmo> getCollectorCmo(String target) {
    	return col;
    }
    
    /**
     * 수집시간을 리턴 합니다.
     * @return
     */
    public String getCollectTime(){
    	return cst;
    }
    
    /**
     * config정보를 메모리에 로드 합니다.
     * @param config
     */
	public void loadConfig(JSONObject config) {
		JSONObject dbInfo    = (JSONObject)config.get("DB_INFO"  );
		JSONArray  targetDb  = (JSONArray )dbInfo.get("TARGET_DB");
		JSONObject dwDb      = (JSONObject)dbInfo.get("DW_DB"    );
		JSONArray  collector = (JSONArray )config.get("COLLECTOR");
		
		/* = -------------------------------------------------------------------------- = */
		/* =   타겟DB 정보 로드  	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */			
		 for(Object item : targetDb){
			 TargetDbCmo temp = new TargetDbCmo();
			 temp.setName    ((String)((JSONObject)item).get("NAME"));
			 temp.setDriver  ((String)((JSONObject)((JSONObject)item).get("RESOURCE_INFO")).get("DIRVER"  ));
			 temp.setUrl     ((String)((JSONObject)((JSONObject)item).get("RESOURCE_INFO")).get("URL"     ));
			 temp.setUser    ((String)((JSONObject)((JSONObject)item).get("RESOURCE_INFO")).get("USER"    ));
			 temp.setPassword((String)((JSONObject)((JSONObject)item).get("RESOURCE_INFO")).get("PASSWORD"));
			 tdb.add(temp);
		 }
		 
		 /* = -------------------------------------------------------------------------- = */
		 /* =   DW DB 정보 로드															 = */
		 /* = -------------------------------------------------------------------------- = */		 
		 ddb.setDriver  ((String)((JSONObject)dwDb).get("DIRVER"  ));
		 ddb.setUrl     ((String)((JSONObject)dwDb).get("URL"     ));
		 ddb.setUser    ((String)((JSONObject)dwDb).get("USER"    ));
		 ddb.setPassword((String)((JSONObject)dwDb).get("PASSWORD"));

		 /* = -------------------------------------------------------------------------- = */
		 /* =   수집시간 로드																 = */
		 /* = -------------------------------------------------------------------------- = */		 
		 cst = (String)config.get("COLLECT_START_TIME");
		 
		 /* = -------------------------------------------------------------------------- = */
		 /* =   Collector 정보 로드														 = */
		 /* = -------------------------------------------------------------------------- = */			 
		 for(Object item : collector){
			 CollectorCmo temp = new CollectorCmo();
			 temp.setTarget  ((String)((JSONObject)item).get("TARGET"   ));
			 temp.setCls     ((String)((JSONObject)item).get("CLASS"    ));
			 temp.setReadSql ((String)((JSONObject)item).get("READ_SQL" ));
			 temp.setWriteSql((String)((JSONObject)item).get("WRITE_SQL"));
			 col.add(temp);
		 }
	}
}
