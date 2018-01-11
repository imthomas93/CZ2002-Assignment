/**
 Represents the Reservation Manager class that let user make/update/delete reservation settings

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE  LAM WEN QI
 @version 1.0
 @since 2016-03-31
*/
package assignment.util;

import java.text.SimpleDateFormat;
import java.util.*;
import assignment.model.*;
import assignment.model.Reservation.ReservationStatus;
import assignment.model.Reservation.CheckInVia;
import assignment.model.Room.RoomStatus;

public class ReservationMgr {
	/**
	 * Scanner scn to take in user input
	 */
	private static Scanner scn = new Scanner(System.in);
	
	/**
	 * Reservation identity.
	 */
	private static int reservationIDCounter;
	
	/**
	 * Highest storey of hotel.
	 */
	private static int maxLevel = 2;
	
	/**
	 * Reservation object
	 */
	private static Reservation reservation = null;
	
	/**
	 * Room object
	 */
	private static Room room= null;
	
	/**
	 * Guest object
	 */
	private static Guest guest=null;
	
	/**
	 * RoomMgr object
	 */
	private static RoomMgr roomMgr=null;
	
	/**
	 * Create a new reservation
	 * 
	 * @param dataStore		Store reservation data
	 */
	public static void createReservation(DataStore dataStore){
		try{
			boolean roomFound=false;
			String input,guestId;
			String check_In="",check_Out="",newRoomNum="";	
			
			// validate lvl within range 
			newRoomNum=validateRoomNumber(dataStore);
			
			if(newRoomNum.equals(""))
				return;
			
			System.out.print("Enter Guest ID: ");
			guestId=scn.nextLine();
			if (guestId.isEmpty())
				 throw new Exception("Guest ID Entry is missing!");
			
			if(dataStore.getGuestHashtable().containsKey(guestId)){
				guest = dataStore.getGuestHashtable().get(guestId);
				
				for(int i=0;i<dataStore.getRoomList().size();i++){
					room=dataStore.getRoomList().get(i);
					
					if(room.getRoomNumber().equals(newRoomNum)){
						int numOfAdult,numOfChild;
						roomFound=true;		
						reservationIDCounter = dataStore.getReservationIDCounter() + 1;
						System.out.print("Enter Number of Adults: ");
						input = scn.nextLine();
						if (input.isEmpty())
							throw new Exception("Blank Entry!");
						numOfAdult = Integer.parseInt(input);

						System.out.print("Enter Number of Children: ");
						input = scn.nextLine();
						if (input.isEmpty())
							throw new Exception("Blank Entry!");
						numOfChild = Integer.parseInt(input);

						// Check in Year
						System.out.print("Enter Year of Check in (Availble to book from 2016 to 2017): ");
						input=scn.nextLine();
						if (input.isEmpty())
							throw new Exception("Blank Entry!");
						int yearIn = Integer.parseInt(input);
						if (yearIn <= 2015 && yearIn >= 2018)
							throw new Exception("Reservation only availble between 2016 to 2017");
						
						// Check in Month
						System.out.print("Enter Month of Check in (i.e 01, 04, etc.) : ");
						input=scn.nextLine();
						if (input.isEmpty())
							throw new Exception("Blank Entry!");
						int monthIn = Integer.parseInt(input);
						if (monthIn < 0 && monthIn > 12)
							throw new Exception("Invalid Month Entered!");	
						
						// Check in Day
						System.out.print("Enter Day of Check in (i.e 12, 31, etc.) : ");
						input=scn.nextLine();
						if (input.isEmpty())
							throw new Exception("Blank Entry!");
						int dayIn = Integer.parseInt(input);
						if (dayIn < 0 && dayIn > 12)
							throw new Exception("Invalid Month Entered!");
							
						// Check out Year
						System.out.print("Enter Year of Check out (Availble to book from 2016 to 2018): ");
						input=scn.nextLine();
						if (input.isEmpty())
							throw new Exception("Blank Entry!");
						int yearOut = Integer.parseInt(input);
						if (yearOut <= 2015 && yearOut >= 2019)
							throw new Exception("Reservation only availble between 2016 to 2018");
						
						// Check out Month
						System.out.print("Enter Month of Check Out (i.e 01, 04, etc.) : ");
						input=scn.nextLine();
						if (input.isEmpty())
							throw new Exception("Blank Entry!");
						int monthOut = Integer.parseInt(input);
						if (monthOut < 0 && monthOut > 12)
							throw new Exception("Invalid Month Entered!");	
						
						// Check out Day
						System.out.print("Enter Day of Check Out (i.e 12, 31, etc.) : ");
						input=scn.nextLine();
						if (input.isEmpty())
							throw new Exception("Blank Entry!");
						int dayOut = Integer.parseInt(input);
						if (dayOut < 0 && dayOut > 12)
							throw new Exception("Invalid Month Entered!");
						
						// validate dates
						if(validateDates(yearIn, monthIn, dayIn, yearOut, monthOut, dayOut) == false)
							throw new Exception ("Invalid Input!");
						
						String dayFormat, monthFormat;
						if (dayIn <10)
							dayFormat = "0" + dayIn;
						else
							dayFormat = "" + dayIn;
						if (monthIn < 10)
							monthFormat = "0" + monthIn;
						else 
							monthFormat = "" + monthIn;
						check_In = dayFormat + "/" + monthFormat + "/" + yearIn;
	
						if (dayOut <10)
							dayFormat = "0" + dayOut;
						else
							dayFormat = "" + dayOut;
						if (monthOut < 10)
							monthFormat = "0" + monthOut;
						else 
							monthFormat = "" + monthOut;
						check_Out = dayFormat + "/" + monthFormat + "/" + yearOut;
						
						check_In+=" 15:00";
						check_Out+=" 12:00";
						
						SimpleDateFormat dateFormatter=new SimpleDateFormat("dd/MM/yyyy HH:mm");
						Date dateIn=dateFormatter.parse(check_In);
						Date dateOut=dateFormatter.parse(check_Out);
						Calendar checkInDate = Calendar.getInstance();
						Calendar checkOutDate=Calendar.getInstance();
						checkInDate.setTime(dateIn);
						checkOutDate.setTime(dateOut);		
						
						ReservationStatus status = null;
						
						if(room.getRoomStatus() == RoomStatus.VACANT)
							status=ReservationStatus.CONFIRMED;
						else
							status=ReservationStatus.IN_WAITLIST;
						
						CheckInVia checkInVia = CheckInVia.RESERVATION;
						
						reservation = new Reservation(reservationIDCounter, room, numOfAdult, numOfChild, checkInDate, checkOutDate, status, checkInVia, guest);	
						dataStore.getReservationHashtable().put(reservationIDCounter, reservation);
						dataStore.setReservationIDCounter(dataStore.getReservationIDCounter() + 1);
						
						room.setRoomStatus(RoomStatus.RESERVED);
						System.out.println("New Reservation is Made!!");
						System.out.println("Acknowledgement Receipt: ");
						reservation.printReservation();
					}
				}
				if(roomFound==false)
					throw new Exception("Room is currently unavailable.");
			}
			else
				throw new Exception("Guest detail not in database!");
		}catch(Exception e){System.out.println("Error: " + e.getMessage());}
	}
	
