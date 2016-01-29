package it.mapler.walla.dao;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;





import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Candidate;
import it.mapler.walla.model.Profile;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

public class ProfileDao extends AbsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileDao.class);
	
	private static final String TABLE_LOGIN = "login";
	private static final String TABLE_UTENTE = "utente";

	
	private class ProfileRowMapper implements RowMapper<Profile>{

		@Override
		public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Profile profilo = new Profile();
			profilo.setTipoProfilo(rs.getString("idprofilo"));
			return profilo;
		}
  }
	
	
    // Ottieni profilo utente dai parametri iduser e username
	public Profile getUserProfile(Long iduser,String username) throws WallaDBException{
    	LOGGER.info("UserDao.getOffer - START");
    	String sql = 
    			"SELECT "
    			+ "U.username as USERNAME, "
    			+ "U.iduser as IDUSER, "
    			+ "U.idprofilo as IDPROFILO"
    			+ "FROM "
    			+ ""+TABLE_LOGIN+" L, "
    			+ TABLE_UTENTE+" U "
    			+ " where  "+TABLE_LOGIN+".username = "+TABLE_UTENTE+".username and "
    			+TABLE_LOGIN+".iduser = "+TABLE_UTENTE+".iduser and "+TABLE_UTENTE+".iduser = ?";

    	Profile profile = null; 
		
    	try{
			
			profile = (Profile) jdbcTemplate.queryForObject(sql, new Object[] { iduser,username }, new ProfileRowMapper());
			
		}catch(EmptyResultDataAccessException ede){
			LOGGER.info(iduser + "not found");
		    return profile; 
		}catch(Exception e){
			LOGGER.error("Error in ProfileDao.getProfile:"+e.getMessage(),e);
			throw new WallaDBException(e.getMessage());
		}finally{
			LOGGER.info("ProfileDao.getProfile - END");	
		}
		return profile;
	}
		
	
}
