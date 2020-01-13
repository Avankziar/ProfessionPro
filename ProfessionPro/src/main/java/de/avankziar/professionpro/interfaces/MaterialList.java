package main.java.de.avankziar.professionpro.interfaces;

import org.bukkit.Material;

public class MaterialList 
{
	private Material material;
	private double exp;
	private double money;
	
	public MaterialList(Material material, double exp, double money)
	{
		setMaterial(material);
		setExp(exp);
		setMoney(money);
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
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
