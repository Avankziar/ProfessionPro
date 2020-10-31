package main.java.de.avankziar.professionpro.newobjects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Repairable;

import main.java.de.avankziar.professionpro.ProfessionPro;

public class Cost
{
	//All "normal" Items as cost
	LinkedHashMap<Material, Integer> materials = new LinkedHashMap<>();
	//All special Items as cost
	LinkedHashMap<ItemStack, Integer> items = new LinkedHashMap<>();
	//Money cost
	private double money = 0.0;
	
	
	//All vanilla enchants as list
	public static ArrayList<Enchantment> enchantments;
	
	public Cost()
	{
		// TODO Auto-generated constructor stub
	}
	
	public boolean existCost()
	{
		return (materials.isEmpty() && items.isEmpty() && money <= 0.0) ? false : true;
	}
	
	public boolean hasCost(Player player)
	{
		if(player == null)
		{
			return false;
		}
		Inventory inv = player.getInventory();
		ItemStack[] content = inv.getContents();
		LinkedHashMap<Material, Integer> materials = this.materials;
		LinkedHashMap<ItemStack, Integer> items = this.items;
		for(int i = 0; i < content.length; i++)
		{
			if(content[i] == null)
			{
				continue;
			}
			boolean used = false;
			for(Entry<ItemStack, Integer> sets: items.entrySet())
			{
				if(sets.getValue() == 0)
				{
					continue;
				}
				if(isSimilar(content[i], sets.getKey()))
				{
					int amount = sets.getValue() - content[i].getAmount();
					items.replace(content[i], (amount < 0) ? 0 : amount);
					used = true;
				}
			}
			if(!used)
			{
				for(Entry<Material, Integer> sets : materials.entrySet())
				{
					if(sets.getValue() == 0)
					{
						continue;
					}
					if(content[i].getType() == sets.getKey())
					{
						int amount = sets.getValue() - content[i].getAmount();
						materials.replace(content[i].getType(), (amount < 0) ? 0 : amount);
					}
				}
			}
		}
		for(Entry<ItemStack, Integer> sets : items.entrySet())
		{
			if(sets.getValue() != 0)
			{
				return false;
			}
		}
		for(Entry<Material, Integer> sets : materials.entrySet())
		{
			if(sets.getValue() != 0)
			{
				return false;
			}
		}
		if(!ProfessionPro.getEco().has(player, money))
		{
			return false;
		}
		return true;
	}
	
