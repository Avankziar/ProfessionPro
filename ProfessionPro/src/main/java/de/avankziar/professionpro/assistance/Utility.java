package main.java.de.avankziar.professionpro.assistance;

import main.java.de.avankziar.professionpro.ProfessionPro;

public class Utility 
{
	private ProfessionPro plugin;
	
	public Utility(ProfessionPro plugin)
	{
		this.plugin = plugin;
	}
	
	/*public static String convertUUIDToName(String uuid)
	{
		String name = null;
		if(plugin.getMysqlHandler().exist(MysqlHandler.Type.BACK, "player_uuid = ?", uuid))
		{
			name = ((Back) plugin.getMysqlHandler().getData(MysqlHandler.Type.BACK, "player_uuid = ?", uuid)).getName();
			return name;
		}
		return null;
	}
	
	public static UUID convertNameToUUID(String playername)
	{
		UUID uuid = null;
		if(plugin.getMysqlHandler().exist(MysqlHandler.Type.BACK, "player_name = ?", playername))
		{
			uuid = ((Back) plugin.getMysqlHandler().getData(MysqlHandler.Type.BACK, "player_name = ?", playername)).getUuid();
			return uuid;
		}
		return null;
	}*/
}
