package bizplay.etl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bizplay.etl.cmo.CollectorCmo;
import bizplay.etl.cmo.DwDbCmo;
import bizplay.etl.cmo.TargetDbCmo;
import bizplay.etl.com.etlLogManager;
import bizplay.etl.exception.Code;
import bizplay.etl.exception.EtlException;

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
    		if(target.equals(((TargetDbCmo)item).getAlias())){
    			cmo = (TargetDbCmo)item;
    		}
    	}
    	return cmo;
    }
    
    /**
     * 타겟 DB정보를 리턴 합니다.
     * @param target
     * @return
     */
    public List<TargetDbCmo> getTargetDbCmo() {
    	return tdb;
    }    
    
    /**
     * ETL DB정보를 리턴 합니다.
     * @return
     */
    public DwDbCmo getDwDbCmo() {
    	return ddb;
    }    
    
    /**
     * Collector Object를 리턴 합니다.
     * @param target
     * @return
     */
    public List<CollectorCmo> getCollectorCmo() {
    	return col;
    }
    
    /**
     * 수집시간을 리턴 합니다.
     * @return
     */
    public String getCollectTime(){
    	return cst;
    }
    
    public void loadConfig(File config , File querySrote) throws EtlException{
    	
    	DocumentBuilderFactory factory = null;
    	DocumentBuilder        builder = null;
    	Document               doc     = null;
    	
    	
    	etlLogManager.etlLog("INFO" ,"etl.config.xml 파일을 로드 합니다.");
		try{
	        factory = DocumentBuilderFactory.newInstance();
	        builder = factory.newDocumentBuilder();
	        doc     = builder.parse(config);
	        doc.getDocumentElement().normalize();
	        
			/* = -------------------------------------------------------------------------- = */
			/* =   XML 엘리먼트 find   	                                   			  		= */
			/* = -------------------------------------------------------------------------- = */
            NodeList agentNodeList           = doc.getElementsByTagName("agent");
            Node     agentInfo               = agentNodeList.item(0);
            Element  agentInfoElement        = (Element) agentInfo;
            /* = -------------------------------------------------------------------------- = */
            NodeList targetDBNodeList        = doc.getElementsByTagName("extractionTargetDB");
            Node     targetDBInfo            = targetDBNodeList.item(0);
            Element  targetDBInfoElement     = (Element) targetDBInfo;
            /* = -------------------------------------------------------------------------- = */
            NodeList dwDBNodeList            = doc.getElementsByTagName("dataWareHouseDB");
            Node     dwDBInfo                = dwDBNodeList.item(0);
            Element  dwDBInfoElement         = (Element) dwDBInfo;
            /* = -------------------------------------------------------------------------- = */
            
            /* = -------------------------------------------------------------------------- = */
            /* =   수집시간 로드																= */
            /* = -------------------------------------------------------------------------- = */		 
            cst = ((Element)((NodeList)agentInfoElement.getElementsByTagName("startTime")).item(0)).getTextContent();
	        
    		/* = -------------------------------------------------------------------------- = */
    		/* =   타겟DB 정보 로드  	                                   			  			= */
    		/* = -------------------------------------------------------------------------- = */            
            NodeList targetInfoList = targetDBInfoElement.getElementsByTagName("info");
            for(int m = 0; m < targetInfoList.getLength(); m++){
            	TargetDbCmo temp = new TargetDbCmo();
            	temp.setAlias   (((Element)((NodeList)((Element)targetInfoList.item(m)).getElementsByTagName("alias"   )).item(0)).getTextContent());
            	temp.setDriver  (((Element)((NodeList)((Element)targetInfoList.item(m)).getElementsByTagName("driver"  )).item(0)).getTextContent());
            	temp.setUrl     (((Element)((NodeList)((Element)targetInfoList.item(m)).getElementsByTagName("url"     )).item(0)).getTextContent());
            	temp.setUser    (((Element)((NodeList)((Element)targetInfoList.item(m)).getElementsByTagName("user"    )).item(0)).getTextContent());
            	temp.setPassword(((Element)((NodeList)((Element)targetInfoList.item(m)).getElementsByTagName("password")).item(0)).getTextContent());
            	tdb.add(temp);            	
            }
            
            /* = -------------------------------------------------------------------------- = */
            /* =   DW DB 정보 로드															 = */
            /* = -------------------------------------------------------------------------- = */		 
            ddb.setDriver  (((Element)((NodeList)dwDBInfoElement.getElementsByTagName("driver"  )).item(0)).getTextContent());
            ddb.setUrl     (((Element)((NodeList)dwDBInfoElement.getElementsByTagName("url"     )).item(0)).getTextContent());
            ddb.setUser    (((Element)((NodeList)dwDBInfoElement.getElementsByTagName("user"    )).item(0)).getTextContent());
            ddb.setPassword(((Element)((NodeList)dwDBInfoElement.getElementsByTagName("password")).item(0)).getTextContent());            
            
        }catch (ParserConfigurationException e) {
        	throw new EtlException(Code.ETL0003 , e);
		}		
        catch (SAXException e){
        	throw new EtlException(Code.ETL0003 , e);
        }
        catch (IOException e){
        	throw new EtlException(Code.ETL0003 , e);
        }

		if(querySrote != null){
			etlLogManager.etlLog("INFO" ,"etl.query.store.xml 파일을 로드 합니다.");
			try{
		        factory = DocumentBuilderFactory.newInstance();
		        builder = factory.newDocumentBuilder();
		        doc     = builder.parse(querySrote);
		        doc.getDocumentElement().normalize();
		        
	           
	    		/* = -------------------------------------------------------------------------- = */
	    		/* =   타겟DB 정보 로드  	                                   			  			= */
	    		/* = -------------------------------------------------------------------------- = */            
	            NodeList queryList = doc.getElementsByTagName("query");
	            for(int m = 0; m < queryList.getLength(); m++){
	            	//etlLogManager.etlLog("INFO" ,((Element)((NodeList)((Element)queryList.item(m)).getElementsByTagName("extractionQuery"    )).item(0)).getTextContent());
	            	CollectorCmo temp = new CollectorCmo();
	   			 	temp.setName      (((Element)((NodeList)((Element)queryList.item(m)).getElementsByTagName("name"                      )).item(0)).getTextContent());
	   			 	temp.seteTarget   (((Element)((NodeList)((Element)queryList.item(m)).getElementsByTagName("extractionTargetDB"        )).item(0)).getTextContent());
	   			 	temp.seteQuery    (((Element)((NodeList)((Element)queryList.item(m)).getElementsByTagName("extractionQuery"           )).item(0)).getTextContent());
	   			 	temp.setTlClass   (((Element)((NodeList)((Element)queryList.item(m)).getElementsByTagName("transformationLoadingClass")).item(0)).getTextContent());
	   			 	temp.setTlQuery   (((Element)((NodeList)((Element)queryList.item(m)).getElementsByTagName("transformationLoadingQuery")).item(0)).getTextContent());
	   			 	col.add(temp);            	
	            }            
	        }catch (ParserConfigurationException e) {
	        	throw new EtlException(Code.ETL0004 , e);
			}		
	        catch (SAXException e){
	        	throw new EtlException(Code.ETL0004 , e);
	        }
	        catch (IOException e){
	        	throw new EtlException(Code.ETL0004 , e);
	        }			
		}
    }
    
    public void print(){
    	etlLogManager.etlLog("INFO" ,"etl.query.store.xml");
    	etlLogManager.etlLog("INFO" ,"Agent 구동 시간 : "+getCollectTime());
    	for(TargetDbCmo item : getTargetDbCmo()){
    		etlLogManager.etlLog("INFO" ,"extractionTargetDB "+item.getAlias()+" driver   :"+item.getDriver  ());
    		etlLogManager.etlLog("INFO" ,"extractionTargetDB "+item.getAlias()+" url      :"+item.getUrl     ());
    		etlLogManager.etlLog("INFO" ,"extractionTargetDB "+item.getAlias()+" user     :"+item.getUser    ());
    		etlLogManager.etlLog("INFO" ,"extractionTargetDB "+item.getAlias()+" password :"+item.getPassword());
    	}
    	etlLogManager.etlLog("INFO" ,"dataWareHouseDB driver   :"+getDwDbCmo().getDriver  ());
    	etlLogManager.etlLog("INFO" ,"dataWareHouseDB url      :"+getDwDbCmo().getUrl     ());
    	etlLogManager.etlLog("INFO" ,"dataWareHouseDB user     :"+getDwDbCmo().getUser    ());
    	etlLogManager.etlLog("INFO" ,"dataWareHouseDB password :"+getDwDbCmo().getPassword());
    	etlLogManager.etlLog("INFO" ,"===============================================================");
    	etlLogManager.etlLog("INFO" ,"etl.query.store.xml");
    	for(CollectorCmo item : getCollectorCmo()){
    		etlLogManager.etlLog("INFO" ,item.getName()+" extractionTargetDB         :"+item.geteTarget());
    		etlLogManager.etlLog("INFO" ,item.getName()+" extractionQuery            :"+item.geteQuery ());
    		etlLogManager.etlLog("INFO" ,item.getName()+" transformationLoadingClass :"+item.getTlClass());
    		etlLogManager.etlLog("INFO" ,item.getName()+" transformationLoadingQuery :"+item.getTlQuery());
    	}    	
    }
    
    /**
     * config정보를 메모리에 로드 합니다.
     * @param config
     */
