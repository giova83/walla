package it.mapler.walla.api;

import io.jsonwebtoken.Claims;
import it.mapler.walla.api.request.CompanyRequest;
import it.mapler.walla.api.response.CompanyResponse;
import it.mapler.walla.api.response.StatusResponse;
import it.mapler.walla.services.CompanyService;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/companies")

public class CompanyApi {

		
		 private static final Logger LOGGER = LoggerFactory.getLogger(CompanyApi.class);
		 
		 @Autowired
		 private CompanyService companyService;
		 
		 @RequestMapping( method = RequestMethod.GET)
		 public @ResponseBody CompanyResponse getAllCompanies( HttpServletRequest request)
		 {
			 LOGGER.info("CompanyApi.getAllCompanies - START");
			 try{
				 Claims claims = (Claims) request.getAttribute("claims");
				  return companyService.getAllCompaniesByOwner(claims.getSubject());
			 }finally{
				 LOGGER.info("CompanyApi.getAllCompaniesByOwner - END");
			 }

		 }
		 
		 
		 @RequestMapping(value="/add", method = RequestMethod.POST)
		 public @ResponseBody StatusResponse addCompany(@RequestBody CompanyRequest companyRequest, HttpServletRequest request)
		 {
			 LOGGER.info("CompanyApi.addCompany - START");
			 try{
					Claims claims = (Claims) request.getAttribute("claims");
				  return companyService.addCompany(companyRequest, claims.getSubject());
			 }finally{
				 LOGGER.info("CompanyApi.addCompany - END");
			 }

		 }
		 
		 
		 @RequestMapping(value="/{idCompany}", method = RequestMethod.DELETE)
		 public @ResponseBody StatusResponse deleteCompany(@PathVariable("idCompany") String idCompany){
			 LOGGER.info("CompanyApi.deleteCompany - START");
			 try{
				return companyService.deleteCompany(idCompany);
			 }finally{
				 LOGGER.info("CompanyApi.login - END");
			 }

		 }

		 
}
