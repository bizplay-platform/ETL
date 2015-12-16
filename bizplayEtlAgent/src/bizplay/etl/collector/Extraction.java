package bizplay.etl.collector;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import bizplay.etl.manager.DatabaseManager;
import bizplay.etl.util.DateUtil;
import bizplay.etl.util.StringUtil;

public class Extraction {
	
	public static List<Map<String,String>> execute(Connection con , String query){
		String                   []strArrParam = new String[StringUtil.inValue(query , "?")];
		DatabaseManager          dm            = new DatabaseManager();
		List<Map<String,String>> result        = null;
		
		if(strArrParam.length > 0){
			for(int i = 0; i < strArrParam.length; i++){
				strArrParam[i] = DateUtil.getAddDate("YYYYMMdd", -1);
			}
		}
		
		try {
			result = dm.executeQuery(con, query, strArrParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
