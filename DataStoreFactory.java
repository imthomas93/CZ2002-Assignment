/**
 DataStore Initialization Class

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE  LAM WEN QI
 @version 1.0
 @since 2016-03-15
*/

package assignment.util.datastore;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.StringTokenizer;
import assignment.model.DataStore;
import assignment.model.Guest;
import assignment.model.CreditCard;
import assignment.model.Room;
import assignment.model.Reservation;
import assignment.model.Payment;
import assignment.model.Menu;
import assignment.model.Order;

// import enum datatype
import assignment.model.CreditCard.Month;
import assignment.model.Room.RoomType;
import assignment.model.Room.BedType;
import assignment.model.Room.FacingSea;
import assignment.model.Room.RoomStatus;
import assignment.model.Room.SmokingStatus;
import assignment.model.Room.WifiStatus;
import assignment.model.Reservation.CheckInVia;
import assignment.model.Reservation.ReservationStatus;
import assignment.model.Payment.PaymentMode;
/**
* Creates a new DataStore file
* 
* @param filename         : filename of the data store file
*/
public class DataStoreFactory {
	
	private static Hashtable<String, Guest> guestHashtable = new Hashtable<>();
	private static Guest guest = null;
	private static CreditCard cc = null;
	private static Room room;
	private static ArrayList<Room> roomList  = new ArrayList<>();
	private static Hashtable<Integer, Reservation> reservationHashtable = new Hashtable<>();
	private static Reservation reservation;
	private static int reservationIDCounter;
	private static int paymentIDCounter;
	private static Hashtable<Integer, Payment> paymentHashtable = new Hashtable<>();
	private static ArrayList<Menu> menuList  = new ArrayList<>();
	private static ArrayList<Order> orderList  = new ArrayList<>();
	private static Menu menu = null;
	
	private static FileReader frStream;
	private static BufferedReader in;
	private static StringTokenizer st;
	
	/**
	* get DataStore file
	* 
	* @param filename         : filename of the data store file
	* 
	* return dataStore
	*/
	public static DataStore getDataStore(String filename) {
		DataStore dataStore = null;
		DataStoreDao dao = new DataStoreDaoSerialMgr();
		dataStore = dao.retrieve(filename);
		return dataStore;
	}
	
	/**
	* update DataStore file
	* 
	* @param filename	: filename of the data store file
	* @param dataStore	: dataStore object
	*/
	public static void update (DataStore dataStore,String filename) {
		
		 DataStoreDao dao = new DataStoreDaoSerialMgr();
		 dao.update(dataStore, filename);			 
		 
	}

	/**
	* Initalize DataStore file
	* 
	* @param filename	: filename of the data store file
	* 
	* return dataStore
	*/
	public static DataStore init(String filename) {
		// Initialize the starting of the application

		DataStoreDao dao = new DataStoreDaoSerialMgr();
		DataStore dataStore = null;
		dataStore = dao.retrieve(filename);
		
		if (dataStore == null) // nothing is stored on the system
		{		
			loadGuest();
			loadRoom();
			loadMenu();
			reservationIDCounter = loadReservation(999);
			paymentIDCounter = loadPayment(4999);
			
			dataStore = new DataStore(guestHashtable, roomList, reservationIDCounter, reservationHashtable, 
					paymentIDCounter, paymentHashtable, menuList, orderList);
			dao.create(dataStore, filename);

		}
		return dataStore;
	}
	
	
	/**
	* Initalize guestDB file
	* 
	* @param filename	: filename of the guestDB.txt
	* 
	* return dataStore
	*/
	private static void loadGuest(){
		// load guestDB
		try {
			frStream = new FileReader(System.getProperty("user.dir") + "/storage/intitalDB/guestDB.txt");
			in = new BufferedReader(frStream);
			
			String newLine;
			while ((newLine = in.readLine()) !=null){
				st = new StringTokenizer(newLine, ";");
				
				while(st.hasMoreTokens()){
					
					String id = st.nextToken();
					String name = st.nextToken();
					String ccNum = st.nextToken();
					
					Month ccMon = Month.valueOf(st.nextToken());		
					int ccYear = Integer.parseInt(st.nextToken());
					int csv = Integer.parseInt(st.nextToken());
					
					String add = st.nextToken();
					String country = st.nextToken();
					char gender = st.nextToken().charAt(0);
					String nationality = st.nextToken();
					String number = st.nextToken();
	
					cc = new CreditCard(ccNum, ccMon, ccYear, csv);
					guest = new Guest(id, name, cc, add, country, gender, nationality, number);
					guestHashtable.put(id, guest);
				}
			}
			in.close();
		}
		catch(IOException e){
			System.out.println( "Error opening the input file!" + e.getMessage() );
			System.exit( 0 );
		}
	}

