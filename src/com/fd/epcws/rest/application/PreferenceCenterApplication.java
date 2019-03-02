/**
 * 
 */
package com.fd.epcws.rest.application;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.apache.logging.log4j.core.Logger;
import org.reflections.Reflections; 

import com.fd.epcws.constants.Constants;
import com.fd.epcws.exceptions.handlers.GenericExceptionMapper;
import com.fd.epcws.rest.services.AbstractService;
import com.fd.epcws.swagger.CustomModelConverter;
import com.fd.epcws.swagger.config.CustomBeanConfig;
import com.fd.epcws.util.helpers.LoggingHelper;
import com.fd.epcws.performance.aspects.LogMetricsAspect;
import com.fd.epcws.providers.JsonPreferenceServiceProvider;
import com.wordnik.swagger.converter.ModelConverters;
import com.wordnik.swagger.jaxrs.config.BeanConfig;

/**
 * @author cgordon
 *
 */
@ApplicationPath("/")
public class PreferenceCenterApplication extends Application{

	protected Logger LOGGER = LoggingHelper.getLogger(PreferenceCenterApplication.class);

	public Map<String,Object> map =  Collections.emptyMap();

	private final String CLASSNAME = "PreferenceCenterApplication";

	/**
	 * (non-Javadoc)
	 *
	 * @see javax.ws.rs.core.Application#getClasses()
	 */
	@Override	
	public Set<Class<?>> getClasses() {

		String METHODNAME = "getClasses";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Set<Class<?>> classes = new HashSet<Class<?>>();

		// Resources
		LOGGER.info(String.format(Constants.MESSAGE_DEBUG_CHECKPOINT_INCLUDE," adding service/resource classes"));
		Reflections reflections = new Reflections(Constants.STRING_RESOURCE_PACKAGE);
		Set<Class<? extends AbstractService>> services = (Set<Class<? extends AbstractService>>)reflections.getSubTypesOf(AbstractService.class);
		classes.addAll(services);

		// JAX RS tooling        
		LOGGER.info(String.format(Constants.MESSAGE_DEBUG_CHECKPOINT_INCLUDE," adding fasterxml [jackson] provider classes"));        
		classes.add(com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider.class );
		classes.add(com.fasterxml.jackson.jaxrs.json.JsonMappingExceptionMapper.class );
		classes.add(com.fasterxml.jackson.jaxrs.json.JsonParseExceptionMapper.class );
		classes.add(com.fasterxml.jackson.jaxrs.json.Annotations.class );

		// Providers
		LOGGER.info(String.format(Constants.MESSAGE_DEBUG_CHECKPOINT_INCLUDE," adding container exception provider classes"));        
		classes.add(GenericExceptionMapper.class );
		classes.add(JsonPreferenceServiceProvider.class);
		classes.add(LogMetricsAspect.class);

		// Swagger tooling        
		LOGGER.info(String.format(Constants.MESSAGE_DEBUG_CHECKPOINT_INCLUDE," adding swagger [wordnik] provider classes"));        
		classes.add(com.wordnik.swagger.jaxrs.listing.ApiListingResource.class);
		classes.add(com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider.class);
		classes.add(com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON.class);
		classes.add(com.wordnik.swagger.jaxrs.listing.ResourceListingProvider.class);

		// Swagger initialize        
		LOGGER.info(String.format(Constants.MESSAGE_DEBUG_CHECKPOINT_INCLUDE," initialise swagger - bean configuration"));
		initialiseSwagger();

		// Initialize Swagger Model Converters
		LOGGER.info(String.format(Constants.MESSAGE_DEBUG_CHECKPOINT_INCLUDE," adding custom annotation converter for hidden resource fields"));        
		ModelConverters.addConverter(new CustomModelConverter(), true);

		LOGGER.info(String.format(Constants.MESSAGE_START_APPLICATION_INCLUDE,"getClasses() - SUCCESS"));        
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return classes;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see javax.ws.rs.core.Application#getProperties()
	 */
	public Map<String,Object> getProperties() {

		String METHODNAME = "getProperties";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		LOGGER.info(String.format(Constants.MESSAGE_START_APPLICATION_INCLUDE,"getProperties() - SUCCESS"));
		return map;
	}


	/** Swagger bootstrap initialization method 
	 */	
	public void initialiseSwagger(){

		String METHODNAME = "initialiseSwagger";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		try{
			LOGGER.info(String.format(Constants.MESSAGE_DEBUG_CHECKPOINT_INCLUDE," before swagger set info"));
			BeanConfig beanConfig = new CustomBeanConfig();

			beanConfig.setVersion("1.0.2");
			beanConfig.setTitle(title);
			beanConfig.setDescription(description);
			beanConfig.setTermsOfServiceUrl(termsUrl);
			beanConfig.setLicense(license);
			beanConfig.setLicenseUrl(licenseUrl);
			beanConfig.setContact(contact);
			beanConfig.setBasePath(basePath);
			beanConfig.setResourcePackage(Constants.STRING_RESOURCE_PACKAGE);
			beanConfig.setScan(true);

			LOGGER.info(String.format(Constants.MESSAGE_DEBUG_CHECKPOINT_INCLUDE," after swagger setup info"));
		}catch(Exception e){
			LOGGER.error(String.format(Constants.MESSAGE_GENERIC_DETAILS_INCLUDE,"Error initializing the swagger tooling.", e.getMessage()));
		}
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));        
	}

	/** Swagger bootstrap initialization configuration constants
	 */	
	private String title = Constants.SWAGGER_TITLE;
	private String description = Constants.SWAGGER_DESCRIPTION;
	private String termsUrl = Constants.SWAGGER_TERMS_URL; 
	private String license = Constants.SWAGGER_LICENSE;
	private String licenseUrl = Constants.SWAGGER_LICENSE_URL;
	private String contact = Constants.SWAGGER_CONTACT; 
	private String basePath = Constants.SWAGGER_BASE_PATH;

}
