package main.java.SQLProductRetrievalAPIs;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import com.mysql.jdbc.Driver;

/**
 * This class represents an API dedicated towards retrieving product category headers from the SQLServer and presenting data
 * for the front-end for mobile display purposes.
 * 
 * @author Nathan Zheng
 *
 */
//complete path: http://localhost:8080/nathan_shuke_gogs/rest/ProductHeaders
@Path("/ProductHeaders")
public class ProductHeadersAPI {

	@GET
	@Produces(MediaType.APPLICATION_JSON)  //returns query result in JSON format
	public String textDetect() throws Exception {
		
		Connection connect = null;
//	    Statement statement = null;
	    PreparedStatement preparedStatement = null; //preparedStatement allows for dynamic SQLQuery creation
	    ResultSet resultSet = null;
	    
	    String resultJSON = null;    //result from SQLQuery in JSON format
	    
	    try {
            // This will load the MySQL driver, each DB has its own driver
	    	new Driver();
            // Setup the connection with the DB
	    	connect = DriverManager.getConnection("jdbc:mysql://60.205.209.93/feedback?" + "user=kc&password=kc");

         //   connect = DriverManager.getConnection("jdbc:mysql://localhost/feedback?" + "user=sqluser&password=sqluserpw");
            // Statements allow to issue SQL queries to the database
         //   statement = connect.createStatement();
            // Result set get the result of the SQL query
         //   resultSet = statement.executeQuery("select * from feedback.comments");

        } catch (Exception e) {
        	
            throw e;
            
        } finally {
        	
            resultSet.close();
            preparedStatement.close();
            connect.close();
            
        }
	    
		return resultJSON;
		
	}
}
