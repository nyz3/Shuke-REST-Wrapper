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
		        int rowIndex = 1;	//index to parse all columns of resultSet, starts from 1 not 0
		        //parses through the resultSet and prints out the String representation of each datapiece column by column
		        //ResultSet.next iterates by row. Call get(index) to access items in each row.
		        while (resultSet.next()) {
		        	//iterate each row by column.
		        	for(int colIndex = 1; colIndex <= resultSet.getMetaData().getColumnCount(); colIndex++) {
		        		results.add(resultSet.getString(colIndex));
		        	}
		            rowIndex++;
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
	 * @return results - ArrayList<String> containing all results from query in a custom-organized fashion.
	 * @throws Exception
	 */
	public static ArrayList<String> getCategoryProducts(String category) throws Exception {
		
		Connection connect = null;
		//address of the SQL database containing all the tables, database access username, password.
		String databaseURL = "jdbc:mysql://60.205.209.93/kc?useSSL=false";  
        String username = "kc";
        String password = "kc";
        
        //query contains the SQLquery to be executed, PreparedStatement protects against SQL injection attacks
        //by automatically escaping special SQL keywords.
        PreparedStatement query = null;
        String queryText = "SELECT * FROM tb_goods"; //QUERY COMMAND GOES HERE
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
		        int rowIndex = 1;	//index to parse all columns of resultSet, starts from 1 not 0
		        //parses through the resultSet and prints out the String representation of each datapiece column by column
		        //ResultSet.next iterates by row. Call get(index) to access items in each row.
		        while (resultSet.next()) {
		        	//iterate each row by column.
		        	for(int colIndex = 1; colIndex <= resultSet.getMetaData().getColumnCount(); colIndex++) {
		        		results.add(resultSet.getString(colIndex));
		        	}
		            rowIndex++;
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
	 * This method returns results of a JDBC SQLQuery that represents all the product categories.
	 * 
	 * @return results - ArrayList<String> containing all results from query in a custom-organized fashion.
	 * @throws Exception
	 */
	public static ArrayList<String> getProductInfo(String product) throws Exception {
		
		Connection connect = null;
		//address of the SQL database containing all the tables, database access username, password.
		String databaseURL = "jdbc:mysql://60.205.209.93/kc?useSSL=false";  
        String username = "kc";
        String password = "kc";
        
        //query contains the SQLquery to be executed, PreparedStatement protects against SQL injection attacks
        //by automatically escaping special SQL keywords.
        PreparedStatement query = null;
        String queryText = "SELECT * FROM tb_goods_class"; //QUERY COMMAND GOES HERE
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
		        int rowIndex = 1;	//index to parse all columns of resultSet, starts from 1 not 0
		        //parses through the resultSet and prints out the String representation of each datapiece column by column
		        //ResultSet.next iterates by row. Call get(index) to access items in each row.
		        while (resultSet.next()) {
		        	//iterate each row by column.
		        	for(int colIndex = 1; colIndex <= resultSet.getMetaData().getColumnCount(); colIndex++) {
		        		results.add(resultSet.getString(colIndex));
		        	}
		            rowIndex++;
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
