/**
  Represents the Payment Manager class is used to generate payment and check out customer from the hotel

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE  LAM WEN QI
 @version 1.0
 @since 2016-03-31
 */

package assignment.util;
import java.text.DecimalFormat;
import java.util.*;
import assignment.model.*;
import assignment.model.Payment.PaymentMode;

import assignment.model.Reservation.ReservationStatus;

public class PaymentMgr {

	private static Scanner scn = new Scanner(System.in);
	private static Reservation reservation = null;
	private static Room room= null;
	private static Guest guest=null;
	private static Payment payment =null;
	/**
	 * assign payment ID
	 */
	private static int paymentIDCounter;
	/**
	 * the total amount of bill
	 */
	private static double totalBill;
	/**
	 * roomBill is the total amount associated to the room
	 * roomServiceOrderBill is the total bill for room services
	 */
	private static double roomBill, roomServiceOrderBill;
	/**
	 * TAX is the percentage of tax charged
	 * DISCOUNT is percentage of discount applied to the total bill
	 * SERVICE_CHARGE is the percentage of service charges incurred by customer
	 */
	private static final float TAX=0.07f%2,DISCOUNT= 0.2f%2,SERVICE_CHARGE=0.1f%2;
	private static DecimalFormat df = new DecimalFormat("####0.00");// decimal format printing	

	/** Creation of payment
	 * 
	 * @param dataStore  payement data
	 */
	public static void checkOut(DataStore dataStore){
		try{
			paymentIDCounter = dataStore.getReservationIDCounter() + 1;
			String input;
			int reservationID;
			int option;
			
			// 1 - Prompt for Reservation ID
			System.out.print("Enter reservation ID for Check Out :");
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty Input!");
			reservationID=Integer.parseInt(input);

			if(dataStore.getReservationHashtable().containsKey(reservationID)){
				reservation = dataStore.getReservationHashtable().get(reservationID);
				
				
				if (!reservation.getReservationStatus().equals(ReservationStatus.CHECKED_IN))
					throw new Exception("Reservation is not Checked In");

				// 2 - print invoice
				totalBill = printBill(dataStore, reservationID);
				
				// 2 - Select PaymentMode
				PaymentMode paymentMode=PaymentMode.NULL;
				System.out.print("\nPayment type (1)Cash (2)Credit Card :");
				input = scn.nextLine();
				if (input.isEmpty())
					throw new Exception("Empty Input!");
				option=Integer.parseInt(input);

				switch(option){
				case 1:
					paymentMode=PaymentMode.CASH;	
					break;
				case 2:
					paymentMode=PaymentMode.CREDIT_CARD;
					break;
				default:
					throw new Exception("An invalid input, please try again ");	
				}
				
				// Confirm payment
				payment = new Payment(paymentIDCounter, reservation, totalBill, paymentMode);
				payment.printPayment();
				
				System.out.print("Confirm Payment? :(1)Yes (2)No: ");
				input = scn.nextLine();
				if (input.isEmpty())
					throw new Exception("Empty Input!");
				option=Integer.parseInt(input);
				
				switch(option){
				case 1:
					dataStore.getPaymentHashtable().put(paymentIDCounter, payment);
					dataStore.setPaymentIDCounter(paymentIDCounter);
					System.out.println("\nPayment completed! Thank you for staying with this hotel. Hope to see you again!");

					// Set ReservationStatus back to CHECK_OUT & RoomStatus to Vacaant
					ReservationMgr.checkOutReservation(dataStore, reservationID);
					// Destroy orderList associated to room number
					room = dataStore.getReservationHashtable().get(reservationID).getRoom();
					RoomServiceMgr.removeOrders(dataStore, room.getRoomNumber());
					break;
				case 2:
					System.out.println("\nPayment not completed!");
					break;
				default:
					throw new Exception("An invalid input, please try again ");	
				}
			}
			else
				throw new Exception("ReservationID not in DB!");			
		}catch(Exception e){System.out.println("Error: " + e.getMessage());}
	}
	/**
	 * 
	 * @param dataStore 		reservation data
	 * @param reservationID		reservation ID
	 * @return 	totalRate 		the net price without counting including the other charges
	 */
	private static double printBill(DataStore dataStore, int reservationID) {
		double discountAmount = 0, serviceCharge, tax, subtotal, overallSubtotal;
		totalBill = 0;
		try{
			// TODO Auto-generated method stub
			reservation = dataStore.getReservationHashtable().get(reservationID);
			guest = reservation.getGuest();
			room = reservation.getRoom();
		
			roomServiceOrderBill = RoomServiceMgr.computeRoomServiceTotal(dataStore, room.getRoomNumber());
			roomBill = ReservationMgr.computeRoomTotal(dataStore, room.getRoomNumber(), reservation.getReservationNumber());

			
			// Prompt user for Discount
			System.out.println("Apply Discount to checkout?");
			System.out.println("(1)Yes (2)No");
			String input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Discount Rate is not chosen!");
			int option=Integer.parseInt(input);
			switch(option){
			case 1:
				subtotal = roomBill + roomServiceOrderBill;
				discountAmount = subtotal*DISCOUNT;
				overallSubtotal = subtotal - discountAmount;
				serviceCharge = overallSubtotal*SERVICE_CHARGE;
				tax = (overallSubtotal + serviceCharge)*TAX;
				totalBill = tax + serviceCharge + overallSubtotal;
				break;
			case 2:
				subtotal = roomBill + roomServiceOrderBill;
				discountAmount = subtotal*0;
				overallSubtotal = subtotal - discountAmount;
				serviceCharge = overallSubtotal*SERVICE_CHARGE;
				tax = (overallSubtotal + serviceCharge)*TAX;
				totalBill = tax + serviceCharge + overallSubtotal;
				break;
			default:
				throw new Exception("An invalid input, please try again ");
			}
		// print invoice
		System.out.println("================================= Checkout Bill ================================= ");
		System.out.println("Guest's Name : " + guest.getName());
		System.out.println("ReservationID :" + reservation.getReservationNumber());
		ReservationMgr.printRoomBill(dataStore, room.getRoomNumber(), reservation.getReservationNumber());
		RoomServiceMgr.printRoomServiceBill(dataStore, room.getRoomNumber());
		System.out.println("Room Total:\t\t\t\t$" + df.format(roomBill));
		System.out.println("Room Service Total:\t\t\t$" + df.format(roomServiceOrderBill));
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("Subtotal:\t\t\t\t$" + df.format(subtotal));
		System.out.println("Discounted Price:\t\t\t$" + df.format(discountAmount));
		System.out.println("---------------------------------------------------------------------------------");
		System.out.println("Overall Subtotal:\t\t\t$" + df.format(overallSubtotal));
		System.out.println("Service Charge:\t\t\t\t$" + df.format(serviceCharge));
		System.out.println("Tax:\t\t\t\t\t$" + df.format(tax));
		System.out.println("=================================================================================");
		System.out.print("TotalBill:\t\t\t\t$" + df.format(totalBill) + "\n");
	
		}catch(Exception e){System.out.println("Error: " + e.getMessage());}
		return totalBill;
	}

