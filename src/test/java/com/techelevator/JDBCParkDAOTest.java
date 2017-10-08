package com.techelevator;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import JDBC.JDBCParkDAO;

public class JDBCParkDAOTest {
	private static SingleConnectionDataSource dataSource;
	private JDBCParkDAO dao;
	private JdbcTemplate jdbcTemplate;
	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections 
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}
	
	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	
	
	@Before
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update("DELETE FROM site");
		jdbcTemplate.update("DELETE FROM reservation");
		jdbcTemplate.update("DELETE FROM campground");
		jdbcTemplate.update("DELETE FROM park");
		dao = new JDBCParkDAO(dataSource);//fresh new dao before every test
		
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	@Test
	public List<Park> getAllParks(){
		return null;
		
	}
	@Test
	public List<Park> getParkInfo(long choice){
		return null;
		
	}
	@Test
	public List<Park> getAllCampgroundsByParkId(){
		return null;
		
	}
}
