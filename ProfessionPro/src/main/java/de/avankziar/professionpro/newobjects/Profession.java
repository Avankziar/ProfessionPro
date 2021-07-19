package main.java.de.avankziar.professionpro.newobjects;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import main.java.de.avankziar.professionpro.ProfessionPro;
import main.java.de.avankziar.professionpro.enums.Activity;
import main.java.de.avankziar.professionpro.newobjects.enums.ListType;
import main.java.de.avankziar.professionpro.newobjects.enums.PaymentOptionType;

public class Profession extends BasicProfession
{
	//Can per cmd the user access the profession (normal accepting a profession)
	private boolean directAccessible = false;
	//From the Profession all Levels and values in the level
	private LinkedHashMap<Integer, Level> levels = new LinkedHashMap<>();
	//Per Activity a Material and/or EntityType and the payment to that
	private LinkedHashMap<Activity, LinkedHashMap<TargetKey, Payment>> paymentPlan = new LinkedHashMap<>(); 
	//what can gives the profession, the user as payment, whitch he can choose from
	private PaymentOptionType paymentOptionType = PaymentOptionType.MONEY;
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
	//If true, only by whitelist can be craft if your professionlevel is equal or higher than 
	private boolean craftableTypeLinkedWithLevels = true;
	//Listed Items are using as "be craftable or not", is decieded by craftableType, sorted by levels
	//If Whitelist, your profession level must be above the integer to access the item
	//If Blacklist, your profession level must be below the integer to access the item
	private LinkedHashMap<String, CraftableItem> craftableItems = new LinkedHashMap<>();
	//All Blocks and EntityTyps whitch can interact or not.
	//If Material is AIR, than all Materials are mean.
	//If EntityType is UNKOW, than all Entitys are mean.
	private LinkedHashMap<TargetKey, Integer> possibleInteraction = new LinkedHashMap<>();
	//if listedBlocks count as whitelisted, means only listed material and entity can be interacted, or blacklist, only listed things are interactable
	private ListType interactableType = ListType.WHITELIST;
	//If true, only by whitelist can be craft if your professionlevel is equal or higher than 
	private boolean interactableTypeLinkedWithLevels = true;
	//All loaded mysql per profession function booster, as list, than it can multiple booster per type
	private ArrayList<Booster> boosters = new ArrayList<>();
	
	//All Professions linked with the referenceName
	private static LinkedHashMap<String, Profession> allProfessions = new LinkedHashMap<>();
	
	public Profession()
	{
		
	}
	
