package it.mapler.walla.api;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import it.mapler.walla.api.request.LoginRequest;
import it.mapler.walla.api.request.OfferRequest;
import it.mapler.walla.api.response.OfferResponse;
import it.mapler.walla.api.response.StatusResponse;
import it.mapler.walla.services.OfferService;

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
@RequestMapping("api/offer")
public class OfferApi {

	 private static final Logger LOGGER = LoggerFactory.getLogger(OfferApi.class);

	 @Autowired
	 private OfferService offerService;

	 @RequestMapping( method = RequestMethod.GET)
	 public @ResponseBody OfferResponse getAllOfferByCityUser(HttpServletRequest request){
		 LOGGER.info("OfferApi.login - START");
		 try{
			Claims claims = (Claims) request.getAttribute("claims");
			return offerService.getAllOfferByUserCity(claims.getSubject());
		 }finally{
			 LOGGER.info("OfferApi.login - END");
		 }

	 }

	 @RequestMapping( method = RequestMethod.POST)
	 public @ResponseBody StatusResponse addOffer(@RequestBody OfferRequest offerRequest ){
		 LOGGER.info("OfferApi.addOffer - START");
		 try{
			return offerService.addOffer(offerRequest);
		 }finally{
			 LOGGER.info("OfferApi.login - END");
		 }

	 }

	 @RequestMapping(value="/{idOffer}", method = RequestMethod.DELETE)
	 public @ResponseBody StatusResponse deleteOffer(@PathVariable("idOffer") String idOffer){
		 LOGGER.info("OfferApi.deleteOffer - START");
		 try{
			return offerService.deleteOffer(idOffer);
		 }finally{
			 LOGGER.info("OfferApi.login - END");
		 }

	 }

	 @RequestMapping(value="/withParam", method = RequestMethod.POST)
	 public @ResponseBody OfferResponse getOfferByParam(@RequestBody OfferRequest offerRequest ){
		 LOGGER.info("OfferApi.login - START");
		 try{
			return offerService.getAllOfferByUserPar(offerRequest.getCitta(),offerRequest.getCategoria(), offerRequest.getTipologia(),
					offerRequest.getTitolo(), offerRequest.getDatapubblicazione());
		 }finally{
			 LOGGER.info("OfferApi.login - END");
		 }

	 }
}
