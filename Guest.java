/**
Guest Class to store guest objects

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE LAM WEN QI
 @version 1.0
 @since 2016-03-17
*/
package assignment.model;

import java.io.Serializable;

public class Guest implements Serializable{
	
	/** 
	Identity of a guest, can be Driving License/Passport
	*/
	private String identity;
	
	/** 
	Name of guest
	*/
	private String name;
	
	/** 
	Creditcard information from a guest
	*/
	private CreditCard ccCard;
	
	/** 
	Address information of a guest
	*/
	private String address;
	
	/** 
	Country of address information of a guest
	*/
	private String country;
	
	/** 
	Gender information of a guest
	*/
	private char gender;

	/** 
	Nationality information of a guest
	*/
	private String nationality;
	
	/** 
	Contact information of a guest
	*/
	private String contact;

	
    /**
    * Creates a new Guest object
    * 
    * @param identity		: Identity of a guest, can be Driving License/Passport
	* @param name			: Name of the Guest
	* @param ccCard			: Creditcard information from a guest
    * @param address		: Address information of a guest
    * @param gender			: Gender information of a guest
    * @param nationality	: Nationality information of a guest
    * @param contact		: Contact information of a guest
    */	
	public Guest(String identity, String name, CreditCard ccCard, String address, String country, char gender, String nationality, String contact) {		
		this.identity = identity;
		this.name = name;
		this.ccCard = ccCard;
		this.address = address;
		this.country = country;
		this.gender = gender;
		this.nationality = nationality;
		this.contact = contact;	
	}
	
	
    /**
    * get the identity of a guest
    * 
    * @return identity
    */
	public String getIdentity() { return identity; }	
	
    /**
    * get the name of guest
    * 
    * @return name
    */
	public String getName(){ return name;}
	
    /**
    * get the guest credit card information
    * 
    * @return ccCard
    */
	public CreditCard getCreditCard(){ return ccCard;}
	
    /**
    * get the address information of guest
    * 
    * @return address
    */
	public String getAdd() { return address; }
	
    /**
    * get the country of address information of guest
    * 
    * @return country
    */
	public String getCountry() { return country; }
	
    /**
    * get the gender information of guest
    * 
    * @return gender
    */
	public char getGender() { return gender; }
	
    /**
    * get the nationality information of guest
    * 
    * @return nationality
    */
	public String getNationality() { return nationality; }
	
    /**
    * get the contact information of guest
    * 
    * @return contact
    */
	public String getContact() { return contact; }

	/**
	 * Set the CreditCard information of a guest
	 * 
	 * @param address
	 *            : This indicates the new address information
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Set the Contact information of a guest
	 * 
	 * @param contact
	 *            : This indicates the new contact information
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}

	/**
	 * Set the CreditCard information of a guest
	 * 
	 * @param ccCard
	 *            : This indicates the new CreditCard information
	 */
	public void setCreditCard(CreditCard ccCard){
		this.ccCard = ccCard;
	}
	
	/**
	 * Prints out guest's information
	 *
	 */
	public void print(){
		System.out.println("Guest Detail\n");
		System.out.println("Identity: "+identity +"\tName : " + name);
		System.out.println("Gender: "+gender + "\tNationality: " + nationality);
		System.out.println("Contact Number: " + contact);
		System.out.println("Address: "+address);
		System.out.println("Country: "+country);
		System.out.println("CreditCard Details: "+ ccCard.getCCNumber() + "\tMM:"+ ccCard.getCCMonth() + "\tYY:" + ccCard.getCCYear());	
	}
	
	/**
	 * Prints out CC information
	 *
	 */
	public void printCreditCard(){
		System.out.println("CreditCard Details: "+ ccCard.getCCNumber() + "\tMM:"+ ccCard.getCCMonth() + "\tYY:" + ccCard.getCCYear());	
	}

}
