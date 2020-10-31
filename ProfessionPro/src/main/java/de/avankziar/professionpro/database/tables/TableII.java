package main.java.de.avankziar.professionpro.database.tables;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

import main.java.de.avankziar.professionpro.ProfessionPro;
import main.java.de.avankziar.professionpro.handler.ProfessionHandler;
import main.java.de.avankziar.professionpro.objectsprofession.GeneralProfession;
import main.java.de.avankziar.professionpro.objectsprofession.Level;
import main.java.de.avankziar.professionpro.objectsprofession.UserProfession;

public interface TableII
{
	default boolean existII(ProfessionPro plugin, String whereColumn, Object... object) 
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT `id` FROM `" + plugin.getMysqlHandler().tableNameII 
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
	
	default boolean createII(ProfessionPro plugin, Object object) 
	{
		if(!(object instanceof UserProfession))
		{
			return false;
		}
		UserProfession ep = (UserProfession) object;
		PreparedStatement preparedStatement = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) {
			try 
			{
				String sql = "INSERT INTO `" + plugin.getMysqlHandler().tableNameII 
						+ "`(`player_uuid`, `player_name`,"
						+ " `professionid`, `isactive`, `levelnumber`, `levelexp`) " 
						+ "VALUES(?, ?, ?, ?, ?, ?)";
				preparedStatement = conn.prepareStatement(sql);
		        preparedStatement.setString(1, ep.getUuid().toString());
		        preparedStatement.setString(2, ep.getName());
		        preparedStatement.setInt(3, ep.getProfession().getId());
		        preparedStatement.setBoolean(4, ep.isActive());
		        preparedStatement.setInt(5, ep.getUserLevel().getLevel());
		        preparedStatement.setDouble(6, ep.getUserLevel().getActualExp());
		        
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
	