	/**
	 * Allow guests who did not make reservation to book room.
	 * 
	 * @param dataStore		Store walk in data
	 * @param guest			Contains guest object  
	 */
	public static void createWalkInReservation(DataStore dataStore,Guest guest){
		try{
			boolean roomFound=false;
			String newRoomNum;
			String input;
			String guestId;
			String check_In="",check_Out="";	

			newRoomNum=validateRoomNumber(dataStore);
			
			if(newRoomNum.equals(""))
				return;
						
			if(dataStore.getGuestHashtable().containsKey(guest.getIdentity())){
				guest = dataStore.getGuestHashtable().get(guest.getIdentity());
				
				for(int i=0;i<dataStore.getRoomList().size();i++){
					room=dataStore.getRoomList().get(i);
					
					if(room.getRoomNumber().equals(newRoomNum) && room.getRoomStatus() == RoomStatus.VACANT){
						int numOfAdult,numOfChild;
						roomFound=true;		
						reservationIDCounter = dataStore.getReservationIDCounter() + 1;
						System.out.print("Enter Number of Adults: ");
						input = scn.nextLine();
						if (input.isEmpty())
							throw new Exception("Blank Entry!");
						numOfAdult = Integer.parseInt(input);
						input = null;
						
						System.out.print("Enter Number of Children: ");
						input = scn.nextLine();
						if (input.isEmpty())
							throw new Exception("Blank Entry!");
						numOfChild = Integer.parseInt(input);
						input = null;
						
						Calendar cal=Calendar.getInstance();
						// Check in Year
						int yearIn=cal.get(Calendar.YEAR);
						// Check in Month
						int monthIn=cal.get(Calendar.MONTH)+1;
						// Check in Day
						int dayIn=cal.get(Calendar.DAY_OF_MONTH);
							
						// Check out Year
						System.out.print("Enter Year of Check out (Availble to book from 2016 to 2018: ");
						input=scn.nextLine();
						if (input.isEmpty())
							throw new Exception("Blank Entry!");
						int yearOut = Integer.parseInt(input);
						if (yearOut <= 2015 && yearOut >= 2019)
							throw new Exception("Reservation only availble between 2016 to 2018");
						
						// Check out Month
						System.out.print("Enter Month of Check in (i.e 01, 04, etc.) : ");
						input=scn.nextLine();
						if (input.isEmpty())
							throw new Exception("Blank Entry!");
						int monthOut = Integer.parseInt(input);
						if (monthOut < 0 && monthOut > 12)
							throw new Exception("Invalid Month Entered!");	
						
						// Check out Day
						System.out.print("Enter Day of Check Out (i.e 12, 31, etc.) : ");
						input=scn.nextLine();
						if (input.isEmpty())
							throw new Exception("Blank Entry!");
						int dayOut = Integer.parseInt(input);
						if (dayOut < 0 && dayOut > 12)
							throw new Exception("Invalid Month Entered!");
						
						// validate dates
						if(validateDates(yearIn, monthIn, dayIn, yearOut, monthOut, dayOut) == false)
							throw new Exception ("Invalid Input!");
						
						String dayFormat, monthFormat;
						if (dayIn <10)
							dayFormat = "0" + dayIn;
						else
							dayFormat = "" + dayIn;
						if (monthIn < 10)
							monthFormat = "0" + monthIn;
						else 
							monthFormat = "" + monthIn;
						check_In = dayFormat + "/" + monthFormat + "/" + yearIn;
	
						if (dayOut <10)
							dayFormat = "0" + dayOut;
						else
							dayFormat = "" + dayOut;
						if (monthOut < 10)
							monthFormat = "0" + monthOut;
						else 
							monthFormat = "" + monthOut;
						check_Out = dayFormat + "/" + monthFormat + "/" + yearOut;
						
						check_In+=" 15:00";
						check_Out+=" 12:00";
						
						SimpleDateFormat dateFormatter=new SimpleDateFormat("dd/MM/yyyy HH:mm");
						Date dateIn=dateFormatter.parse(check_In);
						Date dateOut=dateFormatter.parse(check_Out);
						Calendar checkInDate = Calendar.getInstance();
						Calendar checkOutDate=Calendar.getInstance();
						checkInDate.setTime(dateIn);
						checkOutDate.setTime(dateOut);		
						
						ReservationStatus staus = ReservationStatus.CHECKED_IN;
						CheckInVia checkInVia = CheckInVia.WALK_IN;
						reservation = new Reservation(reservationIDCounter, room, numOfAdult, numOfChild, checkInDate, checkOutDate, staus, checkInVia, guest);	
						dataStore.getReservationHashtable().put(reservationIDCounter, reservation);
						dataStore.setReservationIDCounter(dataStore.getReservationIDCounter() + 1);
						
						room.setRoomStatus(RoomStatus.OCCUPIED);
						System.out.println("Check In Completed!!");
						System.out.println("Acknowledgement Receipt: ");
						reservation.printReservation();
					}
				}
				if(roomFound==false)
					throw new Exception("Room is currently unavailable.");
			}
			else
				throw new Exception("Guest detail not in database!");
		}catch(Exception e){System.out.println("Error: " + e.getMessage());}
	}

