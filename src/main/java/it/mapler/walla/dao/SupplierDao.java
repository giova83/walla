package it.mapler.walla.dao;

import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository
public class SupplierDao extends AbsDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SupplierDao.class);
	    
	private static final String TABLE_UTENTE = "utente";	
	private static final String TABLE_FORNITORE = "fornitore";


	private class SupplierRowMapper implements RowMapper<Supplier>
	{

		@Override
		public Supplier mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Supplier supplier = new Supplier();
			supplier.setIdfornitore(rs.getLong("idfornitore"));
			supplier.setIduser(rs.getLong("iduser"));
			supplier.setNome(rs.getString("nome"));
			supplier.setCognome(rs.getString("cognome"));
			supplier.setCellulare(rs.getString("cellulare"));
			supplier.setCategoria(rs.getString("categoria"));
			supplier.setIndirizzo("indirizzo");
			supplier.setCitta(rs.getString("citta"));
			supplier.setProvincia(rs.getString("provincia"));
			supplier.setCap(rs.getString("cap"));
			supplier.setLongitudine(rs.getDouble("latitudine"));
			supplier.setLatitudine(rs.getDouble("longitudine"));

			return supplier;
		}
		
	  }	
		   // Ottieni singolo Fornitore (READ-SELECT)
		public Supplier getSupplier(Long idfornitore) throws WallaDBException
		{
	    	LOGGER.info("UserDao.getProduct - START");
	    	String sql = "SELECT "
	    			+ "f.nome as NOME,"
	    			+ "f.idfornitore as IDFORNITORE,"
	    			+ "f.iduser as IDUSER,"
	    			+ "f.cognome as COGNOME,"
	    			+ "f.cellulare as CELLULARE,"
	    			+ "f.categoria as CATEGORIA,"
	    			+ "f.indirizzo as INDIRIZZO,"
	    			+ "f.citta as CITTA,"
	    			+ "f.provincia as PROVINCIA,"
	    			+ "f.latitudine as LATITUDINE,"
	    			+ "f.longitudine as LONGITUDINE,"
	    			+ "f.cap as CAP"
	    			+ " FROM "+TABLE_FORNITORE+" as F"
	    			+ " WHERE "
	    			+ " F.iduser = ?" ;

	    	Supplier supplier = null; 
			try{
				supplier = jdbcTemplate.queryForObject(sql, new Object[] { idfornitore }, new SupplierRowMapper());
			}catch(EmptyResultDataAccessException ede){
				LOGGER.info(idfornitore + "not found");
			    return supplier; 
			}catch(Exception e){
				LOGGER.error("Error in SupplierDao.getSupplier:"+e.getMessage(),e);
				throw new WallaDBException(e.getMessage());
			}finally{
				LOGGER.info("SupplierDao.getSupplier - END");	
			}
			return supplier;
		}
		
		
	    // Ottieni elenco Fornitori per città (READ-SELECT)
		public List<Supplier> getSuppliersByCity(String city) throws WallaDBException
		{
	    	LOGGER.info("SupplierDao.getSupplierByCity - START");
	    	String sql = "SELECT "
	    			+ "f.nome as NOME,"
	    			+ "f.cognome as COGNOME,"
	    			+ "f.cellulare as CELLULARE,"
	    			+ "f.categoria as CATEGORIA,"
	    			+ "f.indirizzo as INDIRIZZO,"
	    			+ "f.citta as CITTA,"
	    			+ "f.provincia as PROVINCIA,"
	    			+ "f.latitudine as LATITUDINE,"
	    			+ "f.longitudine as LONGITUDINE,"
	    			+ "f.cap as CAP"
	    			+ "FROM "+TABLE_FORNITORE+" as F "
	    			+ "WHERE"+TABLE_FORNITORE+".citta = ?" ;

	    	List<Supplier> suppliers = null;
		      try{		 
		    	  suppliers = jdbcTemplate.query(sql, new Object[] { city }, new SupplierRowMapper());
		    	}catch(Exception e){
		    		LOGGER.info("error in getSupplierByCity :"+e.getMessage(),e);
		    		throw new WallaDBException("error in getSuppliersByCity:"+e.getMessage());
		    	}finally{
		    		LOGGER.info("SupplierDao.getSuppliersByCity  - END");
		    	}
		      
		    	return suppliers;
		}
		
		
		// ritorna un elenco di Fornitori filtrati per i parametri inseriti
		   public List<Supplier> findAllSuppliersByPar(String nome, String cognome,
				   String categoria, String cellulare,String indirizzo, String citta,
				   String provincia, String cap,double latitudine, double longitudine )
						   throws WallaDBException
		   {
		    	LOGGER.info("SupplierDao.findAllSuppliersByPar - START");
		    	String sql = " SELECT "
		    			+ "f.nome as NOME,"
		    			+ "f.cognome as COGNOME,"
		    			+ "f.celulare as CELLULARE,"
		    			+ "f.categoria as CATEGORIA,"
		    			+ "f.indirizzo as INDIRIZZO,"
		    			+ "f.citta as CITTA,"
		    			+ "f.provincia as PROVINCIA,"
		    			+ "f.latitudine as LATITUDINE,"
		    			+ "f.longitudine as LONGITUDINE,"
		    			+ "f.cap as CAP"
		    			+ " FROM "+TABLE_FORNITORE+" F  WHERE 1=1 AND  ";
		    	
		    	//Object[] elenco_parametri = new Object[] {};// QUI
		    	            List<Object> params = new ArrayList<Object>();
		    	            List<Integer> types = new ArrayList<Integer>(); 
		    	           
		    	            if(nome != null && !nome.isEmpty())
		    			   {sql +=" and F.titolo =  ? " ;
		    			     params.add(nome);
		    			     types.add(Types.VARCHAR);	    			   
		    			   } 
		    			   if(cognome != null && !cognome.isEmpty())
		    			   {sql +=" and F.cognome =  ? " ;
		    			      params.add(cognome);
		    			     types.add(Types.VARCHAR);
		    			   } 
		    			   if(categoria != null && !categoria.isEmpty())
		    			   {sql +=" and P.categoria = ? " ;
		    			      params.add(categoria);
		    			     types.add(Types.VARCHAR);
		    			   } 
		    	            if(cellulare != null && !cellulare.isEmpty())
		    			   {sql +=" and F.cellulare =  ? " ;
		    			     params.add(cellulare);
		    			     types.add(Types.VARCHAR);	    			   
		    			   } 
		    			   if(indirizzo != null && !indirizzo.isEmpty())
		    			   {sql +=" and F.indirizzo =  ? " ;
		    			      params.add(indirizzo);
		    			     types.add(Types.VARCHAR);
		    			   } 
		    			   if(citta != null && !citta.isEmpty())
		    			   {sql +=" and F.citta = ? " ;
		    			      params.add(citta);
		    			     types.add(Types.VARCHAR);
		    			   } 
		    			   if(provincia != null && !provincia.isEmpty())
		    			   {sql +=" and F.provincia =  ? " ;
		    			      params.add(provincia);
		    			     types.add(Types.VARCHAR);
		    			   } 
		    			   if(cap != null && !cap.isEmpty())
		    			   {sql +=" and F.cap = ? " ;
		    			      params.add(cap);
		    			     types.add(Types.VARCHAR);
		    			   } 		    			   
		    			   if(latitudine !=0.0)
		    			   {sql +=" and F.latitudine =  ? " ;
		    			   params.add(latitudine);
		    			     types.add(Types.DECIMAL);
		    			   
		    			   } 		    			   
		    			   if(longitudine !=0.0)
		    			   {sql +=" and F.longitudine =  ? " ;
		    			   params.add(longitudine);
		    			     types.add(Types.DECIMAL);
		    			   
		    			   } 
		    			   
		    			   List<Supplier> suppliers = null;
		      try{
		    	  int [] typesP = ArrayUtils.toPrimitive(types.toArray(new Integer[types.size()]));
		    	  suppliers = jdbcTemplate.query(sql, params.toArray(), typesP ,new SupplierRowMapper());
		    	}catch(Exception e){
		    		LOGGER.info("error in findAllSuppliersByPar:"+e.getMessage(),e);
		    		throw new WallaDBException("error in findAllSuppliersByPar:"+e.getMessage());
		    	}finally{
		    		LOGGER.info("SupplierDAo.findAllSuppliersByPar - END");
		    	}
		      
		    	return suppliers;
		    }
		    // Aggiorna singolo Fornitore (UPDATE)
			   public void supplierUpdate(Supplier supplier) throws WallaDBException 
			   {
				   LOGGER.info("Supplier.UpdateSupplier - START");
				   
				   try {
		           List<Object> params = new ArrayList<Object>();
		           List<Integer> types = new ArrayList<Integer>();
				   
					String  sqlUpdate = "UPDATE TABLE "+TABLE_FORNITORE+ " ";
		           
				   if(supplier.getNome() != null && !supplier.getNome().isEmpty())
				   {sqlUpdate += "SET nome="+supplier.getNome()+"," ;
				      params.add(supplier.getNome());
				     types.add(Types.VARCHAR);
				   }
				   if(supplier.getCognome() != null && !supplier.getCognome().isEmpty())
				   {sqlUpdate += "SET cognome="+supplier.getCognome() +"," ;
				      params.add(supplier.getCognome() );
				     types.add(Types.VARCHAR);
				   }
				   
				   if(supplier.getCategoria() != null && !supplier.getCategoria().isEmpty())
				   {sqlUpdate += "SET categoria="+supplier.getCategoria()+"," ;
				      params.add(supplier.getCategoria());
				     types.add(Types.VARCHAR);
				   }
				   if(supplier.getCellulare() != null && !supplier.getCellulare().isEmpty())
				   {sqlUpdate += "SET cellulare="+supplier.getCellulare()+"," ;
				      params.add(supplier.getCellulare());
				     types.add(Types.VARCHAR);
				   }
				   if(supplier.getIndirizzo() != null && !supplier.getIndirizzo().isEmpty())
				   {sqlUpdate += "SET indirizzo="+supplier.getIndirizzo() +"," ;
				      params.add(supplier.getIndirizzo() );
				     types.add(Types.VARCHAR);
				   }
				   
				   if(supplier.getCitta() != null && !supplier.getCitta().isEmpty())
				   {sqlUpdate += "SET citta="+supplier.getCitta()+"," ;
				      params.add(supplier.getCitta());
				     types.add(Types.VARCHAR);
				   }
				   if(supplier.getProvincia() != null && !supplier.getProvincia().isEmpty())
				   {sqlUpdate += "SET provincia="+supplier.getProvincia()+"," ;
				      params.add(supplier.getProvincia());
				     types.add(Types.VARCHAR);
				   }
				   if(supplier.getCap() != null && !supplier.getCap().isEmpty())
				   {sqlUpdate += "SET cap="+supplier.getCap()+"," ;
				      params.add(supplier.getCap());
				     types.add(Types.VARCHAR);
				   }

				   if(supplier.getLatitudine() != 0.0 )
				   {sqlUpdate += "SET latitudine="+supplier.getLatitudine()+"," ;
				      params.add(supplier.getLatitudine());
				     types.add(Types.VARCHAR);
				   }
				   if(supplier.getLongitudine() != 0.0)
				   {sqlUpdate += "SET quantita="+supplier.getLongitudine()+"," ;
				      params.add(supplier.getLongitudine());
				     types.add(Types.VARCHAR);
				   }

				   // sistemare la virgola (es. se aggiorno un solo attributo ,)
				   sqlUpdate += "WHERE "+TABLE_FORNITORE+".idfornitore = ?";

				   
					jdbcTemplate.update(sqlUpdate,params,types);
					LOGGER.info(supplier +"added to the table " +TABLE_FORNITORE);
					
				   }catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
						LOGGER.error("Error in supplier.UpdateProduct[" + supplier + "]: " + e.getMessage(), e);
					    throw new WallaDBException("Error in Supplier.UpdateSupplier[" + supplier + "]:" + e.getMessage());
					}finally{
						LOGGER.info("Supplier.UpdateSupplier - END");
					}
				   		 
				   	 }
			   
			   
			   // Aggiungi nuovo Fornitore
			   public void productAdd(Supplier supplier) throws WallaDBException {
					LOGGER.info("Supplier.InsertSupplier - START");
		        try{	
				String  sqlAdd = "INSERT INTO "+ TABLE_FORNITORE + 
									" (idfornitore, iduser, nome, cognome, cellulare, categoria, indirizzo, "
									+ "citta, provincia, cap,latitudine,longitudine) " +
									"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)" ;
				      
				Object[] params = {supplier.getIdfornitore(),supplier.getIduser(), supplier.getNome(), 
						supplier.getCognome(),supplier.getCellulare(),supplier.getCategoria(),supplier.getIndirizzo(),
						supplier.getCitta(),supplier.getProvincia(), supplier.getCap(), supplier.getLatitudine(),
						supplier.getLongitudine()};   	  
			             
				LOGGER.info(supplier +"added to the table " +TABLE_FORNITORE);
				 
				int[] types = { Types.BIGINT, Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,Types.VARCHAR,
						Types.VARCHAR, Types.VARCHAR,Types.VARCHAR, Types.VARCHAR, Types.DECIMAL, Types.DECIMAL };	
			   
				jdbcTemplate.update(sqlAdd,params,types);
				LOGGER.info(supplier +"added to the table " +TABLE_FORNITORE);
				
			   } catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					LOGGER.error("Error in supplier.InsertSupplier[" + supplier + "]: " + e.getMessage(), e);
				    throw new WallaDBException("Error in supplier.InsertSupplier[" + supplier + "]:" + e.getMessage());
				}finally{
					LOGGER.info("supplier.InsertSupplier - END");
				}
		        
			   } 

}
