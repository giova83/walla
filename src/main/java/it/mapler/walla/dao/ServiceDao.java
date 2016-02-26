package it.mapler.walla.dao;

import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

public class ServiceDao extends AbsDao {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceDao.class);
	
	private static final String TABLE_UTENTE = "utente";	
	private static final String TABLE_AZIENDAFORNITRICE = "aziendafornitrice";
	private static final String TABLE_FORNITORE = "fornitore";
	private static final String TABLE_SERVIZIO = "servizi";
	
	
	private class ServiceRowMapper implements RowMapper<Service>{

		@Override
		public Service mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Service service = new Service();
			service.setIdservizio(rs.getLong("idservizio"));
			service.setIdazienda(rs.getLong("idazienda"));
			service.setDescrizione(rs.getString("descrizione"));
			service.setCategoria(rs.getString("categoria"));
			service.setTipologia(rs.getString("tipologia"));
			service.setTitolo(rs.getString("titolo"));
			Calendar cal = new GregorianCalendar();
			cal.setTimeInMillis(rs.getTimestamp("datapubblicazione").getTime());
			service.setDatapubblicazione(cal);
			service.setCosto(rs.getDouble("costo"));
			service.setQuantita(rs.getDouble("quantita"));
			service.setServizio_attivo(rs.getBoolean("servizio_attivo"));

			return service;
		}
  }	
	
	
	
    // Ottieni singolo servizio (READ-SELECT)
	public Service getService(String idservizio) throws WallaDBException
	{
    	LOGGER.info("ServiceDao.getService - START");
    	String sql = "SELECT "
    			+ "s.titolo as TITOLO,"
    			+ "s.tipologia as TIPOLOGIA,"
    			+ "s.categoria as CATEGORIA,"
    			+ "s.descrizione as DESCRIZIONE,"
    			+ "s.datapubblicazione as DATAPUBBLICAZIONE,"
    			+ "s.costo as COSTO,"
    			+ "s.quantita as QUANTITA,"
    			+ "s.prodotto_attivo as PRODOTTO_ATTIVO"
    			+ "FROM "+TABLE_SERVIZIO+" as S"
    			+ "WHERE "
    			+ ""+TABLE_SERVIZIO+".idservizio = ?" ;

    	Service service = null; 
		try{
			service = jdbcTemplate.queryForObject(sql, new Object[] { idservizio }, new ServiceRowMapper());
		}catch(EmptyResultDataAccessException ede){
			LOGGER.info(idservizio + "not found");
		    return service; 
		}catch(Exception e){
			LOGGER.error("Error in ServiceDao.getService:"+e.getMessage(),e);
			throw new WallaDBException(e.getMessage());
		}finally{
			LOGGER.info("ServiceDao.getService - END");	
		}
		return service;
	}
	
	
	  // Ottieni elenco servizi del fornitore (READ-SELECT)
		public List<Service> getSupplierServices(String userName) throws WallaDBException
		{
	    	LOGGER.info("ServiceDao.getSupplierServices - START");
	    	String sql = "SELECT "
	    			+ "s.titolo as TITOLO,"
	    			+ "s.tipologia as TIPOLOGIA,"
	    			+ "s.categoria as CATEGORIA,"
	    			+ "s.descrizione as DESCRIZIONE,"
	    			+ "s.datapubblicazione as DATAPUBBLICAZIONE,"
	    			+ "s.costo as COSTO,"
	    			+ "s.quantita as QUANTITA"
	    			+ "FROM "+TABLE_AZIENDAFORNITRICE+" as A ," +TABLE_FORNITORE+" F ,"
	    					+ ""+TABLE_UTENTE+" U ,"+TABLE_SERVIZIO+" as S"
	    			+ "WHERE s.idazienda = a.idazienda and a.idfornitore = f.idfornitore"
	    			+ " and u.iduser = f.iduser "
	    			+ " and "+TABLE_UTENTE+".username = ?" ;

	    	List<Service> services = null;
		      try{		 
		    	  services = jdbcTemplate.query(sql, new Object[] { userName }, new ServiceRowMapper());
		    	}catch(Exception e){
		    		LOGGER.info("error in getSupplierServices :"+e.getMessage(),e);
		    		throw new WallaDBException("error in getSupplierServices:"+e.getMessage());
		    	}finally{
		    		LOGGER.info("ServiceDao.getSupplierServices  - END");
		    	}
		      
		    	return services;
		}
		
		
		
		// ritorna un elenco di Servizi filtrati per i parametri inseriti
		   public List<Service> findAllServicesByPar(String titolo, String tipologia,
				   String categoria, String descrizione, Calendar datapubblicazione, double costo)
						   throws WallaDBException
		   {
		    	LOGGER.info("ServiceDao.findAllServicesByPar - START");
		    	String sql = " SELECT "
		    			+ "s.titolo as TITOLO,"
		    			+ "s.tipologia as TIPOLOGIA,"
		    			+ "s.categoria as CATEGORIA,"
		    			+ "s.descrizione as DESCRIZIONE,"
		    			+ "s.datapubblicazione as DATAPUBBLICAZIONE,"
		    			+ "s.costo as COSTO,"
		    			+ "s.quantita as QUANTITA"
		    			+ " FROM "+TABLE_SERVIZIO+" S  WHERE 1=1 AND  ";
		    	
		    	//Object[] elenco_parametri = new Object[] {};// QUI
		    	            List<Object> params = new ArrayList<Object>();
		    	            List<Integer> types = new ArrayList<Integer>(); 
		    	           
		    	            if(titolo != null && !titolo.isEmpty())
		    			   {sql +=" and P.titolo =  ? " ;
		    			     params.add(titolo);
		    			     types.add(Types.VARCHAR);	    			   
		    			   } 
		    			   if(tipologia != null && !tipologia.isEmpty())
		    			   {sql +=" and P.tipologia =  ? " ;
		    			      params.add(tipologia);
		    			     types.add(Types.VARCHAR);
		    			   } 
		    			   if(categoria != null && !categoria.isEmpty())
		    			   {sql +=" and P.categoria = ? " ;
		    			      params.add(categoria);
		    			     types.add(Types.VARCHAR);
		    			   } 
		    			   if(datapubblicazione != null)
		    			   {sql +=" and P.datapubblicazione =  ? " ;
		    			   params.add(datapubblicazione);
		    			     types.add(Types.TIMESTAMP);
		    			   
		    			   } 
		    			   
		    			   if(costo !=0.0)
		    			   {sql +=" and P.costo =  ? " ;
		    			   params.add(costo);
		    			     types.add(Types.DECIMAL);
		    			   
		    			   } 
		    			   

		    			   List<Service> services = null;
		      try{
		    	  int [] typesP = ArrayUtils.toPrimitive(types.toArray(new Integer[types.size()]));
		    	  services = jdbcTemplate.query(sql, params.toArray(), typesP ,new ServiceRowMapper());
		    	}catch(Exception e){
		    		LOGGER.info("error in findAllServicesByPar:"+e.getMessage(),e);
		    		throw new WallaDBException("error in findAllServicesByPar:"+e.getMessage());
		    	}finally{
		    		LOGGER.info("ServiceDAo.findAllServicesByPar - END");
		    	}
		      
		    	return services;
		    }
		
		
		   
		// Aggiorna singolo Servizio (UPDATE)
		   public void ServiceUpdate(Service service) throws WallaDBException 
		   {
			   LOGGER.info("Service.UpdateService - START");
			   
			   try {
	           List<Object> params = new ArrayList<Object>();
	           List<Integer> types = new ArrayList<Integer>();
			   
				String  sqlUpdate = "UPDATE TABLE "+TABLE_SERVIZIO+ " ";
	           
			   if(service.getTitolo() != null && !service.getTitolo().isEmpty())
			   {sqlUpdate += "SET titolo="+service.getTitolo()+"," ;
			      params.add(service.getTitolo());
			     types.add(Types.VARCHAR);
			   }
			   if(service.getTipologia() != null && !service.getTipologia().isEmpty())
			   {sqlUpdate += "SET tipologia="+service.getTipologia()+"," ;
			      params.add(service.getTipologia());
			     types.add(Types.VARCHAR);
			   }
			   
			   if(service.getCategoria() != null && !service.getCategoria().isEmpty())
			   {sqlUpdate += "SET categoria="+service.getCategoria()+"," ;
			      params.add(service.getCategoria());
			     types.add(Types.VARCHAR);
			   }
			   if(service.getDescrizione() != null && !service.getDescrizione().isEmpty())
			   {sqlUpdate += "SET descrizione="+service.getDescrizione()+"," ;
			      params.add(service.getDescrizione());
			     types.add(Types.VARCHAR);
			   }
			   if(service.getDatapubblicazione() != null)
			   {sqlUpdate += "SET datapubblicazione="+service.getDatapubblicazione()+"," ;
			      params.add(service.getDatapubblicazione());
			     types.add(Types.VARCHAR);
			   }
			   if(service.getCosto() != 0.0 )
			   {sqlUpdate += "SET costo="+service.getCosto()+"," ;
			      params.add(service.getCosto());
			     types.add(Types.VARCHAR);
			   }
			   if(service.getQuantita() != 0.0)
			   {sqlUpdate += "SET quantita="+service.getCosto()+"," ;
			      params.add(service.getCosto());
			     types.add(Types.VARCHAR);
			   }
			   if(service.isServizio_attivo())
			   {sqlUpdate += "SET cap="+service.isServizio_attivo()+"," ;
			      params.add(service.isServizio_attivo());
			     types.add(Types.VARCHAR);
			   }

			   // sistemare la virgola (es. se aggiorno un solo attributo ,)
			   sqlUpdate += "WHERE "+TABLE_SERVIZIO+".idprodotto = ?";

			   
				jdbcTemplate.update(sqlUpdate,params,types);
				LOGGER.info(service +"added to the table " +TABLE_SERVIZIO);
				
			   }catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					LOGGER.error("Error in service.UpdateService[" + service + "]: " + e.getMessage(), e);
				    throw new WallaDBException("Error in service.UpdateService[" + service + "]:" + e.getMessage());
				}finally{
					LOGGER.info("service.UpdateService - END");
				}
			   		 
			   	 } 

		   // Aggiungi un nuovo Servizio
		   public void serviceAdd(Service service) throws WallaDBException {
				LOGGER.info("Service.InsertService - START");
	        try{	
			String  sqlAdd = "INSERT INTO "+ TABLE_SERVIZIO + 
								" (idservizio, idazienda, titolo, tipologia, categoria, descrizione, costo, "
								+ "quantita, datapubblicazione, prodotto_attivo) " +
								"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
			      
			Object[] params = {service.getIdservizio(),service.getIdazienda(), service.getTitolo(), service.getTipologia(),
					service.getCategoria(),service.getDescrizione(), service.getCosto(), service.getQuantita(),
					service.getDatapubblicazione(), service.isServizio_attivo()};   	  
		             
			LOGGER.info(service +"added to the table " +TABLE_SERVIZIO);
			 
			int[] types = { Types.BIGINT, Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
					Types.VARCHAR, Types.TIMESTAMP, Types.DECIMAL, Types.DECIMAL, Types.BOOLEAN };	
		   
			jdbcTemplate.update(sqlAdd,params,types);
			LOGGER.info(service +"added to the table " +TABLE_SERVIZIO);
			
		   } catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				LOGGER.error("Error in Product.InsertProduct[" + service + "]: " + e.getMessage(), e);
			    throw new WallaDBException("Error in Product.InsertProduct[" + service + "]:" + e.getMessage());
			}finally{
				LOGGER.info("product.InsertProduct - END");
			}
	        
		   }    

}
