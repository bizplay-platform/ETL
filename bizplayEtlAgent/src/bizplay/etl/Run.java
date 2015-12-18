package bizplay.etl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import bizplay.etl.exception.Code;
import bizplay.etl.exception.EtlException;
import bizplay.etl.manager.CollectorManager;

public class Run {
	public static void main(String[] args) throws EtlException{
		
		/* = -------------------------------------------------------------------------- = */
		/* =   초기변수 설정	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */		
		JSONParser parser     = new JSONParser(); 
		//JSONObject config   = null;
		Calendar   dateTime   = Calendar.getInstance();
		Timer      timer      = new Timer();
		File       config     = null;
		File       queryStore = null;
		
		/* = -------------------------------------------------------------------------- = */
		/* =   설정 파일 Read	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */

		System.out.println(System.getProperties().getProperty("ETL_HOME")+"/conf 디렉터리 에서 etl.config.xml을 찾습니다.");
		System.out.println(System.getProperties().getProperty("ETL_HOME")+"/conf 디렉터리 에서 etl.query.store.xml을 찾습니다.");
		
		config     = new File(System.getProperties().getProperty("ETL_HOME")+"\\conf\\etl.config.xml");
		queryStore = new File(System.getProperties().getProperty("ETL_HOME")+"\\conf\\etl.query.store.xml");
		
		if( !config.exists    () )
			throw new EtlException(Code.ETL0001);
		
		if( !queryStore.exists() )
			throw new EtlException(Code.ETL0002);
		
		Config.getInstance().loadConfig(config , queryStore);
		
		System.out.println("설정파일 로드가 완료 되었습니다. 설정파일을 출력 합니다.");
		System.out.println("===============================================================");
		Config.getInstance().print();
		System.out.println("===============================================================");	
		System.out.println("Agent구동이 완료 되었습니다.");	

		
		
		/* = -------------------------------------------------------------------------- = */
		/* =   스케쥴 설정	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */		
		dateTime.set(Calendar.HOUR_OF_DAY	, Integer.parseInt(Config.getInstance().getCollectTime()) );
		dateTime.set(Calendar.MINUTE		, Integer.parseInt(Config.getInstance().getCollectTime()) );
		dateTime.set(Calendar.SECOND		, Integer.parseInt(Config.getInstance().getCollectTime()) );
		dateTime.add(Calendar.DATE			, +1                                                      );
		//timer.scheduleAtFixedRate(new CollectorManager(), dateTime.getTime(), 86400000);
		new CollectorManager().run();
	}
}
