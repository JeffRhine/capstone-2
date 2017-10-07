package DAO;

import java.util.List;

import com.techelevator.Campground;
import com.techelevator.Park;

public interface DAOCampground {
public List<Campground> getAllCampgrounds(long parkId);
public List<Campground> getCampgroundInfoByPark(long choice);
}
