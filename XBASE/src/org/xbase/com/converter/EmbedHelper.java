/**
 * 
 */
package org.xbase.com.converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xbase.com.constants.MessageConstants;
import org.xbase.com.constants.PatternConstants;
import org.xbase.com.constants.QueryConstants;
import org.xbase.com.executor.OracleQueryExecutor;
import org.xbase.com.util.PrintUtil;
import org.xbase.com.util.QueryUtil;

/**
 * @author VAMSI KRISHNA MYALAPALLI (vamsikrishna.vasu@gmail.com)
 *
 */
public class EmbedHelper {

	public static JSONArray embed(JSONArray jsonArray, String currentParentTableName, Map<String, String> childTableDetails) {
		int rowCount = jsonArray.length();
		for(int currentRowNum=0; currentRowNum<rowCount ; currentRowNum++) {
			JSONObject currentObject = new JSONObject();
			currentObject = jsonArray.getJSONObject(currentRowNum);
			for(String currentTableName : childTableDetails.keySet()) {
			JSONArray currentChildTableEntry = new JSONArray();
			String joinColumnName = childTableDetails.get(currentTableName);
			String currentRowValue = currentObject.getString(joinColumnName); 
			currentChildTableEntry = getTableChildEntry(currentParentTableName, currentTableName, joinColumnName, currentRowValue);
			currentObject.put(currentTableName, currentChildTableEntry);
			jsonArray.put(currentRowNum, currentObject);
			// jsonArray.put(currentRowNum, currentChildTableEntry);
			// jsonArray.put
			
			PrintUtil.log(PatternConstants.LINEPATTERNASTERIK);
			PrintUtil.log("Table: " + currentTableName);
			PrintUtil.log(jsonArray.toString(4));
			PrintUtil.log(PatternConstants.LINEPATTERNASTERIK);
			}	
		}
		return jsonArray;
	}
	
	/**
	 * @param currentParentTableName
	 * @param currentTableName
	 * @param joinColumnName
	 * @param currentRowValue
	 * @return
	 */
	private static JSONArray getTableChildEntry(String currentParentTableName, String currentTableName,
			String joinColumnName, String currentRowJoinValue) {
		JSONArray childEntriesJSONArray = new JSONArray();
		List<String> columnList = new ArrayList<String>();
		columnList = getColumnListofTable(currentTableName);
		columnList.remove(joinColumnName);
		String childEntriesQuery = QueryUtil.getQueryToFindChildEntriesColumns(currentParentTableName, currentTableName, columnList, joinColumnName, currentRowJoinValue);
		ResultSet childEntriesResultSet = OracleQueryExecutor.execute(childEntriesQuery);
		childEntriesJSONArray = TableToJSONConverter.getJSON(childEntriesResultSet);
		return childEntriesJSONArray;
	}

	/**
	 * @param currentTableName
	 * @param joinColumnName
	 * @param joinColumnName2 
	 */
	private static JSONArray getTableChildEntry(String currentParentTableName, String currentTableName, String joinColumnName) {
		JSONArray jsonRecordArray = new JSONArray();
		List<String> columnList = new ArrayList<String>();
		columnList = getColumnListofTable(currentTableName);
		
		List<String> parentTablejoinColumnValues = new ArrayList<String>();
		parentTablejoinColumnValues = getJoinColumnValues(currentParentTableName, joinColumnName);
		
		for(String currentRowJoinValue: parentTablejoinColumnValues) {
			JSONArray childEntriesJSONArray = new JSONArray();
			String childEntriesQuery = QueryUtil.getQueryToFindChildEntriesColumns(currentParentTableName, currentTableName, columnList, joinColumnName, currentRowJoinValue);
			ResultSet childEntriesResultSet = OracleQueryExecutor.execute(childEntriesQuery);
			childEntriesJSONArray = TableToJSONConverter.getJSON(childEntriesResultSet);
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// jsonRecordArray = TableToJSONConverter.getJSON();
		return jsonRecordArray;
	}

	/**
	 * @param currentParentTableName
	 * @param joinColumnName
	 * @param parentTablejoinColumnValues
	 */
	private static List<String> getJoinColumnValues(String currentParentTableName, String joinColumnName) {
		List<String> parentTablejoinColumnValues = new ArrayList<String>();
		String joinColumnQuery = QueryUtil.getQueryToListAColumn(currentParentTableName, joinColumnName);
		ResultSet joinColumnResultSet = OracleQueryExecutor.execute(joinColumnQuery);
		try {
			while(joinColumnResultSet.next()) {
				parentTablejoinColumnValues.add(joinColumnResultSet.getString(joinColumnName));
			}
		} catch (SQLException e) {
			PrintUtil.log(MessageConstants.ERROR + MessageConstants.EXCEPTIONWHILE + " finding join culumn values.");
			e.printStackTrace();
		}
		return parentTablejoinColumnValues;
	}

	/**
	 * @param currentTableName
	 * @param columnList
	 */
	private static final List<String> getColumnListofTable(String currentTableName) {
		List<String> columnList = new ArrayList<String>();
		String query = QueryUtil.getQueryToFindAllColumnsOfTable(currentTableName);
		ResultSet colListresultset = OracleQueryExecutor.execute(query);		
		try {
			while(colListresultset.next()) {
				columnList.add(colListresultset.getString(QueryConstants.COLUMN_NAME));
			}
		} catch (SQLException e) {
			PrintUtil.log(MessageConstants.ERROR + MessageConstants.EXCEPTIONWHILE + " fetching column names of table during embedding.");
			e.printStackTrace();
		}
		return columnList;
	}
	
	
	
}
