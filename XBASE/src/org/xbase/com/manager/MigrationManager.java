package org.xbase.com.manager;

import static java.lang.System.out;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.xbase.com.constants.QueryConstants;
import org.xbase.com.converter.TableToJSONConverter;
import org.xbase.com.executor.MongoQueryExecutor;
import org.xbase.com.executor.OracleQueryExecutor;

public class MigrationManager {

	private MigrationManager() {}
	
	private static Map<String, String> configMap = new HashMap<String, String>();
	
	public static void main(String[] args) {
		
		try {
			configMap = ConfigManager.populateConfigDetails(args);
		} catch (IOException e) {
			System.out.println("IO Exception while populating config details." + e.getCause());
			e.printStackTrace();
		}
		
		Connection conn = OracleConnectionManager.getInstance().getOracleDBConnection();
		String query = QueryConstants.FTS + "EMP";
		ResultSet resultSet = OracleQueryExecutor.execute(conn, query);

		JSONArray jsonArray = TableToJSONConverter.getJSON(resultSet);
		out.println();
		out.println(jsonArray.toString(4));
		
		out.println("\n\nConnecting to Mongo DB...");
		
		// MongoClient mongoClient = MongoConnectionManager.getMongoClientHandle();
		
		String databaseName = "sampledb";
		String collectionName = "emp";
		
		MongoQueryExecutor mongoQE = MongoQueryExecutor.getInstance();
		
		mongoQE.printCollection(databaseName, "vamsi");
		mongoQE.createCollection(databaseName, collectionName);
		mongoQE.createDocuments(databaseName, collectionName, jsonArray);
		mongoQE.printCollection(databaseName,collectionName);
		
	}
	
	public static Map<String, String> getConfigMap(){
		return configMap;
	}
	
}
