package bizplay.etl.exception;

public class EtlException extends Exception{

	private static final long serialVersionUID = 1L;
	
	private Code Code;
	
	public EtlException(String message) {
		super(message);
	}	
	
	public EtlException(Code code, Throwable rootCause) {
		super(code.getMessage() , rootCause);
	}
	
	public void setCode(Code code){
		this.Code = code;
	}
	
	public Code getCode(){
		return this.Code;
	}		
}
