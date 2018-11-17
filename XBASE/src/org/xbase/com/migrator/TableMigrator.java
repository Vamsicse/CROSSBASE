/**
 * 
 */
package org.xbase.com.migrator;

import static java.lang.System.out;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.xbase.com.constants.ConfigConstants;
import org.xbase.com.constants.DebugConstants;
import org.xbase.com.constants.MessageConstants;
import org.xbase.com.constants.OraQueryConstants;
import org.xbase.com.constants.PatternConstants;
import org.xbase.com.constants.QueryConstants;
import org.xbase.com.converter.TableToJSONConverter;
import org.xbase.com.executor.MongoQueryExecutor;
import org.xbase.com.executor.OracleQueryExecutor;

/**
 * @author VAMSI KRISHNA MYALAPALLI (vamsikrishna.vasu@gmail.com)
 *
 */
public class TableMigrator {
	
	private TableMigrator() {}
	
	// TODO: Catch errors like table is present already. 2. Don't stop when exception comes write error to report
	
	public static void migrate(Connection conn, Map<String,String> configMap) {
		
		String schemaToMigrate = configMap.get(ConfigConstants.SCHEMATOMIGRATE);
		System.out.print(DebugConstants.DEBUG + ConfigConstants.SCHEMATOMIGRATE + PatternConstants.DATASEPERATOR);
		List<String> schemaList = new ArrayList<String>();
		if (schemaToMigrate.equals("*")) {
			System.out.println("All");
			schemaList = populateListFromQuery(conn, OraQueryConstants.SCHEMA, schemaToMigrate);
		} else {
			schemaList.add(schemaToMigrate);
			System.out.println(schemaToMigrate);
		}
		for (String currentSchema : schemaList) {
			List<String> tableList = new ArrayList<String>();
			tableList = populateListFromQuery(conn, OraQueryConstants.TABLE, schemaToMigrate);
			for (String currentTable : tableList) {
				String query = QueryConstants.SIMPLEFTS + currentSchema + PatternConstants.DOTSEPERATOR + currentTable;
				ResultSet resultSet = OracleQueryExecutor.execute(conn, query);
				JSONArray jsonArray = TableToJSONConverter.getJSON(resultSet);
				out.println();
				out.println(jsonArray.toString(4));
				String databaseName = configMap.get(ConfigConstants.SOURCEDATABASENAME);
				String collectionName = currentTable;
				MongoQueryExecutor mongoQE = MongoQueryExecutor.getInstance();
				// mongoQE.printCollection(databaseName, "vamsi");
				mongoQE.createCollection(databaseName, collectionName);
				mongoQE.createDocuments(databaseName, collectionName, jsonArray);
				// mongoQE.printCollection(databaseName, collectionName);
			}
		}
	}

	/**
	 * @param conn
	 * @return
	 */
	private static List<String> populateListFromQuery(Connection conn, String objectType, String schemaToMigrate) {
		List<String> schemaList = new ArrayList<String>();
		String query = null;
		if(OraQueryConstants.SCHEMA.equals(objectType)) {
			query = "SELECT USERNAME FROM " + OraQueryConstants.DBA_USERS;
		}
		else if (OraQueryConstants.TABLE.equals(objectType)){
			query = "SELECT TABLE_NAME FROM " + OraQueryConstants.ALL_TABLES + " WHERE OWNER='" + schemaToMigrate + "'";
		}
		else {
			throw new RuntimeException("Unknown Object Type. Cannot populate Schema/Table List");
		}
		ResultSet resultSet = OracleQueryExecutor.execute(conn, query);
		try {
			while(resultSet.next())
				schemaList.add(resultSet.getString(1).toUpperCase());
		} catch (SQLException e) {
			System.out.println(DebugConstants.DEBUG + MessageConstants.EXCEPTIONWHILE + " finding schema list: " + e.getMessage());
			e.printStackTrace();
		}
		System.out.println(DebugConstants.DEBUG + ConfigConstants.SCHEMATOMIGRATE + PatternConstants.DATASEPERATOR + schemaList);
		return schemaList;
	}
	
}
