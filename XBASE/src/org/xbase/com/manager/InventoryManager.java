/**
 * 
 */
package org.xbase.com.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.xbase.com.actions.MessageType;
import org.xbase.com.actions.MigratorActions;
import org.xbase.com.constants.ActionConstants;
import org.xbase.com.constants.MessageConstants;
import org.xbase.com.constants.MigratorConstants;
import org.xbase.com.constants.PatternConstants;
import org.xbase.com.util.IOUtil;
import org.xbase.com.util.PrintUtil;

/**
 * @author VAMSI KRISHNA MYALAPALLI (vamsikrishna.vasu@gmail.com)
 *
 */

public class InventoryManager {

	private static StringBuilder inventoryLog = new StringBuilder();
	private static long startTime, duration, seconds, minutes;
	private static long collectionsCreated, collectionsDeleted, collectionsEmbedded, databasesCreated, databasesDeleted;
	private static long indexesCreated, indexesDeleted, schemasCreated, schemasDeleted;
	private static List<String> createdCollectionList = new ArrayList<String>();
	private static List<String> createdDatabaseList = new ArrayList<String>();
	private static List<String> createdIndexList = new ArrayList<String>();
	private static List<String> createdSchemaList = new ArrayList<String>();
	private static List<String> deletedCollectionList = new ArrayList<String>();
	private static List<String> deletedDatabaseList = new ArrayList<String>();
	private static List<String> deletedIndexList = new ArrayList<String>();
	private static List<String> deletedSchemaList = new ArrayList<String>();
	private static List<String> embeddedCollectionsList = new ArrayList<String>();
	
	/**
	 * This method will be invoked when the migration begins
	 */
	public static void startMigration() {
		startTime = System.nanoTime();
		inventoryLog.append(PatternConstants.TABSPACINGDOUBLE + MigratorConstants.XBASE + PatternConstants.SPACESEPERATOR + MigratorConstants.MIGRATIONREPORT + PatternConstants.LINESEPERATORDOUBLE);
		inventoryLog.append(PatternConstants.LINEPATTERNASTERIK + PatternConstants.LINESEPERATOR);
		inventoryLog.append(MigratorConstants.DATAMIGRATIONSTART + PatternConstants.DATASEPERATOR + PatternConstants.SPACESEPERATOR + getTimeStamp() + PatternConstants.LINESEPERATOR);
		inventoryLog.append(PatternConstants.LINEPATTERNHIPHEN + PatternConstants.LINESEPERATOR);
	}
	
	public static void log(String currentMessage, MessageType messageType) {
		inventoryLog.append(messageType + PatternConstants.DATASEPERATOR + currentMessage + PatternConstants.LINESEPERATOR);
	}
	
	public static void log(String currentMessage) {
		log(currentMessage, MessageType.INFO);
	}
	
	public static Timestamp getTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	/*
	 * Invoke this method at the end.
	 * */
	public static void writeInventoryToLog() {
		inventoryLog.append(ActionConstants.DATABASESCREATED + PatternConstants.DATASEPERATOR + databasesCreated + PatternConstants.LINESEPERATOR);
		for(String currentDatabase : createdDatabaseList)
			inventoryLog.append(PatternConstants.TABSPACING + currentDatabase + PatternConstants.LINESEPERATOR);
		inventoryLog.append(ActionConstants.SCHEMASCREATED + PatternConstants.DATASEPERATOR + schemasCreated + PatternConstants.LINESEPERATOR);
		for(String currentSchema : createdSchemaList)
			inventoryLog.append(PatternConstants.TABSPACING + currentSchema + PatternConstants.LINESEPERATOR);
		inventoryLog.append(ActionConstants.COLLECTIONSCREATED + PatternConstants.DATASEPERATOR + collectionsCreated + PatternConstants.LINESEPERATOR);
		for(String currentCollection : createdCollectionList)
			inventoryLog.append(PatternConstants.TABSPACING + currentCollection + PatternConstants.LINESEPERATOR);
		inventoryLog.append(ActionConstants.INDEXESCREATED + PatternConstants.DATASEPERATOR + indexesCreated + PatternConstants.LINESEPERATORDOUBLE);
		
		
		
		inventoryLog.append(ActionConstants.COLLECTIONSDELETED + PatternConstants.DATASEPERATOR + collectionsDeleted + PatternConstants.LINESEPERATOR);
		inventoryLog.append(ActionConstants.INDEXESDELETED + PatternConstants.DATASEPERATOR + indexesDeleted + PatternConstants.LINESEPERATOR);
		inventoryLog.append(ActionConstants.SCHEMASDELETED + PatternConstants.DATASEPERATOR + schemasDeleted + PatternConstants.LINESEPERATOR);
		inventoryLog.append(ActionConstants.DATABASESDELETED + PatternConstants.DATASEPERATOR + databasesDeleted + PatternConstants.LINESEPERATORDOUBLE);
		
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
		else if(MigratorActions.DATABASECREATED.equals(currentAction)) {
			createdDatabaseList.add(objectName);
			databasesCreated++;
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
		else if(MigratorActions.DATABASEDELETED.equals(currentAction)) {
			deletedDatabaseList.add(objectName);
			databasesDeleted++;
		}
		else {
			// Should not reach here
			throw new RuntimeException("Unknown Action when Updating Inventory");
		}
	}
	
	public static void endMigration(String inventoryFilePath, String inventoryFileName) {
		duration = System.nanoTime()-startTime;
		minutes = TimeUnit.MINUTES.convert(duration, TimeUnit.NANOSECONDS);
		seconds = TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS)%60;
		writeInventoryToLog();
		inventoryLog.append(MigratorConstants.DATAMIGRATIONCOMPLETE + PatternConstants.DATASEPERATOR + getTimeStamp() + PatternConstants.LINESEPERATOR);
		inventoryLog.append(MigratorConstants.ELAPSEDTIME + PatternConstants.DATASEPERATOR + minutes + " minute(s)" + PatternConstants.SPACESEPERATOR + seconds + " seconds" + PatternConstants.LINESEPERATOR);
		inventoryLog.append(PatternConstants.LINEPATTERNASTERIK + PatternConstants.LINESEPERATOR);
		PrintUtil.log(MessageConstants.INFO + ActionConstants.INVENTORYFILECREATED + PatternConstants.DATASEPERATOR + inventoryFilePath + inventoryFileName);
		IOUtil.writeToFile(inventoryFilePath, inventoryFileName, inventoryLog.toString());
		inventoryLog.setLength(0);
	}
}
