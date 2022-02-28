package com.microfocus.app.service;

import com.microfocus.app.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;

public class DbService {

	private static final Logger log = LogManager.getLogger(DbService.class);

	private final static String DEFAULT_HOST = "localhost";
	private final static String DEFAULT_DB_NAME = "ftfydemoapp";
	private final static String DEFAULT_TABLE_NAME = "products";
	private final static String DEFAULT_USER = "user";
	private final static String DEFAULT_PASSWORD = "password";

	private String tableName = DEFAULT_TABLE_NAME;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public DbService() {
		this.tableName = DEFAULT_TABLE_NAME;
	}

	public ArrayList<Product> getProducts(String query) throws Exception {
		if (log.isDebugEnabled()){
			log.debug("DbAccess:getProducts(query: " + query + ")");
		}

        Connection conn = getConnection();

		initDbIfNeeded(conn);

        // Perform some SQL queries over the connection.
        try
        {
        	Statement stmt = conn.createStatement(); 
			query = query.toLowerCase();

            String sql = "SELECT * FROM " + getTableName() +
					" WHERE lower(name) LIKE '%" + query + "%' " +
						" OR lower(summary) LIKE '%" + query + "%'" +
						" OR lower(description) LIKE '%" + query + "%'";
            ResultSet rs = stmt.executeQuery(sql);

    		ArrayList<Product> products = new ArrayList<Product>();
            while (rs.next()) {
            	Product p = new Product();
                p.Id = UUID.fromString(rs.getString(1));
                p.Code = rs.getString(2);
                p.Name = rs.getString(3);
				p.Summary = rs.getString(4);
				p.Description = rs.getString(5);
				p.Image = rs.getString(6);
				p.Price = rs.getFloat(7);
				p.OnSale = rs.getBoolean(8);
				p.SalePrice = rs.getFloat(9);
				p.InStock = rs.getBoolean(10);
				p.TimeToStock = rs.getInt(11);
				p.Rating = rs.getInt(12);
				p.Available = rs.getBoolean(13);
				products.add(p);
            }

            rs.close();
            stmt.close();
            conn.close();

            return products;
        }
        catch (SQLException e)
        {
            throw new SQLException("Encountered an error when executing SQL statement.", e);
        }
	}

