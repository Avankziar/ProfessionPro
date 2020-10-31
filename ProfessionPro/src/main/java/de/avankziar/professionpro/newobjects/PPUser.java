package main.java.de.avankziar.professionpro.newobjects;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
	
	//All existing player from mysql
	private static LinkedHashMap<UUID, PPUser> allUser = new LinkedHashMap<>();
	//Default after 90 days the playerdata in mysql is deleted
	private static long timeDifferenceToCleanUp = 1000*60*60*24*90;
	//Global amount permission for upgrades and fusion
	//<Perm>.<upgrade|fusion>.<Number>
	private static String userMaximumProfessionUpgradeAndFusionAmountPermission;
	//Maximum Number to evaluate the number for the user upgrade- and fusionamount, by counting down
	private static Integer internMaximumNumberForUserMaximumProfessionUpgradeAndFusionAmountPermission;
	
	public PPUser()
	{
		// TODO Auto-generated constructor stub
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
		for(String referenceName : userActiveProfessions.keySet())
		{
			if(!Profession.getAllProfessions().containsKey(referenceName))
			{
				continue;
			}
			Profession profession = Profession.getAllProfessions().get(referenceName);
			int actualLevel = userActiveProfessions.get(referenceName).getActualProfessionLevel().getLevel(); 
			for(Entry<Integer, ArrayList<ItemStack>> sets : profession.getListedItems().entrySet())
			{
				ItemStack[] array = new ItemStack[sets.getValue().size()];
				sets.getValue().toArray(array);
				if(Cost.isSimilar(itemStack, array))
				{
					if(profession.getCraftableType() == ListType.WHITELIST)
					{
						if(sets.getKey() >= actualLevel)
						{
							return false;
						}
					} else
					{
						if(sets.getKey() < actualLevel)
						{
							return false;
						}
					}
				}
			}
		}
		return true;
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
			
		} else
		{
			materialList.add(location);
		}
		//Block is registert
		return 1;
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
