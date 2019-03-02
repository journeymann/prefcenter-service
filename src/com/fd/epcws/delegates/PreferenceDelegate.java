package com.fd.epcws.delegates;

import com.fd.epcws.constants.Constants;
import com.fd.epcws.db.dao.PreferenceDAO;
import com.fd.epcws.db.dao.valueobjects.ReturnValue;
import com.fd.epcws.exceptions.AppException;
import com.fd.epcws.performance.Loggable;
import com.fd.epcws.rest.beans.AbstractResource;
import com.fd.epcws.rest.beans.Brand;
import com.fd.epcws.rest.beans.BrandChannel;
import com.fd.epcws.rest.beans.Channel;
import com.fd.epcws.rest.beans.ChannelFrequency;
import com.fd.epcws.rest.beans.Contact;
import com.fd.epcws.rest.beans.Frequency;
import com.fd.epcws.util.helpers.LoggingHelper;

import java.util.ArrayList;
import java.util.Properties;
import org.apache.logging.log4j.Logger;

public class PreferenceDelegate {
 
	private static PreferenceDAO prefDao = null;

	protected Logger LOGGER = LoggingHelper.getLogger(PreferenceDAO.class);
	private final String CLASSNAME = "PreferenceDelegate";


	/**
	 *  Implements Business Delegate Design Pattern. This approach decouples the database implementation 
	 *  from the java/business logic; so that code can be loosely coupled but highly cohesive.  
	 */
	/** Default no param constructor for <code>PreferenceDelegate</code> class  
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public PreferenceDelegate() throws AppException{
		String METHODNAME = "constructor";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		prefDao = new PreferenceDAO();
	}

	/**
	 * Brand CRUD
	 * 
	 */

