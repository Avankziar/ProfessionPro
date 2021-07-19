package main.java.de.avankziar.professionpro.objects;

import java.util.ArrayList;
import java.util.List;

import main.java.de.avankziar.professionpro.ProfessionPro;
import main.java.de.avankziar.professionpro.database.YamlHandler;
import main.java.de.avankziar.professionpro.enums.ExpSwitchLooseType;

public class PluginSettings
{
	private static PluginSettings pluginSettings;
	
	private boolean bungee;
	private List<String> professionPaths;
	private boolean updateJoiningPlayersProfession;
	private int maximalProfessionAtOneTime;
	private int maximalUtilityBlocksAtOneTime;
	private long minimalTimeBeforProfessionSwitch;
	private boolean looseExpBySwitchingProfession;
	private ExpSwitchLooseType expSwitchLooseType;
	private double fixValue;
	private double percentValue;
	private int distributeMoneySchedulerTimer;
	private int distributeExpSchedulerTimer;
	
	private PluginSettings(boolean bungee, List<String> professionPaths,
			boolean updateJoiningPlayersProfession, int maximalProfessionAtOneTime, int maximalUtilityBlocksAtOneTime,
			long minimalTimeBeforProfessionSwitch,
			boolean looseExpBySwitchingProfession, ExpSwitchLooseType expSwitchLooseType, double fixValue, double percentValue,
			int distributeMoneySchedulerTimer, int distributeExpSchedulerTimer)
	{
		setBungee(bungee);
		setProfessionPaths(professionPaths);
		setUpdateJoiningPlayersProfession(updateJoiningPlayersProfession);
		setMaximalProfessionAtOneTime(maximalProfessionAtOneTime);
		setMaximalUtilityBlocksAtOneTime(maximalUtilityBlocksAtOneTime);
		setMinimalTimeBeforProfessionSwitch(minimalTimeBeforProfessionSwitch);
		setLooseExpBySwitchingProfession(looseExpBySwitchingProfession);
		setExpSwitchLooseType(expSwitchLooseType);
		setFixValue(fixValue);
		setPercentValue(percentValue);
		setDistributeMoneySchedulerTimer(distributeMoneySchedulerTimer);
		setDistributeExpSchedulerTimer(distributeExpSchedulerTimer);
	}
	
	public static void init(ProfessionPro plugin)
	{
		YamlHandler yh = plugin.getYamlHandler();
		List<String> list = new ArrayList<>();
		if(yh.getConfig().get("General.Professions") != null)
		{
			list = yh.getConfig().getStringList("General.Professions");
		}
		pluginSettings = new PluginSettings(
				yh.getConfig().getBoolean("Bungee", false),
				list,
				yh.getConfig().getBoolean("Player.UpdateJoiningPlayersProfession", false),
				yh.getConfig().getInt("Player.MaximalProfessionAtOneTime", 1),
				yh.getConfig().getInt("Player.MaximalUtilityBlocksAtOneTime", 5),
				yh.getConfig().getLong("Player.MinimalTimeBeforProfessionSwitch", 84600000),
				yh.getConfig().getBoolean("Player.LooseExpBySwitchingProfession", false),
				ExpSwitchLooseType.valueOf(yh.getConfig().getString("Player.ExpSwitchLooseType", "FIX")),
				yh.getConfig().getDouble("Player.ExpLoose.Fix", 1000.0),
				yh.getConfig().getDouble("Player.ExpLoose.Percantage", 0.1),
				yh.getConfig().getInt("Player.DistributeMoneySchedulerTimer", 30),
				yh.getConfig().getInt("Player.DistributeExpSchedulerTimer", 10));
	}
	
	public static PluginSettings get()
	{
		return pluginSettings;
	}

	public boolean isBungee()
	{
		return bungee;
	}

	public void setBungee(boolean bungee)
	{
		this.bungee = bungee;
	}

	public List<String> getProfessionPaths()
	{
		return professionPaths;
	}

	public void setProfessionPaths(List<String> professionPaths)
	{
		this.professionPaths = professionPaths;
	}

	public boolean isUpdateJoiningPlayersProfession()
	{
		return updateJoiningPlayersProfession;
	}

	public void setUpdateJoiningPlayersProfession(boolean updateJoiningPlayersProfession)
	{
		this.updateJoiningPlayersProfession = updateJoiningPlayersProfession;
	}

	public int getMaximalProfessionAtOneTime()
	{
		return maximalProfessionAtOneTime;
	}

	public void setMaximalProfessionAtOneTime(int maximalProfessionAtOneTime)
	{
		this.maximalProfessionAtOneTime = maximalProfessionAtOneTime;
	}

	public boolean isLooseExpBySwitchingProfession()
	{
		return looseExpBySwitchingProfession;
	}

	public void setLooseExpBySwitchingProfession(boolean looseExpBySwitchingProfession)
	{
		this.looseExpBySwitchingProfession = looseExpBySwitchingProfession;
	}

	public ExpSwitchLooseType getExpSwitchLooseType()
	{
		return expSwitchLooseType;
	}

	public void setExpSwitchLooseType(ExpSwitchLooseType expSwitchLooseType)
	{
		this.expSwitchLooseType = expSwitchLooseType;
	}

	public double getFixValue()
	{
		return fixValue;
	}

	public void setFixValue(double fixValue)
	{
		this.fixValue = fixValue;
	}

	public double getPercentValue()
	{
		return percentValue;
	}

	public void setPercentValue(double percentValue)
	{
		this.percentValue = percentValue;
	}

	public int getMaximalUtilityBlocksAtOneTime()
	{
		return maximalUtilityBlocksAtOneTime;
	}

	public void setMaximalUtilityBlocksAtOneTime(int maximalUtilityBlocksAtOneTime)
	{
		this.maximalUtilityBlocksAtOneTime = maximalUtilityBlocksAtOneTime;
	}

	public long getMinimalTimeBeforProfessionSwitch()
	{
		return minimalTimeBeforProfessionSwitch;
	}

	public void setMinimalTimeBeforProfessionSwitch(long minimalTimeBeforProfessionSwitch)
	{
		this.minimalTimeBeforProfessionSwitch = minimalTimeBeforProfessionSwitch;
	}

	public int getDistributeMoneySchedulerTimer()
	{
		return distributeMoneySchedulerTimer;
	}

	public void setDistributeMoneySchedulerTimer(int distributeMoneySchedulerTimer)
	{
		this.distributeMoneySchedulerTimer = distributeMoneySchedulerTimer;
	}

	public int getDistributeExpSchedulerTimer()
	{
		return distributeExpSchedulerTimer;
	}

	public void setDistributeExpSchedulerTimer(int distributeExpSchedulerTimer)
	{
		this.distributeExpSchedulerTimer = distributeExpSchedulerTimer;
	}

}
