package it.mapler.walla.services;

import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import it.mapler.walla.api.request.CandidatureRequest;
import it.mapler.walla.api.response.CandidatureResponse;
import it.mapler.walla.api.response.OfferResponse;
import it.mapler.walla.api.response.StatusResponse;
import it.mapler.walla.dao.CandidateDao;
import it.mapler.walla.dao.CandidatureDao;
import it.mapler.walla.enumeration.STATUS;
import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Candidate;
import it.mapler.walla.model.Candidature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;


@Service
public class CandidatureService extends AbsService{

	private static final Logger LOGGER = LoggerFactory.getLogger(CandidatureService.class);

	@Autowired
	private CandidatureDao candidatureDao;

	@Autowired
	private CandidateDao candidateDao;


	public StatusResponse addCandidature(CandidatureRequest candidatureRequest, String user){
		LOGGER.info("CandidatureServices.addCandidature - START");
		StatusResponse statusResponse = new StatusResponse();
		 try{
			 LOGGER.info("get candidate for id user: "+user);
			 Candidate candidate = candidateDao.getCandidate(Long.valueOf(user));


	         LOGGER.info("save candidature for offerta: "+candidatureRequest.getIdofferta());

	         Candidature candidature = new Candidature();
	         candidature.setDescrizione(candidatureRequest.getDescrizione());
	         candidature.setIdofferta(candidatureRequest.getIdofferta());
             candidature.setIdcandidato(candidate.getIdcandidato());
             candidature.setDatacandidatura(new GregorianCalendar());

	         TransactionStatus status = transactionManager.getTransaction(getTransactionDefinition());

	         candidatureDao.candidatureAdd(candidature);

	         transactionManager.commit(status);

	         statusResponse.setStatusOK();
	         statusResponse.setMessage("Candidatura effettuata con successo!");

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
			  LOGGER.info("CandidatureServices.addCandidature - END");
		  }

	}


	public CandidatureResponse getAllCandidatureByCandidate(String user){
		LOGGER.info("CandidatureServices.getAllCandidatureByCandidate - START");
		CandidatureResponse candidatureResponse = new CandidatureResponse();
		List<Candidature> candidatures = null;
		 try{

	         LOGGER.info("search candidature for user: "+user);

	         candidatures = candidatureDao.getAllCandidatureByCandidate(Long.valueOf(user));

	         candidatureResponse.setCandidatures(candidatures);
	         candidatureResponse.setStatusOK();

	        return candidatureResponse;

		  }catch(WallaDBException we){
			  LOGGER.error(we.getMessage(),we);
			  candidatureResponse.setStatusError(STATUS.ERROR_DB, we.getMessage());
			  return candidatureResponse;
		  }catch(Exception e){
			  LOGGER.error(e.getMessage(),e);
			  candidatureResponse.setStatusError(STATUS.ERROR_UNEXPECTED, e.getMessage());
			  return candidatureResponse;
		  }finally{
			  LOGGER.info("CandidatureServices.getAllCandidatureByCandidate - END");
		  }

	}

	public CandidatureResponse getCandidatureForOffer(String idOffer){
	LOGGER.info("CandidatureServices.getCandidatureForOffer - START");
	CandidatureResponse candidatureResponse = new CandidatureResponse();
	List<Candidature> candidatures = null;
	 try{

         LOGGER.info("search candidature for offer: "+idOffer);

         candidatures = candidatureDao.getCandidatureForOffer(Long.valueOf(idOffer));

         candidatureResponse.setCandidatures(candidatures);
         candidatureResponse.setStatusOK();

        return candidatureResponse;

	  }catch(WallaDBException we){
		  LOGGER.error(we.getMessage(),we);
		  candidatureResponse.setStatusError(STATUS.ERROR_DB, we.getMessage());
		  return candidatureResponse;
	  }catch(Exception e){
		  LOGGER.error(e.getMessage(),e);
	      candidatureResponse.setStatusError(STATUS.ERROR_UNEXPECTED, e.getMessage());
		  return candidatureResponse;
	  }finally{
		  LOGGER.info("CandidatureServices.getCandidatureForOffer - END");
	  }

}

}
