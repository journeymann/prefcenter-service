/**
 *  
 */
package com.fd.epcws.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * @author cgordon
 *
 * Class houses the constants to be used across the entire application
 */
public final class Constants {

	/**
	 * General integer Constants
	 */
	public static final int INT_ZERO = 0;
	public static final int INT_MINUS_ONE = -1;
	public static final int INT_VALUE_TRUE = 1;
	public static final int INT_VALUE_FALSE = 0;
	public static final int INT_VALUE_MIN_RESULTSET_SIZE = 1;
	public static final int INT_MIN_ID = 0;	
	public static final int INT_MIN_DESC = 0;
	public static final int INT_MAX_DESC = 50;
	public static final int INT_MIN_VALUE = 0;
	public static final int INT_MAX_VALUE = 100;	
	public static final int INT_MIN_FREQ = 1;
	public static final int INT_MAX_FREQ = 50;	
	public static final int INT_MIN_SMS = 0;
	public static final int INT_MAX_SMS = 128;	
	public static final int INT_MIN_IDENT = 0;
	public static final int INT_MAX_IDENT = 30;	
	public static final int INT_MIN_RANK = 0;
	public static final int INT_MIN_CODE = 1;	
	public static final int INT_MAX_CODE = 5;	
	public static final int INT_MIN_FLAG = 1;	
	public static final int INT_MAX_FLAG = 1;	
	public static final int INT_MIN_USERNAME = 0;	
	public static final int INT_MAX_USERNAME = 50;
	public static final int INT_INVALID_MARKER = -100;
	public static final int INT_DEFAULT_FREQUENCY_ID = 0;
	public static final int INT_DEFAULT_CONTACT_ID = 0;
	public static final int INT_DEFAULT_COMMCHANNELFREQUENCY_ID = 0;
	public static final int INT_DEFAULT_BRANDCHANNEL_ID = 0;
	public static final int INT_DEFAULT_BRAND_ID = 0;
	public static final int INT_DEFAULT_CHANNEL_ID = 0;
	public static final int INT_DEFAULT_RANK = 10;
	
	public static final int INT_RANDOM_CRYPTO_BIT_SIZE = 130;
	public static final int INT_CRYPTO_SESSION_ID_SIZE = 32;
	
	public static final long LONG_ZERO = 0;

	/**
	 * Default values for optional features Constants
	 */
	public static final boolean BOOL_DEFAULT_PRETTY = false;
	public static final boolean BOOL_DEFAULT_ENVELOPE = false;
	public static final int INT_CACHE_MAX_AGE = 86400;
	
	/**
	 * General char (primitive) Constants
	 */
	public static final char CHAR_NULL = '\u0000' ;
	public static final char CHAR_BLANK = '\u0020' ;	

	/**
	 * Data related String Constants
	 */
	public static final String STR_NAMED_PARAM_P_ERRORMSG = ":P_ERRORMSG";
	public static final String STR_DEFAULT_IDENTIFIER = Constants.STRING_BLANK;	
	public static final String STR_DEFAULT_VALUE = Constants.STRING_BLANK;	
	public static final String STR_DEFAULT_USERMODIFIED = Constants.STRING_BLANK;	
	public static final String STR_DEFAULT_ISACTIVE = "Y";
	public static final String STR_DEFAULT_VISIBLE = "Y";	
	public static final String STR_URL_ENCODE_SCHEME = "UTF-8";
	public static final String STR_DEFAULT_COUNTRY_CODE = "USA";	
	public static final String STR_HTTP_DELIMITER = "\\A";	

	/**
	 * Resource type for quick/easy resource runtime identification integer Constants
	 */
	public static final int TYPE_UNKNOWN_RESOURCE = -1;	
	public static final int TYPE_BRAND_RESOURCE = 10;
	public static final int TYPE_BRANDCHANNEL_RESOURCE = 11;
	public static final int TYPE_CHANNEL_RESOURCE = 12;
	public static final int TYPE_CHANNELFREQUENCY_RESOURCE = 13;
	public static final int TYPE_CONTACT_RESOURCE = 14;
	public static final int TYPE_FREQUENCY_RESOURCE = 15;
	public static final int TYPE_GENERAL_DB_PING = 100;

	/**
	 * log4j configuration file location String Constant
	 */
	public static final String CONFIG_FILE_PARAM_KEY_LOG4J = "log4j-config-location";

	/**
	 * General String Constants
	 */
	public static final String STRING_VALUE_TRUE = "true";
	public static final String STRING_VALUE_FALSE = "false";
	public static final String STRING_LEFT_BRACKET = "[";
	public static final String STRING_RIGHT_BRACKET = "]";
	public static final String STRING_NEWLINE = "\n";
	public static final String STRING_BLANK = "";
	public static final String STRING_CODE_FAKE = "ZZZ";
	public static final String STRING_ERROR_MESSAGE_DELIM = "~";
	public static final String STRING_QUERY_PARAM = "?";
	public static final String STRING_RESOURCE_PACKAGE = "com.fd.epcws.rest.services";
	public static final String STRING_YES = "Y";
	public static final String STRING_NO = "N";	
	public static final String STRING_CODEVALUE_NONE = "NONE";

