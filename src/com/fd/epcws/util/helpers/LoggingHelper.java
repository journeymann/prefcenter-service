/**
 * 
 */
package com.fd.epcws.util.helpers;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.fd.epcws.constants.Constants;
import com.fd.epcws.exceptions.AppException;

/**
 * @author cgordon
 *
 */
public class LoggingHelper {

	/** This returns a formatted  error message string to be used by the logging framework
	 * message includes class and method name as well as the relevant error message supplied to the method   
	 * @param <code>Class</code> source class where call originates
	 * @param <code>Throwable</code> Exception class	    * 
	 * @param <code>String</code> descriptive error message	    * 
	 * @return <code>String</code> formatted error message 
	 */	
	public static String formatMessage(Class<?> klass, AppException exception, String message){
		String CLASSNAME = "LoggingHelper";
		String METHODNAME = "formatMessage OL1";
		Logger LOGGER = LoggingHelper.getLogger(LoggingHelper.class);
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.getException().printStackTrace(pw);

		String classname = klass.getCanonicalName();
		String methodname = Thread.currentThread().getStackTrace()[2].getMethodName();
		String str = Constants.MESSAGE_ERROR_CLASS_DESC + classname + Constants.MESSAGE_ERROR_METHOD_DESC + methodname + 
		Constants.MESSAGE_ERROR_DESC + message + Constants.MESSAGE_ERROR_END_DESC + Constants.MESSAGE_BLANK + exception.getMessage() +
		Constants.MESSAGE_BLANK + exception.getLocalizedMessage() + Constants.MESSAGE_BLANK + exception.getErrorMessage()+
		Constants.MESSAGE_BLANK + exception.getErrors() + Constants.MESSAGE_BLANK + sw.toString();  	

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return str;
	}

	/** This returns a formatted  error message string to be used by the logging framework
	 * message includes class and method name as well as the relevant error message supplied to the method
	 * @param <code>Class</code> source class where call originates
	 * @param <code>String</code> descriptive error message
	 * @return <code>String</code> formatted error message 
	 */	
	public static String formatMessage(Class<?> klass, String message){
		String CLASSNAME = "LoggingHelper";
		String  METHODNAME = "formatMessage OL2";
		Logger LOGGER = LoggingHelper.getLogger(LoggingHelper.class);
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		String classname = klass.getCanonicalName();
		String methodname = Thread.currentThread().getStackTrace()[2].getMethodName();
		String str = Constants.MESSAGE_ERROR_CLASS_DESC + classname + Constants.MESSAGE_ERROR_METHOD_DESC + methodname + 
		Constants.MESSAGE_ERROR_DESC + message + Constants.MESSAGE_ERROR_END_DESC;    	

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return str;
	}

	/** This returns a formatted  error message string to be used by the logging framework
	 * message includes class and method name as well as the relevant error message supplied to the method   
	 * @param <code>String</code> descriptive error message
	 * @return <code>String</code> formatted error message 
	 */	
	public static String formatMessage(String message){
		String CLASSNAME = "LoggingHelper";
		String METHODNAME = "formatMessage OL3";
		Logger LOGGER = LoggingHelper.getLogger(LoggingHelper.class);
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		String str = Constants.MESSAGE_ERROR_DESC + message + Constants.MESSAGE_ERROR_END_DESC;    	

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return str;
	}

	/** This returns a logger object based on the class/type parameter passed
	 * @param <code>Class</code> the class to associate with appender
	 * @return <code>Logger</code> logger class to be used for logging 
	 */	
	public static Logger getLogger(Class<?> klass){

		Logger logger = (Logger)LogManager.getLogger(klass);

		return logger;
	}
}
