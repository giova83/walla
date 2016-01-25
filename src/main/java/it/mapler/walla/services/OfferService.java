//package it.mapler.walla.services;
////@Service
//public class OfferService extends AbsService{
//	
//
//	//spublic OfferResponse searchOffer(OfferRequest)
//}


package it.mapler.walla.services;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.mapler.walla.api.response.LoginResponse;
import it.mapler.walla.api.response.OfferResponse;
import it.mapler.walla.dao.LoginDao;
import it.mapler.walla.dao.OfferDao;
import it.mapler.walla.dao.UserDao;
import it.mapler.walla.enumeration.STATUS;
import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Login;
import it.mapler.walla.model.Offer;
import it.mapler.walla.model.User;









import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

@Service
public class OfferService extends AbsService{

	private static final Logger LOGGER = LoggerFactory.getLogger(OfferService.class);
	
	@Autowired
	private OfferDao offerDao;

	
	public OfferResponse getAllOfferByUserCity(String idUser){
		LOGGER.info("OfferServices.login - START");
		OfferResponse offerResponse = new OfferResponse();
		List<Offer> offers = null;
		 try{
		     
	         LOGGER.info("search offer for user: "+idUser);
	         
	         offers = offerDao.findAllOfferByUserCity(Long.valueOf(idUser));
	         
	         offerResponse.setOfferts(offers);
	         offerResponse.setStatusOK();
	       
	        return offerResponse;
	            
		  }catch(WallaDBException we){
			  LOGGER.error(we.getMessage(),we);
			  offerResponse.setStatusError(STATUS.ERROR_DB, we.getMessage());
			  return offerResponse;
		  }catch(Exception e){
			  LOGGER.error(e.getMessage(),e);
		      offerResponse.setStatusError(STATUS.ERROR_UNEXPECTED, e.getMessage());
			  return offerResponse;
		  }finally{
			  LOGGER.info("OfferServices.login - END");  
		  }
	
	}

	
	public OfferResponse getAllOfferByUserPar(String citta, String categoria,String tipologia, String titolo, Date datapubblicazione){
		LOGGER.info("OfferServices.login - START");
		OfferResponse offerResponse = new OfferResponse();
		List<Offer> offers = null;
		 try{
		     
	         LOGGER.info("search offer for Parameters: Titolo: "+titolo+" ,"
	         		+ "Categoria: "+categoria+", Tipologia: "+tipologia+", CittÃ : "+citta+""
	         		+ ", Data di Pubblicazione: "+datapubblicazione);
	         
	         offers = offerDao.findAllOfferByPar(citta, categoria, tipologia, titolo, datapubblicazione);
	         
	         offerResponse.setOfferts(offers);
	         offerResponse.setStatusOK();
	       
	        return offerResponse;
	            
		  }catch(WallaDBException we){
			  LOGGER.error(we.getMessage(),we);
			  offerResponse.setStatusError(STATUS.ERROR_DB, we.getMessage());
			  return offerResponse;
		  }catch(Exception e){
			  LOGGER.error(e.getMessage(),e);
		      offerResponse.setStatusError(STATUS.ERROR_UNEXPECTED, e.getMessage());
			  return offerResponse;
		  }finally{
			  LOGGER.info("OfferServices.login - END");  
		  }
	
	}
	
	
}
