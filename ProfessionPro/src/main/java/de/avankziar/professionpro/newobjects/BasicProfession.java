package main.java.de.avankziar.professionpro.newobjects;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import main.java.de.avankziar.professionpro.newobjects.enums.ListType;
import main.java.de.avankziar.professionpro.newobjects.enums.PaymentType;

public class BasicProfession
{
	//TODO Permission Checks do with Vault!
	//Unique identifier
	private String referenceName;
	//Displayed Name
	private String displayName;
	//Permission to access the profession
	//<perm>.<referenceName>
	private static String requiredPermissionToAccess = "ppro.access";
	//Maximum active Professionamount per User/player
	//<Perm>.<Number>
	private static String maximumActiveProfessionAmountPerUserPermission = "ppro.maximumactiveprofession";
	//Maximum Number to evaluate the number for the user, by counting down
	private static Integer internMaximumNumberForMaximumActiveProfessionAmountPerUserPermission = 50;
	//Maximum inactive Professionamount per User/player
	//<Perm>.<Number>
	private static String maximumInactiveProfessionAmountPerUserPermission = "ppro.maximuminactiveprofession";
	//Maximum Number to evaluate the number for the user, by counting down
	private static Integer internMaximumNumberForMaximumInactiveProfessionAmountPerUserPermission = 50;
	//Perm for useable (Furnace, Brewstand etc)
	//<Perm>.<Material>.<Number>
	private static String maximumUseableBlocksPermission;
	//Maximum Number to evaluate the number for the useableBlocks
	private static LinkedHashMap<Material, Integer> internMaximumNumberForMaximumUseableBlocksPermission;
	//All loaded mysql per profession function booster, as list, than it can multiple booster per type
	private static ArrayList<Booster> globalBoosters = new ArrayList<>();
	//Global Multiplicator for all Professions
	//<Perm>.[money/professionexp/item/vanillaexp].<Number>
	private static String globalProfessionPaymentMultiplicatorPermission = "ppro.multiplicator.global";
	//Maximum Number to evaluate the number for the user, by counting down
	//TODO maybe not in use
	private static Double internMaximumNumberForGlobalProfessionPaymentMultiplicator = 5.0;
	//Global Multiplicator for all Professions
	//<Perm>.<Profession>.[money/professionexp/item/vanillaexp].<Number>
	private static String professionPaymentMultiplicatorPermission = "ppro.multiplicator.profession";
	//Maximum Number to evaluate the number for the user, by counting down
	//TODO maybe not in use
	private static Double internMaximumNumberForProfessionPaymentMultiplicator = 5.0;
	//Set the listtype, if the item isnt listed in the profession
	private static ListType generalCraftableType = ListType.WHITELIST;
	//Set the listtype, if the block isnt listed in the profession to interact
	private static ListType generalInteractType = ListType.WHITELIST;
	
	public BasicProfession()
	{
		
	}

	public String getReferenceName()
	{
		return referenceName;
	}

	public void setReferenceName(String referenceName)
	{
		this.referenceName = referenceName;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}
	
	public static String getRequiredPermissionToAccess()
	{
		return requiredPermissionToAccess;
	}

	public static void setRequiredPermissionToAccess(String requiredPermissionToAccess)
	{
		if(BasicProfession.requiredPermissionToAccess != null)
		{
			return;
		}
		BasicProfession.requiredPermissionToAccess = requiredPermissionToAccess;
	}
	
	public static void resetRequiredPermissionToAccess()
	{
		BasicProfession.requiredPermissionToAccess = null;
	}
	
	public static boolean canAccessToProfession(Player player, String professionReferenceName)
	{
		if(player.hasPermission(BasicProfession.requiredPermissionToAccess+"."+professionReferenceName)
				|| player.hasPermission("*")
				|| player.hasPermission(BasicProfession.requiredPermissionToAccess+".*")
				|| player.isOp())
		{
			return true;
		}
		return false;
	}

	public static String getMaximumActiveProfessionAmountPerUserPermission()
	{
		return maximumActiveProfessionAmountPerUserPermission;
	}

	public static void setMaximumActiveProfessionAmountPerUserPermission(String maximumProfessionAmountPerUserPermission)
	{
		if(BasicProfession.maximumActiveProfessionAmountPerUserPermission != null)
		{
			return;
		}
		BasicProfession.maximumActiveProfessionAmountPerUserPermission = maximumProfessionAmountPerUserPermission;
	}
	
	public static void resetMaximumActiveProfessionAmountPerUserPermission()
	{
		BasicProfession.maximumActiveProfessionAmountPerUserPermission = null;
	}
	
	public static int getInternMaximumNumberForMaximumActiveProfessionAmountPerUserPermission()
	{
		return internMaximumNumberForMaximumActiveProfessionAmountPerUserPermission;
	}

	public static void setInternMaximumNumberForMaximumAciveProfessionAmountPerUserPermission(
			int internMaximumNumberForMaximumProfessionAmountPerUserPermission)
	{
		if(BasicProfession.internMaximumNumberForMaximumActiveProfessionAmountPerUserPermission != null)
		{
			return;
		}
		BasicProfession.internMaximumNumberForMaximumActiveProfessionAmountPerUserPermission = internMaximumNumberForMaximumProfessionAmountPerUserPermission;
	}
	
