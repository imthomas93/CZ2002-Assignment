/**
 Represents the Menu Manager class that let user change the application setting

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE  LAM WEN QI
 @version 1.0
 @since 2016-04-01
*/
package assignment.util;
import java.text.DecimalFormat;
import assignment.model.*;
import java.util.*;

import assignment.model.Room.RoomStatus;

public class RoomServiceMgr {
	/**
	 * Scanner scn to take in user input
	 */
	private static Scanner scn = new Scanner(System.in);
	/**
	 * Menu Object
	 */
	private static Menu menu = null;
	/**
	 * Room Object
	 */
	private static Room room = null;
	/**
	 * Order Object
	 */
	private static Order order = null;
	/**
	 * format to change all double to 2 decimal spacing
	 */
	private static DecimalFormat df = new DecimalFormat("####0.00");
	
	/**
	 * Create a new menu
	 * 
	 * @param dataStore		Store food menu data
	 */
	public static void createMenu(DataStore dataStore){
		String input;
		String itemName, itemDescription;
		double itemPrice;
		try{
			// print current menu
			System.out.println("Current Food Items in Menu:");
			printAllItem(dataStore);
			
			// user input food name
			System.out.print("Please enter food name: ");
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty Entry");
			itemName = input;
			
			// user input food price
			System.out.print("Please enter food price: ");
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty Entry");
			itemPrice = Double.parseDouble(input);
			
			// user input food description
			System.out.print("Please enter food description: ");
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty Entry");
			itemDescription = input;
			
			menu = new Menu(itemName, itemPrice, itemDescription);
			dataStore.getMenuList().add(menu);
			
			System.out.println("Food added into DB! \n");
			System.out.println("Food" + "\t\t\t\tPrice" + "\tDescription");
			menu.printMenuItem();
			
		} catch(Exception e){System.out.println("Error: " + e.getMessage());}
	}

	/**
	 * Update the menu
	 * 
	 * @param dataStore		Update menu data
	 */
	public static void updateMenu(DataStore dataStore){
		
		String input;
		int itemID, choice;
		double newPrice;
	
		try{
			// print current menu
			printAllItem(dataStore);
			
			// select item id
			System.out.print("Please Enter Food Index: ");
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty Entry");
			
			itemID = Integer.parseInt(input);
			
			// validate Menu Item
			if(!validateMenuItem(dataStore, itemID))
				throw new Exception("Food Index not in DB");
			
			menu = dataStore.getMenuList().get(itemID-1);
			System.out.println("\nFood" + "\t\t\t\tPrice" + "\tDescription");
			menu.printMenuItem();
			
			System.out.println("\nSelect which information to be updated:");
			System.out.println("(1) Item Name");
			System.out.println("(2) Item Price");
			System.out.println("(3) Item Description");
			System.out.println("(4) Exit");
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty Entry");
			choice  = Integer.parseInt(input);
			
			switch (choice){
			case 1:
				// update item name
				System.out.print("Enter New Food Name: ");
				input = scn.nextLine();
				if (input.isEmpty())
					throw new Exception("Empty Entry");
				menu.setItemName(input);
				System.out.println("Food Name has been updated!");
				System.out.println("Food" + "\t\t\t\tPrice" + "\tDescription");
				menu.printMenuItem();			
				break;
			case 2:
				// update item price
				System.out.print("Enter New Price: ");
				input = scn.nextLine();
				if (input.isEmpty())
					throw new Exception("Empty Entry");
				newPrice = Double.parseDouble(input);
				menu.setItemPrice(newPrice);
				System.out.println("Food price has been updated!");
				System.out.println("Food" + "\t\t\t\tPrice" + "\tDescription");
				menu.printMenuItem();
				break;
			case 3:
				// update item description
				System.out.print("Enter New Food Description: ");
				input = scn.nextLine();
				if (input.isEmpty())
					throw new Exception("Empty Entry");
				menu.setItemDescription(input);
				System.out.println("Food description has been updated!");
				System.out.println("Food" + "\t\t\t\tPrice" + "\tDescription");
				menu.printMenuItem();
				break;
			case 4:
				break;
			default:
				throw new Exception ("Invalid Entry!");
			}
		} catch(Exception e){System.out.println("Error: " + e.getMessage());}
	}
	
