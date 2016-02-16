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

import it.mapler.walla.enumeration.PROFILE;
import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Login;
import it.mapler.walla.model.Offer;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class OfferDao extends AbsDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(OfferDao.class);

	private static final String TABLE_OFFER = "offerta";
	private static final String TABLE_RISTORANTE = "ristorante";
	private static final String TABLE_CANDIDATO = "candidato";

    // Ottieni singola Offerta
	public Offer getOffer(String idOfferta) throws WallaDBException{
    	LOGGER.info("UserDao.getOffer - START");
    	String sql = "SELECT * FROM "+TABLE_OFFER+" WHERE "+TABLE_OFFER+".idofferta = ?";
    	/*		String sql = "SELECT * FROM "+TABLE_OFFER+" left join "+TABLE_CANDIDATO+" ON "+TABLE_OFFER+".CITTA = "+TABLE_CANDIDATO+".CITTA"
				+ " WHERE "+TABLE_OFFER+".citta = ?";*/
		Offer offer = null;
		try{
		     offer = (Offer) jdbcTemplate.queryForObject(sql, new Object[] { idOfferta }, new OfferRowMapper());
		}catch(EmptyResultDataAccessException ede){
			LOGGER.info(idOfferta + "not found");
		    return offer;
		}catch(Exception e){
			LOGGER.error("Error in UserDao.getUser:"+e.getMessage(),e);
			throw new WallaDBException(e.getMessage());
		}finally{
			LOGGER.info("UserDao.getOffer - END");
		}
		return offer;
	}



	private class OfferRowMapper implements RowMapper<Offer>{

		@Override
		public Offer mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Offer offer = new Offer();
			offer.setIdofferta(rs.getLong("idofferta"));
			offer.setIdristorante(rs.getLong("idristorante"));
			offer.setNome(rs.getString("nome"));
			offer.setIndirizzo(rs.getString("indirizzo"));
			offer.setCitta(rs.getString("citta"));
			offer.setTitolo(rs.getString("titolo"));
			offer.setTipologia(rs.getString("tipologia"));
			offer.setCategoria(rs.getString("categoria"));
			offer.setDescrizione(rs.getString("descrizione"));
			Calendar cal = new GregorianCalendar();
			cal.setTimeInMillis(rs.getTimestamp("datapubblicazione").getTime());
			offer.setDatapubblicazione(cal);

			return offer;
		}
  }

	// ritorna un elenco di Offerte
	   public List<Offer> findAllOfferByUserCity(Long idUser)throws WallaDBException{
	    	LOGGER.info("UserDao.getAllOffer - START");
	    	String sql = " SELECT "
	    			+ " O.IDRISTORANTE as IDRISTORANTE,"
	    			+ " R.NOME as NOME,"
	    			+ " R.INDIRIZZO as INDIRIZZO,"
	    			+ " R.CITTA as CITTA,"
	    			+ " O.IDOFFERTA as IDOFFERTA,"
	    			+ " O.TITOLO AS TITOLO,"
	    			+ " O.CATEGORIA AS CATEGORIA,"
	    			+ " O.DESCRIZIONE AS DESCRIZIONE, "
	    			+ " O.TIPOLOGIA AS TIPOLOGIA,"
	    			+ " O.DATAPUBBLICAZIONE AS DATAPUBBLICAZIONE"
	    			+ " FROM "+TABLE_OFFER+"  O , "+TABLE_CANDIDATO+" C , "+TABLE_RISTORANTE+" R "
	    		    + " WHERE R.CITTA=C.CITTA and R.IDRISTORANTE=O.IDRISTORANTE "
	    			+ " and C.IDUSER = ?";
	    	List<Offer> offers = null;
	      try{
	    	offers = jdbcTemplate.query(sql, new Object[] { idUser }, new OfferRowMapper());
	    	}catch(Exception e){
	    		LOGGER.info("error in findAllOfferByUserCity:"+e.getMessage(),e);
	    		throw new WallaDBException("error in findAllOfferByUserCity:"+e.getMessage());
	    	}finally{
	    		LOGGER.info("UserDao.getAllOffer - END");
	    	}

	    	return offers;
	    }


		// ritorna un elenco di Offerte filtrate per i parametri inseriti
	   public List<Offer> findAllOfferByPar(String citta, String categoria,String tipologia, String titolo, Calendar datapubblicazione)throws WallaDBException{
	    	LOGGER.info("OfferDao.findAllOfferByPar - START");
	    	String sql = " SELECT "
	    			+ " O.IDRISTORANTE as IDRISTORANTE,"
	    			+ " R.NOME as NOME,"
	    			+ " R.INDIRIZZO as INDIRIZZO,"
	    			+ " R.CITTA as CITTA,"
	    			+ " O.IDOFFERTA as IDOFFERTA,"
	    			+ " O.TITOLO AS TITOLO,"
	    			+ " O.CATEGORIA AS CATEGORIA,"
	    			+ " O.DESCRIZIONE AS DESCRIZIONE, "
	    			+ " O.TIPOLOGIA AS TIPOLOGIA,"
	    			+ " O.DATAPUBBLICAZIONE AS DATAPUBBLICAZIONE "
	    			+ " FROM "+TABLE_OFFER+" O , "+TABLE_RISTORANTE+" R WHERE 1=1 AND R.IDRISTORANTE = O.IDRISTORANTE  ";

	    	//Object[] elenco_parametri = new Object[] {};// QUI
	    	            List<Object> params = new ArrayList<Object>();
	    	            List<Integer> types = new ArrayList<Integer>();
	    	           if(citta != null && !citta.isEmpty())
	    			   {sql +=" and R.citta =  ? " ;
	    			     params.add(citta);
	    			     types.add(Types.VARCHAR);
	    			   }
	    			   if(categoria != null && !categoria.isEmpty())
	    			   {sql +=" and O.categoria =  ? " ;
	    			      params.add(categoria);
	    			     types.add(Types.VARCHAR);
	    			   }
	    			   if(tipologia != null && !tipologia.isEmpty())
	    			   {sql +=" and O.tipologia = ? " ;
	    			      params.add(tipologia);
	    			     types.add(Types.VARCHAR);
	    			   }
	    			   if(titolo != null && !titolo.isEmpty())
	    			   {sql +=" and O.titolo =  ? " ;
	    			   params.add(titolo);
	    			     types.add(Types.VARCHAR);

	    			   }
	    			   if(datapubblicazione != null)
	    			   {sql +="and O.datapubblicazione =  ? " ;
	    			      params.add(datapubblicazione);
	    			     types.add(Types.TIMESTAMP);
	    			   }

	    			  // definire parametri dinamic

	    			   List<Offer> offers = null;
	      try{
	    	  int [] typesP = ArrayUtils.toPrimitive(types.toArray(new Integer[types.size()]));
	    	  offers = jdbcTemplate.query(sql, params.toArray(), typesP ,new OfferRowMapper());// QUI
	    	}catch(Exception e){
	    		LOGGER.info("error in findAllOfferByPar:"+e.getMessage(),e);
	    		throw new WallaDBException("error in findAllOfferByPar:"+e.getMessage());
	    	}finally{
	    		LOGGER.info("OfferDAo.findAllOfferByPar - END");
	    	}

	    	return offers;
	    }


	   public void offerUpdate(Offer offer) throws WallaDBException {
		   LOGGER.info("Offer.UpdateOffer - START");

			String  sqlUpdate = "UPDATE TABLE "+TABLE_OFFER+" "
					+ "SET categoria="+offer.getCategoria()+","
					+ "SET tipologia="+offer.getTipologia()+","
					+ "SET titolo="+offer.getTitolo()+","
					+ "SET descrizione="+offer.getDescrizione()+" "
					+ "WHERE idofferta = ?";
			Object[] params = {offer.getIdofferta()};
			int[] types = { Types.VARCHAR};

		   	 }

	   public void offerDelete(String idOfferta) throws WallaDBException {
			LOGGER.info("Offer.DeleteOffer - START");
		try{
		String  sqlDelete = "DELETE FROM "+TABLE_OFFER+" WHERE idofferta = ?";
		Object[] params = {idOfferta};
		int[] types = { Types.VARCHAR};

		jdbcTemplate.update(sqlDelete,params,types);
		LOGGER.info(idOfferta +"deleted from the table " +TABLE_OFFER);
	      } catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					LOGGER.error("Error in Offer.DeleteOffer[" + idOfferta + "]: " + e.getMessage(), e);
				    throw new WallaDBException("Error in Offer.DeleteOffer[" + idOfferta + "]:" + e.getMessage());
				}finally{
					LOGGER.info("Offer.DeleteOffer - END");
				}
		   	 }

	   public void offerAdd(Offer offer) throws WallaDBException {
			LOGGER.info("Offer.InsertOffer - START");
        try{
		String  sqlAdd = "INSERT INTO "+ TABLE_OFFER +
							" (descrizione, categoria, idristorante, tipologia, titolo, datapubblicazione, offerta_attiva) " +
							"VALUES (?, ?, ?, ?, ?, ?, ?)" ;

		Object[] params = {offer.getDescrizione(), offer.getCategoria()
						, offer.getIdristorante(),offer.getTipologia(),offer.getTitolo(),offer.getDatapubblicazione(), offer.getAttiva()};

		LOGGER.info(offer +"added to the table " +TABLE_OFFER);

		int[] types = {Types.VARCHAR, Types.VARCHAR, Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP , Types.TINYINT};

		jdbcTemplate.update(sqlAdd,params,types);
		LOGGER.info(offer +"added to the table " +TABLE_OFFER);

	   } catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			LOGGER.error("Error in Offer.InsertOffer[" + offer + "]: " + e.getMessage(), e);
		    throw new WallaDBException("Error in Offer.InsertOffer[" + offer + "]:" + e.getMessage());
		}finally{
			LOGGER.info("Offer.InsertOffer - END");
		}

	   }



}