	public static void resetInternMaximumNumberForMaximumActiveProfessionAmountPerUserPermission()
	{
		BasicProfession.internMaximumNumberForMaximumActiveProfessionAmountPerUserPermission = null;
	}
	
	public static int getMaximumActiveProfessionAmount(Player player)
	{
		return getIntegerPermissionCheck(
				player, 
				BasicProfession.maximumActiveProfessionAmountPerUserPermission, 
				0,
				BasicProfession.internMaximumNumberForMaximumActiveProfessionAmountPerUserPermission);
	}
	
	public static String getMaximumInactiveProfessionAmountPerUserPermission()
	{
		return maximumInactiveProfessionAmountPerUserPermission;
	}

	public static void setMaximumInactiveProfessionAmountPerUserPermission(String maximumProfessionAmountPerUserPermission)
	{
		if(BasicProfession.maximumInactiveProfessionAmountPerUserPermission != null)
		{
			return;
		}
		BasicProfession.maximumInactiveProfessionAmountPerUserPermission = maximumProfessionAmountPerUserPermission;
	}
	
	public static void resetMaximumInactiveProfessionAmountPerUserPermission()
	{
		BasicProfession.maximumInactiveProfessionAmountPerUserPermission = null;
	}
	
	public static int getInternMaximumNumberForMaximumInactiveProfessionAmountPerUserPermission()
	{
		return internMaximumNumberForMaximumInactiveProfessionAmountPerUserPermission;
	}

	public static void setInternMaximumNumberForMaximumInaciveProfessionAmountPerUserPermission(
			int internMaximumNumberForMaximumProfessionAmountPerUserPermission)
	{
		if(BasicProfession.internMaximumNumberForMaximumInactiveProfessionAmountPerUserPermission != null)
		{
			return;
		}
		BasicProfession.internMaximumNumberForMaximumInactiveProfessionAmountPerUserPermission = internMaximumNumberForMaximumProfessionAmountPerUserPermission;
	}
	
	public static void resetInternMaximumNumberForMaximumInactiveProfessionAmountPerUserPermission()
	{
		BasicProfession.internMaximumNumberForMaximumInactiveProfessionAmountPerUserPermission = null;
	}
	
	public static int getMaximumInactiveProfessionAmount(Player player)
	{
		return getIntegerPermissionCheck(
				player, 
				BasicProfession.maximumInactiveProfessionAmountPerUserPermission,
				0,
				BasicProfession.internMaximumNumberForMaximumInactiveProfessionAmountPerUserPermission);
	}
	
	public static String getGlobalProfessionPaymentMultiplicator()
	{
		return globalProfessionPaymentMultiplicatorPermission;
	}

	public static void setGlobalProfessionPaymentMultiplicator(String globalProfessionPaymentMultiplicator)
	{
		if(BasicProfession.globalProfessionPaymentMultiplicatorPermission != null)
		{
			return;
		}
		BasicProfession.globalProfessionPaymentMultiplicatorPermission = globalProfessionPaymentMultiplicator;
	}
	
	public static void resetglobalProfessionPaymentMultiplicator()
	{
		BasicProfession.globalProfessionPaymentMultiplicatorPermission = null;
	}

	public static Double getInternMaximumNumberForGlobalProfessionPaymentMultiplicator()
	{
		return internMaximumNumberForGlobalProfessionPaymentMultiplicator;
	}

	public static void setInternMaximumNumberForGlobalProfessionPaymentMultiplicator(
			Double internMaximumNumberForGlobalProfessionPaymentMultiplicator)
	{
		if(BasicProfession.internMaximumNumberForGlobalProfessionPaymentMultiplicator != null)
		{
			return;
		}
		BasicProfession.internMaximumNumberForGlobalProfessionPaymentMultiplicator = internMaximumNumberForGlobalProfessionPaymentMultiplicator;
	}
	
	public static void resetInternMaximumNumberForGlobalProfessionPaymentMultiplicator()
	{
		BasicProfession.internMaximumNumberForGlobalProfessionPaymentMultiplicator = null;
	}
	
	public static double getGlobalPaymentMultiplicator(Player player, PaymentType paymentType)
	{
		return getDoublePermissionCheck(
				player, 
				BasicProfession.globalProfessionPaymentMultiplicatorPermission,
				1.0,
				paymentType.toString().toLowerCase());
	}

	public static String getMaximumUseableBlocksPermission()
	{
		return maximumUseableBlocksPermission;
	}

	public static void setMaximumUseableBlocksPermission(String maximumUseableBlocksPermission)
	{
		if(BasicProfession.maximumUseableBlocksPermission != null)
		{
			return;
		}
		BasicProfession.maximumUseableBlocksPermission = maximumUseableBlocksPermission;
	}
	
	public static void resetMaximumUseableBlocksPermission()
	{
		BasicProfession.maximumUseableBlocksPermission = null;
	}	

