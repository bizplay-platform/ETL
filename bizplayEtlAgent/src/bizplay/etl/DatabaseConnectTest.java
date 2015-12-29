package bizplay.etl;

import java.io.File;
import java.util.Calendar;
import java.util.Timer;

import bizplay.etl.com.etlLogManager;
import bizplay.etl.database.DatabaseConnect;
import bizplay.etl.exception.Code;
import bizplay.etl.exception.EtlException;
import bizplay.etl.manager.CollectorManager;

public class DatabaseConnectTest {
	public static void main(String[] args) throws EtlException{
		
		/* = -------------------------------------------------------------------------- = */
		/* =   초기변수 설정	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */
		DatabaseConnect connect     = DatabaseConnect.getInstance();
		Calendar   		dateTime    = Calendar.getInstance();
		Timer      		timer       = new Timer();
		File       		config      = null;
		long			cStartTime	= 0L;
		long			cEndTime   	= 0L;		
		
		/* = -------------------------------------------------------------------------- = */
		/* =   System log 설정 변경	   	                                   			  	= */
		/* = -------------------------------------------------------------------------- = */
		System.setOut(etlLogManager.createLoggingSystem(System.out));
        System.setErr(etlLogManager.createLoggingError (System.out));
        
		/* = -------------------------------------------------------------------------- = */
		/* =   설정 파일 Read	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */

		etlLogManager.etlLog("INFO" ,System.getProperties().getProperty("ETL_HOME")+""+System.getProperties().getProperty("ETL_CONFIG"     )+" etl.config.xml을 찾습니다.");
		
		config = new File(System.getProperties().getProperty("ETL_HOME")+System.getProperties().getProperty("ETL_CONFIG"));
		
		if( !config.exists    () )
			throw new EtlException(Code.ETL0001);
		
		Config.getInstance().loadConfig(config , null);
		
		/* = -------------------------------------------------------------------------- = */
		/* =   DB컨넥션 생성   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */
		etlLogManager.etlLog("INFO" ,"Database 접속을 시작 합니다.");
		cStartTime = System.currentTimeMillis();	
		connect.init();
		cEndTime   = System.currentTimeMillis();
		etlLogManager.etlLog("INFO" ,"Database 접속이 완료 되었습니다.접속에 걸린 시간"+Long.toString(cEndTime-cStartTime)+"ms");
		etlLogManager.etlLog("INFO" ,"Database Connect 반납");
		connect.destroy();	
	}
}
