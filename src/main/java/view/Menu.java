package view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Menu {
	private PrintWriter out;
	private Scanner in;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while(choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	public Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		try {
			int selectedOption = Integer.valueOf(userInput);
			if(selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			}
		} catch(NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if(choice == null) {
			out.println("\n*** "+userInput+" is not a valid option ***\n");
		}
		return choice;
	}
	public Long getSiteId(){
		while(true){
			System.out.println();
			System.out.print("Which site should be reserved(enter 0 to cancel)?");
			String reserveSite = in.nextLine();
			try{
				Long site = Long.parseLong(reserveSite);		
				return site;
			}catch (NumberFormatException e){
				System.out.println("invalid campsite number");
			}
		}
	}
	public String getName(){
		while(true){
			System.out.println();
			System.out.print("What name should the reservation be under?");
			String name = in.nextLine();
			return name;
		}
	}
	public Long getSite(){
		while(true){
			System.out.println();
			System.out.print("Which campground (enter 0 to cancel)?");
			String campGround = in.nextLine();
			if(!campGround.equals("0")){
			try{	
				Long camp = Long.parseLong(campGround);
				if(camp!=0){
					return camp;
				} 			
			}catch (NumberFormatException e){
				System.out.println("invalid campground number");
			}
		}
		}
	}
	public LocalDate getArrivalDate(){
		while(true){
		System.out.print("What is the arrival date? YYYY-MM-DD");
		String arrive = in.nextLine();
		try{
		LocalDate arrivalDate = LocalDate.parse(arrive);
		 return arrivalDate;
		}catch(DateTimeParseException dtpe){
			System.out.println("invalid date");
		}
		}
	}
		public LocalDate getDepartureDate(){	
			while(true){	
		System.out.print("What is the depature date? YYYY-MM-DD");
		String depart = in.nextLine();
		try{
		LocalDate departureDate = LocalDate.parse(depart);
		 	return departureDate;
			}catch(DateTimeParseException dtpe){
				System.out.println("invalid date");
			}
		}
		}
	
	private void displayMenuOptions(Object[] options) {
		out.println();
		for(int i = 0; i < options.length; i++) {
			int optionNum = i+1;
			out.println(optionNum+") "+options[i]);
		}
		out.print("\nPlease choose an option >>> ");
		out.flush();
	}
}
