/**
 * @author cgordon
 *
 */
package com.fd.epcws.rest.services;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fd.epcws.constants.Constants;
import com.fd.epcws.delegates.PreferenceDelegate;
import com.fd.epcws.exceptions.AppException;
import com.fd.epcws.exceptions.GeneralException;
import com.fd.epcws.exceptions.InvalidDataException;
import com.fd.epcws.exceptions.InvalidUriException;
import com.fd.epcws.exceptions.handlers.Status;
import com.fd.epcws.facade.EnvelopeFacade;
import com.fd.epcws.facade.ResponseCode;
import com.fd.epcws.facade.ResponseFacade;
import com.fd.epcws.rest.beans.AbstractResource;
import com.fd.epcws.util.PreferenceUtils;
import com.fd.epcws.util.helpers.LoggingHelper;
import com.fd.epcws.validate.FieldValidator;

public abstract class AbstractService {

	protected Logger LOGGER = LoggingHelper.getLogger(AbstractService.class);
	protected EnvelopeFacade envelopeFacade = new EnvelopeFacade();
	protected ResponseFacade responseFacade = new ResponseFacade();
	protected FieldValidator fieldValidator = new FieldValidator();	

	private final String CLASSNAME = "AbstractService";

	/** Gets the <code>PreferenceDelegate<code> business delegate class for use by its sub classes 
	 * @return <code>PreferenceDelegate</code> class or sub class
	 */	
	protected PreferenceDelegate getPreferenceDelagate(){
		String METHODNAME="getPreferenceDelagate";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		PreferenceDelegate delegate = null;

		try{
			delegate = new PreferenceDelegate();
		}catch(AppException ae){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(), ae.getMessage()));
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return delegate;
	}

	/** This method provides the option to customize behavior before data is returned to caller
	 * @param <code>String</code> input data type constant
	 * @param <code>ArrayList</code> input data object list      
	 * @param <code>boolean</code> use envelope for output
	 * @return <code>Response</code> data object post processing
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */	
	protected Response getControlResponse(String label, ArrayList<? extends AbstractResource> list, boolean useEnvelope) throws AppException{
		String METHODNAME="getControlResponse OL1";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return this.getControlResponse(label, list, useEnvelope, Constants.BOOL_DEFAULT_PRETTY);
	}

	/** This method provides the option to customize behavior before data is returned to caller
	 * @param <code>AbstractResource</code> input data object list
	 * @param <code>boolean</code> use envelope for output
	 * @return <code>Response</code> data object post processing
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */	
	protected Response getControlResponse(AbstractResource model, boolean useEnvelope) throws AppException{
		String METHODNAME="getControlResponse  OL2";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return this.getControlResponse(model, useEnvelope, Constants.BOOL_DEFAULT_PRETTY);
	}


	/** This method provides the option to customize behavior before data is returned to caller
	 * @param <code>int</code> input data type type of response to return
	 * @param <code>URI</code> input data URI object to include in created response      
	 * @param <code>String</code> input data message object      
	 * @param <code>String</code> resource type for request 
	 * @param <code>boolean</code> use envelope for output
	 * @return <code>Response</code> data object post processing
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */	
	protected Response getControlResponse(int code, URI uri, String msg, String label,boolean useEnvelope) throws AppException{
		String METHODNAME="getControlResponse  OL3";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return this.getControlResponse(code, uri, msg, label, useEnvelope, Constants.BOOL_DEFAULT_PRETTY);
	}	

	/** This method provides the option to customize behavior before data is returned to caller
	 * @param <code>int</code> input data type type of response to return
	 * @param <code>URI</code> input data URI object to include in created response      
	 * @param <code>String</code> input data message object      
	 * @param <code>String</code> resource type for request 
	 * @param <code>boolean</code> use envelope for output
	 * @param <code>boolean</code> pretty print flag for output      
	 * @return <code>Response</code> data object post processing
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */	
	protected Response getControlResponse(int code, URI uri, String msg, String label,boolean useEnvelope, boolean pretty) throws AppException{
		String METHODNAME="getControlResponse  OL4";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		Response response = null;
		JSONObject uriObj = new JSONObject();
		JSONObject msgObj = new JSONObject();
		ObjectMapper mapper = new ObjectMapper();
		
		String value = getURLString(uri);		
		useEnvelope = envelopeFacade.isEnvelopeAllowed() && useEnvelope;
		pretty = responseFacade.isPrettyAllowed() && pretty;

		mapper.configure( SerializationFeature.WRAP_ROOT_VALUE, useEnvelope );

		try{
			
			uriObj.put(Constants.JSON_DATA_LABEL_URI, getURLString(uri));
			msgObj.put(Constants.JSON_DATA_LABEL_MESSAGE, msg);

			LOGGER.debug(new JSONObject().put(Constants.JSON_DATA_LABEL_DIAGNOSTIC, envelopeFacade.getDataEnvelope(code,uri,msg)));
			LOGGER.debug(new JSONObject().put(Constants.JSON_DATA_LABEL_DIAGNOSTIC, envelopeFacade.getDataEnvelope(code,msg,label)));

		}catch(JSONException je){
			LOGGER.error(String.format(Constants.MESSAGE_ERROR_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME, je.getMessage()));
			throw new InvalidUriException(je, Constants.MESSAGE_SERVICE_FAIL_RESOLVE_URI);
		}

		try{
			switch(code){
			case Constants.RESPONSE_CODE_CREATED:
				response = Response.created(uri).type(MediaType.APPLICATION_JSON).entity(
						(pretty)? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value)	        				
								:  mapper.writeValueAsString(value)).build();
				break;
			case Constants.RESPONSE_CODE_OK:
				response = Response.ok(MediaType.APPLICATION_JSON).entity(
						(pretty)? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(msg)	    							
								:  mapper.writeValueAsString(msg)).build();    			
				break;
			case Constants.RESPONSE_CODE_NO_CONTENT:
				response = Response.noContent().type(MediaType.APPLICATION_JSON).entity(
						(pretty)? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(code)	        				
								:  mapper.writeValueAsString(code)).build();    			
				break;
			default:
				response = Response.status(code).type(MediaType.APPLICATION_JSON).entity(
						(pretty)? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(code)
								:  mapper.writeValueAsString(code)).build();    			
			break;
			}

		}catch(JsonProcessingException je){
			LOGGER.error(String.format(Constants.MESSAGE_ERROR_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME, je.getMessage()));   
			throw new GeneralException(je.getMessage());
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return response;
	}


	/**
	 * Encode a string into canonical form (using UTF8 encoding as recommended by W3C), throw exception if not succeed.
	 * @param <code>String</code> input data message object 
	 * @return <code>String</code> encoded URL as a string
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */
	private String getURLString(URI uri) throws AppException{
		String METHODNAME="getURLString";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		if(uri == null) return Constants.MESSAGE_BLANK;

		String urlString = uri.toASCIIString();
		String output = Constants.MESSAGE_BLANK;

		if(StringUtils.isEmpty(urlString)) return Constants.MESSAGE_BLANK;

		try{
			// decode first
			String temp =null;
			do{
				if (null!=temp)
					urlString = temp;
				temp = URLDecoder.decode(urlString, Constants.STR_URL_ENCODE_SCHEME);
			}while (!urlString.equals(temp));
		} catch (UnsupportedEncodingException ue) {
			LOGGER.error(String.format(Constants.MESSAGE_ERROR_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME, ue.getMessage()));
			throw new InvalidUriException(ue, Constants.MESSAGE_SERVICE_FAIL_RESOLVE_URI);
		}

		//encode
		try {
			String urlEncoded = URLEncoder.encode(urlString, Constants.STR_URL_ENCODE_SCHEME);
			output = URLDecoder.decode(urlEncoded, Constants.STR_URL_ENCODE_SCHEME);
		} catch (UnsupportedEncodingException ue) {
			LOGGER.error(String.format(Constants.MESSAGE_ERROR_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME, ue.getMessage()));
			throw new InvalidUriException(ue, Constants.MESSAGE_SERVICE_FAIL_RESOLVE_URI);
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));    		
		return output;
	}

	/** This method provides the option to customize behavior before data is returned to caller
	 * @param <code>int</code> input data type type of response to return
	 * @param <code>String</code> input data message object      
	 * @param <code>String</code> resource type for request 
	 * @param <code>boolean</code> use envelope for output
	 * @return <code>Response</code> data object post processing
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */	
	protected Response getControlResponse(int code, String msg, String label,boolean useEnvelope) throws AppException{
		String METHODNAME="getControlResponse OL5";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return this.getControlResponse(code, msg, label, useEnvelope, Constants.BOOL_DEFAULT_PRETTY);
	}

	/** This method provides the option to customize behavior before data is returned to caller
	 * @param <code>int</code> input data type type of response to return
	 * @param <code>String</code> input data message object      
	 * @param <code>String</code> resource type for request 
	 * @param <code>boolean</code> use envelope for output
	 * @param <code>boolean</code> pretty print flag for output      
	 * @return <code>Response</code> data object post processing
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */	
	protected Response getControlResponse(int code, String msg, String label,boolean useEnvelope, boolean pretty) throws AppException{
		String METHODNAME="getControlResponse  OL6";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		Response response = null;
		ObjectMapper mapper = new ObjectMapper();    	

		Status status = new Status(code, msg, label);
		useEnvelope = envelopeFacade.isEnvelopeAllowed() && useEnvelope;
		pretty = responseFacade.isPrettyAllowed() && pretty;    	
		JSONObject msgObj = new JSONObject();

		mapper.configure( SerializationFeature.WRAP_ROOT_VALUE, useEnvelope );

		try{
			msgObj.put(Constants.JSON_DATA_LABEL_MESSAGE,msg);

			LOGGER.debug(new JSONObject().put(Constants.JSON_DATA_LABEL_DIAGNOSTIC, envelopeFacade.getDataEnvelope(code,label,msg)));

		}catch(JSONException je){
			LOGGER.error(String.format(Constants.MESSAGE_ERROR_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME, je.getMessage()));
			throw new InvalidUriException(je, Constants.MESSAGE_GENERAL_EXCEPTION);
		}

		try{

			switch(code){
			case Constants.RESPONSE_CODE_NO_CONTENT:
				response = Response.noContent().type(MediaType.APPLICATION_JSON).entity(
						(pretty)? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(code)
								: mapper.writeValueAsString(code)).build();    			
				break;

			case Constants.RESPONSE_CODE_OK:
				response = Response.ok(MediaType.APPLICATION_JSON).entity(
						(pretty)? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(status)		
								:  mapper.writeValueAsString(status)).build();    			
				break;

			case Constants.RESPONSE_CODE_BAD_DATA_CREATE:
			case Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE:	    
				response = Response.status(code).type(MediaType.APPLICATION_JSON).entity(
						(pretty)? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(status)		
								:  mapper.writeValueAsString(status)).build();    			
				break;

			default:
				response = Response.status(code).type(MediaType.APPLICATION_JSON).entity(
						(pretty)? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(status)
								:  mapper.writeValueAsString(status)).build();    			
			break;
			}

		}catch(JsonProcessingException je){
			LOGGER.error(String.format(Constants.MESSAGE_ERROR_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME, je.getMessage()));   
			throw new GeneralException(je.getMessage());
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return response;
	}

	/** This returns a <code>Response</code> object based on the integer response code passed to the method
	 * @param <code>int</code> response code 18F custom response code
	 * @param <code>boolean</code> use envelope for output
	 * @return <code>Response</code> response object
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */	
	protected Response getControlResponse(int code, boolean useEnvelope) throws AppException{
		String METHODNAME="getControlResponse  OL7";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return this.getControlResponse(code, useEnvelope, Constants.BOOL_DEFAULT_PRETTY);
	}

	/** This returns a <code>Response</code> object based on the integer response code passed to the method
	 * @param <code>int</code> response code 18F custom response code
	 * @param <code>boolean</code> use envelope for output
	 * @param <code>boolean</code> pretty print flag for output      
	 * @return <code>Response</code> response object
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */	
	protected Response getControlResponse(int code, boolean useEnvelope, boolean pretty) throws AppException{
		String METHODNAME="getControlResponse  OL8";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		Response response = null;
		ObjectMapper mapper = new ObjectMapper();
		ResponseCode responseCode = ResponseFacade.getResponseCode(code);

		useEnvelope = envelopeFacade.isEnvelopeAllowed() && useEnvelope;
		pretty = responseFacade.isPrettyAllowed() && pretty;

		mapper.configure( SerializationFeature.WRAP_ROOT_VALUE, useEnvelope );

		try{
			String msg = responseCode.getDescription();
			
			response = Response.status(code).type(MediaType.APPLICATION_JSON).entity( 
					(pretty)? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(msg)
							:  mapper.writeValueAsString(msg)).build();

			LOGGER.debug(new JSONObject().put(Constants.JSON_DATA_LABEL_DIAGNOSTIC, envelopeFacade.getDataEnvelope(code)));
		}catch(JSONException je){
			LOGGER.error(String.format(Constants.MESSAGE_ERROR_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME, je.getMessage()));
			throw new InvalidDataException(je, Constants.MESSAGE_GENERAL_EXCEPTION);
		}catch(JsonProcessingException je){
			LOGGER.error(String.format(Constants.MESSAGE_ERROR_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME, je.getMessage()));   
			throw new GeneralException(je.getMessage());
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return response;
	}

	/** This method provides the option to customize behavior before data is returned to caller
	 * @param <code>String</code> input data label constant
	 * @param <code>ArrayList</code> input data object list  
	 * @param <code>boolean</code> use envelope for output
	 * @param <code>boolean</code> pretty print flag for output      
	 * @return <code>Response</code> data object post processing
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */	
	protected Response getControlResponse(String label, ArrayList<? extends AbstractResource> list, boolean useEnvelope, boolean pretty) throws AppException{
		String METHODNAME="getControlResponse OL9";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		Response response = null;
		ObjectMapper mapper = new ObjectMapper();
		
		useEnvelope = envelopeFacade.isEnvelopeAllowed() && useEnvelope;
		pretty = responseFacade.isPrettyAllowed() && pretty;

		mapper.configure( SerializationFeature.WRAP_ROOT_VALUE, false );

		try{
			
			HashMap<String, ArrayList<Map<String, AbstractResource>>> map = envelopeFacade.getJsonData(label, list);
			
			response = Response.ok((pretty)? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map)
					:  mapper.writeValueAsString(map)).build();

			LOGGER.debug(new JSONObject().put(Constants.JSON_DATA_LABEL_DIAGNOSTIC, envelopeFacade.getJsonData(label, list)));
			
		}catch(JSONException je){
			LOGGER.error(String.format(Constants.MESSAGE_ERROR_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME, je.getMessage()));
			throw new InvalidDataException(je, Constants.MESSAGE_GENERAL_EXCEPTION);
		}catch(JsonProcessingException je){
			LOGGER.error(String.format(Constants.MESSAGE_ERROR_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME, je.getMessage()));   
			throw new GeneralException(je.getMessage());
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return response;
	}

	/** This method provides the option to customize behavior before data is returned to caller
	 * @param <code>AbstractResource</code> input data object list
	 * @param <code>boolean</code> use envelope for output
	 * @param <code>boolean</code> pretty print flag for output      
	 * @return <code>Response</code> data object post processing
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */	
	protected Response getControlResponse(AbstractResource model, boolean useEnvelope, boolean pretty) throws AppException{
		String METHODNAME="getControlResponse  OL10";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		Response response = null;
		ObjectMapper mapper = new ObjectMapper();

		useEnvelope = envelopeFacade.isEnvelopeAllowed() && useEnvelope;
		pretty = responseFacade.isPrettyAllowed() && pretty;     	

		mapper.configure( SerializationFeature.WRAP_ROOT_VALUE, useEnvelope );

		try{
			
			response = Response.ok((pretty)? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(model)
					:  mapper.writeValueAsString(model)).build();

			LOGGER.debug(new JSONObject().put(Constants.JSON_DATA_LABEL_DIAGNOSTIC, envelopeFacade.getJsonData(model)));
			
		}catch(JSONException je){
			LOGGER.error(String.format(Constants.MESSAGE_ERROR_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME, je.getMessage()));
			throw new InvalidDataException(je, Constants.MESSAGE_GENERAL_EXCEPTION);
		}catch(JsonProcessingException je){
			LOGGER.error(String.format(Constants.MESSAGE_ERROR_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME, je.getMessage()));
			throw new GeneralException(je.getMessage());
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return response;
	}

	/** This returns a <code>URI</code> object based on the integer response code passed to the method
	 * @param <code>String</code> label for URI path e.g. "brands/%s" or "communicationchannels/%s"
	 * @param <code>String</code> value of the resource code       
	 * @return <code>URI</code> uniform resource identifier object
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */	
	protected URI getResponseURI(String label, String new_code) throws AppException{
		String METHODNAME="getResponseURI  OL1";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		URI uri = null;
		PreferenceUtils util = new PreferenceUtils();
		
		try {
			
			String scheme = (String)util.getConsoleProperty(Constants.LOOKUP_PREF_SCHEME);
			String authority = (String)util.getConsoleProperty(Constants.LOOKUP_PREF_AUTHORITY);
			String path = (String)util.getConsoleProperty(Constants.LOOKUP_PREF_PREFIX);

			path = String.format(label, new_code);        
			String query = Constants.MESSAGE_BLANK;
			String fragment = Constants.MESSAGE_BLANK;        

			uri = new URI(scheme, authority, path, query, fragment);
		} catch (Exception e) {
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_RESOLVE_URI));
			throw new InvalidUriException(e, Constants.MESSAGE_SERVICE_FAIL_RESOLVE_URI);
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return uri;
	}

	/** This returns a <code>URI</code> object based on the integer response code passed to the method PS three params specific 
	 * for brand communication channels request for URI with multiple params.
	 * @param <code>String</code> label for URI path e.g. "/contact/%s/communicationchannels/brands%s"
	 * @param <code>String</code> value of the resource code       
	 * @param <code>String</code> value of the resource code 
	 * @return <code>URI</code> uniform resource identifier object
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */	
	protected URI getResponseURI(String label, String new_code1, String new_code2) throws AppException{
		String METHODNAME="getResponseURI  OL2";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		
		URI uri = null;
		PreferenceUtils util = new PreferenceUtils();		

		try {
			String scheme = (String)util.getConsoleProperty(Constants.LOOKUP_PREF_SCHEME);
			String authority = (String)util.getConsoleProperty(Constants.LOOKUP_PREF_AUTHORITY);
			String path = (String)util.getConsoleProperty(Constants.LOOKUP_PREF_PREFIX);
			path = String.format(label, new_code1, new_code2);        
			String query = Constants.MESSAGE_BLANK;
			String fragment = Constants.MESSAGE_BLANK;        
			uri = new URI(scheme, authority, path, query, fragment);
		} catch (Exception e) {
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(), Constants.MESSAGE_SERVICE_FAIL_RESOLVE_URI));
			throw new InvalidUriException(e, Constants.MESSAGE_SERVICE_FAIL_RESOLVE_URI);
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return uri;
	}

	/** This returns a <code>URI</code> object based on the integer response code passed to the method PS three params specific 
	 * for brand communication channels request for URI with multiple params.
	 * @param <code>String</code> label for URI path e.g. "/contact/%s/communicationchannels/%s/brands%s"
	 * @param <code>String</code> value of the resource code       
	 * @param <code>String</code> value of the resource code
	 * @param <code>String</code> value of the resource code 
	 * @return <code>URI</code> uniform resource identifier object
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */	
	protected URI getResponseURI(String label, String new_code1, String new_code2, String new_code3) throws AppException{
		String METHODNAME="getResponseURI  OL3";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		URI uri = null;
		PreferenceUtils util = new PreferenceUtils();		

		try {
			String scheme = (String)util.getConsoleProperty(Constants.LOOKUP_PREF_SCHEME);
			String authority = (String)util.getConsoleProperty(Constants.LOOKUP_PREF_AUTHORITY);
			String path = (String)util.getConsoleProperty(Constants.LOOKUP_PREF_PREFIX);
			path = String.format(label, new_code1, new_code2, new_code3);        
			String query = Constants.MESSAGE_BLANK;
			String fragment = Constants.MESSAGE_BLANK;        

			uri = new URI(scheme, authority, path, query, fragment);
		} catch (Exception e) {
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(), Constants.MESSAGE_SERVICE_FAIL_RESOLVE_URI));
			throw new InvalidUriException(e, Constants.MESSAGE_SERVICE_FAIL_RESOLVE_URI);
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return uri;
	}

	/** This method validates AbstractResource class/sub class based 
	 * @param The generic <code>Object</code> class is used so that either an <code>AbstractResource</code> or <code>ArrayList<AbstractResource></code> can be passed 
	 * @return <code>String</code> complex String containing list of errors that were detected
	 */	
	public String validate(Object input){

		String METHODNAME = "validate OL1";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		@SuppressWarnings("unchecked")
		String errors = (input instanceof AbstractResource)? fieldValidator.validate((AbstractResource)input) : fieldValidator.validate((ArrayList<AbstractResource>)input);

		return errors;

	}
	
}

