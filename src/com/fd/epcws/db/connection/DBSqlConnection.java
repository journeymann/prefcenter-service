/**
 * 
 */
package com.fd.epcws.db.connection;

import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.SQLException; 
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import org.apache.logging.log4j.Logger;

import com.fd.epcws.constants.Constants;
import com.fd.epcws.db.sql.Columns;
import com.fd.epcws.db.sql.Statements;
import com.fd.epcws.db.dao.valueobjects.ReturnValue;
import com.fd.epcws.exceptions.AppException;
import com.fd.epcws.exceptions.ConnectionException;
import com.fd.epcws.exceptions.DatabaseException;
import com.fd.epcws.exceptions.GeneralException;
//import com.fd.epcws.exceptions.NoRecordsFoundDataException;
import com.fd.epcws.performance.Loggable;
import com.fd.epcws.rest.beans.AbstractResource;
import com.fd.epcws.util.PreferenceUtils;
import com.fd.epcws.util.helpers.DatabaseHelper;
import com.fd.epcws.util.helpers.LoggingHelper;
/**
 * @author cgordon
 *
 */
public class DBSqlConnection implements ConnectionInterface{

	private final String CLASSNAME = "DBSqlConnection";	
	private Logger LOGGER = LoggingHelper.getLogger(DBSqlConnection.class); 

