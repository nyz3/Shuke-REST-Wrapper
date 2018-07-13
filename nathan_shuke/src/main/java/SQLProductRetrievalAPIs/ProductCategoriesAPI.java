package main.java.SQLProductRetrievalAPIs;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * This class represents an API dedicated towards retrieving all the products within a category header 
 * from the SQLServer and presenting image references and text for the front-end to display.
 * for the front-end for mobile display purposes.
 * 
 * @author Nathan Zheng
 *
 */
//complete path: http://localhost:8080/nathan_shuke_gogs/rest/CategoryProducts
@Path("/CategoryProducts")
public class ProductCategoriesAPI {

	@POST
	@Produces(MediaType.APPLICATION_JSON)  //returns query result in JSON format
	public String textDetect(String category) throws Exception {
		
		return null;
		
	}
	
	
}
