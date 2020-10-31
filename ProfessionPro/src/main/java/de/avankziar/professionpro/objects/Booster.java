package main.java.de.avankziar.professionpro.objects;

import main.java.de.avankziar.professionpro.enums.BoosterType;

public class Booster
{
	private BoosterType boosterType;
	private double multiplicator;
	private long endTime;
	
	public Booster(BoosterType boosterType, double multiplicator, long endTime)
	{
		setBoosterType(boosterType);
		setMultiplicator(multiplicator);
		setEndTime(endTime);
	}

	public BoosterType getBoosterType()
	{
		return boosterType;
	}

	public void setBoosterType(BoosterType boosterType)
	{
		this.boosterType = boosterType;
	}

	public double getMultiplicator()
	{
		return multiplicator;
	}

	public void setMultiplicator(double multiplicator)
	{
		this.multiplicator = multiplicator;
	}

	public long getEndTime()
	{
		return endTime;
	}

	public void setEndTime(long endTime)
	{
		this.endTime = endTime;
	}

}
