/**
 Represents the Guest Manager class that let user change the application setting

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE  LAM WEN QI
 @version 1.0
 @since 2016-03-31
*/

package assignment.util;

import java.util.*;
import assignment.model.*;
import assignment.model.CreditCard.Month;

public class GuestMgr{

	private static Scanner scn = new Scanner(System.in);
	private static int choice;
	private static Guest guest = null;

	/** basic interface to input and store customer's information
	 * 
	 * @param dataStore   the guest data
	 */
	public static boolean createGuest(DataStore dataStore){	
		boolean flag  = true;
		System.out.println("----Create new Guest----");	
		try{
			System.out.print("Enter Identity :");
			String identity = scn.nextLine();
			if (identity.isEmpty()){
				flag = false;
				System.out.println("Empty Entry");
				return flag;
			}
			else if (dataStore.getGuestHashtable().containsKey(identity)){
				flag = false;
				System.out.println("Identiy is already in database!");
				return flag;
			}
			
			System.out.print("Enter Name :");
			String name = scn.nextLine();
			if (name.isEmpty())
				throw new Exception("Name is empty!");
			
			System.out.print("Enter Address :");
			String address = scn.nextLine();
			if (address.isEmpty())
				throw new Exception("Address is empty!");
			
			System.out.print("Enter Country :");
			String country = scn.nextLine();
			if (country.isEmpty())
				throw new Exception("Country is empty!");
			
			System.out.print("Enter Gender :");
			String input = scn.nextLine();
			input = input.toUpperCase();
			if (input.isEmpty())
				throw new Exception("Invalid Entry!");
			char gender = input.charAt(0);
			
			System.out.print("Enter Nationality :");
			String nationality = scn.nextLine();
			if (nationality.isEmpty())
				throw new Exception("Nationality is empty!");
			
			System.out.print("Enter Contact :");
			String contact = scn.nextLine();
			if (contact.isEmpty())
				throw new Exception("Contact Number is empty!");
			
			System.out.println("\n--Credit Card Information--");
			System.out.print("Enter CreditCard Number :");
			String ccNum = scn.nextLine();
			if (ccNum.isEmpty())
				throw new Exception("CreditCard Number is empty!");
			
			System.out.print("Enter Expiration Month :");
			input = null;
			input = scn.nextLine();
			if (input.isEmpty())
				throw new Exception("Month is empty!");
			Month ccMonth = validateCCMonth(input);
			
			System.out.print("Enter Expiration Year :");
			input = null;
			input = scn.nextLine();
			int ccYear = 0;
			if(input.isEmpty())
				throw new Exception("Year is empty!");
			ccYear = Integer.parseInt(input);
			ccYear = validateCCYear(ccYear);
		
			System.out.print("Enter CSV :");
			input = null;
			input = scn.nextLine();
			int csv = 0;
			if(input.isEmpty())
				throw new Exception("CSV is empty!");
			csv = Integer.parseInt(input);
			csv = validateCSV(csv);
			
			if (validateCreditCard(ccMonth, ccYear, csv)){
				CreditCard creditCard = new CreditCard(ccNum, ccMonth, ccYear, csv);
				Guest guest = new Guest(identity, name, creditCard, address, country, gender, nationality, contact);	
				guest.print();
				
				if(validateConfirm()){
					dataStore.getGuestHashtable().put(identity, guest);
					System.out.println("New Guest Created!");
				}
				else{
					flag = false;
					System.out.println("Guest Not Created!");
					return flag;
				}	
			}
		} catch(Exception e){System.out.println("Error: " + e.getMessage()); flag = false;}
		return flag;
	}
	/**
	 * Updates the guest information in the dataStore 
	 * @param dataStore:	 guest data 
	 * 
	 */
	public static void updateGuest(DataStore dataStore){ 
		System.out.println("----Update Guest----");
		try{
			System.out.print("Enter identity :");
			String id = scn.nextLine();
			if(id.isEmpty())
				throw new Exception("Identity is empty");
			else if(dataStore.getGuestHashtable().containsKey(id)){
				guest = dataStore.getGuestHashtable().get(id);
				guest.print();
				System.out.println("(1) Edit CreditCard Detail");
				System.out.println("(2) Edit Address Detail");
				System.out.println("(3) Edit Contact Detail");
				System.out.println("(4) Exit Update");
				System.out.print("Please enter your choice: ");
				
				choice = Integer.parseInt(scn.nextLine());
				switch(choice){
				case 1:
					System.out.println("\n--Credit Card Information--");
					System.out.print("Enter CreditCard Number :");
					String ccNum = scn.nextLine();
					if (ccNum.isEmpty())
						throw new Exception("CreditCard Number is empty!");
					
					System.out.print("Enter Expiration Month :");
					String input = null;
					input = scn.nextLine();
					if (input.isEmpty())
						throw new Exception("Month is empty!");
					Month ccMonth = validateCCMonth(input);
					
					System.out.print("Enter Expiration Year :");
					input = null;
					input = scn.nextLine();
					int ccYear = 0;
					if(input.isEmpty())
						throw new Exception("Year is empty!");

					ccYear = Integer.parseInt(input);
					ccYear = validateCCYear(ccYear);
				
					System.out.print("Enter CSV :");
					input = null;
					input = scn.nextLine();
					int csv = 0;
					if(input.isEmpty())
						throw new Exception("CSV is empty!");
					
					csv = Integer.parseInt(input);
					csv = validateCSV(csv);
					
					if (validateCreditCard(ccMonth, ccYear, csv)){
					
						CreditCard creditCard = new CreditCard(ccNum, ccMonth, ccYear, csv);
						// create print CC method!
						if (validateConfirm()){
							guest.setCreditCard(creditCard);
							System.out.println("CreditCard updated!");}
						else
							System.out.println("CreditCard Not updated!");
					}
						
					break;
				case 2:
					System.out.print("Enter Address :");
					String add = scn.nextLine();
					if(add.isEmpty())
						throw new Exception("Address is empty!");
					
					System.out.println("New Address: " + add);
					if (validateConfirm()){
						guest.setAddress(add);
						System.out.println("Address updated!");}
					else
						System.out.println("Address Not updated!");
					break;
				case 3:
					System.out.print("Enter Contact :");
					String contact = scn.nextLine();
					if(contact.isEmpty())
						throw new Exception("Contact is empty!");
					
					System.out.println("New Contact: " + contact);
					if (validateConfirm()){
						guest.setContact(contact);
					System.out.println("Contact updated!");}
					else
						System.out.println("Contact Not updated!");
					break;
				case 4:
					break;
				default:
					throw new Exception("Invalid Entry!");
				}
			}
			else
				throw new Exception("Guest not in Database!");
		} catch(Exception e){System.out.println("Error: " + e.getMessage());}

	}
	/**
	 *Used to search for guest in dataStore
	 * @param dataStore: 	the guest data
	 */
	