	/**
	 * remove menu items
	 * 
	 * @param dataStore		Remove menu data
	 */
	public static void removeMenu(DataStore dataStore){
		String input;
		int itemID;
		
		try{
			// print current menu
			printAllItem(dataStore);
		
			// select item id
			System.out.print("Please Enter Food Index: ");
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty Entry");
			
			itemID = Integer.parseInt(input);
			
			// validate Menu Item
			if (!validateMenuItem(dataStore, itemID))
				throw new Exception("Food Index not in DB");
				
			// check if its correct to implement this way
			dataStore.getMenuList().remove(itemID-1);
			
			System.out.println("Item removed");
			
			// print current menu
			printAllItem(dataStore);
		} catch(Exception e){System.out.println("Error: " + e.getMessage());}
	}
	
	/**
	 * Displays the menu
	 * 
	 * @param dataStore		Display menu data
	 */	
	public static void showMenu(DataStore dataStore)
	{
		String input;
		int itemID;
		String roomID;
		
		try{
			//enter room ID
			System.out.print("Enter Room Number(Format: xx-xx): ");
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Empty Entry");
			roomID = input;
			
			for(int i = 0; i < dataStore.getRoomList().size(); i++) //assign RoomList to room
			{
				room = dataStore.getRoomList().get(i);
				if(roomID.equals(room.getRoomNumber()))// validate if user entered roomID is similar to any rooms in the hotel
				{
					if(room.getRoomStatus() == RoomStatus.OCCUPIED)//check if room is occupied
					{
						printAllItem(dataStore);
						// select item id
						System.out.print("\nPlease Enter Food Index, press 0 to confirm order ");
						input = scn.nextLine();
						if (input.isEmpty())
						throw new Exception("Empty Entry");	
						itemID = Integer.parseInt(input);
						if (itemID == 0){
							{
								System.out.println("---------------------------------Room " +roomID + " Order---------------------------------");
								System.out.println("ID Food" + "\t\t\t\tPrice" + "\tDescription");
								for (  i = 0; i < dataStore.getOrderList().size();i++)
								{
									order = dataStore.getOrderList().get(i);
									System.out.print(i+1+") ");
									order.printOrderItem();
									roomID = null; // RESET ROOM ID TO NULL
								}
								System.out.println("----------------------------------------------------------------------------------");
								//dataStore.getOrderList().clear(); // clear orderlist, might be optional (testing)
								break; // break out of for loop
							}
						}
						if(!validateMenuItem(dataStore, itemID))
							throw new Exception("Food Index not in DB");
						
						while(itemID != 0)// while itemID(index) != 0, user can keep adding food
						{
							addOrders(dataStore,itemID,roomID);
							System.out.print("Please Enter Food Index, press 0 to confirm order ");
							input = scn.nextLine();
							if (input.isEmpty())
							throw new Exception("Empty Entry");	
							itemID = Integer.parseInt(input);
						}
						if(itemID == 0)// press 0 to confirm order
						{
							System.out.println("---------------------------------Room " +roomID + " Order---------------------------------");
							System.out.println("ID Food" + "\t\t\t\tPrice" + "\tDescription");
							for (  i = 0; i < dataStore.getOrderList().size();i++)
							{
								order = dataStore.getOrderList().get(i);
								System.out.print(i+1+") ");
								order.printOrderItem();
								roomID = null; // RESET ROOM ID TO NULL
							}
							System.out.println("----------------------------------------------------------------------------------");
							//dataStore.getOrderList().clear(); // clear orderlist, might be optional (testing)
							break; // break out of for loop
						}
					}
					else{System.out.println("Error: Room has not been checked in!");}
				}
			}
			
		}catch(Exception e){System.out.println("Error: " + e.getMessage());}
	}
	/**
	 * Add Orders to an order database
	 * 
	 * @param dataStore		Add orders data
	 * @param itemID		Gets the itemID keyed in by user
	 * @param roomID		adds Room ID into the OrderList
	 * 
	 */
	public static void addOrders(DataStore dataStore,int itemID, String roomID)
	{
		for (int i = 0; i < dataStore.getMenuList().size(); i++)
		{
			menu = dataStore.getMenuList().get(i);
			if(itemID - 1 == i)
			{
				menu.getItemName();
				menu.getItemPrice();
				menu.getItemDescription();
				
				Order order = new Order(menu.getItemName(), menu.getItemPrice(),menu.getItemDescription(),roomID );
				dataStore.getOrderList().add(order);	
			}
		}
	}
	/**
	 * Compute total amount for room service
	 * 
	 * @param dataStore		gets order data from orderlist
	 * @param roomID		gets the food items from order list using room ID
	 * 
	 * @return total		returns room total
	 */
	public static double computeRoomServiceTotal(DataStore dataStore, String roomID)
	{
		double total = 0.0;

		for(int i = 0; i < dataStore.getOrderList().size(); i++)
		{
			order = dataStore.getOrderList().get(i);
			if(roomID.equals(order.getRoomID()))
			{
				total+=order.getItemPrice();
			}
			
		}	
		return total;
	}
	/**
	 * Remove orders from order list
	 * 
	 * @param dataStore		Remove order data
	 * @param roomID		gets the food items from order list using room ID
	 * 
	 */
	public static void removeOrders(DataStore dataStore, String roomID)
	{
		for(int i = 0; i < dataStore.getOrderList().size(); i++)
		{
			order = dataStore.getOrderList().get(i);
			if(roomID.equals(order.getRoomID()))
			{
				dataStore.getOrderList().remove(i);
				i--;
			}
		}	
		//System.out.print("Orders Removed: ");
	}
	/**
	 * Print room service total into a bill
	 * 
	 * @param dataStore		Get order data from order list
	 * @param roomID		gets the food items from order list using room ID
	 * 
	 */
	public static void printRoomServiceBill(DataStore dataStore, String roomID)
	{
		double total = 0.0;
		System.out.println("\nRoom Service Bill----------------------------------------------------------------");
		for(int i = 0; i < dataStore.getOrderList().size(); i++)
		{
		order = dataStore.getOrderList().get(i);
		if(roomID.equals(order.getRoomID()))
		{
			total+=order.getItemPrice();
			System.out.println(i+1+") " +order.getItemName() + "\t\t\t\t$"+ df.format(order.getItemPrice()));
		}
		}
		System.out.println("---------------------------------------------------------------------------------");
	}
	/**
	 * Validate food items are in the menu
	 * 
	 * @param dataStore		Get order data from order list
	 * @param itemID		compares the food items from menu using item ID
	 * 
	 */
	private static boolean validateMenuItem(DataStore dataStore, int itemID) {
		for(int i = 0; i < dataStore.getMenuList().size();i++){
			if (itemID -1 == i)
				return true;
		}
		return false;
	}
	/**
	 * Method to print room service menu table
	 * 
	 * @param dataStore		Get order data from order list
	 * 
	 */
	private static void printAllItem(DataStore dataStore){
		System.out.println("-------------------------------------Room Service Menu-------------------------------------");
		System.out.println("ID Food" + "\t\t\t\tPrice" + "\tDescription");
				
		for (int i = 0; i < dataStore.getMenuList().size();i++){
			menu = dataStore.getMenuList().get(i);
			System.out.print(i+1+") ");
			menu.printMenuItem();
		}		
		System.out.println("-------------------------------------------------------------------------------------------");
	}
}



