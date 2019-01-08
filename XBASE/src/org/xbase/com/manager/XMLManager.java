/**
 * 
 */
package org.xbase.com.manager;

import java.io.File;
import java.util.Map;

import org.xbase.com.constants.ConfigConstants;
import org.xbase.com.constants.MessageConstants;
import org.xbase.com.util.PrintUtil;

/**
 * @author VAMSI KRISHNA MYALAPALLI (vamsikrishna.vasu@gmail.com)
 *
 */
public class XMLManager {

	/**
	 * @param configMap
	 */
	public static void migrate(Map<String, String> configMap) {
		File databaseFile = new File(configMap.get(ConfigConstants.DATABASEFILEPATH));
		
		 if (!databaseFile.exists()) {
			 PrintUtil.log(MessageConstants.ERROR + databaseFile.toPath().toString() + " is incorrect database path." + MessageConstants.EXITING);
             System.exit(1);
		 }
		 
		 
	}

}
