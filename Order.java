/**
Order Class to store Order objects

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE LAM WEN QI
 @version 1.0
 @since 2016-03-17
*/
package assignment.model;
import java.io.Serializable;
import javax.print.DocFlavor.STRING;

public class Order extends Menu {
	/** 
	room identification where order was made
	*/
	private String roomID;
	
	 /**
	    * Creates a new Order object which extends from Menu
	    * 
		* @param itemName		: Name of item
		* @param itemPrice		: Price of item
	    * @param itemDescription: Description of item
	    * @param roomID			: room identification where order was made
	    */
	public Order(String itemName,double itemPrice,String itemDescription,String roomID)
	{
		super(itemName, itemPrice,itemDescription);
		this.roomID = roomID;
	}
	/**
	 * Set the room identification where order was made
	 * 
	 * @param roomID
	 *            : This indicates the new room ID
	 */
	public void setRoomID(String roomID) { this.roomID = roomID; }
	
	 /**
	    * get the room identification where order was made
	    * 
	    * return roomID
	    */
	public String getRoomID() { return roomID;}
	
	/**
	 * Prints out Order items
	 *
	 */
	public void printOrderItem(){
		System.out.println(getItemName()+ "\t\t\t$" + getItemPrice()+ "\t"+getItemDescription()+ "   ");
	}
}

