package bizplay.etl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;

import org.json.simple.JSONObject;
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
		JSONParser parser   = new JSONParser(); 
		JSONObject config   = null;
		Calendar   dateTime = Calendar.getInstance();
		Timer      timer    = new Timer();
		
		/* = -------------------------------------------------------------------------- = */
		/* =   설정 파일 Read	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */
		try {
			config  = (JSONObject)parser.parse(new FileReader(System.getProperties().getProperty("ETL_HOME")+"\\conf\\etl.config.json"));
			Config.getInstance().loadConfig(config);
		}catch (FileNotFoundException e){
			throw new EtlException(Code.ETL9999 , e);
		}catch (IOException e){
			throw new EtlException(Code.ETL9999 , e);
		}catch (ParseException e) {
			throw new EtlException(Code.ETL9999 , e);
		}
		
		
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
