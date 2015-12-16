package bizplay.etl.manager;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import bizplay.etl.Config;
import bizplay.etl.cmo.CollectorCmo;
import bizplay.etl.cmo.TargetDbCmo;
import bizplay.etl.collector.Extraction;
import bizplay.etl.database.DatabaseConnect;
import bizplay.etl.util.StringUtil;

public class CollectorManager extends TimerTask{

	@Override
	public void run() {
		
		DatabaseConnect          connect = DatabaseConnect.getInstance();
		List<CollectorCmo>       col     = Config.getInstance().getCollectorCmo();

		List<Map<String,String>> result  = null;
		
		
		/* = -------------------------------------------------------------------------- = */
		/* =   DB컨넥션 생성	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */
		connect.init();
		

		/* = -------------------------------------------------------------------------- = */
		/* =   컬렉터 실행	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */		
		try{
			for(CollectorCmo item : col){
				//추출
				result = Extraction.execute(connect.getConnection(item.geteTarget()) , item.geteQuery ());
				
				//변환/적재
				//connect.getConnection("DW")
			}
		}catch(Exception e){
		}finally{
			connect.destroy();	
		}
	}

}