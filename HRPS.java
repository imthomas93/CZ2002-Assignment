/**
HRPS is the Interface class of the hotel system

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE LAM WEN QI
 @version 1.0
 @since 2016-03-18
*/
package assignment.main;

import java.util.Scanner;

import assignment.model.DataStore;
import assignment.util.*;
import assignment.util.datastore.DataStoreFactory;

public class HRPS {
	private static Scanner scn  = new Scanner(System.in);
	static String fileName = System.getProperty("user.dir") + "/storage/datastore.ser";
	static DataStore dataStore = null;
	private static int choice;
	
	
	/**
	 * Main program of HRPS
	 * @param args
	 */
	public static void main(String[] args) {
		
		// load from dataFactory
		dataStore = DataStoreFactory.init(fileName);
		do{
			mainMenu();
			try{
				choice = Integer.parseInt(scn.nextLine());
			}
			catch(NumberFormatException e){
				System.out.println("Error: " + e.getMessage());
			}
			switch(choice){
			case 1:
				createUpdateSearchGuest(dataStore);
				DataStoreFactory.update(dataStore, fileName);
				break;
			case 2:
				createUpdateRemovePrintReservation(dataStore);
				DataStoreFactory.update(dataStore, fileName);
				break;
			case 3:
				createUpdateRoom(dataStore);
				DataStoreFactory.update(dataStore, fileName);
				break;
			case 4:
				RoomServiceMgr.showMenu(dataStore);
				DataStoreFactory.update(dataStore, fileName);
				break;
			case 5:
				createUpdateRemoveMenu(dataStore);
				DataStoreFactory.update(dataStore, fileName);
				break;
			case 6:
				RoomMgr.checkAvailability(dataStore);
				DataStoreFactory.update(dataStore, fileName);
				break;
			case 7:
				RoomMgr.checkIn(dataStore);
				DataStoreFactory.update(dataStore, fileName);
				break;
			case 8:
				PaymentMgr.checkOut(dataStore);
				DataStoreFactory.update(dataStore, fileName);
				break;
			case 9:
				RoomMgr.printRoomStatisticReport(dataStore);
				DataStoreFactory.update(dataStore, fileName);
				break;
			case 0:
				System.out.println("Exiting Application");
				break;
			default:
				System.out.println("Invalid input");
			}
		} while (choice !=0);
	}
	
	/**
	 * Used to print out the main interface
	 */
	public static void mainMenu(){
		System.out.println("\n-----------------------Main Menu--------------------------");
		System.out.println("(1) Create/Update/Search guests detail");
		System.out.println("(2) Create/Update/Remove/Print reservation");
		System.out.println("(3) Create/Update rooms details ");
		System.out.println("(4) Entering room service orders");
		System.out.println("(5) Create/Update/Remove room service menu items");
		System.out.println("(6) Check room availability");
		System.out.println("(7) Room Check-in (for walk-in or reservation)");
		System.out.println("(8) Room Check-out and print bill invoice");
		System.out.println("(9) Print Room Status statistic report");
		System.out.println("(0) Exit Application");
		System.out.println("-----------------------Main Menu--------------------------\n");
		System.out.print("Please enter your choice: ");
	}
	
	/**
	 * Used to Update,remove or print guest details
	 * @param dataStore 	:guest data
	 */
	public static void createUpdateSearchGuest(DataStore dataStore){
		try{
			System.out.println("\n----------(1) Create/Update/Search guests detail----------");
			System.out.println("(1) Create new guests");
			System.out.println("(2) Update guests detail");
			System.out.println("(3) Search guest detail");
			System.out.println("(4) Exit function");
			System.out.print("Please enter your choice: ");
			String input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty Entry!");
			
			choice = Integer.parseInt(input);
			switch(choice){
			case 1:
				GuestMgr.createGuest(dataStore);
				choice = 4;
				break;
			case 2:
				GuestMgr.updateGuest(dataStore);
				choice = 4;
				break;
			case 3:
				GuestMgr.searchGuest(dataStore);
				choice = 4;
				break;
			case 4:
				break;
			default:
				throw new Exception("Invalid Entry!");
			}
		}
		catch(Exception e){
			System.out.println("Error: " + e.getMessage());
		}
	}
	/**
	 * Used to Update,remove or print Reservation details
	 * @param dataStore 	:reservation data
	 */
	
	public static void createUpdateRemovePrintReservation(DataStore dataStore){
		try{
			System.out.println("\n--(2) Create/Update/Remove/Print Reservation details--");
			System.out.println("(1) Create New Reservation");
			System.out.println("(2) Update Reservation");
			System.out.println("(3) Remove Reservation");
			System.out.println("(4) Print Reservation");
			System.out.println("(5) Exit function");
			System.out.print("Please enter your choice: ");
			String input = scn.nextLine();
			if(input.isEmpty())
				throw new Exception("Empty Entry!");
			choice = Integer.parseInt(input);
		
			switch(choice){
			case 1:
				ReservationMgr.createReservation(dataStore);
				break;
			case 2:
				ReservationMgr.updateReservation(dataStore);
				break;
			case 3:
				ReservationMgr.removeReservation(dataStore);
				break;
			case 4:
				ReservationMgr.printReservation(dataStore);
				break;
			case 5:
				break;
			default:
				throw new Exception("Invalid Entry");
			}
		}
		catch(Exception e){
			System.out.println("Error: " + e.getMessage());
		}
	}
	/**
	 * Used to Update,remove or print Room details
	 * @param dataStore 	: room data
	 */
	
	public static void createUpdateRoom(DataStore dataStore){
		try{
			System.out.println("\n------(3) Create/Update Room details------");
			System.out.println("(1) Create New Room");
			System.out.println("(2) Update Room");
			System.out.println("(3) Exit function");
			System.out.print("Please enter your choice: ");
			choice = Integer.parseInt(scn.nextLine());
		}
		catch(Exception e){
			System.out.println("Error: " + e.getMessage());
			choice = 4;
		}
		switch(choice){
		case 1:
			RoomMgr.createRoom(dataStore);
			break;
		case 2:
			RoomMgr.updateRoom(dataStore);
			break;
		case 3:
			break;
		}
	}
	/**
	 * Used to Update,remove or print Room service details
	 * @param dataStore 	:room service data
	 */

	public static void createUpdateRemoveMenu(DataStore dataStore) {
		try{
			System.out.println("\n-------(5) Create/Update/Remove Room Service Menu-------");
			System.out.println("(1) Create new Menu");
			System.out.println("(2) Update Menu");
			System.out.println("(3) Remove Menu");
			System.out.println("(4) Exit function");
			System.out.print("Please enter your choice: ");
			String input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty Entry!");
			
			choice = Integer.parseInt(input);
			switch(choice){
			case 1:
				RoomServiceMgr.createMenu(dataStore);
				choice = 4;
				break;
			case 2:
				RoomServiceMgr.updateMenu(dataStore);
				break;
			case 3:
				RoomServiceMgr.removeMenu(dataStore);
				choice = 4;
				break;
			case 4:
				break;
			default:
				throw new Exception("Invalid Entry!");
			}
		}
		catch(Exception e){
			System.out.println("Error: " + e.getMessage());
		}	
	}
}

