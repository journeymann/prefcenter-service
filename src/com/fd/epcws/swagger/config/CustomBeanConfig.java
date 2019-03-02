/**
 * 
 */
package com.fd.epcws.swagger.config;

/**
 * @author cgordon
 *
 */
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletConfig;
import javax.ws.rs.core.Application;

import org.apache.logging.log4j.core.Logger;
import org.reflections.Reflections;

import scala.collection.JavaConverters$;
import scala.collection.TraversableOnce;
import scala.collection.immutable.List; 
import com.fd.epcws.constants.Constants;
import com.fd.epcws.rest.services.AbstractService;
import com.fd.epcws.util.helpers.LoggingHelper;
import com.wordnik.swagger.jaxrs.config.BeanConfig;

public class CustomBeanConfig extends BeanConfig {

	private Logger LOGGER = LoggingHelper.getLogger(CustomBeanConfig.class);
	private final String CLASSNAME = "CustomBeanConfig";

	/**
	 * customized method which fetches the swagger classes from the servlet configuration.
	 * added to fortify JSON web service so that input will be read regardless of format i.e. wrapper envelope 
	 * included or not
	 * @param <code>Application</code> application
	 * @param <code>ServletConfig</code> servlet configuration object
	 * @return <code>List</code> list of swagger configured / annotated classes 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List classesFromContext(Application app, ServletConfig sc) {

		String METHODNAME = "classesFromContext";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Set<Class> classes = new HashSet<Class>();
		Reflections reflections = new Reflections(Constants.STRING_RESOURCE_PACKAGE);
		Set<Class<? extends AbstractService>> services = (Set<Class<? extends AbstractService>>)reflections.getSubTypesOf(AbstractService.class);
		classes.addAll(services);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));		
		return ((TraversableOnce) JavaConverters$.MODULE$.asScalaSetConverter(classes).asScala()).toList();
	}

}