	public void initDbIfNeeded(Connection conn) throws Exception {
		if (log.isDebugEnabled()){
			log.debug("DbAccess:initDbIfNeeded");
		}

		// check table existence
		if (tableExists(conn, getTableName())) {
			if (log.isDebugEnabled()){
				log.debug(getTableName() + " table already exists in database");
			}
			// skip as table already exists
			return;
		}
		
		if (log.isDebugEnabled()){
			log.debug("Creating " + getTableName() + " table in database");
		}

		try
		{
			Statement stmt = conn.createStatement();
			
			// create table
	        String sqlSchema = "create table " + getTableName() + "\n" +
					"(\n" +
					"    id             UUID         not null,\n" +
					"    code           varchar(255) not null,\n" +
					"    name           varchar(255) not null,\n" +
					"    summary        clob not null,\n" +
					"    description    clob not null,\n" +
					"    image          varchar(255),\n" +
					"    price          float not null,\n" +
					"    on_sale        bit(1) default 0 not null,\n" +
					"    sale_price     float default 0.0 not null,\n" +
					"    in_stock       bit(1) default 1 not null,\n" +
					"    time_to_stock  integer default 0 not null,\n" +
					"    rating         integer default 1 not null,\n" +
					"    available      bit(1) default 1 not null,\n" +
					"    primary key (id)\n" +
					");";
	        stmt.executeUpdate(sqlSchema);
	        
	        // initialize data
			String sqlData = "INSERT INTO products (id, code, name, rating, summary, description, image, price, in_stock, time_to_stock, available)\n" +
					"VALUES ('eec467c8-5de9-4c7c-8541-7b31614d31a0', 'SWA234-A568-00010', 'Solodox 750', 4,\n" +
					"        'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',\n" +
					"        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin pharetra enim erat, sed tempor mauris viverra in. Donec ante diam, rhoncus dapibus efficitur ut, sagittis a elit. Integer non ante felis. Curabitur nec lectus ut velit bibendum euismod. Nulla mattis convallis neque ac euismod. Ut vel mattis lorem, nec tempus nibh. Vivamus tincidunt enim a risus placerat viverra. Curabitur diam sapien, posuere dignissim accumsan sed, tempus sit amet diam. Aliquam tincidunt vitae quam non rutrum. Nunc id sollicitudin neque, at posuere metus. Sed interdum ex erat, et ornare purus bibendum id. Suspendisse sagittis est dui. Donec vestibulum elit at arcu feugiat porttitor.',\n" +
					"        'generic-product-1.jpg',\n" +
					"        12.95, 1, 30, 1);";
			stmt.executeUpdate(sqlData);
			sqlData = "INSERT INTO products (id, code, name, rating, summary, description, image, price, on_sale, sale_price, in_stock, time_to_stock, available)\n" +
					"VALUES ('74b87e87-0d77-422c-baaa-622498a84328', 'SWA534-F528-00115', 'Alphadex Plus', 5,\n" +
					"        'Lorem ipsum dolor sit amet, consectetur adipiscing elit.',\n" +
					"        'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas sit amet quam eget neque vestibulum tincidunt vitae vitae augue. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Integer rhoncus varius sem non luctus. Etiam tincidunt et leo non tempus. Etiam imperdiet elit arcu, a fermentum arcu commodo vel. Fusce vel consequat erat. Curabitur non lacus velit. Donec dignissim velit et sollicitudin pulvinar.',\n" +
					"        'generic-product-1.jpg',\n" +
					"        14.95, 1, 9.95, 1, 30, 1);";
			stmt.executeUpdate(sqlData);
			sqlData = "INSERT INTO products (id, code, name, rating, summary, description, image, price, in_stock, time_to_stock, available)\n" +
					"VALUES ('6bbbeb10-6709-4163-a790-f691b09d6aca', 'SWA179-G243-00101', 'Dontax', 3,\n" +
					"        'Aenean sit amet pulvinar mauris.',\n" +
					"        'Aenean sit amet pulvinar mauris. Suspendisse eu ligula malesuada, condimentum tortor rutrum, rutrum dui. Sed vehicula augue sit amet placerat bibendum. Maecenas ac odio libero. Donec mi neque, convallis ut nulla quis, malesuada convallis velit. Aenean a augue blandit, viverra massa nec, laoreet quam. In lacinia eros quis lacus dictum pharetra.',\n" +
					"        'generic-product-2.jpg',\n" +
					"        8.50, 1, 30, 1);";
			stmt.executeUpdate(sqlData);
			sqlData = "INSERT INTO products (id, code, name, rating, summary, description, image, price, on_sale, sale_price, in_stock, time_to_stock, available)\n" +
					"VALUES ('b6a2c319-1d14-424b-9a60-ec3ba97d21e7', 'SWA201-D342-00132', 'Tranix Life', 5,\n" +
					"        'Curabitur imperdiet lacus nec lacus feugiat varius.',\n" +
					"        'Curabitur imperdiet lacus nec lacus feugiat varius. Integer hendrerit erat orci, eget varius urna varius ac. Nulla fringilla, felis eget cursus imperdiet, odio eros tincidunt est, non blandit enim ante nec magna. Suspendisse in justo maximus nisi molestie bibendum. Fusce consequat accumsan nulla, vel pharetra nulla consequat sit amet.',\n" +
					"        'generic-product-3.jpg',\n" +
					"        7.95, 1, 4.95, 1, 14, 1);";
			stmt.executeUpdate(sqlData);
			sqlData = "INSERT INTO products (id, code, name, rating, summary, description, image, price, in_stock, time_to_stock, available)\n" +
					"VALUES ('96018e5d-f34b-4e92-955c-d077809344ab', 'SWA312-F432-00134', 'Salex Two', 5,\n" +
					"        'In porta viverra condimentum.',\n" +
					"        'In porta viverra condimentum. Morbi nibh magna, suscipit sit amet urna sed, euismod consectetur eros. Donec egestas, elit ut commodo fringilla, sem quam suscipit lectus, id tempus enim sem quis risus. Curabitur eleifend bibendum magna, vel iaculis elit varius et. Sed mollis dolor quis metus lacinia posuere. Phasellus odio mi, tempus quis dui et, consectetur iaculis odio. Quisque fringilla viverra eleifend. Cras dignissim euismod tortor, eget congue turpis fringilla sit amet. Aenean sed semper dolor, sed ultrices felis.',\n" +
					"        'generic-product-5.jpg',\n" +
					"        11.95, 0, 14, 1);";
			stmt.executeUpdate(sqlData);
			sqlData = "INSERT INTO products (id, code, name, rating, summary, description, image, price, on_sale, sale_price, in_stock, time_to_stock, available)\n" +
					"VALUES ('b85c1e4b-3ab8-4d15-b884-24db5e246058', 'SWA654-F106-00412', 'Betala Lite', 5,\n" +
					"        'Sed bibendum metus vitae suscipit mattis. Mauris turpis purus, sodales a egestas vel, tincidunt ac ipsum.',\n" +
					"        'Sed bibendum metus vitae suscipit mattis. Mauris turpis purus, sodales a egestas vel, tincidunt ac ipsum. Donec in sapien et quam varius dignissim. Phasellus eros sem, facilisis quis vehicula sed, ornare eget odio. Nam tincidunt urna mauris, id tincidunt risus posuere ac. Integer vel est vel enim convallis blandit sed sed urna. Nam dapibus erat nunc, id euismod diam pulvinar id. Fusce a felis justo.',\n" +
					"        'generic-product-4.jpg',\n" +
					"        11.95, 1, 9.95, 1, 30, 1);";
			stmt.executeUpdate(sqlData);
			sqlData = "INSERT INTO  products (id, code, name, rating, summary, description, image, price, in_stock, time_to_stock, available)\n" +
					"VALUES ('6709d692-4b37-459b-ba40-3bcc3186ca09', 'SWA254-A971-00213', 'Stimlab Mitre', 5,\n" +
					"        'Phasellus malesuada pulvinar justo, ac eleifend magna lacinia eget. Proin vulputate nec odio at volutpat.',\n" +
					"        'Phasellus malesuada pulvinar justo, ac eleifend magna lacinia eget. Proin vulputate nec odio at volutpat. Duis non suscipit arcu. Nam et arcu vehicula, sollicitudin eros non, scelerisque diam. Phasellus sagittis pretium tristique. Vestibulum sit amet lectus nisl. Aliquam aliquet dolor sit amet neque placerat, vel varius metus molestie. Fusce sed ipsum blandit, efficitur est vitae, scelerisque enim. Integer porttitor est et dictum blandit. Quisque gravida tempus orci nec finibus.',\n" +
					"        'generic-product-6.jpg',\n" +
					"        12.95, 0, 7, 1);";
			stmt.executeUpdate(sqlData);
			sqlData = "INSERT INTO products (id, code, name, rating, summary, description, image, price, in_stock, time_to_stock, available)\n" +
					"VALUES ('ba802760-b33e-4352-acfa-0a10859b519a', 'SWA754-B418-00315', 'Alphadex Lite', 2,\n" +
					"        'Nam bibendum porta metus. Aliquam viverra pulvinar velit et condimentum. Pellentesque quis purus libero.',\n" +
					"        'Nam bibendum porta metus. Aliquam viverra pulvinar velit et condimentum. Pellentesque quis purus libero. Fusce hendrerit tortor sed nulla lobortis commodo. Donec ultrices mi et sollicitudin aliquam. Phasellus rhoncus commodo odio quis faucibus. Nullam interdum mi non egestas pellentesque. Duis nec porta leo, eu placerat tellus.',\n" +
					"        'generic-product-7.jpg', 9.95, 1, 30, 1);";
			stmt.executeUpdate(sqlData);
			sqlData = "INSERT INTO products (id, code, name, rating, summary, description, image, price, in_stock, time_to_stock, available)\n" +
					"VALUES ('339311c3-8325-464a-8ca6-4b78716f00d0', 'SWA432-E901-00126', 'Villacore 2000', 1,\n" +
					"        'Aliquam erat volutpat. Ut gravida scelerisque purus a sagittis. Nullam pellentesque arcu sed risus dignissim scelerisque.',\n" +
					"        'Aliquam erat volutpat. Ut gravida scelerisque purus a sagittis. Nullam pellentesque arcu sed risus dignissim scelerisque. Maecenas vel elit pretium, ultrices augue ac, interdum libero. Suspendisse potenti. In felis metus, mattis quis lorem congue, condimentum volutpat felis. Nullam mauris mi, bibendum in ultrices sed, blandit congue ipsum.',\n" +
					"        'generic-product-8.jpg',\n" +
					"        19.95, 1, 30, 1);";
			stmt.executeUpdate(sqlData);
			sqlData = "INSERT INTO products (id, code, name, rating, summary, description, image, price, in_stock, time_to_stock, available)\n" +
					"VALUES ('0bf8ccfc-89e8-4662-b940-ca7d267dcb99', 'SWA723-A375-00412', 'Kanlab Blue', 5,\n" +
					"        'Proin eget nisl non sapien gravida pellentesque. Cras tincidunt tortor posuere, laoreet sapien nec, tincidunt nunc.',\n" +
					"        'Proin eget nisl non sapien gravida pellentesque. Cras tincidunt tortor posuere, laoreet sapien nec, tincidunt nunc. Integer vehicula, erat ut pretium porta, velit leo dignissim odio, eu ultricies urna nulla a dui. Proin et dapibus turpis, et tincidunt augue. In mattis luctus elit, in vehicula erat pretium sed. Suspendisse ullamcorper mollis dolor eu tristique.',\n" +
					"        'generic-product-9.jpg',\n" +
					"        9.95, 0, 7, 1);";
			stmt.executeUpdate(sqlData);

			stmt.close();
		}
		catch (SQLException e)
		{
			throw new SQLException("Encountered an error when initializing the database.", e);
		}
	}
	
