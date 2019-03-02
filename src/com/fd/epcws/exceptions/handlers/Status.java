package com.fd.epcws.exceptions.handlers;

/**
 * @author cgordon
 *
 * Error bean class used to facilitate handling of generic application and container exceptions.
 */

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "status")
public class Status implements Comparable<Status>{

	private int statusCode;
	private String statusDescription;
	private String statusMessage;


	/** This non default constructor takes initialize params as described below. 
	 * @param The status code of the status <code>int</code>.
	 * @param The descriptive value message of the status 
	 *        <code>String</code>.             
	 * @param The descriptive status message associated with this status 
	 *        <code>String</code>.             
	 */	
	public Status(int statusCode, String statusDescription, String statusMessage) {
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
		this.statusMessage = statusMessage;
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
	 * @return the statusMessage
	 */
	public String getErrorMessage() {
		return statusMessage;
	}

	/**
	 * @param statusMessage the statusMessage to set
	 */
	public void setErrorMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}


	/**
	 * required for Comparable/Comparator to function on this class.
	 * @param 'other' Error object to compare
	 * @return int representation of compare operation
	 */
	@Override
	public int compareTo(Status arg0) {
		return (statusCode >= arg0.getStatusCode())? 1 : 0;
	}


}