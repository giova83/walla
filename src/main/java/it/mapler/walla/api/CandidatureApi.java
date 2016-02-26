package it.mapler.walla.api;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import it.mapler.walla.api.request.CandidatureRequest;
import it.mapler.walla.api.request.OfferRequest;
import it.mapler.walla.api.response.CandidateResponse;
import it.mapler.walla.api.response.CandidatureResponse;
import it.mapler.walla.api.response.OfferResponse;
import it.mapler.walla.api.response.StatusResponse;
import it.mapler.walla.services.CandidatureService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/candidature")
public class CandidatureApi {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(CandidatureApi.class);

	 @Autowired
	 private CandidatureService candidatureService;
	 
	 @RequestMapping( method = RequestMethod.GET)
	 public @ResponseBody CandidatureResponse getAllCandidature( HttpServletRequest request)
	 {
		 LOGGER.info("CandidatureApi.getAllCandidature - START");
		 try{
				Claims claims = (Claims) request.getAttribute("claims");
			  return candidatureService.getAllCandidatureByCandidate(claims.getSubject());
		 }finally{
			 LOGGER.info("CandidatureApi.getAllCandidature - END");
		 }

	 }
	 

	 @RequestMapping(value="/add", method = RequestMethod.POST)
	 public @ResponseBody StatusResponse addCandidature(@RequestBody CandidatureRequest candidatureRequest, HttpServletRequest request)
	 {
		 LOGGER.info("CandidatureApi.addCandidature - START");
		 try{
				Claims claims = (Claims) request.getAttribute("claims");
			  return candidatureService.addCandidature(candidatureRequest, claims.getSubject());
		 }finally{
			 LOGGER.info("CandidatureApi.addCandidature - END");
		 }

	 }
	 

}
