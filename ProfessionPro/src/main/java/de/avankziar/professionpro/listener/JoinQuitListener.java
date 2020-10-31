package main.java.de.avankziar.professionpro.listener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import main.java.de.avankziar.professionpro.ProfessionPro;
import main.java.de.avankziar.professionpro.database.MysqlHandler;
import main.java.de.avankziar.professionpro.handler.ConvertHandler;
import main.java.de.avankziar.professionpro.handler.PluginUserHandler;
import main.java.de.avankziar.professionpro.objectsprofession.UserProfession;
import main.java.de.avankziar.professionpro.objectsuser.PluginUser;

public class JoinQuitListener implements Listener
{
	private ProfessionPro plugin;
	
	public JoinQuitListener(ProfessionPro plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) throws IOException
	{
		Player player = event.getPlayer();
		if(plugin.getMysqlHandler().exist(MysqlHandler.Type.PLUGINUSER, "`player_uuid` = ?", player.getUniqueId().toString()))
		{
			PluginUser pluginUser = (PluginUser) plugin.getMysqlHandler().getData(MysqlHandler.Type.PLUGINUSER,
					"`player_uuid` = ?", player.getUniqueId().toString());
			pluginUser.setPlayer(player);
			ArrayList<UserProfession> userProfession = ConvertHandler.convertListII(
					plugin.getMysqlHandler().getAllListAt(MysqlHandler.Type.USERPROFESSION, "`id`", true, 
							"`player_uuid` = ? AND `isactive`", player.getUniqueId().toString(), true));
			LinkedHashMap<Integer, UserProfession> userProfessions = new LinkedHashMap<>();
			for(UserProfession profession : userProfession)
			{
				userProfessions.put(profession.getProfession().getId(), profession);
			}
			pluginUser.setProfession(userProfessions);
			PluginUserHandler.addUser(pluginUser);
		} else
		{
			PluginUser pluginUser = new PluginUser(player,
					player.getUniqueId(), player.getName(), new LinkedHashMap<>(), System.currentTimeMillis(), 0, new ArrayList<>());
			plugin.getMysqlHandler().create(MysqlHandler.Type.PLUGINUSER, pluginUser);
			PluginUserHandler.addUser(pluginUser);
		}
	}
}
