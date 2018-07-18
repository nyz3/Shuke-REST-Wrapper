package test;

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
		
		System.out.println();
		
		ArrayList<ArrayList<String>> temp1 = ProductInformationSQLRetrieval.getCategoryProducts("充值卡");
		for(int x = 0; x < temp1.size(); x++) {
			ArrayList<String> temp2 = temp1.get(x);
			for(int y = 0; y < temp2.size(); y++) {
				System.out.print(temp2.get(y)+" ");
			}
			System.out.println();
		}
		
		System.out.println();
		
		ArrayList<ArrayList<String>> temp3 = ProductInformationSQLRetrieval.getProductInfo("goods_name");
		for(int x = 0; x < temp3.size(); x++) {
			ArrayList<String> temp4 = temp3.get(x);
			for(int y = 0; y < temp4.size(); y++) {
				System.out.print(temp4.get(y)+" ");
			}
			System.out.println();
		}
		
	
	}

}
