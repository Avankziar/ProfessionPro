package main.java.de.avankziar.professionpro.objectsprofession;

import java.util.LinkedHashMap;

import org.bukkit.Material;

import main.java.de.avankziar.professionpro.enums.Activity;
import main.java.de.avankziar.professionpro.enums.ExpPerLevelMode;

public class GeneralProfession 
{
	private int id; //Unique identifier
	private String name; //Name of Profession
	private String permission; //Perm to aquired this job.
	private int maxLevel;
	private LinkedHashMap<Integer, Level> levels; //All Levels
	private boolean canGetExp; //CanGetExp
	private ExpPerLevelMode expPerLevelMode; //Fix or Limes
	private double expEndMulti; //Differenc from Lvl 1 to EndLevel for a ExpMulti. At lvl 1 is the Multi always '1.0'
	private double moneyEndMulti; //Differenc from Lvl 1 to EndLevel for a MoneyMulti. At lvl 1 is the Multi always '1.0'
	private LinkedHashMap<Activity, LinkedHashMap<KeyObject, ExchangeObject>> exchangePerActivity; //Per Activity, Money and Exp per Material and Entity
	private LinkedHashMap<Material, Boolean> professionInteractions; //All Blocks the player can be interact or not.
	
	public GeneralProfession(int id, String name, String permission, int maxLevel, LinkedHashMap<Integer, Level> levels,
			boolean canGetExp, ExpPerLevelMode expPerLevelMode, double expEndMulti, double moneyEndMulti,
			LinkedHashMap<Activity, LinkedHashMap<KeyObject, ExchangeObject>> exchangePerActivity, 
			LinkedHashMap<Material, Boolean> professionInteractions)
	{
		setId(id);
		setName(name);
		setPermission(permission);
		setMaxLevel(maxLevel);
		setLevels(levels);
		setCanGetExp(canGetExp);
		setExpPerLevelMode(expPerLevelMode);
		setExpEndMulti(expEndMulti);
		setMoneyEndMulti(moneyEndMulti);
		setExchangePerActivity(exchangePerActivity);
		setProfessionInteractions(professionInteractions);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}

	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	public String getPermission()
	{
		return permission;
	}

	public void setPermission(String permission)
	{
		this.permission = permission;
	}

	public boolean isCanGetExp() {
		return canGetExp;
	}

	public void setCanGetExp(boolean canGetExp) {
		this.canGetExp = canGetExp;
	}

	public ExpPerLevelMode getExpPerLevelMode() {
		return expPerLevelMode;
	}

	public void setExpPerLevelMode(ExpPerLevelMode expPerLevelMode) {
		this.expPerLevelMode = expPerLevelMode;
	}

	public double getMoneyEndMulti() {
		return moneyEndMulti;
	}

	public void setMoneyEndMulti(double moneyEndMulti) {
		this.moneyEndMulti = moneyEndMulti;
	}

	public double getExpEndMulti()
	{
		return expEndMulti;
	}

	public void setExpEndMulti(double expEndMulti)
	{
		this.expEndMulti = expEndMulti;
	}

	public LinkedHashMap<Material, Boolean> getProfessionInteractions()
	{
		return professionInteractions;
	}

	public void setProfessionInteractions(LinkedHashMap<Material, Boolean> professionInteractions)
	{
		this.professionInteractions = professionInteractions;
	}

	public  LinkedHashMap<Activity, LinkedHashMap<KeyObject, ExchangeObject>> getExchangePerActivity()
	{
		return exchangePerActivity;
	}

	public void setExchangePerActivity(LinkedHashMap<Activity, LinkedHashMap<KeyObject, ExchangeObject>> exchangePerActivity)
	{
		this.exchangePerActivity = exchangePerActivity;
	}

	public LinkedHashMap<Integer, Level> getLevels()
	{
		return levels;
	}

	public void setLevels(LinkedHashMap<Integer, Level> levels)
	{
		this.levels = levels;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}
}
