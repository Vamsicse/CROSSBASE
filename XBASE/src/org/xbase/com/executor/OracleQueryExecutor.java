package org.xbase.com.executor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.xbase.com.constants.DebugConstants;
import org.xbase.com.constants.MessageConstants;
import org.xbase.com.constants.PatternConstants;
import org.xbase.com.manager.MigrationManager;

import static java.lang.System.out;

public class OracleQueryExecutor {

	public static ResultSet execute(Connection conn, String query) {
		PreparedStatement pstmt = null;
		ResultSet resultSet = null;
		try {
			pstmt = conn.prepareStatement(query);
			System.out.println(DebugConstants.DEBUG + PatternConstants.SPACESEPERATOR + query);
			resultSet = pstmt.executeQuery();
		} catch (SQLException sqle) {
			out.println(sqle.getMessage());
			sqle.printStackTrace();
		}
		return resultSet;
	}
	
	public static ResultSet execute(String query) {
		Connection conn = MigrationManager.getOracleConnection();
		return execute(conn, query);
	}	
	
	public static ResultSetMetaData getResultSetMetaData(ResultSet resultSet) {
		ResultSetMetaData resultSetMetadata = null;
		try {
			resultSetMetadata = resultSet.getMetaData();
		}
		catch(SQLException sqle) {
			out.println(MessageConstants.EXCEPTIONWHILE + " getting ResultSetMetaData: " + sqle.getMessage());
			sqle.printStackTrace();
		}
		return resultSetMetadata;
	}
	
}
