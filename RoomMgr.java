/**
 Represents the Room Manager class that let user change the application setting that let user perform change room settings

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE LAM WEN QI
 @version 1.0
 @since 2016-03-31
*/
package assignment.util;

import java.util.Enumeration;
import java.util.Scanner;

import assignment.model.DataStore;
import assignment.model.Guest;
import assignment.model.Reservation;
import assignment.model.Reservation.ReservationStatus;
import assignment.model.Room;
import assignment.model.Room.BedType;
import assignment.model.Room.FacingSea;
import assignment.model.Room.RoomStatus;
import assignment.model.Room.RoomType;
import assignment.model.Room.SmokingStatus;
import assignment.model.Room.WifiStatus;
import java.util.StringTokenizer;

public class RoomMgr{
	/**
	 * Scanner scn to take in user input
	 */
	private static Scanner scn = new Scanner(System.in);
	
	/**
	 * Store user input.
	 */
	private static int choice;
	
	/**
	 * Store room object.
	 */
	private static Room room = null;
	
	/**
	 * Max number of rooms per floor.
	 */
	private static int MAX_ROOMNUMBER = 8;
	
	/**
	 * Highest storey of hotel.
	 */
	private static int maxLevel = 2;
	
	/**
	 * Reservation details of reservation which reserved the room.
	 */
	private static Reservation reservation=null;
	
	/**
	 * Guest who reserved or check-in the room.
	 */
	private static Guest guest=null;
	
	/**
	 * Current max floor of hotel 
	 * 
	 * @return max floor of hotel 
	 */
	public int getMaxLevel(){return maxLevel;}
	
