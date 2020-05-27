package mongoServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Mongomain {

	public static void main(String[] args) {
		String MongoDB_IP="127.0.0.1";
		int MongoDB_PORT=27017;
		String DB_NAME="testDB";
		
		//Connect to MongoDB
		MongoClient mongoClient=new MongoClient(new ServerAddress(MongoDB_IP, MongoDB_PORT));
		
		//View Database List
		List<String> databases=mongoClient.getDatabaseNames();
		
		System.out.println("====Database List======");
		int num=1;
		for(String dbName:databases) {
			System.out.println(num+". "+dbName);
			num++;
		}
		
		System.out.println();
		
		//Connect Database and Show Collection List in Database
		DB db=mongoClient.getDB(DB_NAME);
		Set<String> collections=db.getCollectionNames();
		
		System.out.println("Database: "+DB_NAME);
		for(String colName:collections) {
			System.out.println("+Collection: "+colName);
		}
		
		MongoDatabase database=mongoClient.getDatabase(DB_NAME);
		MongoCollection<Document> collection=database.getCollection("speechData");
		
		// mongoDB insert
		/*Document doc=new Document("name","MongoDB");
		collection.insertOne(doc);*/
		
		// mongoDB find
		MongoCursor<Document> cursor=collection.find().iterator();
		
		/*try {
			while(cursor.hasNext()) {
				
				
				System.out.println(cursor.next().toJson());
			}
		}finally {
			cursor.close();
		}*/
		
		
		
		// collection의 첫번째 출력
		/*Document myDoc=collection.find().first();
		System.out.println(myDoc.toJson());
		
		System.out.println("========================================");*/
		
		// Filter 적용해서 출력하기 밑은 speech가 "안녕"인 것만 출력하기
		/*Filters filters = null;
		Block<Document> printBlock=new Block<Document>() {
			
			@Override
			public void apply(Document document) {
				
				System.out.println(document.toJson());
				
			}
		};
		
		collection.find(filters.eq("speech","안녕")).forEach(printBlock);*/
		
		
		String[] spl=null;
		MongoCollection<Document> collection1=database.getCollection("FilterData");
		cursor=collection.find().iterator();
		Document doc;
		collection1.drop();
		while(cursor.hasNext()) {
			spl=cursor.next().toJson().split("\"");
			doc=new Document("speech_filter",spl[9]);
			collection1.insertOne(doc);
		}
		
		cursor=collection1.find().iterator();
		try {
			File file=new File("f:///mongtcsv"+".csv");
			if(file.exists()) {
				file.delete();
			}
			BufferedWriter fw=new BufferedWriter(new FileWriter("f:///mongtcsv"+".csv",true));
			while(cursor.hasNext()) {
				Document e=cursor.next();
				String x=e.getString("speech_filter");
				System.out.println("speech_filter :" +x);
				
				fw.write(x);
				fw.newLine();
				
			}
			fw.flush();
			fw.close();   
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		}
}
