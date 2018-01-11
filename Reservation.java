/**
Reservation Class to store reservation objects

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE LAM WEN QI
 @version 1.0
 @since 2016-03-31
*/

package assignment.model;

import java.io.Serializable;

import java.text.SimpleDateFormat;
import java.util.*;

public class Reservation implements Serializable{
	/**
	 * Reservation ID
	 */
	private int reservationNumber;
	
	/**
	 * Room selected for reservation
	 */
	private Room room;
	
	/**
	 * Number of Adult
	 */
	private int numOfAdult;
	
	/**
	 * Number of Children
	 */
	private int numOfChild;
	
	/**
	 * Check-In date and time
	 */
	private Calendar checkIn;
	
	/**
	 * Check-Out date and time
	 */
	private Calendar checkOut;
	
	/**
	 * Status of reservation
	 * (E.g. "Confirmed","In Waitlist", "Checked in", "Checked Out", "Expired")
	 */
	private ReservationStatus reservationStatus;
	
	/**
	 * Booking type
	 * (E.g. "Reservation", "Walk In")
	 */
	private CheckInVia	checkInVia;
	
	/**
	 * Guest who made reservation
	 */
	private Guest guest;
	
	/**
	 * Create a new reservation
	 * 
	 * @param reservation_Num		Reservation ID
	 * @param selectedRoom			Room selected for reservation
	 * @param num_Adult				Number of Adult
	 * @param num_Child				Number of Children
	 * @param check_In				Check-In date and time
	 * @param check_Out				Check-Out date and time
	 * @param reservation_Status	Status of reservation
	 * @param checkInVia			Booking Type
	 * @param guestObj				Guest who made reservation
	 */
	public Reservation(int reservation_Num,Room selectedRoom,int num_Adult,int num_Child,Calendar check_In,Calendar check_Out,ReservationStatus reservation_Status, CheckInVia checkInVia, Guest guestObj){
		this.reservationNumber=reservation_Num;
		this.room=selectedRoom;
		this.numOfAdult=num_Adult;
		this.numOfChild= num_Child;
		this.checkIn=check_In;
		this.checkOut=check_Out;
		this.reservationStatus=reservation_Status;
		this.checkInVia = checkInVia;
		this.guest=guestObj;
	}
	
	/**
	 * Gets number of adult.
	 * 
	 * @return number of adult.
	 */
	public int getNumOfAdult(){return numOfAdult;}
	
	/**
	 * Gets reservation number.
	 * 
	 * @return reservation number.
	 */
	public int getReservationNumber(){return reservationNumber;}
	
	/**
	 * Gets reservation status.
	 * 
	 * @return reservation status.
	 */
	public ReservationStatus getReservationStatus(){return reservationStatus;}
	
	/**
	 * Gets number of children.
	 * 
	 * @return number of children.
	 */
	public int getNumOfChild(){return numOfChild;}
	
	/**
	 * Gets room for reservation.
	 * 
	 * @return Room object.
	 */
	public Room getRoom(){return room;}
	
	/**
	 * Gets Check-In date and time.
	 * 
	 * @return Check-in date and time.
	 */
	public Calendar getCheckIn(){return checkIn;}
	
	/**
	 * Gets Check-Out date and time.
	 * 
	 * @return Check-out date and time.
	 */
	public Calendar getCheckOut(){return checkOut;}	
	
	/**
	 * Gets type of check-in status.
	 * 
	 * @return Reservation status.
	 */
	public CheckInVia getCheckInVia(){return checkInVia;}
	
	/**
	 * Gets guest who made reservation.
	 * 
	 * @return Guest object.
	 */
	public Guest getGuest(){return guest;}

	/**
	 * Changes number of adult in reservation
	 * 
	 * @param numOfAdult New number of adult
	 */
	public void setNumOfAdult(int numAdult){this.numOfAdult=numAdult;}
	
	/**
	 * Changes number of children in reservation
	 * 
	 * @param numOfChild New number of children
	 */
	public void setNumOfChild(int numChild){this.numOfChild=numChild;}
	
	/**
	 * Changes room object
	 * 
	 * @param room New room object
	 */
	public void setRoom(Room room){this.room=room;}
	
	/**
	 * Changes check-in date
	 * 
	 * @param checkIn New check-in
	 */
	public void setCheckIn(Calendar checkIn){this.checkIn=checkIn;}
	
	/**
	 * Changes check-out date
	 * 
	 * @param checkOut New check-out
	 */
	public void setCheckOut(Calendar checkOut){this.checkOut=checkOut;}
	
	/**
	 * Changes type of check-in
	 * 
	 * @param checkInVia New check-in type
	 */
	public void setCheckInVia(CheckInVia checkInVia){this.checkInVia = checkInVia;}
	
	/**
	 * Changes status of reservation 
	 * 
	 * @param reservationStatus New number of adult
	 */
	public void setReservationStatus(ReservationStatus status){this.reservationStatus=status;}
	
	public enum ReservationStatus{
		CONFIRMED,IN_WAITLIST,CHECKED_IN, CHECKED_OUT,EXPIRED;
	}
	
	public enum CheckInVia{
		RESERVATION,WALK_IN;
	}
	
	/**
	 * Print reservation details
	 */
	public void printReservation(){
		SimpleDateFormat dateFormatter=new SimpleDateFormat("dd/MM/yyyy");
		
		Date dateIn=checkIn.getTime();
		String strDateIn=dateFormatter.format(dateIn)+ " at 15:00.";
		
		Date dateOut=checkOut.getTime();
		String strDateOut=dateFormatter.format(dateOut)+" at 12:00.";
			
		System.out.println("Reservation number: "+reservationNumber);
		System.out.println("Room number: "+this.room.getRoomNumber());
		System.out.println("Number of adult: "+numOfAdult);
		System.out.println("Number of child: "+numOfChild);
		System.out.println("Check in date: "+strDateIn);;
		System.out.println("Check out date: "+strDateOut);
		System.out.println("Reservation status: "+reservationStatus);
	}

	/**
	 * Check if reservation has expired
	 * 
	 * @return Expiry status of reservation
	 */
	public boolean checkExpiry() {
		boolean expiryStatus=false;
		Calendar calTemp=(Calendar) checkIn.clone();
		Calendar calCurrent=Calendar.getInstance();
		
		calTemp.add(Calendar.HOUR,1);
		
		if(calCurrent.after(calTemp)){
			this.reservationStatus=ReservationStatus.EXPIRED;
			expiryStatus=true;
		}
		return expiryStatus;
	}
}