	/**
	 * Miscellaneous error and status messages String Constants
	 */
	public static final String MESSAGE_PING_SUCCESS = "RESTful Service 'Preference Center Service' is running ==> ping";	
	public static final String MESSAGE_TEST = "This a general test message...";	
	public static final String MESSAGE_OS_NOT_SUPPORTED= "The operating system being used in not supported by this application..";	
	public static final String MESSAGE_DATABASE_NOT_SUPPORTED= "The database type specified in resource bundle (properties file) is not currently supported by this application.";	
	public static final String MESSAGE_SEVERE_FAILURE = "Internal server error. Unable to process request.";
	public static final String MESSAGE_SQL_RESULTSET = "SQLException processing resultset.";	
	public static final String MESSAGE_GENERAL_SQL_RESULTSET = "General Exception raised processing resultset.";
	public static final String MESSAGE_SQL_EXECUTE_ERROR_INCLUDE = "Sql exception occurred while executing query. Details: %s.";	
	public static final String MESSAGE_GENERAL_SQL_RESULTSET_INSTANTIATE = "Error: unable to instantiate model class/sub class!";
	public static final String MESSAGE_GENERAL_SQL_RESULTSET_ILLEGALACCESS = "Error: unable to access model class/sub class!";	
	public static final String MESSAGE_GENERAL_SQL_RESULTSET_UPDATE_FAIL = "Error: failure to perform sql update!";
	public static final String MESSAGE_GENERAL_SQL_RESULTSET_EXECUTE_FAIL = "Error: failure to execute sql prepared statement!";	
	public static final String MESSAGE_GENERAL_EXCEPTION = "General exception encountered.";
	public static final String MESSAGE_CLOSE_DB_CONNECTION = "Exception raised while closing database connection.";
	public static final String MESSAGE_OPEN_DB_CONNECTION_FAIL = "Exception raised while opening database connection.";
	public static final String MESSAGE_OPEN_DB_CONNECTION_ATTEMPT = "Attempting to open JDBC connection.";	
	public static final String MESSAGE_OPEN_DB_CONNECTION_ATTEMPT_INCLUDE = "Attempting to open JDBC connection. Datasource details: %s.";	
	public static final String MESSAGE_OPEN_DB_CONNECTION_SUCCESS = "JDBC successfully able to open database connection.";
	public static final String MESSAGE_OPEN_DB_DRIVER_CLASS_NOT_FOUND = "Exception raised while opening database connection. Database driver class not found.";	
	public static final String MESSAGE_OPEN_DB_DRIVER_CLASS_NOT_FOUND_NAMING = "Naming exception raised while opening database connection. Database driver class not found.";	
	public static final String MESSAGE_ACCESS_DB_FAIL = "Exception raised while accessing database schema/tables.";
	public static final String MESSAGE_CONNECT_DB_FAIL = "Exception raised while attempting to connect with database.";	
	public static final String MESSAGE_ACCESS_DB_SUCCESS = "JDBC successfully able to access database schema/tables.";	
	public static final String MESSAGE_GENERAL_DB_ERROR_CONCAT = "Database returned error retreiving data.";
	public static final String MESSAGE_GENERAL_INPUT_ERROR_CONCAT = "Input request data contains errors. Please revise.";
	public static final String MESSAGE_SORTING_TOO_MANY_FIELDS = "Exception raised while sorting. Too many sort fields.";	
	public static final String MESSAGE_SORTING_INVALID_FIELD = "Exception raised while sorting. Field specified is invalid.";
	public static final String MESSAGE_SERVICE_FAIL_RESOLVE_URI = "Error occurred attempting to construct uri of the requested resource.";	
	public static final String MESSAGE_SERVICE_FAIL_CREATE = "Error occurred attempting to create the requested resource.";	
	public static final String MESSAGE_SERVICE_FAIL_UPDATE_REQ = "Input error occurred attempting to update the requested resource.";	
	public static final String MESSAGE_SERVICE_FAIL_UPDATE_RESP = "Response error occurred attempting to update the requested resource.";	
	public static final String MESSAGE_SERVICE_FAIL_ITEM = "Error occurred attempting to retreive the requested resource.";
	public static final String MESSAGE_SERVICE_FAIL_LIST = "Error occurred attempting to retreive the requested list of resources.";
	public static final String MESSAGE_SERVICE_FAIL_DELETE = "Error occurred attempting to delete the requested resource.";	
	public static final String MESSAGE_MISSING_RESOURCE_BUNDLE = "Error occurred accessing data in resource bundle. Properties is either missing or empty!";	
	public static final String MESSAGE_WARN_MISSING_CONFIG_RESOURCE_BUNDLE = "WARN: Unable to access configured resource bundle. Properties file is either missing or empty!";
	public static final String MESSAGE_WARN_QUERY_RETURNED_NO_ROWS = "WARN: The query results for the criteria given returned no rows";
	public static final String MESSAGE_MISSING_DEFAULT_RESOURCE_BUNDLE = "ERROR: Error occurred accessing default/backup resource bundle. Properties file is either missing or empty!";
	public static final String MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE = "Required '%s' parameter is invalid or missing";
	public static final String MESSAGE_RESPONSE_ERROR_CODE = "Return value 'code' is either invalid or missing";
	public static final String MESSAGE_RESPONSE_ERROR_ID = "Return value 'id' is either invalid or missing";
	public static final String MESSAGE_RESPONSE_ERROR_CONTACTID = "Required 'contactid' path parameter is missing";
	public static final String MESSAGE_RESPONSE_ERROR_DESCRIPTION = "Return value 'description' is either invalid or missing";
	public static final String MESSAGE_RESPONSE_ERROR_RANK = "Return value 'rank' is either invalid or missing";
	public static final String MESSAGE_RESPONSE_ERROR_ACTIVE = "Return value 'active' is either invalid or missing";
	public static final String MESSAGE_RESPONSE_ERROR_VISIBLE = "Return value 'visible' is either invalid or missing";
	public static final String MESSAGE_RESPONSE_ERROR_FREQCODE = "Return value 'frequencycode' is either invalid or missing";
	public static final String MESSAGE_RESPONSE_ERROR_CHANNELCODE = "Return value 'communicationchannelcode' is either invalid or missing";	
	public static final String MESSAGE_RESPONSE_ERROR_USERMODIFIED = "Return value 'usermodified' is either invalid or missing";
	public static final String MESSAGE_RESPONSE_ERROR_EMAIL = "Return value 'email' is either invalid or missing";
	public static final String MESSAGE_RESPONSE_ERROR_VALUE = "Return value 'value' is either invalid or missing";
	public static final String MESSAGE_REQUIRED_REQUEST_BODY_MISSING = "Request body is required for create requests.";
	public static final String MESSAGE_REQUEST_FAILED_VALIDATION = "Requests values have failed validation checks. Please revise request";	
	public static final String MESSAGE_REQUEST_PARAM_INVALID_TYPE_INCLUDE = "Requests param(s):%s has an invalid type. Please revise.";
	public static final String MESSAGE_RESPONSE_FAILED_VALIDATION = "Response values have failed validation checks. Please contact DBA.";
	public static final String MESSAGE_REQUEST_HTTP_SERVLET_ERROR_INCLUDE = "HttpServletRequest object contains errors with message: %s.";
	public static final String MESSAGE_DELETE_FAILED_INCLUDE = "Delete database call returned coded value: %s with description: %s.";
	public static final String MESSAGE_UPDATE_FAILED_INCLUDE = "Update database call returned coded value: %s with description: %s.";	
	public static final String MESSAGE_CREATE_FAILED_INCLUDE = "Create database call returned coded value: %s with description: %s.";	
	public static final String MESSAGE_CONNECT_DB_FAIL_INCLUDE = "Unable to connect to database. The exception message reason: %s.";
	public static final String MESSAGE_ACCESS_DB_FAIL_INCLUDE = "Unable to access to database. The exception message reason: %s.";	
	public static final String MESSAGE_CLOSE_DB_FAIL_INCLUDE = "Unable to close connection to database. The exception message reason: %s.";
	public static final String MESSAGE_CLOSE_STMT_FAIL_INCLUDE = "Unable to close prepared statement call to database. The exception message reason: %s.";	
	public static final String MESSAGE_DATA_PROCESS_ERROR_INCLUDE = "Exception encountered processing data records with message: %s.";
	public static final String MESSAGE_GENERIC_INCLUDE = "Error: default message: %s.";	
	public static final String MESSAGE_GENERIC_DETAILS_INCLUDE = "Error: default message: %s, exception details: %s.";	
	public static final String MESSAGE_PING_INCLUDE = "received ping on date:%s with database connection/access: %s.";
	public static final String MESSAGE_START_APPLICATION_INCLUDE = "Application startup log event. Description:  - %s.";	
	public static final String MESSAGE_DEBUG_CHANGE_LOG_LEVEL_MSG_INCLUDE = "DEBUG: The log level was changed to: %s.";	
	public static final String MESSAGE_DEBUG_FUNCTION_PARAM_INCLUDE = "DEBUG: Function Params: %s. ";	
	public static final String MESSAGE_DEBUG_CLOSE_STATEMENT_SUCCESS = "DEBUG: Successfully closed prepared statement.";
	public static final String MESSAGE_DEBUG_GET_SQL_STATEMENT_SUCCESS = "DEBUG: Successfully obtained prepared statement object.";
	public static final String MESSAGE_DEBUG_EXECUTE_SQL_STATEMENT_SUCCESS = "DEBUG: Successfully executed prepared statement query.";
	public static final String MESSAGE_DEBUG_PROCESSED_SQL_STATEMENT_SUCCESS = "DEBUG: Successfully processed and obtained query resultset.";
	public static final String MESSAGE_DEBUG_CLOSE_CONNECTION_SUCCESS = "DEBUG: Successfully closed database connection.";
	public static final String MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE = "DEBUG: Function Resource Object: %s. ";
	public static final String MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE = "DEBUG: Entered function. Class: %s , Method: %s.";
	public static final String MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE = "DEBUG: Exit function. Class: %s , Method: %s.";
	public static final String MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE = "DEBUG: Calling [small] function. Class: %s , Method: %s.";
	public static final String MESSAGE_DEBUG_SWITCH_OPTION_REACHED_INCLUDE = "DEBUG: Entering switch/case area. Class: %s , Method: %s, Option: %s.";	
	public static final String MESSAGE_DEBUG_FUNCTION_VARIABLE_INCLUDE = "DEBUG: Function variable - name: %s , value: %s.";
	public static final String MESSAGE_DEBUG_CHECKPOINT_INCLUDE = "DEBUG: Reached checkpoint marker: %s.";	
	public static final String MESSAGE_FATAL_ERROR_INCLUDE = "FATAL: Fatal error or exception raised with description: %s.";
	public static final String MESSAGE_ERROR_INCLUDE = "ERROR: Exception raised with description: %s";	
	public static final String MESSAGE_WARN_NOT_IMPLEMENTED_INCLUDE = "WARN: Requested feature has not been implemented as yet: %s";	
	public static final String MESSAGE_ERROR_DETAIL_INCLUDE = "ERROR: Uncaught exception caught. error: %s, localized message: %s, details: %s.";	
	public static final String MESSAGE_REQUEST_ENTITY_DATA_NOT_AVAILABLE = "Not applicable for request method";	
	public static final String MESSAGE_ERROR_APP_BUILD_ERROR_MESG = "ERROR: Application Exception encountered while building the error message.";
	public static final String MESSAGE_ERROR_FAIL_GET_PROCESS_METHOD = "ERROR: Failed to get process method.";;
	public static final String MESSAGE_ERROR_FAIL_GET_FIELD_NAME = "ERROR: Failed to get field name.";
	public static final String MESSAGE_ERROR_GENERAL_BUILD_ERROR_MESG = "ERROR: General Exception Error encountered while building the error message.";
	public static final String MESSAGE_NON_SPECIFIED_EXCEPTION_MESG = "Non specified exception has occured.";
	public static final String MESSAGE_DEBUG_FUNCTION_MESG_INCLUDE = "DEBUG: Stepping into function. Class: %s , Method: %s, Function called: %s.";
	public static final String MESSAGE_ERROR_FUNCTION_MESG_INCLUDE = "ERROR: Encountered a Json Processing Exception while creatring pretty Json output. Class: %s , Method: %s, Function called: %s.";	
	public static final String MESSAGE_SWAGGER_RUNTIME_EXCEPTION_INCLUDE = "ERROR: Failed : HTTP error code : %s";
	public static final String MESSAGE_INVALID_OPERATION_ATTEMPTED = "ERROR: Invalid operation on selected resource was attempted";	
	public static final String MESSAGE_ERROR_FAIL_WRITE_PERFORMANCE_DATA_INCLUDE = "ERROR: Failed to write to performance data to file with description: %s.";
	public static final String MESSAGE_STATUS_SUCCESS = "SUCCESS";	
	public static final String MESSAGE_STATUS_ERROR = "ERROR";	
	public static final String MESSAGE_STATUS_WARNING = "WARNING";
	public static final String MESSAGE_STATUS_INFO = "INFO";
	public static final String MESSAGE_STATUS_DEBUG = "DEBUG";
	public static final String MESSAGE_STATUS_TRACE = "TRACE";
	public static final String MESSAGES_MAP_KEY = "messages";	
	public static final String MESSAGE_STATUS_FORMAT_HTML = "HTML";
	public static final String MESSAGE_STATUS_FORMAT_TEXT = "TEXT";	
	public static final String MESSAGE_STATUS_FORMAT_XML = "XML";
	public static final String MESSAGE_BLANK = " ";
	public static final String MESSAGE_ERROR_CLASS_DESC = "Class Name: '";
	public static final String MESSAGE_ERROR_METHOD_DESC = "' Method Name: '";	
	public static final String MESSAGE_ERROR_DESC = "' Error Message: '";
	public static final String MESSAGE_ERROR_END_DESC = "'";	
	public static final String MESSAGE_SUCCESS = "SUCCESS";

