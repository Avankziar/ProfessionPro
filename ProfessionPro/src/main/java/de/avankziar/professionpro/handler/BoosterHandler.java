package main.java.de.avankziar.professionpro.handler;

import java.util.ArrayList;

import main.java.de.avankziar.professionpro.ProfessionPro;
import main.java.de.avankziar.professionpro.objects.Booster;

public class BoosterHandler
{
	private ProfessionPro plugin;
	private static ArrayList<Booster> activeBooster = new ArrayList<>();
	
	public BoosterHandler(ProfessionPro plugin)
	{
		this.plugin = plugin;
	}
	
	public static void init()
	{
		//TODO load Mysql Booster
	}
	
	public static void activateBooster(int concernedProfessionId, double multiplicator, long totalTimeInMilliseconds)
	{
		/*
		 * - Check if Booster exist
		 * - If Booster exist, extend time. Else create new one.
		 * - In both case send a Bungee message to other spigot server
		 */
	}
	
	/*
	 * Calling, if the a player joined on a empty server.
	 */
	public static void updateBooster()
	{
		/*
		 * - Check Mysql an update the activeBooster list.
		 */
	}
	
	public static ArrayList<Booster> getActiveBooster()
	{
		return activeBooster;
	}
}