	/**
	 * Create new room for application
	 * 
	 * @param dataStore		Store room data
	 */
	public static void createRoom(DataStore dataStore){
		
		int lvl, roomNo;
		String newRoomNo;
		String input;
		
		try{
			System.out.print("\nEnter New Room Level: ");
			input = scn.nextLine();
			if(input.isEmpty())
				throw new Exception("Empty Entry");
			lvl=Integer.parseInt(input);
		
			// validate lvl within range 
			if (validateRoomLvl(dataStore, lvl))
				throw new Exception("Room Lvl exceeded range, please enter within 2 to " + maxLevel);
			
			System.out.print("\nEnter New Room Number: ");
			input = scn.nextLine();
			if(input.isEmpty())
				throw new Exception("Empty Entry");
			roomNo=Integer.parseInt(input);
			
			// validate room within range
			if (roomNo <1 || roomNo > MAX_ROOMNUMBER)
				throw new Exception("Room number exceeded range, please enter within 1 to 8");
			
			String castLvlDigit;
			if (lvl < 10)
				castLvlDigit = "0" + lvl;
			else 
				castLvlDigit = ""+ lvl;
			
			newRoomNo = castLvlDigit + "-0" + roomNo;
			if(validateRoomNumber(dataStore, newRoomNo))
				throw new Exception("Room already in DB! Can't be created, please update room instead");
			
			//Create room type
			RoomType roomType = RoomType.NULL;
			System.out.println("\nUpdate room type");
			System.out.println("(1)Single (2)Double (3)Twin Sharing (4)Deluxe (5)VIP Suite");
			System.out.print("Please enter your choice: ");
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty entry!");
			choice = Integer.parseInt(input);

			
			if(choice<1 || choice>5 )
				throw new Exception("Error: Input must be between 1 to 5 for room type.");	
			else if(choice == 1)
				roomType = RoomType.SINGLE;
			else if(choice == 2)
				roomType = RoomType.DOUBLE;
			else if(choice == 3)
				roomType = RoomType.TWIN_SHARING;
			else if(choice == 4)
				roomType = RoomType.DELUXE;
			else if(choice == 5)
				roomType = RoomType.VIP_SUITE;
			
			//Create bed type
			BedType bedType = BedType.NULL;
			System.out.println("\nUpdate bed type");
			System.out.println("(1)Single Bed  (2)Double Bed (3)Master Bed");
			System.out.print("Please enter your choice: ");
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty entry!");
			choice = Integer.parseInt(input);
			
			if(choice<1 || choice>3)
				throw new Exception("Error: Input must be between 1 to 3 for bed type.");
			else if(choice == 1)
				bedType = BedType.SINGLE;
			else if(choice == 2)
				bedType = BedType.DOUBLE;
			else if(choice == 3)
				bedType = BedType.MASTER;
			
			//Create room availability
			RoomStatus roomStatus = RoomStatus.NULL;
			System.out.println("\nUpdate room status");
			System.out.println("(1)Vacant (2)Occupied (3)Reserved (4)Under Maintenance");
			System.out.print("Please enter your choice: ");
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty entry!");
			choice = Integer.parseInt(input);
			
			if(choice<1 || choice>4)
				throw new Exception("Error: Input must be between 1 to 4 for room availability.");
			else if(choice == 1)
				roomStatus = RoomStatus.VACANT;
			else if(choice == 2)
				roomStatus = RoomStatus.OCCUPIED;
			else if(choice == 3)
				roomStatus = RoomStatus.RESERVED;
			else if(choice == 4)
				roomStatus = RoomStatus.UNDER_MAINTENANCE;
			
			//Create facing sea
			FacingSea faceStatus = FacingSea.NULL;
			System.out.println("\nChange sea view");
			System.out.println("(1)Facing Sea (2)Non-Facing Sea");
			System.out.print("Please enter your choice: ");
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty entry!");
			choice = Integer.parseInt(input);
			
			if(choice<1 || choice>2)
				throw new Exception("Error: Input must be between 1 to 2 for facing sea status.");
			else if(choice==1)
				faceStatus = FacingSea.FACING_SEA_VIEW;
			else if(choice==2)
				faceStatus = FacingSea.FACING_SEA_VIEW;
			
			//Create WIFI status
			WifiStatus wifiStatus = WifiStatus.NULL;
			System.out.println("\nUpdate WIFI option");
			System.out.println("(1)Enabled (2)Disabled");
			System.out.print("Please enter your choice: ");
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty entry!");
			choice = Integer.parseInt(input);
			
			if(choice<1 || choice>2)
				throw new Exception("Error: Input must be between 1 to 2 for WIFI status.");
			else if(choice == 1)
				wifiStatus = WifiStatus.ENABLED;
			else if(choice == 2)
				wifiStatus = WifiStatus.DISABLED;
			
			//Create smokingStatus
			SmokingStatus smokingStatus = SmokingStatus.NULL;
			System.out.println("\nUpdate smoking status");
			System.out.println("(1)Non-smoking room (2)Smoking room");
			System.out.print("Please enter your choice: ");
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty entry!");
			choice = Integer.parseInt(input);
			
			if(choice<1 || choice>2)
				throw new Exception("Error: Input must be between 1 to 2 for smoking status.");
			else if(choice == 1)
				smokingStatus = SmokingStatus.NON_SMOKING_ROOM;
			else if(choice == 2)
				smokingStatus = SmokingStatus.SMOKING_ROOM;
			
			double roomRate = findRoomRate(roomType, bedType, wifiStatus, faceStatus);
			
			room = new Room(newRoomNo, roomType, bedType, roomStatus, wifiStatus, faceStatus, smokingStatus, roomRate);
			dataStore.getRoomList().add(room);
			System.out.println("New Room Added! Below is the new room information:");
			room.printRoomDetail();
		}catch (Exception e){
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	/**
	 * Update room detail
	 * 
	 * @param dataStore		Store room data
	 */
	public static void updateRoom(DataStore dataStore){
		
		try{
			int roomIndex = -1;
			
			System.out.print("Enter room number(Format: xx-xx): ");
			String input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty Entry!");
			
			for(int i=0;i<dataStore.getRoomList().size();i++){
				room = dataStore.getRoomList().get(i);
				if(room.getRoomNumber().equals(input))
					roomIndex = i;
			}
			
			if (roomIndex != -1){
				room = dataStore.getRoomList().get(roomIndex);;
				// (1)Print room info
				room.printRoomDetail();
				
				// (2)Update room
				int choice;
				System.out.println("-- Update Room--");
				System.out.println("(1) Update Room Type");
				System.out.println("(2) Update Bed Type");
				System.out.println("(3) Update Room Status");
				System.out.println("(4) Update Face Sea Status");
				System.out.println("(5) Update Wifi Status");
				System.out.println("(6) Update Smoking Status");
				System.out.println("(7) Exit Update");
				System.out.print("Please Select an Option: ");
				input = scn.nextLine();
				
				if (input.isEmpty())
					throw new Exception("Empty Entry!");
				
				choice = Integer.parseInt(input);
				
				switch (choice) {
				case 1:
					// update room type
					RoomType roomType = RoomType.NULL;
					System.out.println("\nUpdate room type");
					System.out.println("(1)Single (2)Double (3)Twin Sharing (4)Deluxe (5)VIP Suite");
					System.out.print("Please enter your choice: ");
					input = scn.nextLine();
					if (input.isEmpty())
						throw new Exception("Empty entry!");
					choice = Integer.parseInt(input);
					
					if(choice<1 || choice>5 )
						throw new Exception("Error: Input must be between 1 to 5 for room type.");	
					else if(choice == 1)
						roomType = RoomType.SINGLE;
					else if(choice == 2)
						roomType = RoomType.DOUBLE;
					else if(choice == 3)
						roomType = RoomType.TWIN_SHARING;
					else if(choice == 4)
						roomType = RoomType.DELUXE;
					else if(choice == 5)
						roomType = RoomType.VIP_SUITE;		
					room.setRoomType(roomType);
					break;
				case 2:
					//Update bed type
					BedType bedType = BedType.NULL;
					System.out.println("\nUpdate bed type");
					System.out.println("(1)Single Bed  (2)Double Bed (3)Master Bed");
					System.out.print("Please enter your choice: ");
					input = scn.nextLine();
					if (input.isEmpty())
						throw new Exception("Empty entry!");
					choice = Integer.parseInt(input);
					
					if(choice<1 || choice>3)
						throw new Exception("Error: Input must be between 1 to 3 for bed type.");
					else if(choice == 1)
						bedType = BedType.SINGLE;
					else if(choice == 2)
						bedType = BedType.DOUBLE;
					else if(choice == 3)
						bedType = BedType.MASTER;
					room.setBedType(bedType);
					break;
				case 3:
					//Create room availability
					RoomStatus roomStatus = RoomStatus.NULL;
					System.out.println("\nUpdate room status");
					System.out.println("(1)Vacant (2)Occupied (3)Reserved (4)Under Maintenance");
					System.out.print("Please enter your choice: ");
					input = scn.nextLine();
					if (input.isEmpty())
						throw new Exception("Empty entry!");
					choice = Integer.parseInt(input);
					
					if(choice<1 || choice>4)
						throw new Exception("Error: Input must be between 1 to 4 for room status.");
					else if(choice == 1)
						roomStatus = RoomStatus.VACANT;
					else if(choice == 2)
						roomStatus = RoomStatus.OCCUPIED;
					else if(choice == 3)
						roomStatus = RoomStatus.RESERVED;
					else if(choice == 4)
						roomStatus = RoomStatus.UNDER_MAINTENANCE;
					room.setRoomStatus(roomStatus);
					break;
				case 4:
					//Update facing sea
					FacingSea faceStatus = FacingSea.NULL;
					System.out.println("\nChange sea view");
					System.out.println("(1)Facing Sea (2)Non-Facing Sea");
					System.out.print("Please enter your choice: ");
					input = scn.nextLine();
					if (input.isEmpty())
						throw new Exception("Empty entry!");
					choice = Integer.parseInt(input);
					
					if(choice<1 || choice>2)
						throw new Exception("Error: Input must be between 1 to 2 for facing sea status.");
					else if(choice==1)
						faceStatus = FacingSea.FACING_SEA_VIEW;
					else if(choice==2)
						faceStatus = FacingSea.FACING_SEA_VIEW;
					
					room.setFacingSea(faceStatus);
					
					break;
				case 5:
					//update WIFI status
					WifiStatus wifiStatus = WifiStatus.NULL;
					System.out.println("\nUpdate WIFI option");
					System.out.println("(1)Enabled (2)Disabled");
					System.out.print("Please enter your choice: ");
					input = scn.nextLine();
					if (input.isEmpty())
						throw new Exception("Empty entry!");
					choice = Integer.parseInt(input);
					
					if(choice<1 || choice>2)
						throw new Exception("Error: Input must be between 1 to 2 for WIFI status.");
					else if(choice == 1)
						wifiStatus = WifiStatus.ENABLED;
					else if(choice == 2)
						wifiStatus = WifiStatus.DISABLED;
					
					room.setWifiStatus(wifiStatus);
					
					break;
				case 6:
					//update smokingStatus
					SmokingStatus smokingStatus = SmokingStatus.NULL;
					System.out.println("\nUpdate smoking status");
					System.out.println("(1)Non-smoking room (2)Smoking room");
					System.out.print("Please enter your choice: ");
					input = scn.nextLine();
					if (input.isEmpty())
						throw new Exception("Empty entry!");
					choice = Integer.parseInt(input);
					
					if(choice<1 || choice>2)
						throw new Exception("Error: Input must be between 1 to 2 for smoking status.");
					else if(choice == 1)
						smokingStatus = SmokingStatus.NON_SMOKING_ROOM;
					else if(choice == 2)
						smokingStatus = SmokingStatus.SMOKING_ROOM;	
					
					room.setSmokingStatus(smokingStatus);
					break;
				case 7:
					break;
				default:
					throw new Exception("Invalid Entry!");
				}
				System.out.println("Updated Room Rate as such");
				room.setRoomRate(findRoomRate(room.getRoomType(), room.getBedType(),room.getWifiStatus(), room.getFacingSea()));
				room.printRoomDetail();
				
				System.out.println("Room " +room.getRoomNumber()+" Updated!");
			}
			else
				throw new Exception("Room not found");
		} catch(Exception e){
			System.out.println("Error: "+ e.getMessage());
		}
	}	

	/**
	 * Choose type of room check in
	 * 
	 * @param dataStore		Store room data
	 */
	public static void checkIn(DataStore dataStore){
		String input;
		int option=0;
		System.out.println("Select Type of Check-In");
		try{
			System.out.println("(1)Walk in\t(2)Reservation ");
			System.out.print("Please enter your choice: ");
			input=scn.nextLine();
			
			if (input.isEmpty())
				throw new Exception("Blank Entry!");
			
			option=Integer.parseInt(input);
			
			if(option==1){
				System.out.println("(1)Existing Customer\t(2)New Customer: ");
				System.out.print("Please enter your choice: ");
				input=scn.nextLine();
				
				if (input.isEmpty())
					throw new Exception("Blank Entry!");
				
				option=Integer.parseInt(input);
				
				if(option == 1){
					
				System.out.print("Enter Guest ID: ");
				input=scn.nextLine();
				if (input.isEmpty())
					throw new Exception("Guest ID Entry is missing!");
					
				if(dataStore.getGuestHashtable().containsKey(input)){
					guest=dataStore.getGuestHashtable().get(input);
					ReservationMgr.createWalkInReservation(dataStore,guest);		
				}
				else
					throw new Exception("Guest record not in database!");
				}
				else if(option == 2){
					int i=0;
					
					boolean flag = GuestMgr.createGuest(dataStore);
					if (flag == false)
						throw new Exception("Invalid creation!");
					
					int lastGuest=dataStore.getGuestHashtable().size();
					
					Enumeration<String> guestDetail = dataStore.getGuestHashtable().keys();
					
					while(guestDetail.hasMoreElements()){
						i++;
						String guestID=guestDetail.nextElement();
						if(i==lastGuest){
							guest=dataStore.getGuestHashtable().get(guestDetail);
						}	
					}
					ReservationMgr.createWalkInReservation(dataStore,guest);		
				}
				else 
					throw new Exception("Invalid Option!");
			}//end of walk in
			else if(option==2){
				System.out.print("Enter Reservation Number: ");
				input=scn.nextLine();
				
				if (input.isEmpty())
					throw new Exception("Blank Entry!");
				
				int reservation_Num=Integer.parseInt(input);
				
				System.out.print("Enter Customer Identity: ");
				input=scn.nextLine();
				
				if (input.isEmpty())
					throw new Exception("Blank Entry!");
				
				if(dataStore.getReservationHashtable().containsKey(reservation_Num)){
					reservation=dataStore.getReservationHashtable().get(reservation_Num);
					
					if(!reservation.checkExpiry()){
						if(reservation.getGuest().getIdentity().equals(input)){	
							reservation.setReservationStatus(ReservationStatus.CHECKED_IN);
							room=reservation.getRoom();
							room.setRoomStatus(RoomStatus.OCCUPIED);
							System.out.println("Check-In successfully completed!");
						}	
					}
					else
						throw new Exception("Reservation has expired!");
				}
				else
					throw new Exception("ReservationID not in DB!");
			}
		}catch(Exception e){System.out.println("Error: " + e.getMessage());}
	}
									
	/**
	 * Display occupancy breakdown by:(1)Room Type	(2)Room Status 
	 * 
	 * @param dataStore		Store room data
	 */
	public static void printRoomStatisticReport(DataStore dataStore){
		String input;
		int option;
		int countAllSingle=0,countAllDouble=0,countAllTwin=0,countAllDeluxe=0,countAllVIP=0;
		int countSingle=0,countDouble=0,countTwin=0,countDeluxe=0,countVIP=0;
		String roomNumSingle="",roomNumDouble="",roomNumDeluxe="",roomNumVIP="",roomNumTwin="";
		String roomNumVacant="",roomNumOccupied="",roomNumReserved="",roomNumMaintenance="";
		
		System.out.println("View statistic report by");
		System.out.println("(1)Room type occupancy rate\t(2)Room status");
		
		try{
			System.out.print("Please enter your choice: ");
			input=scn.nextLine();
			
			if (input.isEmpty())
				throw new Exception("Blank Entry!");
			
			option=Integer.parseInt(input);
			
			if(option ==1){
				for(int i=0;i<dataStore.getRoomList().size();i++){
					room=dataStore.getRoomList().get(i);
					
					if(room.getRoomType() == RoomType.SINGLE)
						countAllSingle++;
					else if(room.getRoomType() == RoomType.DOUBLE)
						countAllDouble++;
					else if(room.getRoomType() == RoomType.DELUXE)
						countAllDeluxe++;
					else if(room.getRoomType() == RoomType.TWIN_SHARING)
						countAllTwin++;
					else if(room.getRoomType() == RoomType.VIP_SUITE)
						countAllVIP++;
					
					if(room.getRoomStatus()== RoomStatus.VACANT){
						if(room.getRoomType()==RoomType.SINGLE){
							countSingle++;
							roomNumSingle=roomNumSingle + room.getRoomNumber()+",";
						}
						else if(room.getRoomType() == RoomType.DOUBLE){
							countDouble++;
							roomNumDouble=roomNumDouble + room.getRoomNumber()+",";
						}
						else if (room.getRoomType() == RoomType.DELUXE){
							countDeluxe++;
							roomNumDeluxe=roomNumDeluxe + room.getRoomNumber()+",";
						}
						else if(room.getRoomType() == RoomType.TWIN_SHARING){
							countTwin++;
							roomNumTwin=roomNumTwin + room.getRoomNumber() +",";
						}	
						else if(room.getRoomType() == RoomType.VIP_SUITE){
							countVIP++;
							roomNumVIP=roomNumVIP + room.getRoomNumber()+",";
						}
					}
				}
				
				System.out.println("\nSingle: Number: " + countSingle + " out of "+countAllSingle);
				System.out.println("Rooms: " + roomNumSingle);
				
				System.out.println("\nDouble: Number: " + countDouble + " out of "+countAllDouble);
				System.out.println("Rooms: " + roomNumDouble);
				
				System.out.println("\nDeluxe: Number: " + countDeluxe + " out of "+countAllDeluxe);
				System.out.println("Rooms: " + roomNumDeluxe);
				
				System.out.println("\nTwin Sharing: Number: " + countTwin + " out of "+countAllTwin);
				System.out.println("Rooms: " + roomNumTwin);
				
				System.out.println("\nVIP Suite: Number: " + countVIP + " out of "+countAllVIP);
				System.out.println("Rooms: " + roomNumVIP);
				
			}
			else if(option ==2){
				for(int i=0;i<dataStore.getRoomList().size();i++){
					room=dataStore.getRoomList().get(i);
					
					if(room.getRoomStatus()== RoomStatus.VACANT){
						roomNumVacant=roomNumVacant+room.getRoomNumber()+",";
					}
					else if(room.getRoomStatus() == RoomStatus.OCCUPIED){
						roomNumOccupied=roomNumOccupied+room.getRoomNumber()+",";
					}
					else if(room.getRoomStatus() == RoomStatus.RESERVED){
						roomNumReserved=roomNumReserved+room.getRoomNumber()+",";
					}
					else if(room.getRoomStatus() == RoomStatus.UNDER_MAINTENANCE){
						roomNumMaintenance=roomNumMaintenance+room.getRoomNumber()+",";
					}
				}
				
				System.out.println("\nVacant Rooms");
				System.out.println("Rooms: " + roomNumVacant);
				System.out.println("\nOccupied Rooms");
				System.out.println("Rooms: " + roomNumOccupied);
				System.out.println("\nReserved Rooms");
				System.out.println("Rooms: " + roomNumReserved);
				System.out.println("\nUnder Maintenance Rooms");
				System.out.println("Rooms: " + roomNumMaintenance);	
			}
		}catch(Exception e){System.out.println("Error: " + e.getMessage());}
	}

	/**
	 * Check if room is available.
	 * 
	 * @param dataStore		Store room data
	 */
	public static void checkAvailability(DataStore dataStore){
		String input;
		try{
			System.out.print("Enter Room Number(Format: xx-xx): ");
			input=null;
			input=scn.nextLine();
			
			if (input.isEmpty())
				throw new Exception("Blank Entry!");
			
			for(int i=0;i<dataStore.getRoomList().size();i++){
				room=dataStore.getRoomList().get(i);
				
				if(room.getRoomNumber().equals(input))
					System.out.println("Room "+ room.getRoomNumber()+" is currently "+room.getRoomStatus()+".");
			}		
		}
		catch(Exception e){
			System.out.println("Error: "+ e.getMessage());}
	}
	
	/**
	 * Calculate cost of room base on room attribute values
	 * 
	 * @param roomType		Type of room
	 * @param bedType		Type of bed in room
	 * @param wifiStatus	WIFI status of room
	 * @param faceStatus	Facing sea status of room
	 * 
	 * @return Room rate of room
	 */
	private static double findRoomRate(RoomType roomType, BedType bedType, WifiStatus wifiStatus,
			FacingSea faceStatus) {
		double rate = 0;
		
		if(roomType.equals(RoomType.SINGLE))
			rate += 50;
		else if(roomType.equals(RoomType.DOUBLE))
			rate+=100;
		else if(roomType.equals(RoomType.TWIN_SHARING))
			rate+=80;
		else if(roomType.equals(RoomType.DELUXE))
			rate+=120;
		else if(roomType.equals(RoomType.VIP_SUITE))
			rate+=150;
		
		if (bedType.equals(BedType.SINGLE))
			rate+=30;
		else if (bedType.equals(BedType.DOUBLE))
			rate +=50;
		else if (bedType.equals(BedType.MASTER))
			rate+=30;

		if (wifiStatus.equals(WifiStatus.ENABLED))
			rate +=10;
		
		if(faceStatus.equals(FacingSea.FACING_SEA_VIEW))
			rate+=30;
		
		System.out.println("The Rate for this room will be : " + rate);
		return rate;
	}

	/**
	 * Check if room number is of correct format.
	 * 
	 * @param dataStore		Store room object
	 * @param lvl			Floor level of room
	 * 
	 * @return		False if within range;@return True if incorrect						
	 */
	public static boolean validateRoomLvl(DataStore dataStore, int lvl){	
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

		if (lvl >=2 && lvl <= maxLevel)
			return false;
		else
			return true;
	}

	/**
	 * Check if room number is of correct format
	 * 
	 * @param dataStore		Store room data
	 * @param newRoomNo		Room number of room
	 * 
	 * @return true if room number is ;@return false 
	 */
	public static boolean validateRoomNumber(DataStore dataStore, String newRoomNo){
		for(int i=0;i<dataStore.getRoomList().size();i++){
			room = dataStore.getRoomList().get(i);
			String number = room.getRoomNumber();
			
			if(number.equals(newRoomNo))
				return true;
		}
		return false;
	}
	
	/**
	 * Remove room from room list. 
	 * 
	 * @param dataStore		Store room data
	 * @param roomID		Room number of room
	 */
	public static void removeRoom(DataStore dataStore, String roomID){
		
		for(int i = 0; i < dataStore.getRoomList().size(); i++)
		{
			room = dataStore.getRoomList().get(i);
			if(roomID.equals(room.getRoomNumber()))
			{
				dataStore.getRoomList().remove(i);
			}
		}	
		System.out.print("Room Removed: ");	
	}

	}

