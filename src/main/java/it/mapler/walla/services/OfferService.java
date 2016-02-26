//package it.mapler.walla.services;
////@Service
//public class OfferService extends AbsService{
//
//
//	//spublic OfferResponse searchOffer(OfferRequest)
//}


package it.mapler.walla.services;

import java.util.Calendar;

import java.util.GregorianCalendar;
import java.util.List;


import it.mapler.walla.api.request.OfferRequest;

import it.mapler.walla.api.response.OfferResponse;
import it.mapler.walla.api.response.StatusResponse;

import it.mapler.walla.dao.OfferDao;

import it.mapler.walla.enumeration.STATUS;
import it.mapler.walla.exception.WallaDBException;

import it.mapler.walla.model.Offer;
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
		LOGGER.info("OfferServices.getAllOfferByUserCity - START");
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
			  LOGGER.info("OfferServices.getAllOfferByUserCity - END");
		  }

	}

	public StatusResponse addOffer(OfferRequest offerRequest){
		LOGGER.info("OfferServices.addOffer - START");
		StatusResponse statusResponse = new StatusResponse();
		 try{

	         LOGGER.info("save offer for ristorante: "+offerRequest.getIdristorante());

	         Offer offer = new Offer();
	         offer.setCategoria(offerRequest.getCategoria());
	         offer.setCitta(offerRequest.getCitta());
	         offer.setDatapubblicazione(new GregorianCalendar());
	         offer.setDescrizione(offerRequest.getDescrizione());
	         offer.setTitolo(offerRequest.getTitolo());
	         offer.setIdristorante(offerRequest.getIdristorante());
	         offer.setTipologia(offerRequest.getTipologia());
	         offer.setAttiva(1);

	         TransactionStatus status = transactionManager.getTransaction(getTransactionDefinition());

	         offerDao.offerAdd(offer);

	         transactionManager.commit(status);

	         statusResponse.setStatusOK();

	        return statusResponse;

		  }catch(WallaDBException we){
			  LOGGER.error(we.getMessage(),we);
			  statusResponse.setStatusError(STATUS.ERROR_DB, we.getMessage());
			  return statusResponse;
		  }catch(Exception e){
			  LOGGER.error(e.getMessage(),e);
		      statusResponse.setStatusError(STATUS.ERROR_UNEXPECTED, e.getMessage());
			  return statusResponse;
		  }finally{
			  LOGGER.info("OfferServices.addOffer - END");
		  }

	}


	public OfferResponse getAllOfferByUserPar(String citta, String categoria,String tipologia, String titolo, Calendar datapubblicazione){
		LOGGER.info("OfferServices.login - START");
		OfferResponse offerResponse = new OfferResponse();
		List<Offer> offers = null;
		 try{

	         LOGGER.info("search offer for Parameters: Titolo: "+titolo+" ,"
	         		+ "Categoria: "+categoria+", Tipologia: "+tipologia+", CittÃ : "+citta+""
	         		+ ", Data di Pubblicazione: "+datapubblicazione);

	        // GregorianCalendar dataPub = null;

	        /* if(StringUtils.isNoneEmpty(datapubblicazione)){
	           dataPub = new GregorianCalendar();
	           dataPub.setTimeInMillis(Long.valueOf(datapubblicazione));
	         }*/
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

	public StatusResponse deleteOffer(String idOffer) {

		LOGGER.info("OfferServices.deleteOffer - START");
		StatusResponse statusResponse = new StatusResponse();
		 try{

	         LOGGER.info("delete offer with id: "+idOffer);

	         TransactionStatus status = transactionManager.getTransaction(getTransactionDefinition());

	         offerDao.offerDelete(Long.valueOf(idOffer));

	         transactionManager.commit(status);

	         statusResponse.setStatusOK();

	        return statusResponse;

		  }catch(WallaDBException we){
			  LOGGER.error(we.getMessage(),we);
			  statusResponse.setStatusError(STATUS.ERROR_DB, we.getMessage());
			  return statusResponse;
		  }catch(Exception e){
			  LOGGER.error(e.getMessage(),e);
		      statusResponse.setStatusError(STATUS.ERROR_UNEXPECTED, e.getMessage());
			  return statusResponse;
		  }finally{
			  LOGGER.info("OfferServices.addOffer - END");
		  }
	}


}
