package it.mapler.walla.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import it.mapler.walla.enumeration.PROFILE;
import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbsDao{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);
	
	private static final String TABLE_USER = "utente";
	
    public User getUser(String userName) throws WallaDBException{
    	LOGGER.info("UserDao.getUser - START");
		String sql = "SELECT * FROM "+TABLE_USER+" WHERE username = ?";
		User user = null;
		try{ 
		     user = (User) jdbcTemplate.queryForObject(sql, new Object[] { userName }, new UserRowMapper());
		}catch(EmptyResultDataAccessException ede){
			LOGGER.info(userName + "not found");
		    return user; 
		}catch(Exception e){
			LOGGER.error("Error in UserDao.getUser:"+e.getMessage(),e);
			throw new WallaDBException(e.getMessage());
		}finally{
			LOGGER.info("UserDao.getUser - END");	
		}
		return user;
	}
    
	private class UserRowMapper implements RowMapper<User>{

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			User user = new User();
			user.setIdUser(rs.getLong("iduser"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setEmail(rs.getString("email"));
			user.setResetPwd(rs.getString("resetpwd"));
			user.setProfilo(PROFILE.getProfile(rs.getString("idprofilo")));
			
			return user;
		}
  }

}