	/**
	 * Database related [non specified] Constants
	 */
	public static final String PREF_DB_TYPE_UNKNOWN = "";	
	public static final String PREF_DB_TYPE_DB2 = "DB2";
	public static final String PREF_DB_TYPE_ORACLE = "ORACLE";	
	public static final String PREF_DB_TYPE_CSV_NAME = "CSV";	

	/**
	 * Persistence database driver class name(s)
	 */
	public static final String DBDRIVER_ORACLE = "oracle.jdbc.driver.OracleDriver";
	public static final String DBDRIVER_DB2 = "com.ibm.db2.jcc.DB2Driver";	
	
	/**
	 * Websphere Naming lookup values String Constants
	 */
	public static final String LOOKUP_PREF_SCHEME = "cell/persistent/epc/scheme";
	public static final String LOOKUP_PREF_AUTHORITY = "cell/persistent/epc/authority";
	public static final String LOOKUP_PREF_PREFIX = "cell/persistent/epc/prefix";
	public static final String LOOKUP_PREF_JSON_ENV_INCLUDE = "cell/persistent/epc/json.envelope.allow";
	public static final String LOOKUP_PREF_JSON_SORT_INCLUDE = "cell/persistent/epc/json.sort.allow";
	public static final String LOOKUP_PREF_JSON_PRETTY_INCLUDE = "cell/persistent/epc/json.pretty.allow";
	public static final String LOOKUP_PREF_DB_TYPE = "cell/persistent/epc/db.type";
	public static final String LOOKUP_PREF_DB_CONN_URL = "jdbc/EPCDATASRC";
	public static final String LOOKUP_PREF_DATE_FORMAT = "cell/persistent/epc/date.format";
	public static final String LOOKUP_PREF_CAPTURE_PERFORMANCE_DATA = "cell/persistent/epc/performance.allow";

