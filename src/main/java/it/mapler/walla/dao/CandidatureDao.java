package it.mapler.walla.dao;

import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Candidature;
import it.mapler.walla.model.Offer;
import it.mapler.walla.model.Product;
import it.mapler.walla.model.Restaurant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CandidatureDao extends AbsDao {

private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceDao.class);
	
	private static final String TABLE_UTENTE = "utente";
	private static final String TABLE_CANDIDATO = "candidato";	
	private static final String TABLE_CANDIDATURA = "candidatura";	
	private static final String TABLE_OFFERTA = "offerta";
	private static final String TABLE_RISTORANTE = "ristorante";

	
	private class CandidatureRowMapper implements RowMapper<Candidature>
	{

		@Override
		public Candidature mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Candidature candidatura = new Candidature();
			candidatura.setIdcandidatura(rs.getLong("idcandidatura"));
			candidatura.setIdcandidato(rs.getLong("idcandidato"));
			candidatura.setIdofferta(rs.getLong("idofferta"));
			candidatura.setDescrizione(rs.getString("descrizione"));
			Offer offer = new Offer();
			offer.setTitolo(rs.getString("titolo"));
			Restaurant ristorante = new Restaurant();
            ristorante.setNome(rs.getString("nome"));
            candidatura.setOfferta(offer);	
            candidatura.setRistorante(ristorante);

			return candidatura;
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
    			+ "FROM "+TABLE_CANDIDATURA+" , "
    			+ TABLE_CANDIDATO+" , "
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
	
	
    // Ottieni elenco Candidature del Candidato (READ-SELECT)
	public List<Candidature> getAllCandidatureByCandidate(Long iduser) throws WallaDBException
	{
    	LOGGER.info("CandidatureDao.getAllCandidatureByCandidate - START");
    	String sql = "SELECT "
    			+ "c.idcandidatura as IDCANDIDATURA,"
    			+ "c.idcandidato as IDCANDIDATO,"
    			+ "c.idofferta as IDOFFERTA,"
    			+ "o.titolo as TITOLO,"
    			+ "r.nome as NOME,"
    			+ "c.descrizione as DESCRIZIONE, "
    			+ "c.datacandidatura as DATACANDIDATURA "
    			+ "FROM "+TABLE_CANDIDATURA+" as C ," +TABLE_CANDIDATO+" CC ,"
    					+ ""+TABLE_UTENTE+" as U ,"+TABLE_OFFERTA+" as O, "+TABLE_RISTORANTE+ " as R"
    			+ " WHERE c.idcandidato = cc.idcandidato and c.idofferta = o.idofferta and o.idristorante = r.idristorante "
    			+ " and u.iduser = cc.iduser "
    			+ " and U.iduser = ?" ;

    	List<Candidature> candidatures = null;
	      try{		 
	    	  candidatures = jdbcTemplate.query(sql, new Object[] { iduser }, new CandidatureRowMapper());
	    	}catch(Exception e){
	    		LOGGER.info("error in getAllCandidatureByCandidate :"+e.getMessage(),e);
	    		throw new WallaDBException("error in getAllCandidatureByCandidate:"+e.getMessage());
	    	}finally{
	    		LOGGER.info("CandidatureDao.getAllCandidatureByCandidate  - END");
	    	}
	      
	    	return candidatures;
	}
	
	
	// ritorna un elenco di Candidature filtrati per i parametri inseriti
	   public List<Candidature> findAllCandidaturessByPar(String descrizione)
					   throws WallaDBException
	   {
	    	LOGGER.info("CandidatureDao.findAllCandidaturessByPar - START");
	    	String sql = " SELECT "
	    			+ "c.idcandidatura as IDCANDIDATURA,"
	    			+ "c.idcandidato as IDCANDIDATO,"
	    			+ "c.idofferta as IDOFFERTA,"
	    			+ "c.descrizione as DESCRIZIONE, "
	    			+ "c.datacandidatura as DATACANDIDATURA "
	    			+ " FROM "+TABLE_CANDIDATURA+" P  WHERE 1=1 AND  ";
	    	
	    	//Object[] elenco_parametri = new Object[] {};// QUI
	    	            List<Object> params = new ArrayList<Object>();
	    	            List<Integer> types = new ArrayList<Integer>(); 
	    	           
	    	            if(descrizione != null && !descrizione.isEmpty())
	    			   {sql +=" and C.descrizione =  ? " ;
	    			     params.add(descrizione);
	    			     types.add(Types.VARCHAR);	    			   
	    			   }    

	    			   List<Candidature> candidatures = null;
	      try{
	    	  int [] typesP = ArrayUtils.toPrimitive(types.toArray(new Integer[types.size()]));
	    	  candidatures = jdbcTemplate.query(sql, params.toArray(), typesP ,new CandidatureRowMapper());
	    	}catch(Exception e){
	    		LOGGER.info("error in findAllCandidaturessByPar:"+e.getMessage(),e);
	    		throw new WallaDBException("error in findAllCandidaturessByPar:"+e.getMessage());
	    	}finally{
	    		LOGGER.info("CandidatureDAo.findAllCandidaturessByPar - END");
	    	}
	      
	    	return candidatures;
	    }
	   
	   
	   
	    // Aggiorna singola Candidatura (UPDATE)
		   public void candidatureUpdate(Candidature candidature) throws WallaDBException 
		   {
			   LOGGER.info("Candidature.UpdateCandidature - START");
			   
			   try {
	           List<Object> params = new ArrayList<Object>();
	           List<Integer> types = new ArrayList<Integer>();
			   
				String  sqlUpdate = "UPDATE TABLE "+TABLE_CANDIDATURA+ " ";
	           
			   if(candidature.getDescrizione() != null && !candidature.getDescrizione().isEmpty())
			   {sqlUpdate += "SET descrizione="+candidature.getDescrizione() ;
			      params.add(candidature.getDescrizione());
			     types.add(Types.VARCHAR);
			   }
			   

			   // sistemare la virgola (es. se aggiorno un solo attributo ,)
			   sqlUpdate += "WHERE "+TABLE_CANDIDATURA+".idcandidatura = ?";

			   
				jdbcTemplate.update(sqlUpdate,params,types);
				LOGGER.info(candidature +"added to the table " +TABLE_CANDIDATURA);
				
			   }catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					LOGGER.error("Error in Candidature.candidatureUpdate[" + candidature + "]: " + e.getMessage(), e);
				    throw new WallaDBException("Error in Candidature.candidatureUpdate[" + candidature + "]:" + e.getMessage());
				}finally{
					LOGGER.info("Candidature.candidatureUpdate - END");
				}
			   		 
			   	 } 
		   
		   
		   // Aggiungi nuova Candidatura
		   public void candidatureAdd(Candidature candidature) throws WallaDBException {
				LOGGER.info("Candidature.InsertCandidature - START");
	        try{	
			String  sqlAdd = "INSERT INTO "+ TABLE_CANDIDATURA + 
								" (idcandidatura, idcandidato, idofferta, descrizione) " +
								"VALUES (?, ?, ?, ?)" ;
			      
			Object[] params = {candidature.getIdcandidatura(),candidature.getIdcandidato(), 
					           candidature.getIdofferta(), candidature.getDescrizione()};   	  
		             
			LOGGER.info(candidature +"added to the table " +TABLE_CANDIDATURA);
			 
			int[] types = { Types.BIGINT, Types.BIGINT, Types.BIGINT, Types.VARCHAR };	
		   
			jdbcTemplate.update(sqlAdd,params,types);
			LOGGER.info(candidature +"added to the table " +TABLE_CANDIDATURA);
			
		   } catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				LOGGER.error("Error in Candidature.InsertCandidature[" + candidature + "]: " + e.getMessage(), e);
			    throw new WallaDBException("Error in Candidature.InsertCandidature[" + candidature + "]:" + e.getMessage());
			}finally{
				LOGGER.info("candidature.InsertCandidature - END");
			}
	        
		   }
	
}
