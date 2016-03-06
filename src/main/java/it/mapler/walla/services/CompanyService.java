package it.mapler.walla.services;

import java.util.List;



import it.mapler.walla.api.request.CompanyRequest;
import it.mapler.walla.api.response.CompanyResponse;
import it.mapler.walla.api.response.StatusResponse;
import it.mapler.walla.dao.CompanyDao;
import it.mapler.walla.dao.SupplierDao;
import it.mapler.walla.enumeration.STATUS;
import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Company;
import it.mapler.walla.model.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;


@Service
public class CompanyService extends AbsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyService.class);
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private SupplierDao supplierDao;
	
	
	public CompanyResponse getAllCompaniesByOwner(String user)
	{
		LOGGER.info("CompanyService.getAllCompaniesByOwner - START");
		CompanyResponse companyResponse = new CompanyResponse();
		List<Company> companies = null;
		 try{
			 
	         LOGGER.info("search companies for user: "+user);
	         companies = companyDao.getSupplierCompany(Long.valueOf(user));

	         companyResponse.setCompanies(companies);
	         companyResponse.setStatusOK();

	        return companyResponse;


		  }catch(WallaDBException we){
			  LOGGER.error(we.getMessage(),we);
			  companyResponse.setStatusError(STATUS.ERROR_DB, we.getMessage());
			  return companyResponse;
		  }catch(Exception e){
			  LOGGER.error(e.getMessage(),e);
			  companyResponse.setStatusError(STATUS.ERROR_UNEXPECTED, e.getMessage());
			  return companyResponse;
		  }finally{
			  LOGGER.info("CompanyServices.getAllCompanyBySupplier - END");
		  }

	}
	
	
	public StatusResponse addCompany(CompanyRequest companyRequest, String user)
	{
		LOGGER.info("CompanyServices.addCompany - START");
		StatusResponse statusResponse = new StatusResponse();
		 try{
			 LOGGER.info("get company for id user: "+user);
			 Supplier supplier = supplierDao.getSupplier(Long.valueOf(user));
			 
			 
	         LOGGER.info("save company for Company: "+companyRequest.getIdazienda());

	         Company company = new Company();
	         
	         company.setIdfornitore(supplier.getIdfornitore());
	         company.setNome(companyRequest.getNome());
	         company.setCategoria(companyRequest.getCategoria());
	         company.setCellulare(companyRequest.getCellulare());
	         company.setIndirizzo(companyRequest.getIndirizzo());
	         company.setCitta(companyRequest.getCitta());
	         company.setProvincia(companyRequest.getProvincia());
	         company.setCap(companyRequest.getCap());
	         company.setRegione(companyRequest.getRegione());
	         company.setLatitudine(companyRequest.getLatitudine());
	         company.setLongitudine(companyRequest.getLongitudine());
	         company.setPartitaiva(companyRequest.getPartitaiva());


	         TransactionStatus status = transactionManager.getTransaction(getTransactionDefinition());

	         companyDao.companyAdd(company);

	         transactionManager.commit(status);

	         statusResponse.setStatusOK();
	         statusResponse.setMessage("Azienda creata con successo!");

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
			  LOGGER.info("CompanyServices.addCompany - END");
		  }

	}
		
	
	public StatusResponse deleteCompany(String idCompany) {

		LOGGER.info("CompanyServices.deleteCompany - START");
		StatusResponse statusResponse = new StatusResponse();
		 try{

	         LOGGER.info("delete company with id: "+idCompany);

	         TransactionStatus status = transactionManager.getTransaction(getTransactionDefinition());

	         companyDao.companyDelete(Long.valueOf(idCompany));

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
			  LOGGER.info("CompanyServices.deleteCompany - END");
		  }
	}
	

}
