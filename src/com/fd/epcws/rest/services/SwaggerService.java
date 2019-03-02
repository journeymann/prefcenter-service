/**
 * @author cgordon
 *
 */
package com.fd.epcws.rest.services;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.fd.epcws.constants.Constants;
import com.fd.epcws.exceptions.AppException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Path("/swagger")
@Produces(MediaType.APPLICATION_JSON)
public class SwaggerService extends AbstractService {

	@Context
	HttpServletRequest request;

	private final String CLASSNAME = "SwaggerService";
	private final int ROOT_REF_TYPE = 1;
	private final int PATH_REF_TYPE = 2;
	private final String ROOT_REF_REPLACE_STRING = "swagger/swagger.json";
	private final String PATH_REF_REPLACE_STRING = "swagger";


	/**
	 *  no param constructor is required older versions of annotation and web services
	 */
	public SwaggerService(){

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getSwaggerJSONDefault() throws AppException{

		String METHODNAME = "getSwaggerJSON OL1";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		String output = this.getSwaggerData(PATH_REF_TYPE);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return output;
	}    

	@GET
	@Path("/{path}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSwaggerJSON(@PathParam(Constants.FIELD_PATH) String path) throws AppException{

		String METHODNAME = "getSwaggerJSON OL2";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		String output = this.getSwaggerData(PATH_REF_TYPE);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return output;
	}    

	@GET
	@Path("/swagger.json")
	@Produces(MediaType.APPLICATION_JSON)
	public String getSwaggerJSON() throws AppException{

		String METHODNAME = "getSwaggerJSON OL3";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		String output = this.getSwaggerData(ROOT_REF_TYPE);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return output;
	}    

	/**
	 * @param <code>String</code> current url location of service call. rewrite to fetch data from the '/api-docs' equivalent 
	 * @return <code>String</code> requested json data 
	 * @throws <code>AppException</code> generic AppException
	 * included or not
	 */
	private String getSwaggerData(int type) throws AppException {
		String METHODNAME = "getSwaggerData";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		String location = request.getRequestURL().toString();
		location = (location != null)? location.toLowerCase() : location;

		switch(type){
		case ROOT_REF_TYPE:
			location = location.replace(ROOT_REF_REPLACE_STRING, Constants.SWAGGER_API_DEFAULT_LOCATION);
			break;

		case PATH_REF_TYPE:
			location = location.replace(PATH_REF_REPLACE_STRING, Constants.SWAGGER_API_DEFAULT_LOCATION);
			break;
		}

		HttpURLConnection conn = null;
		String output = Constants.STRING_BLANK;
		try{
			URL url = new URL(location);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", MediaType.APPLICATION_JSON);	    
			if (conn.getResponseCode() != Constants.RESPONSE_CODE_OK) {
				throw new RuntimeException(String.format(Constants.MESSAGE_SWAGGER_RUNTIME_EXCEPTION_INCLUDE, conn.getResponseCode()));
			}
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));			

			String line = Constants.STRING_BLANK;

			while ((line = br.readLine()) != null) {
				output += line;
			}
		} catch (MalformedURLException e) {
			throw new AppException(e, e.getMessage());
		} catch (IOException e) {			
			throw new AppException(e, e.getMessage());			
		} finally{
			conn.disconnect();
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));			
		return output;
	}		
}

