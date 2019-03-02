package com.fd.epcws.exceptions;

import com.fd.epcws.constants.Constants;

/**
 * @author cgordon
 *
 * Application <code>AppException</code> exception class. 
 * Subclass of <code>AbstractException</code> class used as concrete implementation of abstract super class. Parent class of normal exceptions.
 */

public class AppException extends AbstractException{

	private static final long serialVersionUID = 971883083714339823L;

	private String errors = new String();

	/** Default no param constructor method for this class
	 */	
	public AppException(){

	}

	/** Constructor method for this class
	 * 
	 * @param <code>Throwable</code> exception class or sub class
	 * @param <code>String</code> descriptive error message 
	 */	
	public AppException(Throwable ex, String msg){
		exception = ex;
		message = msg;
		responseCode = Constants.RESPONSE_CODE_ABSTRACT;
	}

	/** Constructor method for this class
	 * 
	 * @param <code>Throwable</code> exception class or sub class
	 * @param <code>String</code> descriptive error message 
	 */	
	public AppException(Throwable ex, int response_code, String msg){
		exception = ex;
		responseCode = response_code;
		message = msg;		
	}

	/** Constructor method for this class
	 * 
	 * @param <code>Throwable</code> exception class or sub class
	 * @param <code>int</code> response code 
	 * @param <code>String</code> descriptive error message 
	 * @param <code>String</code> detailed descriptive error messages	     
	 */	
	protected AppException(Throwable ex,int response_code, String msg, String errs){
		exception = ex;
		message = msg;
		responseCode = response_code;
		errors = errs;
	}

	/**
	 * @return the responseCode
	 */
	public int getResponseCode(){
		return responseCode; 
	}

	/**
	 * @return the errors
	 */
	public String getErrors() {
		return errors;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(String errors) {
		this.errors = errors;
	}

	/**
	 * @param errors the errors to be concatenated to existing errors
	 */
	public void addErrors(String errors) {
		this.errors += errors;
	}

}
