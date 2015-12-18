package bizplay.etl.collector;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import bizplay.etl.manager.DatabaseManager;
import bizplay.etl.util.DateUtil;
import bizplay.etl.util.StringUtil;

public class Extraction {
	
	public static List<Map<String,String>> execute(Connection con , String query){
		
		/* = -------------------------------------------------------------------------- = */
		/* =   초기변수 설정	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */		
		DatabaseManager          dm            = new DatabaseManager();
		List<Map<String,String>> result        = null;
		String                   []strArrParam = null;
		
		/* = -------------------------------------------------------------------------- = */
		/* =   SQL 파싱		   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */				
		query       = dm.queryCompile(query);
		strArrParam = new String[StringUtil.inValue(query , "?")];
		if(strArrParam.length > 0){
			for(int i = 0; i < strArrParam.length; i++){
				strArrParam[i] = DateUtil.getAddDate("YYYYMMdd", -1);
			}
		}

		/* = -------------------------------------------------------------------------- = */
		/* =   수집대상 데이터를 추출 합니다.                          			  			= */
		/* = -------------------------------------------------------------------------- = */		
		try {
			result = dm.executeQuery(con, query, strArrParam);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
