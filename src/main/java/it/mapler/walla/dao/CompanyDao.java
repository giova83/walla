package it.mapler.walla.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import it.mapler.walla.exception.WallaDBException;
import it.mapler.walla.model.Company;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyDao extends AbsDao {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);
    
	private static final String TABLE_UTENTE = "utente";	
	private static final String TABLE_AZIENDAFORNITRICE = "aziendafornitrice";
	private static final String TABLE_FORNITORE = "fornitore";
	
	
	private class CompanyRowMapper implements RowMapper<Company>{

		@Override
		public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Company company = new Company();
			company.setIdazienda(rs.getLong("idazienda"));
			company.setIdfornitore(rs.getLong("idfornitore"));
			company.setNome(rs.getString("nome"));
			company.setCellulare(rs.getString("cellulare"));
			company.setCategoria(rs.getString("categoria"));
			company.setIndirizzo(rs.getString("indirizzo"));
			company.setCitta(rs.getString("citta"));
			company.setProvincia(rs.getString("provincia"));
			company.setRegione(rs.getString("regione"));
			company.setCap(rs.getString("cap"));
			company.setLatitudine(rs.getDouble("latitudine"));
			company.setLongitudine(rs.getDouble("longitudine"));
			company.setPartitaiva(rs.getString("partitaiva"));

			return company;
		}
  }
	
	
    // Ottieni singola azienda fornitrice (READ-SELECT)
	public Company getCompany(String idazienda) throws WallaDBException{
    	LOGGER.info("UserDao.getOffer - START");
    	String sql = "SELECT "
    			+ "a.idazienda as IDAZIENDA,"
    			+ "f.idfornitore as IDFORNITORE,"
    			+ "a.nome as NOME,"
    			+ "a.cellulare as CELLULARE,"
    			+ "a.categoria as CATEGORIA,"
    			+ "a.indirizzo as INDIRIZZO"
    			+ "a.citta as CITTA,"
    			+ "a.provincia as PROVINCIA,"
    			+ "a.regione as REGIONE,"
    			+ "a.cap as CAP,"
    			+ "a.latitudine as LATITUDINE,"
    			+ "a.longitudine as LONGITUDINE,"
    			+ "a.partitaiva as PARTITAIVA "
    			+ "FROM "+TABLE_AZIENDAFORNITRICE+" as A"
    			+ "WHERE "
    			+ ""+TABLE_AZIENDAFORNITRICE+".idazienda = ?" ;

    	Company company = null; 
		try{
			company = jdbcTemplate.queryForObject(sql, new Object[] { idazienda }, new CompanyRowMapper());
		}catch(EmptyResultDataAccessException ede){
			LOGGER.info(idazienda + "not found");
		    return company; 
		}catch(Exception e){
			LOGGER.error("Error in CompanyDao.getCompany:"+e.getMessage(),e);
			throw new WallaDBException(e.getMessage());
		}finally{
			LOGGER.info("CompanyDao.getCompany - END");	
		}
		return company;
	}
	
	
    // Ottieni elenco aziende del fornitore (READ-SELECT)
	public List<Company> getSupplierCompany(Long userName) throws WallaDBException{
    	LOGGER.info("CompanyDao.getSupplierCompanies - START");
    	String sql = "SELECT "
    			+ "a.idazienda as IDAZIENDA,"
    			+ "f.idfornitore as IDFORNITORE,"
    			+ "a.nome as NOME,"
    			+ "a.cellulare as CELLULARE,"
    			+ "a.categoria as CATEGORIA,"
    			+ "a.indirizzo as INDIRIZZO,"
    			+ "a.citta as CITTA,"
    			+ "a.provincia as PROVINCIA,"
    			+ "a.regione as REGIONE,"
    			+ "a.cap as CAP,"
    			+ "a.latitudine as LATITUDINE,"
    			+ "a.longitudine as LONGITUDINE,"
    			+ "a.partitaiva as PARTITAIVA "
    			+ "FROM "+TABLE_AZIENDAFORNITRICE+" as A ," +TABLE_FORNITORE+" F ,"+TABLE_UTENTE+" U "
    			+ "WHERE a.idfornitore = f.idfornitore and u.iduser = f.iduser "
    			+ " and u.iduser = ?" ;

    	List<Company> companies = null;
	      try{		 
	    	companies = jdbcTemplate.query(sql, new Object[] { userName }, new CompanyRowMapper());
	    	}catch(Exception e){
	    		LOGGER.info("error in getSupplierCompany:"+e.getMessage(),e);
	    		throw new WallaDBException("error in getSupplierCompanies:"+e.getMessage());
	    	}finally{
	    		LOGGER.info("CompanyDao.getSupplierCompany - END");
	    	}
	      
	    	return companies;
	}
	
	
	// ritorna un elenco di Aziende filtrate per i parametri inseriti
	   public List<Company> findAllCompaniesByPar(String nome, String categoria,
			   String citta, String provincia, String regione, String cap)
					   throws WallaDBException
	   {
	    	LOGGER.info("CompanyDao.findAllCompaniesByPar - START");
	    	String sql = " SELECT "
	    			+ "a.nome as NOME,"
	    			+ "a.cellulare as CELLULARE,"
	    			+ "a.categoria as CATEGORIA,"
	    			+ "a.indirizzo as INDIRIZZO"
	    			+ "a.citta as CITTA,"
	    			+ "a.provincia as PROVINCIA,"
	    			+ "a.regione as REGIONE,"
	    			+ "a.cap as CAP,"
	    			+ "a.latitudine as LATITUDINE,"
	    			+ "a.longitudine as LONGITUDINE,"
	    			+ "a.partitaiva as PARTITAIVA "
	    			+ " FROM "+TABLE_AZIENDAFORNITRICE+" A  WHERE 1=1 AND  ";
	    	
	    	//Object[] elenco_parametri = new Object[] {};// QUI
	    	            List<Object> params = new ArrayList<Object>();
	    	            List<Integer> types = new ArrayList<Integer>(); 
	    	           if(nome != null && !nome.isEmpty())
	    			   {sql +=" and A.nome =  ? " ;
	    			     params.add(nome);
	    			     types.add(Types.VARCHAR);	    			   
	    			   } 
	    			   if(categoria != null && !categoria.isEmpty())
	    			   {sql +=" and A.categoria =  ? " ;
	    			      params.add(categoria);
	    			     types.add(Types.VARCHAR);
	    			   } 
	    			   if(citta != null && !citta.isEmpty())
	    			   {sql +=" and A.citta = ? " ;
	    			      params.add(citta);
	    			     types.add(Types.VARCHAR);
	    			   } 
	    			   if(provincia != null && !provincia.isEmpty())
	    			   {sql +=" and A.provincia =  ? " ;
	    			   params.add(provincia);
	    			     types.add(Types.VARCHAR);
	    			   
	    			   } 
	    			   
	    			   if(regione != null && !regione.isEmpty())
	    			   {sql +=" and A.regione =  ? " ;
	    			   params.add(regione);
	    			     types.add(Types.VARCHAR);
	    			   
	    			   } 
	    			   
	    			   if(cap != null && !cap.isEmpty())
	    			   {sql +=" and A.cap =  ? " ;
	    			   params.add(cap);
	    			     types.add(Types.VARCHAR);
	    			   
	    			   } 
	    			   

	    			   List<Company> companies = null;
	      try{
	    	  int [] typesP = ArrayUtils.toPrimitive(types.toArray(new Integer[types.size()]));
	    	  companies = jdbcTemplate.query(sql, params.toArray(), typesP ,new CompanyRowMapper());
	    	}catch(Exception e){
	    		LOGGER.info("error in findAllCompaniesrByPar:"+e.getMessage(),e);
	    		throw new WallaDBException("error in findAllCompaniesrByPar:"+e.getMessage());
	    	}finally{
	    		LOGGER.info("CompanyDao.findAllCompaniesByPar - END");
	    	}
	      
	    	return companies;
	    }	
	   
	    // Aggiorna singola Azienda (UPDATE)
		   public void companyUpdate(Company company) throws WallaDBException {
			   LOGGER.info("Company.UpdateCompany - START");
			   
			   try {
	           List<Object> params = new ArrayList<Object>();
	           List<Integer> types = new ArrayList<Integer>();
			   
				String  sqlUpdate = "UPDATE TABLE "+TABLE_AZIENDAFORNITRICE+ " ";
	           
			   if(company.getNome() != null && !company.getNome().isEmpty())
			   {sqlUpdate += "SET nome="+company.getNome()+"," ;
			      params.add(company.getNome());
			     types.add(Types.VARCHAR);
			   }
			   if(company.getCellulare() != null && !company.getCellulare().isEmpty())
			   {sqlUpdate += "SET cellulare="+company.getCellulare()+"," ;
			      params.add(company.getCellulare());
			     types.add(Types.VARCHAR);
			   }
			   
			   if(company.getCategoria() != null && !company.getCategoria().isEmpty())
			   {sqlUpdate += "SET categoria="+company.getCategoria()+"," ;
			      params.add(company.getCategoria());
			     types.add(Types.VARCHAR);
			   }
			   if(company.getIndirizzo() != null && !company.getIndirizzo().isEmpty())
			   {sqlUpdate += "SET indirizzo="+company.getIndirizzo()+"," ;
			      params.add(company.getIndirizzo());
			     types.add(Types.VARCHAR);
			   }
			   if(company.getRegione() != null && !company.getRegione().isEmpty())
			   {sqlUpdate += "SET regione="+company.getRegione()+"," ;
			      params.add(company.getRegione());
			     types.add(Types.VARCHAR);
			   }
			   if(company.getProvincia() != null && !company.getProvincia().isEmpty())
			   {sqlUpdate += "SET provincia="+company.getProvincia()+"," ;
			      params.add(company.getProvincia());
			     types.add(Types.VARCHAR);
			   }
			   if(company.getCitta() != null && !company.getCitta().isEmpty())
			   {sqlUpdate += "SET citta="+company.getCitta()+"," ;
			      params.add(company.getCitta());
			     types.add(Types.VARCHAR);
			   }
			   if(company.getCap() != null && !company.getCap().isEmpty())
			   {sqlUpdate += "SET cap="+company.getCap()+"," ;
			      params.add(company.getCap());
			     types.add(Types.VARCHAR);
			   }

			   if(company.getCellulare() != null && !company.getCellulare().isEmpty())
			   {sqlUpdate += "SET cellulare="+company.getCellulare()+"," ;
			      params.add(company.getCellulare());
			     types.add(Types.VARCHAR);
			   }

			   // sistemare la virgola (es. se aggiorno un solo attributo ,)
			   sqlUpdate += "WHERE "+TABLE_AZIENDAFORNITRICE+".idazienda = ?";
				//		   + "and" +TABLE_company+".username = ?";	
			   // update(String sql, Object args, int[] argTypes) 
			   
				jdbcTemplate.update(sqlUpdate,params,types);
				LOGGER.info(company +"added to the table " +TABLE_AZIENDAFORNITRICE);
				
			   }catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					LOGGER.error("Error in Company.UpdateCompany[" + company + "]: " + e.getMessage(), e);
				    throw new WallaDBException("Error in Company.UpdateCompany[" + company + "]:" + e.getMessage());
				}finally{
					LOGGER.info("Company.UpdateCompany - END");
				}
			   		 
			   	 } 
	
		   
		   public void companyAdd(Company company) throws WallaDBException {
				LOGGER.info("Company.InsertCompany - START");
	        try{	
			String  sqlAdd = "INSERT INTO "+ TABLE_AZIENDAFORNITRICE + 
								" ( idfornitore, nome, cellulare, categoria, indirizzo, citta, "
								+ "provincia, regione, cap, latitudine, longitudine, partitaiva) " +
								"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )" ;
			      
			Object[] params = { company.getIdfornitore(), company.getNome(), company.getCellulare(),
					company.getCategoria(), company.getIndirizzo(), company.getCitta(), company.getProvincia(),
					company.getRegione(), company.getCap(), company.getLatitudine(),company.getLongitudine(), company.getPartitaiva()
					};   	  
		             
			LOGGER.info(company +"added to the table " +TABLE_AZIENDAFORNITRICE);
			 
			int[] types = { Types.BIGINT, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
					Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, 
					Types.DECIMAL, Types.DECIMAL,Types.VARCHAR };	
		   
			jdbcTemplate.update(sqlAdd,params,types);
			LOGGER.info(company +"added to the table " +TABLE_AZIENDAFORNITRICE);
			
		   } catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				LOGGER.error("Error in Company.InsertCompany[" + company + "]: " + e.getMessage(), e);
			    throw new WallaDBException("Error in Company.InsertCompany[" + company + "]:" + e.getMessage());
			}finally{
				LOGGER.info("Company.InsertCompany - END");
			}
	        
		   } 
		   
		   
		   public void companyDelete(Long idCompany) throws WallaDBException {
				LOGGER.info("Offer.DeleteOffer - START");
			try{
			String  sqlDelete = "DELETE FROM "+TABLE_AZIENDAFORNITRICE+" WHERE idazienda = ?";
			Object[] params = {idCompany};
			int[] types = { Types.VARCHAR};

			jdbcTemplate.update(sqlDelete,params,types);
			LOGGER.info(idCompany +"deleted from the table " +TABLE_AZIENDAFORNITRICE);
		      } catch (Exception e) {
						LOGGER.error(e.getMessage(), e);
						LOGGER.error("Error in Company.DeleteCompany[" + idCompany + "]: " + e.getMessage(), e);
					    throw new WallaDBException("Error in Company.DeleteCompany[" + idCompany + "]:" + e.getMessage());
					}finally{
						LOGGER.info("Company.DeleteCompany - END");
					}
			   	 }
		   
		   
	
}
