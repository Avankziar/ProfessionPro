package main.java.de.avankziar.professionpro.assistance;

import org.bukkit.scheduler.BukkitRunnable;

import main.java.de.avankziar.professionpro.ProfessionPro;

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
		
		return true;
	}
	
	private void distributeMoney()
	{
		long period = plugin.getYamlHandler().getConfig().getLong("");
		new BukkitRunnable()
		{
			
			@Override
			public void run()
			{
				//PPUser.madeMoney
			}
		}.runTaskTimer(plugin, 20L, period);
	}
}
