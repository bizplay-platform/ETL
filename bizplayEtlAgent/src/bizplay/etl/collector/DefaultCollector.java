package bizplay.etl.collector;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import bizplay.etl.collector.impl.Collector;
import bizplay.etl.manager.DatabaseManager;
import bizplay.etl.util.StringUtil;

public class DefaultCollector implements Collector{

	@Override
	public void transformationAndLoading(Connection con , String query , List<Map<String,String>> extractionData){
		
		/* = -------------------------------------------------------------------------- = */
		/* =   초기변수 설정	   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */
		DatabaseManager dm            = new DatabaseManager();
		List<String>    columnMap     = null;
		String          []strArrParam = null;
		int				columnIdx     = 0;
		boolean			isSqlError    = false;
		
		/* = -------------------------------------------------------------------------- = */
		/* =   SQL 파싱		   	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */
		query       = dm.replaceAnnotation(query);
		columnMap   = dm.getParam         (query);
		query       = dm.queryCompile     (query);
		strArrParam = new String[StringUtil.inValue(query , "?")];

		/* = -------------------------------------------------------------------------- = */
		/* =   Transaction 시작 	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */		
		dm.beginTransaction(con);
		
		/* = -------------------------------------------------------------------------- = */
		/* =   데이터 적재 		                                   			  			= */
		/* = -------------------------------------------------------------------------- = */			
		try {
			for(Map<String,String> item : extractionData){
				columnIdx = 0;
				for(String column : columnMap){
					strArrParam[columnIdx] = item.get(column);
					columnIdx++;
				}
				dm.executeUpdate(con, query, strArrParam);
			}
		} catch (SQLException e) {
			isSqlError = true;
			e.printStackTrace();
		}

		/* = -------------------------------------------------------------------------- = */
		/* =   SQL에러 처리 		                                   			  			= */
		/* = -------------------------------------------------------------------------- = */		
		if(isSqlError)
			dm.rollback(con);
		else
			dm.commit  (con);	
		
		/* = -------------------------------------------------------------------------- = */
		/* =   Transaction 종료 	                                   			  			= */
		/* = -------------------------------------------------------------------------- = */		
		dm.endTransaction(con);
		
	}

}
