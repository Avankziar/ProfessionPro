package main.java.de.avankziar.professionpro.newobjects;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import main.java.de.avankziar.professionpro.enums.Activity;
import main.java.de.avankziar.professionpro.newobjects.enums.ListType;
import main.java.de.avankziar.professionpro.newobjects.enums.PaymentOptionType;
import main.java.de.avankziar.professionpro.newobjects.enums.PaymentType;

public class Profession extends BasicProfession
{
	//Can per cmd the user access the profession (normal accepting a profession)
	private boolean directAccessible = false;
	//From the Profession all Levels and values in the level
	private LinkedHashMap<Integer, Level> levels = new LinkedHashMap<>();
	//Per Activity a Material and/or EntityType and the payment to that
	private LinkedHashMap<Activity, LinkedHashMap<TargetKey, Payment>> paymentPlan = new LinkedHashMap<>(); 
	//All Blocks and EntityTyps whitch can interact or not.
	//If Material is AIR, than all Materials are mean.
	//If EntityType is UNKOW, than all Entitys are mean.
	private LinkedHashMap<TargetKey, Boolean> possibleInteraction = new LinkedHashMap<>();
	//what can gives the profession, the user as payment, whitch he can choose from
	private PaymentOptionType paymentOptionType = PaymentOptionType.ALL;
	//Can the profession upgrading at maxlevel to a another profession
	private boolean upgradeable = false;
	//Per ReferenceName Target a Upgradeobject
	private LinkedHashMap<String, Upgrade> possibleUpgradeTarget = new LinkedHashMap<>();
	//Can the profession with other profession fusion to another profession
	private boolean fusionable = false;
	//Per ReferenceName Target a Fusionobject
	private LinkedHashMap<String, Fusion> possibleFusionTarget = new LinkedHashMap<>();
	//if listedItems count as whitelisted, means only listed items can be crafted, or blacklisted, only listed item cannot be crafted
	//@see CraftItemEvent
	private ListType craftableType = ListType.WHITELIST;
	//Listed Items are using as "be craftable or not", is decieded by craftableType, sorted by levels
	//If Whitelist, your profession level must be above the integer to access the item
	//If Blacklist, your profession level must be below the integer to access the item
	private LinkedHashMap<Integer, ArrayList<ItemStack>> listedItems = new LinkedHashMap<>();
	//All loaded mysql per profession function booster, as list, than it can multiple booster per type
	private ArrayList<Booster> boosters = new ArrayList<>();
	
	//All Professions linked with the referenceName
	private static LinkedHashMap<String, Profession> allProfessions = new LinkedHashMap<>();
	//Global Multiplicator for all Professions
	//<Perm>.<Profession>.[money/professionexp/item/vanillaexp].<Number>
	private static String professionPaymentMultiplicatorPermission;
	//Maximum Number to evaluate the number for the user, by counting down
	private static Integer internMaximumNumberForProfessionPaymentMultiplicator;
	
	public Profession()
	{
		
	}
	
	public boolean isDirectAccessible()
	{
		return directAccessible;
	}

	public void setDirectAccessible(boolean directAccessible)
	{
		this.directAccessible = directAccessible;
	}

	public LinkedHashMap<Integer, Level> getLevels()
	{
		return levels;
	}

	public void addLevels(Level level)
	{
		if(levels.containsKey(level.getLevel()))
		{
			levels.replace(level.getLevel(), level);
		} else
		{
			levels.put(level.getLevel(), level);
		}
	}

	public LinkedHashMap<Activity, LinkedHashMap<TargetKey, Payment>> getPaymentPlan()
	{
		return paymentPlan;
	}

	public void addPaymentPlan(Activity activity, TargetKey targetKey, Payment payment)
	{
		if(paymentPlan.containsKey(activity))
		{
			if(paymentPlan.get(activity).containsKey(targetKey))
			{
				paymentPlan.get(activity).replace(targetKey, payment);
			} else
			{
				paymentPlan.get(activity).put(targetKey, payment);
			}
		} else
		{
			LinkedHashMap<TargetKey, Payment> payments = new LinkedHashMap<>();
			payments.put(targetKey, payment);
			paymentPlan.put(activity, payments);
		}
	}

	public LinkedHashMap<TargetKey, Boolean> getPossibleInteraction()
	{
		return possibleInteraction;
	}

	public void setPossibleInteraction(LinkedHashMap<TargetKey, Boolean> possibleInteraction)
	{
		this.possibleInteraction = possibleInteraction;
	}
	
	public void addPossibleInteraction(TargetKey targetKey, boolean canInteract)
	{
		if(possibleInteraction.containsKey(targetKey))
		{
			possibleInteraction.replace(targetKey, canInteract);
		} else
		{
			possibleInteraction.put(targetKey, canInteract);
		}
	}

	public PaymentOptionType getPaymentOptionType()
	{
		return paymentOptionType;
	}

