package main.java.SQLProductRetrievalAPIs;

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
		
		ArrayList<String> temp = ProductInformationSQLRetrieval.productHeaders();
		for(int x = 0; x < temp.size(); x++) {
			System.out.println(temp.get(x));
		}
		
	}

}
