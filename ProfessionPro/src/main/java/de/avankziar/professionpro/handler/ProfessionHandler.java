package main.java.de.avankziar.professionpro.handler;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import main.java.de.avankziar.professionpro.ProfessionPro;
import main.java.de.avankziar.professionpro.enums.Activity;
import main.java.de.avankziar.professionpro.enums.ExpPerLevelMode;
import main.java.de.avankziar.professionpro.objectsprofession.ExchangeObject;
import main.java.de.avankziar.professionpro.objectsprofession.GeneralProfession;
import main.java.de.avankziar.professionpro.objectsprofession.KeyObject;
import main.java.de.avankziar.professionpro.objectsprofession.Level;
import main.java.de.avankziar.professionpro.objectsprofession.UserProfession;

public class ProfessionHandler
{
	private static LinkedHashMap<Integer, GeneralProfession> installedProfession = new LinkedHashMap<>();
	
	public static LinkedHashMap<Integer, GeneralProfession> getInstalledProfessions()
	{
		return installedProfession;
	}
	
	public static void init(ProfessionPro plugin)
	{
		List<String> professionList = plugin.getYamlHandler().getConfig().getStringList("General.Profession");
		if(professionList == null)
		{
			plugin.getLogger().severe("Can not load the Professionlist in the config.yml!");
			return;
		}
		for(String path : professionList)
		{
			if(plugin.getYamlHandler().getPro().get(path) == null)
			{
				continue;
			}
			if(plugin.getYamlHandler().getPro().get(path+".Id") == null)
			{
				continue;
			}
			int id = plugin.getYamlHandler().getPro().getInt(path+".Id");
			String name = plugin.getYamlHandler().getPro().getString(path+".Name");
			String permission = plugin.getYamlHandler().getPro().getString(path+".Permission");
			boolean canGetExp = plugin.getYamlHandler().getPro().getBoolean(path+".CanGetExp");
			int minLevel = 1;
			int maxLevel = plugin.getYamlHandler().getPro().getInt(path+".MaxLevel");
			double expEndMulti = plugin.getYamlHandler().getPro().getDouble(path+".ExpEndMulti");
			double moneyEndMulti = plugin.getYamlHandler().getPro().getDouble(path+".MoneyEndMulti");
			ExpPerLevelMode expPerLevelMode	= ExpPerLevelMode.valueOf(plugin.getYamlHandler().getPro().getString(path+".Level.ExpPerLevelMode"));
			LinkedHashMap<Integer, Level> levels = new LinkedHashMap<>();
			if(expPerLevelMode == ExpPerLevelMode.FIX)
			{
				double expValue = plugin.getYamlHandler().getPro().getDouble(path+".Level.Fix.ExpPerLevel");
				double perLevelExpMultiplicator = (expEndMulti - 1)/maxLevel;
				double perLevelMoneyMultiplicator = (moneyEndMulti - 1)/maxLevel;
				while(minLevel < maxLevel)
				{
					boolean endLevel = false;
					if(minLevel == maxLevel)
					{
						endLevel = true;
					}
					double perLevelExpMulti = 1 + perLevelExpMultiplicator*minLevel - perLevelExpMultiplicator;
					double perLevelMoneyMulti = 1 + perLevelMoneyMultiplicator*minLevel - perLevelMoneyMultiplicator;
					Level level = new Level(minLevel, endLevel, 0, expValue, perLevelExpMulti, perLevelMoneyMulti);
					minLevel++;
					levels.put(minLevel, level);
				}
			} else if(expPerLevelMode == ExpPerLevelMode.EXPONENTIAL)
			{
				double baseValue = plugin.getYamlHandler().getPro().getDouble(path+".Level.Exponential.BaseValue");
				double perLevelExpMultiplicator = (expEndMulti - 1)/maxLevel;
				double perLevelMoneyMultiplicator = (moneyEndMulti - 1)/maxLevel;
				while(minLevel < maxLevel)
				{
					boolean endLevel = false;
					if(minLevel == maxLevel)
					{
						endLevel = true;
					}
					double perLevelExpMulti = 1 + perLevelExpMultiplicator*minLevel - perLevelExpMultiplicator;
					double perLevelMoneyMulti = 1 + perLevelMoneyMultiplicator*minLevel - perLevelMoneyMultiplicator;
					
					double expValue = Math.pow(baseValue, minLevel+1);
					
					Level level = new Level(minLevel, endLevel, 0, expValue, perLevelExpMulti, perLevelMoneyMulti);
					minLevel++;
					levels.put(minLevel, level);
				}
			} else if(expPerLevelMode == ExpPerLevelMode.LIMESFUNCTION)
			{
				double endExp = plugin.getYamlHandler().getPro().getDouble(path+".Level.LimesFunction.EndExp");
				double curveValue = plugin.getYamlHandler().getPro().getDouble(path+".Level.LimesFunction.CurveValue");
				double perLevelExpMultiplicator = (expEndMulti - 1)/maxLevel;
				double perLevelMoneyMultiplicator = (moneyEndMulti - 1)/maxLevel;
				while(minLevel < maxLevel)
				{
					boolean endLevel = false;
					if(minLevel == maxLevel)
					{
						endLevel = true;
					}
					double perLevelExpMulti = 1 + perLevelExpMultiplicator*minLevel - perLevelExpMultiplicator;
					double perLevelMoneyMulti = 1 + perLevelMoneyMultiplicator*minLevel - perLevelMoneyMultiplicator;
					
					double expValue = endExp - endExp*(1-Math.exp(curveValue*(minLevel+1)));
					
					Level level = new Level(minLevel, endLevel, 0, expValue, perLevelExpMulti, perLevelMoneyMulti);
					minLevel++;
					levels.put(minLevel, level);
				}
			} else if(expPerLevelMode == ExpPerLevelMode.PROPORTIONALGRADIENT)
			{
				double expFixValue = plugin.getYamlHandler().getPro().getDouble(path+".Level.ProportionalGradient.ExpValue");
				double perLevelExpMultiplicator = (expEndMulti - 1)/maxLevel;
				double perLevelMoneyMultiplicator = (moneyEndMulti - 1)/maxLevel;
				while(minLevel < maxLevel)
				{
					boolean endLevel = false;
					if(minLevel == maxLevel)
					{
						endLevel = true;
					}
					double perLevelExpMulti = 1 + perLevelExpMultiplicator*minLevel - perLevelExpMultiplicator;
					double perLevelMoneyMulti = 1 + perLevelMoneyMultiplicator*minLevel - perLevelMoneyMultiplicator;
					
					double expValue = (minLevel+1)*expFixValue;
					
					Level level = new Level(minLevel, endLevel, 0, expValue, perLevelExpMulti, perLevelMoneyMulti);
					minLevel++;
					levels.put(minLevel, level);
				}
			} else if(expPerLevelMode == ExpPerLevelMode.STAIRSSLOPE)
			{
				double beginValue = plugin.getYamlHandler().getPro().getDouble(path+"Level.StairsSlope.BeginValue");
				double expIncreaseValue = plugin.getYamlHandler().getPro().getDouble(path+"Level.StairsSlope.ExpIncreaseValue");
				int amountPausedLevel = plugin.getYamlHandler().getPro().getInt(path+".Level.StairsSlope.AmountPausedLevel");
				double perLevelExpMultiplicator = (expEndMulti - 1)/maxLevel;
				double perLevelMoneyMultiplicator = (moneyEndMulti - 1)/maxLevel;
				while(minLevel < maxLevel)
				{
					boolean endLevel = false;
					if(minLevel == maxLevel)
					{
						endLevel = true;
					}
					double perLevelExpMulti = 1 + perLevelExpMultiplicator*minLevel - perLevelExpMultiplicator;
					double perLevelMoneyMulti = 1 + perLevelMoneyMultiplicator*minLevel - perLevelMoneyMultiplicator;
					
					double expValue = Math.floorDiv(minLevel+1, amountPausedLevel)*expIncreaseValue+beginValue;
					
					Level level = new Level(minLevel, endLevel, 0, expValue, perLevelExpMulti, perLevelMoneyMulti);
					minLevel++;
					levels.put(minLevel, level);
				}
			}
			LinkedHashMap<Activity, LinkedHashMap<KeyObject, ExchangeObject>> exchangePerActivity = new LinkedHashMap<>();
			List<Activity> activitys = new ArrayList<Activity>(EnumSet.allOf(Activity.class));
			for(Activity activity : activitys)
			{
				if(plugin.getYamlHandler().getPro().get(path+".ActivityAndExchange."+activity.toString()) != null)
				{
					LinkedHashMap<KeyObject, ExchangeObject> exchange = new LinkedHashMap<>();
					List<String> list = plugin.getYamlHandler().getPro().getStringList(path+".ActivityAndExchange."+activity.toString());
					for(String l : list)
					{
						String[] s = l.split(";");
						Material material = Material.valueOf(s[0]);
						EntityType entityType = EntityType.valueOf(s[1]);
						double exp = Double.parseDouble(s[2]);
						double money = Double.parseDouble(s[3]);
						KeyObject keo = new KeyObject(material, entityType);
						ExchangeObject exo = new ExchangeObject(exp, money);
						exchange.put(keo,exo);
					}
					exchangePerActivity.put(activity, exchange);
				}
			}
			LinkedHashMap<Material, Boolean> professionInteractions = new LinkedHashMap<>();
			if(plugin.getYamlHandler().getPro().get(path+".ProfessionBlock") != null)
			{
				List<String> interactions = plugin.getYamlHandler().getPro().getStringList(path+".ProfessionBlock");
				for(String inter : interactions)
				{
					String[] in = inter.split(";");
					Material material = Material.valueOf(in[0]);
					boolean status = Boolean.getBoolean(in[1]);
					professionInteractions.put(material, status);
				}
			}
			GeneralProfession profession = new GeneralProfession(
					id, name, permission, maxLevel, levels, canGetExp, expPerLevelMode, expEndMulti, moneyEndMulti,
					exchangePerActivity, professionInteractions);
			installedProfession.put(id, profession);
		}
	}

