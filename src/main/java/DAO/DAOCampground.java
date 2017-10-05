package DAO;

import java.util.List;

import com.techelevator.Campground;

public interface DAOCampground {
public List<Campground> getAllCampgrounds();
public List<Campground> getCampgroundInfo(long choice);
}
