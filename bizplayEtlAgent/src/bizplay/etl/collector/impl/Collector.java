package bizplay.etl.collector.impl;

public interface Collector {

	/**
	 * 타겟 DB 테이블에 대한 Read를 담당
	 */
	public void read();

	/**
	 * ETL DB 테이블에 대한 write를 담당 
	 */
	public void write();
	
}
