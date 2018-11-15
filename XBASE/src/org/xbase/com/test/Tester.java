package org.xbase.com.test;

import static java.lang.System.out;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.json.JSONObject;
import org.xbase.com.constants.QueryConstants;
import org.xbase.com.environment.EnvironmentSettings;
import org.xbase.com.executor.OracleQueryExecutor;
import org.xbase.com.manager.OracleConnectionManager;

public class Tester {

	public static void main(String[] args) {
        long start,end;
		/*
        start = System.currentTimeMillis();
		
		JdbcRowSet jdbcRS = null;
		try {
			RowSetFactory rowSetFactory = RowSetProvider.newFactory();
			jdbcRS = rowSetFactory.createJdbcRowSet();
			jdbcRS.setUrl(MigratorConstants.ORACLEDRIVERORCL);
			jdbcRS.setUsername(MigratorConstants.ORACLEUSERNAME);
			jdbcRS.setPassword(MigratorConstants.ORACLEPASSWORD);
			jdbcRS.setCommand("SELECT * FROM student");
			jdbcRS.execute();
			while (jdbcRS.next()) {
				System.out.print(jdbcRS.getObject(1) + " | ");
    	    	System.out.print(jdbcRS.getObject(2) + " | ");
				System.out.print(jdbcRS.getObject(3) + " | ");
				System.out.println();
			}
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			try {
				jdbcRS.close();
			} catch (SQLException e) {
			}
		}
		end = System.currentTimeMillis();
		
		System.out.println(end-start);
		*/
		start = System.currentTimeMillis();
		
		Connection conn = OracleConnectionManager.getInstance().getOracleDBConnection();
		String query = QueryConstants.FTS + "STUDENT";
		ResultSet resultSet = OracleQueryExecutor.execute(conn, query);
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
				out.println();
			}
			// out.println(resultSet.get + " rows selected.");
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		end = System.currentTimeMillis();
		System.out.println(end-start);
	}
}
