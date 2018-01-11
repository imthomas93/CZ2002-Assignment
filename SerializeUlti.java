/**
 Represents the SerializeUlti class that read in data from .ser file
 and write data into .ser file

 * @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE  LAM WEN QI
 @version 1.0
 @since 2016-03-31
*/

package assignment.util;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import assignment.model.DataStore;
public class SerializeUlti {
	

	/**
	 * read in file
	 * 
	 * @param filename : filename of the stored data
	 * 
	 * return obj
	 */
	public static Object readSerializedObject(String filename) {
		Object obj = null;
		FileInputStream fis = null;
		ObjectInputStream in = null;
		try {
			fis = new FileInputStream(filename);
			in = new ObjectInputStream(fis);
			obj = (DataStore) in.readObject();
			in.close();
		} catch (IOException ex) {
			//ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			//ex.printStackTrace();
		}
		return obj;
		
	}

	/**
	 * write to file
	 * 
	 * @param filename	: filename of the stored data
	 * @param obj 		: object to be written to the file
	 */
	
	public static void writeSerializedObject(String filename,Object obj) {
		
		
		FileOutputStream fos = null;
		ObjectOutputStream out = null;
		try {
			fos = new FileOutputStream(filename);
			out = new ObjectOutputStream(fos);
			out.writeObject(obj);
			out.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
