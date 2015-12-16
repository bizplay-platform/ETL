package bizplay.etl.collector;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import bizplay.etl.collector.impl.Collector;

public class DefaultCollector implements Collector{


	@Override
	public void transformationAndLoading(Connection con , String query , List<Map<String,String>> extractionData) {
		안녕~!
	}

} 
