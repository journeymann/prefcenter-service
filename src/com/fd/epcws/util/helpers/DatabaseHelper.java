/**
 * 
 */
package com.fd.epcws.util.helpers;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.Logger;

import com.fd.epcws.constants.Constants;
import com.fd.epcws.db.sql.Columns;
import com.fd.epcws.db.sql.Statements;
import com.fd.epcws.exceptions.AppException;
import com.fd.epcws.exceptions.MissingResourceException;
import com.fd.epcws.rest.beans.AbstractResource;
import com.fd.epcws.rest.beans.Brand;
import com.fd.epcws.rest.beans.BrandChannel;
import com.fd.epcws.rest.beans.Channel;
import com.fd.epcws.rest.beans.ChannelFrequency;
import com.fd.epcws.rest.beans.Contact;
import com.fd.epcws.rest.beans.Frequency;
import com.fd.epcws.util.PreferenceUtils;

/**
 * @author cgordon
 *
 */
public class DatabaseHelper {

	private final String CLASSNAME = "DatabaseHelper";	
	protected Logger LOGGER = LoggingHelper.getLogger(DatabaseHelper.class); 

	/** This method uses the requested result set type to determine the sql query. 
	 * @param The type of return type expected. Also used to determine SQL code used
	 *        <code>int</code>.
	 * @param <code>Connection</code> database connection which will be used to execute the query       
	 * @param <code>Properties</code> Properties object with fields to be inserted into sql statement		    * 
	 * @return <code>CallableStatement</code> CallableStatement value of the sql statement. Uses Constants defined query strings. 
	 * @exception <code>SQLException</code> If a SQLException error occurs.    
	 */	
	public CallableStatement getSelectCallable(int type, Connection conn, Properties props) throws MissingResourceException, SQLException{
		String METHODNAME = "getSelectCallable";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,":type="+type+":props="+props));

		CallableStatement stmt = null;
		PreferenceUtils util = new PreferenceUtils();
		String sql= Constants.STRING_BLANK;
		boolean isWildcardSelect = false;

		if(props==null || props.isEmpty()){
			isWildcardSelect = true;
		}	
		if(props != null && !props.isEmpty() && props.get(Constants.FIELD_SEARCH_KEY1) != null && props.get(Constants.FIELD_SEARCH_KEY1).equals(Constants.QUERY_PARAM_WILDCARD)) {
			isWildcardSelect = true;
		}

		if(isWildcardSelect){
			switch(type){
			case Constants.TYPE_BRAND_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " isWildcardSelect: BRAND"));
				stmt = conn.prepareCall(Statements.SQL_SELECT_BRAND_LIST);							
				break;
			case Constants.TYPE_CHANNEL_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " isWildcardSelect: CHANNEL"));
				stmt = conn.prepareCall(Statements.SQL_SELECT_CHANNEL_LIST);
				break;
			case Constants.TYPE_FREQUENCY_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " isWildcardSelect: FREQUENCY"));
				stmt = conn.prepareCall(Statements.SQL_SELECT_FREQUENCY_LIST);
				break;
			case Constants.TYPE_CONTACT_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " isWildcardSelect: CONTACT"));
				LOGGER.debug(String.format(Constants.MESSAGE_WARN_NOT_IMPLEMENTED_INCLUDE, "get list: CONTACT"));
				break;
			case Constants.TYPE_BRANDCHANNEL_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " isWildcardSelect: BRANDCHANNEL"));
				stmt = conn.prepareCall(Statements.SQL_SELECT_BRANDCHANNEL_LIST);
				break;
			case Constants.TYPE_CHANNELFREQUENCY_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " isWildcardSelect: CHANNELFREQUENCY"));
				stmt = conn.prepareCall(Statements.SQL_SELECT_CHANNELFREQUENCY_LIST);
				break;
			case Constants.TYPE_GENERAL_DB_PING:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " isWildcardSelect: GENERAL_PING"));
				stmt = conn.prepareCall(Statements.databaseHealthQueryMap.get((String)util.getConsoleProperty(Constants.LOOKUP_PREF_DB_TYPE)));
				break;

			default:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :DEFAULT"));
			break;
			}
		}else{	
			switch(type){

			case Constants.TYPE_BRAND_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :BRAND"));
				stmt = conn.prepareCall(Statements.SQL_SELECT_BRAND);
				stmt.setString(1, getPropertyAsString(props.get(Constants.FIELD_SEARCH_KEY1)));
				break;
			case Constants.TYPE_CHANNEL_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CHANNEL"));
				stmt = conn.prepareCall(Statements.SQL_SELECT_CHANNEL);
				stmt.setString(1, getPropertyAsString(props.get(Constants.FIELD_SEARCH_KEY1)));

				break;
			case Constants.TYPE_FREQUENCY_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :FREQUENCY"));
				stmt = conn.prepareCall(Statements.SQL_SELECT_FREQUENCY);
				stmt.setString(1, getPropertyAsString(props.get(Constants.FIELD_SEARCH_KEY1)));

				break;
			case Constants.TYPE_CONTACT_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CONTACT"));
				stmt = conn.prepareCall(Statements.SQL_SELECT_CONTACT);
				stmt.setLong(1, getPropertyAsLong(props.get(Constants.FIELD_SEARCH_KEY1)));

				break;
			case Constants.TYPE_BRANDCHANNEL_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :BRANDCHANNEL"));

				int size = props.size();
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :BRANDCHANNEL PROP SIZE="+size));

				switch (size){

				case 1:
					LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " SELECT: SUB: QUERY: TYPE :CONTACTID (only)"));
					stmt = conn.prepareCall(Statements.SQL_SELECT_BRANDCHANNEL);
					stmt.setLong(1, getPropertyAsLong(props.get(Constants.FIELD_CONTACTID)));

					break;
				case 2:
					LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " SELECT: SUB: QUERY: TYPE :CONTACTID, BRANDCODE"));
					stmt = conn.prepareCall(Statements.SQL_SELECT_BRANDCHANNELBYBRAND);
					stmt.setLong(1, getPropertyAsLong(props.get(Constants.FIELD_CONTACTID)));							
					stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_BRAND)));

					break;								
				case 3:
					LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " SELECT: SUB: QUERY: TYPE :CONTACTID, BRANDCODE, CHANNELCODE"));
					stmt = conn.prepareCall(Statements.SQL_SELECT_BRANDCHANNELBYBRANDCHANNEL);
					stmt.setLong(1, getPropertyAsLong(props.get(Constants.FIELD_CONTACTID)));
					stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_BRAND)));									
					stmt.setString(3, getPropertyAsString(props.get(Constants.FIELD_CHANNEL)));							

					break;
				default:
					LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :DEFAULT"));

				break;								
				}

				break;
			case Constants.TYPE_CHANNELFREQUENCY_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CHANNELFREQUENCY"));
				stmt = conn.prepareCall(Statements.SQL_SELECT_CHANNELFREQUENCY);
				stmt.setInt(1, getPropertyAsInteger(props.get(Constants.FIELD_SEARCH_KEY1)));

				break;
			case Constants.TYPE_GENERAL_DB_PING:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :GENERAL_PING"));
				stmt = conn.prepareCall(Statements.databaseHealthQueryMap.get((String)util.getConsoleProperty(Constants.LOOKUP_PREF_DB_TYPE)));
				break;

			default:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :DEFAULT"));
			break;
			}					
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,sql));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return stmt;
	}

	/** This method uses the requested result set type to determine the sql query. Retreives the CREATE SQL statements. 
	 * @param The type of return type expected. Also used to determine SQL code used
	 *        <code>int</code>.
	 * @param <code>Connection</code> database connection which will be used to execute the query        
	 * @param <code>Properties</code> Properties object with fields to be inserted into sql statement		    *        
	 * @return <code>CallableStatement</code> CallableStatement value of the sql statement. Uses Constants defined query strings. 
	 * @exception <code>SQLException</code> If a SQLException error occurs.    
	 */	
	public CallableStatement getCreateCallable(int type, Connection conn, Properties props) throws SQLException{
		String METHODNAME = "getCreateCallable";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,":type="+type+":props="+props));			

		CallableStatement stmt = null;

		switch(type){

		case Constants.TYPE_BRAND_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :BRAND"));					

			stmt = conn.prepareCall(Statements.SQL_CREATE_BRAND);
			stmt.setString(1, getPropertyAsString(props.get(Constants.FIELD_BRANDCODE)));
			stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_BRANDDESC)));
			stmt.setString(3, getPropertyAsString(props.get(Constants.FIELD_ACTIVE)));
			stmt.setInt(4, getPropertyAsInteger(props.get(Constants.FIELD_RANK)));
			stmt.setString(5, getPropertyAsString(props.get(Constants.FIELD_VISIBLE)));

			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;
		case Constants.TYPE_CHANNEL_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CHANNEL"));

			stmt = conn.prepareCall(Statements.SQL_CREATE_CHANNEL);
			stmt.setString(1, getPropertyAsString(props.get(Constants.FIELD_CHANNELCODE)));
			stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_CHANNELDESC)));
			stmt.setInt(3, getPropertyAsInteger(props.get(Constants.FIELD_RANK)));
			stmt.setString(4, getPropertyAsString(props.get(Constants.FIELD_ACTIVE)));
			stmt.setString(5, getPropertyAsString(props.get(Constants.FIELD_VISIBLE)));

			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;
		case Constants.TYPE_FREQUENCY_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :FREQUENCY"));

			stmt = conn.prepareCall(Statements.SQL_CREATE_FREQUENCY);
			stmt.setString(1, getPropertyAsString(props.get(Constants.FIELD_FREQUENCYCODE)));
			stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_FREQUENCYDESC)));

			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;
		case Constants.TYPE_CONTACT_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CONTACT"));

			stmt = conn.prepareCall(Statements.SQL_CREATE_CONTACT);
			stmt.setLong(1, getPropertyAsLong(props.get(Constants.FIELD_CONTACTID)));
			stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_EMAIL)));
			stmt.setString(3, getPropertyAsString(props.get(Constants.FIELD_USERMODIFIED)));
			stmt.setString(4, getPropertyAsString(props.get(Constants.FIELD_COUNTRYCODE)));

			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;
		case Constants.TYPE_BRANDCHANNEL_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :BRANDCHANNEL"));

			stmt = conn.prepareCall(Statements.SQL_CREATE_BRANDCHANNEL);
			stmt.setLong(1, getPropertyAsLong(props.get(Constants.FIELD_CONTACTID)));
			stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_BRANDCODE)));
			stmt.setString(3, getPropertyAsString(props.get(Constants.FIELD_CHANNELCODE)));
			stmt.setString(4, getPropertyAsString(props.get(Constants.FIELD_FREQUENCYCODE)));
			stmt.setString(5, getPropertyAsString(props.get(Constants.FIELD_USERID)));

			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;
		case Constants.TYPE_CHANNELFREQUENCY_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CHANNELFREQUENCY"));

			stmt = conn.prepareCall(Statements.SQL_CREATE_CHANNELFREQUENCY);
			stmt.setString(1, getPropertyAsString(props.get(Constants.FIELD_CHANNELCODE)));
			stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_FREQUENCYCODE)));
			stmt.setString(3, getPropertyAsString(props.get(Constants.FIELD_ACTIVE)));
			stmt.setInt(4, getPropertyAsInteger(props.get(Constants.FIELD_RANK)));
			stmt.setString(5, getPropertyAsString(props.get(Constants.FIELD_VISIBLE)));

			stmt.registerOutParameter(Columns.PARAM_CHANNELFREQUENCYID, Types.INTEGER);
			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);					

			break;

		default:		
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :DEFAULT"));
		break;
		}

		StringBuffer buff = new StringBuffer("params: ");
		buff.append(stmt.getParameterMetaData().toString());
		buff.append(" toString(): ");
		buff.append(stmt.toString());			

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,buff));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return stmt;
	}

	/** This method uses the requested result set type to determine the sql query. Retrieves the UPDATE SQL statements. 
	 * @param The type of return type expected. Also used to determine SQL code used
	 *        <code>int</code>.
	 * @param <code>Connection</code> database connection which will be used to execute the query        
	 * @param <code>Properties</code> Properties object with fields to be inserted into sql statement        
	 * @return <code>CallableStatement</code> CallableStatement value of the sql statement. Uses Constants defined query strings. 
	 * @exception <code>SQLException</code> If a SQLException error occurs.    
	 */	
	public CallableStatement getUpdateCallable(int type, Connection conn, Properties props) throws SQLException{

		String METHODNAME = "getUpdateCallable";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,":type="+type+":props="+props));			

		CallableStatement stmt = null;

		switch(type){

		case Constants.TYPE_BRAND_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :BRAND"));

			stmt = conn.prepareCall(Statements.SQL_UPDATE_BRAND);
			stmt.setString(1, getPropertyAsString(props.get(Constants.FIELD_BRANDCODE)));
			stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_BRANDDESC)));
			stmt.setString(3, getPropertyAsString(props.get(Constants.FIELD_ACTIVE)));
			stmt.setInt(4, getPropertyAsInteger(props.get(Constants.FIELD_RANK)));
			stmt.setString(5, getPropertyAsString(props.get(Constants.FIELD_VISIBLE)));

			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);


			break;
		case Constants.TYPE_CHANNEL_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CHANNEL"));

			stmt = conn.prepareCall(Statements.SQL_UPDATE_CHANNEL);
			stmt.setString(1, getPropertyAsString(props.get(Constants.FIELD_CHANNELCODE)));
			stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_CHANNELDESC)));
			stmt.setInt(3, getPropertyAsInteger(props.get(Constants.FIELD_RANK)));
			stmt.setString(4, getPropertyAsString(props.get(Constants.FIELD_ACTIVE)));
			stmt.setString(5, getPropertyAsString(props.get(Constants.FIELD_VISIBLE)));

			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;
		case Constants.TYPE_FREQUENCY_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :FREQUENCY"));

			stmt = conn.prepareCall(Statements.SQL_UPDATE_FREQUENCY);
			stmt.setString(1, getPropertyAsString(props.get(Constants.FIELD_FREQUENCYCODE)));
			stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_FREQUENCYDESC)));
			stmt.setString(3, getPropertyAsString(props.get(Constants.FIELD_ACTIVE)));					

			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;
		case Constants.TYPE_CONTACT_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CONTACT"));

			stmt = conn.prepareCall(Statements.SQL_UPDATE_CONTACT);
			stmt.setLong(1, getPropertyAsLong(props.get(Constants.FIELD_CONTACTID)));
			stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_EMAIL)));
			stmt.setString(3, getPropertyAsString(props.get(Constants.FIELD_USERMODIFIED)));
			stmt.setString(4, getPropertyAsString(props.get(Constants.FIELD_COUNTRYCODE)));
			stmt.setString(5, getPropertyAsString(props.get(Constants.FIELD_SMS)));					

			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;
		case Constants.TYPE_BRANDCHANNEL_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :BRANDCHANNEL"));

			stmt = conn.prepareCall(Statements.SQL_UPDATE_BRANDCHANNEL);
			stmt.setLong(1, getPropertyAsLong(props.get(Constants.FIELD_CONTACTID)));
			stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_BRANDCODE)));
			stmt.setString(3, getPropertyAsString(props.get(Constants.FIELD_CHANNELCODE)));
			stmt.setString(4, getPropertyAsString(props.get(Constants.FIELD_FREQUENCYCODE)));
			stmt.setString(5, getPropertyAsString(props.get(Constants.FIELD_USERID)));

			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;
		case Constants.TYPE_CHANNELFREQUENCY_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CHANNELFREQUENCY"));

			stmt = conn.prepareCall(Statements.SQL_UPDATE_CHANNELFREQUENCY);
			stmt.setInt(1, getPropertyAsInteger(props.get(Constants.FIELD_CHANNELFREQID)));
			stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_ACTIVE)));
			stmt.setInt(3, getPropertyAsInteger(props.get(Constants.FIELD_RANK)));
			stmt.setString(4, getPropertyAsString(props.get(Constants.FIELD_VISIBLE)));

			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;

		default:	
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :DEFAULT"));
		break;
		}

		StringBuffer buff = new StringBuffer("params: ");
		buff.append(stmt.getParameterMetaData().toString());
		buff.append(" toString(): ");
		buff.append(stmt.toString());			

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,buff));			
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return stmt;
	}

	/** This method uses the requested result set type to determine the sql query. Retrieves the DELETE SQL statements. 
	 * @param The type of return type expected. Also used to determine SQL code used
	 *        <code>int</code>.
	 * @param <code>Connection</code> database connection which will be used to execute the query        
	 * @param <code>Properties</code> Properties object with fields to be inserted into sql statement
	 * @return <code>CallableStatement</code> CallableStatement value of the sql statement. Uses Constants defined query strings. 
	 * @exception <code>SQLException</code> If a SQLException error occurs.    
	 */	
	public CallableStatement getDeleteCallable(int type, Connection conn, Properties props) throws SQLException{
		String METHODNAME = "getDeleteCallable";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,":type="+type+":props="+props));			

		CallableStatement stmt = null;

		switch(type){

		case Constants.TYPE_BRAND_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :BRAND"));
			stmt = conn.prepareCall(Statements.SQL_DELETE_BRAND);

			stmt.setString(1, getPropertyAsString(props.get(Constants.FIELD_SEARCH_KEY1)));
			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;
		case Constants.TYPE_CHANNEL_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CHANNEL"));
			stmt = conn.prepareCall(Statements.SQL_DELETE_CHANNEL);

			stmt.setString(1, getPropertyAsString(props.get(Constants.FIELD_SEARCH_KEY1)));
			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;
		case Constants.TYPE_FREQUENCY_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :FREQUENCY"));
			stmt = conn.prepareCall(Statements.SQL_DELETE_FREQUENCY);

			stmt.setString(1, getPropertyAsString(props.get(Constants.FIELD_SEARCH_KEY1)));
			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;
		case Constants.TYPE_CONTACT_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CONTACT"));
			stmt = conn.prepareCall(Statements.SQL_DELETE_CONTACT);

			stmt.setLong(1, getPropertyAsLong(props.get(Constants.FIELD_CONTACTID)));
			stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_EMAIL)));
			stmt.setString(3, getPropertyAsString(props.get(Constants.FIELD_USERID)));

			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;
		case Constants.TYPE_BRANDCHANNEL_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :BRANDCHANNEL"));
			stmt = conn.prepareCall(Statements.SQL_DELETE_BRANDCHANNEL);

			stmt.setLong(1, getPropertyAsLong(props.get(Constants.FIELD_CONTACTID)));
			stmt.setString(2, getPropertyAsString(props.get(Constants.FIELD_BRANDCODE)));					
			stmt.setString(3, getPropertyAsString(props.get(Constants.FIELD_CHANNELCODE)));
			stmt.setString(4, getPropertyAsString(props.get(Constants.FIELD_USERMODIFIED)));

			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;
		case Constants.TYPE_CHANNELFREQUENCY_RESOURCE:
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CHANNELFREQUENCY"));
			stmt = conn.prepareCall(Statements.SQL_DELETE_CHANNELFREQUENCY);

			stmt.setInt(1, getPropertyAsInteger(props.get(Constants.FIELD_SEARCH_KEY1)));
			stmt.registerOutParameter(Columns.PARAM_ERRORMSG, Types.VARCHAR);

			break;

		default:	
			LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :DEFAULT"));
		break;
		}

		StringBuffer buff = new StringBuffer("params: ");
		buff.append(stmt.getParameterMetaData().toString());
		buff.append(" toString(): ");
		buff.append(stmt.toString());			

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,buff));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return stmt;
	}

	/** This method processes the resultset after a query is run. Uses the ResultSet object returned by query engine 
	 * @param The type of return type expected. Also used to determine SQL code used
	 *        <code>int</code>.
	 * @param The query resultSet<code>ResultSet</code>.             
	 * @return <code>ArrayList<AbstractResource></code> ArrayList of AbstractResource based objects. 
	 * @exception <code>AppException</code> If a AppException error occurs.    
	 * @exception <code>SQLException</code> If a SQLException error occurs.
	 */	
	public ArrayList<AbstractResource> processResultSet(int resultType, ResultSet rs) throws AppException, SQLException{

		String METHODNAME = "processResultSet";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,":resultType="+resultType+":rs="+rs));			

		ArrayList<AbstractResource> results = new ArrayList<AbstractResource>();

		while(rs.next()){

			switch(resultType){

			case Constants.TYPE_BRAND_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :BRAND"));
				//Brand(String p_description, int p_rank, String p_code, String p_active, String p_visible)						
				Brand brand = new Brand(rs.getString(Columns.COLUMN_BRANDNAME),rs.getInt(Columns.COLUMN_RANK), rs.getString(Columns.COLUMN_BRANDCODE), 
						rs.getString(Columns.COLUMN_ACTIVE), rs.getString(Columns.COLUMN_VISIBLE));

				results.add(brand);
				break;

			case Constants.TYPE_CHANNEL_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CHANNEL"));
				//Channel( String p_description, int p_rank, String p_code, String p_active, String p_visible){
				Channel channel = new Channel(rs.getString(Columns.COLUMN_COMMCHANNELNAME),Integer.parseInt(rs.getString(Columns.COLUMN_RANK)), rs.getString(Columns.COLUMN_COMMCHANNELCODE), 
						rs.getString(Columns.COLUMN_ACTIVE), rs.getString(Columns.COLUMN_VISIBLE));					
				results.add(channel);
				break;

			case Constants.TYPE_FREQUENCY_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :FREQUENCY"));
				//Frequency(String description, String code, int id, String active){
				Frequency frequency = new Frequency(rs.getString(Columns.COLUMN_FREQUENCYDESC), rs.getString(Columns.COLUMN_FREQUENCYCODE),Constants.INT_DEFAULT_FREQUENCY_ID, rs.getString(Columns.COLUMN_ACTIVE));

				results.add(frequency);
				break;

			case Constants.TYPE_CONTACT_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CONTACT"));
				//Contact(int p_id, String p_email, String p_identifier, String p_sms,String p_countrycode,String p_countryname,String p_value,Date p_datemodified, String p_usermodified, String p_active){						
				DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.ENGLISH); //Atom (ISO 8601)

				try{
					Contact contact = new Contact(rs.getLong(Columns.COLUMN_CONTACTID), rs.getString(Columns.COLUMN_EMAILADDRESS), Constants.STR_DEFAULT_IDENTIFIER,  
							rs.getString(Columns.COLUMN_SMS),rs.getString(Columns.COLUMN_COUNTRYCODE),rs.getString(Columns.COLUMN_COUNTRYNAME), Constants.STR_DEFAULT_VALUE 
							,formatter.parse(rs.getString(Columns.COLUMN_DATEMODIFIED)), rs.getString(Columns.COLUMN_USERMODIFIED), rs.getString(Columns.COLUMN_ACTIVE));

					results.add(contact);
				}catch(Exception e){
					LOGGER.debug(String.format(Constants.MESSAGE_DATA_PROCESS_ERROR_INCLUDE,e.getMessage()));
				}
				break;

			case Constants.TYPE_BRANDCHANNEL_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :BRANDCHANNEL"));
				//BrandChannel(String id, String pchannelcode, String pchanneldesc, String pbrandcode, String pbranddesc,String pvalue, String pfrequencycode,String pfrequencydesc,
				//int prank, String pactive,String pvisible){

				BrandChannel brandChannel = new BrandChannel(rs.getLong(Columns.COLUMN_CONTACTID), rs.getString(Columns.COLUMN_COMMCHANNELCODE), rs.getString(Columns.COLUMN_COMMCHANNELNAME), 
						rs.getString(Columns.COLUMN_BRANDCODE), rs.getString(Columns.COLUMN_BRANDNAME), Constants.STRING_BLANK, 
						rs.getString(Columns.COLUMN_FREQUENCYCODE), rs.getString(Columns.COLUMN_FREQUENCYDESC), Constants.INT_DEFAULT_RANK, 
						(rs.getString(Columns.COLUMN_FREQUENCYCODE).equalsIgnoreCase(Constants.STRING_CODEVALUE_NONE))? Constants.STRING_NO : Constants.STRING_YES
								,Constants.STR_DEFAULT_VISIBLE);

				results.add(brandChannel);
				break;

			case Constants.TYPE_CHANNELFREQUENCY_RESOURCE:
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :CHANNELFREQUENCY"));
				//ChannelFrequency(int commchanelfreqid, String pchannelcode, String pchanneldesc, String pfrequencycode, String pfrequencydesc, int prank, String pactive, String pvisible){
				ChannelFrequency channelFrequency = new ChannelFrequency(rs.getInt(Columns.COLUMN_COMMCHANNELFREQID), rs.getString(Columns.COLUMN_COMMCHANNELCODE), rs.getString(Columns.COLUMN_COMMCHANNELNAME), 
						rs.getString(Columns.COLUMN_FREQUENCYCODE), rs.getString(Columns.COLUMN_FREQUENCYDESC), rs.getInt(Columns.COLUMN_RANK),rs.getString(Columns.COLUMN_ACTIVE),
						rs.getString(Columns.COLUMN_VISIBLE));

				results.add(channelFrequency);
				break;

			default:	
				LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE,CLASSNAME, METHODNAME, " :DEFAULT"));
			break;
			}
		}

		StringBuffer buff = new StringBuffer("results: ");
		buff.append(results.toArray().toString());
		buff.append(" toString(): ");
		buff.append(results.toString());			

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,results));			
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return results;
	}		

	/** This private method returns a escaped string form of the query parameter passed 
	 * @param query <code>String</code> to be sanitized 
	 * @return <code>String</code> escape version of parameter string; 
	 * @exception none    
	 */	
	@SuppressWarnings({"unused"})
	private String escape(String param){
		String METHODNAME = "escape OL1";			
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));	
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,param));

		return StringHelper.escapeSQL(param); 
	}

	/** This private method returns a escaped string form of the query (object) parameter passed 
	 * @param query <code>Object</code> to be sanitized 
	 * @return <code>String</code> escape version of parameter string; 
	 * @exception none    
	 */	
	@SuppressWarnings({"unused"})		
	private String escape(Object param){
		String METHODNAME = "escape OL2";			
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));	
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,param));			

		return StringHelper.escapeSQL((String)param); 
	}

	/** This private method returns a escaped string form of the query parameter passed 
	 * @param query <code>Object</code> to be sanitized/converted 
	 * @return <code>String</code> value of property 
	 * @exception none    
	 */	
	private String getPropertyAsString(Object param){
		String METHODNAME = "getPropertyAsString";			
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));	
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,param));

		return (String)((param==null)? Constants.STRING_BLANK : param); 
	}

	/** This private method returns a escaped Integer form of the query parameter passed 
	 * @param query <code>Object</code> to be sanitized/converted 
	 * @return <code>Long</code> value of property 
	 * @exception none    
	 */	
	private Long getPropertyAsLong(Object param){
		String METHODNAME = "getPropertyAsLong";			
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));	
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,param));

		Long val = Constants.LONG_ZERO;

		try{
			val = (param != null && NumberUtils.isDigits(param.toString()))? NumberUtils.createLong(param.toString()) : Constants.INT_MINUS_ONE;
		}catch(Exception e){
			LOGGER.error(String.format(Constants.MESSAGE_GENERIC_INCLUDE,e.getMessage()));
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return val;   
	}

	/** This private method returns a escaped Integer form of the query parameter passed 
	 * @param query <code>Object</code> to be sanitized/converted 
	 * @return <code>Integer</code> value of property 
	 * @exception none    
	 */	
	private Integer getPropertyAsInteger(Object param){
		String METHODNAME = "getPropertyAsInteger";			
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));	
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,param));

		Integer val = Constants.INT_ZERO;

		try{
			val = (param != null && NumberUtils.isDigits(param.toString()))? NumberUtils.createInteger(param.toString()) : Constants.INT_MINUS_ONE;
		}catch(Exception e){
			LOGGER.error(String.format(Constants.MESSAGE_GENERIC_INCLUDE,e.getMessage()));
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return val;   
	}

	/** This method returns a a boolean flag indicating the existence of errors returned by database in/out param P_ERRMSG
	 * @param <code>String</code> value of data returned by database call (error message) 
	 * @return <code>boolean</code> flag existence of errors returned by database 
	 * @exception none    
	 */	
	public boolean hasErrorMessage(String msg){
		String METHODNAME = "hasErrorMessage";			
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));	
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,msg));

		boolean flag = false;

		if(msg != null && !StringUtils.isEmpty(msg) && !StringUtils.isBlank(msg)) flag = true;

		return flag;
	}

	/** This private method returns a escaped char (primitive) form of the query parameter passed 
	 * @param query <code>Object</code> to be sanitized/converted 
	 * @return <code>char</code> value of property 
	 * @exception none    
	 */	
	@SuppressWarnings({"unused"})
	private Character getPropertyAsCharacter(Object param){
		String METHODNAME = "getPropertyAsCharacter";			
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));	
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE,param));

		Character val = Constants.CHAR_BLANK;

		try{
			val = (param != null && StringUtils.isNotEmpty(param.toString()))? param.toString().charAt(Constants.INT_ZERO) : Constants.CHAR_BLANK;
		}catch(Exception e){
			LOGGER.error(String.format(Constants.MESSAGE_GENERIC_INCLUDE,e.getMessage()));
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return val;
	}
}
