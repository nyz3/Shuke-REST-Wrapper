package main.java.SQLProductRetrievalAPIs;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.mysql.cj.jdbc.Driver;

/**
 * This class is dedicated towards retrieving product category headers and product information/details from the SQLServer and presenting data
 * for the front-end for mobile display purposes using JDBC and SQL queries.
 * 
 * @author Nathan Zheng
 *
 */

public class ProductInformationSQLRetrieval {
	
	//CAN LIKELY FACTOR OUT COMMON FUNCTIONALITIES BETWEEN METHODS: Connection, Query execution (query as parameter), 
	//processing results, closing connections,etc.
	
	/**
	 * This method returns results of a JDBC SQLQuery that represents all the product categories.
	 * 
	 * @return results - ArrayList<String> containing all results from query in a custom-organized fashion.
	 * @throws Exception
	 */
	public static ArrayList<String> productHeaders() throws Exception {
		
		Connection connect = null;
		//address of the SQL database containing all the tables, database access username, password.
		String databaseURL = "jdbc:mysql://60.205.209.93/kc?useSSL=false";  
        String username = "kc";
        String password = "kc";
        
        //query contains the SQLquery to be executed, PreparedStatement protects against SQL injection attacks
        //by automatically escaping special SQL keywords.
        PreparedStatement query = null;
        String queryText = "SELECT name FROM tb_goods_class"; //QUERY COMMAND GOES HERE
        ResultSet resultSet = null;
        ArrayList<String> results = new ArrayList<String>(); //results to be organized from query for return
        
        //Execute query and fetch/return results.
        try {
	        	// This will load the MySQL driver, each DB has its own driver class instance
		        new Driver();
		        // Setup the connection with the DB
		        connect = DriverManager.getConnection(databaseURL, username, password);
		        //TESTING
		        System.out.println("Connection established.");
		        //prepare query command to be executed over the connection to database
		        query = connect.prepareStatement(queryText);
		        //execute query, and store query results in resultSet
		        resultSet = query.executeQuery();
		        //PROCESS resultSet and return data in a easily accessible and organized manner for display.
		        //parses through the resultSet and prints out the String representation of each datapiece column by column
		        //ResultSet.next iterates by row. Call get(index) to access items in each row.
		        while (resultSet.next()) {
		        	//iterate each row by column.
		        	for(int colIndex = 1; colIndex <= resultSet.getMetaData().getColumnCount(); colIndex++) {
		        		results.add(resultSet.getString(colIndex));
		        	}
		        }
        	} catch (Exception e) {
        		System.out.print("Error : " + e.getMessage());
        	} finally {
        		//close all connections and streams
        		resultSet.close();
                query.close();
                connect.close();
        	}
        
        return results;
	}
        
	/**
	 * This method returns results of a JDBC SQLQuery that represents all the products within a specified category
	 * 
	 * @return results - ArrayList<ArrayList<String>> containing all results from query in a custom-organized fashion.
	 * Each nested ArrayList represents a row in the table (a product) and the nested elements are the properties.
	 * @throws Exception
	 */
	public static ArrayList<ArrayList<String>> getCategoryProducts(String category) throws Exception {
		
		Connection connect = null;
		//address of the SQL database containing all the tables, database access username, password.
		String databaseURL = "jdbc:mysql://60.205.209.93/kc?useSSL=false";  
        String username = "kc";
        String password = "kc";
        
        //query contains the SQLquery to be executed, PreparedStatement protects against SQL injection attacks
        //by automatically escaping special SQL keywords.
        PreparedStatement query = null;
        //? is a parameter placeholder for safety reasons (parameter inserted below), specifically to protect against SQL injections.
        String queryText = "SELECT goods_name, mer_name, goods_price, goods_promotion_price, "
        		+ "goods_original_price, goods_image FROM tb_goods WHERE goods_class_id IN "
        		+ "(SELECT id FROM tb_goods_class WHERE name = ?)"; 
        ResultSet resultSet = null;
        ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>(); //results to be organized from query for return
        
        //Execute query and fetch/return results.
        try {
	        	// This will load the MySQL driver, each DB has its own driver class instance
		        new Driver();
		        // Setup the connection with the DB
		        connect = DriverManager.getConnection(databaseURL, username, password);
		        //TESTING
		        System.out.println("Connection established.");
		        //prepare query command to be executed over the connection to database
		        query = connect.prepareStatement(queryText);
		        //Places category parameter into query request (replaces the first '?')
		        query.setString(1, category);
		        //execute query, and store query results in resultSet
		        resultSet = query.executeQuery();
		        //PROCESS resultSet and return data in a easily accessible and organized manner for display.
		        //parses through the resultSet and prints out the String representation of each datapiece column by column
		        //ResultSet.next iterates by row. Call get(index) to access items in each row.
		        while (resultSet.next()) {
		        	//iterate each row by column, and store row info in productInfo.
		        	ArrayList<String> productInfo = new ArrayList<String>();
		        	for(int colIndex = 1; colIndex <= resultSet.getMetaData().getColumnCount(); colIndex++) {
		        		productInfo.add(resultSet.getString(colIndex));
		        	}
		        	//after each row, add resulting ArrayList with each product info in the overall product ArrayList.
		        	results.add(productInfo);
		        }
        	} catch (Exception e) {
        		System.out.print("Error : " + e.getMessage());
        	} finally {
        		//close all connections and streams
        		resultSet.close();
                query.close();
                connect.close();
        	}
        
        return results;
	}
	