	public Profession(YamlConfiguration pfs, String referenceName)
	{
		setReferenceName(referenceName);
		if(pfs.get("DisplayName") != null)
		{
			setDisplayName(pfs.getString("DisplayName"));
		} else
		{
			setDisplayName(referenceName);
		}
		if(pfs.get("PerCommandDirectAccessible") != null)
		{
			setDirectAccessible(pfs.getBoolean("PerCommandDirectAccessible"));
		}
		int maxLevel = 5;
		String levelEquation = "Level*1000";
		ArrayList<String> levelEquationValues = new ArrayList<>();
		String moneyMultiEquation = "1+Level*0.01-0.01";
		ArrayList<String> moneyMultiValues = new ArrayList<>();
		String pexpMultiEquation = "1+Level*0.01-0.01";
		ArrayList<String> pexpMultiValues = new ArrayList<>();		
		String itemMultiEquation = "1+Level*0.01-0.01";
		ArrayList<String> itemMultiValues = new ArrayList<>();
		String expMultiEquation = "1+Level*0.01-0.01";
		ArrayList<String> expMultiValues = new ArrayList<>();
		if(pfs.get("MaxLevel") != null)
		{
			maxLevel = pfs.getInt("MaxLevel");
		}
		if(pfs.get("LevelEquation") != null)
		{
			levelEquation = pfs.getString("LevelEquation");
		}
		if(pfs.get("LevelEquationValues") != null)
		{
			levelEquationValues = (ArrayList<String>) pfs.getStringList("EquationValues");
		}
		if(pfs.get("MoneyMultiplicatorPerLevelEquation") != null)
		{
			moneyMultiEquation = pfs.getString("MoneyMultiplicatorPerLevelEquation");
		}
		if(pfs.get("MoneyMultiplicatorPerLevelValues") != null)
		{
			moneyMultiValues = (ArrayList<String>) pfs.getStringList("MoneyMultiplicatorPerLevelValues");
		}
		if(pfs.get("ProfessionMultiplicatorPerLevelEquation") != null)
		{
			pexpMultiEquation = pfs.getString("ProfessionMultiplicatorPerLevelEquation");
		}
		if(pfs.get("ProfessionMultiplicatorPerLevelValues") != null)
		{
			pexpMultiValues = (ArrayList<String>) pfs.getStringList("ProfessionMultiplicatorPerLevelValues");
		}
		if(pfs.get("ItemMultiplicatorPerLevelEquation") != null)
		{
			itemMultiEquation = pfs.getString("ItemMultiplicatorPerLevelEquation");
		}
		if(pfs.get("ItemMultiplicatorPerLevelValues") != null)
		{
			itemMultiValues = (ArrayList<String>) pfs.getStringList("ItemMultiplicatorPerLevelValues");
		}
		if(pfs.get("VanillaExpMultiplicatorPerLevelEquation") != null)
		{
			expMultiEquation = pfs.getString("VanillaExpMultiplicatorPerLevelEquation");
		}
		if(pfs.get("VanillaExpMultiplicatorPerLevelValues") != null)
		{
			expMultiValues = (ArrayList<String>) pfs.getStringList("VanillaExpMultiplicatorPerLevelValues");
		}
		LinkedHashMap<String, Double> internValues = new LinkedHashMap<>();
		internValues.put("Level", 1.0);
		//TODO Registered Player in Myqsql put in
		for(int i = 1; i <= maxLevel; i++)
		{
			internValues.replace("Level", new Double(i));
			FormulaParser expFormula = new FormulaParser(levelEquation, levelEquationValues);
			double endExp = expFormula.caculate(internValues);
			boolean endLevel = maxLevel == i;
			//PerLevelMultiplicator
			double pexpMulti = new FormulaParser(pexpMultiEquation, pexpMultiValues).caculate(internValues);
			double expMulti = new FormulaParser(expMultiEquation, expMultiValues).caculate(internValues);
			double moneyMulti = new FormulaParser(moneyMultiEquation, moneyMultiValues).caculate(internValues);
			double itemMulti = new FormulaParser(itemMultiEquation, itemMultiValues).caculate(internValues);
			Level level = new Level(i, endLevel, 0.0, endExp, moneyMulti, pexpMulti, itemMulti, expMulti);
			levels.put(i, level);
		}
		if(pfs.get("PaymentPlan") != null)
		{
			ArrayList<String> paymentPlan = (ArrayList<String>) pfs.getStringList("PaymentPlan");
			for(String value : paymentPlan)
			{
				String[] group = value.split("@");
				if(group.length < 3)
				{
					ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref% | Line:%values%"
							.replace("%value%", "Not enough Grouping by split `@` or `;`")
							.replace("%ref%", referenceName)
							.replace("%values%", value));
					continue;
				}
				Activity activity = Activity.BREACKING;
				try
				{
					Activity.valueOf(group[0]);
				} catch(IllegalArgumentException | NullPointerException e)
				{
					ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref% | Line:%values%"
							.replace("%value%", "Activity")
							.replace("%ref%", referenceName)
							.replace("%values%", value));
				}
				String[] targetKeySplit = group[1].split(";");
				Material material = Material.AIR;
				EntityType entityType = EntityType.UNKNOWN;
				try
				{
					material = Material.valueOf(targetKeySplit[0]);
					entityType = EntityType.valueOf(targetKeySplit[1]);
				} catch(IllegalArgumentException | NullPointerException e)
				{
					ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref% | Line:%values%"
							.replace("%value%", "TargetKey")
							.replace("%ref%", referenceName)
							.replace("%values%", value));
				}
				TargetKey targetKey = new TargetKey(material, entityType);
				String[] paymentSplit = group[2].split(";");
				double money = 0.0;
				double professionExp = 0.0;
				double item = 0.0;
				double vanillaExp = 0.0;
				try
				{
					money = Double.parseDouble(paymentSplit[0]);
					professionExp = Double.parseDouble(paymentSplit[1]);
					item = Double.parseDouble(paymentSplit[2]);
					vanillaExp = Double.parseDouble(paymentSplit[3]);
				} catch(IllegalArgumentException | NullPointerException e)
				{
					ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref% | Line:%values%"
							.replace("%value%", "TargetKey")
							.replace("%ref%", referenceName)
							.replace("%values%", value));
				}
				Payment payment = new Payment(money, professionExp, item, vanillaExp);
				addPaymentPlan(activity, targetKey, payment);
			}
		}
		if(pfs.get("PaymentOptionType") != null)
		{
			try
			{
				setPaymentOptionType(PaymentOptionType.valueOf(pfs.getString("PaymentOptionType")));
			} catch(IllegalArgumentException | NullPointerException e)
			{
				ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref%"
						.replace("%value%", "PaymentOptionType")
						.replace("%ref%", referenceName));
			}
		}
		if(pfs.get("Upgradeable") != null)
		{
			setUpgradeable(pfs.getBoolean("Upgradeable"));
		}
		if(pfs.get("PossibleUpgrades") != null)
		{
			ArrayList<String> upgradeValues = (ArrayList<String>) pfs.getStringList("PossibleUpgrades");
			for(String value : upgradeValues)
			{
				String[] group = value.split("@");
				if(group.length < 1)
				{
					ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref% | Line:%values% | Minimal >Profession;NeededLevel<"
							.replace("%value%", "PossibleUpgrades")
							.replace("%ref%", referenceName)
							.replace("%values%", value));
					continue;
				}
				String[] nameGroupSplit = group[0].split(";");
				String otherReferenceName = "";
				Upgrade upgrade = new Upgrade();
				try
				{
					otherReferenceName = nameGroupSplit[0];
					upgrade.setNeededProfessionReference(referenceName);
					upgrade.setNeededLevel(Integer.parseInt(nameGroupSplit[1]));
				} catch(IllegalArgumentException | NullPointerException e)
				{
					ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref% | Line:%values%"
							.replace("%value%", "PossibleUpgrades|NameOrLevel")
							.replace("%ref%", referenceName)
							.replace("%values%", value));
					continue;
				}
				
				Cost cost = new Cost();
				if(group.length >= 2)
				{
					try
					{
						cost.setMoney(Double.parseDouble(group[1]));
					} catch(IllegalArgumentException | NullPointerException e)
					{
						ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref% | Line:%values%"
								.replace("%value%", "PossibleUpgrades|Money")
								.replace("%ref%", referenceName)
								.replace("%values%", value));
					}
				}
				if(group.length >= 3)
				{
					String[] materialGroupSplit = group[2].split("|");
					for(String materialGroupSplitValue : materialGroupSplit)
					{
						String[] materialSplit = materialGroupSplitValue.split(";");
						if(!materialSplit[0].equalsIgnoreCase("null"))
						{
							try
							{
								cost.addMaterial(Material.valueOf(materialSplit[0]), Integer.parseInt(materialSplit[1]));
							} catch(IllegalArgumentException | NullPointerException e)
							{
								ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref% | Line:%values%"
										.replace("%value%", "PossibleUpgrades|Material")
										.replace("%ref%", referenceName)
										.replace("%values%", value));
							}
						}
					}
				}
				if(group.length >= 4)
				{
					String[] itemGroupSplit = group[3].split("|");
					for(String itemGroupSplitValue : itemGroupSplit)
					{
						String[] itemSplit = itemGroupSplitValue.split(";");
						if(!itemSplit[0].equalsIgnoreCase("null"))
						{
							try
							{
								ItemStack itemStack = new YamlItemStackGenerator().generate(itemSplit[0]);
								cost.addItems(itemStack, Integer.parseInt(itemSplit[1]));
							} catch(IllegalArgumentException | NullPointerException e)
							{
								ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref% | Line:%values%"
										.replace("%value%", "PossibleUpgrades|Material")
										.replace("%ref%", referenceName)
										.replace("%values%", value));
							}
						}
					}
				}
				upgrade.setCost(cost);
				addPossibleUpgrade(otherReferenceName, upgrade);
			}
		}
		if(pfs.get("Fusionable") != null)
		{
			setFusionable(pfs.getBoolean("Fusionable"));
		}
		if(pfs.get("PossibleFusions") != null)
		{
			for(String values : pfs.getStringList("PossibleFusions"))
			{
				String[] group = values.split("@");
				if(group.length <= 2)
				{
					ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref% | Line:%values% | Minimal >Profession@Money<"
							.replace("%value%", "PossibleFusion")
							.replace("%ref%", referenceName)
							.replace("%values%", values));
					continue;
				}
				String otherReferenceName = "";
				Fusion fusion = new Fusion();
				Cost cost = new Cost();
				try
				{
					otherReferenceName = group[0];
					cost.setMoney(Double.parseDouble(group[1]));
				} catch(IllegalArgumentException | NullPointerException e)
				{
					ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref% | Line:%values%"
							.replace("%value%", "PossibleFusion|NameOrMoney")
							.replace("%ref%", referenceName)
							.replace("%values%", values));
				}
				if(group.length >= 3)
				{
					String[] subgroup = group[2].split("|");
					for(String sub : subgroup)
					{
						String[] subs = sub.split(";");
						try
						{
							fusion.addNeededProfession(subs[0], Integer.parseInt(subs[1]));
						} catch(IllegalArgumentException | NullPointerException e)
						{
							ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref% | Line:%values%"
									.replace("%value%", "PossibleFusion|RequiredJobs")
									.replace("%ref%", referenceName)
									.replace("%values%", values));
						}
					}
				}
				if(group.length >= 4)
				{
					String[] subgroup = group[3].split("|");
					for(String sub : subgroup)
					{
						String[] subs = sub.split(";");
						if(!"null".equalsIgnoreCase(subs[0]))
						{
							try
							{
								cost.addMaterial(Material.valueOf(subs[0]), Integer.parseInt(subs[1]));
							} catch(IllegalArgumentException | NullPointerException e)
							{
								ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref% | Line:%values%"
										.replace("%value%", "PossibleFusion|CostsNormalItems")
										.replace("%ref%", referenceName)
										.replace("%values%", values));
							}
						}
					}
				}
				if(group.length >= 5)
				{
					String[] subgroup = group[4].split("|");
					for(String sub : subgroup)
					{
						String[] subs = sub.split(";");
						if(!"null".equalsIgnoreCase(subs[0]))
						{
							try
							{
								ItemStack itemStack = new YamlItemStackGenerator().generate(subs[0]);
								cost.addItems(itemStack, Integer.parseInt(subs[1]));
							} catch(IllegalArgumentException | NullPointerException e)
							{
								ProfessionPro.log.severe("Profession has a Error in >%value%<, in the file:%ref% | Line:%values%"
										.replace("%value%", "PossibleFusion|CostCustomItems")
										.replace("%ref%", referenceName)
										.replace("%values%", values));
							}
						}
					}
				}
				fusion.setCost(cost);
				addPossibleFusion(otherReferenceName, fusion);
			}
		}
		if(pfs.get("CraftableType") != null)
		{
			setCraftableType(ListType.valueOf(pfs.getString("CraftableType")));
		}
		if(pfs.get("CraftableTypeLinkedWithLevels") != null)
		{
			setCraftableTypeLinkedWithLevels(pfs.getBoolean("CraftableTypeLinkedWithLevels"));
		}
		if(pfs.get("CraftableItems") != null)
		{
			for(String values : pfs.getStringList("CraftableItems"))
			{
				String[] valueSplit = values.split(";");
				if(valueSplit.length != 2)
				{
					continue;
				}
				ItemStack is = new ItemStack(Material.valueOf(valueSplit[0]));
				addCraftableItem(valueSplit[0], Integer.parseInt(valueSplit[1]), is);
			}
		}
		if(pfs.get("CraftableCustomItems") != null)
		{
			for(String values : pfs.getStringList("CraftableCustomItems"))
			{
				String[] valueSplit = values.split(";");
				if(valueSplit.length != 2)
				{
					continue;
				}
				ItemStack is = new YamlItemStackGenerator().generate(valueSplit[0]);
				int level = Integer.parseInt(valueSplit[1]);
				if(is != null)
				{
					String displayName = is.getType().toString();
					if(is.getItemMeta() != null && is.getItemMeta().hasDisplayName())
					{
						displayName = is.getItemMeta().getDisplayName();
					}
					addCraftableItem(displayName, level, is);
				}
			}
		}
		if(pfs.get("PossibleInteraction") != null)
		{
			for(String values : pfs.getStringList("PossibleInteraction"))
			{
				String[] valueSplit = values.split(";");
				if(valueSplit.length == 3)
				{
					addPossibleInteraction(new TargetKey(
							Material.valueOf(valueSplit[0]), EntityType.valueOf(valueSplit[1])),
							Integer.parseInt(valueSplit[2]));
				}
			}
		}
		if(pfs.get("InteractableType") != null)
		{
			setInteractableType(ListType.valueOf(pfs.getString("WHITELIST")));
		}
		if(pfs.get("InteractableTypeLinkedWithLevels") != null)
		{
			setInteractableTypeLinkedWithLevels(pfs.getBoolean("InteractableTypeLinkedWithLevels"));
		}
		addProfession(this);
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
	
	public LinkedHashMap<TargetKey, Integer> getPossibleInteraction()
	{
		return possibleInteraction;
	}

	public void setPossibleInteraction(LinkedHashMap<TargetKey, Integer> possibleInteraction)
	{
		this.possibleInteraction = possibleInteraction;
	}
	
	public void addPossibleInteraction(TargetKey targetKey, int level)
	{
		for(TargetKey key : possibleInteraction.keySet())
		{
			if(key.getMaterial() == targetKey.getMaterial()
					&& key.getEntityType() == targetKey.getEntityType())
			{
				possibleInteraction.replace(targetKey, level);
				return;
			}
		}
		possibleInteraction.put(targetKey, level);
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
	
	public void addPossibleUpgrade(String otherReferenceName, Upgrade upgrade)
	{
		if(possibleUpgradeTarget.containsKey(otherReferenceName))
		{
			possibleUpgradeTarget.replace(otherReferenceName, upgrade);
		} else
		{
			possibleUpgradeTarget.put(otherReferenceName, upgrade);
		}
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
	
	public void addPossibleFusion(String otherReferenceName, Fusion fusion)
	{
		if(possibleFusionTarget.containsKey(otherReferenceName))
		{
			possibleFusionTarget.replace(otherReferenceName, fusion);
		} else
		{
			possibleFusionTarget.put(otherReferenceName, fusion);
		}
	}
	
	public ListType getCraftableType()
	{
		return craftableType;
	}

	public void setCraftableType(ListType craftableType)
	{
		this.craftableType = craftableType;
	}

	public boolean isCraftableTypeLinkedWithLevels()
	{
		return craftableTypeLinkedWithLevels;
	}

	public void setCraftableTypeLinkedWithLevels(boolean craftableTypeLinkedWithLevels)
	{
		this.craftableTypeLinkedWithLevels = craftableTypeLinkedWithLevels;
	}

	public ListType getInteractableType()
	{
		return interactableType;
	}

	public void setInteractableType(ListType interactableType)
	{
		this.interactableType = interactableType;
	}

	public boolean isInteractableTypeLinkedWithLevels()
	{
		return interactableTypeLinkedWithLevels;
	}

	public void setInteractableTypeLinkedWithLevels(boolean interactableTypeLinkedWithLevels)
	{
		this.interactableTypeLinkedWithLevels = interactableTypeLinkedWithLevels;
	}

	public LinkedHashMap<String, CraftableItem> getCraftableItems()
	{
		return craftableItems;
	}

	public void setCraftableItems(LinkedHashMap<String, CraftableItem> craftableItems)
	{
		this.craftableItems = craftableItems;
	}
	
	public void addCraftableItem(String displayName, int level, ItemStack itemStack)
	{
		if(craftableItems.containsKey(displayName))
		{
			if(!Cost.isSimilar(itemStack, craftableItems.get(displayName).getItemStack()))
			{
				craftableItems.replace(displayName, craftableItems.get(displayName));
			}
		} else
		{
			CraftableItem craftableItem = new CraftableItem(itemStack, level);
			craftableItems.put(displayName, craftableItem);
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
}