	/** This method creates a <code>Brand</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Brand</code>.             
	 * @return <code>ReturnValue</code> class with result
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue createBrand(Brand brand) throws AppException{
		String METHODNAME = "createBrand";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_BRANDCODE, brand.getCode());
		props.put(Constants.FIELD_BRANDDESC, brand.getDescription());		
		props.put(Constants.FIELD_RANK, brand.getRank());
		props.put(Constants.FIELD_ACTIVE, brand.getActive());
		props.put(Constants.FIELD_VISIBLE, brand.getVisible());

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));
		return prefDao.createBrand(props);
	}

	/** This method finds and retrieves a <code>Brand</code> object based on data passed into web service 
	 * @param The key identifier for the object
	 *  <code>String</code>.             
	 * @return <code>Brand</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */
	@Loggable
	public Brand readBrand(String code) throws AppException{
		String METHODNAME = "readBrand";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_SEARCH_KEY1, code);
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));		
		return prefDao.findBrand(props);
	}

	/** This method updates <code>Brand</code> database object based on data passed into web service 
	 * @param the updated <code>Brand</code> object is passed as input to be used to update database 
	 * @return <code>ReturnValue</code> details of update operations success/errors 
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue update(Brand brand) throws AppException{
		String METHODNAME = "update Brand";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_BRANDCODE, brand.getCode());
		props.put(Constants.FIELD_BRANDDESC, brand.getDescription());		
		props.put(Constants.FIELD_RANK, brand.getRank());
		props.put(Constants.FIELD_ACTIVE, brand.getActive());
		props.put(Constants.FIELD_VISIBLE, brand.getVisible());
		props.put(Constants.FIELD_SEARCH_KEY1, brand.getCode());		

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.updateBrand(props);
	}

	/** This method deletes <code>Brand</code> database object based on data passed into web service 
	 * @param the <code>String</code> key identifier for the <code>Brand</code> object is passed as input to be used for deletion  
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue deleteBrand(String code) throws AppException{
		String METHODNAME = "deleteBrand";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_SEARCH_KEY1, code);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.deleteBrand(props);
	}

	/*** This method finds and retrieves a list of <code>Brand</code> objects <code>ArrayList</code> based on data passed into web service
	 * @return <code>ArrayList</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	@Loggable	
	public ArrayList<? extends AbstractResource> findAllBrands() throws AppException{	

		String METHODNAME = "findAllBrands";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_SEARCH_KEY1, Constants.QUERY_PARAM_WILDCARD);	

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.findAllBrands(props);
	}

	/**
	 * Channel CRUD
	 * 
	 */
	/*** This method creates a <code>Channel</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Channel</code>.             
	 * @return <code>Channel</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue createChannel(Channel channel) throws AppException{
		String METHODNAME = "createChannel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_CHANNELCODE, channel.getCode());
		props.put(Constants.FIELD_CHANNELDESC, channel.getDescription());		
		props.put(Constants.FIELD_ACTIVE, channel.getActive());
		props.put(Constants.FIELD_RANK, channel.getRank());
		props.put(Constants.FIELD_VISIBLE, channel.getVisible());

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.createChannel(props);
	}

	/*** This method finds and retrieves a <code>Channel</code> object based on data passed into web service 
	 * @param The key identifier for the object
	 *  <code>String</code>.             
	 * @return <code>Channel</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	@Loggable
	public Channel readChannel(String code) throws AppException{
		String METHODNAME = "readChannel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_SEARCH_KEY1, code);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));		

		return prefDao.findChannel(props);
	}

	/*** This method updates <code>Channel</code> database object based on data passed into web service 
	 * @param the updated <code>Channel</code> object is passed as input to be used to update database 
	 * @return <code>ReturnValue</code> details of update operations success/errors 
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue update(Channel channel) throws AppException{
		String METHODNAME = "update Channel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_CHANNELCODE, channel.getCode());
		props.put(Constants.FIELD_CHANNELDESC, channel.getDescription());		
		props.put(Constants.FIELD_ACTIVE, channel.getActive());
		props.put(Constants.FIELD_RANK, channel.getRank());
		props.put(Constants.FIELD_VISIBLE, channel.getVisible());
		props.put(Constants.FIELD_SEARCH_KEY1, channel.getCode());

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.updateChannel(props);
	}

	/*** This method deletes <code>Frequency</code> database object based on data passed into web service 
	 * @param the <code>String</code> key identifier for the <code>Frequency</code> object is passed as input to be used for deletion  
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue deleteChannel(String code) throws AppException{
		String METHODNAME = "deleteChannel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_SEARCH_KEY1, code);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.deleteChannel(props);
	}

	/*** This method finds and retrieves a list of <code>Channel</code> objects <code>ArrayList</code> based on data passed into web service 
	 * @return <code>ArrayList</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	@Loggable	
	public ArrayList<? extends AbstractResource> findAllChannels() throws AppException{
		String METHODNAME = "findAllChannels";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_SEARCH_KEY1, Constants.QUERY_PARAM_WILDCARD);	

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.findAllChannels(props);
	}

	/**
	 * Frequency CRUD
	 * 
	 */
	/*** This method creates a <code>Frequency</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Frequency</code>.             
	 * @return <code>Frequency</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue createFrequency(Frequency frequency) throws AppException{
		String METHODNAME = "createFrequency";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_FREQUENCYCODE, frequency.getCode());
		props.put(Constants.FIELD_FREQUENCYDESC, frequency.getDescription());		
		props.put(Constants.FIELD_ACTIVE, frequency.getActive());

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.createFrequency(props);
	}

	/*** This method finds and retrieves a <code>Frequency</code> object based on data passed into web service 
	 * @param The key identifier for the object
	 *  <code>String</code>.             
	 * @return <code>Frequency</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	@Loggable
	public Frequency readFrequency(String code) throws AppException{
		String METHODNAME = "readFrequency";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_SEARCH_KEY1, code);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.findFrequency(props);
	}

	/*** This method updates <code>Frequency</code> database object based on data passed into web service 
	 * @param the updated <code>Frequency</code> object is passed as input to be used to update database 
	 * @return <code>ReturnValue</code> details of update operations success/errors 
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue update(Frequency frequency) throws AppException{
		String METHODNAME = "update Frequency";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_FREQUENCYCODE, frequency.getCode());
		props.put(Constants.FIELD_FREQUENCYDESC, frequency.getDescription());		
		props.put(Constants.FIELD_ACTIVE, frequency.getActive());
		props.put(Constants.FIELD_SEARCH_KEY1, frequency.getCode());

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.updateFrequency(props);
	}

	/*** This method deletes <code>Frequency</code> database object based on data passed into web service 
	 * @param the <code>String</code> key identifier for the <code>Frequency</code> object is passed as input to be used for deletion  
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue deleteFrequency(String code) throws AppException{
		String METHODNAME = "deleteFrequency";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_SEARCH_KEY1, code);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.deleteFrequency(props);
	}

	/*** This method finds and retrieves a list of <code>Frequency</code> objects <code>ArrayList</code> based on data passed into web service 
	 * @return <code>ArrayList</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	@Loggable
	public ArrayList<? extends AbstractResource> findAllFrequencies() throws AppException{
		String METHODNAME = "findAllFrequencies";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_SEARCH_KEY1, Constants.QUERY_PARAM_WILDCARD);	

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.findAllFrequencies(props);
	}

	/**
	 * Contact CRUD
	 * 
	 */
	/*** This method creates a <code>Contact</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Contact</code>.             
	 * @return <code>Contact</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue createContact(Contact contact) throws AppException{
		String METHODNAME = "createContact";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_CONTACTID, contact.getContactid());
		props.put(Constants.FIELD_EMAIL, contact.getEmail());		
		props.put(Constants.FIELD_USERMODIFIED, contact.getUsermodified());
		props.put(Constants.FIELD_COUNTRYCODE, contact.getCountrycode());

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.createContact(props);
	}

	/*** This method finds and retrieves a <code>Contact</code> object based on data passed into web service 
	 * @param The key identifier for the object
	 *  <code>String</code>.             
	 * @return <code>Contact</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public Contact readContact(String contactid) throws AppException{
		String METHODNAME = "readContact";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_SEARCH_KEY1, contactid);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.findContact(props);
	}

	/*** This method updates <code>Contact</code> database object based on data passed into web service 
	 * @param the updated <code>Contact</code> object is passed as input to be used to update database 
	 * @return <code>ReturnValue</code> details of update operations success/errors 
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue update(Contact contact) throws AppException{
		String METHODNAME = "update Contact";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_CONTACTID, contact.getContactid());
		props.put(Constants.FIELD_EMAIL, contact.getEmail());		
		props.put(Constants.FIELD_USERMODIFIED, contact.getUsermodified());
		props.put(Constants.FIELD_COUNTRYCODE, contact.getCountrycode());
		props.put(Constants.FIELD_SMS, contact.getSms());		

		props.put(Constants.FIELD_SEARCH_KEY1, contact.getContactid());

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));		

		return prefDao.updateContact(props);
	}

	/*** This method deletes <code>Contact</code> database object based on data passed into web service 
	 * @param the <code>String</code> key identifier for the <code>Contact</code> object is passed as input to be used for deletion  
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue deleteContact(Contact contact) throws AppException{
		String METHODNAME = "deleteContact";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_CONTACTID, contact.getContactid());
		props.put(Constants.FIELD_EMAIL, contact.getEmail());		
		props.put(Constants.FIELD_USERMODIFIED, contact.getUsermodified());

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.deleteContact(props);
	}

	/*** This method finds and retrieves a list of <code>Contact</code> objects <code>ArrayList</code> based on data passed into web service 
	 * @return <code>ArrayList</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ArrayList<? extends AbstractResource> findAllContacts() throws AppException{
		String METHODNAME = "findAllContacts";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_SEARCH_KEY1, Constants.QUERY_PARAM_WILDCARD);	

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.findAllContacts(props);
	}

	/**
	 * ChannelFrequency CRUD
	 * 
	 */
	/*** This method creates a <code>ChannelFrequency</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>ChannelFrequency</code>.             
	 * @return <code>ChannelFrequency</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue createChannelFrequency(ChannelFrequency channelFrequency) throws AppException{
		String METHODNAME = "createChannelFrequency";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_CHANNELCODE, channelFrequency.getChannelcode());
		props.put(Constants.FIELD_FREQUENCYCODE, channelFrequency.getFrequencycode());
		props.put(Constants.FIELD_RANK, channelFrequency.getRank());
		props.put(Constants.FIELD_ACTIVE, channelFrequency.getActive());
		props.put(Constants.FIELD_VISIBLE, channelFrequency.getVisible());

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.createChannelFrequency(props);
	}

	/*** This method finds and retrieves a <code>ChannelFrequency</code> object based on data passed into web service 
	 * @param The key identifier for the object
	 *  <code>String</code>.             
	 * @return <code>ChannelFrequency</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	@Loggable
	public ChannelFrequency readChannelFrequency(String code) throws AppException{
		String METHODNAME = "readChannelFrequency";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_SEARCH_KEY1, code);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.findChannelFrequency(props);
	}

	/*** This method updates <code>ChannelFrequency</code> database object based on data passed into web service 
	 * @param the updated <code>ChannelFrequency</code> object is passed as input to be used to update database 
	 * @return <code>ReturnValue</code> details of update operations success/errors 
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue update(ChannelFrequency channelFrequency) throws AppException{
		String METHODNAME = "update ChannelFrequency";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_CHANNELCODE, channelFrequency.getChannelcode());
		props.put(Constants.FIELD_FREQUENCYCODE, channelFrequency.getFrequencycode());
		props.put(Constants.FIELD_RANK, channelFrequency.getRank());
		props.put(Constants.FIELD_ACTIVE, channelFrequency.getActive());
		props.put(Constants.FIELD_VISIBLE, channelFrequency.getVisible());
		props.put(Constants.FIELD_SEARCH_KEY1, channelFrequency.getChannelcode());

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.updateChannelFrequency(props);
	}

	/*** This method deletes <code>ChannelFrequency</code> database object based on data passed into web service 
	 * @param the <code>String</code> key identifier for the <code>ChannelFrequency</code> object is passed as input to be used for deletion  
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue deleteChannelFrequency(String id) throws AppException{
		String METHODNAME = "deleteChannelFrequency";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_SEARCH_KEY1, id);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.deleteChannelFrequency(props);
	}

	/*** This method finds and retrieves a list of <code>ChannelFrequency</code> objects <code>ArrayList</code> based on data passed into web service 
	 * @return <code>ArrayList</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	@Loggable
	public ArrayList<? extends AbstractResource> findAllChannelFrequencies() throws AppException{
		String METHODNAME = "findAllChannelFrequencies";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_SEARCH_KEY1, Constants.QUERY_PARAM_WILDCARD);	

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.findAllChannelFrequencies(props);
	}


	/**
	 * BrandChannel CRUD
	 * 
	 */
	/*** This method creates a <code>BrandChannel</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>BrandChannel</code>.             
	 * @return <code>BrandChannel</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue createBrandChannel(BrandChannel brandChannel) throws AppException{
		String METHODNAME = "createBrandChannel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_CONTACTID, brandChannel.getContactid());
		props.put(Constants.FIELD_BRANDCODE, brandChannel.getBrandcode());
		props.put(Constants.FIELD_CHANNELCODE, brandChannel.getChannelcode());
		props.put(Constants.FIELD_FREQUENCYCODE, brandChannel.getFrequencycode());		
		props.put(Constants.FIELD_USERID, brandChannel.getValue());

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.createBrandChannel(props);
	}

	/*** This method finds and retrieves a <code>BrandChannel</code> object based on data passed into web service 
	 * @param The key identifier for the object
	 *  <code>String</code>.             
	 * @return <code>BrandChannel</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	@Loggable
	public BrandChannel readBrandChannel(Properties props) throws AppException{
		String METHODNAME = "readBrandChannel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.findBrandChannel(props);
	}

	/*** This method updates <code>BrandChannel</code> database object based on data passed into web service 
	 * @param the updated <code>BrandChannel</code> object is passed as input to be used to update database 
	 * @return <code>ReturnValue</code> details of update operations success/errors 
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue updateBrandChannel(BrandChannel brandChannel) throws AppException{
		String METHODNAME = "updateBrandChannel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_CONTACTID, brandChannel.getContactid());
		props.put(Constants.FIELD_BRANDCODE, brandChannel.getBrandcode());
		props.put(Constants.FIELD_CHANNELCODE, brandChannel.getChannelcode());
		props.put(Constants.FIELD_FREQUENCYCODE, brandChannel.getFrequencycode());		
		props.put(Constants.FIELD_USERID, brandChannel.getValue()); 
		props.put(Constants.FIELD_SEARCH_KEY1, brandChannel.getContactid());

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.updateBrandChannel(props);
	}

	/*** This method deletes <code>BrandChannel</code> database object based on data passed into web service 
	 * @param the <code>BrandChannel</code> object is passed as input to be used for deletion  
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue deleteBrandChannel(BrandChannel brandChannel_in) throws AppException{
		String METHODNAME = "deleteBrandChannel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_CONTACTID, brandChannel_in.getContactid());
		props.put(Constants.FIELD_CHANNELCODE, brandChannel_in.getChannelcode()); 
		props.put(Constants.FIELD_BRANDCODE, brandChannel_in.getBrandcode());    	    	
		props.put(Constants.FIELD_USERMODIFIED, brandChannel_in.getValue());    	

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.deleteBrandChannel(props);
	}

	/*** This method finds and retrieves a list of <code>BrandChannel</code> objects <code>ArrayList</code> based on data passed into web service
	 * @param the <code>String</code> key identifier for the <code>BrandChannel</code> list of objects   
	 * @return <code>ArrayList</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	@Loggable
	public ArrayList<? extends AbstractResource> findAllBrandChannels(String id) throws AppException{
		String METHODNAME = "findAllBrandChannels";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_CONTACTID, id);

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.findAllBrandChannels(props);
	}

	/*** This method finds and retrieves a list of <code>BrandChannel</code> objects <code>ArrayList</code> based on data passed into web service
	 * @param the <code>String</code> composite key identifier for the <code>BrandChannel</code> list of objects   
	 * @param the <code>String</code> composite key identifier for the <code>BrandChannel</code> list of objects	   
	 * @return <code>ArrayList</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	@Loggable
	public ArrayList<? extends AbstractResource> findAllBrandChannelsForContact(String id, String code) throws AppException{
		String METHODNAME = "findAllBrandChannelsForContact";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Properties props = new Properties();
		props.put(Constants.FIELD_CONTACTID, id);
		props.put(Constants.FIELD_BRAND, code);	

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_FUNCTION_RESOURCE_INCLUDE,props));

		return prefDao.findAllBrandChannelsForContact(props);
	}

}
