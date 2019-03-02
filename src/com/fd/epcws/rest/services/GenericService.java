/**
 * @author cgordon
 *
 */
package com.fd.epcws.rest.services;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fd.epcws.constants.Constants;
import com.fd.epcws.db.connection.DBSqlConnection;
import com.fd.epcws.exceptions.AppException;
import com.fd.epcws.util.helpers.LoggingHelper;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import java.util.Date;

@Path("/tools")
@Api(value = "/tools", description = "API general convenience utilities and operations", produces = MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class GenericService extends AbstractService {

	private final String CLASSNAME = "GenericService";

	@Context
	javax.ws.rs.core.Application app;	

	/**
	 *  no param constructor is required older versions of annotation and web services
	 */
	public GenericService(){

	}

	@GET
	@Path("/ping")
	@Produces(MediaType.APPLICATION_JSON)   
	@ApiOperation(value = "Web Service Health Ping", httpMethod = "GET", notes = "Performs a health check on the web service as well as the database connection.", response = Response.class)
	@ApiResponses(value = { @ApiResponse(code = Constants.SWAGGER_API_RESPONSE_OK, message = Constants.SWAGGER_API_PING_SUCCESS)})
	public Response getGeneralServerPing(@ApiParam(value = Constants.SWAGGER_API_ENVELOPE_DESCRIPTION, required = false) @DefaultValue("false") @QueryParam(Constants.QUERY_PARAM_USE_ENVELOPE) Boolean envelope) throws Exception{

		String METHODNAME = "getGeneralServerPing";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,":envelope="+envelope));

		boolean connect = false;

		DBSqlConnection conn =  null;

		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"new DBSqlConnection()"));
			conn =  new DBSqlConnection();
			LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_OPEN_DB_CONNECTION_SUCCESS));

			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"db_ping()"));
			conn.db_ping() ;
			connect = true;
		}catch(AppException e){
			connect = false;
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),e, String.format(Constants.MESSAGE_ACCESS_DB_FAIL_INCLUDE,e.getMessage())));
		}

		String val =  String.format(Constants.MESSAGE_PING_INCLUDE, new Date().toString(), connect);

		if(connect)
			LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_ACCESS_DB_SUCCESS));    		
		else
			LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_OPEN_DB_CONNECTION_FAIL));    		

		LOGGER.info(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_PING_SUCCESS));    	

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return getControlResponse(Constants.RESPONSE_CODE_OK, val, Constants.MESSAGE_PING_SUCCESS, envelope);        
	}
}

