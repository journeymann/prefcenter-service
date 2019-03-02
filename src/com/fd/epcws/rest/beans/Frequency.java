package com.fd.epcws.rest.beans;
/**
 * @author cgordon
 *
 * Frequency resource class
 * 
 */

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fd.epcws.constants.Constants;
import com.wordnik.swagger.annotations.ApiModelProperty;

@JsonRootName(value = "frequency")
public class Frequency extends AbstractResource{

	@Min(Constants.INT_MIN_ID)
	@ApiModelProperty( value = "frequency id.", required = true )	
	private int id;

	@Size(min=Constants.INT_MIN_DESC, max=Constants.INT_MAX_DESC)
	@ApiModelProperty( value = "frequency description.", required = false )	
	private String description;

	@NotNull
	@Size(min=Constants.INT_MIN_CODE, max=Constants.INT_MAX_CODE)
	@ApiModelProperty( value = "frequency code.", required = true )	
	private String code;

	@Size(min=Constants.INT_MIN_FLAG, max=Constants.INT_MAX_FLAG)	
	@Pattern(regexp=Constants.REGEXP_VALID_FLAG, message ="Valid value for this field is either Y or N")	
	@ApiModelProperty( value = "flags status of frequency.", required = false )	
	private String active;

	/** Default no param constructor for the this class 
	 */	
	public Frequency() {

	}

	/** Constructor method for this class
	 * @param <code>String</code> frequency description
	 * @param <code>String</code> frequency code
	 * @param <code>int</code> unique identifier
	 * @param <code>String</code> is active indicator flag  
	 */	
	public Frequency(String description, String code, int id, String active){

		this.description = description;
		this.code = code;
		this.id = id;
		this.active = active;
	}

	/**
	 * @return the id
	 */
	@JsonIgnore    
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 *  bean getter convenience method for class identifier used to differentiate between sub classes of AbstractResource
	 * @return <code>int</code> value for description
	 */
	@JsonIgnore	
	@ApiModelProperty(access="hidden", required=false )	
	public int getResourceType(){
		return Constants.TYPE_FREQUENCY_RESOURCE;
	}

	/** 
	 *  bean getter convenience method for class identifier used to construct JSON object for output
	 * @return <code>String</code> value for description
	 */
	@JsonIgnore	
	@ApiModelProperty(access="hidden", required=false )	
	public String getResourceTypeLabel(){
		return Constants.JSON_DATA_LABEL_FREQUENCY;
	}

	/**
	 * @return the Frequency - added to fortify JSON web service so that input will be read regardless of format i.e. wrapper envelope 
	 * included or not
	 */
	@JsonIgnore	
	public Frequency getFrequency(){
		return this; 
	}

	/**
	 * @param Frequency the Frequency to set
	 * added to fortify JSON web service so that input will be read regardless of format i.e. wrapper envelope 
	 * included or not
	 */
	@JsonProperty		
	public void setFrequency(Frequency t){
		id = t.getId();
		description = t.getDescription();
		code = t.getCode();
		active =  t.getActive();
	}
	/**
	 * @return the <code>String</code> description for logging etc 
	 */
	@JsonIgnore
	@Override
	public String toString(){
		return String.format("[label:%s,id:%s,description:%s,code:%s,active:%s.]",Constants.JSON_DATA_LABEL_FREQUENCY,
				id,description,code,active); 
	}


}    
