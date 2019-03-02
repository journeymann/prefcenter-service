/**
 * @author cgordon
 *
 */
package com.fd.epcws.rest.services;

import com.fd.epcws.constants.Constants;
import com.fd.epcws.db.dao.valueobjects.ReturnValue;
import com.fd.epcws.delegates.PreferenceDelegate;
import com.fd.epcws.exceptions.AppException;
import com.fd.epcws.performance.Loggable;
import com.fd.epcws.rest.beans.Contact;
import com.fd.epcws.util.helpers.LoggingHelper;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import java.net.URI;

@Path("/contact")
@Api(value = "/contact", description = "API do operations on Contact resources", produces = MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContactService extends AbstractService{

	private final String CLASSNAME = "ContactService";

	/**
	 *  no param constructor is required older versions of annotation and web services
	 */
	public ContactService(){

	}

	@Path("/{contactid}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)    
	@Consumes(MediaType.APPLICATION_JSON)    
	@ApiOperation(value = "Creates a Contact resource", httpMethod = "PUT", notes = "Creates a new Contact resource.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_CREATE_RESOURCE_SUCCESS, message = Constants.SWAGGER_API_CREATE_RESOURCE_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),    		
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INPUT_VALIDATION_FAILED, message = Constants.SWAGGER_API_INPUT_VALIDATION_FAILED),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INSERT_DB_CALL_FAILED, message = Constants.SWAGGER_API_INSERT_DB_CALL_FAILED) })
			public Response create(@ApiParam(value = "contactid of Contact record that will be created", required = true) @PathParam(Constants.FIELD_CONTACTID) String contactid, 
					@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope, 
					@ApiParam(value = "Information for the Contact that will be created", required = true) Contact contact_in) throws Exception {

		String METHODNAME = "create";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":contactid="+contactid+":envelope="+envelope+":contact="+contact_in));

		if(StringUtils.isEmpty(contactid)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_CONTACT, envelope);    		
		}

		if(!NumberUtils.isDigits(contactid)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_CREATE, String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_CONTACT, envelope);    		
		}

		if(contact_in == null){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING, Constants.JSON_DATA_LABEL_CONTACT, envelope);    		
		}

		contact_in.setOperation(Constants.TYPE_OPERATION_CREATE);
		contact_in.setContactid(Long.parseLong(contactid));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,contact_in));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"validate(contact_in)"));
		String errors = validate(contact_in);

		if(!StringUtils.isEmpty(errors)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_REQUEST_FAILED_VALIDATION));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_CREATE, errors, Constants.JSON_DATA_LABEL_CONTACT, envelope);
		}

		ReturnValue result = null;

		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"createContact(contact_in)"));

			result  = delegate.createContact(contact_in);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,result));
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_CREATE));    	
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_CREATE, e.getErrors(), Constants.JSON_DATA_LABEL_CONTACT, envelope);
		}

		if(result != null && result.getKey()!=Constants.RESPONSE_CODE_SUCCESS){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_UPDATE_RESP));
			return super.getControlResponse(Constants.RESPONSE_CODE_FAIL_CREATE, String.format(Constants.MESSAGE_CREATE_FAILED_INCLUDE,result.getKey(), result.getValue()), Constants.JSON_DATA_LABEL_CONTACT, envelope);            
		}

		URI uri = getResponseURI(Constants.URI_LABEL_CONTACT, contactid);
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,uri));
		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(Constants.RESPONSE_CODE_CREATED, uri, Constants.JSON_DATA_MESSAGE_CONTACT_CREATED, Constants.JSON_DATA_LABEL_CONTACT, envelope);
	}

	@Path("/{contactid}")
	@GET
	@Loggable
	@Produces(MediaType.APPLICATION_JSON)    
	@ApiOperation(value = "Get specific Contact", httpMethod = "GET", notes = "Retreive detail information for a specified Contact resource.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_OK, message = Constants.SWAGGER_API_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_DATA_INVALID), 
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_NO_ROWS_RETURNED, message = Constants.SWAGGER_API_DB_NO_ROWS_RETURNED),        
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_CALL_FAILED, message = Constants.SWAGGER_API_DB_CALL_FAILED) })
			public Response find(@ApiParam(value = "contactid of Contact record that needs to be retreived", required = true) @PathParam(Constants.FIELD_CONTACTID) String contactid, 
					@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope,
					@ApiParam(value = Constants.SWAGGER_API_PRETTY_JSON_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_PRETTY) Boolean pretty) throws Exception{

		String METHODNAME = "find";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":contactid="+contactid+":envelope="+envelope));

		if(StringUtils.isEmpty(contactid)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_CONTACT, envelope);    		
		}

		if(!NumberUtils.isDigits(contactid)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_ITEM, String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_CONTACT, envelope);    		
		}

		Contact contact = new Contact();
		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"readContact(contactid)"));
			contact = delegate.readContact(contactid);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,contact));
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_ITEM));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, e.getErrors(), Constants.JSON_DATA_LABEL_CONTACT, envelope);
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"validate(contact)"));
		String errors = (contact != null)? validate(contact) : Constants.MESSAGE_WARN_QUERY_RETURNED_NO_ROWS;

		if(!StringUtils.isEmpty(errors)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_ITEM));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, errors, Constants.JSON_DATA_LABEL_CONTACT, envelope);
		}

		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));
		//LOGGER.fatal(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_TEST));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(contact, envelope,pretty);    	
	}

	@Path("/{contactid}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)  
	@ApiOperation(value = "Deletes a contact resource.", httpMethod = "DELETE", notes = "Deletes an existing Contact resource.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DELETE_RESOURCE_SUCCESS, message = Constants.SWAGGER_API_DELETE_RESOURCE_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),    		
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INVALID_PARAM, message = Constants.SWAGGER_API_INVALID_PARAM),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_CONNECT_FAIL, message = Constants.SWAGGER_API_DB_CONNECT_FAIL),    	
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_UPDATE_DATA_ERROR_FAILED, message = Constants.SWAGGER_API_DB_UPDATE_DATA_ERROR_FAILED) })
			public Response delete(@ApiParam(value = "contactid of Contact record that will be deleted", required = true) @PathParam(Constants.FIELD_CONTACTID) String contactid, 
					@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope, 
					@ApiParam(value = "Information for the Contact that will be deleted.", required = true) Contact contact_in) throws Exception {

		String METHODNAME = "delete";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":contactid="+contactid+":envelope="+envelope));

		if(StringUtils.isEmpty(contactid)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_CONTACT, envelope);    		
		}

		if(!NumberUtils.isDigits(contactid)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_DELETE, String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_CONTACT, envelope);    		
		}

		if(contact_in == null){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING, Constants.JSON_DATA_LABEL_CONTACT, envelope);    		
		}
		contact_in.setOperation(Constants.TYPE_OPERATION_DELETE);
		contact_in.setContactid(Long.parseLong(contactid));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,contact_in));

		ReturnValue result = null;

		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"deleteContact(contactid)"));

			result = delegate.deleteContact(contact_in);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,result));
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_DELETE));  
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, e.getErrors(), Constants.JSON_DATA_LABEL_CONTACT, envelope);
		}

		if(result != null && result.getKey()!=Constants.RESPONSE_CODE_SUCCESS){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_DELETE));
			return super.getControlResponse(Constants.RESPONSE_CODE_FAIL_DELETE, String.format(Constants.MESSAGE_DELETE_FAILED_INCLUDE,result.getKey(), result.getValue()), Constants.JSON_DATA_LABEL_CONTACT, envelope);
		}

		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(Constants.RESPONSE_CODE_NO_CONTENT, envelope);    	
	}

	@Path("/{contactid}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)    
	@Consumes(MediaType.APPLICATION_JSON)  
	@ApiOperation(value = "Updates a contact resource", httpMethod = "POST", notes = "Updates an existing Contact resource.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_UPDATE_RESOURCE_SUCCESS, message = Constants.SWAGGER_API_UPDATE_RESOURCE_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),    		
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INPUT_VALIDATION_FAILED, message = Constants.SWAGGER_API_INPUT_VALIDATION_FAILED),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INPUT_DATA_VALIDATION_FAILED, message = Constants.SWAGGER_API_INPUT_DATA_VALIDATION_FAILED),    	
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INSERT_DB_CALL_FAILED, message = Constants.SWAGGER_API_UPDATE_DB_CALL_FAILED) })
			public Response update(@ApiParam(value = "contactid of Contact record that will be updated", required = true) @PathParam(Constants.FIELD_CONTACTID) String contactid, 
					@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope, 
					@ApiParam(value = "Information for the Contact that will be updated.", required = true) Contact contact_in) throws Exception {

		String METHODNAME = "update";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":contactid="+contactid+":envelope="+envelope+":contact="+contact_in));

		if(StringUtils.isEmpty(contactid)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_CONTACT, envelope);    		
		}

		if(!NumberUtils.isDigits(contactid)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE, Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_UPDATE, String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_CONTACT, envelope);    		
		}

		if(contact_in == null){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING, Constants.JSON_DATA_LABEL_CONTACT, envelope);    		
		}

		contact_in.setOperation(Constants.TYPE_OPERATION_UPDATE);
		contact_in.setContactid(Long.parseLong(contactid));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,contact_in));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"update(contact_in)"));
		String errors = validate(contact_in);

		if(!StringUtils.isEmpty(errors)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_UPDATE_REQ));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_REQUEST, errors, Constants.JSON_DATA_LABEL_CONTACT, envelope);            
		}

		ReturnValue result = null;
		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"update(contact_in)"));

			result = delegate.update(contact_in);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,result));
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_UPDATE_REQ));  
			return super.getControlResponse(Constants.RESPONSE_CODE_FAIL_UPDATE, e.getErrors(), Constants.JSON_DATA_LABEL_CONTACT, envelope);    		
		}

		if(result != null && result.getKey()!=Constants.RESPONSE_CODE_SUCCESS){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_UPDATE_RESP));
			return super.getControlResponse(Constants.RESPONSE_CODE_FAIL_UPDATE, String.format(Constants.MESSAGE_UPDATE_FAILED_INCLUDE,result.getKey(), result.getValue()), Constants.JSON_DATA_LABEL_CONTACT, envelope);            
		}

		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(Constants.RESPONSE_CODE_NO_CONTENT, envelope);        
	}

	PreferenceDelegate delegate = super.getPreferenceDelagate();    
}

