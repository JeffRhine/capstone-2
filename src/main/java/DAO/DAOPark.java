package DAO;

import java.util.List;

import com.techelevator.Park;

public interface DAOPark {
	public List<Park> getAllParks();
	public List<Park> getParkInfo(long choice);
}