	/**
	 * Update details of reservation.
	 * 
	 * @param dataStore		Store reservation data
	 */
	public static void updateReservation(DataStore dataStore){
		int reservation_Num,choice;
		int dayIn,monthIn,yearIn,dayOut,monthOut,yearOut;
		String input,check_In,check_Out,dayFormat, monthFormat,newRoomNum;
		
		System.out.println("----Update Reservation----");
		try{
			SimpleDateFormat dateFormatter=new SimpleDateFormat("dd/MM/yyyy HH:mm");
			Date date;
			Calendar newCal=Calendar.getInstance();
			String[] output;
			int roomLvl;
			
			System.out.print("Enter reservation number: ");
			input = scn.nextLine();
			
			if(input.isEmpty())
				throw new Exception("Reservation is empty");
			
			reservation_Num=Integer.parseInt(input);
			
			if(dataStore.getReservationHashtable().containsKey(reservation_Num)){
				reservation=dataStore.getReservationHashtable().get(reservation_Num);
				
				input=null;
				
				System.out.println("(1) Edit Room Number");
				System.out.println("(2) Edit Number of Adult");
				System.out.println("(3) Edit Number of Children");
				System.out.println("(4) Edit Check-In Date");
				System.out.println("(5) Edit Check-Out Date");
				System.out.println("(6) Edit Reservation Status");
				System.out.print("Please enter your choice: ");
				input = scn.nextLine();
				
				if(input.isEmpty())
					throw new Exception("Invalid Option!");
				
				choice=Integer.parseInt(input);
				input=null;
				
				switch(choice){
				case 1://room num
					newRoomNum=validateRoomNumber(dataStore);
					
					if(newRoomNum.equals(""))
						return;
					
					Room currentRoom=reservation.getRoom();
					
					for(int i=0;i<dataStore.getRoomList().size();i++){
						room=dataStore.getRoomList().get(i);
						
						if(room.getRoomNumber().equals(newRoomNum) && room.getRoomStatus() == RoomStatus.VACANT){
							currentRoom.setRoomStatus(RoomStatus.VACANT);
							room.setRoomStatus(RoomStatus.RESERVED);
							reservation.setRoom(room);
						}
					}
					break;
				case 2://edit adult
					System.out.print("Enter Number of Adult: ");
					input = scn.nextLine();
					
					if(input.isEmpty())
						throw new Exception("Number of Adult Input is Empty!");
					
					int numOfAdult=Integer.parseInt(input);
					reservation.setNumOfAdult(numOfAdult);
					break;
				case 3://edit children
					System.out.print("Enter Number of Children: ");
					input = scn.nextLine();
					
					if(input.isEmpty())
						throw new Exception("Number of Children Input is Empty!");
					
					int numOfChild=Integer.parseInt(input);
					
					reservation.setNumOfChild(numOfChild);
					break;
				case 4://check in
					// Check in Year
					System.out.print("Enter Year of Check in (Availble to book from 2016 to 2017): ");
					input=scn.nextLine();
					if (input.isEmpty())
						throw new Exception("Blank Entry!");

					yearIn = Integer.parseInt(input);
					if (yearIn <= 2015 && yearIn >= 2018)
						throw new Exception("Reservation only availble between 2016 to 2017");
					
					// Check in Month
					System.out.print("Enter Month of Check in (i.e 01, 04, etc.) : ");
					input=scn.nextLine();
					if (input.isEmpty())
						throw new Exception("Blank Entry!");
					
					monthIn = Integer.parseInt(input);
					if (monthIn < 0 && monthIn > 12)
						throw new Exception("Invalid Month Entered!");	
					
					// Check in Day
					System.out.print("Enter Day of Check in (i.e 12, 31, etc.) : ");
					input=scn.nextLine();
					if (input.isEmpty())
						throw new Exception("Blank Entry!");
					
					dayIn = Integer.parseInt(input);
					if (dayIn < 0 && dayIn > 12)
						throw new Exception("Invalid Month Entered!");
					
					if (dayIn <10)
						dayFormat = "0" + dayIn;
					else
						dayFormat = "" + dayIn;
					if (monthIn < 10)
						monthFormat = "0" + monthIn;
					else 
						monthFormat = "" + monthIn;
					check_In = dayFormat + "/" + monthFormat + "/" + yearIn;

					Calendar calCheckOut=reservation.getCheckOut();
					
					dayOut=calCheckOut.get(Calendar.DAY_OF_MONTH);
					monthOut=calCheckOut.get(Calendar.MONTH)+1;
					yearOut=calCheckOut.get(Calendar.YEAR);
					
					if(validateDates(yearIn,monthIn,dayIn,yearOut,monthOut,dayOut)== false)
						throw new Exception ("Invalid Input!");
						
					check_In+=" 15:00";
					date=dateFormatter.parse(check_In);
					Calendar newCheckIn=Calendar.getInstance();
					
					newCheckIn.setTime(date);
					reservation.setCheckIn(newCheckIn);
					break;
				case 5://check out
					// Check out Year
					System.out.print("Enter Year of Check out (Availble to book from 2016 to 2018: ");
					input=scn.nextLine();
					if (input.isEmpty())
						throw new Exception("Blank Entry!");
					yearOut = Integer.parseInt(input);
					if (yearOut <= 2015 && yearOut >= 2019)
						throw new Exception("Reservation only availble between 2016 to 2018");
					
					// Check out Month
					System.out.print("Enter Month of Check out (i.e 01, 04, etc.) : ");
					input=scn.nextLine();
					if (input.isEmpty())
						throw new Exception("Blank Entry!");
					monthOut = Integer.parseInt(input);
					if (monthOut < 0 && monthOut > 12)
						throw new Exception("Invalid Month Entered!");	
					
					// Check out Day
					System.out.print("Enter Day of Check out (i.e 12, 31, etc.) : ");
					input=scn.nextLine();
					if (input.isEmpty())
						throw new Exception("Blank Entry!");
					dayOut = Integer.parseInt(input);
					if (dayOut < 0 && dayOut > 12)
						throw new Exception("Invalid Month Entered!");
					
					Calendar calCheckIn=reservation.getCheckIn();
					yearIn=calCheckIn.get(Calendar.YEAR);
					monthIn=calCheckIn.get(Calendar.MONTH)+1;
					dayIn=calCheckIn.get(Calendar.DAY_OF_MONTH);
					
					if (dayOut <10)
						dayFormat = "0" + dayOut;
					else
						dayFormat = "" + dayOut;
					if (monthOut < 10)
						monthFormat = "0" + monthOut;
					else 
						monthFormat = "" + monthOut;
					
					check_Out = dayFormat + "/" + monthFormat + "/" + yearOut;
					
					if(validateDates(yearIn,monthIn,dayIn,yearOut,monthOut,dayOut)== false)
						throw new Exception("Invalid Input!");
					
					check_Out+=" 12:00";
					System.out.println(check_Out);
					date=dateFormatter.parse(check_Out);
					Calendar newCheckOut=Calendar.getInstance();
					
					newCheckOut.setTime(date);
					reservation.setCheckOut(newCheckOut);
					break;
				case 6:
					//Reservation Status
					System.out.println("Select New Reservation Status: ");
					System.out.println("(1)Confirmed\t(2)In waitlist\t(3)Checked-In\t(4)Expired\t(5)Checked-Out");
					System.out.print("Please enter your choice: ");
					input = scn.nextLine();
					
					if(input.isEmpty())
						throw new Exception("Check-Out Input is Empty!");
					
					int newStatus=Integer.parseInt(input);
					
					if(newStatus == 1)
						reservation.setReservationStatus(ReservationStatus.CONFIRMED);
					else if(newStatus == 2)
						reservation.setReservationStatus(ReservationStatus.IN_WAITLIST);
					else if(newStatus == 3)
						reservation.setReservationStatus(ReservationStatus.CHECKED_IN);
					else if(newStatus == 4)
						reservation.setReservationStatus(ReservationStatus.EXPIRED);
					else if(newStatus == 5)
						reservation.setReservationStatus(ReservationStatus.CHECKED_OUT);
						
					break;
				default:
					throw new Exception("Invalid Entry!");
				}//switch close		
				reservation.printReservation();
			}
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}	
	}
	
