package org.xbase.com.manager;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

import org.xbase.com.constants.ConfigConstants;
import org.xbase.com.constants.MigratorConstants;

public class OracleConnectionManager {

	private static OracleConnectionManager oracleConnectionManager = new OracleConnectionManager();
	private static Map<String, String> configMap = ConfigManager.getConfigMap();
	private static Connection connection = null;

	private OracleConnectionManager() {
	}

	public static OracleConnectionManager getInstance() {
		return oracleConnectionManager;
	}

	/**
	 * This method will establish connection to Oracle Database and returns the
	 * connection
	 */
	public Connection getOracleDBConnection() {
		try {
			if (null == connection) {
				Properties properties = new Properties();
				properties.put(ConfigConstants.USER, configMap.get(ConfigConstants.SOURCEDATABASEUSERNAME));
				properties.put(ConfigConstants.PASSWORD, configMap.get(ConfigConstants.SOURCEDATABASEPASSWORD));
				// Sample URL: "jdbc:oracle:thin:@localhost:1521:orcl";
				String connectionURL = MigratorConstants.JDBCORACLETHIN + configMap.get(ConfigConstants.HOSTNAME) + ":"
						+ configMap.get(ConfigConstants.SOURCEDATABASEPORT) + ":" + MigratorConstants.ORCL;
				out.println("Trying to establish connection over: [" + connectionURL + "]");
				// load and register, establish db connection
				// connection = DriverManager.getConnection(MigratorConstants.ORACLEDRIVERORCL,
				// properties);
				connection = DriverManager.getConnection(connectionURL, properties);
				if (connection != null) {
					out.println("Connection Successful..!\n");
				} else {
					out.println("Failed to make connection!");
				}
			}
		} catch (SQLException e) {
			out.println("Oracle JDBC Driver not found. Please use ojdbc7.jar");
			e.printStackTrace();
		}
		return connection;
	}

	/**
	 * This method will close an open connection
	 */
	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		}
	}
}
