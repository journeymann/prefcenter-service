package com.fd.epcws.exceptions.handlers;

/**
 * @author cgordon
 * 
 * GenericExceptionMapper class used to handle generic application and container exceptions and return a graceful message.
 */

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.Logger;

import com.fd.epcws.constants.Constants;
import com.fd.epcws.envelope.JsonEnvelope;
import com.fd.epcws.exceptions.AppException;
import com.fd.epcws.facade.EnvelopeFacade;
import com.fd.epcws.facade.ResponseCode;
import com.fd.epcws.facade.ResponseFacade;
import com.fd.epcws.util.helpers.LoggingHelper;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	private final String CLASSNAME = "GenericExceptionMapper";

	@Context
	HttpServletRequest request;

	private Logger LOGGER = LoggingHelper.getLogger(GenericExceptionMapper.class);
	private EnvelopeFacade envelopeFacade = new EnvelopeFacade();
	protected ResponseFacade responseFacade = new ResponseFacade();	

	/** This returns a <code>Response</code> object which is used by container to create response data  
	 * @param <code>Throwable</code> exception that initiated this behavior
	 * @return <code>Response</code> Response data object 
	 */	
	@Override
	public Response toResponse(Throwable ex) {

		String METHODNAME = "toResponse";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		LOGGER.info(String.format(Constants.MESSAGE_DEBUG_CHECKPOINT_INCLUDE," trace: "+this.stackTrace((Exception)ex)));

		boolean envelopeFlag = false;		
		Error error = getError(ex);
		Response response = this.getResponseObject(error, envelopeFlag);

		String detailMessage = Constants.STRING_BLANK;
		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"isEnvelopeAllowed()"));
			envelopeFlag = envelopeFacade.isEnvelopeAllowed();
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"buildErrorMessage(request)"));
			detailMessage = responseFacade.buildErrorMessage(request);
		}catch(AppException e){
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"(AppException) getResponseObject(getError(e), envelopeFlag)"));
			response = this.getResponseObject(getError(e), envelopeFlag);
			detailMessage = String.format(Constants.MESSAGE_GENERIC_DETAILS_INCLUDE, Constants.MESSAGE_ERROR_APP_BUILD_ERROR_MESG, e.getMessage());
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(), e, detailMessage));
			LOGGER.fatal(LoggingHelper.formatMessage(Constants.MESSAGE_ERROR_APP_BUILD_ERROR_MESG));
		}catch(Exception e){
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"(Exception) getResponseObject(getError(e), envelopeFlag)"));
			response = this.getResponseObject(getError(e), envelopeFlag);
			detailMessage = String.format(Constants.MESSAGE_GENERIC_DETAILS_INCLUDE, Constants.MESSAGE_ERROR_GENERAL_BUILD_ERROR_MESG, e.getMessage());
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_ERROR_DETAIL_INCLUDE, ex.getMessage(),ex.getLocalizedMessage(),String.format(Constants.MESSAGE_GENERIC_INCLUDE, detailMessage))));
			LOGGER.fatal(LoggingHelper.formatMessage(Constants.MESSAGE_ERROR_GENERAL_BUILD_ERROR_MESG));
		}

		try{
			LOGGER.info(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_GENERIC_INCLUDE, detailMessage)));
		}catch (Exception e){
			LOGGER.error(String.format(Constants.MESSAGE_ERROR_DETAIL_INCLUDE, e.getMessage(), e.getLocalizedMessage(), String.format(Constants.MESSAGE_GENERIC_INCLUDE, detailMessage)));
		}
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return response;
	}

	/** This returns a <code>Error</code> object which contains details to be included in <code>Response</code> object  
	 * @param <code>Throwable</code> exception that initiated this behavior
	 * @return <code>Error</code> Error data to be included as entity in <code>Response</code>
	 */	
	private Error getError(Throwable ex) {

		String METHODNAME = "getError";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Error error = null;

		if (ex instanceof WebApplicationException) {
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"(WebApplicationException) typeOf conditional"));
			int statusCode = ((WebApplicationException)ex).getResponse().getStatus();
			ResponseCode responseCode = ResponseFacade.getResponseCode(statusCode);
			String reason = responseCode.getDescription();
			String message = (ex.getMessage()==null || ex.getMessage().isEmpty())? responseCode.getComments() : ex.getMessage();

			error = new Error(statusCode, reason, message);
		}else if (ex instanceof AppException) {
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"(AppException) typeOf conditional"));
			int statusCode = ((AppException)ex).getResponseCode();
			String reason = ((AppException)ex).getException().getMessage();
			String localMessage = ((AppException)ex).getErrorMessage();
			String errors = ((AppException)ex).getErrors();

			localMessage += (errors != null && !errors.isEmpty())? Constants.STRING_ERROR_MESSAGE_DELIM + errors : Constants.MESSAGE_BLANK; 

			error = new Error(statusCode, reason,localMessage); 

		} else {
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"(else) typeOf conditional"));
			String localMessage = !StringUtils.isEmpty(ex.getLocalizedMessage())? ex.getLocalizedMessage() : Constants.MESSAGE_NON_SPECIFIED_EXCEPTION_MESG;
			int statusCode = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode();
			String reason = Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase();

			ResponseCode responseCode = ResponseFacade.getResponseCode(statusCode);
			localMessage += (responseCode!=null)? responseCode.getComments() : Constants.MESSAGE_NON_SPECIFIED_EXCEPTION_MESG;

			error = new Error(statusCode, reason, localMessage);
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return error;
	}

	/** This returns a <code>Response</code> object which contains details to be sent back to the service caller
	 * @param <code>Error</code> Error data to be included as entity in <code>Response</code>
	 * @return <code>Response</code> Response data object
	 */	
	private Response getGeneralErrorResponse(Error error) {
		String METHODNAME = "getGeneralErrorResponse";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return Response.status(error.getStatusCode()).entity(error).type(MediaType.APPLICATION_JSON).build();
	}

	/** This returns a <code>Response</code> object which contains envelope form of details to be sent back to the service caller
	 * @param <code>Error</code> Error data to be included as entity in <code>Response</code>
	 * @return <code>Response</code> Response data object
	 */	
	private Response getEnvelopeErrorResponse(Error error) {
		String METHODNAME = "getEnvelopeErrorResponse";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		JsonEnvelope envelope = envelopeFacade.getDataEnvelope(error);

		return Response.status(error.getStatusCode()).entity(envelope).type(MediaType.APPLICATION_JSON).build();

	}

	/** This returns a <code>Response</code> object which contains the constructed and well formed JSON response
	 * @param <code>Error</code> object with error details  
	 * @param <code>boolean</code> boolean flag indicating if envelope was requested 
	 * @return <code>Response</code> Response object constructs
	 */	
	private Response getResponseObject(Error error, boolean envelopeFlag){
		String METHODNAME = "getResponseObject";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		if(envelopeFacade.isEnvelope(request) && envelopeFlag){
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"getEnvelopeErrorResponse(error)"));
			return getEnvelopeErrorResponse(error);
		}else{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"getGeneralErrorResponse(error)"));
			return getGeneralErrorResponse(error);
		}

	}

	/** This returns a <code>String</code> representation of the exception stack trace
	 * @param <code>Exception</code> exception object  
	 * @return <code>String</code> stack trace
	 */    
	private String stackTrace(Exception e){
		String METHODNAME = "stackTrace";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		return sw.toString();
	}
}