	/**
	 * 
	 * @param dataStore		Reservation data
	 * @param ID
	 * @return		


	public static double roomCharges(DataStore dataStore,int ID){
		reservation = dataStore.getReservationHashtable().get(ID);
		room = reservation.getRoom();
		
		// the value of i will be from 1-7 for Sunday to Saturday
		//I.e. Assume 5th April 2016, tueday will return 3
		int day=reservation.getCheckIn().get(Calendar.DAY_OF_WEEK);
		int weekend =0, weekday = 0;
		double rate=room.getRoomRate(),totalRate=0;
		
		// get current date and time
		Calendar c_in = reservation.getCheckIn();
		Calendar c_out = reservation.getCheckOut();
		System.out.println(c_in);
		System.out.println(c_out);

	    do{
	    	//weekend
	    	if(day==1||day==7){
	    		// number of days to add	
	    		totalRate=rate+30;
	    		weekend++;
	    	}else {
	    			//weekday
	    			totalRate+=rate;
	    			weekday++;
	    	}
    		// increment day by 1
    		c_in.add(Calendar.DATE, 1);
    		day=c_in.get(Calendar.DAY_OF_WEEK);
	    	// loop until checkinday matches checkoutday
	    	} while((c_out.getTime().equals(c_in.getTime())));
	   
	   System.out.println("Roomrate per day: " + room.getRoomRate());
	   System.out.println("Total Amount of Weekdays Stay (additional charge of $30 per day) : " + weekday);
	   System.out.println("Total Amount of Weekends Stay (no additional charges)			: " + weekend);

	   return  totalRate;
	}*/

}



