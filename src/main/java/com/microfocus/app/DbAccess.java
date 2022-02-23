package com.microfocus.app;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DbAccess {

	private static final Logger log = LogManager.getLogger(DbAccess.class);

	private final static String DEFAULT_HOST = "localhost";
	private final static String DEFAULT_DB = "products";
	private final static String DEFAULT_USER = "mysql";
	private final static String DEFAULT_PASSWORD = "mysql";

	public ArrayList<Product> getProducts(String query) throws Exception {
        Connection conn = getConnection();

		initDbIfNeed(conn);
		
        // Perform some SQL queries over the connection.
        try
        {
			if (log.isDebugEnabled()){
				log.debug("DBAccess:getProducts");
				log.debug("Query: " + query);
			}
        	Statement stmt = conn.createStatement(); 

            String sql = "SELECT * FROM products WHERE Title LIKE '%" + query + "%' OR Description LIKE '%" + query + "%'";
            ResultSet rs = stmt.executeQuery(sql);

    		ArrayList<Product> products= new ArrayList<Product>();
            while (rs.next()) {
            	Product p = new Product();
                p.Id = rs.getInt(1);
                p.Title = rs.getString(2);
                p.Category = rs.getString(3);
                p.Description = rs.getString(4);
                    
                products.add(p);
            }

            rs.close();
            stmt.close();
            conn.close();
            
            return products;
        }
        catch (SQLException e)
        {
            throw new SQLException("Encountered an error when executing given sql statement.", e);
        }
	}

	public void initDbIfNeed(Connection conn) throws Exception {
		if (log.isDebugEnabled()){
			log.debug("DBAccess:initDbIfNeed");
		}
		// check table existence
		if (tableExists(conn, "products")) {
			if (log.isDebugEnabled()){
				log.debug("Products table already exists in database");
			}
			// skip as table already exists
			return;
		}
		
		if (log.isDebugEnabled()){
			log.debug("Creating products table in database");
		}

		try
		{
			Statement stmt = conn.createStatement();
			
			// create table
	        String sqlSchema = "CREATE TABLE products (" + 
	        		"  Id int(11) NOT NULL," + 
	        		"  Title varchar(45) NOT NULL," + 
	        		"  Category varchar(45) DEFAULT NULL," +
	        		"  Description varchar(500) DEFAULT NULL," + 
	        		"  PRIMARY KEY (Id)" + 
	        		");";
	        stmt.executeUpdate(sqlSchema);
	        
	        // initialize data
	        String sqlData = "INSERT INTO products VALUES(1, 'Solodox 750', 'Pain Medication', 'Donec id nulla molestie tortor gravida venenatis eu non leo. Suspendisse eget ante non arcu elementum dictum.');";
	        stmt.executeUpdate(sqlData);

	        sqlData = "INSERT INTO products VALUES(2, 'Alphadex Plus', 'Supplement', 'Suspendisse eget ante non arcu elementum dictum. Praesent sit amet est non tortor consequat imperdiet sed in risus.');";
	        stmt.executeUpdate(sqlData);

	        sqlData = "INSERT INTO products VALUES(3, 'Dontax', 'Heart Medication', 'Mauris id nisl diam. Pellentesque ut leo massa. Vivamus et enim eu enim facilisis tempor. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae');";
	        stmt.executeUpdate(sqlData);

	        stmt.close();
		}
		catch (SQLException e)
		{
			throw new SQLException("Encountered an error when initialize the database.", e);
		}
	}
	
	private Connection getConnection() throws Exception {
		Map<String, String> connData = getConnectionData();
		
		// Initialize connection variables
        String host = connData.get("Data Source");		if (host.isEmpty()) host = DEFAULT_HOST;
        String database = connData.get("Database");		if (database.isEmpty()) database = DEFAULT_DB;
        String user = connData.get("User Id");			if (user.isEmpty()) user = DEFAULT_USER;
        String password = connData.get("Password");		if (password.isEmpty()) password = DEFAULT_PASSWORD;

		if (log.isDebugEnabled()){
			log.debug("DBAccess:getConnection");
			log.debug("host:"+host+";database:"+database+";user:"+user);
		}
        
        // check that the driver is installed
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            throw new ClassNotFoundException("MySQL JDBC driver NOT detected in library path.", e);
        }

        // Initialize connection object
        try
        {
            String url = String.format("jdbc:mysql://%s/%s", host, database);
           
            // Set connection properties.
            Properties properties = new Properties();
            properties.setProperty("user", user);
            properties.setProperty("password", password);
            properties.setProperty("useSSL", "false");
            properties.setProperty("verifyServerCertificate", "false");
            properties.setProperty("requireSSL", "false");
            properties.setProperty("serverTimezone", "UTC");

            // get connection
            return DriverManager.getConnection(url, properties);
        }
        catch (SQLException e)
        {
            throw new SQLException("Failed to create connection to database.", e);
        }
	}
	
	static boolean tableExists(Connection connection, String tableName) throws SQLException {
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT count(*) "
		  + "FROM information_schema.tables "
		  + "WHERE table_name = ?"
		  + "LIMIT 1;");
		preparedStatement.setString(1, tableName);
	
		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		return resultSet.getInt(1) != 0;
	}

	// Get the connection string settings from Azure Web App, following this format:
	// 		Database=[database];Data Source=[server name];User Id=[username];Password=[password]
	// You can also creat a System Environment Variable called MYSQLCONNSTR_defaultConnection on your machine for testing
	private Map<String, String> getConnectionData() throws Exception {
		String connStr = System.getenv("MYSQLCONNSTR_defaultConnection");
		if (connStr == null) {
			throw new Exception("Couldn't find the connection string.");
		}
		
		String[] segments = connStr.split(";");
		Map<String, String> dict = new HashMap<String, String>();
		for (int i = 0; i < segments.length; i++) {
			String[] pair = segments[i].split("=");
			
			dict.put(pair[0], pair[1]);
		}
		
		return dict;
	}
}
