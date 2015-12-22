package bizplay.etl.com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;

public class etlLogManager {
	public static void etlLog(String logLevel, String errMsg){

		Logger LOGGER = LoggerFactory.getLogger("");

		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		
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
