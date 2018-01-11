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
import assignment.util.SerializeUlti;

public class DataStoreDaoSerialMgr implements DataStoreDao {

	/**
	 *Create DataStore file
	 * 
	 * @param filename 	: file name of the stored datastore
	 * @param datastore : datastore file used by the user
	 * 
	 * return DataStore
	 */
	@Override
	public DataStore create(DataStore datastore,String filename) {
		SerializeUlti.writeSerializedObject(filename, datastore);
		return null;   
	}

	/**
	 *update DataStore file
	 * 
	 * @param filename 	: file name of the stored datastore
	 * @param datastore : datastore file used by the user
	 * 
	 * return DataStore
	 */
	@Override
	public DataStore update(DataStore datastore,String filename) {
		SerializeUlti.writeSerializedObject(filename, datastore);
		return null;
	}

	/**
	 *delete DataStore file
	 * 
	 * @param filename 	: file name of the stored datastore
	 * 
	 * return DataStore
	 */
	@Override
	public DataStore delete(String filename) {
		//not used
		return null;
	}

	
	/**
	 *retrieve DataStore file
	 * 
	 * @param filename 	: file name of the stored datastore
	 * 
	 * return DataStore
	 */
	@Override
	public DataStore retrieve(String filename) {
		return (DataStore) SerializeUlti.readSerializedObject(filename);
	}

}
