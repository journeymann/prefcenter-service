package com.fd.epcws.rest.beans;
/**
 * @author cgordon
 *
 * Communication Channel Frequency resource class
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

@JsonRootName(value = "communicationchannelfrequency")
public class ChannelFrequency extends AbstractResource{

	@Min(Constants.INT_MIN_ID)
	@ApiModelProperty( value = "channel frequency id number.", required = false )	
	private int channelfrequencyid;

	@NotNull
	@Size(min=Constants.INT_MIN_CODE, max=Constants.INT_MAX_CODE)
	@ApiModelProperty( value = "channel code.", required = true )	
	private String channelcode;

	@Size(min=Constants.INT_MIN_DESC, max=Constants.INT_MAX_DESC)
	@ApiModelProperty( value = "channel description.", required = false )	
	private String channeldesc;    

	@NotNull
	@Size(min=Constants.INT_MIN_CODE, max=Constants.INT_MAX_CODE)
	@ApiModelProperty( value = "frequency code.", required = true )	
	private String frequencycode;    

	@Size(min=Constants.INT_MIN_DESC, max=Constants.INT_MAX_DESC)
	@ApiModelProperty( value = "frequency description.", required = false )	
	private String frequencydesc;

	@Min(Constants.INT_MIN_RANK)
	@ApiModelProperty( value = "determines position a sequence of channel frequencies.", required = false )	
	private int rank = Constants.INT_DEFAULT_RANK;    

	@Size(min=Constants.INT_MIN_FLAG, max=Constants.INT_MAX_FLAG)	
	@Pattern(regexp=Constants.REGEXP_VALID_FLAG, message ="Valid value for this field is either Y or N")	
	@ApiModelProperty( value = "flags status of channel frequency.", required = false )	
	private String active;

	@Size(min=Constants.INT_MIN_FLAG, max=Constants.INT_MAX_FLAG)	
	@Pattern(regexp=Constants.REGEXP_VALID_FLAG, message ="Valid value for this field is either Y or N")	
	@ApiModelProperty( value = "flags channel frequency visibility.", required = false )	
	private String visible;

	/** Default no param constructor for the this class 
	 */	
	public ChannelFrequency() {
		super();
	}

	/** Constructor method for this class
	 * @param <code>String</code> channel frequency channel code
	 * @param <code>String</code> channel frequency channel description
	 * @param <code>String</code> channel frequency frequency code
	 * @param <code>String</code> channel frequency frequency description
	 * @param <code>int</code> channel frequency rank 
	 * @param <code>char</code> channel frequency active boolean flag
	 * @param <code>char</code> channel frequency visible boolean flag
	 */	
	public ChannelFrequency(int pchannelfrequencyid, String pchannelcode, String pchanneldesc, String pfrequencycode, String pfrequencydesc, int prank, String pactive, String pvisible){

		this.channelfrequencyid = pchannelfrequencyid;
		this.channelcode = pchannelcode;
		this.channeldesc = pchanneldesc;
		this.frequencycode = pfrequencycode;
		this.frequencydesc = pfrequencydesc;
		this.rank=prank;
		this.active=pactive;
		this.visible=pvisible;

	}

	/**
	 * @return the channelfrequencyid
	 */
	public int getChannelfrequencyid() {
		return channelfrequencyid;
	}

	/**
	 * @param channelfrequencyid the channelfrequencyid to set
	 */
	public void setChannelfrequencyid(int channelfrequencyid) {
		this.channelfrequencyid = channelfrequencyid;
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
	 * @return the channeldesc
	 */
	public String getChanneldesc() {
		return channeldesc;
	}

	/**
	 * @param channeldesc the channeldesc to set
	 */
	public void setChanneldesc(String channeldesc) {
		this.channeldesc = channeldesc;
	}

	/**
	 * @return the frequencycode
	 */
	public String getFrequencycode() {
		return frequencycode;
	}

	/**
	 * @param frequencycode the frequencycode to set
	 */
	public void setFrequencycode(String frequencycode) {
		this.frequencycode = frequencycode;
	}

	/**
	 * @return the frequencydesc
	 */
	public String getFrequencydesc() {
		return frequencydesc;
	}

	/**
	 * @param frequencydesc the frequencydesc to set
	 */
	public void setFrequencydesc(String frequencydesc) {
		this.frequencydesc = frequencydesc;
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
		return Constants.TYPE_CHANNELFREQUENCY_RESOURCE;
	}

	/** 
	 *  bean getter convenience method for class identifier used to construct JSON object for output
	 * @return <code>String</code> value for description
	 */
	@JsonIgnore
	@ApiModelProperty(access="hidden", required=false )	
	public String getResourceTypeLabel(){
		return Constants.JSON_DATA_LABEL_CHANNELFREQUENCY;
	}

	/**
	 * @return the ChannelFrequency - added to fortify JSON web service so that input will be read regardless of format i.e. wrapper envelope 
	 * included or not
	 */
	@JsonIgnore	
	public ChannelFrequency getCommunicationchannelfrequency(){
		return this; 
	}

	/**
	 * @param ChannelFrequency the ChannelFrequency to set
	 * added to fortify JSON web service so that input will be read regardless of format i.e. wrapper envelope 
	 * included or not
	 */
	@JsonProperty		
	public void setCommunicationchannelfrequency(ChannelFrequency t){
		channelfrequencyid = t.getChannelfrequencyid();
		rank = t.getRank();
		channelcode = t.getChannelcode();
		channeldesc = t.getChanneldesc();	    
		active =  t.getActive();
		visible = t.getVisible();
		frequencydesc = t.getFrequencydesc();
		frequencycode = t.getFrequencycode();	    
	}

	/**
	 * @return the <code>String</code> description for logging etc 
	 */
	@JsonIgnore
	@Override
	public String toString(){
		return String.format("[label:%s,channelfrequencyid:%s,rank:%s,channelcode:%s,channeldesc:%s,active:%s,visible:%s,frequencycode:%s,frequencydesc:%s.]",Constants.JSON_DATA_LABEL_CHANNELFREQUENCY,
				channelfrequencyid,rank,channelcode,channeldesc,active,visible,frequencycode,frequencydesc); 
	}

}