	private Connection getConnection() throws Exception {
		if (log.isDebugEnabled()){
			log.debug("DBAccess:getConnection");
		}

		Map<String, String> connData = getConnectionData();
		
		// initialize connection variables
		String host = DEFAULT_HOST;
		String database = DEFAULT_DB_NAME;
		String tableName = DEFAULT_TABLE_NAME;
		String user = DEFAULT_USER;
		String password = DEFAULT_PASSWORD;
		if (connData != null) {
			host = connData.get("Data Source");
			if (host == null || host.isEmpty()) host = DEFAULT_HOST;
			database = connData.get("Database");
			if (database == null || database.isEmpty()) database = DEFAULT_DB_NAME;
			tableName = connData.get("Table");
			if (tableName == null || tableName.isEmpty()) tableName = DEFAULT_TABLE_NAME;
			setTableName(tableName);
			user = connData.get("User Id");
			if (user == null || user.isEmpty()) user = DEFAULT_USER;
			password = connData.get("Password");
			if (password == null || password.isEmpty()) password = DEFAULT_PASSWORD;
		}

		if (log.isDebugEnabled()){
			log.debug("Connecting string::host:"+host+";database:"+database+";table:"+tableName+";user:"+user+";password:"+password);
		}
        
        // check that the driver class is installed
        try
        {
            Class.forName("org.h2.Driver");
        }
        catch (ClassNotFoundException e)
        {
			throw new ClassNotFoundException("H2 JDBC driver NOT detected in library path.", e);
        }

        // initialize connection object
        try
        {
            String url = String.format("jdbc:h2:mem:%s", database);
			Properties properties = new Properties();
			properties.setProperty("user", user);
			properties.setProperty("password", password);
            return DriverManager.getConnection(url, properties);
        }
        catch (SQLException e)
        {
            throw new SQLException("Failed to create connection to database.", e);
        }
	}
	
	static boolean tableExists(Connection connection, String tableName) throws SQLException {
		if (log.isDebugEnabled()){
			log.debug("DbAccess:tableExists");
		}

		PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(*) "
				+ "FROM INFORMATION_SCHEMA.TABLES "
				+ "WHERE TABLE_NAME = ? "
				+ "LIMIT 1;");

		preparedStatement.setString(1, tableName);
		if (log.isDebugEnabled()){
			log.debug(preparedStatement.toString());
		}

		ResultSet resultSet = preparedStatement.executeQuery();
		resultSet.next();
		Integer result = resultSet.getInt(1);
		return result != 0;
	}

	// Get the connection string settings from the environment if it exists
	private Map<String, String> getConnectionData() throws Exception {
		String connStr = System.getenv("FTFYDEMOAPPCONNSTR_defaultConnection");
		if (connStr == null) {
			log.debug("Couldn't find the connection string - using defaults.");
			return null;
		}
		
		String[] segments = connStr.split(";");
		Map<String, String> dict = new HashMap<String, String>();
		for (String segment : segments) {
			String[] pair = segment.split("=");

			dict.put(pair[0], pair[1]);
		}
		
		return dict;
	}
}
