package org.xbase.com.executor;

import static java.lang.System.out;

import org.bson.Document;
import org.json.JSONArray;
import org.xbase.com.actions.MigratorActions;
import org.xbase.com.environment.EnvironmentSettings;
import org.xbase.com.manager.InventoryManager;
import org.xbase.com.manager.MongoConnectionManager;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoQueryExecutor {
		
	private static DB db;
	private static MongoDatabase mongoDB;
	private static MongoClient mongo = MongoConnectionManager.getInstance().getMongoClientHandle();
	private static MongoQueryExecutor mongoQueryExecutor = new MongoQueryExecutor();
	
	private MongoQueryExecutor() {}		
	
	public static MongoQueryExecutor getInstance() {
		return mongoQueryExecutor;
	}
	
	/*
	 * This method will create Collection/Table in Database
	 */
	public void createCollection(String databaseName, String collectionName) {	
		mongoDB = mongo.getDatabase(databaseName);
		mongoDB.createCollection(collectionName);
		if(EnvironmentSettings.DEBUG) {
			//out.println("Current Collection Name:\t" + db.getCollectionNames());
			out.println("Created Collection:\t" + collectionName);
		}
		InventoryManager.updateInventory(MigratorActions.COLLECTIONCREATED, collectionName);
	}
	
	/*
	 * This method will create Documents/Records in target Collection/Table
	 */
	public void createDocuments(String databaseName, String collectionName, JSONArray documents) {
		MongoCollection<Document> collection = mongoDB.getCollection(collectionName);
		
		for(int i=0 ; i<documents.length() ; i++) {
		   collection.insertOne(Document.parse(documents.get(i).toString()));
		}
	}
	
	/*
	 * This method will print Documents/Records in target Collection/Table
	 */
	public void printCollection(String databaseName, String collectionName) {
		// db = mongo.getDB(databaseName);
		mongoDB = mongo.getDatabase(databaseName);
		MongoCollection<Document> collection = mongoDB.getCollection(collectionName);
		System.out.println(collection);
	}

	public void dropCollection(String databaseName, String collectionName)
	{
		// db = mongo.getDB(databaseName);
		mongoDB = mongo.getDatabase(databaseName);
		
		DBCollection collection = db.getCollection(collectionName);
		collection.drop();
		if(EnvironmentSettings.DEBUG) {
			//out.println("Current Collection Name:\t" + db.getCollectionNames());
			out.println("Deleting Collection:\t" + collectionName);
		}
		InventoryManager.updateInventory(MigratorActions.COLLECTIONDELETED, collectionName);
	}
	
}
