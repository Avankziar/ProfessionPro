package main.java.de.avankziar.professionpro.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import main.java.de.avankziar.professionpro.ProfessionPro;

public class MysqlSetup 
{
	private ProfessionPro plugin;
	private Connection conn = null;
	
	public MysqlSetup(ProfessionPro plugin) 
	{
		this.plugin = plugin;
		loadMysqlSetup();
	}
	
	
	public boolean loadMysqlSetup()
	{
		if(!connectToDatabase())
		{
			return false;
		}
		if(!setupDatabaseI())
		{
			return false;
		}
		if(!setupDatabaseII())
		{
			return false;
		}
		if(!setupDatabaseIII())
		{
			return false;
		}
		/*if(!setupDatabaseIV())
		{
			return false;
		}
		if(!setupDatabaseV())
		{
			return false;
		}
		if(!setupDatabaseVI())
		{
			return false;
		}*/
		return true;
	}
	
	public boolean connectToDatabase() 
	{
		ProfessionPro.log.info("Connecting to the database...");
		try 
		{
       	 	//Load Drivers
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user", plugin.getYamlHandler().getConfig().getString("Mysql.User"));
            properties.setProperty("password", plugin.getYamlHandler().getConfig().getString("Mysql.Password"));
            properties.setProperty("autoReconnect", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.AutoReconnect", true) + "");
            properties.setProperty("verifyServerCertificate", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.VerifyServerCertificate", false) + "");
            properties.setProperty("useSSL", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.SSLEnabled", false) + "");
            properties.setProperty("requireSSL", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.SSLEnabled", false) + "");
            //Connect to database
            conn = DriverManager.getConnection("jdbc:mysql://" + plugin.getYamlHandler().getConfig().getString("Mysql.Host") 
            		+ ":" + plugin.getYamlHandler().getConfig().getInt("Mysql.Port", 3306) + "/" 
            		+ plugin.getYamlHandler().getConfig().getString("Mysql.DatabaseName"), properties);
           
          } catch (ClassNotFoundException e) 
		{
        	  ProfessionPro.log.severe("Could not locate drivers for mysql! Error: " + e.getMessage());
            return false;
          } catch (SQLException e) 
		{
        	  ProfessionPro.log.severe("Could not connect to mysql database! Error: " + e.getMessage());
            return false;
          }
		ProfessionPro.log.info("Database connection successful!");
		return true;
	}
	
	public boolean setupDatabaseI() 
	{
		if (conn != null) 
		{
			PreparedStatement query = null;
		      try 
		      {	        
		        String data = "CREATE TABLE IF NOT EXISTS `" + plugin.getMysqlHandler().tableNameI
		        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
		        		+ " player_uuid char(36) NOT NULL UNIQUE,"
		        		+ " player_name varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,"
		        		+ " jobswitchtimer bigint DEFAULT '0',"
		        		+ " furnaces longtext,"
		        		+ " blastfurnaces longtext,"
		        		+ " smokers lontext,"
		        		+ " brewstands longtext,"
		        		+ " composters longtext);";
		        query = conn.prepareStatement(data);
		        query.execute();
		      } catch (SQLException e) 
		      {
		        e.printStackTrace();
		        ProfessionPro.log.severe("Error creating tables! Error: " + e.getMessage());
		        return false;
		      } finally 
		      {
		    	  try 
		    	  {
		    		  if (query != null) 
		    		  {
		    			  query.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    		  return false;
		    	  }
		      }
		}
		return true;
	}
	
	public boolean setupDatabaseII() 
	{
		if (conn != null) 
		{
			PreparedStatement query = null;
		      try 
		      {	        
		        String data = "CREATE TABLE IF NOT EXISTS `" + plugin.getMysqlHandler().tableNameII
		        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
		        		+ " player_uuid char(36) NOT NULL UNIQUE,"
		        		+ " player_name varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,"
		        		+ " professionid int,"
		        		+ " isactive boolean,"
		        		+ " levelnumber int,"
		        		+ " levelexp double,);"; //Actuale Exp
		        query = conn.prepareStatement(data);
		        query.execute();
		      } catch (SQLException e) 
		      {
		        e.printStackTrace();
		        ProfessionPro.log.severe("Error creating tables! Error: " + e.getMessage());
		        return false;
		      } finally 
		      {
		    	  try 
		    	  {
		    		  if (query != null) 
		    		  {
		    			  query.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    		  return false;
		    	  }
		      }
		}
		return true;
	}
	
	public boolean setupDatabaseIII() 
	{
		if (conn != null) 
		{
			PreparedStatement query = null;
		      try 
		      {	        
		        String data = "CREATE TABLE IF NOT EXISTS `" + plugin.getMysqlHandler().tableNameIII
		        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
		        		+ " datetime text,"
		        		+ " from_uuidornumber text,"
		        		+ " from_name text,"
		        		+ " to_uuidornumber text,"
		        		+ " to_name text,"
		        		+ " orderer_uuid text,"
		        		+ " amount double,"
		        		+ " eco_type text,"
		        		+ " comment mediumtext);";
		        query = conn.prepareStatement(data);
		        query.execute();
		      } catch (SQLException e) 
		      {
		        e.printStackTrace();
		        ProfessionPro.log.severe("Error creating tables! Error: " + e.getMessage());
		        return false;
		      } finally 
		      {
		    	  try 
		    	  {
		    		  if (query != null) 
		    		  {
		    			  query.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    		  return false;
		    	  }
		      }
		}
		return true;
	}
	
	public boolean setupDatabaseIV() 
	{
		if (conn != null) 
		{
			PreparedStatement query = null;
		      try 
		      {	        
		        String data = "CREATE TABLE IF NOT EXISTS `" + plugin.getMysqlHandler().tableNameIV
		        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
		        		+ " dates text,"
		        		+ " trend_type text,"
		        		+ " uuidornumber text,"
		        		+ " relative_amount_change double,"
		        		+ " firstvalue double,"
		        		+ " lastvalue double);";
		        query = conn.prepareStatement(data);
		        query.execute();
		      } catch (SQLException e) 
		      {
		        e.printStackTrace();
		        ProfessionPro.log.severe("Error creating tables! Error: " + e.getMessage());
		        return false;
		      } finally 
		      {
		    	  try 
		    	  {
		    		  if (query != null) 
		    		  {
		    			  query.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    		  return false;
		    	  }
		      }
		}
		return true;
	}
	
	public boolean setupDatabaseV() 
	{
		if (conn != null) 
		{
			PreparedStatement query = null;
		      try 
		      {	        
		        String data = "CREATE TABLE IF NOT EXISTS `" + plugin.getMysqlHandler().tableNameV
		        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
		        		+ " standing_order_name text,"
		        		+ " from_player text,"
		        		+ " to_player text,"
		        		+ " amount double,"
		        		+ " amountpaidsofar double,"
		        		+ " starttime bigint,"
		        		+ " repeatingtime bigint,"
		        		+ " lasttime bigint,"
		        		+ " cancelled boolean,"
		        		+ " paused boolean);";
		        query = conn.prepareStatement(data);
		        query.execute();
		      } catch (SQLException e) 
		      {
		        e.printStackTrace();
		        ProfessionPro.log.severe("Error creating tables! Error: " + e.getMessage());
		        return false;
		      } finally 
		      {
		    	  try 
		    	  {
		    		  if (query != null) 
		    		  {
		    			  query.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    		  return false;
		    	  }
		      }
		}
		return true;
	}
	
	public boolean setupDatabaseVI() 
	{
		if (conn != null) 
		{
			PreparedStatement query = null;
		      try 
		      {	        
		        String data = "CREATE TABLE IF NOT EXISTS `" + plugin.getMysqlHandler().tableNameVI
		        		+ "` (id int AUTO_INCREMENT PRIMARY KEY,"
		        		+ " name text,"
		        		+ " from_player text,"
		        		+ " to_player text,"
		        		+ " debtowner text,"
		        		+ " totalamount double,"
		        		+ " amountratio double,"
		        		+ " amountpaidsofar double,"
		        		+ " interest double,"
		        		+ " starttime bigint,"
		        		+ " repeatingtime bigint,"
		        		+ " lasttime bigint,"
		        		+ " endtime bigint,"
		        		+ " forgiven boolean,"
		        		+ " paused boolean,"
		        		+ " finished boolean);";
		        query = conn.prepareStatement(data);
		        query.execute();
		      } catch (SQLException e) 
		      {
		        e.printStackTrace();
		        ProfessionPro.log.severe("Error creating tables! Error: " + e.getMessage());
		        return false;
		      } finally 
		      {
		    	  try 
		    	  {
		    		  if (query != null) 
		    		  {
		    			  query.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    		  return false;
		    	  }
		      }
		}
		return true;
	}
	
	public Connection getConnection() 
	{
		checkConnection();
		return conn;
	}
	
	public void checkConnection() 
	{
		try {
			if (conn == null) 
			{
				ProfessionPro.log.warning("Connection failed. Reconnecting...");
				reConnect();
			}
			if (!conn.isValid(3)) 
			{
				ProfessionPro.log.warning("Connection is idle or terminated. Reconnecting...");
				reConnect();
			}
			if (conn.isClosed() == true) 
			{
				ProfessionPro.log.warning("Connection is closed. Reconnecting...");
				reConnect();
			}
		} catch (Exception e) 
		{
			ProfessionPro.log.severe("Could not reconnect to Database! Error: " + e.getMessage());
		}
	}
	
	public boolean reConnect() 
	{
		try 
		{            
            long start = 0;
			long end = 0;
			
		    start = System.currentTimeMillis();
		    ProfessionPro.log.info("Attempting to establish a connection to the MySQL server!");
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            properties.setProperty("user", plugin.getYamlHandler().getConfig().getString("Mysql.User"));
            properties.setProperty("password", plugin.getYamlHandler().getConfig().getString("Mysql.Password"));
            properties.setProperty("autoReconnect", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.AutoReconnect", true) + "");
            properties.setProperty("verifyServerCertificate", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.VerifyServerCertificate", false) + "");
            properties.setProperty("useSSL", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.SSLEnabled", false) + "");
            properties.setProperty("requireSSL", 
            		plugin.getYamlHandler().getConfig().getBoolean("Mysql.SSLEnabled", false) + "");
            //Connect to database
            conn = DriverManager.getConnection("jdbc:mysql://" + plugin.getYamlHandler().getConfig().getString("Mysql.Host") 
            		+ ":" + plugin.getYamlHandler().getConfig().getInt("Mysql.Port", 3306) + "/" 
            		+ plugin.getYamlHandler().getConfig().getString("Mysql.DatabaseName"), properties);
		    end = System.currentTimeMillis();
		    ProfessionPro.log.info("Connection to MySQL server established!");
		    ProfessionPro.log.info("Connection took " + ((end - start)) + "ms!");
            return true;
		} catch (Exception e) 
		{
			ProfessionPro.log.severe("Error re-connecting to the database! Error: " + e.getMessage());
			return false;
		}
	}
	
	public void closeConnection() 
	{
		try
		{
			ProfessionPro.log.info("Closing database connection...");
			conn.close();
			conn = null;
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
}
