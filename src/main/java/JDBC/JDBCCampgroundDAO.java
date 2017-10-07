package JDBC;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Campground;
import com.techelevator.Park;


import DAO.DAOCampground;

public class JDBCCampgroundDAO implements DAOCampground {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Campground> getAllCampgrounds(long parkId) {
		List<Campground> campInfo = new ArrayList<>();
		String camp=("SELECT * FROM campground WHERE park_id=?");
		SqlRowSet campNextRow = jdbcTemplate.queryForRowSet(camp,parkId);
		while(campNextRow.next()) {		
			Campground campground =mapRowToCampground(campNextRow);
			campInfo.add(campground);
		}
		return campInfo;
	}
	private Campground mapRowToCampground(SqlRowSet campNextRow){
		Campground campground = new Campground();
		campground.setCampgroundId(campNextRow.getLong("campground_id"));
		campground.setName(campNextRow.getString("name"));
		campground.setOpen(campNextRow.getString("open_from_mm"));
		campground.setClosed(campNextRow.getString("open_to_mm"));
		campground.setFee(campNextRow.getBigDecimal("daily_fee"));
		
		return campground;
		
	}


	@Override
	public List<Campground> getCampgroundInfoByPark(long choice) {
		List<Campground> campInfo= new ArrayList<>();
		String camp=("SELECT * FROM campground WHERE park_id=?");
		jdbcTemplate.update(camp,choice);
		SqlRowSet campNextRow= jdbcTemplate.queryForRowSet(camp);
		while(campNextRow.next()){
			campInfo.add(mapRowToCamp(campNextRow));
		}
return campInfo;
}
	private Campground mapRowToCamp(SqlRowSet campNextRow) {
		Campground camp = new Campground();
		camp.setCampgroundId(campNextRow.getLong("campground_id"));
		camp.setParkId(campNextRow.getLong("park_id"));
		camp.setName(campNextRow.getString("name"));
		camp.setOpen(campNextRow.getString("open_from_mm"));
		camp.setClosed(campNextRow.getString("open_to_mm"));;
		camp.setFee(campNextRow.getBigDecimal("daily_fee"));
		
		return camp;
	}
}