	public static void searchGuest(DataStore dataStore){

		System.out.println("----Search Guest----");
		try{
			System.out.print("Enter identity :");
			String id = scn.nextLine();
			if(id.isEmpty())
				throw new Exception("Identiy is empty!");
			
			if(dataStore.getGuestHashtable().containsKey(id)){
				guest = dataStore.getGuestHashtable().get(id);
				guest.print();
			}
			else
				throw new Exception("Guest not in Database!");
			
		} catch(Exception e){System.out.println("Error: " + e.getMessage());}
		
	}
	
	/**
	 * validate the credit cards expiry date month for 
	 * 
	 * @param Month : input by the user for ccMonth
	 * 
	 * @return ccMonth
	 */
	private static Month validateCCMonth(String ccMonth){
		if (ccMonth.toUpperCase().equals("JAN") || ccMonth.equals("01") || ccMonth.toUpperCase().equals("JANUARY") || ccMonth.equals("1"))
			return Month.JAN;
		if (ccMonth.toUpperCase().equals("FEB") || ccMonth.equals("02") || ccMonth.toUpperCase().equals("FEBURARY")|| ccMonth.equals("2"))
			return Month.FEB;
		if (ccMonth.toUpperCase().equals("MAR") || ccMonth.equals("03") || ccMonth.toUpperCase().equals("MARCH")|| ccMonth.equals("3"))
			return Month.MAR;
		if (ccMonth.toUpperCase().equals("APR") || ccMonth.equals("04") || ccMonth.toUpperCase().equals("APRIL")|| ccMonth.equals("4"))
			return Month.APR;
		if (ccMonth.toUpperCase().equals("MAY") || ccMonth.equals("05")|| ccMonth.equals("5"))
			return Month.MAY;
		if (ccMonth.toUpperCase().equals("JUN") || ccMonth.equals("06") || ccMonth.toUpperCase().equals("JUNE")|| ccMonth.equals("6"))
			return Month.JUN;
		if (ccMonth.toUpperCase().equals("JUL") || ccMonth.equals("07") || ccMonth.toUpperCase().equals("JULY")|| ccMonth.equals("7"))
			return Month.JUL;
		if (ccMonth.toUpperCase().equals("AUG") || ccMonth.equals("08") || ccMonth.toUpperCase().equals("AUGUST")|| ccMonth.equals("8"))
			return Month.AUG;
		if (ccMonth.toUpperCase().equals("SEP") || ccMonth.equals("09") || ccMonth.toUpperCase().equals("NOVEMBER")|| ccMonth.equals("9"))
			return Month.SEP;
		if (ccMonth.toUpperCase().equals("OCT") || ccMonth.equals("10") || ccMonth.toUpperCase().equals("OCTOBER"))
			return Month.OCT;
		if (ccMonth.toUpperCase().equals("NOV") || ccMonth.equals("11") || ccMonth.toUpperCase().equals("NOVEMBER"))
			return Month.NOV;
		if (ccMonth.toUpperCase().equals("DEC") || ccMonth.equals("12") || ccMonth.toUpperCase().equals("DECEMBER"))
			return Month.DEC;
		else
			return null;
	}
	/**
	 * Validate Credit Card expiry date
	 * @param ccYear 	: credit card 
	 * @return	0		: credit card expired
	 * 			ccYear	: credit card year
	 */
	