	/**
	 * Input data field name String Constants
	 */
	public static final String FIELD_CODE = "code";
	public static final String FIELD_CHANNELFREQID = "channelfrequencyid";	
	public static final String FIELD_CHANNELCODE = "channelcode";	
	public static final String FIELD_CHANNELDESC = "channeldesc";	
	public static final String FIELD_BRANDCODE = "brandcode";	
	public static final String FIELD_BRANDDESC = "branddesc";	
	public static final String FIELD_BRAND = "brand";	
	public static final String FIELD_DESCRIPTION = "description";
	public static final String FIELD_RANK = "rank";
	public static final String FIELD_ACTIVE = "active";
	public static final String FIELD_VISIBLE = "visible";
	public static final String FIELD_CHANNEL = "channel";
	public static final String FIELD_FREQUENCYCODE = "frequencycode";	
	public static final String FIELD_FREQUENCYDESC = "frequencydesc";
	public static final String FIELD_VALUE = "value";
	public static final String FIELD_ID = "id";
	public static final String FIELD_KEY = "key";	
	public static final String FIELD_USERID = "userid";	
	public static final String FIELD_CONTACTID = "contactid";	
	public static final String FIELD_USERMODIFIED = "usermodified";
	public static final String FIELD_SYSTEM = "system";	
	public static final String FIELD_FREQUENCY = "frequency";
	public static final String FIELD_EMAIL = "email";
	public static final String FIELD_IDENTIFIER = "identifier";
	public static final String FIELD_SMS = "sms";		
	public static final String FIELD_STARTDATE = "startdate";
	public static final String FIELD_MODIFYDATE = "datemodified";	
	public static final String FIELD_ENDDATE = "enddate";	
	public static final String FIELD_SEARCH_KEY1 = "search_key_1";	
	public static final String FIELD_SEARCH_KEY2 = "search_key_2";
	public static final String FIELD_SEARCH_KEY3 = "search_key_3";	
	public static final String FIELD_LEVEL = "level";	
	public static final String FIELD_COUNTRYCODE = "countrycode";	
	public static final String FIELD_COUNTRYDESC = "countrydesc";	
	public static final String FIELD_PATH = "path";	
	public static final String FIELD_PARAM_PING = "ping";

