package bizplay.etl.manager;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import bizplay.etl.Config;
import bizplay.etl.cmo.CollectorCmo;
import bizplay.etl.collector.Extraction;
import bizplay.etl.collector.impl.Collector;
import bizplay.etl.com.etlLogManager;
import bizplay.etl.database.DatabaseConnect;

public class CollectorManager extends TimerTask{

	@Override
	public void run() {
		
		/* = -------------------------------------------------------------------------- = */
		/* =   초기변수 설정	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */		
		DatabaseConnect          connect    = DatabaseConnect.getInstance();
		List<CollectorCmo>       col        = Config.getInstance().getCollectorCmo();
		Class<?>                 cls        = null;
		Collector                collector  = null; 
		List<Map<String,String>> result     = null;
		long                    cStartTime	= 0L;
		long                    cEndTime   	= 0L;		
		long                    eStartTime	= 0L;
		long                    eEndTime   	= 0L;
		long                    tlStartTime	= 0L;
		long                    tlEndTime  	= 0L;
		
		/* = -------------------------------------------------------------------------- = */
		/* =   DB컨넥션 생성   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */
		etlLogManager.etlLog("INFO" ,"Database 접속을 시작 합니다.");
		cStartTime = System.currentTimeMillis();	
		connect.init();
		cEndTime   = System.currentTimeMillis();
		etlLogManager.etlLog("INFO" ,"Database 접속이 완료 되었습니다.접속에 걸린 시간"+Long.toString(cEndTime-cStartTime)+"ms");
		
		/* = -------------------------------------------------------------------------- = */
		/* =   컬렉터 실행	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */		
		try{
			for(CollectorCmo item : col){
				
				etlLogManager.etlLog("INFO" ,item.getName()+"(을) 시작 합니다.....................................");
				
				//추출
				eStartTime = System.currentTimeMillis();				
				result     = Extraction.execute(connect.getConnection(item.geteTarget()) , item.geteQuery ());
				eEndTime   = System.currentTimeMillis();
				etlLogManager.etlLog("INFO" ,"추출에 걸린 시간은 : "+Long.toString(eEndTime-eStartTime)+"ms 이며, "+result.size()+"행이 추출 되었습니다.");
				
				//변환/적재
				if(result.size() > 0){
					tlStartTime = System.currentTimeMillis();
					cls         = Class.forName(item.getTlClass());
					collector   = (Collector)cls.newInstance();
					collector.transformationAndLoading(connect.getConnection("DW"), item.getTlQuery(), result);
					tlEndTime   = System.currentTimeMillis();
					etlLogManager.etlLog("INFO" ,"변환/적재에 걸린 시간은 "+Long.toString(tlEndTime-tlStartTime)+"ms 입니다.");					
				}else{
					
				}
				etlLogManager.etlLog("INFO" ,item.getName()+"(을) 종료 합니다.....................................");
			}
		}catch(Exception e){
		}finally{
			connect.destroy();	
		}
	}

}