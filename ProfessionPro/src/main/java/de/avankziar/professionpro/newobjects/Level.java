package main.java.de.avankziar.professionpro.newobjects;

public class Level
{
	private int level;
	private boolean endLevel;
	private double actualExp;
	private double endExp;
	
	private double perLevelMoneyMultiplicator;
	private double perLevelProfessionMultiplicator;
	private double perLevelItemMultiplicator;
	private double perLevelExpMultiplicator;
	
	public Level(){}
	
	public Level(int level, boolean endLevel,
			double actualExp, double endExp,
			double perLevelMoneyMultiplicator,
			double perLevelProfessionExpMultiplicator,
			double perLevelItemMultiplicator,
			double perLevelExpMultiplicator)
	{
		setLevel(level);
		setEndLevel(endLevel);
		setActualExp(actualExp);
		setEndExp(endExp);
		setPerLevelExpMultiplicator(perLevelExpMultiplicator);
		setPerLevelMoneyMultiplicator(perLevelMoneyMultiplicator);
		setPerLevelItemMultiplicator(perLevelItemMultiplicator);
		setPerLevelProfessionMultiplicator(perLevelProfessionExpMultiplicator);
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

	public double getPerLevelExpMultiplicator()
	{
		return perLevelExpMultiplicator;
	}

	public void setPerLevelExpMultiplicator(double perLevelExpMultiplicator)
	{
		this.perLevelExpMultiplicator = perLevelExpMultiplicator;
	}

	public double getPerLevelMoneyMultiplicator()
	{
		return perLevelMoneyMultiplicator;
	}

	public void setPerLevelMoneyMultiplicator(double perLevelMoneyMultiplicator)
	{
		this.perLevelMoneyMultiplicator = perLevelMoneyMultiplicator;
	}

	public double getPerLevelItemMultiplicator()
	{
		return perLevelItemMultiplicator;
	}

	public void setPerLevelItemMultiplicator(double perLevelItemMultiplicator)
	{
		this.perLevelItemMultiplicator = perLevelItemMultiplicator;
	}

	public double getPerLevelProfessionMultiplicator()
	{
		return perLevelProfessionMultiplicator;
	}

	public void setPerLevelProfessionMultiplicator(double perLevelProfessionMultiplicator)
	{
		this.perLevelProfessionMultiplicator = perLevelProfessionMultiplicator;
	}
}
