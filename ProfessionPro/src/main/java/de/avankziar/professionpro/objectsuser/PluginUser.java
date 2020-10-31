package main.java.de.avankziar.professionpro.interfaces;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class User 
{
	private Player player;
	private ArrayList<Profession> profession;
	private double exp;
	private int level;
	private long jobSwitchTimer;
	private double madeMoney;
	private double madeExp;
	private ArrayList<Location> brewstands = new ArrayList<Location>();
	private ArrayList<Location> furnaces = new ArrayList<Location>();
	
	public User(Player player, ArrayList<Profession> profession, double exp, int level, long jobSwitchTimer, double madeMoney, double madeExp,
			ArrayList<Location> brewstands, ArrayList<Location> furnance)
	{
		setPlayer(player);
		setProfession(profession);
		setExp(exp);
		setLevel(level);
		setJobSwitchTimer(jobSwitchTimer);
		setMadeMoney(madeMoney);
		setMadeExp(madeExp);
		setBrewstands(brewstands);
		setFurnaces(furnance);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Profession> getProfession() {
		return profession;
	}

	public void setProfession(ArrayList<Profession> profession) {
		this.profession = profession;
	}

	public double getExp() {
		return exp;
	}

	public void setExp(double exp) {
		this.exp = exp;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public long getJobSwitchTimer() {
		return jobSwitchTimer;
	}

	public void setJobSwitchTimer(long jobSwitchTimer) {
		this.jobSwitchTimer = jobSwitchTimer;
	}

	public double getMadeMoney() {
		return madeMoney;
	}

	public void setMadeMoney(double madeMoney) {
		this.madeMoney = madeMoney;
	}

	public double getMadeExp() {
		return madeExp;
	}

	public void setMadeExp(double madeExp) {
		this.madeExp = madeExp;
	}

	public ArrayList<Location> getBrewstands() {
		return brewstands;
	}

	public void setBrewstands(ArrayList<Location> brewstands) {
		this.brewstands = brewstands;
	}

	public ArrayList<Location> getFurnaces() {
		return furnaces;
	}

	public void setFurnaces(ArrayList<Location> furnaces) {
		this.furnaces = furnaces;
	}
}