	private static int validateCCYear(int ccYear){
		if (ccYear < 2016)
			return 0;
		else
			return ccYear;
	}
	/**
	 * Validate Credit Card ccv
	 * @param csv
	 * @return 0:	error
	 * 		 cvs:	Cvs number
	 */
	public static int validateCSV(int csv){
		if (csv < 100 || csv >999)
			return 0;
		else
			return csv;
	}
	/**
	 * Validate Credit card details 
	 * @param ccMonth	: validated month
	 * @param ccYear	: validated month
	 * @param csv		: validate csv number
	 * @return true		: if all the validations are right
	 *		   false	: if any one of the validations fails 
	 */
	
	private static boolean validateCreditCard(Month ccMonth, int ccYear, int csv){
		if (ccMonth != null && ccYear !=0 && csv !=0)
			return true;
		else
			System.out.println("Invalid Credit Card Entry!\n");
		return false;
	}
	/** Validates the confirmation of customer
	 * 
	 * @return  true 	: user key in Y 
	 * 			false	: user key in N
	 */
	
	private static boolean validateConfirm(){
		try{
			System.out.print("Confirm request? (Y/N): ");
			String input = scn.nextLine();
			input = input.toUpperCase();
			if (input.isEmpty())
				throw new Exception("Input is empty!");
			else if(input.toUpperCase().charAt(0) == 'Y'){
				return true;
			} else if (input.toUpperCase().charAt(0) != 'N')
				return false;
			else
				System.out.println("Invalid Entry!");
		} catch(Exception e){System.out.println("Error: " + e.getMessage());}
		return false;

	}

}
