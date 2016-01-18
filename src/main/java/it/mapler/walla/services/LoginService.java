package it.mapler.walla.services;

import java.util.Calendar;
import java.util.GregorianCalendar;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.mapler.walla.api.response.LoginResponse;
import it.mapler.walla.dao.LoginDao;
import it.mapler.walla.dao.UserDao;
import it.mapler.walla.enumeration.STATUS;
import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Login;
import it.mapler.walla.model.User;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

@Service
public class LoginService extends AbsService{

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LoginDao loginDao;
	
	public LoginResponse login(String userName, String pwd){
		LOGGER.info("LoginServices.login - START");
		 LoginResponse loginResponse = new LoginResponse();
		 try{
			 if (StringUtils.isEmpty(userName)) {
	            LOGGER.info("user is empty");
	             loginResponse.setStatusError(STATUS.WARNING_USER, "Campo user vuoto!");
	             return loginResponse;
	         }
	         if (StringUtils.isEmpty(pwd)) {
	            LOGGER.info("pwd is empty");
	            loginResponse.setStatusError(STATUS.WARNING_PWD, "Campo password vuoto!");
	            return loginResponse;
	         }
	         LOGGER.info("login for user:"+userName);
	         
	         User user = userDao.getUser(userName);
	         
	         if(user != null){
	        	 if(pwd.equals(user.getPassword())){
	        		 LOGGER.info("password OK");
	        		 LOGGER.info("prepare token...");
	        		 
	        		 Calendar now = new GregorianCalendar();
	        		 
	        		 if(user.getProfilo() == null){
	        			 loginResponse.setStatusError(STATUS.WARNING_USER, "profilo non gestito!");
	        			 return loginResponse;
	        		 }
	        		 
	        		 String token = Jwts.builder().setSubject(user.getUsername()).claim("profile", user.getProfilo()
	        				                       .getName()).setIssuedAt(now.getTime()).signWith(SignatureAlgorithm.HS256, "secretkey").compact();
	        		 LOGGER.info("user["+user.getUsername()+"] -> "+token);
	        		 
	        		 TransactionStatus status = transactionManager.getTransaction(getTransactionDefinition());
	        		 
	        		 LOGGER.info("save token in db...");
	        		 Login login = new Login(now, user.getUsername(), now, token);
	        		 
	        		 loginDao.persistLogin(login);
	        		 
	        		 transactionManager.commit(status);
	        		 
	        		 loginResponse.setToken(token);
	        		 loginResponse.setStatusOK();
	        		 
	        	 }else{
	        		 LOGGER.info("incorrect password");
		        	 loginResponse.setStatusError(STATUS.WARNING_PWD, "Password non corretta!"); 		 
	        	 }
	         }else{
	        	 LOGGER.info("username not found");
	        	 loginResponse.setStatusError(STATUS.WARNING_USER, "Usermame non trovata!"); 
	         }
	       
	        return loginResponse;
	            
		  }catch(WallaDBException we){
			  LOGGER.error(we.getMessage(),we);
			  loginResponse.setStatusError(STATUS.ERROR_DB, we.getMessage());
			  return loginResponse;
		  }catch(Exception e){
			  LOGGER.error(e.getMessage(),e);
			  loginResponse.setStatusError(STATUS.ERROR_UNEXPECTED, e.getMessage());
			  return loginResponse;
		  }finally{
			  LOGGER.info("LoginServices.login - END");  
		  }
	
	}
	
	
	public boolean checkLogin(String token) throws Exception{
		LOGGER.info("LoginServices.checkLogin - START");
		 try{
	         LOGGER.info("token:"+token);
	         Login login = loginDao.getLogin(token);
	         if(login == null){
	        	 return false;
	         }
	         return true;
	            
		  }catch(WallaDBException we){
			  LOGGER.error(we.getMessage(),we);
			  throw new Exception(we.getMessage());
		  }catch(Exception e){
			  LOGGER.error(e.getMessage(),e);
			  throw e;
		  }finally{
			  LOGGER.info("LoginServices.checkLogin - END");  
		  }
	
	}
	
	
}
