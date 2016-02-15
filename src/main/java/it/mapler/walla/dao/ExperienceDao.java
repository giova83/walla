package it.mapler.walla.dao;

import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Experience;
import it.mapler.walla.model.Product;

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

public class ExperienceDao extends AbsDao {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceDao.class);
	
	private static final String TABLE_LOGIN = "login";
	private static final String TABLE_UTENTE = "utente";
	private static final String TABLE_CANDIDATO = "candidato";	
	private static final String TABLE_ESPERIENZA = "esperienza";	
	
	
	private class ExperienceRowMapper implements RowMapper<Experience>
	{

		@Override
		public Experience mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Experience experience = new Experience();
			experience.setIdesperienza(rs.getLong("idesperienza"));
			experience.setIdcandidato(rs.getLong("idcandidato"));
			experience.setEsperienza(rs.getString("experienza"));
			experience.setQualifica(rs.getString("qualifica"));
            experience.setTipologia(rs.getString("tipologia"));
            experience.setSettore(rs.getString("settore"));
            experience.setLivello(rs.getString("livello"));
            experience.setAnni(rs.getLong("anni"));

			return experience;
		}
  }	
	
	
    // Ottieni singola esperienza (READ-SELECT)
	public Experience getExperience(String username, String idesperienza) throws WallaDBException
	{
    	LOGGER.info("ExperienceDao.getExperience - START");
    	String sql = "SELECT "
    			+ "e.esperienza as ESPERIENZA,"
    			+ "e.qualifica as QUALIFICA,"
    			+ "e.tipologia as TIPOLOGIA,"
    			+ "e.settore as SETTORE,"
    			+ "e.livello as LIVELLO,"
    			+ "e.anni as ANNI "
    			+ "FROM "+TABLE_ESPERIENZA+" as E, "
    			+ TABLE_CANDIDATO+" as C, "
    			+ TABLE_UTENTE+" as U "
    			+ "WHERE "
    		    + ""+TABLE_ESPERIENZA+".idcandidato ="+ TABLE_CANDIDATO+".idcandidato "
		        + "and"
    		    + ""+TABLE_CANDIDATO+".iduser ="+ TABLE_UTENTE+".iduser "
    		    + "and"
	            + ""+TABLE_ESPERIENZA+".idesperienza = ?"
	            + "and"
	            + TABLE_UTENTE+".username = ?";
    	
    	Experience experience = null; 
		try{
			experience = jdbcTemplate.queryForObject(sql, new Object[] { username, idesperienza }, new ExperienceRowMapper());
		}catch(EmptyResultDataAccessException ede){
			LOGGER.info(username + "not found");
		    return experience; 
		}catch(Exception e){
			LOGGER.error("Error in ExperienceDao.getExperience:"+e.getMessage(),e);
			throw new WallaDBException(e.getMessage());
		}finally{
			LOGGER.info("ExperienceDao.getExperience - END");	
		}
		return experience;
	}
	
    // Ottieni elenco Esperienze del candidato (READ-SELECT)
	public List<Experience> getCandidateExperiencesExperiences(String userName) throws WallaDBException
	{
    	LOGGER.info("ExperienceDao.getCandidateExperience - START");
    	String sql = "SELECT "
    			+ "e.esperienza as ESPERIENZA,"
    			+ "e.qualifica as QUALIFICA,"
    			+ "e.tipologia as TIPOLOGIA,"
    			+ "e.settore as SETTORE,"
    			+ "e.livello as LIVELLO,"
    			+ "e.anni as ANNI "
    			+ "FROM "+TABLE_ESPERIENZA+" as E ,"
    			+ TABLE_CANDIDATO+" C ,"
    		    + ""+TABLE_UTENTE+" U "
    			+ " WHERE "
    			+ " e.idcandidato = c.idcandidato"
    			+ " and c.iduser = u.iduser "
    			+ " and "+TABLE_UTENTE+".username = ?" ;

    	List<Experience> experiences = null;
	      try{		 
	    	  experiences = jdbcTemplate.query(sql, new Object[] { userName }, new ExperienceRowMapper());
	    	}catch(Exception e){
	    		LOGGER.info("error in getCandidateExperiencesExperiences :"+e.getMessage(),e);
	    		throw new WallaDBException("error in getCandidateExperiencesExperiences:"+e.getMessage());
	    	}finally{
	    		LOGGER.info("ExperienceDao.getCandidateExperiencesExperiences  - END");
	    	}
	      
	    	return experiences;
	}	
	
	
	
	// ritorna un elenco di Esperienze filtrati per i parametri inseriti
	   public List<Experience> findAllExperiencesByPar(String esperienza, String qualifica,
			   String tipologia, String settore, String livello, Long anni )
					   throws WallaDBException
	   {
	    	LOGGER.info("ExperienceDao.findAllExperiencesByPar - START");
	    	String sql = " SELECT "
	    			+ "e.esperienza as ESPERIENZA,"
	    			+ "e.qualifica as QUALIFICA,"
	    			+ "e.tipologia as TIPOLOGIA,"
	    			+ "e.settore as SETTORE,"
	    			+ "e.livello as LIVELLO,"
	    			+ "e.anni as ANNI "
	    			+ " FROM "+TABLE_ESPERIENZA+" E  WHERE 1=1 AND  ";
	    	
	    	//Object[] elenco_parametri = new Object[] {};// QUI
	    	            List<Object> params = new ArrayList<Object>();
	    	            List<Integer> types = new ArrayList<Integer>(); 
	    	           
	    	            if(esperienza != null && !esperienza.isEmpty())
	    			   {sql +=" and E.esperienza =  ? " ;
	    			     params.add(esperienza);
	    			     types.add(Types.VARCHAR);	    			   
	    			   } 
	    			   if(qualifica != null && !qualifica.isEmpty())
	    			   {sql +=" and E.qualifica =  ? " ;
	    			      params.add(qualifica);
	    			     types.add(Types.VARCHAR);
	    			   } 
	    			   
	    			   if(tipologia != null && !tipologia.isEmpty())
	    			   {sql +=" and E.tipologia =  ? " ;
	    			   params.add(tipologia);
	    			     types.add(Types.VARCHAR);
	    			   } 
	    			   
	    			   if(settore != null && !settore.isEmpty())
	    			   {sql +=" and E.settore = ? " ;
	    			      params.add(settore);
	    			     types.add(Types.VARCHAR);
	    			   } 
	    			   if(livello != null && !livello.isEmpty())
	    			   {sql +=" and E.livello =  ? " ;
	    			   params.add(livello);
	    			     types.add(Types.VARCHAR);
	    			   } 
	    			   
	    			   if( anni!= null)
	    			   {sql +=" and E.anni =  ? " ;
	    			   params.add(anni);
	    			     types.add(Types.INTEGER);
	    			   } 

	    			   List<Experience> experiences = null;
	      try{
	    	  int [] typesP = ArrayUtils.toPrimitive(types.toArray(new Integer[types.size()]));
	    	  experiences = jdbcTemplate.query(sql, params.toArray(), typesP ,new ExperienceRowMapper());
	    	}catch(Exception e){
	    		LOGGER.info("error in findAllExperiencesByPar:"+e.getMessage(),e);
	    		throw new WallaDBException("error in findAllExperiencesByPar:"+e.getMessage());
	    	}finally{
	    		LOGGER.info("ExperienceDAo.findAllExperiencesByPar - END");
	    	}
	      
	    	return experiences;
	    }
	
	
	// Aggiorna singola Esperienza (UPDATE)
	   public void experienceUpdate(Experience experience) throws WallaDBException 
	   {
		   LOGGER.info("Experience.UpdateExperience - START");
		   
		   try {
           List<Object> params = new ArrayList<Object>();
           List<Integer> types = new ArrayList<Integer>();
		   
			String  sqlUpdate = "UPDATE TABLE "+TABLE_ESPERIENZA+ " ";
           
		   if(experience.getEsperienza() != null && ! experience.getEsperienza().isEmpty())
		   {
			  sqlUpdate += "SET esperienza="+experience.getEsperienza()+"," ;
		      params.add(experience.getEsperienza());
		     types.add(Types.VARCHAR);
		   }
		   if(experience.getQualifica() != null && !experience.getQualifica().isEmpty())
		   {sqlUpdate += "SET qualifica="+experience.getQualifica()+"," ;
		      params.add(experience.getQualifica());
		     types.add(Types.VARCHAR);
		   }
		   
		   if(experience.getTipologia() != null && !experience.getTipologia().isEmpty())
		   {sqlUpdate += "SET tipologia="+experience.getTipologia()+"," ;
		      params.add(experience.getTipologia());
		     types.add(Types.VARCHAR);
		   }
		   if(experience.getSettore() != null && !experience.getSettore().isEmpty())
		   {sqlUpdate += "SET settore="+experience.getSettore()+"," ;
		      params.add(experience.getSettore());
		     types.add(Types.VARCHAR);
		   }
		   if(experience.getLivello() != null)
		   {sqlUpdate += "SET livello="+experience.getLivello()+"," ;
		      params.add(experience.getLivello());
		     types.add(Types.VARCHAR);
		   }
		   
		   if(experience.getAnni() != null)
		   {sqlUpdate += "SET anni="+experience.getAnni()+"," ;
		      params.add(experience.getAnni());
		     types.add(Types.INTEGER);
		   }

		   // sistemare la virgola (es. se aggiorno un solo attributo ,)
		   sqlUpdate += "WHERE "+TABLE_ESPERIENZA+".idesperienza = ?";

			jdbcTemplate.update(sqlUpdate,params,types);
			LOGGER.info(experience +"added to the table " +TABLE_ESPERIENZA);
			
		   }catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				LOGGER.error("Error in Experience.UpdateExperience[" + experience + "]: " + e.getMessage(), e);
			    throw new WallaDBException("Error in Experience.UpdateExperience[" + experience + "]:" + e.getMessage());
			}finally{
				LOGGER.info("Experience.UpdateExperience - END");
			}
		   		 
		   	 } 
	   
	   
	   // Aggiungi nuova Esperienza
	   public void experienceAdd(Experience experience) throws WallaDBException 
	   {
			LOGGER.info("Experience.InsertExperience - START");
        try{	
		String  sqlAdd = "INSERT INTO "+ TABLE_ESPERIENZA + 
				" (idesperienza, idcandidato, esperienza, qualifica, anni, tipologia, settore, livello) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?)" ;
		      
		Object[] params = {experience.getIdesperienza(),experience.getIdcandidato(),experience.getEsperienza(),
				experience.getQualifica(), experience.getAnni(),experience.getTipologia(),
				experience.getSettore(), experience.getLivello()};   	  
	             
		LOGGER.info(experience +"added to the table " +TABLE_ESPERIENZA);
		 
		int[] types = { Types.BIGINT, Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.INTEGER,
				Types.VARCHAR, Types.VARCHAR, Types.VARCHAR  };	
	   
		jdbcTemplate.update(sqlAdd,params,types);
		LOGGER.info(experience +"added to the table " +TABLE_ESPERIENZA);
		
	   } catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			LOGGER.error("Error in Experience.InsertExperience[" + experience + "]: " + e.getMessage(), e);
		    throw new WallaDBException("Error in Experience.InsertExperience[" + experience + "]:" + e.getMessage());
		}finally{
			LOGGER.info("Experience.InsertExperience - END");
		}
        
	   }   
	   
	   
}
