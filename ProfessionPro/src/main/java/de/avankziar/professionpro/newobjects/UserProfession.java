package main.java.de.avankziar.professionpro.newobjects;

import main.java.de.avankziar.professionpro.newobjects.enums.PaymentOptionType;

public class UserProfession extends BasicProfession
{
	//Mysql unique id
	private int id;
	//What has the user choose as payment in this profession
	private PaymentOptionType chosenPaymentOptionType = PaymentOptionType.NONE;
	//The actual Professionlevel
	private Level actualProfessionLevel;
	//RAM value of maded ProfessionExp
	private double madeExp;
	
	
	public UserProfession(){}
	
	public UserProfession(String referenceName, String displayName, Level level)
	{
		setReferenceName(referenceName);
		setDisplayName(displayName);
		setActualProfessionLevel(level);
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public PaymentOptionType getChosenPaymentOptionType()
	{
		return chosenPaymentOptionType;
	}
	
	public void setChosenPaymentOptionType(PaymentOptionType chosenPaymentOptionType)
	{
		this.chosenPaymentOptionType = chosenPaymentOptionType;
	}

	public Level getActualProfessionLevel()
	{
		return actualProfessionLevel;
	}

	public void setActualProfessionLevel(Level actualProfessionLevel)
	{
		this.actualProfessionLevel = actualProfessionLevel;
	}

	public double getMadeExp()
	{
		return madeExp;
	}

	public void setMadeExp(double madeExp)
	{
		this.madeExp = madeExp;
	}

}
