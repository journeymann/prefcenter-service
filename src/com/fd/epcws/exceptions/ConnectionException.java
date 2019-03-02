package com.fd.epcws.exceptions;

import com.fd.epcws.constants.Constants;

/**
 * @author cgordon
 *
 * Application <code>ConnectionException</code> exception class. 
 * Subclass of <code>AppException</code> class used specifically for database connection errors.
 * 
 */

public class ConnectionException extends AppException{

	private static final long serialVersionUID = -1206686334220441500L;

	/** Constructor method for this class
	 * 
	 * @param <code>Throwable</code> exception class or sub class
	 * @param <code>String</code> descriptive error message 
	 */	
	public ConnectionException(Throwable ex, String msg){
		super(ex,Constants.RESPONSE_CODE_DATABASE_CONNECTION, msg);
	}

}
