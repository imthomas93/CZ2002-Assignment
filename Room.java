/**
Room Class to store room objects

 @author 	THOMAS LIM JUN WEI
 			LIM ZI YANG
 			LIM HAO ZHE
 			GENEVIEVE LAM WEN QI
 @version 1.0
 @since 2016-03-17
*/
package assignment.model;
import java.io.Serializable;
import java.util.Scanner;

public class Room implements Serializable{

	/**
	 * Room number of room
	 */
	private String roomNumber;
	
	/**
	 * Type of room
	 * (e.g. "Single", "Twin-Sharing", "Double", "Deluxe","VIP Suite", "NULL")
	 */
	private RoomType roomType;
	
	/**
	 * Type of bed in room
	 * (e.g. "Single", "Double", "Master", "NULL")
	 */
	private BedType bedType;
	
	/**
	 * Current status of room 
	 * (e.g. "Vacant", "Occupied", "Reserved", "Under maintenance", "NULL")
	 */
	private RoomStatus roomStatus;
	
	/**
	 * Rooming facing or not facing sea
	 * (e.g. "Facing sea view","Non-facing sea view", "NULL")
	 */
	private FacingSea facingSea;
	
	/**
	 * Wifi availability of room
	 * (e.g. "Enabled", "Disabled", "NULL")
	 */
	private WifiStatus wifiStatus;
	
	/**
	 * Smoking availability of room
	 * (e.g. "Smoking room", "Non smoking room", "NULL")
	 */
	private SmokingStatus smokingStatus;
	
	/**
	 * Room rate of room
	 */
	private double roomRate;
	
	/**
	 * Create a new room.
	 * 
	 * @param room_Num			Room number of room.
	 * @param room_Type			Type of room.
	 * @param bed_Type			Type of bed in room
	 * @param room_Status		Current status of room 
	 * @param wifi_Status		Wifi availability of room
	 * @param facing_Sea		Rooming facing or not facing sea
	 * @param smoking_Status	Smoking availability of room
	 * @param roomRate			Room rate of room
	 */
	public Room(String room_Num, RoomType room_Type, BedType bed_Type, RoomStatus room_Status, WifiStatus wifi_Status, FacingSea facing_Sea,SmokingStatus smoking_Status, double roomRate){
	roomNumber=room_Num;
	roomType=room_Type;
	bedType=bed_Type;
	roomStatus=room_Status;
	facingSea=facing_Sea;
	wifiStatus=wifi_Status;
	smokingStatus=smoking_Status;
	this.roomRate = roomRate;
}
	
/**
 * Gets room number of room.
 * 
 * @return room number of room. 
 */
public String getRoomNumber(){return roomNumber;}

/**
 * Gets room type of room.
 * 
 * @return room type of room. 
 */
public RoomType getRoomType(){return roomType;}

/**
 * Gets bed type of room.
 * 
 * @return bed type of room. 
 */
public BedType getBedType(){return bedType;}

/**
 * Gets facing sea status of room.
 * 
 * @return facing sea status of room. 
 */
public FacingSea getFacingSea(){ return facingSea;}

/**
 * Gets wifi status of room.
 * 
 * @return wifi status of room. 
 */
public WifiStatus getWifiStatus(){ return wifiStatus;}

/**
 * Gets room status of room.
 * 
 * @return room status of room. 
 */
public RoomStatus getRoomStatus(){return roomStatus;}

/**
 * Gets smoking status of room.
 * 
 * @return smoking status of room. 
 */
public SmokingStatus getSmokingStatus(){return smokingStatus;}

/**
 * Gets room rate of room.
 * 
 * @return room rate of room. 
 */
public double getRoomRate(){return roomRate;}

/**
 * Changes room type of room.
 * 
 * @param roomType New room type of room.
 */
public void setRoomType(RoomType roomType){this.roomType=roomType;}

/**
 * Changes bed type of room.
 * 
 * @param bedType New bed type of room.
 */
public void setBedType(BedType bedType){this.bedType=bedType;}

/**
 * Changes facing sea status of room.
 * 
 * @param facingSea New facing status of room.
 */
public void setFacingSea(FacingSea facingSea){this.facingSea=facingSea;}

/**
 * Changes smoking status of room.
 * 
 * @param smokingStatus New smoking status of room.
 */
public void setSmokingStatus(SmokingStatus smokingStatus){this.smokingStatus =smokingStatus;}

/**
 * Changes WIFI status of room.
 * 
 * @param wifiStatus New WIFI status of room.
 */
public void setWifiStatus(WifiStatus wifiStatus){this.wifiStatus=wifiStatus;}

/**
 * Changes status of room.
 * 
 * @param roomStatus New room status
 */
public void setRoomStatus(RoomStatus roomStatus){this.roomStatus=roomStatus;}

/**
 * Changes room rate of room.
 * 
 * @param roomRate New room rate of room.
 */
public void setRoomRate(double roomRate){this.roomRate = roomRate;}

public enum RoomType{
	SINGLE,TWIN_SHARING,DOUBLE,DELUXE,VIP_SUITE,NULL;
}

public enum BedType{
	SINGLE,DOUBLE,MASTER,NULL;
}

public enum RoomStatus{
	VACANT,OCCUPIED,RESERVED,UNDER_MAINTENANCE,NULL;
}

public enum FacingSea{
	FACING_SEA_VIEW,NON_FACING_SEAVIEW,NULL;
}

public enum WifiStatus{
	ENABLED,DISABLED,NULL;
}

public enum SmokingStatus{
	SMOKING_ROOM,NON_SMOKING_ROOM,NULL;
}

/**
 * Display room information
 */
public void printRoomDetail(){
	System.out.println("Room details of #"+this.getRoomNumber());
	System.out.println("Room Type: "+this.roomType );
	System.out.println("Bed Type: "+this.bedType );
	System.out.println("Room Status: "+this.roomStatus);
	
	//Get facing sea status
	if(this.facingSea == FacingSea.FACING_SEA_VIEW)
		System.out.println("View: "+FacingSea.FACING_SEA_VIEW);
	else if(this.facingSea == FacingSea.NON_FACING_SEAVIEW)
		System.out.println("View: "+FacingSea.NON_FACING_SEAVIEW);

	//Get Wifi status
	if(this.wifiStatus == WifiStatus.ENABLED)
		System.out.println("WIFI Status: "+WifiStatus.ENABLED);
	else if(this.wifiStatus == WifiStatus.DISABLED)
		System.out.println("WIFI Status: "+WifiStatus.DISABLED);
	
	//Get smoking status
	if(this.smokingStatus == SmokingStatus.NON_SMOKING_ROOM)
		System.out.println("Smoking Status: "+SmokingStatus.NON_SMOKING_ROOM);
	else if(this.smokingStatus == SmokingStatus.SMOKING_ROOM)
		System.out.println("Smoking Status: "+SmokingStatus.SMOKING_ROOM);
}

}
