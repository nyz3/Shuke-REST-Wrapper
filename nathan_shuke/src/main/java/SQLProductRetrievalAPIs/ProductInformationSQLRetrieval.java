package main.java.SQLProductRetrievalAPIs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import com.mysql.jdbc.Driver;

/**
 * This class is dedicated towards retrieving product category headers and product information/details from the SQLServer and presenting data
 * for the front-end for mobile display purposes using JDBC and SQL queries.
 * 
 * @author Nathan Zheng
 *
 */

public class ProductInformationSQLRetrieval {

	public String productHeaders() throws Exception {
		
		Connection conn = null;
		String url = "jdbc:mysql://localhost:3306/db_name";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "root";
        String password = "";
        try {
        Class.forName(driver);
        conn = DriverManager.getConnection(url,userName,password);
        System.out.print("Connection estd");
        Statement st = conn.createStatement();
        st.executeQuery("insert into table_name values(2, 'testing');");
        } catch (Exception e) {
            System.out.print("Error : " +e.getMessage());
        } finally {
        	
        }
        
        // break 
        
        
		Connection connect = null;
//	    Statement statement = null;
	    PreparedStatement preparedStatement = null; //preparedStatement allows for dynamic SQLQuery creation
	    ResultSet resultSet = null;
	    
	    
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
	    
		return null;
		
	}

	
	public String getCategoryProducts(String category) throws Exception {
		
		return null;
		
	}
	
	public String getProductInfo(String product) throws Exception {
		
		return null;
		
	}
	
}
