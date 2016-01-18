package it.mapler.walla.api;

import it.mapler.walla.api.response.RedirectResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redirect")
public class RedirectApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(RedirectApi.class);
	
	@RequestMapping(method = RequestMethod.GET)
	private RedirectResponse redirectLogin(){
		 
		return null;
		
	}
	
	
}
