package main.java.de.avankziar.professionpro.newobjects;

import main.java.de.avankziar.professionpro.newobjects.enums.PaymentType;

public class Booster
{
	//What Type the Booster count to
	private PaymentType boosterType = PaymentType.MONEY;
	//Tha absolute value, is accumulated outside all multipliers.
	private double absolutValue = 0.0; 
	//The relative (%) value, is accumulated inside all multipliers.
	private double relativeValue = 1.0;
	//When the booster begins
	private long startTime = 0;
	//When the booster ends, if booster ended, next restart mysql delete
	private long endTime = 0;
	
	public Booster(){}
	
	//TODO
	public static void cleanUpMysql()
	{
		
	}

	public PaymentType getBoosterType()
	{
		return boosterType;
	}

	public void setBoosterType(PaymentType boosterType)
	{
		this.boosterType = boosterType;
	}

	public double getAbsolutValue()
	{
		return absolutValue;
	}

	public void setAbsolutValue(double absolutValue)
	{
		this.absolutValue = absolutValue;
	}

	public double getRelativeValue()
	{
		return relativeValue;
	}

	public void setRelativeValue(double relativeValue)
	{
		this.relativeValue = relativeValue;
	}

	public long getStartTime()
	{
		return startTime;
	}

	public void setStartTime(long startTime)
	{
		this.startTime = startTime;
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
