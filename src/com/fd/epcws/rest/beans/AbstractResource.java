package com.fd.epcws.rest.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fd.epcws.constants.Constants;

/**
 * @author cgordon
 *
 * Parent/super class of all resource classes used in preference center application
 * 
 */

public abstract class AbstractResource implements Cloneable{

	private int returnid = Constants.INT_MINUS_ONE;
	private String errMsg = Constants.STRING_BLANK;	
	private byte operation = Constants.TYPE_OPERATION_NONE;

	/** This creates a clone of this object. Useful in cases when there are iterations where java will use pass by reference logic  
	 * @return <code>Object</code> generic object of this class/sub class
	 * @exception <code>CloneNotSupportedException</code> If an CloneNotSupportedException error occurs.
	 */	
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}    

	/** This method returns the convenient type indicator constant of the underlying implementing concrete class.    
	 * @return <code>int</code> type constant for concrete class
	 */	
	public abstract int getResourceType();

	/** This method returns the convenient <code>String</code> type descriptive label of the underlying implementing concrete class.    
	 * @return <code>String</code> type constant for concrete class
	 */	
	public abstract String getResourceTypeLabel();

	/**
	 * @return the returnid
	 */
	@JsonIgnore	
	public final int getReturnid() {
		return returnid;
	}

	/**
	 * @param returnid the returnid to set
	 */
	public final void setReturnid(int returnid) {
		this.returnid = returnid;
	}

	/**
	 * @return the errMsg
	 */
	@JsonIgnore	
	public final String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param errMsg the errMsg to set
	 */
	public final void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * @return the operation
	 */
	@JsonIgnore	
	public byte getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(byte operation) {
		this.operation = operation;
	}



}