	/**
	 * Database query parameter String Constants
	 */
	public static final String QUERY_PARAM_SORT = "sort";
	public static final String QUERY_PARAM_FIELDS = "fields";
	public static final String QUERY_PARAM_USE_ENVELOPE = "envelope";
	public static final String QUERY_PARAM_PRETTY = "pretty";	
	public static final String QUERY_PARAM_SORT_SEPARATOR = ",";
	public static final String QUERY_PARAM_DELIM = "&";
	public static final String QUERY_PARAM_TOKEN_DELIM = "=";
	public static final String QUERY_PARAM_WILDCARD = "*";

	/**
	 * Database data columns name String Constants
	 */
	public static final String DB_COLUMN_BRANDCODE = "BRANDCODE";	
	public static final String DB_COLUMN_BRANDNAME = "BRANDNAME";
	public static final String DB_COLUMN_ISACTIVE = "ISACTIVE";
	public static final String DB_COLUMN_RANK = "RANK";
	public static final String DB_COLUMN_VISIBLE = "VISIBLE";
	public static final String DB_COLUMN_COMMCHANNELCODE = "COMMCHANNELCODE";
	public static final String DB_COLUMN_COMMCHANNELNAME = "COMMCHANNELNAME";
	public static final String DB_COLUMN_FREQUENCYCODE = "FREQUENCYCODE";
	public static final String DB_COLUMN_FREQUENCYNAME = "FREQUENCYNAME";
	public static final String DB_COLUMN_COMMCHANNELFREQID = "COMMCHANNELFREQID";
	public static final String DB_COLUMN_VALUE = "VALUE";
	public static final String DB_COLUMN_CONTACTID = "CONTACTID";
	public static final String DB_COLUMN_EMAILADDRESS = "EMAILADDRESS";	

