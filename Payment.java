/**
Payment Class to store Payment objects

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE LAM WEN QI
 @version 1.0
 @since 2016-03-17
*/
package assignment.model;
import java.io.Serializable;
import assignment.model.Reservation;
import java.text.DecimalFormat;

public class Payment implements Serializable {
	/**
	 * Assign an ID to the payment
	 */
	private int paymentID;
	/**
	 * Entity reservation
	 */
	private Reservation reservation;
	/**
	 * Total bill presented
	 */
	private double totalBill;
	/**
	 * Type of payment mode 
	 */
	private static PaymentMode paymentMode;
	/**
	 * To set format of double to 2 decimal places
	 */
	private static DecimalFormat df = new DecimalFormat("####0.00");// decimal format printing	

	/**
	 * Create a payment object
	 * 
	 * @param paymentID		: Give payment a unique number
	 * @param reervation	: Entity reservation
	 * @param totalBill		: Total bill presented
	 * @param paymentMode	: Type of payment mode
	 */

	public Payment(int paymentID, Reservation reservation, double totalBill, PaymentMode paymentMode) {

		this.paymentID = paymentID;
		this.reservation = reservation;
		this.totalBill = totalBill;
		Payment.paymentMode = paymentMode;
	}
	/**
	 * 
	 * used to determine the type of payment mode
	 *
	 */

	public enum PaymentMode {
		/**
		 * pay by cash
		 */
		CASH, 
		/**
		 * pay by credit card
		 */
		CREDIT_CARD,
		/**
		 * set payment as null
		 */
		NULL;
	}
	
	/**
	 * gets the payment id
	 * @return paymentID
	 */

	public int getpaymentID() {
		return paymentID;
	}
	
	/**
	 * Returns the associated reservation object
	 * @return reservation
	 */

	public Reservation getReservation() {
		return reservation;
	}
	
	/**
	 * Returns the total bill 
	 * @return totalBill
	 */

	public double getTotalBill() {
		return totalBill;
	}

	/**
	 * Returns the type of payment mode
	 * @return paymentMode
	 */

	public static PaymentMode getPaymentMode() {
		return paymentMode;
	}

	/**
	 * Set the total bill of a payment
	 * @param totalBill
	 */
	public void setTotalBill(double totalBill) {
		this.totalBill = totalBill;
	}
	
	/**
	 * Set the reservation entity  
	 * @param reservation
	 */

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	/**
	 * set the payment mode 
	 * @param paymentMode
	 */
	public void setPaymentMode(PaymentMode paymentMode) {
		Payment.paymentMode = paymentMode;
	}
	/**
	 * Prints out the payment informations
	 */

	public void printPayment() {
		System.out.println("Guest Information: " + this.getReservation().getGuest().getName());
		if (Payment.getPaymentMode().equals(PaymentMode.CREDIT_CARD)) {
			System.out.println("Payment Type: " + Payment.getPaymentMode());
			this.getReservation().getGuest().getCreditCard().printCreditCard();
		} else
			System.out.println("Payment Type: " + Payment.getPaymentMode());
		System.out.println("Total Amount: " + df.format(this.getTotalBill()));
	}
}
