package main.java.de.avankziar.professionpro.newobjects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import main.java.de.avankziar.professionpro.enums.Activity;
import main.java.de.avankziar.professionpro.newobjects.enums.EnhancementType;
import main.java.de.avankziar.professionpro.newobjects.enums.ListType;

public class PPUser
{
	//PlayerUUID to access the user profession direct
	private UUID userUUID;
	//PlayerName to display it
	private String userName;
	//RAM value of made money
	private double madeMoney;
	//Measurement to clean up the mysql
	private long lastActivity;
	//Countamount of how often the player has upgrading
	private int amountOfUpgrades = 0;
	//Countamount of how often the player has fusing
	private int amountOfFusions = 0;
	//ReferenceName for the active UserProfession
	private LinkedHashMap<String, UserProfession> userActiveProfessions = new LinkedHashMap<>();
	//ReferenceName for the inactive UserProfession
	private LinkedHashMap<String, UserProfession> userInactiveProfessions = new LinkedHashMap<>();
	//All registiert location from useable Blocks
	private LinkedHashMap<Material, ArrayList<ServerLocation>> useableBlocks = new LinkedHashMap<>();
	//Set RAM craftableItem to speedup the system
	private LinkedHashMap<String, Boolean> craftableItemsBooleanRAM = new LinkedHashMap<>();
	//Set RAM interactable to speedup the system, String = "Material;EntityType"
	private LinkedHashMap<String, Boolean> interactableBooleanRAM = new LinkedHashMap<>();
	//Per Activity a Material and/or EntityType and the payment to that.
	//List everything, what the player has done in the last x-Time
	private LinkedHashMap<Activity, LinkedHashMap<TargetKey, ArrayList<Payment>>> paymentPlanRAM = new LinkedHashMap<>();
	
	//All existing player from mysql
	private static LinkedHashMap<UUID, PPUser> allUser = new LinkedHashMap<>();
	//Default after 90 days the playerdata in mysql is deleted
	private static long timeDifferenceToCleanUp = 1000*60*60*24*90;
	//Global amount permission for upgrades and fusion
	//<Perm>.<upgrade|fusion>.<Number>
	private static String userMaximumProfessionUpgradeAndFusionAmountPermission;
	//Maximum Number to evaluate the number for the user upgrade- and fusionamount, by counting down
	private static Integer internMaximumNumberForUserMaximumProfessionUpgradeAndFusionAmountPermission;
	
	public PPUser(){}
	
	public PPUser(String userUUID, String userName)
	{
		
	}
	
	public UUID getUserUUID()
	{
		return userUUID;
	}

