package com.techelevator;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import JDBC.JDBCSiteDAO;

public class JDBCReservationDAOTest {
	private static SingleConnectionDataSource dataSource;
	private JDBCSiteDAO dao;
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
		dao = new JDBCSiteDAO(dataSource);//fresh new dao before every test
		
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	@Test
	public List<Reservation> getAllReservations(long campId, LocalDate fromDate,LocalDate toDate){
		return null;
	}
	@Test
	public void setReservation(long siteId, LocalDate fromDate,LocalDate toDate, String name){
		
	}
	@Test
	public List<Reservation> getConfirmId(String name,LocalDate fromDate){
		return null;
	}
}
