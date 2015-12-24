package bizplay.etl.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class etlLogManager {
	/*
	static final Logger LOGGER = LoggerFactory.getLogger("access");
	
	
	public static void main(String args[]){
		
		System.out.println(System.getProperties().getProperty("logback.configurationFile"));
	    
		LOGGER.trace("trace");
		LOGGER.debug("debug");
		LOGGER.info("info");
		LOGGER.warn("warn");
		LOGGER.error("error");
		LOGGER.info("#### Success Save User : UserName is  {}, Email is {} " ,"aaa", "bbb");
		
		LOGGER.info("access", "1211111111111111111111");

	}
	*/
	public static void etlLog(String logLevel, String errMsg){

		Logger LOGGER = LoggerFactory.getLogger("");

		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		
		/*
		System.out.println(System.getProperties().getProperty("logback.configurationFile"));

		try {
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(context);
			context.reset(); 
			configurator.doConfigure(System.getProperties().getProperty("logback.configurationFile"));
		} catch (JoranException je) {
			// StatusPrinter will handle this
		}
		*/

		if("INFO".equals(logLevel)){
			LOGGER.info(errMsg);
		}else if("TRACE".equals(logLevel)){
			LOGGER.trace(errMsg);
		}else if("DEBUG".equals(logLevel)){
			LOGGER.debug(errMsg);
		}else if("WARN".equals(logLevel)){
			LOGGER.warn(errMsg);
		}else if("ERROR".equals(logLevel)){
			LOGGER.error(errMsg);
		}else {
			LOGGER.debug(errMsg);
		}

	}

}
