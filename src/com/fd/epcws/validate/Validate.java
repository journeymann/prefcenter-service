package com.fd.epcws.validate;

/**
 * @author cgordon
 * Validate class used application wide. Uses Apache validation framework w/ annotations
 * Dependency on Apache BVal java bean validaton API (JSR349) Java 1.6+
 */

import java.util.ArrayList;
import java.util.HashMap; 
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.logging.log4j.Logger;

import com.fd.epcws.constants.Constants;
import com.fd.epcws.rest.beans.AbstractResource;
import com.fd.epcws.util.helpers.LoggingHelper;

public class Validate {
	private final String CLASSNAME = "Validate";

	protected Logger LOGGER = LoggingHelper.getLogger(Validate.class);

	/** This method validates AbstractResource class/sub class based 
	 * 
	 * Please note that logging is not maintained for this method because it is being called too frequently and was 
	 * cluttering up the log file.
	 * 
	 * @param The <code>AbstractResource</code> class subclass object to be validated
	 * @return <code>Set<ConstraintViolation<AbstractResource>></code> complex nested Set with validation failures
	 */	
	public Set<ConstraintViolation<AbstractResource>> validate(AbstractResource input){

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator =  factory.getValidator();
		Set<ConstraintViolation<AbstractResource>> violations = validator.validate(input);

		return violations;
	} 

	/** This method validates AbstractResource class/sub class based (overloaded for array list)
	 * @param The <code>ArrayList</code> generic list of AbstractResource objects to be validated
	 * @return <code>Map<AbstractResource,Set<ConstraintViolation<AbstractResource>>></code> complex nested Map of Set with validation failures
	 */	
	public Map<AbstractResource,Set<ConstraintViolation<AbstractResource>>> validate(ArrayList<? extends AbstractResource> list){
		String METHODNAME = "validate OL2";
		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_ENTER_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));

		Map<AbstractResource,Set<ConstraintViolation<AbstractResource>>> violationsList = new HashMap<AbstractResource,Set<ConstraintViolation<AbstractResource>>>();

		for(AbstractResource input : list){
			Set<ConstraintViolation<AbstractResource>> issues = this.validate(input);
			if(!issues.isEmpty()){
				violationsList.put(input,issues);
			}
		}

		LOGGER.debug(String.format(Constants.MESSAGE_DEBUG_EXIT_FUNCTION_INCLUDE,CLASSNAME, METHODNAME));
		return violationsList;
	} 


}
