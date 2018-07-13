package APISQLProductRetrieval;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
		
		return null;
		
	}
}
