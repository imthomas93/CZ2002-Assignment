/**
 Represents the DataStore object in the application
 DataStore contains various different models to be used with the application

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE LAM WEN QI
 @version 1.0
 @since 2016-03-15
*/
package assignment.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

public class DataStore implements Serializable {
	
    /** 
    Hashtable of Guest
    */
	private Hashtable<String, Guest> guestHashtable = new Hashtable<>();
	
    /** 
	ArrayList of Room
    */
	private ArrayList<Room> roomList = new ArrayList<Room>();

    /** 
    Integer of reservationIDCounter
    */
	private int reservationIDCounter;
	
    /** 
    Hashtable of Reservation
    */
	private Hashtable<Integer, Reservation> reservationHashtable = new Hashtable<>();
	
    /** 
    Integer of paymentIDCounter
    */
	private int paymentIDCounter;
	
    /** 
    Hashtable of Payment
    */
	private Hashtable<Integer, Payment> paymentHashtable = new Hashtable<>();
	
    /** 
	ArrayList of Menu
    */
	private ArrayList<Menu> menuList  = new ArrayList<>();

    /** 
	ArrayList of Order
    */
	private ArrayList<Order> orderList  = new ArrayList<>();
	
    /**
    * Creates a new DataStore object
    * 
    * @param guestHashtable       : Hashtable of Guest
    * @param roomList   : roomList of Room
    * @param reservationIDCounter   : reservationIDCounter of Integer
	* @param reservationHashtable   : reservationHashtable of Reservation
    * @param paymentIDCounter   : paymentIDCounter of Integer
	* @param paymentHashtable   : paymentHashtable of Payment
    * @param menuList   : menuList of Room
    * 
    */	
	public DataStore(Hashtable<String, Guest> guestHashtable, ArrayList<Room> roomList, int reservationIDCounter, 
			Hashtable<Integer, Reservation> reservationHashtable, int paymentIDCounter, Hashtable<Integer, Payment> paymentHashtable
			, ArrayList<Menu> menuList, ArrayList<Order> orderList)
	{
		super();
		this.guestHashtable = guestHashtable;
		this.roomList = roomList;
		this.reservationIDCounter = reservationIDCounter;
		this.reservationHashtable = reservationHashtable;
		this.paymentIDCounter = paymentIDCounter;
		this.paymentHashtable = paymentHashtable;
		this.menuList = menuList;
		this.orderList = orderList;
	}
	
    /**
    * get Hashtable of guests
    * 
    * return guestHashTable
    */
	public Hashtable<String, Guest> getGuestHashtable() {return guestHashtable;}
	
    /**
    * get Arraylist of Room
    * 
    * return roomList
    */	
	public ArrayList<Room> getRoomList(){return roomList;}
	
    /**
    * get Arraylist of Menu
    * 
    * return menuList
    */	
	public ArrayList<Menu> getMenuList(){return menuList;}
	
    /**
    * get Arraylist of Order
    * 
    * return orderList
    */	
	public ArrayList<Order> getOrderList(){return orderList;}
	
    /**
    * get Integer of ReservationIDCounter
    * 
    * return reservationIDCounter
    */	
	public int getReservationIDCounter(){ return reservationIDCounter;}
	
    /**
    * get Hashtable of Reservation
    * 
    * return reservationHashtable
    */	
	public Hashtable<Integer, Reservation> getReservationHashtable(){ return reservationHashtable;}
	
    /**
    * get Hashtable of Payment
    * 
    * return paymentHashtable
    */	
	public Hashtable<Integer, Payment> getPaymentHashtable(){ return paymentHashtable;}
	
    /**
    * get Integer of ReservationIDCounter
    * 
    * return reservationIDCounter
    */
	public int getPaymentIDCounter(){return paymentIDCounter;}	
   
	/**
    * setup the guestHashtable
    * 
    * @param guestHashtable   : guestHashtable of Guest
    */
	public void setStaffHashTable(Hashtable<String, Guest> guestHashtable) { this.guestHashtable = guestHashtable;}
 
    /**
    * setup the roomList
    * 
    * @param roomList   : roomList of Room
    */
	public void setRoomList(ArrayList<Room> roomList){this.roomList = roomList;}
	
    /**
    * setup the orderList
    * 
    * @param orderList   : oderList of Order
    */
	public void setOrderList(ArrayList<Order> orderList){this.orderList = orderList;}
	
    /**
    * setup the menuList
    * 
    * @param menuList   : menuList of Room
    */
	public void setMenuList(ArrayList<Menu> menuList){this.menuList = menuList;}
	
    /**
    * setup the reservationIDCounter
    * 
    * @param reservationIDCounter   : reservationIDCounter of Integer
    */
	public void setReservationIDCounter(int reservationIDCounter){ this.reservationIDCounter = reservationIDCounter;}

    /**
    * setup the paymentIDCounter
    * 
    * @param paymentIDCounter   : paymentIDCounter of Integer
    */	
	public void setPaymentIDCounter(int paymentIDCounter){
		this.paymentIDCounter = paymentIDCounter;
	}
	
	/**
	    * setup the reservationHashtable
	    * 
	    * @param reservationHashtable   : reservationHashtable of Reservation
	    */
	public void setReservationHashTable(Hashtable<Integer, Reservation> reservationHashtable){
		this.reservationHashtable =reservationHashtable;
	}
	
	/**
	    * setup the paymentHashtable
	    * 
	    * @param paymentHashtable   : paymentHashtable of Payment
	    */
	public void setPaymentHashTable(Hashtable<Integer, Payment> paymentHashtable){
		this.paymentHashtable =paymentHashtable;
	}
	
}
