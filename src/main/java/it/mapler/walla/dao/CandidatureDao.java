package it.mapler.walla.dao;

import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Candidature;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

public class CandidatureDao extends AbsDao {

private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceDao.class);
	
	private static final String TABLE_LOGIN = "login";
	private static final String TABLE_UTENTE = "utente";
	private static final String TABLE_CANDIDATO = "candidato";	
	private static final String TABLE_CANDIDATURA = "candidatura";	

	
	private class CandidatureRowMapper implements RowMapper<Candidature>
	{

		@Override
		public Candidature mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Candidature canidatura = new Candidature();
			canidatura.setIdcandidatura(rs.getLong("idcandidatura"));
			canidatura.setIdcandidato(rs.getLong("idcandidato"));
			canidatura.setIdofferta(rs.getLong("idofferta"));
			canidatura.setDescrizione(rs.getString("descrizione"));

			return canidatura;
		}
  }	
	
	
    // Ottieni singola Candidatura (READ-SELECT)
	public Candidature getCandidature(String idcandidature, String username) throws WallaDBException
	{
    	LOGGER.info("CandidatureDao.getCandidature - START");
    	String sql = "SELECT "
    			+ "c.idcandidatura as IDCANDIDATURA,"
    			+ "c.idcandidato as IDCANDIDATO,"
    			+ "c.idofferta as IDOFFERTA,"
    			+ "c.descrizione as DESCRIZIONE "
    			+ "FROM "+TABLE_CANDIDATURA+" as C, "
    			+ TABLE_CANDIDATO+" as CC, "
    			+ TABLE_UTENTE+" as U "
    			+ "WHERE "
    		    + ""+TABLE_CANDIDATURA+".idcandidato ="+ TABLE_CANDIDATO+".idcandidato "
		        + "and"
    		    + ""+TABLE_CANDIDATO+".iduser ="+ TABLE_UTENTE+".iduser "
    		    + "and"
	            + ""+TABLE_CANDIDATURA+".idcandidatura = ?"
	            + "and"
	            + TABLE_UTENTE+".username = ?";
    	
    	Candidature candidature = null; 
		try{
			candidature = jdbcTemplate.queryForObject(sql, new Object[] { idcandidature, username }, new CandidatureRowMapper());
		}catch(EmptyResultDataAccessException ede){
			LOGGER.info(idcandidature + "not found");
		    return candidature; 
		}catch(Exception e){
			LOGGER.error("Error in CandidatureDao.getCandidature:"+e.getMessage(),e);
			throw new WallaDBException(e.getMessage());
		}finally{
			LOGGER.info("CandidatureDao.getCandidature - END");	
		}
		return candidature;
	}
	
}
