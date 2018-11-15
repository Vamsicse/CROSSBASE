package org.xbase.com.manager;

import static java.lang.System.out;

import java.net.UnknownHostException;

import org.xbase.com.constants.MigratorConstants;

import com.mongodb.MongoClient;

public class MongoConnectionManager {
	
	private static MongoConnectionManager mongoConnectionManager = new MongoConnectionManager();

	private MongoConnectionManager() {}
	
	public static MongoConnectionManager getInstance() {
		return mongoConnectionManager;
	}
	
	public MongoClient getMongoClientHandle() {
		MongoClient mongo = null;
		try {
			mongo = new MongoClient(MigratorConstants.LOCALHOST, MigratorConstants.MONGODEFAULTPORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		// MongoCredential credential = MongoCredential.createCredential("sampleUser","myDb", "password".toCharArray());
		
		out.println("Connected to the database successfully...\n");
		return mongo;
	}
}