	/**
	 * General[custom] Response Codes integer Constants
	 */
	public static final int RESPONSE_CODE_INTERNAL_SERVER_ERROR = 500;
	public static final int RESPONSE_CODE_METHOD_NOT_IMPLEMENTED = 501;	
	public static final int RESPONSE_CODE_BAD_DATA_CREATE = 554;	
	public static final int RESPONSE_CODE_BAD_DATA_UPDATE = 555;
	public static final int RESPONSE_CODE_BAD_DATA_IN_RESPONSE = 556;
	public static final int RESPONSE_CODE_BAD_DATA_IN_REQUEST = 557;
	public static final int RESPONSE_CODE_BAD_DATA_ITEM = 558;
	public static final int RESPONSE_CODE_BAD_DATA_LIST = 559;
	public static final int RESPONSE_CODE_BAD_DATA_DELETE = 560;	
	public static final int RESPONSE_CODE_FAIL_DELETE = 561;
	public static final int RESPONSE_CODE_FAIL_UPDATE = 562;	
	public static final int RESPONSE_CODE_FAIL_LOCATE_RESOURCE = 563;
	public static final int RESPONSE_CODE_FAIL_CREATE = 564;	
	public static final int RESPONSE_CODE_INVALID_DATA = 567;
	public static final int RESPONSE_CODE_INVALID_URI = 568;
	public static final int RESPONSE_CODE_MISSING_RESOURCE_BUNDLE = 569;
	public static final int RESPONSE_CODE_SORTING_FAIL = 570;
	public static final int RESPONSE_CODE_UNSUPPORTED_SYSTEM = 571;
	public static final int RESPONSE_CODE_DATABASE_CONNECTION = 572;
	public static final int RESPONSE_CODE_DATABASE_ERROR = 573;
	public static final int RESPONSE_CODE_GENERAL_ISSUE = 574;
	public static final int RESPONSE_CODE_INVALID_REQUEST = 575;	
	public static final int RESPONSE_CODE_NO_RECORDS_FOUND = 576;
	public static final int RESPONSE_CODE_MISSING_PARAM = 578;
	public static final int RESPONSE_CODE_ABSTRACT = 0;	
	public static final int RESPONSE_CODE_OK = 200;	
	public static final int RESPONSE_CODE_SUCCESS = 200;
	public static final int RESPONSE_CODE_CREATED = 201;
	public static final int RESPONSE_CODE_NO_CONTENT = 204;
	public static final int RESPONSE_CODE_NOT_MODIFIED = 304;
	public static final int RESPONSE_CODE_BAD_REQUEST = 400;
	public static final int RESPONSE_CODE_UNAUTHORIZED = 401;
	public static final int RESPONSE_CODE_FORBIDDEN = 403;
	public static final int RESPONSE_CODE_NOTFOUND = 404;
	public static final int RESPONSE_CODE_METHOD_NOT_ALLOWED = 405;
	public static final int RESPONSE_CODE_GONE = 410;	
	public static final int RESPONSE_CODE_UNSUPPORTED_MEDIA_TYPE = 415;
	public static final int RESPONSE_CODE_UNPROCESSED_ENTITY = 422;
	public static final int RESPONSE_CODE_TOO_MANY_REQUESTS = 429;	

	/**
	 * Conditional validation byte Constants
	 */
	public static final byte TYPE_OPERATION_NONE = (byte) 0x00;	
	public static final byte TYPE_OPERATION_CREATE = (byte) 0x01;
	public static final byte TYPE_OPERATION_UPDATE = (byte) 0x02;
	public static final byte TYPE_OPERATION_DELETE = (byte) 0x03;
	public static final byte TYPE_OPERATION_LIST = (byte) 0x04;
	public static final byte TYPE_OPERATION_FIND = (byte) 0x05;	

	/**
	 * JSON data label String Constants
	 */
	public static final String JSON_DATA_LABEL_BRAND = "brand";
	public static final String JSON_DATA_LABEL_CONTACT = "contact";
	public static final String JSON_DATA_LABEL_CHANNEL = "communicationchannel";
	public static final String JSON_DATA_LABEL_BRANDCHANNEL = "brandcommunication";
	public static final String JSON_DATA_LABEL_FREQUENCY = "frequency";
	public static final String JSON_DATA_LABEL_CHANNELFREQUENCY = "communicationchannelfrequency";	
	public static final String JSON_DATA_LABEL_ERROR = "error";
	public static final String JSON_DATA_LABEL_OPERATIONS = "operations";	
	public static final String JSON_DATA_LABEL_BRAND_LIST = "brands";
	public static final String JSON_DATA_LABEL_CONTACT_LIST = "contacts";
	public static final String JSON_DATA_LABEL_CHANNEL_LIST = "communicationchannels";
	public static final String JSON_DATA_LABEL_BRANDCHANNEL_LIST = "brandcommunications";
	public static final String JSON_DATA_LABEL_FREQUENCY_LIST = "frequencies";
	public static final String JSON_DATA_LABEL_URI = "uri";
	public static final String JSON_DATA_LABEL_DIAGNOSTIC = "diagnostic";
	public static final String JSON_DATA_LABEL_MESSAGE = "message";
	public static final String JSON_DATA_LABEL_SWAGGER = "swagger";	
	public static final String JSON_DATA_LABEL_CHANNELFREQUENCY_LIST = "communicationchannelfrequencies";

