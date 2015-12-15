package bizplay.etl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import bizplay.etl.exception.Code;
import bizplay.etl.exception.EtlException;

public class Run {
	public static void main(String[] args) throws EtlException{
		
		/* = -------------------------------------------------------------------------- = */
		/* =   초기변수 설정	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */		
		JSONParser parser = new JSONParser(); 
		JSONObject config = null;
		
		
		/* = -------------------------------------------------------------------------- = */
		/* =   설정 파일 Read	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */
		try {
			config  = (JSONObject)parser.parse(new FileReader(System.getProperties().getProperty("ETL_HOME")+"\\conf\\etl.config.json"));
			System.out.println(config.toJSONString());
			
		}catch (FileNotFoundException e){
			throw new EtlException(Code.ETL9999 , e);
		}catch (IOException e){
			throw new EtlException(Code.ETL9999 , e);
		}catch (ParseException e) {
			throw new EtlException(Code.ETL9999 , e);
		}
	}
}
