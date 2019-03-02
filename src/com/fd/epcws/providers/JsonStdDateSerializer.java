/**
 * 
 */
package com.fd.epcws.providers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.Logger;
import com.fasterxml.jackson.core.JsonGenerator; 
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fd.epcws.constants.Constants;
import com.fd.epcws.exceptions.MissingResourceException;
import com.fd.epcws.util.PreferenceUtils;
import com.fd.epcws.util.helpers.LoggingHelper;
import com.fasterxml.jackson.databind.util.StdDateFormat;

/**
 * @author cgordon
 *
 */
public class JsonStdDateSerializer extends JsonSerializer<Date> {

	private final String CLASSNAME = "JsonStdDateSerializer";
	private Logger LOGGER = LoggingHelper.getLogger(JsonStdDateSerializer.class);

	/** This method serializes date fields to the configured date format 
	 * @param <code>Date</code> Date field to be formatted
	 * @param <code>JsonGenerator</code> JSON generator class	    
	 * @param <code>SerializerProvider</code> JSON Se4rialization provider 
	 */	
	@Override
	public void serialize(Date date, JsonGenerator jgen, SerializerProvider provider)
	throws IOException, JsonProcessingException {

		String METHODNAME = "serialize";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		String dateFormat = Constants.STRING_BLANK;
		PreferenceUtils util = new PreferenceUtils();		

		try {
			dateFormat = (String)util.getConsoleProperty(Constants.LOOKUP_PREF_DATE_FORMAT);

		}catch(MissingResourceException e){
			LOGGER.warn(LoggingHelper.formatMessage(this.getClass(), e.getMessage()));
			dateFormat = StdDateFormat.getBlueprintISO8601Format().toString(); // default format
		}

		SimpleDateFormat FORMATTER = new SimpleDateFormat(dateFormat);	  
		String formattedDate = FORMATTER.format(date);
		jgen.writeString(formattedDate);
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
	}
}
