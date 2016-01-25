package it.mapler.walla.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LoginDao extends AbsDao{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginDao.class);
	
	private static final String TABLE_LOGIN = "login";
	
	public void persistLogin(Login login) throws WallaDBException{
		LOGGER.info("LoginDao.persistLogin - START");
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
			LOGGER.error("Error in LoginDao.persistLogin[" + login + "]: " + e.getMessage(), e);
		    throw new WallaDBException("Error in Login.persistLogin[" + login + "]:" + e.getMessage());
		}finally{
			LOGGER.info("LoginDao.persistLogin - END");
		}
		
	}
	
	public Login getLogin(String token) throws WallaDBException{
		LOGGER.info("LoginDao.getLogin - START");
		String sql = "SELECT * FROM "+TABLE_LOGIN+" WHERE  token = ?";
		Login login = null;
		try{ 
		     login = (Login) jdbcTemplate.queryForObject(sql, new Object[] { token }, new LoginRowMapper());
		}catch(EmptyResultDataAccessException ede){
			LOGGER.info(token + " not found");
		    return null; 
		}catch(Exception e){
			LOGGER.error("Error in LoginDao.getLogin:"+e.getMessage(),e);
			throw new WallaDBException(e.getMessage());
		}finally{
			LOGGER.info("LoginDao.getLogin - END");	
		}
		
		return login;
	  }
	
	private class LoginRowMapper implements RowMapper<Login>{

		@Override
		public Login mapRow(ResultSet rs, int rowNum) throws SQLException {
			Login login = new Login();
			login.setToken(rs.getString("token"));
			login.setUsername(rs.getString("username"));
			return login;
		}
  }

}
