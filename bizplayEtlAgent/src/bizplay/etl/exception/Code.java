package bizplay.etl.exception;

public enum Code {
	ETL0000("정상 처리 되었습니다."							 ),
	ETL0001("etl.config.xml 파일을 찾지 못하였습니다."     	 ),
	ETL0002("etl.query.store.xml 파일을 찾지 못하였습니다."	 ),
	ETL0003("etl.config.xml 로드 중 오류가 발생하였습니다."	 ),
	ETL0004("etl.query.store.xml 로드 중 오류가 발생하였습니다."),
	ETL9999("처리중 오류가 발생 하였습니다."  );
	
	private String name;
	
	private Code(String name) {
		this.name = name;
	}
	
	public String getMessage(){
		return this.name;
	}
}
