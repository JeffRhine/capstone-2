package view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.time.temporal.ChronoUnit;
import javax.sql.DataSource;

import java.text.DateFormatSymbols;
import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.Campground;
import com.techelevator.Park;
import com.techelevator.Reservation;
import com.techelevator.Site;

import DAO.DAOCampground;
import DAO.DAOPark;
import DAO.DAOReservation;
import DAO.DAOSite;
import JDBC.JDBCCampgroundDAO;
import JDBC.JDBCParkDAO;
import JDBC.JDBCReservationDAO;
import JDBC.JDBCSiteDAO;

public class CampgroundCLI {
	private Menu menu;
	private DAOPark parkDAO;
	private DAOCampground campgroundDAO;
	private DAOSite siteDAO;
	private DAOReservation reservationDAO;
	
	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		parkDAO = new JDBCParkDAO(datasource);
		campgroundDAO =new JDBCCampgroundDAO(datasource);
		siteDAO = new JDBCSiteDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
		menu = new Menu(System.in,System.out);
	}
	private static final String MENU_OPTION_RETURN_TO_QUIT = "Return to main menu";
	
	
	
	private static final String CG_MENU_OPTION_ALL_CAMPGROUNDS = "View Campgrounds";
	private static final String CG_MENU_OPTION_SEARCH_FOR_RESERVE = "Search for Reservation";
	private static final String[] CAMP_MENU_OPTIONS = new String[] { CG_MENU_OPTION_ALL_CAMPGROUNDS,
																		   CG_MENU_OPTION_SEARCH_FOR_RESERVE,														
																		   MENU_OPTION_RETURN_TO_QUIT};

	
	private static final String MENU_OPTION_SEARCH_FOR_RESERVE = "Search for Available Reservation";
	private static final String[] RESERVE_MENU_OPTIONS = new String[] { MENU_OPTION_SEARCH_FOR_RESERVE,														
																		   MENU_OPTION_RETURN_TO_QUIT};
	
	private static final String CAMPGROUND_CHOICE = "Which campground (enter 0 to cancel)?";
	private static final String ARRIVAL_DATE = "What is the arrival date? ";
	private static final String DEPARTURE_DATE = "What is the departure date? ";
	private static final String[] RESERVATION_OPTIONS = new String[] { CAMPGROUND_CHOICE,
																			ARRIVAL_DATE,
																			DEPARTURE_DATE};
	
	private void run() {
	String textInBold = "View Parks Interface";
	printHeading(textInBold);
	printHeading("Select A Park For Further Details");
	
	  while(true){
		  Park choice = (Park)menu.getChoiceFromOptions(parkDAO.getAllParks().toArray());
		  handleParkInfo(choice);		
	  }
	 
}
	private void handleParkInfo(Park park) {
		while(true){
		printHeading("Park Information Screen");
		System.out.println(park.getName()+" National Park");
		  System.out.println("Location: " + String.format("%16s",park.getLocation()));
		  System.out.println("Established: "+String.format("%18s",park.getEstDate()));
		  System.out.println("Area: " +String.format("%20s",park.getArea()));
		  System.out.println("Annual Visitors: " +String.format("%11s", park.getVisitors()));
		  System.out.println("");
		  int charCount = 0;
		  for(String word: park.getDescription().split(" ")){
			  System.out.print(word+" ");
			  charCount+=word.length();
			  if(charCount>=60){
				  System.out.println();
				  charCount=0;
			  }
		  }
		  System.out.println("\n");
		  
		String choice = (String)menu.getChoiceFromOptions(CAMP_MENU_OPTIONS);
			if(choice.equals(CG_MENU_OPTION_ALL_CAMPGROUNDS)){
			  handleViewCampground(park);	
			}if(choice.equals(CG_MENU_OPTION_SEARCH_FOR_RESERVE)){
				handleSearchReservation(park);
			}if(choice.equals(MENU_OPTION_RETURN_TO_QUIT)){
				break;
				}
			}
		}
		private void handleViewCampground(Park park) {
			printHeading("Park Campgrounds");
			printHeading(park.getName()+ " National Park Campgrounds");
			System.out.println( "Id  Name                               Open       Close         DailyFee");
			List<Campground> campgrounds = campgroundDAO.getAllCampgrounds(park.getId());
			for(Campground camp:campgrounds){
				System.out.print("#"+camp.getCampgroundId()+"  ");
				System.out.print(String.format("%-35s",camp.getName()));
				System.out.print(String.format("%-11s",Month.of(Integer.parseInt(camp.getOpen())).name()));
				System.out.print(String.format("%-14s",Month.of(Integer.parseInt(camp.getClosed())).name()));
				System.out.println(String.format("%-25s","$"+camp.getFee()));
			}
			 while(true){	  
				  	String choice = (String)menu.getChoiceFromOptions(RESERVE_MENU_OPTIONS);
					if(choice.equals(MENU_OPTION_SEARCH_FOR_RESERVE)){
						handleSearchReservation(park);	
						}if(choice.equals(MENU_OPTION_RETURN_TO_QUIT)){
							break;
						}
			  }
		}	

			 private void handleSearchReservation(Park park){
			
					printHeading("Search for Campground Reservation");
					System.out.println( "Id  Name                               Open       Close         DailyFee");
					List<Campground> campgrounds = campgroundDAO.getAllCampgrounds(park.getId());
					for(Campground camp:campgrounds){
						System.out.print("#"+camp.getCampgroundId()+"  ");
						System.out.print(String.format("%-35s",camp.getName()));
						System.out.print(String.format("%-11s",Month.of(Integer.parseInt(camp.getOpen())).name()));
						System.out.print(String.format("%-14s",Month.of(Integer.parseInt(camp.getClosed())).name()));
						System.out.println(String.format("%-25s","$"+camp.getFee()));
					}
					while(true){	 
						
						Long choice = menu.getSite();
						LocalDate arrive = menu.getArrivalDate();
						LocalDate depart = menu.getDepartureDate();
						long daysBetween = ChronoUnit.DAYS.between(arrive,depart);
							System.out.println("");
					System.out.println("Results Matching Your Search Criteria");
							System.out.println("");
					System.out.println("Site No.   Max Occup.  Accessible?  Max RV Length     Utility    Cost");
					List<Site> sites = siteDAO.getAllSites(choice,arrive,depart);
//					int count=1;
					for(Site site:sites){
//						System.out.print(count++ +") ");
						System.out.print(String.format("%-15s","#"+site.getSiteNum()));
						System.out.print(String.format("%-10s",site.getMaxOccupy()));
						System.out.print(String.format("%-15s",site.isAvailable() ? "Yes": "No") );
						if(site.getMaxRVLength()==0){System.out.print(String.format("%-15s","N/A"));};
						if(site.getMaxRVLength()!=0){System.out.print(String.format("%-15s",site.getMaxRVLength()));};
						System.out.print(String.format("%-10s",site.isUtilities() ? "Yes": "N/A"));
						System.out.println(String.format("%-15s","$"+ (daysBetween * site.getDailyFee().longValue())+".00"));
					}
				
					Long choice2 = menu.getSiteId();
					String name = menu.getName();
					reservationDAO.setReservation(choice2, arrive, depart, name);
					List<Reservation> reserveId = reservationDAO.getConfirmId(name, arrive);
					for (Reservation reserve:reserveId){
					System.out.print("The reservation has been made and the confirmation id is "+reserve.getReservationId());
					System.exit(0);
					}
				}
			 }
				

private void printHeading(String headingText) {
	System.out.println("\n"+headingText);
	for(int i = 0; i < headingText.length(); i++) {
		System.out.print("-");
	}
	System.out.println();
}



//	private void displayApplicationBanner() {
//		System.out.println(" ______                          _               ");
//		System.out.println("|  ____|                        |_|              ");
//		System.out.println("| |      ____  _ __ ___   _ __   _  ___      __  ");
//		System.out.println("| |     /  /||| '_ ` _ \\| '_ \ | ||  _ \\ / _ | ");
//		System.out.println("| |____ | |_||| | | | | || |_) || || | | || | || "); 
//		System.out.println("|______|\____||_| |_| |_|| .__/ |_||_| |_| \ \|| "); 
//		System.out.println("                         | |              __/ /  ");                        
//		System.out.println("                         |_|             |___/   ");                       
//		System.out.println();
//	}
}