	default boolean updateDataII(ProfessionPro plugin, Object object, String whereColumn, Object... whereObject) 
	{
		if(!(object instanceof UserProfession))
		{
			return false;
		}
		if(whereObject == null)
		{
			return false;
		}
		UserProfession ep = (UserProfession) object;
		PreparedStatement preparedStatement = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{
				String data = "UPDATE `" + plugin.getMysqlHandler().tableNameII
						+ "` SET `player_uuid` = ?, `player_name` = ?,"
						+ " `professionid` = ?, `isactive` = ?, `levelnumber` = ?, `levelexp` = ?"
						+ " WHERE "+whereColumn;
				preparedStatement = conn.prepareStatement(data);
				preparedStatement.setString(1, ep.getUuid().toString());
		        preparedStatement.setString(2, ep.getName());
		        preparedStatement.setInt(3, ep.getProfession().getId());
		        preparedStatement.setBoolean(4, ep.isActive());
		        preparedStatement.setInt(5, ep.getUserLevel().getLevel());
		        preparedStatement.setDouble(6, ep.getUserLevel().getActualExp());
		        int i = 7;
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
	
	default Object getDataII(ProfessionPro plugin, String whereColumn, Object... whereObject)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT * FROM `" + plugin.getMysqlHandler().tableNameII 
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
		        	int professionid = result.getInt("professionid");
		        	int level = result.getInt("levelnumber");
		        	double exp = result.getDouble("levelexp");
		        	GeneralProfession profession = ProfessionHandler.getGeneralProfession(professionid);
		        	Level userLevel = profession.getLevels().get(level);
		        	userLevel.setActualExp(exp);
		        	return new UserProfession(
		        			UUID.fromString(result.getString("player_uuid")), 
		        			result.getString("player_name"),
		        			profession, 
		        			userLevel,
		        			0,
		        			result.getBoolean("isactive"));
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
	
	default boolean deleteDataII(ProfessionPro plugin, String whereColumn, Object... whereObject)
	{
		PreparedStatement preparedStatement = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		try 
		{
			String sql = "DELETE FROM `" + plugin.getMysqlHandler().tableNameII + "` WHERE "+whereColumn;
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
	
	default int lastIDII(ProfessionPro plugin)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT `id` FROM `" + plugin.getMysqlHandler().tableNameII + "` ORDER BY `id` DESC LIMIT 1";
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
	
	default int countWhereIDII(ProfessionPro plugin, String whereColumn, Object... whereObject)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT `id` FROM `" + plugin.getMysqlHandler().tableNameII
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
	
	default ArrayList<UserProfession> getListII(ProfessionPro plugin, String orderByColumn,
			int start, int end, String whereColumn, Object...whereObject)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT * FROM `" + plugin.getMysqlHandler().tableNameII
						+ "` WHERE "+whereColumn+" ORDER BY "+orderByColumn+" DESC LIMIT "+start+", "+end;
		        preparedStatement = conn.prepareStatement(sql);
		        int i = 1;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        result = preparedStatement.executeQuery();
		        ArrayList<UserProfession> list = new ArrayList<UserProfession>();
		        while (result.next()) 
		        {
		        	int professionid = result.getInt("professionid");
		        	int level = result.getInt("levelnumber");
		        	double exp = result.getDouble("levelexp");
		        	GeneralProfession profession = ProfessionHandler.getGeneralProfession(professionid);
		        	Level userLevel = profession.getLevels().get(level);
		        	userLevel.setActualExp(exp);
		        	UserProfession ep =  new UserProfession(
		        			UUID.fromString(result.getString("player_uuid")), 
		        			result.getString("player_name"),
		        			profession, 
		        			userLevel,
		        			0,
		        			result.getBoolean("isactive"));
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
	
	default ArrayList<UserProfession> getTopII(ProfessionPro plugin, String orderByColumn, int start, int end)
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT * FROM `" + plugin.getMysqlHandler().tableNameII 
						+ "` ORDER BY "+orderByColumn+" DESC LIMIT "+start+", "+end;
		        preparedStatement = conn.prepareStatement(sql);
		        
		        result = preparedStatement.executeQuery();
		        ArrayList<UserProfession> list = new ArrayList<UserProfession>();
		        while (result.next()) 
		        {
		        	int professionid = result.getInt("professionid");
		        	int level = result.getInt("levelnumber");
		        	double exp = result.getDouble("levelexp");
		        	GeneralProfession profession = ProfessionHandler.getGeneralProfession(professionid);
		        	Level userLevel = profession.getLevels().get(level);
		        	userLevel.setActualExp(exp);
		        	UserProfession ep =  new UserProfession(
		        			UUID.fromString(result.getString("player_uuid")), 
		        			result.getString("player_name"),
		        			profession, 
		        			userLevel,
		        			0,
		        			result.getBoolean("isactive"));
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
	
	default ArrayList<UserProfession> getAllListAtII(ProfessionPro plugin, String orderByColumn,
			String whereColumn, Object...whereObject) throws IOException
	{
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Connection conn = plugin.getMysqlSetup().getConnection();
		if (conn != null) 
		{
			try 
			{			
				String sql = "SELECT * FROM `" + plugin.getMysqlHandler().tableNameII
						+ "` WHERE "+whereColumn+" ORDER BY "+orderByColumn+" DESC";
		        preparedStatement = conn.prepareStatement(sql);
		        int i = 1;
		        for(Object o : whereObject)
		        {
		        	preparedStatement.setObject(i, o);
		        	i++;
		        }
		        result = preparedStatement.executeQuery();
		        ArrayList<UserProfession> list = new ArrayList<UserProfession>();
		        while (result.next()) 
		        {
		        	int professionid = result.getInt("professionid");
		        	int level = result.getInt("levelnumber");
		        	double exp = result.getDouble("levelexp");
		        	GeneralProfession profession = ProfessionHandler.getGeneralProfession(professionid);
		        	Level userLevel = profession.getLevels().get(level);
		        	userLevel.setActualExp(exp);
		        	UserProfession ep =  new UserProfession(
		        			UUID.fromString(result.getString("player_uuid")), 
		        			result.getString("player_name"),
		        			profession, 
		        			userLevel,
		        			0,
		        			result.getBoolean("isactive"));
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