	/**
	 * JSON Data Message String Constants
	 */
	public static final String JSON_DATA_MESSAGE_BRAND_CREATED = "Brand resource was created successfully";
	public static final String JSON_DATA_MESSAGE_CONTACT_CREATED = "Contact resource was created successfully";
	public static final String JSON_DATA_MESSAGE_CHANNEL_CREATED = "Communication Channel resource was created successfully";
	public static final String JSON_DATA_MESSAGE_BRANDCHANNEL_CREATED = "Brand Communication Channel resource was created successfully";
	public static final String JSON_DATA_MESSAGE_FREQUENCY_CREATED = "Frequency resource was created successfully";
	public static final String JSON_DATA_MESSAGE_CHANNELFREQUENCY_CREATED = "Communication Channel Frequency resource was created successfully";	
	public static final String JSON_DATA_MESSAGE_BRAND_DELETED = "Brand resource was deleted successfully";
	public static final String JSON_DATA_MESSAGE_CONTACT_DELETED = "Contact resource was deleted successfully";
	public static final String JSON_DATA_MESSAGE_CHANNEL_DELETED = "Communication Channel resource was deleted successfully";
	public static final String JSON_DATA_MESSAGE_BRANDCHANNEL_DELETED = "Brand Communication Channel resource was deleted successfully";
	public static final String JSON_DATA_MESSAGE_FREQUENCY_DELETED = "Frequency resource was deleted successfully";
	public static final String JSON_DATA_MESSAGE_CHANNELFREQUENCY_DELETED = "Communication Channel Frequency resource was deleted successfully";	

	/**
	 * URI Constants associated with creating resources
	 */
	public static final String URI_LABEL_BRAND = "/brands/%s";
	public static final String URI_LABEL_CHANNEL = "/communicationchannels/%s";
	public static final String URI_LABEL_FREQUENCY = "/frequencies/%s";
	public static final String URI_LABEL_CONTACT = "/contacts/%s";
	public static final String URI_LABEL_CHANNELFREQUENCY = "/communicationchannelfrequencies/%s";
	public static final String URI_LABEL_BRANDCHANNEL = "/contacts/%s/communicationchannels/%s/brands/%s";

	/**
	 * Swagger related String Constants
	 */
	public static final String SWAGGER_TITLE = "Swagger 18F Enterprise Preferences Web Services";
	public static final String SWAGGER_DESCRIPTION = "Catalog of available 18F Preference Center web services";
	public static final String SWAGGER_TERMS_URL = "http://www.1800flowers.com/About-Us-Terms-of-Use"; 
	public static final String SWAGGER_LICENSE = "Apache 2.0";
	public static final String SWAGGER_LICENSE_URL = "http://www.apache.org/licenses/LICENSE-2.0.html";
	public static final String SWAGGER_CONTACT = "cgordon@1800flowers.com"; 
	public static final String SWAGGER_BASE_PATH = "/api/preferencecenter";
	public static final String SWAGGER_API_VERSION = "1.0.1";	
	public static final String SWAGGER_API_DEFAULT_LOCATION = "api-docs";
	public static final String SWAGGER_API_SORT_DESCRIPTION = "Valid column name to be used to sort list of resources.";
	public static final String SWAGGER_API_ENVELOPE_DESCRIPTION = "Wraps output in a data envelope.";
	public static final String SWAGGER_API_PRETTY_JSON_DESCRIPTION = "Creates readable pretty JSON output.";
	public static final String SWAGGER_API_DB_CALL_FAILED = "Database call was unsuccessful.";
	public static final String SWAGGER_API_SUCCESS = "Successful operation.";
	public static final String SWAGGER_API_CREATE_RESOURCE_SUCCESS = "Successful resource creation.";
	public static final String SWAGGER_API_UPDATE_RESOURCE_SUCCESS = "Successful resource update.";
	public static final String SWAGGER_API_DELETE_RESOURCE_SUCCESS = "Successful resource deletion.";	
	public static final String SWAGGER_API_UPDATE_DB_CALL_FAILED = "Update database call was unsuccessful";
	public static final String SWAGGER_API_INPUT_VALIDATION_FAILED = "Failed input validation.";
	public static final String SWAGGER_API_INSERT_DB_CALL_FAILED = "Insert database call was unsuccessful";
	public static final String SWAGGER_API_INPUT_DATA_INVALID = "Input data [param/request] is missing or invalid.";
	public static final String SWAGGER_API_INVALID_PARAM = "Input data [param] is missing or invalid.";
	public static final String SWAGGER_API_DB_UPDATE_DATA_ERROR_FAILED = "Update database call was unsuccessful due to data error.";
	public static final String SWAGGER_API_DB_CONNECT_FAIL = "Database connection attempt was unsuccessful.";
	public static final String SWAGGER_API_INPUT_DATA_VALIDATION_FAILED = "Input failed data valdation";
	public static final String SWAGGER_API_PING_SUCCESS = "Successful web service and database connection.";
	public static final String SWAGGER_API_DB_NO_ROWS_RETURNED = "The query for the criteria given returned no rows.";	

