package org.xbase.com.manager;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.xbase.com.actions.MessageType;
import org.xbase.com.constants.ConfigConstants;
import org.xbase.com.constants.MessageConstants;
import org.xbase.com.constants.MigratorConstants;
import org.xbase.com.migrator.DatabaseMigrator;
import org.xbase.com.util.PrintUtil;

public class XBASEManager {

	private XBASEManager() {}
	private static Map<String, String> configMap = new HashMap<String, String>();
	private static Connection conn = null;
	
	public static void main(String[] args) {
		InventoryManager.startMigration();
		try {
			configMap = ConfigManager.populateConfigDetails(args);
		} catch (IOException e) {
			PrintUtil.log(MessageType.ERROR + "IO " + MessageConstants.EXCEPTIONWHILE + " populating config details." + e.getMessage());
			PrintUtil.log(e.toString());
			e.printStackTrace();
		}
		ConfigManager.printConfigDetails(configMap);
		if(configMap.get(ConfigConstants.SOURCEDATABASE).equalsIgnoreCase(MigratorConstants.ORACLE)) {
		 conn = OracleConnectionManager.getOracleDBConnection();
		}
		
		DatabaseMigrator.migrate(conn, configMap);
		
		InventoryManager.endMigration(configMap.get(ConfigConstants.INVENTORYFILEPATH), configMap.get(ConfigConstants.INVENTORYFILENAME));
		LogManager.logMigration(configMap.get(ConfigConstants.LOGFILEPATH), configMap.get(ConfigConstants.LOGFILENAME), PrintUtil.getLog());
		OracleConnectionManager.getInstance().closeConnection(conn);
	}
	
	public static Map<String, String> getConfigMap(){
		if(null==configMap)
			throw new RuntimeException("ConfigMap " + MessageConstants.NOTINITIALIZED);
		else
			return configMap;
	}
	
}
