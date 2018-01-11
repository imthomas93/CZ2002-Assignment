/**
Menu Class to store Menu objects

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE LAM WEN QI
 @version 1.0
 @since 2016-03-17
*/

package assignment.model;
import java.io.Serializable;
public class Menu implements Serializable{

	/** 
	Name of item
	*/
	private String itemName;
	
	/** 
	Description of item
	*/
	private String itemDescription;
	
	/** 
 	Price of item
	*/
	private double itemPrice;

	
    /**
    * Creates a new Menu object
    * 
	* @param itemName		: Name of item
	* @param itemPrice		: Price of item
    * @param itemDescription: Description of item
    */		
	public Menu(String itemName,double itemPrice,String itemDescription) {
		this.itemName = itemName;
		this.itemPrice = itemPrice;
		this.itemDescription = itemDescription;	
	}
 
	
	/**
	 * Set the name of item
	 * 
	 * @param itemName
	 *            : This indicates the new item name
	 */
	public void setItemName(String itemName){this.itemName = itemName;}
	
	/**
	 * Set the description of item
	 * 
	 * @param itemDescription
	 *            : This indicates the new item description
	 */
	public void setItemDescription(String itemDescription){this.itemDescription = itemDescription;}
	
	/**
	 * Set the price of item
	 * 
	 * @param itemPrice
	 *            : This indicates the new item price
	 */
	public void setItemPrice(double itemPrice){this.itemPrice = itemPrice;}
	
    /**
    * get the name of item
    * 
    * return itemName
    */
	public String getItemName() { return itemName; }
	
    /**
    * get the description of item
    * 
    * return itemDescription
    */
	public String getItemDescription() { return itemDescription; }
	
    /**
    * get the price of item
    * 
    * return itemPrice
    */
	public double getItemPrice() { return itemPrice;}


	/**
	 * Prints out Menu items
	 *
	 */
	public void printMenuItem(){
	System.out.println(this.itemName+ "\t\t\t$" + this.itemPrice+ "\t"+ this.itemDescription);

	}
}
