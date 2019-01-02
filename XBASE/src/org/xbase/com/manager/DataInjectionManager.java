/**
 * 
 */
package org.xbase.com.manager;

import java.sql.Timestamp;

import org.xbase.com.constants.ConfigConstants;
import org.xbase.com.constants.MigratorConstants;
import org.xbase.com.constants.ObjectConstants;
import org.xbase.com.constants.PatternConstants;
import org.xbase.com.util.PrintUtil;

/**
 * @author VAMSI KRISHNA MYALAPALLI (vamsikrishna.vasu@gmail.com)
 *
 */
public class DataInjectionManager {
	/**
	 * @param dataInjectionRanges
	 */
	public static void injectData(String dataInjectionRanges) {
		PrintUtil.log(PatternConstants.LINEPATTERNASTERIK);
		PrintUtil.log("Initiating " + ConfigConstants.DATAINJECTIONMODE);
		PrintUtil.log(PatternConstants.LINEPATTERNASTERIK);

		String[] injectionRange = dataInjectionRanges.split(",");
		int tableRange = Integer.parseInt(injectionRange[0]);
		int rowRange = Integer.parseInt(injectionRange[1]);
		int columRange = Integer.parseInt(injectionRange[2]);

		for (int i = 0; i < tableRange; i++) {
			
			
			
		}

	}

	public static String getRandomCollectionName() {
		StringBuilder tableName = new StringBuilder();
		tableName.append(ObjectConstants.COLLECTION);
		tableName.append(getTimeStamp());
		return tableName.toString();
	}

	public static String getRandomColumnName() {
		StringBuilder columnName = new StringBuilder();
		columnName.append(ObjectConstants.COLUMN);
		columnName.append(getTimeStamp());
		return columnName.toString();
	}

	public static String getRandomKey() {
		StringBuilder rowData = new StringBuilder();
		rowData.append(ObjectConstants.KEY);
		rowData.append(getTimeStamp());
		return rowData.toString();
	}

	public static String getRandomValue() {
		StringBuilder rowData = new StringBuilder();
		rowData.append(ObjectConstants.KEY);
		rowData.append(getTimeStamp());
		return rowData.toString();
	}
	
	public static String getTimeStamp() {
		return new Timestamp(System.currentTimeMillis()).toString().replaceAll(" ", "_");
	}
}
