package JDBC;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Park;


import DAO.DAOPark;

public class JDBCParkDAO implements DAOPark {
	
	
	private JdbcTemplate jdbcTemplate;

	public JDBCParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<Park> getParkInfo(long choice){
		List<Park> parkInfo= new ArrayList<>();
				String park=("SELECT * FROM park WHERE park_id=?");
				jdbcTemplate.update(park,choice);
				SqlRowSet parkNextRow= jdbcTemplate.queryForRowSet(park);
				while(parkNextRow.next()){
					parkInfo.add(mapRowToPark(parkNextRow));
				}
		return parkInfo;
	}

	public List<Park> getAllParks(){
		List<Park> parkInfo = new ArrayList<>();
		String parks = "SELECT * FROM park ";
		SqlRowSet parkNextRow = jdbcTemplate.queryForRowSet(parks);
		while(parkNextRow.next()) {
			parkInfo.add(mapRowToPark(parkNextRow));
		}
		return parkInfo;
	}
		
//	public List<Park> searchCampgroundsReservation(String resSearch){
//		return;
//	}
//	public void updateReservation(Long departmentId, String reservation){
//		
//	}
//	public Park getCampgroundById(Long id){
//		return;
//	}
	private Park mapRowToPark(SqlRowSet parkNextRow) {
		Park thePark = new Park();
		thePark.setId(parkNextRow.getLong("park_id"));
		thePark.setName(parkNextRow.getString("name"));
		thePark.setLocation(parkNextRow.getString("location"));
		thePark.setEstDate(parkNextRow.getDate("establish_date").toLocalDate());
		thePark.setArea(parkNextRow.getLong("area"));
		thePark.setVisitors(parkNextRow.getLong("visitors"));
		thePark.setDescription(parkNextRow.getString("description"));
		
		
		return thePark;
	}
}
//	@Override
//	public List<Park> getParkInfo() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//}
	

//@Override
//public List<Employee> getAllEmployees() {
//	List<Employee> employees = new ArrayList<>();
//	String employee = "SELECT * FROM employee";

//	return employees;
//}
//

//}
