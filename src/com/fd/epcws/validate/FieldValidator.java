/**
 * 
 */
package com.fd.epcws.validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

import com.fd.epcws.constants.Constants;
import com.fd.epcws.rest.beans.AbstractResource;
import com.fd.epcws.rest.beans.Brand;
import com.fd.epcws.rest.beans.BrandChannel;
import com.fd.epcws.rest.beans.Channel;
import com.fd.epcws.rest.beans.ChannelFrequency;
import com.fd.epcws.rest.beans.Contact;
import com.fd.epcws.rest.beans.Frequency;
import com.fd.epcws.util.helpers.LoggingHelper;

/**
 * @author cgordon
 *
 */
public class FieldValidator {

	private Logger LOGGER = LoggingHelper.getLogger(FieldValidator.class);
	private final String CLASSNAME = "FieldValidator";


	/** This method validate a <code>AbstractResource</code> object based on the using the apache fieldValidator 
	 * @param <code>AbstractResource</code> AbstractResource class to be validated       
	 * @returns <code>String</code> String description of errors 
	 */
	public String validate(AbstractResource model){
		String METHODNAME="validate OL1";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		/** preValidate performs basic required field checks here */
		model = prevalidate(model);

		String errors = (StringUtils.isNotEmpty(model.getErrMsg()))? model.getErrMsg() : Constants.STRING_BLANK;

		Validate validator = new Validate();	   

		if(!validator.validate(model).isEmpty()){
			Object obj = validator.validate(model);
			if(obj instanceof Set){
				@SuppressWarnings("unchecked")
				Set<ConstraintViolation<AbstractResource>> e_set = (Set<ConstraintViolation<AbstractResource>>)obj; 
				errors += e_set.toString();
			}else{
				@SuppressWarnings("unchecked")        		
				Map<AbstractResource,Set<ConstraintViolation<AbstractResource>>> e_map = (HashMap<AbstractResource,Set<ConstraintViolation<AbstractResource>>>)obj;        		
				errors += e_map.toString();
			}
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return errors;
	}

	/** This method validate a <code>ArrayList</code> of AbstractResource object based on the using the apache fieldValidator 
	 * @param <code>ArrayList</code> list of AbstractResource classes to be validated       
	 * @returns <code>String</code> String description of errors 
	 */
	public String validate(ArrayList<? extends AbstractResource> list){
		String METHODNAME="validate OL2";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		String errors = new String();
		Validate validator = new Validate();	   

		if(!validator.validate(list).isEmpty()){
			Object obj = validator.validate(list);
			if(obj instanceof Set){
				@SuppressWarnings("unchecked")
				Set<ConstraintViolation<AbstractResource>> e_set = (Set<ConstraintViolation<AbstractResource>>)obj; 
				errors = e_set.toString();
			}else{
				@SuppressWarnings("unchecked")        		
				Map<AbstractResource,Set<ConstraintViolation<AbstractResource>>> e_map = (HashMap<AbstractResource,Set<ConstraintViolation<AbstractResource>>>)obj;        		
				errors = e_map.toString();
			}
		}

		if(!errors.isEmpty()){
			errors.replace(Constants.STRING_LEFT_BRACKET, Constants.MESSAGE_BLANK);
			errors.replace(Constants.STRING_RIGHT_BRACKET, Constants.MESSAGE_BLANK);
			errors.replace(Constants.STRING_ERROR_MESSAGE_DELIM, Constants.STRING_NEWLINE);
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return errors;
	}

	/** This method pre-processes <code>AbstractResource</code> object before the apache validate logic is applied.  Intended primarily to 
	 * address the problem of required fields general and particularly the use cases where the required field is inside the request body.  
	 * @param <code>AbstractResource</code> AbstractResource class to be validated       
	 * @returns <code>AbstractResource</code> String description of errors 
	 */
	public AbstractResource prevalidate(AbstractResource model){
		String METHODNAME="preValidate";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		String errors = Constants.STRING_BLANK;
		
		if(model == null) 
			return model;
		else if(!Constants.validOperations.contains(model.getOperation())
				||(model.getOperation() == Constants.TYPE_OPERATION_NONE)) return model;
		
		switch(model.getResourceType()){

		case Constants.TYPE_CHANNEL_RESOURCE:

			Channel channel = (Channel)model;
			switch(channel.getOperation()){
			case Constants.TYPE_OPERATION_CREATE:

				if(StringUtils.isEmpty(channel.getCode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE)));
				}

				if(StringUtils.isEmpty(channel.getDescription())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELDESC).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELDESC)));
				}

				if(StringUtils.isEmpty(channel.getActive())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_ACTIVE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_ACTIVE)));
				}

				if(StringUtils.isEmpty(channel.getVisible())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VISIBLE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VISIBLE)));
				}

				if(channel.getRank() == Constants.INT_ZERO){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_RANK).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_RANK)));
				}

				break;
			case Constants.TYPE_OPERATION_UPDATE:

				if(StringUtils.isEmpty(channel.getDescription())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELDESC).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELDESC)));
				}

				if(StringUtils.isEmpty(channel.getActive())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_ACTIVE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_ACTIVE)));
				}

				if(StringUtils.isEmpty(channel.getVisible())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VISIBLE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VISIBLE)));
				}

				if(channel.getRank() == Constants.INT_ZERO){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_RANK).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_RANK)));
				}

				break;

			case Constants.TYPE_OPERATION_DELETE:
			case Constants.TYPE_OPERATION_FIND:						

				if(StringUtils.isEmpty(channel.getCode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE)));
				}
				break;
			default:	
			}


			channel.setDescription(StringUtils.isEmpty(channel.getDescription())? Constants.STRING_BLANK : channel.getDescription()); 
			channel.setActive(StringUtils.isEmpty(channel.getActive())? Constants.STRING_YES : channel.getActive());
			channel.setVisible(StringUtils.isEmpty(channel.getVisible())? Constants.STRING_YES : channel.getVisible());

			break;

		case Constants.TYPE_BRAND_RESOURCE:

			Brand brand = (Brand)model;

			switch(brand.getOperation()){
			case Constants.TYPE_OPERATION_CREATE:

				if(StringUtils.isEmpty(brand.getCode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE)));
				}

				if(StringUtils.isEmpty(brand.getDescription())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDDESC).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDDESC)));
				}

				if(StringUtils.isEmpty(brand.getActive())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_ACTIVE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_ACTIVE)));
				}

				if(StringUtils.isEmpty(brand.getVisible())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VISIBLE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VISIBLE)));
				}

				if(brand.getRank() == Constants.INT_ZERO){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_RANK).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_RANK)));
				}

				break;
			case Constants.TYPE_OPERATION_UPDATE:
				if(StringUtils.isEmpty(brand.getDescription())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDDESC).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDDESC)));
				}

				if(brand.getRank() == Constants.INT_ZERO){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_RANK).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_RANK)));
				}

				break;

			case Constants.TYPE_OPERATION_DELETE:
			case Constants.TYPE_OPERATION_FIND:						

				if(StringUtils.isEmpty(brand.getCode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE)));
				}
				break;
			default:	
			}

			brand.setDescription(StringUtils.isEmpty(brand.getDescription())? Constants.STRING_BLANK : brand.getDescription()); 
			brand.setActive(StringUtils.isEmpty(brand.getActive())? Constants.STRING_YES : brand.getActive());
			brand.setVisible(StringUtils.isEmpty(brand.getVisible())? Constants.STRING_YES : brand.getVisible());

			break;

		case Constants.TYPE_CHANNELFREQUENCY_RESOURCE:			

			ChannelFrequency channelFrequency = (ChannelFrequency)model;

			switch(channelFrequency.getOperation()){

			case Constants.TYPE_OPERATION_UPDATE:
				if(channelFrequency.getChannelfrequencyid() == Constants.INT_MIN_ID){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELFREQID).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELFREQID)));
				}

				if(StringUtils.isEmpty(channelFrequency.getActive())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_ACTIVE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_ACTIVE)));
				}

				if(StringUtils.isEmpty(channelFrequency.getVisible())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VISIBLE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VISIBLE)));
				}

				if(channelFrequency.getRank() == Constants.INT_ZERO){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_RANK).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_RANK)));
				}

				break;

			case Constants.TYPE_OPERATION_CREATE:

				if(StringUtils.isEmpty(channelFrequency.getChannelcode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE)));
				}

				if(StringUtils.isEmpty(channelFrequency.getFrequencycode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_FREQUENCYCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_FREQUENCYCODE)));
				}

				if(StringUtils.isEmpty(channelFrequency.getActive())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_ACTIVE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_ACTIVE)));
				}

				if(StringUtils.isEmpty(channelFrequency.getVisible())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VISIBLE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VISIBLE)));
				}

				if(channelFrequency.getRank() == Constants.INT_ZERO){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_RANK).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_RANK)));
				}

				break;

			case Constants.TYPE_OPERATION_DELETE:
			case Constants.TYPE_OPERATION_FIND:						

				if(channelFrequency.getChannelfrequencyid() == Constants.INT_MIN_ID){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELFREQID).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELFREQID)));
				}
				break;
			case Constants.TYPE_OPERATION_LIST:

				break;

			default:	
				errors += Constants.MESSAGE_INVALID_OPERATION_ATTEMPTED.concat(Constants.STRING_ERROR_MESSAGE_DELIM);
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_INVALID_OPERATION_ATTEMPTED));

			}
			channelFrequency.setChannelcode(StringUtils.isEmpty(channelFrequency.getChannelcode())? Constants.STRING_BLANK : channelFrequency.getChannelcode());
			channelFrequency.setFrequencycode(StringUtils.isEmpty(channelFrequency.getFrequencycode())? Constants.STRING_BLANK : channelFrequency.getFrequencycode());
			channelFrequency.setActive(StringUtils.isEmpty(channelFrequency.getActive())? Constants.STRING_YES : channelFrequency.getActive());
			channelFrequency.setVisible(StringUtils.isEmpty(channelFrequency.getVisible())? Constants.STRING_YES : channelFrequency.getVisible());

			break;		    	
		case Constants.TYPE_CONTACT_RESOURCE:	

			Contact contact = (Contact)model;			

			switch(contact.getOperation()){		

			case Constants.TYPE_OPERATION_CREATE:

				if(StringUtils.isEmpty(contact.getEmail())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_EMAIL).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_EMAIL)));
				}

				break;
			case Constants.TYPE_OPERATION_UPDATE:				

				if(contact.getContactid() == Constants.INT_MIN_ID){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
				}

				if(StringUtils.isEmpty(contact.getEmail())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_EMAIL).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_EMAIL)));
				}

				if(StringUtils.isEmpty(contact.getUsermodified())){

					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_USERMODIFIED).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_USERMODIFIED)));
				}

				contact.setCountrycode(StringUtils.isNotEmpty(contact.getCountrycode())? contact.getCountrycode() : Constants.STR_DEFAULT_COUNTRY_CODE);
				contact.setEmail(StringUtils.isEmpty(contact.getEmail())? Constants.STRING_BLANK : contact.getEmail()); 
				contact.setUsermodified(StringUtils.isEmpty(contact.getUsermodified())? Constants.STRING_BLANK : contact.getUsermodified());
				contact.setCountrycode(StringUtils.isEmpty(contact.getCountrycode())? Constants.STR_DEFAULT_COUNTRY_CODE : contact.getCountrycode());
				contact.setSms(StringUtils.isEmpty(contact.getSms())? Constants.STRING_BLANK : contact.getSms());

				break;
			case Constants.TYPE_OPERATION_DELETE:	

				if(contact.getContactid() == Constants.INT_MIN_ID){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
				}

				if(StringUtils.isEmpty(contact.getUsermodified())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_USERMODIFIED).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_USERMODIFIED)));
				}

				if(StringUtils.isEmpty(contact.getEmail())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_EMAIL).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_EMAIL)));
				}

				contact.setEmail(StringUtils.isEmpty(contact.getEmail())? Constants.STRING_BLANK : contact.getEmail()); 
				contact.setUsermodified(StringUtils.isEmpty(contact.getUsermodified())? Constants.STRING_BLANK : contact.getUsermodified());

				break;

			case Constants.TYPE_OPERATION_LIST:
			case Constants.TYPE_OPERATION_FIND:	
				if(contact.getContactid() == Constants.INT_MIN_ID){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
				}

				break;

			default:
				errors += Constants.MESSAGE_INVALID_OPERATION_ATTEMPTED.concat(Constants.STRING_ERROR_MESSAGE_DELIM);
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_INVALID_OPERATION_ATTEMPTED));
			}

			break;				
		case Constants.TYPE_BRANDCHANNEL_RESOURCE:			

			BrandChannel brandChannel = (BrandChannel)model;

			switch(brandChannel.getOperation()){

			case Constants.TYPE_OPERATION_CREATE:

				if(brandChannel.getContactid() == Constants.INT_MIN_ID){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
				}

				if(StringUtils.isEmpty(brandChannel.getBrandcode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE)));
				}

				if(StringUtils.isEmpty(brandChannel.getFrequencycode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_FREQUENCYCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_FREQUENCYCODE)));
				}

				if(StringUtils.isEmpty(brandChannel.getChannelcode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE)));
				}

				if(StringUtils.isEmpty(brandChannel.getValue())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VALUE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VALUE)));
				}

				break;	
			case Constants.TYPE_OPERATION_UPDATE:			

				if(brandChannel.getContactid() == Constants.INT_MIN_ID){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
				}

				if(StringUtils.isEmpty(brandChannel.getValue())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VALUE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VALUE)));
				}

				if(StringUtils.isEmpty(brandChannel.getFrequencycode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_FREQUENCYCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_FREQUENCYCODE)));
				}

				break;

			case Constants.TYPE_OPERATION_DELETE:

				if(brandChannel.getContactid() == Constants.INT_MIN_ID){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
				}

				if(StringUtils.isEmpty(brandChannel.getBrandcode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE)));
				}

				if(StringUtils.isEmpty(brandChannel.getChannelcode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CHANNELCODE)));
				}

				if(StringUtils.isEmpty(brandChannel.getValue())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VALUE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_VALUE)));
				}

				/** frequency code validation will fail if required code is absent. fake for this operation (not passed nor used by database) */
				brandChannel.setFrequencycode(StringUtils.isEmpty(brandChannel.getFrequencycode())? Constants.STRING_CODE_FAKE : brandChannel.getFrequencycode());
				break;
			case Constants.TYPE_OPERATION_FIND:		
				if(brandChannel.getContactid() == Constants.INT_MIN_ID){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_CONTACTID)));
				}

				break;
			case Constants.TYPE_OPERATION_LIST:

				break;
			default:
				errors += Constants.MESSAGE_INVALID_OPERATION_ATTEMPTED.concat(Constants.STRING_ERROR_MESSAGE_DELIM);
			LOGGER.error(LoggingHelper.formatMessage(this.getClass(),Constants.MESSAGE_INVALID_OPERATION_ATTEMPTED));
			}
			break;
		case Constants.TYPE_FREQUENCY_RESOURCE:	

			Frequency frequency = (Frequency)model;

			switch(frequency.getOperation()){

			case Constants.TYPE_OPERATION_CREATE:

				if(StringUtils.isEmpty(frequency.getCode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_FREQUENCYCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_FREQUENCYCODE)));
				}
				if(StringUtils.isEmpty(frequency.getDescription())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_FREQUENCYDESC).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_FREQUENCYDESC)));
				}

				break;
			case Constants.TYPE_OPERATION_UPDATE:				    	
				if(StringUtils.isEmpty(frequency.getCode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDCODE)));
				}

				if(StringUtils.isEmpty(frequency.getDescription())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDDESC).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_BRANDDESC)));
				}

				if(StringUtils.isEmpty(frequency.getActive())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_ACTIVE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_ACTIVE)));
				}
				break;
			case Constants.TYPE_OPERATION_DELETE:
				if(StringUtils.isEmpty(frequency.getCode())){
					errors+=String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_FREQUENCYCODE).concat(Constants.STRING_ERROR_MESSAGE_DELIM);;
					LOGGER.error(LoggingHelper.formatMessage(this.getClass(),String.format(Constants.MESSAGE_REQUIRED_REQ_PARAM_FIELD_INCLUDE,Constants.FIELD_FREQUENCYCODE)));
				}
				break;
			default:	
			}
			frequency.setDescription(StringUtils.isEmpty(frequency.getDescription())? Constants.STRING_BLANK : frequency.getDescription()); 
			frequency.setActive(StringUtils.isEmpty(frequency.getActive())? Constants.STRING_YES : frequency.getActive());

			break;
		}	

		model.setErrMsg(errors);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return model;
	}

}
