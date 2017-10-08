package JDBC;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Campground;
import com.techelevator.Reservation;

import DAO.DAOReservation;

public class JDBCReservationDAO implements DAOReservation {

	private JdbcTemplate jdbcTemplate;

	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Reservation> getAllReservations(long campId, LocalDate fromDate,LocalDate toDate) {
		List<Reservation> reserveInfo = new ArrayList<>();
		String reserve = ("SELECT s.site_number,s.max_occupancy,s.accessible,s.max_rv_length,s.utilities,c.daily_fee FROM reservation r "
				+ "JOIN site s ON r.site_id=s.site_id JOIN campground c ON s.campground_id=c.campground_id WHERE c.campground =?"
				+ "AND ? ");
		SqlRowSet reserveNextRow = jdbcTemplate.queryForRowSet(reserve,campId);
		while(reserveNextRow.next()){
			Reservation reservation = mapRowToReservation(reserveNextRow);
			reserveInfo.add(reservation);
		}
		return reserveInfo;
	}
		private Reservation mapRowToReservation(SqlRowSet reserveNextRow){
			Reservation reservation = new Reservation();
			reservation.setReservationId(reserveNextRow.getLong("reservation_id"));
			reservation.setSiteId(reserveNextRow.getLong("site_id"));
			reservation.setName(reserveNextRow.getString("name"));
			reservation.setFromDate(reserveNextRow.getDate("from_date").toLocalDate());
			reservation.setToDate(reserveNextRow.getDate("to_date").toLocalDate());
			reservation.setCreateDate(reserveNextRow.getDate("create_date").toLocalDate());
			
			return reservation;
		}

		@Override
		public void  setReservation(long siteId, LocalDate fromDate, LocalDate toDate, String name) {
			String reserve = ("INSERT INTO reservation (site_id,name,from_date,to_date) VALUES (?,?,?,?)");
			 jdbcTemplate.update(reserve,siteId,name,fromDate,toDate);
			
		}

		@Override
		public List<Reservation> getConfirmId(String name,LocalDate fromDate) {
			List<Reservation> reserveInfo = new ArrayList<>();
			 String reserveId = ("SELECT reservation_id FROM reservation WHERE name =? AND from_date=?");
			 SqlRowSet reserveNextRow = jdbcTemplate.queryForRowSet(reserveId,name,fromDate);
				while(reserveNextRow.next()){
					Reservation reservation = mapRowToReservationId(reserveNextRow);
					reserveInfo.add(reservation);
				}
				return reserveInfo;
		}
		private Reservation mapRowToReservationId(SqlRowSet reserveNextRow){
			Reservation reservation = new Reservation();
			reservation.setReservationId(reserveNextRow.getLong("reservation_id"));
			return reservation;
		}
}

