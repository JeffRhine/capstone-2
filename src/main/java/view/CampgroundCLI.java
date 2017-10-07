package view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.time.temporal.ChronoUnit;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.Campground;
import com.techelevator.Park;
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
				System.out.print(String.format("%-11s",camp.getOpen()));
				System.out.print(String.format("%-14s",camp.getClosed()));
				System.out.println(String.format("%-25s","$"+camp.getFee()));
			}
			 while(true){

				  
				  	String choice = (String)menu.getChoiceFromOptions(RESERVE_MENU_OPTIONS);
					if(choice.equals(MENU_OPTION_SEARCH_FOR_RESERVE)){
						handleSearchReservation(park);	
						}if(choice.equals(MENU_OPTION_RETURN_TO_QUIT)){
							handleSearchReservation(park);
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
						System.out.print(String.format("%-11s",camp.getOpen()));
						System.out.print(String.format("%-14s",camp.getClosed()));
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
					for(Site site:sites){
						System.out.print(String.format("%-15s","#"+site.getSiteNum()));
						System.out.print(String.format("%-10s",site.getMaxOccupy()));
						System.out.print(String.format("%-15s",site.isAvailable()));
						System.out.print(String.format("%-15s",site.getMaxRVLength()));
						System.out.print(String.format("%-10s",site.isUtilities()));
						System.out.println(String.format("%-15s","$"+ (daysBetween * site.getDailyFee().longValue())));
					}
					Long choice2 = menu.getSiteId();
					String name = menu.getName();
					reservationDAO.setReservation(choice2, arrive, depart, name);
					System.out.print("The reservation has been made and the confirmation id is "+reservationDAO.getReservationId(name, arrive));
					break;
				}
			 }
				
//			  Campground choice2 = (Campground)menu.getChoiceFromOptions(campgroundDAO.getAllCampgrounds(park.getId()).toArray());				 
//			while(true){
		
//			printHeading("Park Campgrounds");
//				System.out.println(parkDAO.toString()+" National Park Campgrounds");
//			  System.out.println("Location: " + String.format("%16s",park.getLocation()));
//			  System.out.println("Established: "+String.format("%18s",park.getEstDate()));
//			  System.out.println("Area: " +String.format("%20s",park.getArea()));
//			  System.out.println("Annual Visitors: " +String.format("%11s", park.getVisitors()));
//			  System.out.println("");
//			  System.out.println(camp.getDescription());
//			String choice = (String)menu.getChoiceFromOptions(CAMP_MENU_OPTIONS);
//			 while(true){
//		}
//			}
private void printHeading(String headingText) {
	System.out.println("\n"+headingText);
	for(int i = 0; i < headingText.length(); i++) {
		System.out.print("-");
	}
	System.out.println();
}
}
//private static final String MAIN_MENU_OPTION_ACADIA = "Acadia";
//private static final String MAIN_MENU_OPTION_ARCHES = "Arches";
//private static final String MAIN_MENU_OPTION_CUYAHOGA = "Cuyahoga National Valley Park";
//private static final String MAIN_MENU_OPTION_QUIT = "Quit";
//private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_OPTION_ACADIA, 
//																 MAIN_MENU_OPTION_ARCHES, 
//																 MAIN_MENU_OPTION_CUYAHOGA, 
//																 MAIN_MENU_OPTION_QUIT };

//	
//	private static final String EMPL_MENU_OPTION_ALL_EMPLOYEES = "Show all employees";
//	private static final String EMPL_MENU_OPTION_SEARCH_BY_NAME = "Employee search by name";
//	private static final String EMPL_MENU_OPTION_EMPLOYEES_NO_PROJECTS = "Show employees without projects";
//	private static final String EMPL_MENU_OPTION_CHANGE_DEPARTMENT = "Change employee's department";
//	private static final String[] EMPL_MENU_OPTIONS = new String[] { EMPL_MENU_OPTION_ALL_EMPLOYEES,
//																	 EMPL_MENU_OPTION_SEARCH_BY_NAME,
//																	 EMPL_MENU_OPTION_EMPLOYEES_NO_PROJECTS,
//																	 EMPL_MENU_OPTION_CHANGE_DEPARTMENT,
//																	 MENU_OPTION_RETURN_TO_MAIN};
//	
//	private static final String PROJ_MENU_OPTION_ACTIVE_PROJECTS = "Show active projects";
//	private static final String PROJ_MENU_OPTION_PROJECT_EMPLOYEES = "Show project employees";
//	private static final String PROJ_MENU_OPTION_ASSIGN_EMPLOYEE_TO_PROJECT = "Assign an employee to a project";
//	private static final String PROJ_MENU_OPTION_REMOVE_EMPLOYEE_FROM_PROJECT = "Remove employee from project";
//	private static final String[] PROJ_MENU_OPTIONS = new String[] { PROJ_MENU_OPTION_ACTIVE_PROJECTS,
//																	 PROJ_MENU_OPTION_PROJECT_EMPLOYEES,
//																	 PROJ_MENU_OPTION_ASSIGN_EMPLOYEE_TO_PROJECT,
//																	 PROJ_MENU_OPTION_REMOVE_EMPLOYEE_FROM_PROJECT,
//																	 MENU_OPTION_RETURN_TO_MAIN };
//	