	public void setPaymentOptionType(PaymentOptionType paymentOptionType)
	{
		this.paymentOptionType = paymentOptionType;
	}

	public boolean isUpgradeable()
	{
		return upgradeable;
	}

	public void setUpgradeable(boolean upgradeable)
	{
		this.upgradeable = upgradeable;
	}

	public LinkedHashMap<String, Upgrade> getPossibleUpgradeTarget()
	{
		return possibleUpgradeTarget;
	}

	public void setPossibleUpgradeTarget(LinkedHashMap<String, Upgrade> possibleUpgradeTarget)
	{
		this.possibleUpgradeTarget = possibleUpgradeTarget;
	}

	public boolean isFusionable()
	{
		return fusionable;
	}

	public void setFusionable(boolean fusionable)
	{
		this.fusionable = fusionable;
	}

	public LinkedHashMap<String, Fusion> getPossibleFusionTarget()
	{
		return possibleFusionTarget;
	}

	public void setPossibleFusionTarget(LinkedHashMap<String, Fusion> possibleFusionTarget)
	{
		this.possibleFusionTarget = possibleFusionTarget;
	}
	
	public ListType getCraftableType()
	{
		return craftableType;
	}

	public void setCraftableType(ListType craftableType)
	{
		this.craftableType = craftableType;
	}

	public LinkedHashMap<Integer, ArrayList<ItemStack>> getListedItems()
	{
		return listedItems;
	}

	public void setListedItems(LinkedHashMap<Integer, ArrayList<ItemStack>> listedItems)
	{
		this.listedItems = listedItems;
	}
	
	public void addListedItem(int level, ItemStack itemStack)
	{
		if(listedItems.containsKey(level))
		{
			ArrayList<ItemStack> list = listedItems.get(level);
			ItemStack[] array = new ItemStack[list.size()];
			list.toArray(array);
			if(!Cost.isSimilar(itemStack, array))
			{
				list.add(itemStack);
				listedItems.replace(level, list);
			}
		} else
		{
			ArrayList<ItemStack> list = new ArrayList<>();
			list.add(itemStack);
			listedItems.put(level, list);
		}
	}
	
	public ArrayList<Booster> getBoosters()
	{
		return boosters;
	}

	public void setBoosters(ArrayList<Booster> boosters)
	{
		this.boosters = boosters;
	}
	
	public void addBooster(Booster booster)
	{
		this.boosters.add(booster);
	}

	public static LinkedHashMap<String, Profession> getAllProfessions()
	{
		return allProfessions;
	}

	public static void setAllProfessions(LinkedHashMap<String, Profession> allProfessions)
	{
		Profession.allProfessions = allProfessions;
	}
	
	public static void addProfession(Profession profession)
	{
		if(allProfessions.containsKey(profession.getReferenceName()))
		{
			allProfessions.replace(profession.getReferenceName(), profession);
		} else
		{
			allProfessions.put(profession.getReferenceName(), profession);
		}
	}

	public static String getProfessionPaymentMultiplicator()
	{
		return professionPaymentMultiplicatorPermission;
	}

	public static void setProfessionPaymentMultiplicator(String professionPaymentMultiplicator)
	{
		if(Profession.professionPaymentMultiplicatorPermission != null)
		{
			return;
		}
		Profession.professionPaymentMultiplicatorPermission = professionPaymentMultiplicator;
	}
	
	public static void resetProfessionPaymentMultiplicator()
	{
		Profession.professionPaymentMultiplicatorPermission = null;
	}

	public static Integer getInternMaximumNumberForProfessionPaymentMultiplicator()
	{
		return internMaximumNumberForProfessionPaymentMultiplicator;
	}

	public static void setInternMaximumNumberForProfessionPaymentMultiplicator(
			Integer internMaximumNumberForProfessionPaymentMultiplicator)
	{
		if(Profession.internMaximumNumberForProfessionPaymentMultiplicator != null)
		{
			return;
		}
		Profession.internMaximumNumberForProfessionPaymentMultiplicator = internMaximumNumberForProfessionPaymentMultiplicator;
	}
	
	public static void resetInternMaximumNumberForProfessionPaymentMultiplicator()
	{
		Profession.internMaximumNumberForProfessionPaymentMultiplicator = null;
	}
	
	public static int getPaymentMultiplicator(Player player, String referenceName, PaymentType paymentType)
	{
		for(int i = (Profession.internMaximumNumberForProfessionPaymentMultiplicator != null) 
				? Profession.internMaximumNumberForProfessionPaymentMultiplicator : 0;
				i > 0; i--)
		{
			if(player.hasPermission(Profession.professionPaymentMultiplicatorPermission+"."+referenceName+
					"."+paymentType.toString().toLowerCase()+"."+i))
			{
				return i;
			}
		}
		return 0;
	}
}