//	public void loadConfig(JSONObject config) {
//		JSONObject dbInfo    = (JSONObject)config.get("DB_INFO"  );
//		JSONArray  targetDb  = (JSONArray )dbInfo.get("TARGET_DB");
//		JSONObject dwDb      = (JSONObject)dbInfo.get("DW_DB"    );
//		JSONArray  collector = (JSONArray )config.get("COLLECTOR");
//		
//		/* = -------------------------------------------------------------------------- = */
//		/* =   타겟DB 정보 로드  	                                   			  			= */
//		/* = -------------------------------------------------------------------------- = */			
//		 for(Object item : targetDb){
//			 TargetDbCmo temp = new TargetDbCmo();
//			 temp.setName    ((String)((JSONObject)item).get("NAME"));
//			 temp.setDriver  ((String)((JSONObject)((JSONObject)item).get("RESOURCE_INFO")).get("DIRVER"  ));
//			 temp.setUrl     ((String)((JSONObject)((JSONObject)item).get("RESOURCE_INFO")).get("URL"     ));
//			 temp.setUser    ((String)((JSONObject)((JSONObject)item).get("RESOURCE_INFO")).get("USER"    ));
//			 temp.setPassword((String)((JSONObject)((JSONObject)item).get("RESOURCE_INFO")).get("PASSWORD"));
//			 tdb.add(temp);
//		 }
//		 
//		 /* = -------------------------------------------------------------------------- = */
//		 /* =   DW DB 정보 로드															 = */
//		 /* = -------------------------------------------------------------------------- = */		 
//		 ddb.setDriver  ((String)((JSONObject)dwDb).get("DIRVER"  ));
//		 ddb.setUrl     ((String)((JSONObject)dwDb).get("URL"     ));
//		 ddb.setUser    ((String)((JSONObject)dwDb).get("USER"    ));
//		 ddb.setPassword((String)((JSONObject)dwDb).get("PASSWORD"));
//
//		 /* = -------------------------------------------------------------------------- = */
//		 /* =   수집시간 로드																 = */
//		 /* = -------------------------------------------------------------------------- = */		 
//		 cst = (String)config.get("COLLECT_START_TIME");
//		 
//		 /* = -------------------------------------------------------------------------- = */
//		 /* =   Collector 정보 로드														 = */
//		 /* = -------------------------------------------------------------------------- = */			 
//		 for(Object item : collector){
//			 CollectorCmo temp = new CollectorCmo();
//			 temp.setName      ((String)((JSONObject)item).get("NAME"));
//			 temp.seteTarget   ((String)((JSONObject)((JSONObject)item).get("EXTRACTION")).get("TARGET"));
//			 temp.seteQuery    ((String)((JSONObject)((JSONObject)item).get("EXTRACTION")).get("QUERY" ));
//			 temp.setTlClass   ((String)((JSONObject)((JSONObject)item).get("TRANSFORMATION_LOADING")).get("CLASS" ));
//			 temp.setTlQuery   ((String)((JSONObject)((JSONObject)item).get("TRANSFORMATION_LOADING")).get("QUERY" ));
//			 col.add(temp);
//		 }
//	}
}
