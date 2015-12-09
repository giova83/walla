package it.mapler.walla.api;

import it.mapler.walla.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApi {
	
	@Autowired
	private UserService userService;
	
	

}
