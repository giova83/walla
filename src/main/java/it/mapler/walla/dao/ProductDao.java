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
import it.mapler.walla.model.Product;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


public class ProductDao extends AbsDao {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductDao.class);
    
	private static final String TABLE_UTENTE = "utente";	
	private static final String TABLE_AZIENDAFORNITRICE = "aziendafornitrice";
	private static final String TABLE_FORNITORE = "fornitore";
	private static final String TABLE_PRODOTTO = "prodotti";
	
	
	private class ProductRowMapper implements RowMapper<Product>
	{

		@Override
		public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Product product = new Product();
			product.setIdprodotto(rs.getLong("idprodotto"));
			product.setIdazienda(rs.getLong("idazienda"));
			product.setTitolo(rs.getString("titolo"));
			product.setTipologia(rs.getString("tipologia"));
			product.setCategoria(rs.getString("categoria"));
			product.setDescrizione(rs.getString("descrizione"));
			Calendar cal = new GregorianCalendar();
			cal.setTimeInMillis(rs.getTimestamp("datapubblicazione").getTime());
			product.setDatapubblicazione(cal);
			product.setCosto(rs.getDouble("costo"));
			product.setQuantita(rs.getDouble("quantita"));
			product.setProdotto_attivo(rs.getBoolean("prodotto_attivo"));

			return product;
		}
  }	
	
	
	
    // Ottieni singolo prodotto (READ-SELECT)
	public Product getProduct(String idprodotto) throws WallaDBException
	{
    	LOGGER.info("UserDao.getProduct - START");
    	String sql = "SELECT "
    			+ "p.titolo as TITOLO,"
    			+ "p.tipologia as TIPOLOGIA,"
    			+ "p.categoria as CATEGORIA,"
    			+ "p.descrizione as DESCRIZIONE,"
    			+ "p.datapubblicazione as DATAPUBBLICAZIONE,"
    			+ "p.costo as COSTO,"
    			+ "p.quantita as QUANTITA,"
    			+ "p.prodotto_attivo as PRODOTTO_ATTIVO"
    			+ "FROM "+TABLE_PRODOTTO+" as P"
    			+ "WHERE "
    			+ ""+TABLE_PRODOTTO+".idprodotto = ?" ;

    	Product product = null; 
		try{
			product = jdbcTemplate.queryForObject(sql, new Object[] { idprodotto }, new ProductRowMapper());
		}catch(EmptyResultDataAccessException ede){
			LOGGER.info(idprodotto + "not found");
		    return product; 
		}catch(Exception e){
			LOGGER.error("Error in ProductDao.getProduct:"+e.getMessage(),e);
			throw new WallaDBException(e.getMessage());
		}finally{
			LOGGER.info("ProductDao.getProduct - END");	
		}
		return product;
	}
	
	

    // Ottieni elenco prodotti del fornitore (READ-SELECT)
	public List<Product> getSupplierProducts(String userName) throws WallaDBException
	{
    	LOGGER.info("ProductDao.getSupplierProducts - START");
    	String sql = "SELECT "
    			+ "p.titolo as TITOLO,"
    			+ "p.tipologia as TIPOLOGIA,"
    			+ "p.categoria as CATEGORIA,"
    			+ "p.descrizione as DESCRIZIONE,"
    			+ "p.datapubblicazione as DATAPUBBLICAZIONE,"
    			+ "p.costo as COSTO,"
    			+ "p.quantita as QUANTITA"
    			+ "FROM "+TABLE_AZIENDAFORNITRICE+" as A ," +TABLE_FORNITORE+" F ,"
    					+ ""+TABLE_UTENTE+" U ,"+TABLE_PRODOTTO+" as P"
    			+ "WHERE p.idazienda = a.idazienda and a.idfornitore = f.idfornitore"
    			+ " and u.iduser = f.iduser "
    			+ " and "+TABLE_UTENTE+".username = ?" ;

    	List<Product> products = null;
	      try{		 
	    	  products = jdbcTemplate.query(sql, new Object[] { userName }, new ProductRowMapper());
	    	}catch(Exception e){
	    		LOGGER.info("error in getSupplierProducts :"+e.getMessage(),e);
	    		throw new WallaDBException("error in getSupplierproduct:"+e.getMessage());
	    	}finally{
	    		LOGGER.info("ProductDao.getSupplierProducts  - END");
	    	}
	      
	    	return products;
	}
	
	
	// ritorna un elenco di Prodotti filtrati per i parametri inseriti
	   public List<Product> findAllProductsByPar(String titolo, String tipologia,
			   String categoria, String descrizione, Calendar datapubblicazione, double costo)
					   throws WallaDBException
	   {
	    	LOGGER.info("ProductDao.findAllProductsByPar - START");
	    	String sql = " SELECT "
	    			+ "p.titolo as TITOLO,"
	    			+ "p.tipologia as TIPOLOGIA,"
	    			+ "p.categoria as CATEGORIA,"
	    			+ "p.descrizione as DESCRIZIONE,"
	    			+ "p.datapubblicazione as DATAPUBBLICAZIONE,"
	    			+ "p.costo as COSTO,"
	    			+ "p.quantita as QUANTITA"
	    			+ " FROM "+TABLE_PRODOTTO+" P  WHERE 1=1 AND  ";
	    	
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
	    			   

	    			   List<Product> products = null;
	      try{
	    	  int [] typesP = ArrayUtils.toPrimitive(types.toArray(new Integer[types.size()]));
	    	  products = jdbcTemplate.query(sql, params.toArray(), typesP ,new ProductRowMapper());
	    	}catch(Exception e){
	    		LOGGER.info("error in findAllProductsByPar:"+e.getMessage(),e);
	    		throw new WallaDBException("error in findAllProductsByPar:"+e.getMessage());
	    	}finally{
	    		LOGGER.info("ProductDAo.findAllProductsByPar - END");
	    	}
	      
	    	return products;
	    }
	
	

	    // aggiorna singolo prodotto (UPDATE)
		   public void productUpdate(Product product) throws WallaDBException 
		   {
			   LOGGER.info("Product.UpdateProduct - START");
			   
			   try {
	           List<Object> params = new ArrayList<Object>();
	           List<Integer> types = new ArrayList<Integer>();
			   
				String  sqlUpdate = "UPDATE TABLE "+TABLE_PRODOTTO+ " ";
	           
			   if(product.getTitolo() != null && !product.getTitolo().isEmpty())
			   {sqlUpdate += "SET titolo="+product.getTitolo()+"," ;
			      params.add(product.getTitolo());
			     types.add(Types.VARCHAR);
			   }
			   if(product.getTipologia() != null && !product.getTipologia().isEmpty())
			   {sqlUpdate += "SET tipologia="+product.getTipologia()+"," ;
			      params.add(product.getTipologia());
			     types.add(Types.VARCHAR);
			   }
			   
			   if(product.getCategoria() != null && !product.getCategoria().isEmpty())
			   {sqlUpdate += "SET categoria="+product.getCategoria()+"," ;
			      params.add(product.getCategoria());
			     types.add(Types.VARCHAR);
			   }
			   if(product.getDescrizione() != null && !product.getDescrizione().isEmpty())
			   {sqlUpdate += "SET descrizione="+product.getDescrizione()+"," ;
			      params.add(product.getDescrizione());
			     types.add(Types.VARCHAR);
			   }
			   if(product.getDatapubblicazione() != null)
			   {sqlUpdate += "SET datapubblicazione="+product.getDatapubblicazione()+"," ;
			      params.add(product.getDatapubblicazione());
			     types.add(Types.VARCHAR);
			   }
			   if(product.getCosto() != 0.0 )
			   {sqlUpdate += "SET costo="+product.getCosto()+"," ;
			      params.add(product.getCosto());
			     types.add(Types.VARCHAR);
			   }
			   if(product.getQuantita() != 0.0)
			   {sqlUpdate += "SET quantita="+product.getCosto()+"," ;
			      params.add(product.getCosto());
			     types.add(Types.VARCHAR);
			   }
			   if(product.isProdotto_attivo())
			   {sqlUpdate += "SET cap="+product.isProdotto_attivo()+"," ;
			      params.add(product.isProdotto_attivo());
			     types.add(Types.VARCHAR);
			   }

			   // sistemare la virgola (es. se aggiorno un solo attributo ,)
			   sqlUpdate += "WHERE "+TABLE_PRODOTTO+".idprodotto = ?";

			   
				jdbcTemplate.update(sqlUpdate,params,types);
				LOGGER.info(product +"added to the table " +TABLE_PRODOTTO);
				
			   }catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					LOGGER.error("Error in product.UpdateProduct[" + product + "]: " + e.getMessage(), e);
				    throw new WallaDBException("Error in Product.Updateproduct[" + product + "]:" + e.getMessage());
				}finally{
					LOGGER.info("Product.UpdateProduct - END");
				}
			   		 
			   	 } 
	   // Aggiungi nuovo Prodotto
		   public void productAdd(Product product) throws WallaDBException {
				LOGGER.info("Product.InsertProduct - START");
	        try{	
			String  sqlAdd = "INSERT INTO "+ TABLE_PRODOTTO + 
								" (idprodotto, idazienda, titolo, tipologia, categoria, descrizione, costo, "
								+ "quantita, datapubblicazione, prodotto_attivo) " +
								"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
			      
			Object[] params = {product.getIdprodotto(),product.getIdazienda(), product.getTitolo(), product.getTipologia(),
					product.getCategoria(),product.getDescrizione(), product.getCosto(), product.getQuantita(),
					product.getDatapubblicazione(), product.isProdotto_attivo()};   	  
		             
			LOGGER.info(product +"added to the table " +TABLE_PRODOTTO);
			 
			int[] types = { Types.BIGINT, Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
					Types.VARCHAR, Types.TIMESTAMP, Types.DECIMAL, Types.DECIMAL, Types.BOOLEAN };	
		   
			jdbcTemplate.update(sqlAdd,params,types);
			LOGGER.info(product +"added to the table " +TABLE_PRODOTTO);
			
		   } catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				LOGGER.error("Error in Product.InsertProduct[" + product + "]: " + e.getMessage(), e);
			    throw new WallaDBException("Error in Product.InsertProduct[" + product + "]:" + e.getMessage());
			}finally{
				LOGGER.info("product.InsertProduct - END");
			}
	        
		   }    
	
}