	public static LinkedHashMap<Material, Integer> getInternMaximumNumberForMaximumUseableBlocksPermission()
	{
		return internMaximumNumberForMaximumUseableBlocksPermission;
	}
	
	public static void resetInternMaximumNumberForMaximumUseableBlocksPermission()
	{
		BasicProfession.internMaximumNumberForMaximumUseableBlocksPermission = new LinkedHashMap<>();
	}
	
	public static int getMaximumUseableBlocks(Player player, Material material)
	{
		switch(material)
		{
		default:
			return 0;
		case FURNACE:
		case BLAST_FURNACE:
		case SMOKER:
		case BREWING_STAND:
		case COMPOSTER:
			for(int i = (BasicProfession.internMaximumNumberForMaximumUseableBlocksPermission.containsKey(material)) 
					? BasicProfession.internMaximumNumberForMaximumUseableBlocksPermission.get(material) : 0;
					i > 0; i--)
			{
				if(player.hasPermission(BasicProfession.internMaximumNumberForMaximumUseableBlocksPermission+"."+material.toString().toLowerCase()+"."+i))
				{
					return i;
				}
			}
			return 0;
		}
	}

	public static ArrayList<Booster> getGlobalBoosters()
	{
		return globalBoosters;
	}

	public static void setGlobalBoosters(ArrayList<Booster> globalBoosters)
	{
		BasicProfession.globalBoosters = globalBoosters;
	}
	
	public static void addGlobalBooster(Booster globalBooster)
	{
		BasicProfession.globalBoosters.add(globalBooster);
	}
	
	public static String getProfessionPaymentMultiplicator()
	{
		return professionPaymentMultiplicatorPermission;
	}

	public static void setProfessionPaymentMultiplicator(String professionPaymentMultiplicator)
	{
		if(BasicProfession.professionPaymentMultiplicatorPermission != null)
		{
			return;
		}
		BasicProfession.professionPaymentMultiplicatorPermission = professionPaymentMultiplicator;
	}
	
	public static void resetProfessionPaymentMultiplicator()
	{
		BasicProfession.professionPaymentMultiplicatorPermission = null;
	}

	public static Double getInternMaximumNumberForProfessionPaymentMultiplicator()
	{
		return internMaximumNumberForProfessionPaymentMultiplicator;
	}

	public static void setInternMaximumNumberForProfessionPaymentMultiplicator(
			Double internMaximumNumberForProfessionPaymentMultiplicator)
	{
		if(BasicProfession.internMaximumNumberForProfessionPaymentMultiplicator != null)
		{
			return;
		}
		BasicProfession.internMaximumNumberForProfessionPaymentMultiplicator = internMaximumNumberForProfessionPaymentMultiplicator;
	}
	
	public static void resetInternMaximumNumberForProfessionPaymentMultiplicator()
	{
		BasicProfession.internMaximumNumberForProfessionPaymentMultiplicator = null;
	}
	
	public static double getPaymentMultiplicator(Player player, String referenceName, PaymentType paymentType)
	{
		return getDoublePermissionCheck(
				player,
				BasicProfession.professionPaymentMultiplicatorPermission,
				1.0,
				referenceName, paymentType.toString().toLowerCase());
	}

	public static ListType getGeneralCraftableType()
	{
		return generalCraftableType;
	}

	public static void setGeneralCraftableType(ListType generalCraftableType)
	{
		BasicProfession.generalCraftableType = generalCraftableType;
	}

	public static ListType getGeneralInteractType()
	{
		return generalInteractType;
	}

	public static void setGeneralInteractType(ListType generalInteractType)
	{
		BasicProfession.generalInteractType = generalInteractType;
	}
	
	public static double getDoublePermissionCheck(
			Player player, String basicPermission, double minimalReturnValue, String...values)
	{
		String perm = basicPermission;
		for(String s : values)
		{
			perm += "."+s;
		}
		if(player.hasPermission("*")
				|| player.hasPermission(perm+".*")
				|| player.isOp())
		{
			return Double.MAX_VALUE;
		}
		for(PermissionAttachmentInfo permai : player.getEffectivePermissions())
		{
			if(permai.getPermission().startsWith(perm))
			{
				if(permai.getValue())
				{
					String s = permai.getPermission().substring(perm.length()+1);
					double value = 0.0;
					try
					{
						value = Double.parseDouble(s);
						return value;
					} catch(NumberFormatException e)
					{
						return minimalReturnValue;
					}
				} else
				{
					return minimalReturnValue;
				}
			}
		}
		return minimalReturnValue;
	}
	
	public static int getIntegerPermissionCheck(
			Player player, String basicPermission, Integer maxNumber,
			int minimalReturnValue, String... values)
	{
		String perm = basicPermission;
		for(String s : values)
		{
			perm += "."+s;
		}
		if(player.hasPermission("*")
				|| player.hasPermission(perm+".*")
				|| player.isOp())
		{
			return Integer.MAX_VALUE;
		}
		for(int i = (maxNumber != null) ? maxNumber: 0;
				i > 0; i--)
		{
			if(player.hasPermission(perm+"."+i))
			{
				return i;
			}
		}
		return minimalReturnValue;
	}
}
