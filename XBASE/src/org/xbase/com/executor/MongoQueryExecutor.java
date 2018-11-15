package org.xbase.com.executor;

import static java.lang.System.out;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xbase.com.manager.MongoConnectionManager;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class MongoQueryExecutor {
	
	
	private static DB db;
	private static MongoClient mongo = MongoConnectionManager.getInstance().getMongoClientHandle();
	private static MongoQueryExecutor mongoQueryExecutor = new MongoQueryExecutor(); 
			
	public static MongoQueryExecutor getInstance() {
		return mongoQueryExecutor;
	}
	
	/*
	 * This method will create Collection/Table in Database
	 */
	public void createCollection(String databaseName, String collectionName) {	
		db = mongo.getDB(databaseName);
		db.createCollection(collectionName, new BasicDBObject());
	    out.println("Current Collection Names:\n" + db.getCollectionNames());
	}
	
	/*
	 * This method will create Documents/Records in target Collection/Table
	 */
	public void createDocuments(String databaseName, String collectionName, JSONArray documents) {
		DBCollection collection = db.getCollection(collectionName);
		
		for(int i=0 ; i<documents.length() ; i++) {
			collection.insert((DBObject)JSON.parse(documents.get(i).toString()));
		}
	}
	
	public void printCollection(String databaseName, String collectionName) {
		db = mongo.getDB(databaseName);
		DBCollection collection = db.getCollection(collectionName);
		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			out.println(cursor.next());
		}
	}
	
	
	public JSONArray getCollection(String databaseName, String collectionName) {
		db = mongo.getDB(databaseName);
		DBCollection collection = db.getCollection(collectionName);
		DBCursor cursor = collection.find();
		JSONArray documents = new JSONArray();
		while (cursor.hasNext()) {
			JSONObject currentDocument = new JSONObject();
			currentDocument = (JSONObject)cursor.next();
			out.println(currentDocument);
			documents.put(currentDocument);
		}
		return documents;
	}
	
	public void deleteCollection(String databaseName, String collectionName)
	{
		db = mongo.getDB(databaseName);
		DBCollection collection = db.getCollection(collectionName);
		collection.drop();
	}
	
	
}
