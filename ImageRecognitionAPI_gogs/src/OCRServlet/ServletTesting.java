package OCRServlet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ServletTesting {

	public static void main(String args[]) throws Exception {
		
		URL url = new URL("http://localhost:8080/ImageRecognitionAPI_gogs/rest/GoogleOCRSanHaoWang?apiKey=sanhao123");
		
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		connection.setRequestProperty("Content-Type", "application/json");
		connection.connect();
				    
		BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line = null;
		String response = "";
		while((line = br.readLine() ) != null) {
			response += line;
		}
		connection.disconnect();
		System.out.println(response);
		
		/*URL url = new URL("https://vision.googleapis.com/v1/images:annotate?key=AIzaSyBCHMEO4kQJN3gkQWds7nCmNf8vnhWourE");
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
	    while((line = br.readLine() ) != null) {
	        response += line;
	    }
	    connection.disconnect();
	    System.out.println(response);
	    return response;*/
	}
}
