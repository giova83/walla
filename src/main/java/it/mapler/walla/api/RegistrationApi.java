package it.mapler.walla.api;

import it.mapler.walla.api.request.UserRequest;
import it.mapler.walla.api.response.RegistrationResponse;
import it.mapler.walla.services.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/registration")
public class RegistrationApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationApi.class);
		
    @Autowired
	private RegistrationService registrationService;
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody  RegistrationResponse createCandidate(@RequestBody  UserRequest userRequest){
		 LOGGER.info("RegistrationApi.createUser - START");
		 try{
				return null; 
		 }finally{
				 LOGGER.info("RegistrationApi.createUser - END"); 
	    }
		
	}
	
	public  @ResponseBody  RegistrationResponse createRestaurant(){
		return null;
	}
	
	//aziende fornotrici
	public  @ResponseBody  RegistrationResponse crerateProvider (){
	  return null;
	}
	
	public void confirmRegistration (){
	
	}
	
	
}
