package com.fd.epcws.rest.beans;
/**
 * @author cgordon
 *
 * Communication Channel resource class
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

@JsonRootName(value = "communicationchannel")
public class Channel extends AbstractResource{

	@Size(min=Constants.INT_MIN_DESC, max=Constants.INT_MAX_DESC)
	@ApiModelProperty( value = "channel description.", required = false )	
	private String description;

	@Min(Constants.INT_MIN_RANK)
	@ApiModelProperty( value = "determines position a sequence of channels.", required = false )	
	private int rank = Constants.INT_DEFAULT_RANK;

	@NotNull
	@Size(min=Constants.INT_MIN_CODE, max=Constants.INT_MAX_CODE)
	@ApiModelProperty( value = "channel code.", required = true )	
	private String code;

	@Size(min=Constants.INT_MIN_FLAG, max=Constants.INT_MAX_FLAG)	
	@Pattern(regexp=Constants.REGEXP_VALID_FLAG, message ="Valid value for this field is either Y or N")	
	@ApiModelProperty( value = "flags status of channel.", required = false )	
	private String active;

	@Size(min=Constants.INT_MIN_FLAG, max=Constants.INT_MAX_FLAG)	
	@Pattern(regexp=Constants.REGEXP_VALID_FLAG, message ="Valid value for this field is either Y or N")	
	@ApiModelProperty( value = "flags channel visibility.", required = false )	
	private String visible;

	/** Default no param constructor for the this class 
	 */	
	public Channel() {
	}

	/** Constructor method for this class
	 * @param <code>String</code> channel description
	 * @param <code>int</code> channel rank
	 * @param <code>String</code> channel code
	 * @param <code>char</code> channel active boolean flag
	 * @param <code>char</code> channel visible boolean flag
	 */	
	public Channel( String p_description, int p_rank, String p_code, String p_active, String p_visible){
		this.description = p_description;
		this.rank = p_rank;
		this.code = p_code;
		this.active = p_active;
		this.visible = p_visible;
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
	 * @return the rank
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * @param rank the rank to set
	 */
	public void setRank(int rank) {
		this.rank = rank;
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
	 * @return the visible
	 */
	public String getVisible() {
		return visible;
	}

	/**
	 * @param visible the visible to set
	 */
	public void setVisible(String visible) {
		this.visible = visible;
	}

	/** 
	 *  bean getter convenience method for class identifier used to differentiate between sub classes of AbstractResource
	 * @return <code>int</code> value for description
	 */
	@JsonIgnore	
	@ApiModelProperty(access="hidden", required=false )	
	public int getResourceType(){
		return Constants.TYPE_CHANNEL_RESOURCE;
	}

	/** 
	 *  bean getter convenience method for class identifier used to construct JSON object for output
	 * @return <code>String</code> value for description
	 */
	@JsonIgnore
	@ApiModelProperty(access="hidden", required=false )	
	public String getResourceTypeLabel(){
		return Constants.JSON_DATA_LABEL_CHANNEL;
	}

	/**
	 * @return the Channel - added to fortify JSON web service so that input will be read regardless of format i.e. wrapper envelope 
	 * included or not
	 */
	@JsonIgnore	
	public Channel getCommunicationchannel(){
		return this; 
	}

	/**
	 * @param Channel the Channel to set
	 * added to fortify JSON web service so that input will be read regardless of format i.e. wrapper envelope 
	 * included or not
	 */
	@JsonProperty		
	public void setCommunicationchannel(Channel t){
		description = t.getDescription();
		rank = t.getRank();
		active =  t.getActive();
		visible = t.getVisible();
		code = t.getCode();
	}

	/**
	 * @return the <code>String</code> description for logging etc 
	 */
	@JsonIgnore
	@Override
	public String toString(){
		return String.format("[label:%s,description:%s,rank:%s,code:%s,active:%s,visible:%s.]",Constants.JSON_DATA_LABEL_CHANNEL,description,rank,code,active,visible); 
	}

}    
