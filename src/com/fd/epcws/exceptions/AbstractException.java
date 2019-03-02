package com.fd.epcws.exceptions;

/**
 * @author cgordon
 *
 * Application <code>AbstractException</code> exception class. Parent class for the concrete classes used in the application.
 * 
 */

public abstract class AbstractException extends Exception{

	private static final long serialVersionUID = -856355412362006329L;
	protected Throwable exception;
	protected String message;
	protected int responseCode;
	protected boolean envelope = false;	

	/** Bean get method for getting the <code>Throwable</code> exception private variable.
	 * @return <code>Throwable</code> exception class or sub class
	 */	
	public Throwable getException() {
		return exception;
	}

	/** Bean set method for setting the <code>Throwable</code> exception private variable.
	 * @param The exception variable to be maintained by the bean 
	 *        <code>Throwable</code>.             
	 */	
	public void setException(Throwable exception) {
		this.exception = exception;
	}

	/** Bean get method for getting the <code>Throwable</code> exception private variable.
	 * @return <code>String</code> descriptive error message 
	 */	
	public String getErrorMessage() {
		return message;
	}

	/** Bean set method for setting the <code>String</code> message private variable.
	 * @param The exception variable to be maintained by the bean 
	 *        <code>Throwable</code>.             
	 */	
	public void setErrorMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the responseCode
	 */
	protected abstract int getResponseCode();

	/**
	 * @return the envelope
	 */
	public boolean isEnvelope() {
		return envelope;
	}

	/**
	 * @param envelope the envelope to set
	 */
	public void setEnvelope(boolean envelope) {
		this.envelope = envelope;
	}



}