	public boolean withDrawCost(Player player)
	{
		if(!hasCost(player))
		{
			return false;
		}
		//TODO
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public static boolean isSimilar(ItemStack item, ItemStack... filter)
	{
		for(ItemStack is : filter)
		{
			if (is == null || item == null) 
	        {
				//debug("i || o == null || i:"+(is == null)+" | o:"+(item == null));
	            continue;
	        }
	        final ItemStack i = item.clone();
	        final ItemStack f = is.clone();
	        //debug("i & f getType != |||| i:"+i.getType()+" | f:"+f.getType());
	        if(i.getType() != f.getType())
	        {
	        	//debug("i & f getType != || i:"+i.getType()+" | f:"+f.getType());
	        	continue;
	        }
	        if(i.hasItemMeta() == true && f.hasItemMeta() == true)
	        {
	        	//debug("i & f hasItemMeta == true");
	        	if(i.getItemMeta() != null && f.getItemMeta() != null)
	        	{
	        		//debug("i & f getItemMeta != null");
	        		if(i.getItemMeta() instanceof Damageable && f.getItemMeta() instanceof Damageable)
	        		{
	        			Damageable id = (Damageable) i.getItemMeta();
	        			id.setDamage(0);
	        			i.setItemMeta((ItemMeta) id);
	        			Damageable od = (Damageable) f.getItemMeta();
	        			od.setDamage(0);
	        			f.setItemMeta((ItemMeta) od);
	        		}
		        	if(i.getItemMeta() instanceof Repairable && f.getItemMeta() instanceof Repairable)
	            	{
	            		Repairable ir = (Repairable) i.getItemMeta();
	            		ir.setRepairCost(0);
	            		i.setItemMeta((ItemMeta) ir);
	            		Repairable or = (Repairable) f.getItemMeta();
	            		or.setRepairCost(0);
	            		f.setItemMeta((ItemMeta) or);
	            	}
		        	if(i.getItemMeta() instanceof EnchantmentStorageMeta && f.getItemMeta() instanceof EnchantmentStorageMeta)
		        	{
		        		EnchantmentStorageMeta iesm = (EnchantmentStorageMeta) i.getItemMeta();
		        		i.setItemMeta(orderStorageEnchantments(iesm));
		        		EnchantmentStorageMeta oesm = (EnchantmentStorageMeta) f.getItemMeta();
		        		f.setItemMeta(orderStorageEnchantments(oesm));
		        	}
		        	if(i.getItemMeta().hasEnchants() && i.getType() != Material.ENCHANTED_BOOK 
		        			&& f.getItemMeta().hasEnchants() && i.getType() != Material.ENCHANTED_BOOK)
		        	{
		        		i.setItemMeta(orderEnchantments(i.getItemMeta()));
		        		f.setItemMeta(orderEnchantments(f.getItemMeta()));
		        	}
		        	i.setAmount(1);
		        	f.setAmount(1);
		        	if(i.getItemMeta().toString().equals(f.getItemMeta().toString()))
		        	{
		        		//debug("isSimliar : long");
		        		return true;
		        	}
	        	}
	        } else
	        {
	        	i.setAmount(1);
	        	f.setAmount(1);
	        	i.setDurability((short) 0);
	        	f.setDurability((short) 0);
	        	if(i.toString().equals(f.toString()))
	        	{
	        		//debug("isSimliar : short");
	        		return true;
	        	}
	        }
		}
		//debug("!isSimilar");
		return false;
	}
	
	public static ItemMeta orderEnchantments(ItemMeta i)
	{
		ItemMeta ri = i.clone();
		for(Enchantment enchan : i.getEnchants().keySet())
		{
			ri.removeEnchant(enchan);
		}
		for(Enchantment enchan : enchantments)
		{
			if(i.hasEnchant(enchan))
			{
				ri.addEnchant(enchan, i.getEnchantLevel(enchan), true);
			}
		}
		return ri;
	}
	
	public static EnchantmentStorageMeta orderStorageEnchantments(EnchantmentStorageMeta esm)
	{
		EnchantmentStorageMeta resm = esm.clone();
		for(Enchantment enchan : esm.getStoredEnchants().keySet())
		{
			resm.removeStoredEnchant(enchan);
		}
		for(Enchantment enchan : enchantments)
		{
			if(esm.hasStoredEnchant(enchan))
			{
				resm.addStoredEnchant(enchan, esm.getStoredEnchantLevel(enchan), true);
			}
		}
		return resm;
	}
	
	public static void initEnchantments()
	{
		enchantments = new ArrayList<>();
		enchantments.add(Enchantment.ARROW_DAMAGE);
		enchantments.add(Enchantment.ARROW_FIRE);
		enchantments.add(Enchantment.ARROW_INFINITE);
		enchantments.add(Enchantment.ARROW_KNOCKBACK);
		enchantments.add(Enchantment.BINDING_CURSE);
		enchantments.add(Enchantment.CHANNELING);
		enchantments.add(Enchantment.DAMAGE_ALL);
		enchantments.add(Enchantment.DAMAGE_ARTHROPODS);
		enchantments.add(Enchantment.DAMAGE_UNDEAD);
		enchantments.add(Enchantment.DEPTH_STRIDER);
		enchantments.add(Enchantment.DIG_SPEED);
		enchantments.add(Enchantment.DURABILITY);
		enchantments.add(Enchantment.FIRE_ASPECT);
		enchantments.add(Enchantment.FROST_WALKER);
		enchantments.add(Enchantment.IMPALING);
		enchantments.add(Enchantment.KNOCKBACK);
		enchantments.add(Enchantment.LOOT_BONUS_BLOCKS);
		enchantments.add(Enchantment.LOOT_BONUS_MOBS);
		enchantments.add(Enchantment.LOYALTY);
		enchantments.add(Enchantment.LUCK);
		enchantments.add(Enchantment.LURE);
		enchantments.add(Enchantment.MENDING);
		enchantments.add(Enchantment.MULTISHOT);
		enchantments.add(Enchantment.OXYGEN);
		enchantments.add(Enchantment.PIERCING);
		enchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
		enchantments.add(Enchantment.PROTECTION_EXPLOSIONS);
		enchantments.add(Enchantment.PROTECTION_FALL);
		enchantments.add(Enchantment.PROTECTION_FIRE);
		enchantments.add(Enchantment.PROTECTION_PROJECTILE);
		enchantments.add(Enchantment.QUICK_CHARGE);
		enchantments.add(Enchantment.RIPTIDE);
		enchantments.add(Enchantment.SILK_TOUCH);
		enchantments.add(Enchantment.SOUL_SPEED);
		enchantments.add(Enchantment.SWEEPING_EDGE);
		enchantments.add(Enchantment.THORNS);
		enchantments.add(Enchantment.VANISHING_CURSE);
		enchantments.add(Enchantment.WATER_WORKER);
	}
}
