package lets.chat.letschat;


import java.io.IOException;
import java.util.regex.Pattern;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

@Path("/rest/")
public class ServiceRest {
	
	@POST
	@Path("/insertregistrationdetails")
	public String insertRegistrationDetails(String userdetails)
	{
		ObjectMapper mapper = new ObjectMapper();
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("letschatDb");
		DBCollection table = db.getCollection("userdetails");
		DBObject dbObject = (DBObject) JSON
				.parse(userdetails);
		WriteResult result = table.insert(dbObject);
		return "inserted";
	}
	
	/*@POST
	@Path("/updatedetails")
	public String updateDetails(String userdetails)
	{
		ObjectMapper mapper = new ObjectMapper();
		RegistrationBean bean=null;
		try {
			bean = mapper.readValue(userdetails, RegistrationBean.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("servicesdb");
		DBCollection table = db.getCollection("userdetails");
		BasicDBObject newDocument = new BasicDBObject();
		BasicDBObject searchQuery = new BasicDBObject().append("number", bean.getNumber());
		BasicDBObject newDocument_inserDetails = new BasicDBObject();
		newDocument_inserDetails.append("address1", bean.getAddress1());
		newDocument_inserDetails.append("address2", bean.getAddress2());
		newDocument_inserDetails.append("skill", bean.getSkill());
		newDocument_inserDetails.append("city", bean.getCity());
		newDocument_inserDetails.append("pin", bean.getPin());
		newDocument.append("$set",newDocument_inserDetails);
		table.update(searchQuery, newDocument);
		System.out.println("Inserted");
		System.out.println(userdetails);
		return "Sysoooooooo";
	}*/
	
	@GET
	@Path("/insertadditionaldetails")
	@Produces("application/json")
	@Consumes("application/json")
	public String insertAdditionalDetails(String userAdditionalDetails)
	{
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("servicesdb");
		DBCollection table = db.getCollection("userdetails");
		
		BasicDBObject newDocument = new BasicDBObject();
		newDocument.append("$set", new BasicDBObject().append("skill", "Teaching maths"));
		newDocument.append("$set", new BasicDBObject().append("areas", "in and Around Hyderabad"));
		BasicDBObject searchQuery = new BasicDBObject().append("userPhoneNumber", "9705793049");

		table.update(searchQuery, newDocument);
		return null;
	}
	
	@POST
	@Path("/retrievemaincontent")
	//To get main content
	public String retrieveMainContent(String body)
	{
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("letschatDb");
		DBCollection table = db.getCollection("userdetails");
		BasicDBObject searchQuery = new BasicDBObject();
		//Pattern pattern = Pattern.compile(".*"+searchFor+"*" , Pattern.CASE_INSENSITIVE);
		//Pattern pattern = Pattern.compile(".*"+searchFor+"*" , Pattern.CASE_INSENSITIVE);
		//searchQuery.put("skill",pattern);
		//DBCursor cursor = table.find(searchQuery);
		DBCursor cursor = table.find();
		JSON json = new JSON();
		System.out.println(json.serialize(cursor));
		return json.serialize(cursor);
	}
	
	@POST
	@Path("/retrieveuser")
	public String retrieveUser(String searchFor)
	{
		MongoClient mongo = new MongoClient("localhost", 27017);
		DB db = mongo.getDB("servicesdb");
		DBCollection table = db.getCollection("userdetails");
		BasicDBObject searchQuery = new BasicDBObject();
		Pattern pattern = Pattern.compile(".*"+searchFor+"*" , Pattern.CASE_INSENSITIVE);
		searchQuery.put("number",pattern);
		DBCursor cursor = table.find(searchQuery);
		JSON json = new JSON();
		System.out.println(json.serialize(cursor));
		return json.serialize(cursor);
	}

}
