/**
 * 
 */
package com.fd.epcws.listeners;

/**
 * @author cgordon
 *
 */
import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;

import com.fd.epcws.constants.Constants;
import com.fd.epcws.util.helpers.LoggingHelper;

public class ContextListener implements ServletContextListener {

	private Logger LOGGER = LoggingHelper.getLogger(ContextListener.class);
	private final String CLASSNAME = "ContextListener"; 


	/** Provides opportunity for custom context behavior to be configured when application
	 * server is started.
	 * Currently used to initialize log4j when the application is being started
	 * @param <code>ServletContextEvent</code> servlet context event
	 */	
	@Override
	public void contextInitialized(ServletContextEvent event) {

		String METHODNAME = "contextInitialized";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		// initialize log4j here
		ServletContext context = event.getServletContext();
		String log4jConfigFile = context.getInitParameter(Constants.CONFIG_FILE_PARAM_KEY_LOG4J);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"log.xml file init param location: "+ log4jConfigFile));        
		File file = new File(log4jConfigFile);

		// these will force a log4J2 reconfiguration
		LoggerContext log_context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		log_context.setConfigLocation(file.toURI());        

		// initialize application properties (service.properties) here        
		context.setAttribute(Constants.CONFIG_FILE_PARAM_KEY_LOG4J,context.getInitParameter(Constants.CONFIG_FILE_PARAM_KEY_LOG4J));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
	}

	/** Provides opportunity to gracefully remove custom context behavior when application is suspended
	 * @param <code>ServletContextEvent</code> servlet context event
	 */	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		String METHODNAME = "contextDestroyed";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		ServletContext context = event.getServletContext();
		context.removeAttribute(Constants.CONFIG_FILE_PARAM_KEY_LOG4J);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
	}  
}
