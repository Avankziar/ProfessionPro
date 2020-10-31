package main.java.de.avankziar.professionpro.newobjects;

public class Payment
{
	//All values are default zero
	//The money basevalue to getting after a professionactivity
	private double money = 0.0;
	//The exp basevalue to getting after a professionactivity
	private double professionExp = 0.0;
	//The item basevalue to getting after a professionactivity
	//The item value is more a multiplicator which multipliated on the existing drops
	private double item = 0.0;
	//The vanilla exp
	private double vanillaExp = 0.0;
	
	public Payment(double money, double professionExp, double item, double vanillaExp)
	{
		setMoney(money);
		setProfessionExp(professionExp);
		setItem(item);
		setVanillaExp(vanillaExp);
	}

	public double getMoney()
	{
		return money;
	}

	public void setMoney(double money)
	{
		this.money = money;
	}

	public double getItem()
	{
		return item;
	}

	public void setItem(double item)
	{
		this.item = item;
	}

	public double getProfessionExp()
	{
		return professionExp;
	}

	public void setProfessionExp(double professionExp)
	{
		this.professionExp = professionExp;
	}

	public double getVanillaExp()
	{
		return vanillaExp;
	}

	public void setVanillaExp(double vanillaExp)
	{
		this.vanillaExp = vanillaExp;
	}
}
