package it.mapler.walla.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Candidate;
import it.mapler.walla.model.Offer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CandidateDao extends AbsDao {

	
    private static final Logger LOGGER = LoggerFactory.getLogger(CandidateDao.class);
	
	private static final String TABLE_LOGIN = "login";
	private static final String TABLE_UTENTE = "utente";
	private static final String TABLE_CANDIDATO = "candidato";
	
	private class CandidateRowMapper implements RowMapper<Candidate>
	{

		@Override
		public Candidate mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Candidate candidato = new Candidate();			

			candidato.setNome(rs.getString("nome"));
			candidato.setCognome(rs.getString("cognome"));
			candidato.setCellulare(rs.getString("cellulare"));
			candidato.setIdcandidato(rs.getLong("idcandidato"));
			//TODO candidato.setCv(rs.getString("cv"));
			candidato.setIndirizzo(rs.getString("indirizzo"));
			candidato.setProvincia(rs.getString("provincia"));
			candidato.setRegione(rs.getString("regione"));
			candidato.setCitta(rs.getString("citta"));
			candidato.setCap(rs.getString("cap"));

			return candidato;
		}
  }
	
	
    // Ottieni singolo candidato (READ-SELECT)
	public Candidate getCandidate(Long iduser) throws WallaDBException
	{
    	LOGGER.info("UserDao.Candidate - START");
    	String sql = "SELECT "
    			+ "c.idcandidato as IDCANDIDATO,"
    			+ "c.nome as NOME,"
    			+ "c.cognome as COGNOME,"
    			+ "c.indirizzo as INDIRIZZO, "
    			+ "c.regione as REGIONE,"
    			+ "c.provincia as PROVINCIA,"
    			+ "c.citta as CITTA,"
    			+ "c.cap as CAP,"
    			+ "c.cv as CV,"
    			+ "c.cellulare as CELLULARE,"
    			+ "c.datadinascita as DATANASCITA,"
    			+ "c.annidiesperienza as ANNIESPERIENZA"
    			+ " FROM "+TABLE_CANDIDATO+" as C , "
    			+ TABLE_UTENTE+" as U "
    			+ "WHERE "
    		    + "C.iduser = ?"
    			+ " and "
    	        + "C.iduser = U.iduser";
    	
    	Candidate candidato = null; 
		try{
			candidato = jdbcTemplate.queryForObject(sql, new Object[] { iduser }, new CandidateRowMapper());
		}catch(EmptyResultDataAccessException ede){
			LOGGER.info(iduser + "not found");
		    return candidato; 
		}catch(Exception e){
			LOGGER.error("Error in CandidatoDao.getCandidato:"+e.getMessage(),e);
			throw new WallaDBException(e.getMessage());
		}finally{
			LOGGER.info("CandidatoDao.getCandidato - END");	
		}
		return candidato;
	}
	
    // Aggiorna singolo candidato (UPDATE)
	   public void candidateUpdate(Candidate candidato) throws WallaDBException {
		   LOGGER.info("Candidate.UpdateCandidate - START");
		   
		   try {
           List<Object> params = new ArrayList<Object>();
           List<Integer> types = new ArrayList<Integer>();
		   
			String  sqlUpdate = "UPDATE TABLE "+TABLE_CANDIDATO+ " ";
           
		   if(candidato.getNome() != null && !candidato.getNome().isEmpty())
		   {sqlUpdate += "SET nome="+candidato.getNome()+"," ;
		      params.add(candidato.getNome());
		     types.add(Types.VARCHAR);
		   }
		   if(candidato.getCognome() != null && !candidato.getCognome().isEmpty())
		   {sqlUpdate += "SET cognome="+candidato.getCognome()+"," ;
		      params.add(candidato.getCognome());
		     types.add(Types.VARCHAR);
		   }
		   if(candidato.getIndirizzo() != null && !candidato.getIndirizzo().isEmpty())
		   {sqlUpdate += "SET indirizzo="+candidato.getIndirizzo()+"," ;
		      params.add(candidato.getIndirizzo());
		     types.add(Types.VARCHAR);
		   }
		   if(candidato.getRegione() != null && !candidato.getRegione().isEmpty())
		   {sqlUpdate += "SET regione="+candidato.getRegione()+"," ;
		      params.add(candidato.getRegione());
		     types.add(Types.VARCHAR);
		   }
		   if(candidato.getProvincia() != null && !candidato.getProvincia().isEmpty())
		   {sqlUpdate += "SET provincia="+candidato.getProvincia()+"," ;
		      params.add(candidato.getProvincia());
		     types.add(Types.VARCHAR);
		   }
		   if(candidato.getCitta() != null && !candidato.getCitta().isEmpty())
		   {sqlUpdate += "SET citta="+candidato.getCitta()+"," ;
		      params.add(candidato.getCitta());
		     types.add(Types.VARCHAR);
		   }
		   if(candidato.getCap() != null && !candidato.getCap().isEmpty())
		   {sqlUpdate += "SET cap="+candidato.getCap()+"," ;
		      params.add(candidato.getCap());
		     types.add(Types.VARCHAR);
		   }
		   if(candidato.getCv() != null && !candidato.getCv().isEmpty())
		   {sqlUpdate += "SET cv="+candidato.getCv()+"," ;
		      params.add(candidato.getCv());
		     types.add(Types.VARCHAR);
		   }
		   if(candidato.getCellulare() != null && !candidato.getCellulare().isEmpty())
		   {sqlUpdate += "SET cellulare="+candidato.getCellulare()+"," ;
		      params.add(candidato.getCellulare());
		     types.add(Types.VARCHAR);
		   }
		   if(candidato.getDatanascita() != null)
		   {sqlUpdate += "SET datanascita="+candidato.getDatanascita()+"," ;
		      params.add(candidato.getDatanascita());
		     types.add(Types.TIMESTAMP);
		   }
		   if(candidato.getAnniesperienza() != null && !candidato.getAnniesperienza().isEmpty())
		   {sqlUpdate += "SET anniesperienza="+candidato.getAnniesperienza()+" " ;
		      params.add(candidato.getNome());
		     types.add(Types.VARCHAR);
		   }
		   // sistemare la virgola (es. se aggiorno un solo attributo ,)
		   sqlUpdate += "WHERE "+TABLE_CANDIDATO+".iduser = ?"
					   + "and" +TABLE_CANDIDATO+".username = ?";	
		   // update(String sql, Object args, int[] argTypes) 
		   
			jdbcTemplate.update(sqlUpdate,params,types);
			LOGGER.info(candidato +"added to the table " +TABLE_CANDIDATO);
			
		   }catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				LOGGER.error("Error in Candidate.UpdateCandidate[" + candidato + "]: " + e.getMessage(), e);
			    throw new WallaDBException("Error in Candidate.UpdateCandidate[" + candidato + "]:" + e.getMessage());
			}finally{
				LOGGER.info("Candidate.UpdateCandidate - END");
			}
		   		 
		   	 } 
	   
	   // Aggiungi candidato [ADD]
	   public void candidateAdd(Candidate candidato) throws WallaDBException {
			LOGGER.info("Candidate.InsertCandidate - START");
        try{	
		String  sqlAdd = "INSERT INTO "+ TABLE_CANDIDATO + 
							" (nome, cognome, username, indirizzo, regione, provincia, citta, cap, cv, cellulare, datanascita, anniesperienza) " +
							"VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" ;
		      
		Object[] params = { candidato.getNome(), candidato.getCognome(),candidato.getUsername(),candidato.getIndirizzo(),
				            candidato.getRegione(), candidato.getProvincia(), candidato.getCitta(), candidato.getCap(),
				            candidato.getCv(), candidato.getCellulare(), candidato.getDatanascita(), candidato.getAnniesperienza()
				          };   	  
	             
		LOGGER.info(candidato +"added to the table " +TABLE_CANDIDATO);
		 
		int[] types = { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, 
				        Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, 
				        Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP, Types.BIGINT };	
	   
		jdbcTemplate.update(sqlAdd,params,types);
		LOGGER.info(candidato +"added to the table " +TABLE_CANDIDATO);
		
	   } catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			LOGGER.error("Error in Candidate.InsertCandidate[" + candidato + "]: " + e.getMessage(), e);
		    throw new WallaDBException("Error in Candidate.InsertCandidate[" + candidato + "]:" + e.getMessage());
		}finally{
			LOGGER.info("Offer.InsertCandidate - END");
		}
        
	   } 
	
		
	
}
