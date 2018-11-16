package org.xbase.com.manager;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.xbase.com.constants.ConfigConstants;
import org.xbase.com.constants.MigratorConstants;
import org.xbase.com.migrator.TableMigrator;

public class MigrationManager {

	private MigrationManager() {}
	
	private static Map<String, String> configMap = new HashMap<String, String>();
	
	public static void main(String[] args) {
		InventoryManager.startMigration();
		try {
			configMap = ConfigManager.populateConfigDetails(args);
		} catch (IOException e) {
			System.out.println("IO Exception while populating config details." + e.getCause());
			e.printStackTrace();
		}
		
		Connection conn = null;
		if(configMap.get(ConfigConstants.SOURCEDATABASE).equalsIgnoreCase(MigratorConstants.ORACLE)) {
		 conn = OracleConnectionManager.getInstance().getOracleDBConnection();
		}
		
		TableMigrator.migrate(conn, configMap);
		
		InventoryManager.endMigration(configMap.get(ConfigConstants.INVENTORYFILEPATH), configMap.get(ConfigConstants.INVENTORYFILENAME));
	}
	
	public static Map<String, String> getConfigMap(){
		return configMap;
	}
	
}
