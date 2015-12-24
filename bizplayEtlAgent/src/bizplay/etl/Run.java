package bizplay.etl;

import java.io.File;
import java.util.Calendar;
import java.util.Timer;

import bizplay.etl.com.etlLogManager;
import bizplay.etl.exception.Code;
import bizplay.etl.exception.EtlException;
import bizplay.etl.manager.CollectorManager;
import bizplay.etl.manager.TimerTaskManager;

public class Run {
	public static void main(String[] args) throws EtlException{
		
		/* = -------------------------------------------------------------------------- = */
		/* =   초기변수 설정	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */		
		Calendar   dateTime   = Calendar.getInstance();
		Timer      timer      = new Timer();
		File       config     = null;
		File       queryStore = null;
		
		
		/* = -------------------------------------------------------------------------- = */
		/* =   System log 설정 변경	   	                                   			  	= */
		/* = -------------------------------------------------------------------------- = */
		System.setOut(etlLogManager.createLoggingSystem(System.out));
        System.setErr(etlLogManager.createLoggingError (System.out));
        
		/* = -------------------------------------------------------------------------- = */
		/* =   설정 파일 Read	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */

		etlLogManager.etlLog("INFO" ,System.getProperties().getProperty("ETL_HOME")+"/conf 디렉터리 에서 etl.config.xml을 찾습니다.");
		etlLogManager.etlLog("INFO" ,System.getProperties().getProperty("ETL_HOME")+"/conf 디렉터리 에서 etl.query.store.xml을 찾습니다.");
		
		config     = new File(System.getProperties().getProperty("ETL_HOME")+"\\conf\\etl.config.xml");
		queryStore = new File(System.getProperties().getProperty("ETL_HOME")+"\\conf\\etl.query.store.xml");
		
		if( !config.exists    () )
			throw new EtlException(Code.ETL0001);
		
		if( !queryStore.exists() )
			throw new EtlException(Code.ETL0002);
		
		if( args.length == 0 )
			throw new EtlException(Code.ETL0005);
		
		Config.getInstance().loadConfig(config , queryStore);
		
		etlLogManager.etlLog("INFO" , "설정파일 로드가 완료 되었습니다. 설정파일을 출력 합니다.");
		etlLogManager.etlLog("INFO" , "===============================================================");
		Config.getInstance().print();
		etlLogManager.etlLog("INFO" , "===============================================================");	

		
		
		if("B".equals(args[0])){
			etlLogManager.etlLog("INFO" , "Agent가 구동 되었습니다.");
			/* = -------------------------------------------------------------------------- = */
			/* =   스케쥴 설정	   	                                   			  			= */
			/* = -------------------------------------------------------------------------- = */		
			dateTime.set(Calendar.HOUR_OF_DAY	, Integer.parseInt(Config.getInstance().getCollectTime()) );
			dateTime.set(Calendar.MINUTE		, Integer.parseInt(Config.getInstance().getCollectTime()) );
			dateTime.set(Calendar.SECOND		, Integer.parseInt(Config.getInstance().getCollectTime()) );
			dateTime.add(Calendar.DATE			, +1                                                      );
			timer.scheduleAtFixedRate(new TimerTaskManager(), dateTime.getTime(), 86400000);			
		}else if("A".equals(args[0])){
			etlLogManager.etlLog("INFO" , "CollectorManager를 실행 합니다.");
			CollectorManager cm = new CollectorManager();
			cm.run();
		}
	}
}
