/**
 * 
 */
package org.xbase.com.manager;

/**
 * @author VAMSI KRISHNA MYALAPALLI (vamsikrishna.vasu@gmail.com)
 *
 */
public class InventoryManager {

	private static StringBuilder inventoryLog=null;
	private static long collectionsCreated, indexesCreated, schemasCreated;
	
	public static void log(String currentMessage) {
		inventoryLog.append(currentMessage);
	}
	
	public static String getTimeStamp() {
		
		return null;
	}
	
	public static void writeInventoryToLog() {
		inventoryLog.append("");
	}
	
}
