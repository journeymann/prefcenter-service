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
import com.fd.epcws.rest.beans.BrandChannel;
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
import java.util.Properties;

@Path("/contacts")
@Api(value = "/contacts", description = "Operations on Brand Channel resources", produces = MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class BrandChannelService extends AbstractService{

	private final String CLASSNAME = "BrandChannelService";

	/**
	 *  no param constructor is required older versions of annotation and web services
	 */
	public BrandChannelService(){

	}

	@Path("/{contactid}/communicationchannels/{channelcode}/brands/{brandcode}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)    
	@Consumes(MediaType.APPLICATION_JSON)    
	@ApiOperation(value = "Creates a Brand Communication Channel resource", httpMethod = "PUT", notes = "Creates a new Brand Communication Channel resource.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_CREATE_RESOURCE_SUCCESS, message = Constants.SWAGGER_API_CREATE_RESOURCE_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INPUT_VALIDATION_FAILED, message = Constants.SWAGGER_API_INPUT_VALIDATION_FAILED),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INPUT_DATA_INVALID, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INVALID_PARAM),    	
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INSERT_DB_CALL_FAILED, message = Constants.SWAGGER_API_INSERT_DB_CALL_FAILED) })
			public Response create(@ApiParam(value = "Contact id of Brand Communication Channel record that will be created", required = true) @PathParam(Constants.FIELD_CONTACTID) String contactid,
					@ApiParam(value = "Channel associated with the Brand Communication Channel record that will be created", required = true) @PathParam(Constants.FIELD_CHANNELCODE) String code,
					@ApiParam(value = "Brand associated with the Brand Communication Channel record that will be created", required = true) @PathParam(Constants.FIELD_BRANDCODE) String brand, 
					@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope, 
					@ApiParam(value = "Information for the Brand Communication Channel that will be created", required = true) BrandChannel brandChannel_in) throws Exception {

		String METHODNAME = "create";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":contactid="+contactid+":code="+code+":brand="+brand+":envelope="+envelope+":brandChannel="+brandChannel_in));

		if(StringUtils.isEmpty(contactid)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		if(!NumberUtils.isDigits(contactid)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_CREATE, String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		if(StringUtils.isEmpty(code)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);
		}

		if(StringUtils.isEmpty(brand)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);
		}

		if(brandChannel_in == null){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_CREATE, Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING, Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);
		}

		delegate = super.getPreferenceDelagate();    	

		brandChannel_in.setOperation(Constants.TYPE_OPERATION_CREATE);    	
		brandChannel_in.setContactid(Long.parseLong(contactid));
		brandChannel_in.setChannelcode(code);
		brandChannel_in.setBrandcode(brand);
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,brandChannel_in));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"validate(brandChannel_in)"));    	
		String errors = validate(brandChannel_in);

		if(!StringUtils.isEmpty(errors)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_REQUEST_FAILED_VALIDATION));    		
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_CREATE, errors, Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);
		}

		ReturnValue result = null;
		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"createBrandChannel(brandChannel_in)"));
			result  = delegate.createBrandChannel(brandChannel_in);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,result));
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_CREATE));    	
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_CREATE, e.getErrors(), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);
		}

		if(result != null && result.getKey()!=Constants.RESPONSE_CODE_SUCCESS){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_UPDATE_RESP));
			return super.getControlResponse(Constants.RESPONSE_CODE_FAIL_UPDATE, String.format(Constants.MESSAGE_UPDATE_FAILED_INCLUDE,result.getKey(), result.getValue()), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);            
		}

		URI uri = getResponseURI(Constants.URI_LABEL_BRANDCHANNEL, contactid, code, brand);
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,uri));
		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(Constants.RESPONSE_CODE_CREATED, uri, Constants.JSON_DATA_MESSAGE_BRANDCHANNEL_CREATED, Constants.JSON_DATA_LABEL_BRANDCHANNEL,envelope);    	
	}

	@Path("/{id}/communicationchannels/brands/{brandcode}")
	@GET
	@Loggable
	@Produces(MediaType.APPLICATION_JSON)    
	@ApiOperation(value = "Gets list of brand channel resources.", httpMethod = "GET", notes = "Gets list of existing Brand Communication Channel resources.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_OK, message = Constants.SWAGGER_API_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_NO_ROWS_RETURNED, message = Constants.SWAGGER_API_DB_NO_ROWS_RETURNED),        
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_CALL_FAILED, message = Constants.SWAGGER_API_DB_CALL_FAILED) })
			public Response listBrandsForContact(@ApiParam(value = "Contact ID of the brand channel resources to be retreived.", required = false) @PathParam(Constants.FIELD_ID) String id, 
					@ApiParam(value = "brandcode of the brand channel resources to be retreived.", required = false) @PathParam(Constants.FIELD_BRANDCODE) String code,
					@ApiParam(value = Constants.SWAGGER_API_SORT_DESCRIPTION, required = false) @QueryParam(Constants.QUERY_PARAM_SORT) String sort, 
					@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope,
					@ApiParam(value = Constants.SWAGGER_API_PRETTY_JSON_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_PRETTY) Boolean pretty) throws Exception{

		String METHODNAME = "listBrandsForContact";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":id="+id+":code="+code+":sort="+sort+":envelope="+envelope));

		if(StringUtils.isEmpty(id)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		if(!NumberUtils.isDigits(id)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_LIST, String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		if(StringUtils.isEmpty(code)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		delegate = super.getPreferenceDelagate();

		ArrayList<? extends AbstractResource> list = new ArrayList<BrandChannel>();

		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"findAllBrandChannelsForContact(id,code)"));
			list = delegate.findAllBrandChannelsForContact(id, code);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,list));
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_LIST));  
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, e.getErrors(), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"validate(list)"));
		String errors = (list != null && !list.isEmpty())? validate(list) : Constants.MESSAGE_WARN_QUERY_RETURNED_NO_ROWS;

		if(!StringUtils.isEmpty(errors)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_LIST));    		
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, errors, Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);
		}

		if(!StringUtils.isEmpty(sort) && responseFacade.isSortAllowed()){
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"sort(list,sort)"));
			list = new PreferenceUtils().sort(list, sort);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,list));
		}

		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(Constants.JSON_DATA_LABEL_BRANDCHANNEL_LIST, list,envelope, pretty);       	
	}

	@Path("/{id}/communicationchannels/brands")
	@GET
	@Loggable
	@Produces(MediaType.APPLICATION_JSON)    
	@ApiOperation(value = "Gets list of brand channel resources.", httpMethod = "GET", notes = "Gets list of existing Brand Communication Channel resources.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_OK, message = Constants.SWAGGER_API_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_NO_ROWS_RETURNED, message = Constants.SWAGGER_API_DB_NO_ROWS_RETURNED),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_CALL_FAILED, message = Constants.SWAGGER_API_DB_CALL_FAILED) })
			public Response list(@ApiParam(value = "Contact ID of the brand channel resources to be retreived.", required = false) @PathParam(Constants.FIELD_ID) String id,
					@ApiParam(value = Constants.SWAGGER_API_SORT_DESCRIPTION, required = false) @QueryParam(Constants.QUERY_PARAM_SORT) String sort, 
					@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope,
					@ApiParam(value = Constants.SWAGGER_API_PRETTY_JSON_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_PRETTY) Boolean pretty) throws Exception{

		String METHODNAME = "list";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":id="+id+":sort="+sort+":envelope="+envelope));

		if(StringUtils.isEmpty(id)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);
		}

		if(!NumberUtils.isDigits(id)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_LIST, String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		delegate = super.getPreferenceDelagate();

		ArrayList<? extends AbstractResource> list = new ArrayList<BrandChannel>();

		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"findAllBrandChannels(id)"));
			list = delegate.findAllBrandChannels(id);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,list));
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_LIST));  
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, e.getErrors(), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"validate(list)"));
		String errors = (list != null && !list.isEmpty())? validate(list) : Constants.MESSAGE_WARN_QUERY_RETURNED_NO_ROWS;

		if(!StringUtils.isEmpty(errors)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_LIST));    		
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, errors, Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);
		}

		if(!StringUtils.isEmpty(sort) && responseFacade.isSortAllowed()){
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"sort(list, sort)"));
			list = new PreferenceUtils().sort(list, sort);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,list));
		}

		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(Constants.JSON_DATA_LABEL_BRANDCHANNEL_LIST, list,envelope, pretty);        
	}

	@Path("/{id}/communicationchannels/{channelcode}/brands/{brandcode}")
	@GET
	@Loggable
	@Produces(MediaType.APPLICATION_JSON)    
	@ApiOperation(value = "Get specific Brand Communication Channel", httpMethod = "GET", notes = "Retreive detail information for a specified Brand Communication Channel resource.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_OK, message = Constants.SWAGGER_API_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_NO_ROWS_RETURNED, message = Constants.SWAGGER_API_DB_NO_ROWS_RETURNED),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_CALL_FAILED, message = Constants.SWAGGER_API_DB_CALL_FAILED) })
			public Response find(@ApiParam(value = "Contact ID of the brand channel resources to be retreived.", required = false) @PathParam(Constants.FIELD_ID) String id,
					@ApiParam(value = "channelcode of Brand Communication Channel record that needs to be retreived", required = true) @PathParam(Constants.FIELD_CHANNELCODE) String channelcode, 
					@ApiParam(value = "brandcode of Brand Communication Channel record that needs to be retreived", required = true) @PathParam(Constants.FIELD_BRANDCODE) String brandcode,    		
					@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope,
					@ApiParam(value = Constants.SWAGGER_API_PRETTY_JSON_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_PRETTY) Boolean pretty) throws Exception{

		String METHODNAME = "find";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":id="+id+":code="+channelcode+":brand="+brandcode+":envelope="+envelope));

		if(StringUtils.isEmpty(id)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		if(!NumberUtils.isDigits(id)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_ITEM, String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		if(StringUtils.isEmpty(channelcode)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}
		if(StringUtils.isEmpty(brandcode)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		delegate = super.getPreferenceDelagate();    	

		Properties props = new Properties();
		props.put(Constants.FIELD_CONTACTID, id);
		props.put(Constants.FIELD_CHANNEL, channelcode);    	
		props.put(Constants.FIELD_BRAND, brandcode);    	

		BrandChannel brandChannel = new BrandChannel();
		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"readBrandChannel(props)"));
			brandChannel = delegate.readBrandChannel(props);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,brandChannel));
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_ITEM));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, e.getErrors(), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"validate(brandChannel)"));
		String errors = (brandChannel != null)? validate(brandChannel) : Constants.MESSAGE_WARN_QUERY_RETURNED_NO_ROWS;

		if(!StringUtils.isEmpty(errors)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_ITEM));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, errors, Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);
		}

		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));
		//LOGGER.fatal(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_TEST));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(brandChannel,envelope, pretty);
	}

	@Path("/{contactid}/communicationchannels/{channelcode}/brands/{brandcode}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)  
	@ApiOperation(value = "Deletes a brand channel resource.", httpMethod = "DELETE", notes = "Deletes an existing Brand Communication Channel resource.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DELETE_RESOURCE_SUCCESS, message = Constants.SWAGGER_API_DELETE_RESOURCE_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),    		
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INVALID_PARAM, message = Constants.SWAGGER_API_INVALID_PARAM),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_CONNECT_FAIL, message = Constants.SWAGGER_API_DB_CONNECT_FAIL),    	
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_DB_UPDATE_DATA_ERROR_FAILED, message = Constants.SWAGGER_API_DB_UPDATE_DATA_ERROR_FAILED) })
			public Response delete(@ApiParam(value = "Contact ID of the brand channel resources to be deleted", required = false) @PathParam(Constants.FIELD_CONTACTID) String contactid,
					@ApiParam(value = "channelcode of Brand Communication Channel record that will be deleted", required = true) @PathParam(Constants.FIELD_CHANNELCODE) String channelcode,
					@ApiParam(value = "brandcode of Brand Communication Channel record that will be deleted", required = true) @PathParam(Constants.FIELD_BRANDCODE) String brandcode, 
					@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope,
					@ApiParam(value = "Information for the Brand Communication Channel that will be deleted", required = true) BrandChannel brandChannel_in) throws Exception{

		String METHODNAME = "delete";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":id="+contactid+":channelcode="+channelcode+":brandcode="+brandcode+":envelope="+envelope));

		if(StringUtils.isEmpty(contactid)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		if(!NumberUtils.isDigits(contactid)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_DELETE, String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		if(StringUtils.isEmpty(channelcode)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		if(StringUtils.isEmpty(brandcode)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		if(brandChannel_in == null){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_DELETE, Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING, Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);
		}

		delegate = super.getPreferenceDelagate();    	

		brandChannel_in.setOperation(Constants.TYPE_OPERATION_DELETE);
		brandChannel_in.setContactid(Long.parseLong(contactid));
		brandChannel_in.setChannelcode(channelcode);
		brandChannel_in.setBrandcode(brandcode);
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,brandChannel_in));    	

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"validate(brandChannel_in)"));
		String errors = validate(brandChannel_in);

		if(!StringUtils.isEmpty(errors)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_UPDATE_REQ));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_REQUEST, errors, Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);            
		}

		ReturnValue result = null;

		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"deleteBrandChannel(props)"));
			result = delegate.deleteBrandChannel(brandChannel_in);
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_DELETE));  
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_RESPONSE, e.getErrors(), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);
		}

		if(result != null && result.getKey()!=Constants.RESPONSE_CODE_SUCCESS){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_DELETE));
			return super.getControlResponse(Constants.RESPONSE_CODE_FAIL_DELETE, String.format(Constants.MESSAGE_DELETE_FAILED_INCLUDE,result.getKey(), result.getValue()), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);
		}

		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(Constants.RESPONSE_CODE_NO_CONTENT, envelope);
	}

	@Path("/{id}/communicationchannels/{channelcode}/brands/{brandcode}/brandcommunications")
	@POST
	@Produces(MediaType.APPLICATION_JSON)  
	@Consumes(MediaType.APPLICATION_JSON)  
	@ApiOperation(value = "Updates a brand channel resource", httpMethod = "POST", notes = "Updates an existing Brand Communication Channel resource.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_UPDATE_RESOURCE_SUCCESS, message = Constants.SWAGGER_API_UPDATE_RESOURCE_SUCCESS),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_MISSING_PARAM, message = Constants.SWAGGER_API_INPUT_DATA_INVALID),    		
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INPUT_VALIDATION_FAILED, message = Constants.SWAGGER_API_INPUT_VALIDATION_FAILED),
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INPUT_DATA_VALIDATION_FAILED, message = Constants.SWAGGER_API_INPUT_DATA_VALIDATION_FAILED),    	
			@ApiResponse(code = Constants.SWAGGER_API_RESPONSE_INSERT_DB_CALL_FAILED, message = Constants.SWAGGER_API_UPDATE_DB_CALL_FAILED) })
			public Response update(@ApiParam(value = "Contact ID of the brand channel resources to be updated.", required = false) @PathParam(Constants.FIELD_ID) String id,
					@ApiParam(value = "channelcode of Brand Communication Channel record that will be updated", required = true) @PathParam(Constants.FIELD_CHANNELCODE) String channel,
					@ApiParam(value = "brandcode of Brand Communication Channel record that will be updated", required = true) @PathParam(Constants.FIELD_BRANDCODE) String brand,
					@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope, 
					@ApiParam(value = "Information for the Brand Communication Channel that will be updated.", required = true) BrandChannel brandChannel_in) throws Exception {

		String METHODNAME = "update";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":contactid="+id+":channel="+channel+":brand="+brand+":envelope="+envelope+":brandChannel="+brandChannel_in));

		if(StringUtils.isEmpty(id)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		if(!NumberUtils.isDigits(id)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID)));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_UPDATE, String.format(Constants.MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE,Constants.FIELD_CONTACTID), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		if(StringUtils.isEmpty(channel)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);
		}
		if(StringUtils.isEmpty(brand)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE)));
			return super.getControlResponse(Constants.RESPONSE_CODE_MISSING_PARAM, String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}
		if(brandChannel_in == null){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_UPDATE, Constants.MESSAGE_REQUIRED_REQUEST_BODY_MISSING, Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		delegate = super.getPreferenceDelagate();

		brandChannel_in.setOperation(Constants.TYPE_OPERATION_UPDATE);    	
		brandChannel_in.setContactid(Long.parseLong(id));
		brandChannel_in.setChannelcode(channel);
		brandChannel_in.setBrandcode(brand);
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,brandChannel_in));    	

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"validate(brandChannel_in)"));
		String errors = validate(brandChannel_in);

		if(!StringUtils.isEmpty(errors)){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_UPDATE_REQ));
			return super.getControlResponse(Constants.RESPONSE_CODE_BAD_DATA_IN_REQUEST, errors, Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);            
		}

		ReturnValue result = null;

		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"updateBrandChannel(brandChannel_in)"));
			result = delegate.updateBrandChannel(brandChannel_in);
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,result));
		}catch(AppException e){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, Constants.MESSAGE_SERVICE_FAIL_UPDATE_REQ));  
			return super.getControlResponse(Constants.RESPONSE_CODE_FAIL_UPDATE, e.getErrors(), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);    		
		}

		if(result != null && result.getKey()!=Constants.RESPONSE_CODE_SUCCESS){
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SERVICE_FAIL_UPDATE_RESP));
			return super.getControlResponse(Constants.RESPONSE_CODE_FAIL_UPDATE, String.format(Constants.MESSAGE_UPDATE_FAILED_INCLUDE,result.getKey(), result.getValue()), Constants.JSON_DATA_LABEL_BRANDCHANNEL, envelope);            
		}

		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_SUCCESS));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(Constants.RESPONSE_CODE_NO_CONTENT,envelope);        
	}

	PreferenceDelegate delegate = super.getPreferenceDelagate();    
}

