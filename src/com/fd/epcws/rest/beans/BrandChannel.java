package com.fd.epcws.rest.beans;
/**
 * @author cgordon
 *
 * Brand Communication Channel resource class
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

@JsonRootName(value = "brandcommunication")
public class BrandChannel extends AbstractResource{

	@NotNull
	@Min(Constants.INT_MIN_ID)
	@ApiModelProperty( value = "enterprise contact id number.", required = true )	
	private long contactid;

	@Min(Constants.INT_MIN_ID)
	@ApiModelProperty( value = "brand channel unique id number.", required = false )	
	private String id;

	@NotNull
	@Size(min=Constants.INT_MIN_CODE, max=Constants.INT_MAX_CODE)
	@ApiModelProperty( value = "channel code.", required = true )	
	private String channelcode;

	@Size(min=Constants.INT_MIN_DESC, max=Constants.INT_MAX_DESC)
	@ApiModelProperty( value = "channel description.", required = false )	
	private String channelname;    

	@NotNull
	@Size(min=Constants.INT_MIN_CODE, max=Constants.INT_MAX_CODE)
	@ApiModelProperty( value = "brand code.", required = true )	
	private String brandcode;    

	@Size(min=Constants.INT_MIN_DESC, max=Constants.INT_MAX_DESC)
	@ApiModelProperty( value = "brand description.", required = false )	
	private String brandname;    

	@NotNull
	@Size(min=Constants.INT_MIN_CODE, max=Constants.INT_MAX_CODE)
	@ApiModelProperty( value = "frequency code.", required = true )	
	private String frequencycode;

	@Size(min=Constants.INT_MIN_DESC, max=Constants.INT_MAX_DESC)
	@ApiModelProperty( value = "frequency description.", required = false )	
	private String frequencyname;

	@Min(Constants.INT_MIN_RANK)
	@ApiModelProperty( value = "determines position in a sequence of brand channels.", required = false )
	private int rank = Constants.INT_DEFAULT_RANK;    

	@Pattern(regexp=Constants.REGEXP_VALID_FLAG, message ="Valid value for this field is either Y or N")	
	@ApiModelProperty( value = "flags status of brand channel.", required = false )	
	private String active;

	@Pattern(regexp=Constants.REGEXP_VALID_FLAG, message ="Valid value for this field is either Y or N")	
	@ApiModelProperty( value = "flags brand channel visibility.", required = false )	
	private String visible;

	@ApiModelProperty( value = "brand channel arbitrary value.", required = false )	
	private String value;    


	/** Default no param constructor for the this class 
	 */	
	public BrandChannel() {
		super();
	}

	/** Constructor method for this class
	 * @param <code>long</code> brand channel id key
	 * @param <code>String</code> channel code
	 * @param <code>String</code> channel description
	 * @param <code>String</code> brand code
	 * @param <code>String</code> brand description
	 * @param <code>String</code> brandchannel value
	 * @param <code>String</code> frequency code
	 * @param <code>String</code> frequency description
	 * @param <code>int</code> brand channel rank 
	 * @param <code>char</code> brand channel active boolean flag
	 * @param <code>char</code> brand channel visible boolean flag
	 */	
	public BrandChannel(long id, String pchannelcode, String pchanneldesc, String pbrandcode, String pbranddesc,String pvalue, String pfrequencycode,String pfrequencydesc,
			int prank, String pactive,String pvisible){

		this.contactid = id;
		this.channelcode = pchannelcode;
		this.channelname = pchanneldesc;
		this.brandcode = pbrandcode;
		this.brandname = pbranddesc;    	
		this.rank=prank;
		this.value=pvalue;
		this.frequencycode=pfrequencycode;
		this.frequencyname=pfrequencydesc;    	
		this.active=pactive;
		this.visible=pvisible;
	}

	/**
	 * @return the id
	 */
	@JsonIgnore
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the channelcode
	 */
	public String getChannelcode() {
		return channelcode;
	}

	/**
	 * @param channelcode the channelcode to set
	 */
	public void setChannelcode(String channelcode) {
		this.channelcode = channelcode;
	}

	/**
	 * @return the brandcode
	 */
	public String getBrandcode() {
		return brandcode;
	}

	/**
	 * @param brandcode the brandcode to set
	 */
	public void setBrandcode(String brandcode) {
		this.brandcode = brandcode;
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
		return Constants.TYPE_BRANDCHANNEL_RESOURCE;
	}

	/**
	 * @return the contactid
	 */
	public long getContactid() {
		return contactid;
	}

	/**
	 * @param contactid the contactid to set
	 */
	public void setContactid(long contactid) {
		this.contactid = contactid;
	}

	/**
	 * @return the channelname
	 */
	public final String getChannelname() {
		return channelname;
	}

	/**
	 * @param channelname the channelname to set
	 */
	public final void setChannelname(String channelname) {
		this.channelname = channelname;
	}

	/**
	 * @return the brandname
	 */
	public final String getBrandname() {
		return brandname;
	}

	/**
	 * @param brandname the brandname to set
	 */
	public final void setBrandname(String brandname) {
		this.brandname = brandname;
	}

	/**
	 * @return the frequencycode
	 */
	public final String getFrequencycode() {
		return frequencycode;
	}

	/**
	 * @param frequencycode the frequencycode to set
	 */
	public final void setFrequencycode(String frequencycode) {
		this.frequencycode = frequencycode;
	}

	/**
	 * @return the frequencyname
	 */
	public final String getFrequencyname() {
		return frequencyname;
	}

	/**
	 * @param frequencyname the frequencyname to set
	 */
	public final void setFrequencyname(String frequencyname) {
		this.frequencyname = frequencyname;
	}

	/**
	 * @return the BrandChannel - added to fortify JSON web service so that input will be read regardless of format i.e. wrapper envelope 
	 * included or not
	 */
	@JsonIgnore	
	public BrandChannel getBrandcommunication(){
		return this; 
	}

	/** 
	 *  bean getter convenience method for class identifier used to construct JSON object for output
	 * @return <code>String</code> value for description
	 */
	@JsonIgnore
	@ApiModelProperty(access="hidden", required=false )	
	public String getResourceTypeLabel(){
		return Constants.JSON_DATA_LABEL_BRANDCHANNEL;
	}

	/**
	 * @param BrandChannel the BrandChannel to set
	 * added to fortify JSON web service so that input will be read regardless of format i.e. wrapper envelope 
	 * included or not
	 */
	@JsonProperty		
	public void setBrandcommunication(BrandChannel t){
		frequencycode=t.getFrequencycode();
		frequencyname=t.getFrequencyname();    	
		rank = t.getRank();
		active =  t.getActive();
		visible = t.getVisible();
		contactid = t.getContactid();
		id = t.getId();
		channelcode = t.getChannelcode();
		channelname = t.getChannelcode();		
		brandcode = t.getBrandcode();
		brandname = t.getBrandname();		
		value = t.getValue();
	}

	/**
	 * @return the <code>String</code> description for logging etc 
	 */
	@JsonIgnore
	@Override
	public String toString(){
		return String.format("[label:%s,channelcode:%s,brandcode:%s,frequencycode:%s,contactid:%s,rank:%s,id:%s,channelcode:%s,brandcode:%s,frequencycode:%s," +
				"value:%s,active:%s,visible:%s.]",Constants.JSON_DATA_LABEL_BRANDCHANNEL,
				channelcode,brandcode,frequencycode,contactid,rank,id,channelname,brandname,frequencyname,value,active,visible); 
	}

}
