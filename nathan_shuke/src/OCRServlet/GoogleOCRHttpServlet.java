package OCRServlet;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * Class containing middleman POST API for calling the Google OCR. API to be hosted on Hong Kong servers in order to bypass
 * firewall in order to call the Google API.
 * 
 * @author Nathan Zheng
 *
 */
//complete path: http://localhost:8080/nathan_shuke_gogs/rest/GoogleOCRSanHaoWang?apiKey=sanhao123
@Path("/GoogleOCRSanHaoWang")
public class GoogleOCRHttpServlet {
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)  //change to application_JSON
	public String textDetect(@QueryParam("apiKey") String password, String encoding) throws Exception {
		
		if(!password.equals("sanhao123")) {
			return "{\"response\": \"Incorrect API Key\"}";
		}
		
		//Creates JSON object "value" for API-Call
		JsonBuilderFactory factory = Json.createBuilderFactory(null);
		JsonObject value = factory.createObjectBuilder()
			.add("requests", factory.createArrayBuilder()
				.add(factory.createObjectBuilder()
					.add("image", factory.createObjectBuilder()
				        .add("content", encoding)) 			//Base64 encoding goes here
			        .add("features", factory.createArrayBuilder()
			            .add(factory.createObjectBuilder()
		                    .add("type", "TEXT_DETECTION"))))).build();
					
		URL url = new URL("https://vision.googleapis.com/v1/images:annotate?key=AIzaSyBCHMEO4kQJN3gkQWds7nCmNf8vnhWourE");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
				    
		OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());  
		out.write(value.toString());
		out.flush();
		out.close();
				    
		InputStream is = connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String line = null;
		String response = "";
		while((line = br.readLine()) != null) {
			response += line;
		}
		connection.disconnect();
		return response;
		
	}
}

