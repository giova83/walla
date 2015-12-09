package it.mapler.walla.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class AbsDao {
	 
	@Autowired
    protected JdbcTemplate jdbcTemplate;

}