	/**
	 * Swagger related integer Constants
	 */
	public static final int SWAGGER_API_RESPONSE_OK = 200;
	public static final int SWAGGER_API_RESPONSE_CREATE_RESOURCE_SUCCESS = 201;
	public static final int SWAGGER_API_RESPONSE_INVALID_PARAM = 560;
	public static final int SWAGGER_API_RESPONSE_INPUT_VALIDATION_FAILED = 554;
	public static final int SWAGGER_API_RESPONSE_MISSING_PARAM = 578;	
	public static final int SWAGGER_API_RESPONSE_INSERT_DB_CALL_FAILED = 562;
	public static final int SWAGGER_API_RESPONSE_DB_CALL_FAILED = 556;
	public static final int SWAGGER_API_RESPONSE_INPUT_DATA_INVALID = 558;
	public static final int SWAGGER_API_RESPONSE_DB_CONNECT_FAIL = 556;
	public static final int SWAGGER_API_RESPONSE_DB_UPDATE_DATA_ERROR_FAILED = 561;
	public static final int SWAGGER_API_RESPONSE_DB_NO_ROWS_RETURNED = 564;	
	public static final int SWAGGER_API_RESPONSE_UPDATE_DB_CALL_FAILED = 573;
	public static final int SWAGGER_API_RESPONSE_INPUT_DATA_VALIDATION_FAILED = 557;
	public static final int SWAGGER_API_RESPONSE_UPDATE_RESOURCE_SUCCESS = 204;
	public static final int SWAGGER_API_RESPONSE_DELETE_RESOURCE_SUCCESS = 204;

	/**
	 * regular expression  String Constants
	 */
	public static final String REGEXP_VALID_FLAG = "^[Y|N]{1}$";
	public static final String REGEXP_VALID_EMAIL = "^(.+)@(.+)$";	

	/**
	 * collection Constants
	 */
	public static final Set<Integer> validatorExceptionTypes 
	= new HashSet<Integer>(Arrays.asList(RESPONSE_CODE_BAD_DATA_CREATE, RESPONSE_CODE_BAD_DATA_UPDATE, RESPONSE_CODE_BAD_DATA_IN_RESPONSE,
			RESPONSE_CODE_BAD_DATA_IN_REQUEST));

	public static final Set<Integer> databaseExceptionTypes 
	= new HashSet<Integer>(Arrays.asList(RESPONSE_CODE_DATABASE_ERROR,RESPONSE_CODE_FAIL_DELETE, RESPONSE_CODE_FAIL_UPDATE));

	public static final Set<String> outputFormatTypes 
	= new HashSet<String>(Arrays.asList(MESSAGE_STATUS_FORMAT_TEXT,MESSAGE_STATUS_FORMAT_HTML,MESSAGE_STATUS_FORMAT_XML));

	public static final Set<String> databaseTypes 
	= new HashSet<String>(Arrays.asList(PREF_DB_TYPE_ORACLE,PREF_DB_TYPE_DB2));

	public static final Set<String> requestWithEntityTypes 
	= new HashSet<String>(Arrays.asList("POST", "PUT"));

	public static final Set<Byte> validOperations 
	= new HashSet<Byte>(Arrays.asList(TYPE_OPERATION_NONE,
			TYPE_OPERATION_CREATE,TYPE_OPERATION_UPDATE,
			TYPE_OPERATION_DELETE,TYPE_OPERATION_LIST,
			TYPE_OPERATION_FIND));

	@SuppressWarnings("serial")
	public static Map<String, String> databaseDriversMap = new HashMap<String, String>() {
        {
            put(PREF_DB_TYPE_ORACLE, DBDRIVER_ORACLE);
            put(PREF_DB_TYPE_DB2, DBDRIVER_DB2);
        };
    };

	/**
	 * Performance related Constants
	 */
	public static final String PERFORMANCE_START = "Method Called";
	public static final String PERFORMANCE_EXIT = "Method Returns";
	public static final String PERFORMANCE_DB_START = "Database Called";
	public static final String PERFORMANCE_DB_EXIT = "Database Returns";
	
	public static final String PERFORMANCE_TEMPLATE_MSG = "PERFORMANCE: Label: %s, Method: %s, Timestamp: %s, Query: %s, URL: %s, SessionId: %s, Param Map: %s.";
	public static final String PERFORMANCE_LOG_FILENAME = "logFileName";
	public static final String PERFORMANCE_LOG_FILENAME_VALUE = "performance.log";
	
	/** Private no param constructor to ensure that this class is not instantiated; also final class so it cannot be extended.
	 */
	private Constants(){

	}
}