	/**
	 * This method returns results of a JDBC SQLQuery that represents all the information about a specific product.
	 * The first ArrayList represents info about the product specifically. The second ArrayList represents info
	 * about the corresponding merchant selling the product.
	 * 
	 * @return results - ArrayList<String> containing all results from query in a custom-organized fashion.
	 * @throws Exception
	 */
	public static ArrayList<ArrayList<String>> getProductInfo(String product) throws Exception {
		
		Connection connect = null;
		//address of the SQL database containing all the tables, database access username, password.
		String databaseURL = "jdbc:mysql://60.205.209.93/kc?useSSL=false";  
        String username = "kc";
        String password = "kc";
        
        //query contains the SQLquery to be executed, PreparedStatement protects against SQL injection attacks
        //by automatically escaping special SQL keywords.
        PreparedStatement query = null;
        //Query for Product Info, limit to first match(prevent duplicates)*
        String queryProductInfo = "SELECT * FROM tb_goods WHERE goods_name = ? LIMIT 1";
        //Query for product's Merchant Info, limit to first match(prevent duplicates)*
        String queryMerchantInfo = "SELECT * FROM tb_mer_info WHERE name IN (SELECT mer_name FROM tb_goods"
        		+ " WHERE goods_name = ?) LIMIT 1"; 
        ResultSet resultSet = null;
        ArrayList<ArrayList<String>> results = new ArrayList<ArrayList<String>>(); //results to be organized from query for return
        
        //Execute query and fetch/return results.
        try {
	        	// This will load the MySQL driver, each DB has its own driver class instance
		        new Driver();
		        // Setup the connection with the DB
		        connect = DriverManager.getConnection(databaseURL, username, password);
		        //TESTING
		        System.out.println("Connection established.");
		        //prepare query command to be executed over the connection to database
		        query = connect.prepareStatement(queryProductInfo);
		        //fill question mark placeholder in queryProductInfo with the product name
		        query.setString(1, product);
		        //execute query, and store query results in resultSet
		        resultSet = query.executeQuery();
		        //PROCESS resultSet and return data in a easily accessible and organized manner for display.
		        //parses through the resultSet and prints out the String representation of each datapiece column by column
		        //There should be only one unique row for the product parameter, thus use if to check if there exists one
		        //and only one row of productInfo.
		        if(resultSet.next()) {
		        	//iterate each row by column, and store row info in info. First productInfo then corresponding merchantInfo.
		        	ArrayList<String> productInfo = new ArrayList<String>();
		        	for(int colIndex = 1; colIndex <= resultSet.getMetaData().getColumnCount(); colIndex++) {
		        		productInfo.add(resultSet.getString(colIndex));
		        	}
		        	//after each row, add resulting ArrayList with each product info in the overall product ArrayList.
		        	results.add(productInfo);
		        }
		        
		        //Prepare query for retrieval of corresponding merchant info.
		        query = connect.prepareStatement(queryMerchantInfo);
		        //fill question mark placeholder in queryMerchantInfo with the product name
		        query.setString(1, product);
		        //execute query, and store query results in resultSet
		        resultSet = query.executeQuery();
		        //PROCESS resultSet and return data in a easily accessible and organized manner for display.
		        //parses through the resultSet and prints out the String representation of each datapiece column by column
		        //There should be only one unique merchant for the product parameter, thus use if to check if there exists one
		        //and only one row of merchantInfo.
		        if(resultSet.next()) {
		        	//iterate each row by column, and store row info in info. First productInfo then corresponding merchantInfo.
		        	ArrayList<String> merchantInfo = new ArrayList<String>();
		        	for(int colIndex = 1; colIndex <= resultSet.getMetaData().getColumnCount(); colIndex++) {
		        		merchantInfo.add(resultSet.getString(colIndex));
		        	}
		        	//after each row, add resulting ArrayList with each product info in the overall product ArrayList.
		        	results.add(merchantInfo);
		        }    		
        	} catch (Exception e) {
        		System.out.print("Error : " + e.getMessage());
        	} finally {
        		//close all connections and streams
        		resultSet.close();
                query.close();
                connect.close();
        	}
        
        return results;	
	}
}