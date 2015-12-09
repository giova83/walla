package it.mapler.walla.dao;

import java.sql.Types;

import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao extends AbsDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginDao.class);
	
	private static final String TABLE_LOGIN = "login";
	
	public void persistLogin(Login login) throws WallaDBException{
		LOGGER.info("Login.persistLogin - START");
        try{
        	
	     String  sql = "INSERT INTO "+ TABLE_LOGIN + 
						" (username, dataaccesso, token, dataultimamodifica) " +
						"VALUES (?, ?, ?, ?)" ;
	      
	      Object[] params = {login.getUsername(), login.getDataAccesso(), login.getToken()
					, login.getDataUltimaModifica()};
	      
	      
	      int[] types = { Types.VARCHAR,	
			              Types.TIMESTAMP,	
			              Types.VARCHAR,
			              Types.TIMESTAMP	  
          };	
				        
	      jdbcTemplate.update(sql,params,types);
	        
		  LOGGER.info(login +"added to the table " +TABLE_LOGIN);
		  
        }catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			LOGGER.error("Error in Login.persistLogin[" + login + "]: " + e.getMessage(), e);
		    throw new WallaDBException("Error in Login.persistLogin[" + login + "]:" + e.getMessage());
		}finally{
			LOGGER.info("Login.persistLogin - END");
		}
		
	}
	

}
