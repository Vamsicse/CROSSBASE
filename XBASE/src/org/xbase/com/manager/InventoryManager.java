/**
 * 
 */
package org.xbase.com.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.xbase.com.actions.MigratorActions;
import org.xbase.com.constants.ActionConstants;
import org.xbase.com.constants.MigratorConstants;
import org.xbase.com.constants.PatternConstants;
import org.xbase.com.util.IOUtil;

/**
 * @author VAMSI KRISHNA MYALAPALLI (vamsikrishna.vasu@gmail.com)
 *
 */

// TODO: List schema, collection name in inventory

public class InventoryManager {

	private static StringBuilder inventoryLog = new StringBuilder();
	private static long collectionsCreated, collectionsDeleted, indexesCreated, indexesDeleted, schemasCreated, schemasDeleted;
	private static List<String> createdCollectionList = new ArrayList<String>();
	private static List<String> createdIndexList = new ArrayList<String>();
	private static List<String> createdSchemaList = new ArrayList<String>();
	private static List<String> deletedCollectionList = new ArrayList<String>();
	private static List<String> deletedIndexList = new ArrayList<String>();
	private static List<String> deletedSchemaList = new ArrayList<String>();
	
	/**
	 * This method will be invoked when the migration begins
	 */
	public static void startMigration() {
		inventoryLog.append(PatternConstants.LINEPATTERNASTERIK + PatternConstants.LINESEPERATOR);
		inventoryLog.append(MigratorConstants.DATAMIGRATIONSTART + PatternConstants.DATASEPERATOR + PatternConstants.SPACESEPERATOR + getTimeStamp() + PatternConstants.LINESEPERATOR);
		inventoryLog.append(PatternConstants.LINEPATTERNHIPHEN + PatternConstants.LINESEPERATOR);
	}
	
	public static void log(String currentMessage) {
		inventoryLog.append(currentMessage);
	}
	
	public static String getTimeStamp() {
		return new Timestamp(System.currentTimeMillis()).toString();
	}
	
	/*
	 * Invoke this method at the end.
	 * */
	public static void writeInventoryToLog() {
		inventoryLog.append(ActionConstants.COLLECTIONSCREATED + PatternConstants.DATASEPERATOR + collectionsCreated + PatternConstants.LINESEPERATOR);
		for(String currentCollection : createdCollectionList)
			inventoryLog.append(PatternConstants.TABSPACING + currentCollection + PatternConstants.LINESEPERATOR);
		
		inventoryLog.append(ActionConstants.INDEXESCREATED + PatternConstants.DATASEPERATOR + indexesCreated + PatternConstants.LINESEPERATOR);
		inventoryLog.append(ActionConstants.SCHEMASCREATED + PatternConstants.DATASEPERATOR + schemasCreated + PatternConstants.DOUBLELINESEPERATOR);
		
		inventoryLog.append(ActionConstants.COLLECTIONSDELETED + PatternConstants.DATASEPERATOR + collectionsDeleted + PatternConstants.LINESEPERATOR);
		inventoryLog.append(ActionConstants.INDEXESDELETED + PatternConstants.DATASEPERATOR + indexesDeleted + PatternConstants.LINESEPERATOR);
		inventoryLog.append(ActionConstants.SCHEMASDELETED + PatternConstants.DATASEPERATOR + schemasDeleted + PatternConstants.LINESEPERATOR);
		
		inventoryLog.append(PatternConstants.LINEPATTERNHIPHEN + PatternConstants.LINESEPERATOR);
	}
	
	public static void updateInventory(MigratorActions currentAction, String objectName) {
		if(currentAction.equals(MigratorActions.COLLECTIONCREATED)) {
			createdCollectionList.add(objectName);
			collectionsCreated++;
		}
		else if(currentAction.equals(MigratorActions.INDEXCREATED)) {
			createdIndexList.add(objectName);
			indexesCreated++;
		}
		else if(currentAction.equals(MigratorActions.SCHEMACREATED)) {
			createdSchemaList.add(objectName);
			schemasCreated++;
		}
		else if(currentAction.equals(MigratorActions.COLLECTIONDELETED)) {
			deletedCollectionList.add(objectName);
			collectionsDeleted++;
		}
		else if(currentAction.equals(MigratorActions.SCHEMADELETED)) {
			deletedSchemaList.add(objectName);
			schemasDeleted++;
		}
		else if(currentAction.equals(MigratorActions.INDEXDELETED)) {
			deletedIndexList.add(objectName);
			indexesDeleted++;
		}
		else {
			// Should not reach here
			throw new RuntimeException("Unknown Action when Updating Inventory");
		}
	}
	
	public static void endMigration(String inventoryFilePath, String inventoryFileName) {
		writeInventoryToLog();
		inventoryLog.append(MigratorConstants.DATAMIGRATIONCOMPLETE + PatternConstants.DATASEPERATOR + getTimeStamp() + PatternConstants.LINESEPERATOR);
		inventoryLog.append(PatternConstants.LINEPATTERNASTERIK + PatternConstants.LINESEPERATOR);
		IOUtil.writeToFile(inventoryFilePath, inventoryFileName, inventoryLog.toString());
		inventoryLog.setLength(0);
	}
	
}
