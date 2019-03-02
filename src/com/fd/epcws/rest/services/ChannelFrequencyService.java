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
import com.fd.epcws.rest.beans.AbstractResource;
import com.fd.epcws.rest.beans.ChannelFrequency; 
import com.fd.epcws.util.PreferenceUtils;
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
import java.util.ArrayList;

@Path("/communicationchannelfrequencies")
@Api(value = "/communicationchannelfrequencies", description = "Channel Frequency operations", produces = MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChannelFrequencyService extends AbstractService{

	private final String CLASSNAME = "ChannelFrequencyService";

	/**
	 *  no param constructor is required older versions of annotation and web services
	 */
	public ChannelFrequencyService(){

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)    
	@Consumes(MediaType.APPLICATION_JSON)   
	@ApiOperation(value = "Creates a Channel Frequency resource", httpMethod = "PUT", notes = "Creates a new Channel Frequency resource.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_CREATE_RESOURCE_SUCCESS, message = Constants.SWAGGER_API_CREATE_RESOURCE_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),    		
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INPUT_VALIDATION_FAILED, message = Constants.SWAGGER_API_INPUT_VALIDATION_FAILED),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INSERT_DB_CALL_FAILED, message = Constants.SWAGGER_API_INSERT_DB_CALL_FAILED) })
			public Response create(@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope, 
					@ApiParam(value = "Information for the ChannelFrequency that will be created", required = true) ChannelFrequency channelFrequency_in) throws Exception {

		String METHODNAME = "create";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":envelope="+envelope+":channelFrequency="+channelFrequency_in));

		if(channelFrequency_in == null){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING, Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);    		
		}

		channelFrequency_in.setOperation(Constants.TYPE_OPERATION_CREATE);
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,channelFrequency_in));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"validate(channelFrequency_in)"));    	
		String errors = validate(channelFrequency_in);

		if(!StringUtils.isEmpty(errors)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_REQUEST_FAILED_VALIDATION));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_CREATE, errors, Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);
		}

		ReturnValue result = null;

		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"createChannelFrequency(channelFrequency_in)"));

			result  = delegate.createChannelFrequency(channelFrequency_in);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,result));
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_CREATE));    	
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_CREATE, e.getErrors(), Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);
		}

		if(result != null && result.getKey()!=Constants.RESPONSE_CODE_SUCCESS){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_UPDATE_RESP));
			return super.getControlResponse(Constants.RESPONSE_CODE_FAIL_CREATE, String.format(Constants.MESSAGE_CREATE_FAILED_INCLUDE,result.getKey(), result.getValue()), Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);            
		}

		String new_id = new Integer(channelFrequency_in.getChannelfrequencyid()).toString();
		URI uri = getResponseURI(Constants.URI_LABEL_CHANNELFREQUENCY, new_id);
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,uri));

		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(Constants.RESPONSE_CODE_CREATED, uri, Constants.JSON_DATA_MESSAGE_CHANNELFREQUENCY_CREATED,
				Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);
	}

	@GET
	@Loggable
	@Produces(MediaType.APPLICATION_JSON)    
	@ApiOperation(value = "Gets list of Channel Frequency resources.", httpMethod = "GET", notes = "Gets list of existing Channel Frequency resources.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_OK, message = Constants.SWAGGER_API_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_NO_ROWS_RETURNED, message = Constants.SWAGGER_API_DB_NO_ROWS_RETURNED),    		
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_CALL_FAILED, message = Constants.SWAGGER_API_DB_CALL_FAILED) })
			public Response list(@ApiParam(value = Constants.SWAGGER_API_SORT_DESCRIPTION, required = false) @QueryParam(Constants.QUERY_PARAM_SORT) String sort, 
					@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope,
					@ApiParam(value = Constants.SWAGGER_API_PRETTY_JSON_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_PRETTY) Boolean pretty) throws Exception{

		String METHODNAME = "list";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":sort="+sort+":envelope="+envelope));

		ArrayList<? extends AbstractResource> list = new ArrayList<ChannelFrequency>();

		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"findAllChannelFrequencies()"));
			list = delegate.findAllChannelFrequencies();
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,list));
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_LIST));  
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, e.getErrors(), Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);    		
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"validate(list)"));
		String errors = (list != null && !list.isEmpty())? validate(list) : Constants.MESSAGE_WARN_QUERY_RETURNED_NO_ROWS;

		if(!StringUtils.isEmpty(errors)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_LIST));    		
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, errors, Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);
		}

		if(!StringUtils.isEmpty(sort) && responseFacade.isSortAllowed()){
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"sort(list, sort)"));
			list = new PreferenceUtils().sort(list, sort);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,list));
		}

		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(Constants.JSON_DATA_LABEL_CHANNELFREQUENCY_LIST, list, envelope,pretty);    	
	}


	@Path("/{id}")
	@GET
	@Loggable
	@Produces(MediaType.APPLICATION_JSON)    
	@ApiOperation(value = "Get specific Channel Frequency", httpMethod = "GET", notes = "Retrieve detail information for a specified Channel Frequency resource.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_OK, message = Constants.SWAGGER_API_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),    		
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INPUT_DATA_INVALID, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_NO_ROWS_RETURNED, message = Constants.SWAGGER_API_DB_NO_ROWS_RETURNED),	    
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_CALL_FAILED, message = Constants.SWAGGER_API_DB_CALL_FAILED) })
			public Response find(@ApiParam(value = "ID of the Channel Frequency record that needs to be retreived", required = true) @PathParam(Constants.FIELD_ID) String id, 
					@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope,
					@ApiParam(value = Constants.SWAGGER_API_PRETTY_JSON_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_PRETTY) Boolean pretty) throws Exception{

		String METHODNAME = "find";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":id="+id+":envelope="+envelope));

		if(StringUtils.isEmpty(id)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELFREQID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELFREQID), Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);    		
		}

		ChannelFrequency channelFrequency = new ChannelFrequency();
		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"readChannelFrequency(code)"));
			channelFrequency  = delegate.readChannelFrequency(id);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,channelFrequency));
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_ITEM));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, e.getErrors(), Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"validate(channelFrequency)"));
		String errors = (channelFrequency != null)? validate(channelFrequency) : Constants.MESSAGE_WARN_QUERY_RETURNED_NO_ROWS;

		if(!StringUtils.isEmpty(errors)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_ITEM));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, errors, Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);
		}

		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));
		//LOGGER.fatal(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_TEST));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(channelFrequency, envelope,pretty);    	
	}

	@Path("/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)   
	@ApiOperation(value = "Deletes a channel frequency resource.", httpMethod = "DELETE", notes = "Deletes an existing Channel Frequency resource.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DELETE_RESOURCE_SUCCESS, message = Constants.SWAGGER_API_DELETE_RESOURCE_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),    		
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INVALID_PARAM, message = Constants.SWAGGER_API_INVALID_PARAM),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_CONNECT_FAIL, message = Constants.SWAGGER_API_DB_CONNECT_FAIL),    	
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_UPDATE_DATA_ERROR_FAILED, message = Constants.SWAGGER_API_DB_UPDATE_DATA_ERROR_FAILED) })
			public Response delete(@ApiParam(value = "ID of the Channel Frequency record that will be deleted", required = true) @PathParam(Constants.FIELD_ID) String id, 
					@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope) throws Exception{

		String METHODNAME = "delete";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":code="+id+":envelope="+envelope));

		if(StringUtils.isEmpty(id)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELFREQID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELFREQID), Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);    		
		}

		if(!NumberUtils.isDigits(id)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CHANNELFREQID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_DELETE, String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CHANNELFREQID), Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);    		
		}

		ReturnValue result = null;

		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"deleteChannelFrequency(code)"));
			result = delegate.deleteChannelFrequency(id);
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_DELETE));  
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, e.getErrors(), Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);
		}

		if(result != null && result.getKey()!=Constants.RESPONSE_CODE_SUCCESS){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_DELETE));
			return super.getControlResponse(Constants.RESPONSE_CODE_FAIL_DELETE, String.format(Constants.MESSAGE_DELETE_FAILED_INCLUDE,result.getKey(), result.getValue()), Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);
		}

		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(Constants.RESPONSE_CODE_NO_CONTENT, envelope);    	
	}

	@Path("/{id}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)    
	@Consumes(MediaType.APPLICATION_JSON)  
	@ApiOperation(value = "Updates a channel frequency resource", httpMethod = "POST", notes = "Updates an existing ChannelFrequency resource.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_UPDATE_RESOURCE_SUCCESS, message = Constants.SWAGGER_API_UPDATE_RESOURCE_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),    		
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_VALIDATION_FAILED),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INPUT_DATA_VALIDATION_FAILED, message = Constants.SWAGGER_API_INPUT_DATA_VALIDATION_FAILED),    	
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INSERT_DB_CALL_FAILED, message = Constants.SWAGGER_API_UPDATE_DB_CALL_FAILED) })
			public Response update(@ApiParam(value = "ID of the Channel Frequency record that will be updated", required = true) @PathParam(Constants.FIELD_ID) String id, 
					@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope, 
					@ApiParam(value = "Information for the ChannelFrequency that will be updated.", required = true) ChannelFrequency channelFrequency_in) throws Exception {

		String METHODNAME = "update";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":id="+id+":envelope="+envelope+":channelFrequency="+channelFrequency_in));

		if(StringUtils.isEmpty(id)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELFREQID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELFREQID), Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);
		}

		if(!NumberUtils.isDigits(id)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,"id")));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_UPDATE, String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,"id"), Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);    		
		}

		if(channelFrequency_in == null){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING, Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);    		
		}

		channelFrequency_in.setOperation(Constants.TYPE_OPERATION_UPDATE);
		channelFrequency_in.setChannelfrequencyid(Integer.parseInt(id));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,channelFrequency_in));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"validate(channelFrequency_in)"));
		String errors = validate(channelFrequency_in);

		if(!StringUtils.isEmpty(errors)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_UPDATE_REQ));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_REQUEST, errors, Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);            
		}

		ReturnValue result = null;
		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"update(channelFrequency_in)"));

			result = delegate.update(channelFrequency_in);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,result));    		
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_UPDATE_REQ));  
			return super.getControlResponse(Constants.RESPONSE_CODE_FAIL_UPDATE, e.getErrors(), Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);    		
		}

		if(result != null && result.getKey()!=Constants.RESPONSE_CODE_SUCCESS){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_UPDATE_RESP));
			return super.getControlResponse(Constants.RESPONSE_CODE_FAIL_UPDATE, String.format(Constants.MESSAGE_UPDATE_FAILED_INCLUDE,result.getKey(), result.getValue()), Constants.JSON_DATA_LABEL_CHANNELFREQUENCY, envelope);            
		}

		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(Constants.RESPONSE_CODE_NO_CONTENT, envelope);        
	}

	PreferenceDelegate delegate = super.getPreferenceDelagate();    
}

