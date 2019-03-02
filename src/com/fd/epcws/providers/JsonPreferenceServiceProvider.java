/**
 * 
 */
/**
 * @author cgordon
 *
 */

package com.fd.epcws.providers;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fd.epcws.constants.Constants;
import com.fd.epcws.exceptions.MissingResourceException;
import com.fd.epcws.rest.beans.AbstractResource;
import com.fd.epcws.util.PreferenceUtils;
import com.fd.epcws.util.helpers.LoggingHelper;

/** Custom provider class used to provide 18F specific JSON behavior such as how to format date fields
 */

@Provider
@Produces(MediaType.APPLICATION_JSON )
public class JsonPreferenceServiceProvider implements MessageBodyWriter<Object> {

	//private final String CLASSNAME = "JsonPreferenceServiceProvider";
	private Logger LOGGER = LoggingHelper.getLogger(JsonPreferenceServiceProvider.class);

	/** Determines size. preset to -1 
	 * @param <code>Object</code> servlet context event
	 * @param <code>Class</code> Generic Class type
	 * @param <code>Type</code> Type class (JSON)
	 * @param <code>Annotation</code> list of annotations
	 * @param <code>MediaType</code> type of media such as JSON XML etc 
	 * @return <code>long</code> size preset to -1  
	 */	
	public long getSize( Object object, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType ) {
		return -1;
	}

	/** Determines if JSON data list is writeable   
	 * @param <code>Class</code> Generic Class type
	 * @param <code>Type</code> Type class (JSON) 
	 * @param <code>Annotation</code> list of annotations
	 * @param <code>MediaType</code> type of media such as JSON XML etc 
	 * @return <code>long</code> size preset to -1  
	 */	
	public boolean isWriteable( Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType ) {
		boolean isWritable = false;
		if ( List.class.isAssignableFrom( type )
				&& genericType instanceof ParameterizedType ) {
			ParameterizedType parameterizedType = (ParameterizedType) genericType;
			Type[] actualTypeArgs = ( parameterizedType.getActualTypeArguments() );
			isWritable = (
					actualTypeArgs.length == 1
					&& actualTypeArgs[0].equals( AbstractResource.class ) );
		} else if ( type == AbstractResource.class ) {
			isWritable = true;
		}
		return isWritable;
	}

	/** Modifies the JSON data. writes to outputstream
	 * 
	 * Please note that logging is not maintained for this method because it is being called too frequently and was 
	 * cluttering up the log file.
	 * 
	 * @param <code>Object</code> servlet context event
	 * @param <code>Class</code> Generic Class type
	 * @param <code>Type</code> Type class (JSON)
	 * @param <code>Annotation</code> list of annotations
	 * @param <code>MediaType</code> type of media such as JSON XML etc
	 * @param <code>MultivaluedMap</code> map of http headers
	 * @param <code>OutputStream</code> entity output stream   
	 * @return <code>long</code> size preset to -1  
	 */	
	public void writeTo( Object object, Class<?> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream )
	throws IOException {

		boolean wrapRootValue = false;
		PreferenceUtils util = new PreferenceUtils();

		try{
			
			String flag = (String)util.getConsoleProperty(Constants.LOOKUP_PREF_JSON_ENV_INCLUDE);
			wrapRootValue = new Boolean(flag);

		}catch(MissingResourceException e){
			LOGGER.warn(LoggingHelper.formatMessage(this.getClass(), e.getMessage()));
		}

		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure( SerializationFeature.WRAP_ROOT_VALUE, wrapRootValue );

		mapper.writeValue( entityStream, object );
	}
}
