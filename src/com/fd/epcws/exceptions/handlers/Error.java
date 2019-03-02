package com.fd.epcws.exceptions.handlers;

/**
 * @author cgordon
 *
 * Error bean class used to facilitate handling of generic application and container exceptions.
 */

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "error")
public class Error implements Comparable<Error>{

	private int statusCode;
	private String statusDescription;
	private String errorMessage;


	/** This non default constructor takes initialize params as described below. 
	 * @param The status code of the error <code>int</code>.
	 * @param The descriptive value message of the status 
	 *        <code>String</code>.             
	 * @param The descriptive error message associated with this error 
	 *        <code>String</code>.             
	 */	
	public Error(int statusCode, String statusDescription, String errorMessage) {
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
		this.errorMessage = errorMessage;
	}


	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return statusCode;
	}


	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}


	/**
	 * @return the statusDescription
	 */
	public String getStatusDescription() {
		return statusDescription;
	}


	/**
	 * @param statusDescription the statusDescription to set
	 */
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}


	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	/**
	 * required for Comparable/Comparator to function on this class.
	 * @param 'other' Error object to compare
	 * @return int representation of compare operation
	 */
	@Override
	public int compareTo(Error arg0) {
		return (statusCode >= arg0.getStatusCode())? 1 : 0;
	}


}