/**
 * 
 */
package org.xbase.com.migrator;

import java.sql.Connection;
import java.util.Map;

/**
 * @author VAMSI KRISHNA MYALAPALLI (vamsikrishna.vasu@gmail.com)
 *
 */
public class DatabaseMigrator {
	
	public static void migrate(Connection conn, Map<String, String> configMap) {
		TableMigrator.migrate(conn, configMap);
	}
	
}
