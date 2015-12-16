package bizplay.etl.collector.impl;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface Collector {

	public void transformationAndLoading(Connection con , String query , List<Map<String,String>> extractionData);
	
}
