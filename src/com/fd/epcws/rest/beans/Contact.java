package com.fd.epcws.rest.beans;
/**
 * @author cgordon
 *
 * Communication Contact resource class
 * 
 */

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
//import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fd.epcws.providers.JsonStdDateSerializer;

import com.fd.epcws.constants.Constants;
import com.wordnik.swagger.annotations.ApiModelProperty;

@JsonRootName(value = "contact")
public class Contact extends AbstractResource{

	@Min(Constants.INT_MIN_ID)
	@ApiModelProperty( value = "enterprise contact id number.", required = false )	
	private long contactid;

	@Size(min=Constants.INT_MIN_IDENT, max=Constants.INT_MAX_IDENT)
	@ApiModelProperty( value = "contact identifier.", required = false )	
	private String identifier;

	@Pattern(regexp=Constants.REGEXP_VALID_EMAIL)
	@ApiModelProperty( value = "contact email address.", required = false )	
	private String email;

	@Size(min=Constants.INT_MIN_CODE, max=Constants.INT_MAX_CODE)
	@ApiModelProperty( value = "country code.", required = true )	
	private String countrycode;

	@Size(min=Constants.INT_MIN_CODE, max=Constants.INT_MAX_VALUE)
	@ApiModelProperty( value = "country name.")	
	private String countryname;

	@Size(min=Constants.INT_MIN_VALUE, max=Constants.INT_MAX_VALUE)
	@ApiModelProperty( value = "contact value.", required = false )	
	private String value;

	@Size(min=Constants.INT_MIN_SMS, max=Constants.INT_MAX_SMS)
	@ApiModelProperty( value = "sms number.", required = false )	
	private String sms;

	///@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="mm/dd/yyyy", timezone="EST")
	@ApiModelProperty( value = "contact modification date.", required = false )	
	private Date datemodified;

	@Size(min=Constants.INT_MIN_USERNAME, max=Constants.INT_MAX_USERNAME)
	@ApiModelProperty( value = "last user to modifiy contact.", required = false )	
	private String usermodified;

	@Size(min=Constants.INT_MIN_FLAG, max=Constants.INT_MAX_FLAG)	
	@Pattern(regexp=Constants.REGEXP_VALID_FLAG, message ="Valid value for this field is either Y or N")	
	@ApiModelProperty( value = "flags status of contact.", required = false )	
	private String active;

	/** Default no param constructor for the this class 
	 */	
	public Contact() {

	}

	/** Constructor method for this class
	 * @param <code>long</code> unique identifier
	 * @param <code>String</code> email address  
	 * @param <code>String</code> contact identifier
	 * @param <code>String</code> sms phone/text/email string
	 * @param <code>String</code> value field 
	 * @param <code>Date</code> contact last modification date
	 * @param <code>String</code> contact user id of last modifier
	 * @param <code>char</code> contact active boolean flag
	 */	
	public Contact(long p_id, String p_email, String p_identifier, String p_sms,String p_countrycode,String p_countryname,String p_value,Date p_datemodified, String p_usermodified, String p_active){

		this.contactid = p_id;
		this.email = p_email;
		this.identifier = p_identifier;
		this.sms = p_sms;
		this.value = p_value;
		this.datemodified = p_datemodified;
		this.usermodified = p_usermodified;
		this.active = p_active;
		this.countrycode = p_countrycode;
		this.countryname = p_countryname;	    

	}

	/**
	 * @return the Contactid
	 */
	public long getContactid() {
		return contactid;
	}

	/**
	 * @param id the id to set
	 */
	public void setContactid(long id) {
		this.contactid = id;
	}

	/**
	 * @return the identifier
	 */
	public String getIdentifier() {
		return identifier;
	}

	/**
	 * @param identifier the identifier to set
	 */
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**
	 * @return the datemodified
	 */
	@JsonSerialize(using=JsonStdDateSerializer.class) 
	public Date getDatemodified() {
		return datemodified;
	}

	/**
	 * @param datemodified the datemodified to set
	 */
	public void setDatemodified(Date datemodified) {
		this.datemodified = datemodified;
	}

	/**
	 * @return the usermodified
	 */
	public String getUsermodified() {
		return usermodified;
	}

	/**
	 * @param usermodified the usermodified to set
	 */
	public void setUsermodified(String usermodified) {
		this.usermodified = usermodified;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the sms
	 */
	public String getSms() {
		return sms;
	}

	/**
	 * @param sms the sms to set
	 */
	public void setSms(String sms) {
		this.sms = sms;
	}

	/**
	 * @return the countrycode
	 */
	public String getCountrycode() {
		return countrycode;
	}

	/**
	 * @param countrycode the countrycode to set
	 */
	public void setCountrycode(String countrycode) {
		this.countrycode = countrycode;
	}

	/**
	 * @return the countryname
	 */
	public String getCountryname() {
		return countryname;
	}

	/**
	 * @param countryname the countryname to set
	 */
	public void setCountryname(String countryname) {
		this.countryname = countryname;
	}

	/** 
	 *  bean getter convenience method for class identifier used to differentiate between sub classes of AbstractResource
	 * @return <code>int</code> value for description
	 */
	@JsonIgnore	
	@ApiModelProperty(access="hidden", required=false )	
	public int getResourceType(){
		return Constants.TYPE_CONTACT_RESOURCE;
	}

	/** 
	 *  bean getter convenience method for class identifier used to construct JSON object for output
	 * @return <code>String</code> value for description
	 */
	@JsonIgnore	
	@ApiModelProperty(access="hidden", required=false )	
	public String getResourceTypeLabel(){
		return Constants.JSON_DATA_LABEL_CONTACT;
	}

	/**
	 * @return the Contact - added to fortify JSON web service so that input will be read regardless of format i.e. wrapper envelope 
	 * included or not
	 */
	@JsonIgnore	
	public Contact getContact(){
		return this; 
	}

	/**
	 * @param Contact the Contact to set
	 * added to fortify JSON web service so that input will be read regardless of format i.e. wrapper envelope 
	 * included or not
	 */
	@JsonProperty		
	public void setContact(Contact t){
		identifier = t.getIdentifier();
		contactid = t.getContactid();
		active =  t.getActive();
		email = t.getEmail();
		value = t.getValue();
		sms = t.getSms();
		usermodified = t.getUsermodified();
		datemodified = t.getDatemodified();
		countrycode = t.getCountrycode();
		countryname = t.getCountryname();	    
	}

	/**
	 * @return the <code>String</code> description for logging etc 
	 */
	@JsonIgnore
	@Override
	public String toString(){
		return String.format("[label:%s,contactid:%s,identifier:%s,email:%s,countrycode:%s,countryname:%s,value:%s,active:%s,sms:%s,usermodified:%s,datemodified:%s.]",
				Constants.JSON_DATA_LABEL_CONTACT,contactid,identifier,email,countrycode,countryname,value,active,sms,usermodified,datemodified); 
	}

}    
