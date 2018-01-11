/**
 Interface Class for DataStoreDao class

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE  LAM WEN QI
 @version 1.0
 @since 2016-03-15
*/



package assignment.util.datastore;

import assignment.model.DataStore;

public interface DataStoreDao {
	
	/**
	 *retrieve DataStore file
	 * 
	 * @param filename 	: file name of the stored datastore
	 * 
	 * return DataStore
	 */
	public DataStore retrieve(String filename) ;
	
	/**
	 *create DataStore file
	 * 
	 * @param filename 	: file name of the stored datastore
	 * @param datastore : datastore file used by the user
	 * 
	 * return DataStore
	 */
	public DataStore create(DataStore datastore,String filename) ;
	
	/**
	 *update DataStore file
	 * 
	 * @param filename 	: file name of the stored datastore
	 * @param datastore : datastore file used by the user
	 * 
	 * return DataStore
	 */
	public DataStore update(DataStore datastore,String filename) ;
	
	/**
	 *delete DataStore file
	 * 
	 * @param filename 	: file name of the stored datastore
	 * 
	 * return DataStore
	 */
	public DataStore delete(String filename) ;

}
