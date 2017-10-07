package DAO;

import java.time.LocalDate;
import java.util.List;

import com.techelevator.Site;

public interface DAOSite {
	public List<Site> getAllSites(long campId,LocalDate fromDate,LocalDate toDate);
}
