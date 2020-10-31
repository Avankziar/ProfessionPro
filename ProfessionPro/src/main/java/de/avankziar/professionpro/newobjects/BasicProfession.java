package main.java.de.avankziar.professionpro.newobjects;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;

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
	private static String requiredPermissionToAccess;
	//Maximum active Professionamount per User/player
	//<Perm>.<Number>
	private static String maximumActiveProfessionAmountPerUserPermission;
	//Maximum Number to evaluate the number for the user, by counting down
	private static Integer internMaximumNumberForMaximumActiveProfessionAmountPerUserPermission;
	//Maximum inactive Professionamount per User/player
	//<Perm>.<Number>
	private static String maximumInactiveProfessionAmountPerUserPermission;
	//Maximum Number to evaluate the number for the user, by counting down
	private static Integer internMaximumNumberForMaximumInactiveProfessionAmountPerUserPermission;
	//Global Multiplicator for all Professions
	//<Perm>.[money/professionexp/item/vanillaexp].<Number>
	private static String globalProfessionPaymentMultiplicatorPermission;
	//Maximum Number to evaluate the number for the user, by counting down
	private static Integer internMaximumNumberForGlobalProfessionPaymentMultiplicator;
	//Perm for useable (Furnace, Brewstand etc)
	//<Perm>.<Material>.<Number>
	private static String maximumUseableBlocksPermission;
	//Maximum Number to evaluate the number for the useableBlocks
	private static LinkedHashMap<Material, Integer> internMaximumNumberForMaximumUseableBlocksPermission;
	//All loaded mysql per profession function booster, as list, than it can multiple booster per type
	private static ArrayList<Booster> globalBoosters = new ArrayList<>();
	
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
		if(player.hasPermission("*")
				|| player.hasPermission(BasicProfession.maximumActiveProfessionAmountPerUserPermission+".*")
				|| player.isOp())
		{
			return Integer.MAX_VALUE;
		}
		for(int i = (BasicProfession.internMaximumNumberForMaximumActiveProfessionAmountPerUserPermission != null) 
				? BasicProfession.internMaximumNumberForMaximumActiveProfessionAmountPerUserPermission : 0;
				i > 0; i--)
		{
			if(player.hasPermission(BasicProfession.maximumActiveProfessionAmountPerUserPermission+"."+i))
			{
				return i;
			}
		}
		return 0;
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
		if(player.hasPermission("*")
				|| player.hasPermission(BasicProfession.maximumInactiveProfessionAmountPerUserPermission+".*")
				|| player.isOp())
		{
			return Integer.MAX_VALUE;
		}
		for(int i = (BasicProfession.internMaximumNumberForMaximumInactiveProfessionAmountPerUserPermission != null) 
				? BasicProfession.internMaximumNumberForMaximumInactiveProfessionAmountPerUserPermission : 0;
				i > 0; i--)
		{
			if(player.hasPermission(BasicProfession.maximumInactiveProfessionAmountPerUserPermission+"."+i))
			{
				return i;
			}
		}
		return 0;
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

	public static Integer getInternMaximumNumberForGlobalProfessionPaymentMultiplicator()
	{
		return internMaximumNumberForGlobalProfessionPaymentMultiplicator;
	}

	public static void setInternMaximumNumberForGlobalProfessionPaymentMultiplicator(
			Integer internMaximumNumberForGlobalProfessionPaymentMultiplicator)
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
	
	public static int getGlobalPaymentMultiplicator(Player player, PaymentType paymentType)
	{
		for(int i = (BasicProfession.internMaximumNumberForGlobalProfessionPaymentMultiplicator != null) 
				? BasicProfession.internMaximumNumberForGlobalProfessionPaymentMultiplicator : 0;
				i > 0; i--)
		{
			if(player.hasPermission(BasicProfession.globalProfessionPaymentMultiplicatorPermission+"."+paymentType.toString().toLowerCase()+"."+i))
			{
				return i;
			}
		}
		return 0;
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
}
