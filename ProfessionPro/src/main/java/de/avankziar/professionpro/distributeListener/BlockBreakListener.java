package main.java.de.avankziar.professionpro.distributeListener;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import main.java.de.avankziar.professionpro.enums.Activity;
import main.java.de.avankziar.professionpro.events.DistributeEvent;
import main.java.de.avankziar.professionpro.handler.PluginUserHandler;
import main.java.de.avankziar.professionpro.objectsprofession.ExchangeObject;
import main.java.de.avankziar.professionpro.objectsprofession.KeyObject;
import main.java.de.avankziar.professionpro.objectsprofession.UserProfession;
import main.java.de.avankziar.professionpro.objectsuser.PluginUser;

public class BlockBreakListener implements Listener
{	
	@EventHandler
	public void onBreak(BlockBreakEvent event)
	{
		if(event.isCancelled())
		{
			return;
		}
		int amount = 1;
		Activity activity = Activity.BREACKING;
		Material material = event.getBlock().getType();
		EntityType entityType = EntityType.UNKNOWN;
		KeyObject keo = new KeyObject(material, entityType);
		
		Player player = event.getPlayer();
		UUID uuid = player.getUniqueId();
		ExchangeObject exo = null;
		PluginUser user = PluginUserHandler.getUser(player.getUniqueId());
		if(user == null)
		{
			return;
		}
		for(UserProfession profession : user.getProfession().values())
		{
			int id = profession.getProfession().getId();
			exo = PluginUserHandler.getLastActivity(uuid, id, activity, keo);
			if(exo != null)
			{
				Bukkit.getPluginManager().callEvent(new DistributeEvent(user, profession.getProfession(), amount, keo, exo));
			}
		}
		if(exo != null)
		{
			return;
		}
		for(UserProfession profession : user.getProfession().values())
		{
			if(profession.getProfession().getExchangePerActivity().containsKey(activity))
			{
				exo = profession.getProfession().getExchangePerActivity().get(activity).get(keo);
				if(exo != null)
				{
					Bukkit.getPluginManager().callEvent(new DistributeEvent(user, profession.getProfession(), amount, keo, exo));
					PluginUserHandler.addingLastActivity(uuid, profession.getProfession().getId(), activity, keo, exo);
				}
			}
		}		
	}
}
