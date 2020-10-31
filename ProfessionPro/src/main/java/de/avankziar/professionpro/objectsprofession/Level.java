package main.java.de.avankziar.professionpro.objectsprofession;

public class Level
{
	private int level;
	private boolean endLevel;
	private double actualExp;
	private double endExp;
	private double perLevelExpMulti;
	private double perLevelMoneyMulti;
	
	public Level(int level, boolean endLevel,
			double actualExp, double endExp,
			double perLevelExpMulti, double perLevelMoneyMulti)
	{
		setLevel(level);
		setEndLevel(endLevel);
		setActualExp(actualExp);
		setEndExp(endExp);
		setPerLevelExpMulti(perLevelExpMulti);
		setPerLevelMoneyMulti(perLevelMoneyMulti);
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public double getActualExp()
	{
		return actualExp;
	}

	public void setActualExp(double actualExp)
	{
		this.actualExp = actualExp;
	}

	public double getEndExp()
	{
		return endExp;
	}

	public void setEndExp(double endExp)
	{
		this.endExp = endExp;
	}

	public boolean isEndLevel()
	{
		return endLevel;
	}

	public void setEndLevel(boolean endLevel)
	{
		this.endLevel = endLevel;
	}

	public double getPerLevelExpMulti()
	{
		return perLevelExpMulti;
	}

	public void setPerLevelExpMulti(double perLevelExpMulti)
	{
		this.perLevelExpMulti = perLevelExpMulti;
	}

	public double getPerLevelMoneyMulti()
	{
		return perLevelMoneyMulti;
	}

	public void setPerLevelMoneyMulti(double perLevelMoneyMulti)
	{
		this.perLevelMoneyMulti = perLevelMoneyMulti;
	}

}
