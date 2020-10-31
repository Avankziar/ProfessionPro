package main.java.de.avankziar.professionpro.database.tables;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

import main.java.de.avankziar.professionpro.ProfessionPro;
import main.java.de.avankziar.professionpro.handler.ConvertHandler;
import main.java.de.avankziar.professionpro.objectsuser.PluginUser;

public interface TableI
{
	default boolean existI(ProfessionPro plugin, String whereColumn, Object... object) 
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT `id` FROM `" + plugin.getMysqlHandler().tableNameI 
						+ "` WHERE "+whereColumn+" LIMIT 1";
		        preparedStatement = conn.prepareStatement(sql);
		        int i = 1;
		        for(Object o : object)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        
		        result = preparedStatement.executeQuery();
		        while (result.next()) 
		        {
		        	return true;
		        }
		    } catch (SQLException e) 
			{
				  ProfessionPro.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return false;
	}
	
	default boolean createI(ProfessionPro plugin, Object object) 
	{
		if(!(object instanceof PluginUser))
		{
			return false;
		}
		PluginUser ep = (PluginUser) object;
		PreparedStatement preparedStatement = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) {
			try 
			{
				String sql = "INSERT INTO `" + plugin.getMysqlHandler().tableNameI 
						+ "`(`player_uuid`, `player_name`,"
						+ " `jobswitchtimer`, `furnaces`, `blastfurnaces`, `smokers`, `brewstands`, `composters`) " 
						+ "VALUES(?, ?, ?, ?)";
				preparedStatement = conn.prepareStatement(sql);
		        preparedStatement.setString(1, ep.getUuid().toString());
		        preparedStatement.setString(2, ep.getName());
		        preparedStatement.setDouble(3, ep.getJobSwitchTimer());
		        preparedStatement.setString(4, ConvertHandler.serialised(ep.getFurnaces()));
		        preparedStatement.setString(5, ConvertHandler.serialised(ep.getBlastFurnaces()));
		        preparedStatement.setString(6, ConvertHandler.serialised(ep.getSmokers()));
		        preparedStatement.setString(7, ConvertHandler.serialised(ep.getBrewstands()));
		        preparedStatement.setString(8, ConvertHandler.serialised(ep.getComposters()));
		        
		        preparedStatement.executeUpdate();
		        return true;
		    } catch (SQLException e) 
			{
				  ProfessionPro.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return false;
	}
	
	default boolean updateDataI(ProfessionPro plugin, Object object, String whereColumn, Object... whereObject) 
	{
		if(!(object instanceof PluginUser))
		{
			return false;
		}
		if(whereObject == null)
		{
			return false;
		}
		PluginUser ep = (PluginUser) object;
		PreparedStatement preparedStatement = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{
				String data = "UPDATE `" + plugin.getMysqlHandler().tableNameI
						+ "` SET `player_uuid` = ?, `player_name` = ?,"
						+ " `jobswitchtimer` = ?, `furnaces` = ?, `blastfurnaces` = ?, `smokers` = ?, `brewstands` = ?, `composters` = ?"
						+ " WHERE "+whereColumn;
				preparedStatement = conn.prepareStatement(data);
				preparedStatement.setString(1, ep.getUuid().toString());
			    preparedStatement.setString(2, ep.getName());
			    preparedStatement.setDouble(3, ep.getJobSwitchTimer());
			    preparedStatement.setString(4, ConvertHandler.serialised(ep.getFurnaces()));
		        preparedStatement.setString(5, ConvertHandler.serialised(ep.getBlastFurnaces()));
		        preparedStatement.setString(6, ConvertHandler.serialised(ep.getSmokers()));
		        preparedStatement.setString(7, ConvertHandler.serialised(ep.getBrewstands()));
		        preparedStatement.setString(8, ConvertHandler.serialised(ep.getComposters()));
		        int i = 9;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
				
				preparedStatement.executeUpdate();
				return true;
			} catch (SQLException e) {
				ProfessionPro.log.warning("Error: " + e.getMessage());
				e.printStackTrace();
			} finally {
				try {
					if (preparedStatement != null) 
					{
						preparedStatement.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
        return false;
	}
	
	default Object getDataI(ProfessionPro plugin, String whereColumn, Object... whereObject)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT * FROM `" + plugin.getMysqlHandler().tableNameI 
						+ "` WHERE "+whereColumn+" LIMIT 1";
		        preparedStatement = conn.prepareStatement(sql);
		        int i = 1;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        
		        result = preparedStatement.executeQuery();
		        while (result.next()) 
		        {
		        	return new PluginUser(null,
		        			UUID.fromString(result.getString("player_uuid")),
		        			result.getString("player_name"),
		        			new LinkedHashMap<>(),
		        			result.getLong("jobswitchtimer"),
		        			0,
		        			ConvertHandler.deserialisedArray(result.getString("furnaces")),
		        			ConvertHandler.deserialisedArray(result.getString("blastfurnaces")),
		        			ConvertHandler.deserialisedArray(result.getString("smokers")),
		        			ConvertHandler.deserialisedArray(result.getString("brewstands")),
		        			ConvertHandler.deserialisedArray(result.getString("composters")));
		        }
		    } catch (SQLException e) 
			{
				  ProfessionPro.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return null;
	}
	
	default boolean deleteDataI(ProfessionPro plugin, String whereColumn, Object... whereObject)
	{
		PreparedStatement preparedStatement = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		try 
		{
			String sql = "DELETE FROM `" + plugin.getMysqlHandler().tableNameI + "` WHERE "+whereColumn;
			preparedStatement = conn.prepareStatement(sql);
			int i = 1;
	        for(Object o : whereObject)
	        {
	        	preparedStatement.setObject(i, o);
	        	i++;
	        }
			preparedStatement.execute();
			return true;
		} catch (Exception e) 
		{
			e.printStackTrace();
		} finally 
		{
			try {
				if (preparedStatement != null) 
				{
					preparedStatement.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	default int lastIDI(ProfessionPro plugin)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT `id` FROM `" + plugin.getMysqlHandler().tableNameI + "` ORDER BY `id` DESC LIMIT 1";
		        preparedStatement = conn.prepareStatement(sql);
		        
		        result = preparedStatement.executeQuery();
		        while(result.next())
		        {
		        	return result.getInt("id");
		        }
		    } catch (SQLException e) 
			{
		    	e.printStackTrace();
		    	return 0;
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return 0;
	}
	
	default int countWhereIDI(ProfessionPro plugin, String whereColumn, Object... whereObject)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT `id` FROM `" + plugin.getMysqlHandler().tableNameI
						+ "` WHERE "+whereColumn
						+ " ORDER BY `id` DESC";
		        preparedStatement = conn.prepareStatement(sql);
		        int i = 1;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        result = preparedStatement.executeQuery();
		        int count = 0;
		        while(result.next())
		        {
		        	count++;
		        }
		        return count;
		    } catch (SQLException e) 
			{
		    	e.printStackTrace();
		    	return 0;
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) 
		    	  {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return 0;
	}
	
	default ArrayList<PluginUser> getListI(ProfessionPro plugin, String orderByColumn,
			int start, int end, String whereColumn, Object...whereObject)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT * FROM `" + plugin.getMysqlHandler().tableNameI
						+ "` WHERE "+whereColumn+" ORDER BY "+orderByColumn+" DESC LIMIT "+start+", "+end;
		        preparedStatement = conn.prepareStatement(sql);
		        int i = 1;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        result = preparedStatement.executeQuery();
		        ArrayList<PluginUser> list = new ArrayList<PluginUser>();
		        while (result.next()) 
		        {
		        	PluginUser ep = new PluginUser(null,
		        			UUID.fromString(result.getString("player_uuid")),
		        			result.getString("player_name"),
		        			new LinkedHashMap<>(),
		        			result.getLong("jobswitchtimer"),
		        			0,
		        			ConvertHandler.deserialisedArray(result.getString("furnaces")),
		        			ConvertHandler.deserialisedArray(result.getString("blastfurnaces")),
		        			ConvertHandler.deserialisedArray(result.getString("smokers")),
		        			ConvertHandler.deserialisedArray(result.getString("brewstands")),
		        			ConvertHandler.deserialisedArray(result.getString("composters")));
		        	list.add(ep);
		        }
		        return list;
		    } catch (SQLException e) 
			{
				  ProfessionPro.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return null;
	}
	
	default ArrayList<PluginUser> getTopI(ProfessionPro plugin, String orderByColumn, int start, int end)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT * FROM `" + plugin.getMysqlHandler().tableNameI 
						+ "` ORDER BY "+orderByColumn+" DESC LIMIT "+start+", "+end;
		        preparedStatement = conn.prepareStatement(sql);
		        
		        result = preparedStatement.executeQuery();
		        ArrayList<PluginUser> list = new ArrayList<PluginUser>();
		        while (result.next()) 
		        {
		        	PluginUser ep = new PluginUser(null,
		        			UUID.fromString(result.getString("player_uuid")),
		        			result.getString("player_name"),
		        			new LinkedHashMap<>(),
		        			result.getLong("jobswitchtimer"),
		        			0,
		        			ConvertHandler.deserialisedArray(result.getString("furnaces")),
		        			ConvertHandler.deserialisedArray(result.getString("blastfurnaces")),
		        			ConvertHandler.deserialisedArray(result.getString("smokers")),
		        			ConvertHandler.deserialisedArray(result.getString("brewstands")),
		        			ConvertHandler.deserialisedArray(result.getString("composters")));
		        	list.add(ep);
		        }
		        return list;
		    } catch (SQLException e) 
			{
				  ProfessionPro.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return null;
	}
	
	default ArrayList<PluginUser> getAllListAtI(ProfessionPro plugin, String orderByColumn,
			String whereColumn, Object...whereObject) throws IOException
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT * FROM `" + plugin.getMysqlHandler().tableNameI
						+ "` WHERE "+whereColumn+" ORDER BY "+orderByColumn+" DESC";
		        preparedStatement = conn.prepareStatement(sql);
		        int i = 1;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        result = preparedStatement.executeQuery();
		        ArrayList<PluginUser> list = new ArrayList<PluginUser>();
		        while (result.next()) 
		        {
		        	PluginUser ep = new PluginUser(null,
		        			UUID.fromString(result.getString("player_uuid")),
		        			result.getString("player_name"),
		        			new LinkedHashMap<>(),
		        			result.getLong("jobswitchtimer"),
		        			0,
		        			ConvertHandler.deserialisedArray(result.getString("furnaces")),
		        			ConvertHandler.deserialisedArray(result.getString("blastfurnaces")),
		        			ConvertHandler.deserialisedArray(result.getString("smokers")),
		        			ConvertHandler.deserialisedArray(result.getString("brewstands")),
		        			ConvertHandler.deserialisedArray(result.getString("composters")));
		        	list.add(ep);
		        }
		        return list;
		    } catch (SQLException e) 
			{
				  ProfessionPro.log.warning("Error: " + e.getMessage());
				  e.printStackTrace();
		    } finally 
			{
		    	  try 
		    	  {
		    		  if (result != null) 
		    		  {
		    			  result.close();
		    		  }
		    		  if (preparedStatement != null) 
		    		  {
		    			  preparedStatement.close();
		    		  }
		    	  } catch (Exception e) {
		    		  e.printStackTrace();
		    	  }
		      }
		}
		return null;
	}
}