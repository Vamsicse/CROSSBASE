package org.xbase.com.converter;

import static java.lang.System.out;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.xbase.com.environment.EnvironmentSettings;
import org.xbase.com.executor.OracleQueryExecutor;
import org.json.JSONArray;
import org.json.JSONObject;

public class TableToJSONConverter {

	/**
	 * This utility method will convert ResultSet into its equivalent JSONArray
	 * @param resultSet
	 * @return JSONArray
	 * @author VAMSI KRISHNA MYALAPALLI (vamsikrishna.vasu@gmail.com)
	 */
	public static JSONArray getJSON(ResultSet resultSet) {
		JSONArray jsonArray = new JSONArray();
		ResultSetMetaData resultSetMetaData = OracleQueryExecutor.getResultSetMetaData(resultSet);

		try {
			int columnCount = resultSetMetaData.getColumnCount();
			
			if(EnvironmentSettings.DEBUG) {
				out.println("Column Count: " + columnCount + "\n");
			}
			
			for (int i = 1; i <= columnCount; i++) {
				String currentColumnName = resultSetMetaData.getColumnName(i);
				out.print(currentColumnName + " | ");
			}
			
			out.println();
			
			while (resultSet.next()) {
				JSONObject jsonObject = new JSONObject();
				for (int i = 1; i <= columnCount; i++) {
					jsonObject.put(resultSetMetaData.getColumnName(i), resultSet.getString(i));
					out.print(resultSet.getString(i) + " | ");
				}
				jsonArray.put(jsonObject);
				out.println();
			}
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		
		return jsonArray;
	}

}