	/**
	* Initalize roomDB file
	* 
	* @param filename	: filename of the roomDB.txt
	* 
	* return dataStore
	*/
	private static void loadRoom(){
		// load roomDB
		try {
			frStream = new FileReader(System.getProperty("user.dir") + "/storage/intitalDB/roomDB.txt");
			in = new BufferedReader(frStream);

			String newLine;
			while ((newLine = in.readLine()) !=null){
				st = new StringTokenizer(newLine, ";");
				
				while(st.hasMoreTokens()){
					String roomNumber = st.nextToken();
					RoomType roomType = RoomType.valueOf(st.nextToken());
					BedType bedType=BedType.valueOf(st.nextToken());
					RoomStatus roomStatus=RoomStatus.valueOf(st.nextToken());
					FacingSea facingSea=FacingSea.valueOf(st.nextToken());
					WifiStatus wifiStatus=WifiStatus.valueOf(st.nextToken());
					SmokingStatus smokingStatus=SmokingStatus.valueOf(st.nextToken());
					double roomRate = Integer.parseInt(st.nextToken());
					
					room =new Room(roomNumber,roomType,bedType,roomStatus,wifiStatus,facingSea,smokingStatus, roomRate);
					roomList.add(room);
				}
			}
			in.close();
		}
		catch(IOException e){
			System.out.println( "Error opening the input file!" + e.getMessage() );
			System.exit( 0 );
		}
	}
	