	public void setUserUUID(UUID userUUID)
	{
		this.userUUID = userUUID;
	}
	
	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}
	
	public Player getPlayer()
	{
		return (userUUID != null) ? Bukkit.getPlayer(userUUID) : null;
	}
	
	public OfflinePlayer getOfflinePlayer()
	{
		return (userUUID != null) ? Bukkit.getOfflinePlayer(userUUID) : null;
	}

	public double getMadeMoney()
	{
		return madeMoney;
	}

	public void setMadeMoney(double madeMoney)
	{
		this.madeMoney = madeMoney;
	}
	
	public long getLastActivity()
	{
		return lastActivity;
	}

	public void setLastActivity(long lastActivity)
	{
		this.lastActivity = lastActivity;
	}

	public int getAmountOfUpgrades()
	{
		return amountOfUpgrades;
	}

	public void setAmountOfUpgrades(int amountOfUpgrades)
	{
		this.amountOfUpgrades = amountOfUpgrades;
	}

	public int getAmountOfFusions()
	{
		return amountOfFusions;
	}

	public void setAmountOfFusions(int amountOfFusions)
	{
		this.amountOfFusions = amountOfFusions;
	}

	public LinkedHashMap<String, UserProfession> getUserActiveProfessions()
	{
		return userActiveProfessions;
	}

	public void setUserActiveProfessions(LinkedHashMap<String, UserProfession> userActiveProfessions)
	{
		this.userActiveProfessions = userActiveProfessions;
	}

	public LinkedHashMap<String, UserProfession> getUserInactiveProfessions()
	{
		return userInactiveProfessions;
	}

	public void setUserInactiveProfessions(LinkedHashMap<String, UserProfession> userInactiveProfessions)
	{
		this.userInactiveProfessions = userInactiveProfessions;
	}
	
	//TODO
	public int activateProfession(Player player, String referenceName)
	{
		if(BasicProfession.getMaximumActiveProfessionAmount(player) <= userActiveProfessions.size())
		{
			//too many active Profession
			return -1;
		}
		if(userActiveProfessions.containsKey(referenceName))
		{
			//profession is already active
			return -2;
		}
		if(!userInactiveProfessions.containsKey(referenceName))
		{
			//profession didnt exist
			return -3;
		}
		userActiveProfessions.put(referenceName, userInactiveProfessions.get(referenceName));
		userInactiveProfessions.remove(referenceName);
		return 1;
	}
	
	//TODO
	public int deactivateProfession(Player player, String referenceName)
	{
		if(BasicProfession.getMaximumInactiveProfessionAmount(player) <= userInactiveProfessions.size())
		{
			//too many active Profession
			return -1;
		}
		if(userInactiveProfessions.containsKey(referenceName))
		{
			//profession already exist and is inactive
			return -2;
		}
		if(!userActiveProfessions.containsKey(referenceName))
		{
			//profession didnt exist
			return -3;
		}
		userInactiveProfessions.put(referenceName, userActiveProfessions.get(referenceName));
		userActiveProfessions.remove(referenceName);
		return 1;
	}
	
	public int abandonedProfession(Player player, String referenceName)
	{
		/*
		 * TODO check if profession is already abandoned
		 */
		if(!userActiveProfessions.containsKey(referenceName)
				&& !userInactiveProfessions.containsKey(referenceName))
		{
			//Profession is not active nor inactive
			return -2;
		}
		/*
		 * TODO set profession in the abandoned mysql
		 */
		//Profession abandoned
		return 1;
	}
	
	public int reactiveAbandonedProfession(Player player, String referenceName)
	{
		/*
		 * TODO check if profession is abandoned
		 */
		int active = BasicProfession.getMaximumActiveProfessionAmount(player);
		int inactive = BasicProfession.getMaximumInactiveProfessionAmount(player);
		if(active <= userActiveProfessions.size()
				&& inactive <= userInactiveProfessions.size())
		{
			//Amount of active or inactive profession is at the limit
			return -2;
		}
		//TODO FROM MYSQL!
		UserProfession abandonedProfession = new UserProfession();
		if(active > userActiveProfessions.size())
		{
			userActiveProfessions.put(referenceName, abandonedProfession);
			//Abandoned Profession is reactived as active Profession
			return 1;
		} else
		{
			userInactiveProfessions.put(referenceName, abandonedProfession);
			//Abandoned Profession is reactived as inactive profession
			return 2;
		}
	}
	
	public boolean addNewInactivatedProfession(UserProfession profession)
	{
		if(userInactiveProfessions.containsKey(profession.getReferenceName()))
		{
			return false;
		}
		if(userActiveProfessions.containsKey(profession.getReferenceName()))
		{
			return false;
		}
		userInactiveProfessions.put(profession.getReferenceName(), profession);
		return true;
	}
	
	//TODO
	public int upgradeProfession(Player player, String actualReferenceName, String targetReferenceName)
	{
		if(amountOfUpgrades >= getMaximalUpgradeOrFusionAmount(player, EnhancementType.UPGRADE))
		{
			//Limit of upgrades reached
			return 0;
		}
		if(!userActiveProfessions.containsKey(actualReferenceName)
				&& !userInactiveProfessions.containsKey(actualReferenceName))
		{
			//the to upgraded Profession not active or inactive
			return -1;
		}
		if(userActiveProfessions.containsKey(targetReferenceName)
				&& userInactiveProfessions.containsKey(targetReferenceName))
		{
			//the to upgraded Profession are already existing as active or inactive
			return -2;
		}
		if(!Profession.getAllProfessions().containsKey(actualReferenceName))
		{
			//The actualReferenceName not exist
			return -3;
		}
		Profession toUpgradedProfession = Profession.getAllProfessions().get(actualReferenceName);
		if(!toUpgradedProfession.getPossibleUpgradeTarget().containsKey(targetReferenceName))
		{
			//The targetReferenceName not existing as UpgradeOption in the actualReferenceName
			return -4;
		}
		if(!Profession.getAllProfessions().containsKey(targetReferenceName))
		{
			//TargetProfession dont exist
			return -5;
		}
		Upgrade targetUpgrade = toUpgradedProfession.getPossibleUpgradeTarget().get(targetReferenceName);
		if(targetUpgrade.hasNeededProfession(this))
		{
			//Has not reached the minimum Level or has not the needed profession to upgrade the profession to UpgradeTarget
			return -6;
		}
		if(!targetUpgrade.getCost().hasCost(player))
		{
			//Has not the required Costs
			return -7;
		}
		if(!targetUpgrade.getCost().withDrawCost(player))
		{
			//Has not the required Costs
			return -7;
		}
		Profession targetProfession = Profession.getAllProfessions().get(targetReferenceName);
		UserProfession newProfession = new UserProfession(
				targetReferenceName, targetProfession.getDisplayName(), targetProfession.getLevels().get(1));
		if(userActiveProfessions.containsKey(actualReferenceName))
		{
			//TODO set the old profession in a mysql save list
			userActiveProfessions.remove(actualReferenceName);
			//TODO Set the new Profession in mysql
			userActiveProfessions.put(targetReferenceName, newProfession);
			//New Professian, set as active profession
			return 1;
		} else
		{
			//TODO set the old profession in a mysql save list
			userInactiveProfessions.remove(actualReferenceName);
			//TODO Set the new Profession in mysql
			userInactiveProfessions.put(targetReferenceName, newProfession);
			//New profession, set as inactive Profession
			return 2;
		}
	}
	
	public int fusionProfessions(Player player, String actualReferenceName, String targetReferenceName)
	{
		if(amountOfFusions >= getMaximalUpgradeOrFusionAmount(player, EnhancementType.FUSION))
		{
			//Limit of fusion reached
			return 0;
		}
		if(!userActiveProfessions.containsKey(actualReferenceName)
				&& !userInactiveProfessions.containsKey(actualReferenceName))
		{
			//the to fusion Profession not active or inactive
			return -1;
		}
		if(userActiveProfessions.containsKey(targetReferenceName)
				&& userInactiveProfessions.containsKey(targetReferenceName))
		{
			//the to fusion Profession are already existing as active or inactive
			return -2;
		}
		if(!Profession.getAllProfessions().containsKey(actualReferenceName))
		{
			//The actualReferenceName not exist
			return -3;
		}
		Profession toFusionProfession = Profession.getAllProfessions().get(actualReferenceName);
		if(!toFusionProfession.getPossibleFusionTarget().containsKey(targetReferenceName))
		{
			//the actualProfession has not this targetReferenceName as FusionTarget
			return -4;
		}
		Fusion targetFusion = toFusionProfession.getPossibleFusionTarget().get(targetReferenceName);
		if(!targetFusion.hasNeededProfession(this))
		{
			//Has not sufficent amount an required Professions
			return -5;
		}
		for(Entry<String, Integer> sets : targetFusion.getNeededProfessionLevel().entrySet())
		{
			if(userActiveProfessions.containsKey(sets.getKey()))
			{
				if(sets.getValue() <= getUserActiveProfessions().get(sets.getKey()).getActualProfessionLevel().getLevel())
				{
					//TODO set the old profession in a mysql save list
					userActiveProfessions.remove(actualReferenceName);
					continue;
				}
			}
			if(userActiveProfessions.containsKey(sets.getKey()))
			{
				if(sets.getValue() <= userActiveProfessions.get(sets.getKey()).getActualProfessionLevel().getLevel())
				{
					//TODO set the old profession in a mysql save list
					userInactiveProfessions.remove(actualReferenceName);
					continue;
				}
			}
		}
		Profession targetProfession = Profession.getAllProfessions().get(targetReferenceName);
		UserProfession newProfession = new UserProfession(
				targetReferenceName, targetProfession.getDisplayName(), targetProfession.getLevels().get(1));
		if(BasicProfession.getMaximumActiveProfessionAmount(player) > userActiveProfessions.size())
		{
			//TODO Set the new Profession in mysql
			userActiveProfessions.put(targetReferenceName, newProfession);
			//New profession is added to active Group
			return 1;
		} else
		{
			//TODO Set the new Profession in mysql
			userInactiveProfessions.put(targetReferenceName, newProfession);
			//New profession is added to inactive Group
			return 2;
		}
	}

	public LinkedHashMap<Material, ArrayList<ServerLocation>> getUseableBlocks()
	{
		return useableBlocks;
	}

	public void setUseableBlocks(LinkedHashMap<Material, ArrayList<ServerLocation>> useableBlocks)
	{
		this.useableBlocks = useableBlocks;
	}
	
	public boolean isItemCraftable(ItemStack itemStack)
	{
		if(!craftableItemsBooleanRAM.isEmpty())
		{
			Boolean boo = craftableItemsBooleanRAM.get(
					(itemStack.getItemMeta() != null && itemStack.getItemMeta().hasDisplayName()) ? itemStack.getItemMeta().getDisplayName() : itemStack.getType().toString());
			if(boo != null)
			{
				return boo;
			}
		}
		for(String referenceName : userActiveProfessions.keySet())
		{
			if(!Profession.getAllProfessions().containsKey(referenceName))
			{
				continue;
			}
			Profession profession = Profession.getAllProfessions().get(referenceName);
			int actualLevel = userActiveProfessions.get(referenceName).getActualProfessionLevel().getLevel(); 
			
			CraftableItem craftableItem = profession.getCraftableItems().get(
					(itemStack.getItemMeta() != null && itemStack.getItemMeta().hasDisplayName()) ? itemStack.getItemMeta().getDisplayName() : itemStack.getType().toString());
			if(craftableItem != null)
			{
				if(Cost.isSimilar(itemStack, craftableItem.getItemStack()))
				{
					if(profession.getCraftableType() == ListType.WHITELIST)
					{
						if(profession.isCraftableTypeLinkedWithLevels())
						{
							if(actualLevel >= craftableItem.getLevel())
							{
								settingCraftableItemsRAM(itemStack, true);
								return true;
							} else
							{
								settingCraftableItemsRAM(itemStack, false);
								return false;
							}
						} else
						{
							settingCraftableItemsRAM(itemStack, true);
							return true;
						}
					} else
					{
						if(profession.isCraftableTypeLinkedWithLevels())
						{
							if(actualLevel < craftableItem.getLevel())
							{
								settingCraftableItemsRAM(itemStack, true);
								return true;
							} else
							{
								settingCraftableItemsRAM(itemStack, false);
								return false;
							}
						} else
						{
							settingCraftableItemsRAM(itemStack, false);
							return false;
						}
					}
				}
			}
		}
		if(Profession.getGeneralCraftableType() == ListType.WHITELIST)
		{
			return false;
		} else
		{
			return true;
		}
	}
	
	private void settingCraftableItemsRAM(ItemStack itemStack, boolean boo)
	{
		if(!craftableItemsBooleanRAM.containsKey(
		(itemStack.getItemMeta() != null && itemStack.getItemMeta().hasDisplayName())
		? itemStack.getItemMeta().getDisplayName() : itemStack.getType().toString()))
		{
			craftableItemsBooleanRAM.put(
			(itemStack.getItemMeta() != null && itemStack.getItemMeta().hasDisplayName())
			? itemStack.getItemMeta().getDisplayName() : itemStack.getType().toString(), boo);
		}
	}
	
	public boolean isInteractable(Material material, EntityType entityType)
	{
		if(!interactableBooleanRAM.isEmpty())
		{
			Boolean boo = interactableBooleanRAM.get(material.toString()+";"+entityType.toString());
			if(boo != null)
			{
				return boo;
			}
		}
		for(String referenceName : userActiveProfessions.keySet())
		{
			if(!Profession.getAllProfessions().containsKey(referenceName))
			{
				continue;
			}
			Profession profession = Profession.getAllProfessions().get(referenceName);
			int actualLevel = userActiveProfessions.get(referenceName).getActualProfessionLevel().getLevel(); 
			for(Entry<TargetKey, Integer> sets : profession.getPossibleInteraction().entrySet())
			{
				if(sets.getKey().getMaterial() == material
						&& sets.getKey().getEntityType() == entityType)
				{
					if(profession.getCraftableType() == ListType.WHITELIST)
					{
						if(profession.isInteractableTypeLinkedWithLevels())
						{
							if(actualLevel >= sets.getValue())
							{
								settingInteractableRAM(material, entityType, true);
								return true;
							} else
							{
								settingInteractableRAM(material, entityType, false);
								return false;
							}
						} else
						{
							settingInteractableRAM(material, entityType, true);
							return true;
						}
					} else
					{
						if(profession.isInteractableTypeLinkedWithLevels())
						{
							if(actualLevel < sets.getValue())
							{
								settingInteractableRAM(material, entityType, true);
								return true;
							} else
							{
								settingInteractableRAM(material, entityType, false);
								return false;
							}
						} else
						{
							settingInteractableRAM(material, entityType, false);
							return false;
						}
					}
				}
			}
		}
		if(Profession.getGeneralInteractType() == ListType.WHITELIST)
		{
			return false;
		} else
		{
			return true;
		}
	}
	
	private void settingInteractableRAM(Material material, EntityType entityType, boolean boo)
	{
		if(!interactableBooleanRAM.containsKey(material.toString()+";"+entityType.toString()))
		{
			interactableBooleanRAM.put(material.toString()+";"+entityType.toString(), boo);
		}
	}
	
	//TODO
	public int addUseableBlocksForPlayer(Player player, Material material, ServerLocation location)
	{
		if(BasicProfession.getMaximumUseableBlocks(player, material) <= useableBlocks.get(material).size())
		{
			//Too many already registert useableBlocks of that material
			return -1;
		}
		for(PPUser otherUser : allUser.values())
		{
			if(!otherUser.getUseableBlocks().containsKey(material))
			{
				continue;
			}
			ArrayList<ServerLocation> otherBlocks = otherUser.getUseableBlocks().get(material);
			for(ServerLocation sl : otherBlocks)
			{
				if(location.equalsLocation(sl))
				{
					//Otherplayer already register this block
					return -2;
				}
			}
		}
		ArrayList<ServerLocation> materialList = new ArrayList<>();
		if(useableBlocks.containsKey(material))
		{
			materialList = useableBlocks.get(material);
			materialList.add(location);
			useableBlocks.replace(material, materialList);
		} else
		{
			materialList.add(location);
			useableBlocks.put(material, materialList);
		}
		//Block is registered
		return 1;
	}

	public LinkedHashMap<String, Boolean> getCraftableItemsBooleanRAM()
	{
		return craftableItemsBooleanRAM;
	}
	
	public LinkedHashMap<String, Boolean> getInteractableBooleanRAM()
	{
		return interactableBooleanRAM;
	}
	
	
	//TODO The real thing
	public void activeDoing(Activity activity, Material material, EntityType entityType)
	{
		if(!paymentPlanRAM.isEmpty())
		{
			LinkedHashMap<TargetKey,ArrayList<Payment>> paymentPlans = paymentPlanRAM.get(activity);
			if(paymentPlans != null)
			{
				ArrayList<Payment> payments = paymentPlans.get(new TargetKey(material, entityType));
				if(payments != null)
				{
					//TODO hier die dinge money, exp etc. dem spieler geben
					return;
				}
			}
		}
		for(UserProfession userProfession : userActiveProfessions.values())
		{
			Profession profession = Profession.getAllProfessions().get(userProfession.getReferenceName());
			if(profession == null)
			{
				continue;
			}
			Payment payment = profession.getPaymentPlan().get(activity).get(new TargetKey(material, entityType));
			if(payment != null)
			{
				if(paymentPlanRAM.containsKey(activity))
				{
					LinkedHashMap<TargetKey, ArrayList<Payment>> paymentPlans = paymentPlanRAM.get(activity);
					if(paymentPlans.containsKey(new TargetKey(material, entityType)))
					{
						//TODO
					} else
					{
						ArrayList<Payment> list = new ArrayList<>();
						list.add(payment);
						paymentPlans.put(new TargetKey(material, entityType), list);
						paymentPlanRAM.replace(activity, paymentPlans);
					}
				} else
				{
					LinkedHashMap<TargetKey, Payment> pays = new LinkedHashMap<>();
					pays.put(new TargetKey(material, entityType), payment);
				}
			}
		}
	}

	//Call everytime userprofession is level up, to reset RAM values, (from time to time the whitelist values change)
	public void levelUp()
	{
		craftableItemsBooleanRAM = new LinkedHashMap<>();
		interactableBooleanRAM = new LinkedHashMap<>();
		paymentPlanRAM = new LinkedHashMap<>();
	}

	public void addUser()
	{
		if(allUser.containsKey(this.userUUID))
		{
			allUser.replace(this.userUUID, this);
		} else
		{
			allUser.put(this.userUUID, this);
		}
	}

	public static void setAllUser(LinkedHashMap<UUID, PPUser> allUser)
	{
		PPUser.allUser = allUser;
	}
	
	public static PPUser getUser(UUID userUUID)
	{
		return (allUser.get(userUUID) == null) ? null : allUser.get(userUUID);
	}

	public static long getTimeDifferenceToCleanUp()
	{
		return timeDifferenceToCleanUp;
	}

	public static void setTimeDifferenceToCleanUp(long timeDifferenceToCleanUp)
	{
		PPUser.timeDifferenceToCleanUp = timeDifferenceToCleanUp;
	}
	
	public static String getGlobalProfessionPaymentMultiplicator()
	{
		return userMaximumProfessionUpgradeAndFusionAmountPermission;
	}

	public static void setGlobalProfessionPaymentMultiplicator(String globalProfessionPaymentMultiplicator)
	{
		if(PPUser.userMaximumProfessionUpgradeAndFusionAmountPermission != null)
		{
			return;
		}
		PPUser.userMaximumProfessionUpgradeAndFusionAmountPermission = globalProfessionPaymentMultiplicator;
	}
	
	public static void resetglobalProfessionPaymentMultiplicator()
	{
		PPUser.userMaximumProfessionUpgradeAndFusionAmountPermission = null;
	}

	public static Integer getinternMaximumNumberForUserMaximumProfessionUpgradeAndFusionAmountPermission()
	{
		return internMaximumNumberForUserMaximumProfessionUpgradeAndFusionAmountPermission;
	}

	public static void setinternMaximumNumberForUserMaximumProfessionUpgradeAndFusionAmountPermission(
			Integer internMaximumNumberForUserMaximumProfessionUpgradeAndFusionAmountPermission)
	{
		if(PPUser.internMaximumNumberForUserMaximumProfessionUpgradeAndFusionAmountPermission != null)
		{
			return;
		}
		PPUser.internMaximumNumberForUserMaximumProfessionUpgradeAndFusionAmountPermission = internMaximumNumberForUserMaximumProfessionUpgradeAndFusionAmountPermission;
	}
	
	public static void resetinternMaximumNumberForUserMaximumProfessionUpgradeAndFusionAmountPermission()
	{
		PPUser.internMaximumNumberForUserMaximumProfessionUpgradeAndFusionAmountPermission = null;
	}
	
	public static int getMaximalUpgradeOrFusionAmount(Player player, EnhancementType enhancementType)
	{
		for(int i = (PPUser.internMaximumNumberForUserMaximumProfessionUpgradeAndFusionAmountPermission != null) 
				? PPUser.internMaximumNumberForUserMaximumProfessionUpgradeAndFusionAmountPermission : 0;
				i > 0; i--)
		{
			if(player.hasPermission(PPUser.userMaximumProfessionUpgradeAndFusionAmountPermission
					+"."+enhancementType.toString().toLowerCase()+"."+i))
			{
				return i;
			}
		}
		return 0;
	}
}
