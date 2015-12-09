package it.mapler.walla.api;

import it.mapler.walla.api.request.LoginRequest;
import it.mapler.walla.api.response.LoginResponse;
import it.mapler.walla.services.LoginService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginApi {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(LoginApi.class);
	 
	 @Autowired
	 private LoginService loginService;
	
	 @RequestMapping(method = RequestMethod.POST)
	 public @ResponseBody LoginResponse login(@RequestBody LoginRequest loginRequest){
		 LOGGER.info("LoginApi.login - START");
		 try{
			return loginService.login(loginRequest.getUsername(), loginRequest.getPassword()); 
		 }finally{
			 LOGGER.info("LoginApi.login - END"); 
		 }
		 
	 }
}