	/**
	 * Static initializer for database connection driver class forName
	 */
	static {
		final Logger LOGGER = LoggingHelper.getLogger(DBSqlConnection.class);
		String CLASSNAME = "DBSqlConnection";
		String METHODNAME = "static initializer";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		PreferenceUtils util = new PreferenceUtils();
		
		try{
			
			String dbType = (String)util.getConsoleProperty(Constants.LOOKUP_PREF_DB_TYPE);			
			if (Constants.databaseTypes.contains(dbType)){
				Class.forName(Constants.databaseDriversMap.get(dbType)).newInstance();
			}else{
				LOGGER.fatal(LoggingHelper.formatMessage(Constants.MESSAGE_DATABASE_NOT_SUPPORTED));
			}
			
		}catch(ClassNotFoundException ex) {
			LOGGER.error(LoggingHelper.formatMessage(DBSqlConnection.class, new ConnectionException(ex, Constants.MESSAGE_OPEN_DB_DRIVER_CLASS_NOT_FOUND), Constants.MESSAGE_OPEN_DB_DRIVER_CLASS_NOT_FOUND));
		} catch (Exception e){
			LOGGER.error(LoggingHelper.formatMessage(DBSqlConnection.class, new ConnectionException(e, Constants.MESSAGE_CONNECT_DB_FAIL), Constants.MESSAGE_CONNECT_DB_FAIL));
		}
		
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
	}	
	
	
	/** This method opens/creates and returns a database connection object 
	 * @return <code>Connection</code> database connection class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	@Loggable
	public Connection getConnection () throws AppException{
		String METHODNAME = "getConnection";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		Connection conn = null;

		try{
			LOGGER.info(Constants.MESSAGE_OPEN_DB_CONNECTION_ATTEMPT);			

			PreferenceUtils util = new PreferenceUtils();
			
			DataSource dataSource = (DataSource)util.getConsoleProperty(Constants.LOOKUP_PREF_DB_CONN_URL);
			LOGGER.debug(String.format(Constants.MESSAGE_OPEN_DB_CONNECTION_ATTEMPT));

			conn = dataSource.getConnection();

			StringBuffer buff = new StringBuffer("client: ");
			buff.append(conn.getClientInfo().toString());
			buff.append("connection: ");
			buff.append(conn.getMetaData().toString());

			LOGGER.debug(String.format(Constants.MESSAGE_OPEN_DB_CONNECTION_ATTEMPT_INCLUDE,buff));
			LOGGER.info(Constants.MESSAGE_OPEN_DB_CONNECTION_SUCCESS);
		}catch(SQLException ex) {
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_OPEN_DB_CONNECTION_ATTEMPT_INCLUDE, ex.getMessage())));
			throw new ConnectionException(ex.getCause(), String.format(Constants.MESSAGE_GENERIC_DETAILS_INCLUDE,"SQL exception encountered while opening database connection!", ex.getMessage()));
		}catch(Exception ex) {
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_OPEN_DB_CONNECTION_ATTEMPT_INCLUDE, ex.getMessage())));
			throw new ConnectionException(ex.getCause(), String.format(Constants.MESSAGE_GENERIC_INCLUDE,ex.getMessage()));				
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return conn;
	}

	/** This method finds a AbstractResource class/sub class type based 
	 * @param The type of return type expected. Also used to determine SQL code used
	 *        <code>Class</code>.
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Properties</code>.             
	 * @return <code>AbstractResource</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	@Loggable
	public AbstractResource find(Class<?> klass, Properties props) throws AppException{

		String METHODNAME = "find";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));			

		int type = Constants.TYPE_UNKNOWN_RESOURCE;
		AbstractResource retObj = null;

		try{
			AbstractResource model = (AbstractResource)klass.newInstance();
			type = model.getResourceType();
		}catch(InstantiationException e){
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), Constants.MESSAGE_GENERAL_SQL_RESULTSET_INSTANTIATE);			
		}catch(IllegalAccessException e){
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), Constants.MESSAGE_GENERAL_SQL_RESULTSET_ILLEGALACCESS);			
		}

		Object obj = this.executeQuery(type, props);

		if(obj instanceof AbstractResource){
			retObj = obj != null? (AbstractResource)obj : null;	
		}else{
			retObj = obj != null && !((ArrayList<?>)obj).isEmpty()? (AbstractResource)((ArrayList<?>)obj).get(Constants.INT_ZERO) : null;
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,retObj));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));			
		return retObj; 
	}

	/** This method finds a list of AbstractResource class/sub class type based 
	 * @param The type of return type expected. Also used to determine SQL code used
	 *        <code>Class</code>.
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Properties</code>.             
	 * @return <code>ArrayList<AbstractResource></code> ArrayList of AbstractResource based objects. 
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	@Loggable
	public ArrayList<? extends AbstractResource> findAll(Class<?> klass, Properties props) throws AppException{

		String METHODNAME = "findAll";			
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));				

		ArrayList<AbstractResource> retList = new ArrayList<AbstractResource>();
		int type = Constants.TYPE_UNKNOWN_RESOURCE;

		try{
			AbstractResource model = (AbstractResource)klass.newInstance();
			type = model.getResourceType();
		}catch(InstantiationException e){
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), Constants.MESSAGE_GENERAL_SQL_RESULTSET_INSTANTIATE);			
		}catch(IllegalAccessException e){
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), Constants.MESSAGE_GENERAL_SQL_RESULTSET_ILLEGALACCESS);			
		}

		Object obj = this.executeQuery(type, props);			

		if(obj instanceof AbstractResource){
			if(obj != null) retList.add(((AbstractResource)obj));	
		}else{
			@SuppressWarnings({"unchecked"})
			ArrayList<AbstractResource> list = (ArrayList<AbstractResource>)obj;
			retList = list;				
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,retList));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return retList; 
	}

	/** This method updates a AbstractResource class/sub class type based 
	 * @param The type of return type expected. Also used to determine SQL code used
	 *        <code>Class</code>.
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Properties</code>.             
	 * @return <code>ReturnValue</code> details of update operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue update(Class<?> klass, Properties props) throws AppException{

		String METHODNAME = "update";

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));	
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		int type = Constants.TYPE_UNKNOWN_RESOURCE;
		ReturnValue retval = null;

		try{
			AbstractResource model = (AbstractResource)klass.newInstance();
			type = model.getResourceType();
		}catch(InstantiationException e){
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), Constants.MESSAGE_GENERAL_SQL_RESULTSET_INSTANTIATE);			
		}catch(IllegalAccessException e){
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), Constants.MESSAGE_GENERAL_SQL_RESULTSET_ILLEGALACCESS);			
		}

		retval = this.executeUpdate(type, props);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,retval));			
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return retval; 
	}

	/** This method updates a AbstractResource class/sub class type based 
	 * @param The type of return type expected. Also used to determine SQL code used
	 *        <code>Class</code>.
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Properties</code>.             
	 * @return <code>ReturnValue</code> class with result
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue create(Class<?> klass, Properties props) throws AppException{

		String METHODNAME = "create";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));	
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));				
		int type = Constants.TYPE_UNKNOWN_RESOURCE;
		ReturnValue retval = null;

		try{
			AbstractResource model = (AbstractResource)klass.newInstance();
			type = model.getResourceType();
		}catch(InstantiationException e){
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), Constants.MESSAGE_GENERAL_SQL_RESULTSET_INSTANTIATE);			
		}catch(IllegalAccessException e){
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), Constants.MESSAGE_GENERAL_SQL_RESULTSET_ILLEGALACCESS);			
		}

		retval = this.executeCreate(type, props);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,retval));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return retval; 
	}

	/** This method deletes a AbstractResource class/sub class type based. This is a soft delete = actually a update 
	 * @param The type of return type expected. Also used to determine SQL code used
	 *        <code>Class</code>.
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Properties</code>.             
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue delete(Class<?> klass, Properties props) throws AppException{

		String METHODNAME = "delete";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));			

		int type = Constants.TYPE_UNKNOWN_RESOURCE;
		ReturnValue retVal = null;

		try{
			AbstractResource model = (AbstractResource)klass.newInstance();
			type = model.getResourceType();
		}catch(InstantiationException e){
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), Constants.MESSAGE_GENERAL_SQL_RESULTSET_INSTANTIATE);			
		}catch(IllegalAccessException e){
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), Constants.MESSAGE_GENERAL_SQL_RESULTSET_ILLEGALACCESS);			
		}

		retVal = this.executeDelete(type, props);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,retVal));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return retVal; 
	}

	/** This method is a execute an update SQL query. Returns a coded int value generated by execute Update statement 
	 * @param The complete sql string  to be executed by the JDBC function 
	 *        <code>String</code>.             
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue updateSql(int type, Properties props) throws AppException{

		String METHODNAME = "updateSql";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));			

		Connection conn = this.getConnection();

		int val = Constants.INT_MINUS_ONE;
		CallableStatement stmt = null;
		DatabaseHelper helper = new DatabaseHelper();
		ReturnValue retVal = new ReturnValue(val, Constants.MESSAGE_SUCCESS);

		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"helper.getUpdateCallable"));

			//Create a Statement object to execute the SQL statement
			stmt = helper.getUpdateCallable(type, conn, props);

			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_GET_SQL_STATEMENT_SUCCESS));				

			//Execute the SQL update statement and get the return value
			val = stmt.executeUpdate();

			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_EXECUTE_SQL_STATEMENT_SUCCESS));
			String msg = stmt.getString(Columns.PARAM_ERRORMSG);
			if(helper.hasErrorMessage(msg)) throw new GeneralException(msg);
			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_EXECUTE_SQL_STATEMENT_SUCCESS));

			if(helper.hasErrorMessage(msg)) retVal = new ReturnValue(val, msg);
		}catch(SQLException e){
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new DatabaseException(e.getCause(), Constants.MESSAGE_GENERAL_SQL_RESULTSET_UPDATE_FAIL);
		}catch(Exception e){
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, "Exception: Database error: create operation")));				
		}finally {
			if (stmt != null) {
				try {
					stmt.close();
					LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_CLOSE_STATEMENT_SUCCESS));
				} catch (SQLException e) {
					LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_CLOSE_STMT_FAIL_INCLUDE, e.getMessage())));
				}
			}
			if (conn != null) {
				try {
					conn.close();
					LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_CLOSE_CONNECTION_SUCCESS));
				} catch (SQLException e) {
					LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_CLOSE_DB_FAIL_INCLUDE, e.getMessage())));
				}
			}
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,retVal));			
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return retVal;
	}

	/** This method is a execute SQL query. Returns a boolean flag indicating success 
	 * @param The complete sql string  to be executed by the JDBC function 
	 *        <code>String</code>.             
	 * @return <code>boolean</code> primitive indicating status of query execute update
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public boolean executeSql(int type,Properties props) throws AppException{

		String METHODNAME = "executeSql";	
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));			

		Connection conn = this.getConnection();		
		CallableStatement stmt = null;
		boolean retVal = false;
		DatabaseHelper helper = new DatabaseHelper();

		try{
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"helper.getSelectCallable()"));

			//Create a Statement object to execute the SQL statement
			stmt = helper.getSelectCallable(type, conn, props);

			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_GET_SQL_STATEMENT_SUCCESS));

			//Execute the SQL statement and get the return value				
			retVal = stmt.execute();
			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_EXECUTE_SQL_STATEMENT_SUCCESS));				
		}catch(SQLException e){
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new DatabaseException(e.getCause(), LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, "SQLException: Database error: create operation")));
		}catch(Exception e){
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, "Exception: Database error: create operation")));				
		}finally {
			if (stmt != null) {
				try {
					stmt.close();
					LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_CLOSE_STATEMENT_SUCCESS));
				} catch (SQLException e) {
					LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_CLOSE_STMT_FAIL_INCLUDE, e.getMessage())));
				}
			}
			if (conn != null) {
				try {
					conn.close();
					LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_CLOSE_CONNECTION_SUCCESS));
				} catch (SQLException e) {
					LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_CLOSE_DB_FAIL_INCLUDE, e.getMessage())));
				}
			}
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,retVal));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return retVal;
	}

	/** This method is a generic execute SQL query. Returns AbstractResource or ArrayList<AbstractResource> objects 
	 * @param The type of return type expected. Also used to determine SQL code used
	 *        <code>int</code>.
	 * @param <code>Properties</code> Properties object with parameter values for sql select/execute/update 
	 * @return <code>Object</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public Object executeQuery(int resultType, Properties props) throws AppException{

		String METHODNAME = "executeQuery";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,":resultType="+resultType+":sql="+props));			

		Connection conn = this.getConnection();
		ArrayList<? extends AbstractResource> resultsObj = null;
		CallableStatement stmt = null;
		DatabaseHelper helper = new DatabaseHelper();

		try {
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"helper.getSelectCallable()"));

			//Create a Statement object to execute the SQL statement
			stmt = helper.getSelectCallable(resultType, conn, props);

			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_GET_SQL_STATEMENT_SUCCESS));
			//Execute the SQL statement and get the results in a Resultset
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"stmt.executeQuery()"));				
			ResultSet rs = stmt.executeQuery(); 
			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_EXECUTE_SQL_STATEMENT_SUCCESS));

			//Use helper function to process the result set
			resultsObj = helper.processResultSet(resultType, rs);
			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_PROCESSED_SQL_STATEMENT_SUCCESS));
		}
		catch (SQLException e) {
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new DatabaseException(e.getCause(), LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, "SQLException: Database error: create operation")));
		}
		catch (Exception e) {
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, "Exception: Database error: create operation")));
		}finally {
			if (stmt != null) {
				try {
					stmt.close();
					LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_CLOSE_STATEMENT_SUCCESS));
				} catch (SQLException e) {
					LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_CLOSE_STMT_FAIL_INCLUDE, e.getMessage())));	                	
				}
			}
			if (conn != null) {
				try {
					conn.close();
					LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_CLOSE_CONNECTION_SUCCESS));	                    
				} catch (SQLException e) {
					LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_CLOSE_DB_FAIL_INCLUDE, e.getMessage())));
				}
			}
		}

		/*if(resultsObj == null || resultsObj.size() < Constants.INT_VALUE_MIN_RESULTSET_SIZE){
				throw new NoRecordsFoundDataException(new Throwable(), Constants.RESPONSE_CODE_NO_RECORDS_FOUND, 
						String.format(Constants.MESSAGE_DATA_PROCESS_ERROR_INCLUDE,"No records found."),
						String.format(Constants.MESSAGE_DATA_PROCESS_ERROR_INCLUDE,"No records were returned by database for the search parameters specified."));
			}*/

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,resultsObj));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return resultsObj;
	}


	/** This method is a create execute SQL query. Returns integer returnCode 
	 * @param The complete sql string  to be executed by the JDBC function 
	 *        <code>String</code>.             
	 * @param <code>Properties</code> Properties object with parameter values for sql select/execute/create		    *        
	 * @return <code>ReturnValue</code> details of create operations success/errors, key/value pair
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue executeCreate(int type, Properties props) throws AppException{

		String METHODNAME = "executeCreate";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));			

		Connection conn = this.getConnection();
		CallableStatement stmt = null;	
		DatabaseHelper helper = new DatabaseHelper();

		ReturnValue retVal = new ReturnValue(Constants.RESPONSE_CODE_SUCCESS, "Create operation success.");

		try {
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"helper.getCreateCallable()"));				 

			//Create a Statement object to execute the SQL statement
			stmt = helper.getCreateCallable(type, conn, props);
			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_GET_SQL_STATEMENT_SUCCESS));			    

			//Execute the SQL statement and get the results in a Resultset
			int val = stmt.executeUpdate();

			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_EXECUTE_SQL_STATEMENT_SUCCESS));
			String msg = stmt.getString(Columns.PARAM_ERRORMSG);
			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_EXECUTE_SQL_STATEMENT_SUCCESS));

			if(helper.hasErrorMessage(msg)) retVal = new ReturnValue(val, msg);
		}catch (SQLException e) {
			retVal = new ReturnValue(Constants.RESPONSE_CODE_FAIL_UPDATE, "SQLException: Database error: create operation");				
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new DatabaseException(e.getCause(), LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, retVal.getValue())));
		}
		catch (Exception e) {
			retVal = new ReturnValue(Constants.RESPONSE_CODE_FAIL_UPDATE, "Exception: Database error: create operation");	
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, retVal.getValue())));
		}finally {
			if (stmt != null) {
				try {
					stmt.close();
					LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_CLOSE_STATEMENT_SUCCESS));		            	
				} catch (SQLException e) {
					retVal = new ReturnValue(Constants.RESPONSE_CODE_FAIL_UPDATE, "finally: SQLException: Close statement: Database error: create operation");
					LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_CLOSE_STMT_FAIL_INCLUDE, e.getMessage())));
				}
			}
			if (conn != null) {
				try {
					conn.close();
					LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_CLOSE_CONNECTION_SUCCESS));
				} catch (SQLException e) {
					retVal = new ReturnValue(Constants.RESPONSE_CODE_FAIL_UPDATE, "finally: SQLException: Close database conection: Database error: update operation");
					LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_CLOSE_DB_FAIL_INCLUDE, e.getMessage())));
				}
			}
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,retVal));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return retVal;
	}


	/** This method is a update execute SQL query. Returns integer returnCode 
	 * @param The complete sql string  to be executed by the JDBC function 
	 *        <code>String</code>.             
	 * @param <code>Properties</code> Properties object with parameter values for sql select/execute/update		    *        
	 * @return <code>ReturnValue</code> details of update operations success/errors, key/value pair
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue executeUpdate(int type, Properties props) throws AppException{

		String METHODNAME = "executeUpdate";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));			

		Connection conn = this.getConnection();
		CallableStatement stmt = null;	
		DatabaseHelper helper = new DatabaseHelper();

		ReturnValue retVal = new ReturnValue(Constants.RESPONSE_CODE_SUCCESS, "Update operation success.");

		try {
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"helper.getUpdateCallable()"));				 

			//Create a Statement object to execute the SQL statement
			stmt = helper.getUpdateCallable(type, conn, props);
			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_GET_SQL_STATEMENT_SUCCESS));			    

			//Execute the SQL statement and get the results in a Resultset
			int val = stmt.executeUpdate();
			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_EXECUTE_SQL_STATEMENT_SUCCESS));

			String msg = stmt.getString(Columns.PARAM_ERRORMSG);
			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_EXECUTE_SQL_STATEMENT_SUCCESS));

			if(helper.hasErrorMessage(msg)) retVal = new ReturnValue(val, msg);
		}catch (SQLException e) {
			retVal = new ReturnValue(Constants.RESPONSE_CODE_FAIL_UPDATE, "SQLException: Database error: update operation");	
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new DatabaseException(e.getCause(), LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, retVal.getValue())));
		}
		catch (Exception e) {
			retVal = new ReturnValue(Constants.RESPONSE_CODE_FAIL_UPDATE, "Exception: Database error: update operation");	
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, retVal.getValue())));
		}finally {
			if (stmt != null) {
				try {
					stmt.close();
					LOGGER.info(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_CLOSE_STATEMENT_SUCCESS));		            	
				} catch (SQLException e) {
					retVal = new ReturnValue(Constants.RESPONSE_CODE_FAIL_UPDATE, "finally: SQLException: Close statement: Database error: update operation");
					LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_CLOSE_STMT_FAIL_INCLUDE, e.getMessage())));
				}
			}
			if (conn != null) {
				try {
					conn.close();
					LOGGER.info(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_CLOSE_CONNECTION_SUCCESS));
				} catch (SQLException e) {
					retVal = new ReturnValue(Constants.RESPONSE_CODE_FAIL_UPDATE, "finally: SQLException: Close database conection: Database error: update operation");
					LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_CLOSE_DB_FAIL_INCLUDE, e.getMessage())));
				}
			}
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,retVal));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return retVal;
	}

	/** This method is a delete (update) execute SQL query. Returns integer returnCode of success. delete is a soft delete.
	 * @param The complete sql string  to be executed by the JDBC function 
	 *        <code>String</code>.             
	 * @param <code>Properties</code> Properties object with parameter values for sql select/execute/update		    *        
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue executeDelete(int type, Properties props) throws AppException{

		String METHODNAME = "executeDelete";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));			

		Connection conn = this.getConnection();
		CallableStatement stmt = null;		
		DatabaseHelper helper = new DatabaseHelper();

		ReturnValue retVal = new ReturnValue(Constants.RESPONSE_CODE_SUCCESS, "Delete operation success.");

		try {
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE,CLASSNAME, METHODNAME,"helper.getDeleteCallable()"));				 

			//Create a Statement object to execute the SQL statement
			stmt = helper.getDeleteCallable(type, conn, props);

			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_GET_SQL_STATEMENT_SUCCESS));

			//Execute the SQL statement and get the results in a Resultset
			int val = stmt.executeUpdate();

			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_EXECUTE_SQL_STATEMENT_SUCCESS));
			String msg = stmt.getString(Columns.PARAM_ERRORMSG);
			LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_EXECUTE_SQL_STATEMENT_SUCCESS));

			if(helper.hasErrorMessage(msg)) retVal = new ReturnValue(val, msg);
		}catch (SQLException e) {
			retVal = new ReturnValue(Constants.RESPONSE_CODE_FAIL_DELETE, "SQLException: Database error: delete operation");			    
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, retVal.getValue())));

		}
		catch (Exception e) {
			retVal = new ReturnValue(Constants.RESPONSE_CODE_FAIL_DELETE, "Exception: Database error: delete operation");
			LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, e.getMessage())));
			throw new GeneralException(e.getCause(), LoggingHelper.formatMessage(String.format(Constants.MESSAGE_SQL_EXECUTE_ERROR_INCLUDE, retVal.getValue())));
		}finally {
			if (stmt != null) {
				try {
					stmt.close();
					LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_CLOSE_STATEMENT_SUCCESS));
				} catch (SQLException e) {
					retVal = new ReturnValue(Constants.RESPONSE_CODE_FAIL_DELETE, "finally: SQLException: Close statement: Database error: delete operation");
					LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_CLOSE_STMT_FAIL_INCLUDE, e.getMessage())));
				}
			}
			if (conn != null) {
				try {
					conn.close();
					LOGGER.debug(LoggingHelper.formatMessage(Constants.MESSAGE_DEBUG_CLOSE_CONNECTION_SUCCESS));
				} catch (SQLException e) {
					retVal = new ReturnValue(Constants.RESPONSE_CODE_FAIL_DELETE, "finally: SQLException: Close database connection: Database error: delete operation");
					LOGGER.error(LoggingHelper.formatMessage(String.format(Constants.MESSAGE_CLOSE_DB_FAIL_INCLUDE, e.getMessage())));
				}
			}
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,retVal));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return retVal;
	}

	/** This method attempts to access the database / schema and throws exception if fails. health check if database is actually available 
	 * @return <code>boolean</code> flag successful execution of test sql query
	 * @exception <code>AppException</code> If an AppException error occurs. 
	 */	
	public boolean db_ping() throws AppException{
		String METHODNAME = "db_ping";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		int type = Constants.TYPE_GENERAL_DB_PING;
		Properties props = new Properties();
		props.setProperty(Constants.FIELD_PARAM_PING, Statements.databaseHealthQueryMap.get(getDatabaseType()));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return this.executeSql(type, props);			

	}

	/** This method returns the type of Database that is being used. 
	 * @param 
	 * @return <code>String</code> String value for the database type (DB2, Oracle) 
	 * @exception none    
	 */	
	public String getDatabaseType() throws AppException{
		String METHODNAME = "getDatabaseType";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		PreferenceUtils util = new PreferenceUtils();
		String dbtype = (String)util.getConsoleProperty(Constants.LOOKUP_PREF_DB_TYPE);
		
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return Constants.databaseTypes.contains(dbtype)? dbtype : Constants.PREF_DB_TYPE_UNKNOWN;
	}
}
