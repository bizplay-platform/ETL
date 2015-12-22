package bizplay.etl.com;

import java.io.PrintStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class etlLogManager {
	private static final Logger LOGGER = LoggerFactory.getLogger("");

	public static void etlLog(String logLevel, String errMsg){
		
		/*
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		
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
	
	public static void etlLog(String logLevel, String errMsg, Exception ex){
		LOGGER.error(errMsg,ex);
	}
	
	public static PrintStream createLoggingSystem(final PrintStream realPrintStream) {
	    return new PrintStream(realPrintStream) {
	        public void print(final String string) {
	        	LOGGER.debug(string);
	        }
	        public void println(final String string) {
	        	LOGGER.debug(string);
	        }
	    };
	}
	
	public static PrintStream createLoggingError(final PrintStream realPrintStream) {
		return new PrintStream(realPrintStream) {
			public void print(final String string) {
				LOGGER.error(string);
			}
			public void println(final String string) {
				LOGGER.error(string);
			}
		};
	}

}