	/**
	 * Delete reservation from data.
	 * 
	 * @param dataStore		Store reservation data
	 */
	public static void removeReservation(DataStore dataStore){
		int reservation_Num;
		
		try{
			if(dataStore.getReservationHashtable().isEmpty())
				System.out.println("No Reservation Records!");
			else
			{
				System.out.print("Enter Reservation Number: ");
				String input = scn.nextLine();
				if (input.isEmpty())
					throw new Exception("Empty Entry!");
				reservation_Num = Integer.parseInt(input);
				
				if(dataStore.getReservationHashtable().containsKey(reservation_Num)){
					dataStore.getReservationHashtable().remove(reservation_Num);
					System.out.println("Reservation Successfully Deleted!");
				}
				else
					System.out.println("No Such Reservation!");
			}
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		
	}

	/**
	 * Allow user to checkout from hotel and proceed to payment.
	 * 
	 * @param dataStore			Store reservation data
	 * @param reservationID		ID of reservation to be checked out   
	 */
	public static void checkOutReservation(DataStore dataStore, int reservationID){
		if (dataStore.getReservationHashtable().containsKey(reservationID)){
			reservation = dataStore.getReservationHashtable().get(reservationID);
			room = reservation.getRoom();
			if (reservation.getReservationStatus() == ReservationStatus.CHECKED_IN && room.getRoomStatus() == RoomStatus.OCCUPIED){
				reservation.setReservationStatus(ReservationStatus.CHECKED_OUT);
				room.setRoomStatus(RoomStatus.VACANT);
			}
		}
	}

	/**
	 * Print details of all reservation which are not expired.
	 * User can select to view walk in or reservation type of record.
	 * 
	 * @param dataStore		Store reservation data
	 */
	public static void printReservation(DataStore dataStore){
		//print all reservations
		String input1;
		int option;
		try{
			System.out.print("(1)Reservation\t(2)Walk In ");
			input1 = scn.nextLine();
			
			if(input1.isEmpty())
				throw new Exception("Input is empty");
			
			option=Integer.parseInt(input1);
			
			SimpleDateFormat dateFormatter=new SimpleDateFormat("dd/MM/yyyy");

			Enumeration<Integer> countReservation=dataStore.getReservationHashtable().keys();
			
			if(option==1){
				while(countReservation.hasMoreElements()){
					int key=countReservation.nextElement();

					reservation=dataStore.getReservationHashtable().get(key);
					
					if(reservation.getReservationStatus() != ReservationStatus.EXPIRED && reservation.getCheckInVia()==CheckInVia.RESERVATION){
						System.out.println("\n=====================Reservation Details=====================");
						System.out.println("Reservation Number		: "+key);
						System.out.println("Room Number			: "+reservation.getRoom().getRoomNumber());
						System.out.println("Room Rate			: "+reservation.getRoom().getRoomRate());		
						System.out.println("Guest Name			: "+reservation.getGuest().getName());
						System.out.println("Number of Adult			: "+reservation.getNumOfAdult());
						System.out.println("Number of Children		: "+reservation.getNumOfChild());
						System.out.println("Reservation status		: "+reservation.getReservationStatus());
						System.out.println("Check In Via			: "+reservation.getCheckInVia());
						countNumberofDays(dataStore, reservation.getReservationNumber());
						Date dateIn=reservation.getCheckIn().getTime();
						String strDateIn=dateFormatter.format(dateIn);
						
						Date dateOut=reservation.getCheckOut().getTime();
						String strDateOut=dateFormatter.format(dateOut);
						
						System.out.println("Check-In Date			: "+strDateIn);
						System.out.println("Check-Out Date			: "+strDateOut);
						System.out.println("=============================================================");
					}
				}
			}
			else if(option==2){
				while(countReservation.hasMoreElements()){
					int key=countReservation.nextElement();

					reservation=dataStore.getReservationHashtable().get(key);
					room = reservation.getRoom();
					if(reservation.getReservationStatus() != ReservationStatus.EXPIRED && reservation.getCheckInVia()==CheckInVia.WALK_IN){
						System.out.println("\n=====================Reservation Details=====================");
						System.out.println("Reservation Number		: "+key);
						System.out.println("Room Number			: "+reservation.getRoom().getRoomNumber());
						System.out.println("Room Rate			: "+reservation.getRoom().getRoomRate());		
						System.out.println("Guest Name			: "+reservation.getGuest().getName());
						System.out.println("Number of Adult			: "+reservation.getNumOfAdult());
						System.out.println("Number of Children		: "+reservation.getNumOfChild());
						System.out.println("Reservation status		: "+reservation.getReservationStatus());
						countNumberofDays(dataStore, reservation.getReservationNumber());
						System.out.println("Check In Via			: "+reservation.getCheckInVia());

						Date dateIn=reservation.getCheckIn().getTime();
						String strDateIn=dateFormatter.format(dateIn);
						
						Date dateOut=reservation.getCheckOut().getTime();
						String strDateOut=dateFormatter.format(dateOut);
						
						System.out.println("Check-In Date			: "+strDateIn);
						System.out.println("Check-Out Date			: "+strDateOut);
						System.out.println("=============================================================");
					}
				}
			}
			else
				throw new Exception("Invalid Option!");	
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
	}
	}
	
	/**
	 * Check if user input dates are of correct format.
	 * 
	 * @param yearIn	Year of check in.
	 * @param monthIn	Month of check in.
	 * @param dayIn		Day of check in.
	 * @param yearOut	Year of check out.
	 * @param monthOut	Month of check out.
	 * @param dayOut	Day of check out.
	 * 
	 * @return true if date is of correct format; false if otherwise.
	 */
	private static boolean validateDates(int yearIn, int monthIn, int dayIn, int yearOut, int monthOut, int dayOut) {
		// validate date in is always smaller than year in
		if (yearOut <yearIn){
			System.out.println("Error: Year Out is before Year in");
			return false;
		}
		else if ((yearOut == yearIn) && (monthOut < monthIn)){
			System.out.println("Error: Month Out is before Month in!");
			return false;
		}
		else if ((yearOut == yearIn) && (monthOut == monthIn) && (dayOut < dayIn)){
			System.out.println("Error: Day Out is before Day in");
			return false;
		}

		// validate dateOut for 28/29/30/31 days
		if (monthIn == 4 || monthIn == 6 || monthIn == 9 || monthIn == 11){
			if (dayIn == 31){
				System.out.println("Error: There is no 31st on April, June, September & November!");
				return false;
			}
		}
		if(yearIn == 2016 && monthIn == 2 && dayIn >29){
			System.out.println("Error: Feb 2016 only have 29 days");
			return false;
		}
		else if (yearIn != 2016 && monthIn == 2 && dayIn >28){
			System.out.println("Error: Feb only have 28 days");
			return false;
		}
		
		// validate dateOut for 28/29/30/31 days
		if (monthOut == 4 || monthOut == 6 || monthOut == 9 || monthOut == 11){
			if (dayOut == 31){
				System.out.println("Error: There is no 31st on April, June, September & November!");
				return false;
			}
		}
		if(yearOut == 2016 && monthOut == 2 && dayOut >29){
			System.out.println("Error: Feb 2016 only have 29 days");
			return false;
		}
		else if (yearOut != 2016 && monthOut == 2 && dayOut >28){
			System.out.println("Error: Feb only have 28 days");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Calculate number of weekend and weekday from check in to check out date.
	 * 
	 * @param dataStore			Store reservation data.
	 * @param reservationID		ID of reservation
	 */
	public static void countNumberofDays(DataStore dataStore, int reservationID){
		int total = 0;
		reservation = dataStore.getReservationHashtable().get(reservationID);
		Calendar c_in = reservation.getCheckIn();
		Calendar c_out = reservation.getCheckOut();
		Calendar c_inOriginal = (Calendar) reservation.getCheckIn().clone();	
		reservation = dataStore.getReservationHashtable().get(reservationID);
		room = reservation.getRoom();
		
		int weekend = 0, weekday = 0;
	    do{
	    	int day=c_in.get(Calendar.DAY_OF_WEEK);
	    	//weekend
	    	total++;
	    	if(day==1||day==7)
	    	{
	    	//  number of days to add	
	    		weekend++;
	    	}
	    	else 
	    	{
	    		//weekday
	    		weekday++;
	    	}
    		// increment day by 1
    		c_in.add(Calendar.DATE, 1);
	    	// loop until checkinday matches checkoutday
	    	} while(!(c_out.get(Calendar.DAY_OF_MONTH) ==  c_in.get(Calendar.DAY_OF_MONTH)) && !(c_out.get(Calendar.DAY_OF_WEEK) ==  c_in.get(Calendar.DAY_OF_WEEK))&& !(c_out.get(Calendar.DAY_OF_YEAR) ==  c_in.get(Calendar.DAY_OF_YEAR)));
	    reservation.setCheckIn(c_inOriginal);
	    
	    System.out.println("Total amount of days stayed	: " + total);
	    System.out.println("No. of weekdays stayed		: " + weekday);	
	    System.out.println("No. of weekends stayed 		: " + weekend + " (additional of $30/night)");   	
	}
	
	/**
	 * Check if room level input by user is of correct format.
	 * 
	 * @param dataStore		Store room object
	 * @param lvl			Floor level of room
	 * 
	 * @return true if room number is correct format; false if otherwise
	 */
	private static boolean validateRoomLvl(DataStore dataStore, int lvl){
		int maxLvl = 2;	
		for(int i=0;i<dataStore.getRoomList().size();i++){
			room = dataStore.getRoomList().get(i);
			String number = room.getRoomNumber();
			
			// Find max lvl - current max level + 1;
			StringTokenizer st = new StringTokenizer(number, "-");
			while (st.hasMoreElements()){
				int currentLvl = Integer.parseInt(st.nextToken());
				st.nextToken();
				if (maxLvl < currentLvl){
					maxLvl = currentLvl;
					maxLvl+=1;
				}
			}				
		}
		maxLevel = maxLvl;
		if (lvl >2 && lvl <= maxLevel)
			return false;
		else
			return true;
		
	}

	/**
	 * Calculate cost of room.
	 * 
	 * @param dataStore			Store room data.
	 * @param roomID			ID of room.
	 * @param reservationID		ID of reservation.
	 * 
	 * @return	Total cost of room.
	 */
	public static double computeRoomTotal(DataStore dataStore, String roomID,int reservationID)
	{
		double total = 0.0;
		reservation = dataStore.getReservationHashtable().get(reservationID);
		double rate = 0.0;
		double weekendCharge = 30.0;
		Calendar c_in = reservation.getCheckIn();
		Calendar c_out = reservation.getCheckOut();
		Calendar c_inOriginal = (Calendar) reservation.getCheckIn().clone();
		for(int i = 0; i < dataStore.getRoomList().size(); i++)
		{
			room = dataStore.getRoomList().get(i);
			if(roomID.equals(room.getRoomNumber()))
			{
				rate = room.getRoomRate();
			}
		}	
		reservation = dataStore.getReservationHashtable().get(reservationID);
		room = reservation.getRoom();
		
	    do{
	    	int day=c_in.get(Calendar.DAY_OF_WEEK);
	    	//weekend
	    	if(day==1||day==7)
	    	//  number of days to add	
	    		total += rate + weekendCharge;
	    	else
	    		//weekday
	    		total += rate;

    		// increment day by 1
    		c_in.add(Calendar.DATE, 1);
	    	// loop until checkinday matches checkoutday
	    	} while(!(c_out.get(Calendar.DAY_OF_MONTH) ==  c_in.get(Calendar.DAY_OF_MONTH)) && !(c_out.get(Calendar.DAY_OF_WEEK) ==  c_in.get(Calendar.DAY_OF_WEEK))&& !(c_out.get(Calendar.DAY_OF_YEAR) ==  c_in.get(Calendar.DAY_OF_YEAR)));
	    reservation.setCheckIn(c_inOriginal);	    
	    return total;	
	}
	
	/**
	 * Display total cost of room.
	 * 
	 * @param dataStore			Store room data.
	 * @param roomID			ID of room.
	 * @param reservationID		ID or reservation.
	 */
	public static void printRoomBill(DataStore dataStore, String roomID, int reservationID)
	{
		for(int i = 0; i < dataStore.getRoomList().size(); i++)
		{
		room = dataStore.getRoomList().get(i);
		if(roomID.equals(room.getRoomNumber()))
		{
			System.out.println("Room Number: " + room.getRoomNumber());
			System.out.println("Room Type: " + room.getRoomType());
			System.out.println("Room Rate: " + "$" +room.getRoomRate());
			countNumberofDays(dataStore, reservationID);
		}
		}
	}
	
	/**
	 * Check if room number input by user is of correct format.
	 * 
	 * @param dataStore		Store room data
	 * 
	 * @return roomnumber
	 */
	public static String validateRoomNumber(DataStore dataStore){
		String input,roomNum="";
		try{	
		int roomLvl,roomDigit;
		String castLvlDigit,castRoomDigit;
		System.out.print("Enter Room Level: ");
		input = scn.nextLine();
		if(input.isEmpty())
			throw new Exception("Empty Entry");
		roomLvl=Integer.parseInt(input);
		
		if (roomMgr.validateRoomLvl(dataStore, roomLvl))
			throw new Exception("Room level not in range!");
		
		if(roomLvl<10)
			castLvlDigit = "0" + roomLvl;
		else 
			castLvlDigit = ""+ roomLvl;
		System.out.print("Enter Room Number: ");
		input = scn.nextLine();
		if(input.isEmpty())
			throw new Exception("Empty Entry");
		roomDigit=Integer.parseInt(input);	
		
		if (roomDigit < 10)
			castRoomDigit = "0" + roomDigit;
		else 
			castRoomDigit = ""+ roomDigit;
		
		roomNum = castLvlDigit + "-" + castRoomDigit;
		if(!roomMgr.validateRoomNumber(dataStore, roomNum)){
			roomNum="";
			throw new Exception("Room number not in range!");
		}
		return roomNum;
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
		return roomNum;
	}
	
}
