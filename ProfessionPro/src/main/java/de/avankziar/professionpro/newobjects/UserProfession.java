package main.java.de.avankziar.professionpro.newobjects;

import org.bukkit.entity.Player;

import main.java.de.avankziar.professionpro.newobjects.enums.PaymentOptionType;
import main.java.de.avankziar.professionpro.newobjects.enums.PaymentType;

public class UserProfession extends BasicProfession
{
	//Mysql unique id
	private int id;
	//What has the user choose as payment in this profession
	private PaymentOptionType chosenPaymentOptionType = PaymentOptionType.NONE;
	//The actual Professionlevel
	private Level actualProfessionLevel;
	//RAM value of maded ProfessionExp
	
	private double madeExp = 0.0;
	//RAM value of multi which is calculate every x-min.
	private double moneyMultiplicator = 1.0;
	//RAM value of all cumulative Booster absolute Values.
	private double moneyAddingValue = 0.0;
	private double professionExpMultiplicator = 1.0;
	private double professionExpAddingValue = 0.0;
	private double itemMultiplicator = 1.0;
	private double itemAddingValue = 0.0;
	private double vanillaExpMultiplicator = 1.0;
	private double vanillaExpAddingValue = 0.0;
	
	
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
	
	public double getMoneyMultiplicator()
	{
		return moneyMultiplicator;
	}

	public void setMoneyMultiplicator(double moneyMultiplicator)
	{
		this.moneyMultiplicator = moneyMultiplicator;
	}

	public double getProfessionExpMultiplicator()
	{
		return professionExpMultiplicator;
	}

	public void setProfessionExpMultiplicator(double professionExpMultiplicator)
	{
		this.professionExpMultiplicator = professionExpMultiplicator;
	}

	public double getItemMultiplicator()
	{
		return itemMultiplicator;
	}

	public void setItemMultiplicator(double itemMultiplicator)
	{
		this.itemMultiplicator = itemMultiplicator;
	}

	public double getVanillaExpMultiplicator()
	{
		return vanillaExpMultiplicator;
	}

	public void setVanillaExpMultiplicator(double vanillaExpMultiplicator)
	{
		this.vanillaExpMultiplicator = vanillaExpMultiplicator;
	}

	public double getMoneyAddingValue()
	{
		return moneyAddingValue;
	}

	public void setMoneyAddingValue(double moneyAddingValue)
	{
		this.moneyAddingValue = moneyAddingValue;
	}

	public double getProfessionExpAddingValue()
	{
		return professionExpAddingValue;
	}

	public void setProfessionExpAddingValue(double professionExpAddingValue)
	{
		this.professionExpAddingValue = professionExpAddingValue;
	}

	public double getItemAddingValue()
	{
		return itemAddingValue;
	}

	public void setItemAddingValue(double itemAddingValue)
	{
		this.itemAddingValue = itemAddingValue;
	}

	public double getVanillaExpAddingValue()
	{
		return vanillaExpAddingValue;
	}

	public void setVanillaExpAddingValue(double vanillaExpAddingValue)
	{
		this.vanillaExpAddingValue = vanillaExpAddingValue;
	}

	public void calculateProfessionMultiplicator(Player player)
	{
		double moneyMulti = 1.0 *
				BasicProfession.getGlobalPaymentMultiplicator(player, PaymentType.MONEY) *
				BasicProfession.getPaymentMultiplicator(player, getReferenceName(), PaymentType.MONEY);
		double moneyBoosterValue = 0.0;
		double profExpMulti = 1.0 *
				BasicProfession.getGlobalPaymentMultiplicator(player, PaymentType.PROFESSIONEXP) *
				BasicProfession.getPaymentMultiplicator(player, getReferenceName(), PaymentType.PROFESSIONEXP);
		double profExpBoosterValue = 0.0;
		double itemMulti = 1.0 *
				BasicProfession.getGlobalPaymentMultiplicator(player, PaymentType.ITEM) *
				BasicProfession.getPaymentMultiplicator(player, getReferenceName(), PaymentType.ITEM);
		double itemBoosterValue = 0.0;
		double vanillaExpMulti = 1.0 *
				BasicProfession.getGlobalPaymentMultiplicator(player, PaymentType.VANILLAEXP) *
				BasicProfession.getPaymentMultiplicator(player, getReferenceName(), PaymentType.VANILLAEXP);
		double vanillaExpBoosterValue = 0.0;
		if(Profession.getAllProfessions().containsKey(getReferenceName()))
		{
			for(Booster booster : Profession.getAllProfessions().get(getReferenceName()).getBoosters())
			{
				switch(booster.getBoosterType())
				{
				case MONEY:
					moneyBoosterValue += booster.getAbsolutValue();
					moneyMulti *= booster.getRelativeValue();
					break;
				case PROFESSIONEXP:
					profExpBoosterValue += booster.getAbsolutValue();
					profExpMulti *= booster.getRelativeValue();
					break;
				case ITEM:
					itemBoosterValue += booster.getAbsolutValue();
					itemMulti *= booster.getRelativeValue();
					break;
				case VANILLAEXP:
					vanillaExpBoosterValue += booster.getAbsolutValue();
					vanillaExpMulti *= booster.getRelativeValue();
					break;
				}
			}
		}
		for(Booster booster : BasicProfession.getGlobalBoosters())
		{
			switch(booster.getBoosterType())
			{
			case MONEY:
				moneyBoosterValue += booster.getAbsolutValue();
				moneyMulti *= booster.getRelativeValue();
				break;
			case PROFESSIONEXP:
				profExpBoosterValue += booster.getAbsolutValue();
				profExpMulti *= booster.getRelativeValue();
				break;
			case ITEM:
				itemBoosterValue += booster.getAbsolutValue();
				itemMulti *= booster.getRelativeValue();
				break;
			case VANILLAEXP:
				vanillaExpBoosterValue += booster.getAbsolutValue();
				vanillaExpMulti *= booster.getRelativeValue();
				break;
			}
		}
		setMoneyMultiplicator(moneyMulti);
		setMoneyAddingValue(moneyBoosterValue);
		setProfessionExpMultiplicator(profExpMulti);
		setProfessionExpAddingValue(profExpBoosterValue);
		setItemMultiplicator(itemMulti);
		setItemAddingValue(itemBoosterValue);
		setVanillaExpMultiplicator(vanillaExpMulti);
		setVanillaExpAddingValue(vanillaExpBoosterValue);
	}
}
