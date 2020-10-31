package main.java.de.avankziar.professionpro.assistance;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import main.java.de.avankziar.professionpro.ProfessionPro;
import main.java.de.avankziar.professionpro.handler.PluginUserHandler;
import main.java.de.avankziar.professionpro.handler.ProfessionHandler;
import main.java.de.avankziar.professionpro.objects.PluginSettings;
import main.java.de.avankziar.professionpro.objectsprofession.UserProfession;
import main.java.de.avankziar.professionpro.objectsuser.PluginUser;
import net.milkbowl.vault.economy.EconomyResponse;

public class BackgroundTask
{
	private static ProfessionPro plugin;
	
	public BackgroundTask(ProfessionPro plugin)
	{
		BackgroundTask.plugin = plugin;
		initBackgroundTask();
	}
	
	public boolean initBackgroundTask()
	{
		runDistributeMoneyTask();
		runDistrubuteExpTask();
		return true;
	}
	
	private void runDistributeMoneyTask()
	{
		new BukkitRunnable()
		{
			
			@Override
			public void run()
			{
				for(Player player : Bukkit.getOnlinePlayers())
				{
					PluginUser user = PluginUserHandler.getUser(player.getUniqueId());
					if(user.getPlayer() == null)
					{
						continue;
					}
					final double mademoney = user.getMadeMoney();
					EconomyResponse er = plugin.getEco().depositPlayer(Bukkit.getOfflinePlayer(user.getUuid()), mademoney);
					if(!er.transactionSuccess())
					{
						continue;
					}
					//TODO EcoLog | JobLog | Jobtrendlog
					user.setMadeMoney(user.getMadeMoney()-mademoney);
				}
			}
		}.runTaskTimerAsynchronously(plugin, 20L*1, 20L*PluginSettings.get().getDistributeMoneySchedulerTimer());
	}
	
	private void runDistrubuteExpTask()
	{
		new BukkitRunnable()
		{
			@Override
			public void run()
			{
				for(Player player : Bukkit.getOnlinePlayers())
				{
					PluginUser user = PluginUserHandler.getUser(player.getUniqueId());
					if(user.getPlayer() == null)
					{
						continue;
					}
					for(UserProfession profession : user.getProfession().values())
					{
						ProfessionHandler.updateActualExp(profession);
					}
				}
			}
		}.runTaskTimerAsynchronously(plugin, 20L*1, 20L*PluginSettings.get().getDistributeExpSchedulerTimer());
	}
}