//	
//	public static void main(String[] args) {
//		ProjectsCLI application = new ProjectsCLI();
//		application.run();
//	}
//	
//	public ProjectsCLI() {
//		this.menu = new Menu(System.in, System.out);
//		
//		BasicDataSource dataSource = new BasicDataSource();
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/projects");
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("postgres1");
//		
//		departmentDAO = new JDBCDepartmentDAO(dataSource);
//		employeeDAO = new JDBCEmployeeDAO(dataSource);
//		projectDAO = new JDBCProjectDAO(dataSource);
//	}
//

//
	
	
//
//	private void handleAddDepartment() {
//		printHeading("Add New Department");
//		String newDepartmentName = getUserInput("Enter new Department name");
//		Department newDepartment = departmentDAO.createDepartment(newDepartmentName);
//		System.out.println("\n*** "+newDepartment.getName()+" created ***");
//	}
//	
//	private void handleUpdateDepartmentName() {
//		printHeading("Update Department Name");
//		List<Department> allDepartments = departmentDAO.getAllDepartments();
//		if(allDepartments.size() > 0) {
//			System.out.println("\n*** Choose a Department ***");
//			Department selectedDepartment = (Department)menu.getChoiceFromOptions(allDepartments.toArray());
//			String newDepartmentName = getUserInput("Enter new Department name");
//			departmentDAO.updateDepartmentName(selectedDepartment.getId(), newDepartmentName);
//		} else {
//			System.out.println("\n*** No results ***");
//		}
//	}
//

