package JDBC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Reservation;
import com.techelevator.Site;

import DAO.DAOSite;

public class JDBCSiteDAO implements DAOSite{
	
	private JdbcTemplate jdbcTemplate;

	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getAllSites(long campId,LocalDate fromDate,LocalDate toDate) {
		List<Site> siteInfo = new ArrayList<>();
		String site = ("SELECT s.site_id, s.campground_id, s.site_number, s.max_occupancy, s.accessible, s.max_rv_length, s.utilities, c.daily_fee "
				+ "FROM site s JOIN reservation r ON r.site_id = s.site_id JOIN campground c ON c.campground_id = s.campground_id "
				+ "WHERE s.site_id NOT IN (SELECT r.site_id FROM reservation "
				+"WHERE (r.from_date < ? and r.to_date > ? ) OR (r.from_date > ?"
				+"AND r.to_date < ? ) OR (r.from_date = ? AND r.to_date = ?)"
				+") AND s.campground_id = ? GROUP BY s.site_id,s.campground_id, c.daily_fee LIMIT 5");
		SqlRowSet siteNextRow = jdbcTemplate.queryForRowSet(site,fromDate,toDate,fromDate,toDate,fromDate,toDate,campId);
		while(siteNextRow.next()){
			Site newSite = mapRowToSite(siteNextRow);
			siteInfo.add(newSite);
		}
		return siteInfo;
	}


	private Site mapRowToSite(SqlRowSet siteNextRow){
		Site theSite = new Site();
			theSite.setSiteId(siteNextRow.getLong("site_id"));
			theSite.setCampgroundId(siteNextRow.getLong("campground_id"));
			theSite.setSiteNum(siteNextRow.getLong("site_number"));
			theSite.setMaxOccupy(siteNextRow.getLong("max_occupancy"));
			theSite.setAvailable(siteNextRow.getBoolean("accessible"));
			theSite.setMaxRVLength(siteNextRow.getLong("max_rv_length"));
			theSite.setUtilities(siteNextRow.getBoolean("utilities"));
			theSite.setDailyFee(siteNextRow.getBigDecimal("daily_fee"));
	
			return theSite;
	}
}