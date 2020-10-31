package main.java.de.avankziar.professionpro.objectsprofession;

public class ExchangeObject 
{
	private double exp;
	private double money;
	
	public ExchangeObject(double exp, double money)
	{
		setExp(exp);
		setMoney(money);
	}

	public double getExp() {
		return exp;
	}

	public void setExp(double exp) {
		this.exp = exp;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}
}
