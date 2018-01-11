/**
CreditCard Class to store guest objects

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE LAM WEN QI
 @version 1.0
 @since 2016-03-18
*/
package assignment.model;

import java.io.Serializable;

public class CreditCard implements Serializable{

	/** 
	CreditCard Number of a guest's creditcard
	*/
	private String ccNumber;
	
	/** 
	Expiration month of the creditcard
	*/
	private Month ccMonth;
	
	/** 
	Expiration year of the creditcard
	*/
	private int ccYear;
	
	/** 
	Card security code of the creditcard
	*/
	private int csv;

	
    /**
    * Creates a new CreditCard object
    * 
    * @param ccNumber		: CreditCard Number of a guest's creditcard
	* @param ccMonth		: Expiration month of the creditcard
    * @param ccYear			: Expiration year of the creditcard
    * @param csv			: Card security code of the creditcard
    */	
	public CreditCard(String ccNumber, Month ccMonth, int ccYear, int csv){
		this.ccNumber = ccNumber;
		this.ccMonth = ccMonth;

		this.ccYear = ccYear;
		this.csv = csv;
	}
	
    /**
    * get the CreditCard Number of a guest
    * 
    * @return ccNumber
    */
	public String getCCNumber(){ return ccNumber;}
	
    /**
    * get the Expiration month of the creditcard
    * 
    * @return ccMonth
    */
	public Month getCCMonth(){ return ccMonth;}
	
    /**
    * get the Expiration year of the creditcard
    * 
    * @return ccYear
    */
	public int getCCYear(){ return ccYear;}
	
    /**
    * get the Card security code of the creditcard
    * 
    * @return csv
    */
	public int getCsv(){return csv;}
	
	
	/**
	 * Set the CreditCard Number of a guest
	 * 
	 * @param ccNumber
	 *            : This indicates the new creditcard number
	 */
	public void setCCNum(String ccNumber){
		this.ccNumber = ccNumber;
	}
	
	/**
	 * Set the Expiration year of the creditcard
	 * 
	 * @param ccYear
	 *            : This indicates the new expiration year of the creditcard
	 */
	public void setCCYear(int ccYear){
		this.ccYear = ccYear;
	}
	
	/**
	 * Set the card security code of the creditcard
	 * 
	 * @param csv
	 *            : This indicates the new card security code
	 */
	public void setCSV(int csv){
		this.csv = csv;
	}
	
	/**
	Enumeration of Month of ccMonth
	JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC
	 */
	public enum Month{
		JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC;
	}
	
	/**
	 * Prints out CC information
	 *
	 */
	public void printCreditCard(){
		System.out.println("CreditCard Details: "+ this.getCCNumber() + "\tMM:"+ this.getCCMonth() + "\tYY:" + this.getCCYear());	
	}

}