//
//	private void handleDepartmentSearch() {
//		printHeading("Department Search");
//		String departmentSearch = getUserInput("Enter department name to search for");
//		List<Department> departments = departmentDAO.searchDepartmentsByName(departmentSearch);
//		listDepartments(departments);
//	}
//	
//	private void handleDepartmentEmployeeList() {
//		printHeading("Department Employee List");
//		List<Department> allDepartments = departmentDAO.getAllDepartments();
//		if(allDepartments.size() > 0) {
//			System.out.println("\n*** Choose a Department ***");
//			Department selectedDepartment = (Department)menu.getChoiceFromOptions(allDepartments.toArray());
//			List<Employee> departmentEmployees = employeeDAO.getEmployeesByDepartmentId(selectedDepartment.getId());
//			listEmployees(departmentEmployees);
//		} else {
//			System.out.println("\n*** No results ***");
//		}
//	}
//
//	private void listDepartments(List<Department> departments) {
//		System.out.println();
//		if(departments.size() > 0) {
//			for(Department dept : departments) {
//				System.out.println(dept.getName());
//			}
//		} else {
//			System.out.println("\n*** No results ***");
//		}
//	}
//	
//	private void handleEmployees() {
//		printHeading("Employees");
//		String choice = (String)menu.getChoiceFromOptions(EMPL_MENU_OPTIONS);
//		if(choice.equals(EMPL_MENU_OPTION_ALL_EMPLOYEES)) {
//			handleListAllEmployees();
//		} else if(choice.equals(EMPL_MENU_OPTION_SEARCH_BY_NAME)) {
//			handleEmployeeSearch();
//		} else if(choice.equals(EMPL_MENU_OPTION_EMPLOYEES_NO_PROJECTS)) {
//			handleUnassignedEmployeeSearch();
//		} else if(choice.equals(EMPL_MENU_OPTION_CHANGE_DEPARTMENT)) {
//			handleChangeEmployeeDepartment();
//		}
//	}
//
//	private void handleListAllEmployees() {
//		printHeading("All Employees");
//		List<Employee> allEmployees = employeeDAO.getAllEmployees();
//		listEmployees(allEmployees);
//	}
//
//	private void handleEmployeeSearch() {
//		printHeading("Employee Search");
//		String firstNameSearch = getUserInput("Enter first name to search for");
//		String lastNameSearch = getUserInput("Enter last name to search for");
//		List<Employee> employees = employeeDAO.searchEmployeesByName(firstNameSearch, lastNameSearch);
//		listEmployees(employees);
//	}
//
//	private void handleUnassignedEmployeeSearch() {
//		printHeading("Unassigned Employees");
//		List<Employee> employees = employeeDAO.getEmployeesWithoutProjects();
//		listEmployees(employees);
//	}
//	
//	private void listEmployees(List<Employee> employees) {
//		System.out.println();
//		if(employees.size() > 0) {
//			for(Employee emp : employees) {
//				System.out.println(emp.getLastName() + ", " + emp.getFirstName());
//			}
//		} else {
//			System.out.println("\n*** No results ***");
//		}
//	}
//	
//	private void handleChangeEmployeeDepartment() {
//		printHeading("Change Employee Department");
//		
//		System.out.println("Choose an employee to transfer:");
//		List<Employee> allEmployees = employeeDAO.getAllEmployees();
//		Employee selectedEmployee = (Employee)menu.getChoiceFromOptions(allEmployees.toArray());
//		
//		System.out.println("Choose the new department:");
//		List<Department> allDepartments = departmentDAO.getAllDepartments();
//		Department selectedDepartment = (Department)menu.getChoiceFromOptions(allDepartments.toArray());
//		
//		employeeDAO.changeEmployeeDepartment(selectedEmployee.getId(), selectedDepartment.getId());
//	}
//
//	private void handleProjects() {
//		printHeading("Projects");
//		String choice = (String)menu.getChoiceFromOptions(PROJ_MENU_OPTIONS);
//		if(choice.equals(PROJ_MENU_OPTION_ACTIVE_PROJECTS)) {
//			handleListActiveProjects();
//		} else if(choice.equals(PROJ_MENU_OPTION_PROJECT_EMPLOYEES)) {
//			handleProjectEmployeeList();
//		} else if(choice.equals(PROJ_MENU_OPTION_ASSIGN_EMPLOYEE_TO_PROJECT)) {
//			handleEmployeeProjectAssignment();
//		}  else if(choice.equals(PROJ_MENU_OPTION_REMOVE_EMPLOYEE_FROM_PROJECT)) {
//			handleEmployeeProjectRemoval();
//		}
//	}
//
//	private void handleListActiveProjects() {
//		printHeading("Active Projects");
//		List<Project> projects = projectDAO.getAllActiveProjects();
//		listProjects(projects);
//	}
//
//	private void handleEmployeeProjectRemoval() {
//		printHeading("Remove Employee From Project");
//		
//		Project selectedProject = getProjectSelectionFromUser();
//		
//		System.out.println("Choose an employee to remove:");
//		List<Employee> projectEmployees = employeeDAO.getEmployeesByProjectId(selectedProject.getId());
//		if(projectEmployees.size() > 0) {
//			Employee selectedEmployee = (Employee)menu.getChoiceFromOptions(projectEmployees.toArray());
//			projectDAO.removeEmployeeFromProject(selectedProject.getId(), selectedEmployee.getId());
//			System.out.println("\n*** "+selectedEmployee+" removed from "+selectedProject+" ***");
//		} else {
//			System.out.println("\n*** No results ***");
//		}
//	}
//
//	private void handleEmployeeProjectAssignment() {
//		printHeading("Assign Employee To Project");
//		
//		Project selectedProject = getProjectSelectionFromUser();
//		
//		System.out.println("Choose an employee to add:");
//		List<Employee> allEmployees = employeeDAO.getAllEmployees();
//		Employee selectedEmployee = (Employee)menu.getChoiceFromOptions(allEmployees.toArray());
//		
//		projectDAO.addEmployeeToProject(selectedProject.getId(), selectedEmployee.getId());
//		System.out.println("\n*** "+selectedEmployee+" added to "+selectedProject+" ***");
//	}
//	
//	private void handleProjectEmployeeList() {
//		Project selectedProject = getProjectSelectionFromUser();
//		List<Employee> projectEmployees = employeeDAO.getEmployeesByProjectId(selectedProject.getId());
//		listEmployees(projectEmployees);
//	}
//
//	private Project getProjectSelectionFromUser() {
//		System.out.println("Choose a project:");
//		List<Project> allProjects = projectDAO.getAllActiveProjects();
//		return (Project)menu.getChoiceFromOptions(allProjects.toArray());
//	}
//	
//	private void listProjects(List<Project> projects) {
//		System.out.println();
//		if(projects.size() > 0) {
//			for(Project proj : projects) {
//				System.out.println(proj.getName());
//			}
//		} else {
//			System.out.println("\n*** No results ***");
//		}
//	}
//

//	
//	private String getUserInput(String prompt) {
//		System.out.print(prompt + " >>> ");
//		return new Scanner(System.in).nextLine();
//	}
//
//	private void displayApplicationBanner() {
//		System.out.println(" ______                 _                         _____           _           _     _____  ____");
//		System.out.println("|  ____|               | |                       |  __ \\         (_)         | |   |  __ \\|  _ \\");
//		System.out.println("| |__   _ __ ___  _ __ | | ___  _   _  ___  ___  | |__) | __ ___  _  ___  ___| |_  | |  | | |_) |");
//		System.out.println("|  __| | '_ ` _ \\| '_ \\| |/ _ \\| | | |/ _ \\/ _ \\ |  ___/ '__/ _ \\| |/ _ \\/ __| __| | |  | |  _ <");
//		System.out.println("| |____| | | | | | |_) | | (_) | |_| |  __/  __/ | |   | | | (_) | |  __/ (__| |_  | |__| | |_) |");
//		System.out.println("|______|_| |_| |_| .__/|_|\\___/ \\__, |\\___|\\___| |_|   |_|  \\___/| |\\___|\\___|\\__| |_____/|____/");
//		System.out.println("                 | |             __/ |                          _/ |");
//		System.out.println("                 |_|            |___/                          |__/");
//		System.out.println();
//	}
//}
