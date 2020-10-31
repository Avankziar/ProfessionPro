package main.java.de.avankziar.professionpro.objectsuser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import main.java.de.avankziar.professionpro.objectsprofession.UserProfession;

public class PluginUser 
{
	private Player player;
	private UUID uuid;
	private String name;
	private LinkedHashMap<Integer, UserProfession> profession; //In Use Profession, Database
	private long jobSwitchTimer; //When the player can be switch the job again
	private double madeMoney; //RAM value of made money
	private ArrayList<Location> furnaces = new ArrayList<Location>();
	private ArrayList<Location> blastFurnaces = new ArrayList<Location>();
	private ArrayList<Location> smokers = new ArrayList<Location>();
	private ArrayList<Location> brewstands = new ArrayList<Location>();
	private ArrayList<Location> composters = new ArrayList<Location>();
	
	public PluginUser(Player player, UUID uuid, String name,
			LinkedHashMap<Integer, UserProfession> profession,
			long jobSwitchTimer, double madeMoney,
			ArrayList<Location> furnaces, ArrayList<Location> blastFurnaces,
			ArrayList<Location> smokers, ArrayList<Location> brewstands,
			ArrayList<Location> composters)
	{
		setPlayer(player);
		setUuid(uuid);
		setName(name);
		setProfession(profession);
		setJobSwitchTimer(jobSwitchTimer);
		setMadeMoney(madeMoney);
		setFurnaces(furnaces);
		setBlastFurnaces(blastFurnaces);
		setSmokers(smokers);
		setBrewstands(brewstands);
		setComposters(composters);
	}

	public Player getPlayer() 
	{
		return player;
	}

	public void setPlayer(Player player) 
	{
		this.player = player;
	}

	public LinkedHashMap<Integer, UserProfession> getProfession() 
	{
		return profession;
	}

	public void setProfession(LinkedHashMap<Integer, UserProfession> profession) 
	{
		this.profession = profession;
	}

	public long getJobSwitchTimer() 
	{
		return jobSwitchTimer;
	}

	public void setJobSwitchTimer(long jobSwitchTimer) 
	{
		this.jobSwitchTimer = jobSwitchTimer;
	}

	public double getMadeMoney() 
	{
		return madeMoney;
	}

	public void setMadeMoney(double madeMoney) 
	{
		this.madeMoney = madeMoney;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public ArrayList<Location> getFurnaces()
	{
		return furnaces;
	}

	public void setFurnaces(ArrayList<Location> furnaces)
	{
		this.furnaces = furnaces;
	}

	public ArrayList<Location> getBlastFurnaces()
	{
		return blastFurnaces;
	}

	public void setBlastFurnaces(ArrayList<Location> blastFurnaces)
	{
		this.blastFurnaces = blastFurnaces;
	}

	public ArrayList<Location> getSmokers()
	{
		return smokers;
	}

	public void setSmokers(ArrayList<Location> smokers)
	{
		this.smokers = smokers;
	}

	public ArrayList<Location> getBrewstands()
	{
		return brewstands;
	}

	public void setBrewstands(ArrayList<Location> brewstands)
	{
		this.brewstands = brewstands;
	}

	public ArrayList<Location> getComposters()
	{
		return composters;
	}

	public void setComposters(ArrayList<Location> composters)
	{
		this.composters = composters;
	}
}