	public static GeneralProfession getGeneralProfession(int id)
	{
		return installedProfession.get(id);
	}
	
	/*
	 * Called, when ?,  not sure-...
	 */
	public static ArrayList<UserProfession> UpdateUserProfessions(ArrayList<UserProfession> userProfessions)
	{
		ArrayList<UserProfession> list = new ArrayList<>();
		
		for(UserProfession userProfession : userProfessions)
		{
			GeneralProfession generalProfession = getGeneralProfession(userProfession.getProfession().getId());
			if(generalProfession == null)
			{
				continue;
			}
			
		}
		//TODO update the userProfession with installedProfession
		return list;
	}
	
	/*
	 * Called, to Update mading Exp. returns true, if a new Level is reached.
	 */
	public static boolean updateActualExp(UserProfession profession)
	{
		Level atTimeLevel = profession.getUserLevel();
		double exp = profession.getMadeExp();
		if(atTimeLevel.isEndLevel())
		{
			if(atTimeLevel.getEndExp() <= atTimeLevel.getActualExp())
			{
				return false;
			}
			if(atTimeLevel.getEndExp() <= atTimeLevel.getActualExp()+exp)
			{
				profession.getUserLevel().setActualExp(atTimeLevel.getEndExp());
				profession.setMadeExp(0);
				return false;
			} else
			{
				profession.getUserLevel().setActualExp(atTimeLevel.getActualExp()+exp);
				profession.setMadeExp(0);
				return false;
			}
		} else
		{
			if(atTimeLevel.getEndExp() <= atTimeLevel.getActualExp()+exp)
			{
				final int levelnumber = atTimeLevel.getLevel();
				final double dif = (atTimeLevel.getActualExp()+exp)-atTimeLevel.getEndExp();
				Level newLevel = profession.getProfession().getLevels().get(levelnumber+1);
				newLevel.setActualExp(dif);
				profession.setUserLevel(newLevel);
				profession.setMadeExp(0);
				return true;
			} else
			{
				atTimeLevel.setActualExp(atTimeLevel.getActualExp()+exp);
				profession.setMadeExp(0);
				return false;
			}
		}
		
	}
}
