package com.fd.epcws.util;

/**
 * @author cgordon
 *
 * Preference Center Utility class
 * 
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.logging.log4j.Logger;

import com.fd.epcws.constants.Constants;
import com.fd.epcws.exceptions.AppException;
import com.fd.epcws.exceptions.MissingResourceException;
import com.fd.epcws.exceptions.SortingException;
import com.fd.epcws.rest.beans.AbstractResource;
import com.fd.epcws.util.helpers.LoggingHelper;

import java.lang.reflect.Field;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class PreferenceUtils {
	private final String CLASSNAME = "PreferenceUtils";

	protected Logger LOGGER = LoggingHelper.getLogger(PreferenceUtils.class);

	/** This method sorts a collection of <code>AbstractResource</code> subclass objects. validates field names to sort to avoid runtime exception if field is invalid 
	 * @param <code>ArrayList</code> list of AbstractResource objects to sort
	 * @param <code>String</code> tokenized list of field name(s) to sort by	    
	 * @return <code>ArrayList</code> sorted list of AbstractResource objects to sort
	 * @throws <code>AppException</code> AppException  
	 */	
	public ArrayList<? extends AbstractResource> sort(ArrayList<? extends AbstractResource> list, String fieldnames) throws AppException{
		String METHODNAME = "sort";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		if(fieldnames == null || fieldnames.isEmpty()){
			return list;
		}

		if(list == null || list.isEmpty()) return list;

		Class<?> klass = ((AbstractResource)list.get(0)).getClass();

		StringTokenizer tokenizer = new StringTokenizer(fieldnames, Constants.QUERY_PARAM_SORT_SEPARATOR);

		String token1 = null, token2 = null , token3 = null;

		try{
			switch(tokenizer.countTokens()){

			case 1:

				token1 = tokenizer.nextToken();					
				if(!verifyFieldname(klass, token1)) {
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(), Constants.MESSAGE_SORTING_INVALID_FIELD));
					return list;
				}

				Collections.sort(list, new BeanComparator<AbstractResource>(token1));
				break;

			case 2:
				token1 = tokenizer.nextToken();
				token2 = tokenizer.nextToken();	
				if(!verifyFieldname(klass,token1) || !verifyFieldname(klass,token2)) {
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(), Constants.MESSAGE_SORTING_INVALID_FIELD));
					return list;
				}

				Collections.sort(list,  new BeanComparator<AbstractResource>(token1, new BeanComparator<AbstractResource>(token2)));			
				break;

			case 3:
				token1 = tokenizer.nextToken();
				token2 = tokenizer.nextToken();					
				token3 = tokenizer.nextToken();

				if(!verifyFieldname(klass,token1) || !verifyFieldname(klass,token2) || !verifyFieldname(klass,token3)) {
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(), Constants.MESSAGE_SORTING_INVALID_FIELD));
					return list;
				}

				Collections.sort(list, new BeanComparator<AbstractResource>(token1, new BeanComparator<AbstractResource>(token2, new BeanComparator<AbstractResource>(token3))));			
				break;

			default:
				throw new SortingException(new Throwable(Constants.MESSAGE_SORTING_TOO_MANY_FIELDS), Constants.MESSAGE_SORTING_TOO_MANY_FIELDS);
			}
		}catch(NoSuchElementException e){
			throw new SortingException(e, Constants.MESSAGE_SORTING_INVALID_FIELD);
		}	

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return list;
	}

	/** This method verifies if a field exists in the <code>AbstractResource</code> classs subclass so as to avoid a nasty BeanComparator unchecked error
	 * from occurring.   
	 * @param <code>Class</code> class of the requested object to verify field existence
	 * @param <code>String</code> descriptive error message	    
	 * @return <code>boolean</code> flag existence of given field
	 * @throws <code>SortingException</code> SortingException  
	 */	
	private boolean verifyFieldname(Class<?> klass, String fieldname) throws SortingException{
		String METHODNAME = "verifyFieldname";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		try {
			@SuppressWarnings("unused")
			Field f = klass.getDeclaredField(fieldname);
		}
		catch (NoSuchFieldException ex) {
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(), Constants.MESSAGE_SORTING_INVALID_FIELD));
			return false;
		}
		catch (SecurityException ex) {
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(), Constants.MESSAGE_SORTING_INVALID_FIELD));
			return false;
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return true;
	}

	/** This returns a boolean flag indicating if the feature is available to the caller requests.
	 * @param <code>String</code> feature to be verified 
	 * @return <code>boolean</code> flag if feature is turned on
	 * @throws <code>AppException</code> if an exception occurs
	 */	
	public boolean isFeatureAllowed(String feature) throws AppException{

		String METHODNAME = "isFeatureAllowed";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
 		
		return new Boolean((String)getConsoleProperty(feature));
	}
	
	/** This returns a raw [object] value of the requested feature 
	 * @param <code>String</code> feature to be verified 
	 * @return <code>String</code> value of the was console environment variable
	 * @throws <code>MissingResourceException</code> if an exception occurs due to missing was console value
	 */	
	public Object getConsoleProperty(String feature) throws MissingResourceException{

		String METHODNAME = "getConsoleProperty";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Object value = new Object();

		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,feature));
			Context ctx = new InitialContext();
			value = ctx.lookup(feature);

		}catch(NamingException e){
			LOGGER.fatal(LoggingHelper.formatMessage(this.getClass(), e.getMessage()));
			throw new MissingResourceException(e, Constants.MESSAGE_MISSING_RESOURCE_BUNDLE);
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return value;
	}
	
	
	public String printMap(Map<?, ?> map){

		StringBuffer sb = new StringBuffer();
		sb.append(Constants.STRING_LEFT_BRACKET);
	    for (Map.Entry<?, ?> entry : map.entrySet()) {          
	         sb.append(entry.getKey()).append(" : ").append(entry.getValue().toString());
	    }
	    sb.append(Constants.STRING_RIGHT_BRACKET);;
	    
		return sb.toString();
	}
	
}
