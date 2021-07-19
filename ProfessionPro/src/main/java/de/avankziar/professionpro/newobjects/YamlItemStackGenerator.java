package main.java.de.avankziar.professionpro.newobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import main.java.de.avankziar.professionpro.ProfessionPro;
import main.java.de.avankziar.professionpro.assistance.ChatApi;

public class YamlItemStackGenerator
{

	public YamlItemStackGenerator(){}
	
	
	@SuppressWarnings("deprecation")
	public ItemStack generate(String referenceName)
	{
		YamlConfiguration itm = ProfessionPro.getPlugin().getYamlHandler().getItem(referenceName);
		if(itm == null)
		{
			return null;
		}
		ItemStack itemStack = null;
		int amount = 1;
		if(itm.getString("Material") == null)
		{
			return itemStack;
		}
		Material material = Material.matchMaterial(itm.getString("Material"));
		itemStack = new ItemStack(material);
		ItemMeta itemMeta = itemStack.getItemMeta();
		String displayName = referenceName;
		if(itm.get("DisplayName") != null)
		{
			displayName = itm.getString("DisplayName");
		}
		 
		itemMeta.setDisplayName(ChatApi.tl(displayName));
		if(itm.get("isUnbreakable") != null)
		{
			itemMeta.setUnbreakable(itm.getBoolean("isUnbreakable"));
		}
		ArrayList<String> attributes = null;
		if(itm.get("Attributes") != null)
		{
			attributes = (ArrayList<String>) itm.getStringList("Attributes");
			for(int i = 0 ; i < attributes.size() ; i++)
			{
				String[] attributeName = attributes.get(i).split(";");
				Attribute attribute = Attribute.valueOf(attributeName[0]);
				double value = new Double(attributeName[1]);
				if(attribute == Attribute.GENERIC_ATTACK_SPEED && value >= 4.0)
				{
					value = 3.9;
				}
				itemMeta.addAttributeModifier(attribute, 
						new AttributeModifier(attribute.toString(), value, AttributeModifier.Operation.ADD_NUMBER));
			}
		}
		ArrayList<String> itemFlags = null;
		if(itm.get("Itemflag") != null)
		{
			itemFlags = (ArrayList<String>) itm.getStringList("Itemflag");
			for(String itemFlagString : itemFlags)
			{
				ItemFlag itemFlag = ItemFlag.valueOf(itemFlagString);
				itemMeta.addItemFlags(itemFlag);
			}
		}
		ArrayList<String> enchantments = null;
		if(itm.getStringList("Enchantments") != null)
		{
			enchantments = (ArrayList<String>) itm.getStringList("Enchantments");
			for(int i = 0 ; i < enchantments.size() ; i++)
			{
				String[] enchantmentsArray = enchantments.get(i).split(";");
				String enchantmentName = enchantmentsArray[0].toUpperCase();
				Enchantment enchantment = EnchantmentWrapper.getByName(enchantmentName);
				int level = Integer.parseInt(enchantmentsArray[1]);
				if(enchantment!=null)
				{
					itemMeta.addEnchant(enchantment, level, true);	
				}
			}
		}
		if(isPotion(material))
		{
			ArrayList<String> potionTypes = null;
			if(itm.getStringList("PotionType") != null)
			{
				potionTypes = (ArrayList<String>) itm.getStringList("PotionType");
				for(String potionString : potionTypes)
				{
					String[] potionSplit = potionString.split(";");
					String potionType = potionSplit[0].toUpperCase();
					PotionMeta potionMeta = (PotionMeta) itemMeta;
					int duration = Integer.parseInt(potionSplit[1]);
					int amplifier = Integer.parseInt(potionSplit[2]);
					potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(potionType), duration, amplifier), true);
					itemStack.setItemMeta(potionMeta);
				}
			}
		}
		ArrayList<String> lore = null;
		if(itm.get("Lore") != null)
		{
			lore = (ArrayList<String>) itm.getStringList("Lore");
			itemMeta.setLore(color(lore));
		}
		itemStack.setItemMeta(itemMeta);
		itemStack.setAmount(amount);
		return itemStack;
	}
	
	private boolean isPotion(Material mat)
	{
		if(mat == Material.TIPPED_ARROW || mat == Material.POTION || mat == Material.LINGERING_POTION || mat == Material.SPLASH_POTION)
		{
			return true;
		}
		return false;
	}
	
	private static List<String> color(List<String> lore)
	{
		ArrayList<String> list = new ArrayList<String>();
		for(String s : lore)
		{
			list.add(ChatApi.tl(s));
		}
	    return list;
	}
	
	public static double getNumberFormat(double d)//FIN
	{
		BigDecimal bd = new BigDecimal(d).setScale(1, RoundingMode.HALF_UP);
		double newd = bd.doubleValue();
		return newd;
	}
	
	public static double getNumberFormat(double d, int scale)//FIN
	{
		BigDecimal bd = new BigDecimal(d).setScale(scale, RoundingMode.HALF_UP);
		double newd = bd.doubleValue();
		return newd;
	}
}
