package com.fd.epcws.db.dao;

import com.fd.epcws.constants.Constants;
import com.fd.epcws.db.connection.DBSqlConnection;
import com.fd.epcws.db.dao.DAO;
import com.fd.epcws.db.dao.valueobjects.ReturnValue;
import com.fd.epcws.exceptions.AppException;
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

public class PreferenceDAO extends DAO{

	private final String CLASSNAME = "PreferenceDAO";

	protected Logger LOGGER = LoggingHelper.getLogger(PreferenceDAO.class);

	private DBSqlConnection conn = null;

	/** default no param constructor    
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public PreferenceDAO() throws AppException{
		String METHODNAME = "constructor";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		conn = super.getConnection();
	}

	// CRUD create

	/** This method creates a <code>Brand</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Properties</code>.             
	 * @return <code>ReturnValue</code> class with result
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue createBrand(Properties props) throws AppException{
		String METHODNAME = "createBrand";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.create(Brand.class, props);
	}

	/** This method creates a <code>Channel</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Properties</code>.             
	 * @return <code>ReturnValue</code> class with result
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue createChannel(Properties props) throws AppException{
		String METHODNAME = "createChannel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.create(Channel.class, props);
	}

	/** This method creates a <code>Frequency</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Properties</code>.             
	 * @return <code>ReturnValue</code> class with result
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue createFrequency(Properties props) throws AppException{
		String METHODNAME = "createFrequency";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.create(Frequency.class, props);
	}

	/** This method creates a <code>Contact</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Properties</code>.             
	 * @return <code>ReturnValue</code> class with result
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue createContact(Properties props) throws AppException{
		String METHODNAME = "createContact";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.create(Contact.class, props);
	}

	/** This method creates a <code>ChannelFrequency</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Properties</code>.             
	 * @return <code>ReturnValue</code> class with result
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue createChannelFrequency(Properties props) throws AppException{
		String METHODNAME = "createChannelFrequency";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.create(ChannelFrequency.class, props);
	}

	/** This method creates a <code>BrandChannel</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to populate object. 
	 *        <code>Properties</code>.             
	 * @return <code>ReturnValue</code> class with result
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue createBrandChannel(Properties props) throws AppException{
		String METHODNAME = "createBrandChannel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.create(BrandChannel.class, props);
	}

	// CRUD read

	/** This method finds and retrieves a <code>Brand</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to find data object. 
	 *        <code>Properties</code>.             
	 * @return <code>Brand</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public Brand findBrand(Properties props) throws AppException{
		String METHODNAME = "findBrand";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return (Brand)conn.find(Brand.class, props);
	}

	/** This method finds and retrieves a <code>Channel</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to find data object. 
	 *        <code>Properties</code>.             
	 * @return <code>Channel</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public Channel findChannel(Properties props) throws AppException{
		String METHODNAME = "findChannel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return (Channel)conn.find(Channel.class,  props);
	}

	/** This method finds and retrieves a <code>Frequency</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to find data object. 
	 *        <code>Properties</code>.             
	 * @return <code>Frequency</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public Frequency findFrequency(Properties props) throws AppException{
		String METHODNAME = "findFrequency";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return (Frequency)conn.find(Frequency.class, props);
	}

	/** This method finds and retrieves a <code>Contact</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to find data object. 
	 *        <code>Properties</code>.             
	 * @return <code>Contact</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public Contact findContact(Properties props) throws AppException{
		String METHODNAME = "findContact";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return (Contact)conn.find(Contact.class, props);
	}

	/** This method finds and retrieves a <code>ChannelFrequency</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to find data object. 
	 *        <code>Properties</code>.             
	 * @return <code>ChannelFrequency</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ChannelFrequency findChannelFrequency(Properties props) throws AppException{
		String METHODNAME = "findChannelFrequency";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return (ChannelFrequency)conn.find(ChannelFrequency.class, props);
	}

	/** This method finds and retrieves a <code>BrandChannel</code> object based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to find data object. 
	 *        <code>Properties</code>.             
	 * @return <code>BrandChannel</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public BrandChannel findBrandChannel(Properties props) throws AppException{
		String METHODNAME = "findBrandChannel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return (BrandChannel)conn.find(BrandChannel.class, props);
	}

	// CRUD find Lists
	/** This method finds and retrieves a list of <code>Brand</code> objects <code>ArrayList</code> based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to find list of data objects. 
	 *        <code>Properties</code>.             
	 * @return <code>ArrayList</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ArrayList<? extends AbstractResource> findAllBrands(Properties props) throws AppException{

		String METHODNAME = "findAllBrands";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return (ArrayList<? extends AbstractResource>)conn.findAll(Brand.class, props);
	}

	/** This method finds and retrieves a list of <code>Channel</code> objects <code>ArrayList</code> based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to find list of data objects. 
	 *        <code>Properties</code>.             
	 * @return <code>ArrayList</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ArrayList<? extends AbstractResource> findAllChannels(Properties props) throws AppException{

		String METHODNAME = "findAllChannels";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return (ArrayList<? extends AbstractResource>)conn.findAll(Channel.class, props);
	}

	/** This method finds and retrieves a list of <code>Frequency</code> objects <code>ArrayList</code> based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to find list of data objects. 
	 *        <code>Properties</code>.             
	 * @return <code>ArrayList</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ArrayList<? extends AbstractResource> findAllFrequencies(Properties props) throws AppException{

		String METHODNAME = "findAllFrequencies";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return (ArrayList<? extends AbstractResource>)conn.findAll(Frequency.class, props);
	}

	/** This method finds and retrieves a list of <code>Contact</code> objects <code>ArrayList</code> based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to find list of data objects. 
	 *        <code>Properties</code>.             
	 * @return <code>ArrayList</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ArrayList<? extends AbstractResource> findAllContacts(Properties props) throws AppException{
		String METHODNAME = "findAllContacts";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return (ArrayList<? extends AbstractResource>)conn.findAll(Contact.class, props);
	}

	/** This method finds and retrieves a list of <code>ChannelFrequency</code> objects <code>ArrayList</code> based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to find list of data objects. 
	 *        <code>Properties</code>.             
	 * @return <code>ArrayList</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ArrayList<? extends AbstractResource> findAllChannelFrequencies(Properties props) throws AppException{
		String METHODNAME = "findAllChannelFrequencies";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return (ArrayList<? extends AbstractResource>)conn.findAll(ChannelFrequency.class, props);
	}

	/** This method finds and retrieves a list of <code>BrandChannel</code> objects <code>ArrayList</code> based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to find list of data objects. 
	 *        <code>Properties</code>.             
	 * @return <code>ArrayList</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ArrayList<? extends AbstractResource> findAllBrandChannels(Properties props) throws AppException{
		String METHODNAME = "findAllBrandChannels";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return (ArrayList<? extends AbstractResource>)conn.findAll(BrandChannel.class, props);
	}

	/** This method finds and retrieves a list of <code>BrandChannel</code> objects <code>ArrayList</code> based on data passed into web service 
	 * @param The query parameters used in the SQL DB call to find list of data objects. 
	 *        <code>Properties</code>.             
	 * @return <code>ArrayList</code> class or sub class
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ArrayList<? extends AbstractResource> findAllBrandChannelsForContact(Properties props) throws AppException{
		String METHODNAME = "findAllBrandChannelsForContact";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return (ArrayList<? extends AbstractResource>)conn.findAll(BrandChannel.class, props);
	}

	// CRUD update
	/** This method updates <code>Brand</code> database object based on data passed into web service 
	 * @param the updated <code>Properties</code> object is passed as input to be used to update database 
	 * @return <code>ReturnValue</code> details of update operations success/errors 
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue updateBrand(Properties props) throws AppException{
		String METHODNAME = "update(brand)";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.update(Brand.class, props);
	}

	/** This method updates <code>Channel</code> database object based on data passed into web service 
	 * @param the updated <code>Properties</code> object is passed as input to be used to update database 
	 * @return <code>ReturnValue</code> details of update operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue updateChannel(Properties props) throws AppException{
		String METHODNAME = "update(channel)";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.update(Channel.class, props);
	}

	/** This method updates <code>Frequency</code> database object based on data passed into web service 
	 * @param the updated <code>Properties</code> object is passed as input to be used to update database 
	 * @return <code>ReturnValue</code> details of update operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue updateFrequency(Properties props) throws AppException{
		String METHODNAME = "update(frequency)";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.update(Frequency.class, props);
	}

	/** This method updates <code>Contact</code> database object based on data passed into web service 
	 * @param the updated <code>Properties</code> object is passed as input to be used to update database 
	 * @return <code>ReturnValue</code> details of update operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue updateContact(Properties props) throws AppException{
		String METHODNAME = "update(contact)";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.update(Contact.class, props);
	}

	/** This method updates <code>BrandChannel</code> database object based on data passed into web service 
	 * @param the updated <code>Properties</code> object is passed as input to be used to update database 
	 * @return <code>ReturnValue</code> details of update operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue updateBrandChannel(Properties props) throws AppException{
		String METHODNAME = "updateBrandChannel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.update(BrandChannel.class, props);
	}

	/** This method updates <code>ChannelFrequency</code> database object based on data passed into web service 
	 * @param the updated <code>Properties</code> object is passed as input to be used to update database 
	 * @return <code>ReturnValue</code> details of update operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue updateChannelFrequency(Properties props) throws AppException{
		String METHODNAME = "update(channel frequency)";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.update(ChannelFrequency.class, props);
	}

	// CRUD delete    
	/** This method deletes <code>Brand</code> database object based on data passed into web service 
	 * @param the key identifier for the <code>Brand</code> object is passed as input to be used for deletion  
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue deleteBrand(Properties props)  throws AppException{
		String METHODNAME = "deleteBrand";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.delete(Brand.class, props);
	}

	/** This method deletes <code>Channel</code> database object based on data passed into web service 
	 * @param the key identifier for the <code>Channel</code> object is passed as input to be used for deletion  
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue deleteChannel(Properties props)  throws AppException{
		String METHODNAME = "deleteChannel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.delete(Channel.class, props);
	}

	/** This method deletes <code>Frequency</code> database object based on data passed into web service 
	 * @param the key identifier for the <code>Frequency</code> object is passed as input to be used for deletion  
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue deleteFrequency(Properties props)  throws AppException{
		String METHODNAME = "deleteFrequency";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.delete(Frequency.class, props);
	}

	/** This method deletes <code>Contact</code> database object based on data passed into web service 
	 * @param the key identifier for the <code>Contact</code> object is passed as input to be used for deletion  
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue deleteContact(Properties props)  throws AppException{
		String METHODNAME = "deleteContact";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.delete(Contact.class, props);
	}

	/** This method deletes <code>ChannelFrequency</code> database object based on data passed into web service 
	 * @param the key identifier for the <code>ChannelFrequency</code> object is passed as input to be used for deletion  
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue deleteChannelFrequency(Properties props)  throws AppException{
		String METHODNAME = "deleteChannelFrequency";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.delete(ChannelFrequency.class, props);
	}

	/** This method deletes <code>BrandChannel</code> database object based on data passed into web service 
	 * @param the key identifier for the <code>BrandChannel</code> object is passed as input to be used for deletion  
	 * @return <code>ReturnValue</code> details of delete operations success/errors
	 * @exception <code>AppException</code> If an AppException error occurs.
	 */	
	public ReturnValue deleteBrandChannel(Properties props)  throws AppException{
		String METHODNAME = "deleteBrandChannel";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_CALLED_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		return conn.delete(BrandChannel.class, props);
	}

}
