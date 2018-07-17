package test;

import java.io.PrintStream;
import java.util.ArrayList;

import main.java.SQLProductRetrievalAPIs.ProductInformationSQLRetrieval;
/**
 * Temporary class used to test effectiveness/accuracy of Product APIs that retrieve data from SQL database.
 * 
 * @author Nathan Zheng
 *
 */
public class TestingSQLAPI {
	
	public static void main (String args[]) throws Exception {
		
//		PrintStream out = new PrintStream(System.out, false, "UTF8"); //allows for Chinese characters to print to console
		ArrayList<String> temp = ProductInformationSQLRetrieval.productHeaders();
		for(int x = 0; x < temp.size(); x++) {
			System.out.println(temp.get(x));
		}
		
	}

}
