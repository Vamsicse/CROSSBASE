package org.xbase.com.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.xbase.com.constants.ConfigConstants;
import org.xbase.com.constants.MigratorConstants;

/**
 * @author VAMSI KRISHNA MYALAPALLI (vamsikrishna.vasu@gmail.com)
 *
 */
public class ConfigManager {
    
	private ConfigManager() {}
    
    private static Map<String,String> configMap = new HashMap<String,String>();

	public static Map<String, String> populateConfigDetails(String[] configArgs) throws IOException {
		
        final Properties properties = new Properties();
        if (configArgs.length < 1) {
            System.out.println("\n***** Please pass the location of XBASE.properties file. Exiting ******\n");
            System.exit(1);
        }
        
        String configFilePath = configArgs[0];
        InputStream inputStream = new FileInputStream(configFilePath);
        properties.load(inputStream);
        
    	String dataInjectionMode = properties.getProperty(ConfigConstants.DATAINJECTIONMODE, "false");
    	String dataInjectionRange = properties.getProperty(ConfigConstants.DATAINJECTIONRANGE, "0");
    	String inventoryFilePath = properties.getProperty(ConfigConstants.INVENTORYFILEPATH); 
    	String hostName = properties.getProperty(ConfigConstants.HOSTNAME, ConfigConstants.LOCALHOST);
    	String migrateIndexes = properties.getProperty(ConfigConstants.MIGRATEINDEXES); 
    	String migrationMode = properties.getProperty(ConfigConstants.MIGRATIONMODE);
    	String password = properties.getProperty(ConfigConstants.PASSWORD);
    	String schemaToMigrate = properties.getProperty(ConfigConstants.SCHEMATOMIGRATE);
    	String sourceDatabasePassword = properties.getProperty(ConfigConstants.SOURCEDATABASEPASSWORD); 
    	String sourceDatabase = properties.getProperty(ConfigConstants.SOURCEDATABASE);
    	String sourceDatabasePort = properties.getProperty(ConfigConstants.SOURCEDATABASEPORT);  
    	String sourceDatabaseUsername = properties.getProperty(ConfigConstants.SOURCEDATABASEUSERNAME);
    	String targetDatabase = properties.getProperty(ConfigConstants.TARGETDATABASE); 
    	String targetDatabasePassword = properties.getProperty(ConfigConstants.TARGETDATABASEPASSWORD); 
    	String targetDatabasePort = properties.getProperty(ConfigConstants.TARGETDATABASEPORT, String.valueOf(MigratorConstants.MONGODEFAULTPORT)); 
    	String targetDatabaseUserName = properties.getProperty(ConfigConstants.TARGETDATABASEUSERNAME);
    	String user = properties.getProperty(ConfigConstants.USER);
        
    	configMap.put(ConfigConstants.DATAINJECTIONMODE, dataInjectionMode);
    	configMap.put(ConfigConstants.DATAINJECTIONRANGE, dataInjectionRange);
    	configMap.put(ConfigConstants.HOSTNAME, hostName);
    	configMap.put(ConfigConstants.INVENTORYFILEPATH, inventoryFilePath); 
    	configMap.put(ConfigConstants.MIGRATEINDEXES, migrateIndexes); 
    	configMap.put(ConfigConstants.MIGRATIONMODE, migrationMode);
    	configMap.put(ConfigConstants.PASSWORD, password);
    	configMap.put(ConfigConstants.SCHEMATOMIGRATE, schemaToMigrate);
    	configMap.put(ConfigConstants.SOURCEDATABASEPASSWORD, sourceDatabasePassword); 
    	configMap.put(ConfigConstants.SOURCEDATABASE, sourceDatabase);
    	configMap.put(ConfigConstants.SOURCEDATABASEPORT, sourceDatabasePort);  
    	configMap.put(ConfigConstants.SOURCEDATABASEUSERNAME, sourceDatabaseUsername);
    	configMap.put(ConfigConstants.TARGETDATABASE, targetDatabase); 
    	configMap.put(ConfigConstants.TARGETDATABASEPASSWORD, targetDatabasePassword); 
    	configMap.put(ConfigConstants.TARGETDATABASEPORT, targetDatabasePort); 
    	configMap.put(ConfigConstants.TARGETDATABASEUSERNAME, targetDatabaseUserName);
    	configMap.put(ConfigConstants.USER, user);
    	
        printConfigDetails(configMap);
         
		return configMap;
	}

	/**
	 * This module will print Configuration Details to Console.
	 * @param configMap
	 */
	private static void printConfigDetails(Map<String, String> configMap) {
		System.out.println("\n\t***** Validating Inputs ******\n");
        System.out.println("*** MigrationMode - - - - - - - - - [ " + configMap.get(ConfigConstants.MIGRATIONMODE) + " ]");
        System.out.println("*** SourceDatabase - - - - - - - - -[ " + configMap.get(ConfigConstants.SOURCEDATABASE) + " ]");
        System.out.println("*** SourceDatabasePort - - - - - - -[ " + configMap.get(ConfigConstants.SOURCEDATABASEPORT) + " ]");
        System.out.println("*** SourceDatabaseUsername- - - - - [ " + configMap.get(ConfigConstants.SOURCEDATABASEUSERNAME) + " ]");
        System.out.println("*** SourceDatabasePassword - - - - -[ " + configMap.get(ConfigConstants.SOURCEDATABASEPASSWORD) + " ]");
        System.out.println("*** TargetDatabase - - - - - - - - -[ " + configMap.get(ConfigConstants.TARGETDATABASE) + " ]");
        System.out.println("*** TargetDatabasePort- - - - - - - [ " + configMap.get(ConfigConstants.TARGETDATABASEPORT) + " ]");
        System.out.println("*** TargetDatabaseUsername - - - - -[ " + configMap.get(ConfigConstants.TARGETDATABASEUSERNAME) + " ]");
        System.out.println("*** TargetDatabasePassword - - - - -[ " + configMap.get(ConfigConstants.TARGETDATABASEPASSWORD) + " ]");
        System.out.println("*** MigrateIndexes - - - - - - - - -[ " + configMap.get(ConfigConstants.MIGRATEINDEXES) + " ]");
        System.out.println("*** SchemaToMigrate - - - - - - - - [ " + configMap.get(ConfigConstants.SCHEMATOMIGRATE) + " ]");
        System.out.println("*** InventoryFilePath - - - - - - - [ " + configMap.get(ConfigConstants.INVENTORYFILEPATH) + " ]");
        System.out.println("*** Operating Sytem - - - - - - - - [ " + System.getProperty(ConfigConstants.OSNAME) + " ]");
        System.out.println("*** DataInjectionMode - - - - - - - [ " + configMap.get(ConfigConstants.DATAINJECTIONMODE) + " ]");
        System.out.println("*** DataInjectionRange - - - - - - -[ " + configMap.get(ConfigConstants.DATAINJECTIONRANGE) + " ]");
        System.out.println();
	}	
	
	public static Map<String, String> getConfigMap() {
		return configMap;
	}
	
}