	/**
	* Initalize reservationDB file
	* 
	* @param filename	: filename of the guestDB.txt
	* 
	* return dataStore
	* return reservationIDCounter
	*/
	private static int loadReservation(int reservationIDCounter){
		try {
			
			SimpleDateFormat checkInFormat=new SimpleDateFormat("dd/MM/yyyy");
			
			frStream = new FileReader(System.getProperty("user.dir") + "/storage/intitalDB/reservationDB.txt");
			in = new BufferedReader(frStream);
			
			String newLine;
			while ((newLine = in.readLine()) !=null){
				st = new StringTokenizer(newLine, ";");
				
				while(st.hasMoreTokens()){
					// reservation number
					reservationIDCounter = Integer.parseInt(st.nextToken());
					
					// room
					String roomNumber = st.nextToken();
					RoomType roomType = RoomType.valueOf(st.nextToken());
					BedType bedType=BedType.valueOf(st.nextToken());
					RoomStatus roomStatus=RoomStatus.valueOf(st.nextToken());
					FacingSea facingSea=FacingSea.valueOf(st.nextToken());
					WifiStatus wifiStatus=WifiStatus.valueOf(st.nextToken());
					SmokingStatus smokingStatus=SmokingStatus.valueOf(st.nextToken());
					double roomRate = Integer.parseInt(st.nextToken());
					room = new Room(roomNumber,roomType,bedType,roomStatus,wifiStatus,facingSea,smokingStatus, roomRate);
					
					// num of adult
					int numOfAdult = Integer.parseInt(st.nextToken());
					
					// num of child
					int numOfChild = Integer.parseInt(st.nextToken());
					
					// check in 
					Date dateIn = checkInFormat.parse(st.nextToken());
					Calendar checkIn = Calendar.getInstance();
					checkIn.setTime(dateIn);
					
					// check out
					Date dateOut = checkInFormat.parse(st.nextToken());
					Calendar checkOut = Calendar.getInstance();
					checkOut.setTime(dateOut);
					
					// reservation status
					ReservationStatus reservationStatus=ReservationStatus.valueOf(st.nextToken());
					
					// check in via type
					CheckInVia checkInVia = CheckInVia.valueOf(st.nextToken());

					// Guest
					String id = st.nextToken();
					String name = st.nextToken();
					String ccNum = st.nextToken();
					Month ccMon = Month.valueOf(st.nextToken());		
					int ccYear = Integer.parseInt(st.nextToken());
					int csv = Integer.parseInt(st.nextToken());
					String add = st.nextToken();
					String country = st.nextToken();
					char gender = st.nextToken().charAt(0);
					String nationality = st.nextToken();
					String number = st.nextToken();
	
					cc = new CreditCard(ccNum, ccMon, ccYear, csv);
					guest = new Guest(id, name, cc, add, country, gender, nationality, number);
							
					reservation = new Reservation(reservationIDCounter, room, numOfAdult, numOfChild, checkIn, checkOut, reservationStatus,checkInVia,guest);
					reservationHashtable.put(reservationIDCounter, reservation);
			
				}
			}
			in.close();
		}
		catch(IOException e){
			System.out.println( "Error opening the input file!" + e.getMessage() );
			System.exit( 0 );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return reservationIDCounter+1;
	}

	/**
	* Initalize paymentDB file
	* 
	* @param filename	: filename of the paymentDB.txt
	* 
	* return dataStore
	* return paymentIDCounter
	*/
	private static int loadPayment(int paymentID){
		// load paymentDB
		try {
			frStream = new FileReader(System.getProperty("user.dir") + "/storage/intitalDB/paymentDB.txt");
			in = new BufferedReader(frStream);
			SimpleDateFormat checkInFormat=new SimpleDateFormat("dd/MM/yyyy");
			
			String newLine;
			while ((newLine = in.readLine()) !=null){
				st = new StringTokenizer(newLine, ";");
				
				while(st.hasMoreTokens()){
					
					
					paymentID = Integer.parseInt(st.nextToken());
					
					// reservation number
					int reservationIDCounter = Integer.parseInt(st.nextToken());
					
					// room
					String roomNumber = st.nextToken();
					RoomType roomType = RoomType.valueOf(st.nextToken());
					BedType bedType=BedType.valueOf(st.nextToken());
					RoomStatus roomStatus=RoomStatus.valueOf(st.nextToken());
					FacingSea facingSea=FacingSea.valueOf(st.nextToken());
					WifiStatus wifiStatus=WifiStatus.valueOf(st.nextToken());
					SmokingStatus smokingStatus=SmokingStatus.valueOf(st.nextToken());
					double roomRate = Integer.parseInt(st.nextToken());
					room = new Room(roomNumber,roomType,bedType,roomStatus,wifiStatus,facingSea,smokingStatus, roomRate);
					// num of adult
					int numOfAdult = Integer.parseInt(st.nextToken());
					// num of child
					int numOfChild = Integer.parseInt(st.nextToken());
					// check in 
					Date dateIn = checkInFormat.parse(st.nextToken());
					Calendar checkIn = Calendar.getInstance();
					checkIn.setTime(dateIn);
					// check out
					Date dateOut = checkInFormat.parse(st.nextToken());
					Calendar checkOut = Calendar.getInstance();
					checkOut.setTime(dateOut);
					// reservation status
					ReservationStatus reservationStatus=ReservationStatus.valueOf(st.nextToken());
					// check in via type
					CheckInVia checkInVia = CheckInVia.valueOf(st.nextToken());
					// Guest
					String id = st.nextToken();
					String name = st.nextToken();
					String ccNum = st.nextToken();
					Month ccMon = Month.valueOf(st.nextToken());		
					int ccYear = Integer.parseInt(st.nextToken());
					int csv = Integer.parseInt(st.nextToken());
					String add = st.nextToken();
					String country = st.nextToken();
					char gender = st.nextToken().charAt(0);
					String nationality = st.nextToken();
					String number = st.nextToken();
	
					cc = new CreditCard(ccNum, ccMon, ccYear, csv);
					guest = new Guest(id, name, cc, add, country, gender, nationality, number);	
					reservation = new Reservation(reservationIDCounter, room, numOfAdult, numOfChild, checkIn, checkOut, reservationStatus,checkInVia,guest);
					
					double totalBill = Double.parseDouble(st.nextToken());
					PaymentMode paymentMode = PaymentMode.valueOf(st.nextToken());
					
					Payment payment  = new Payment(paymentID, reservation, totalBill, paymentMode);
					paymentHashtable.put(paymentID, payment);

				}
			}
			in.close();
		}
		catch(IOException e){
			System.out.println( "Error opening the input file!" + e.getMessage() );
			System.exit( 0 );
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return paymentID+1;
	}
	
	/**
	* Initalize menuDB file
	* 
	* @param filename	: filename of the menuDB.txt
	* 
	* return dataStore
	*/
	private static void loadMenu(){
		// load menuDB
		try {
			frStream = new FileReader(System.getProperty("user.dir") + "/storage/intitalDB/menuDB.txt");
			in = new BufferedReader(frStream);
			
			String newLine;
			while ((newLine = in.readLine()) !=null){
				st = new StringTokenizer(newLine, ";");
				
				while(st.hasMoreTokens()){
					
					String itemName = st.nextToken();
					double itemPrice = Double.parseDouble(st.nextToken());
					String itemDescription = st.nextToken();
					
					menu = new Menu(itemName,  itemPrice, itemDescription);
					
					menuList.add(menu);	
				}
			}
			in.close();
		}
		catch(IOException e){
			System.out.println( "Error opening the input file!" + e.getMessage() );
			System.exit( 0 );
		}	
	}
}
