package main.java.de.avankziar.professionpro.newobjects;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CraftableItem
{
	private int level = 1;
	private ItemStack itemStack = new ItemStack(Material.COBBLESTONE);
	
	public CraftableItem(){}
	
	public CraftableItem(ItemStack itemStack, int level)
	{
		setItemStack(itemStack);
		setLevel(level);
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public ItemStack getItemStack()
	{
		return itemStack;
	}

	public void setItemStack(ItemStack itemStack)
	{
		this.itemStack = itemStack;
	}
}
