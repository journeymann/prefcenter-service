/**
 * 
 */
package com.fd.epcws.db.dao;

import org.apache.logging.log4j.Logger;
import com.fd.epcws.constants.Constants;
import com.fd.epcws.db.connection.DBSqlConnection;
import com.fd.epcws.exceptions.AppException;
import com.fd.epcws.util.PreferenceUtils;
import com.fd.epcws.util.helpers.LoggingHelper;

/**
 * @author cgordon
 *
 */
public class DAO {

	private final String CLASSNAME = "DAO";
	protected Logger LOGGER = LoggingHelper.getLogger(DAO.class);
	private DBSqlConnection conn = null;

	/** default no param constructor    
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public DAO() throws AppException{
		conn = this.getDBConnection();
	}

	/** This method returns a database connection which will be used its sub classes 
	 * @return <code>DBSqlConnection</code> database connection class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	private DBSqlConnection getDBConnection() throws AppException{

		String METHODNAME = "getDBConnection";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		DBSqlConnection dbaseConn = null;
		PreferenceUtils util = new PreferenceUtils();
		
		String dbType = (String)util.getConsoleProperty(Constants.LOOKUP_PREF_DB_TYPE);

		if (Constants.databaseTypes.contains(dbType)){
			dbaseConn = new DBSqlConnection();
		}else{
			LOGGER.fatal(LoggingHelper.formatMessage(Constants.MESSAGE_DATABASE_NOT_SUPPORTED));
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return dbaseConn;
	}

	/** Abstract method returns a database connection which will be used its sub classes 
	 * @return <code>DBSqlConnection</code> database connection class
	 */	
	protected DBSqlConnection getConnection(){
		return conn;
	}

}
