package com.techelevator;

import static org.junit.Assert.*;

import java.math.BigDecimal;
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


public class JDBCSiteDAOTest {
	private static SingleConnectionDataSource dataSource;
	private JDBCSiteDAO dao;
	private JdbcTemplate jdbcTemplate;
	private long siteId;
	private long campgroundId;
	private long siteNum;
	private long maxOccupy;
	private boolean available;
	private long maxRVLength;
	private boolean utilities;
	private BigDecimal dailyFee;
	private long reservationId;
	private LocalDate toDate;
	private LocalDate fromDate;
	private long campId;
	private long parkId;
	
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
		jdbcTemplate.update("DELETE FROM reservation");
		jdbcTemplate.update("DELETE FROM site");
		jdbcTemplate.update("DELETE FROM campground");
		jdbcTemplate.update("DELETE FROM park");
		dao = new JDBCSiteDAO(dataSource);//fresh new dao before every test
		parkId=jdbcTemplate.queryForObject("INSERT INTO park (name,location, establish_date,area,visitors,description) VALUES ('PARK','Neverland','2000-01-01','55555','7777777','A String Of Words') RETURNING park_id",Long.class);
		jdbcTemplate.update("INsert INTO campground (park_id,name, open_from_mm, open_to_mm, daily_fee) VALUES (?,'Crystal Lake','01','12','30') ", parkId);
		
		siteId = jdbcTemplate.queryForObject("INSERT INTO site (site_number,max_occupancy,accessible,max_rv_length, utilities) VALUES ('10','6','false','20','false') RETURNING site_id",Long.class);
		reservationId= jdbcTemplate.queryForObject("INSERT INTO reservation (site_id,name,from_date,to_date,create_date) VALUES ('1','Jeff Test Family Reservation', 2017-10-05, 2017-10-08,2017-10-01) RETURNING reservation_id", Long.class);
		campgroundId=jdbcTemplate.queryForObject("SELECT campground_id FROM site", Long.class);
		 fromDate = jdbcTemplate.queryForObject("SELECT from_date FROM reservation", LocalDate.class);
		 toDate = jdbcTemplate.queryForObject("SELECT to_date FROM reservation", LocalDate.class);
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}
	@Test
	public void testGetAllSites(){
		List<Site> site = dao.getAllSites(campgroundId,fromDate,toDate);
		assertNotNull(site);
		assertEquals(1,site.size());
		assertEquals(1,site.get(0).getCampgroundId());
		assertEquals(1,site.get(0).getSiteId());
		assertEquals(1,site.get(0).getSiteNum());
		assertEquals(10,site.get(0).getMaxOccupy());
		assertFalse(site.get(0).isAvailable());
	
	}
		
	}

