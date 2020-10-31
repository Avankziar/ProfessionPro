package main.java.de.avankziar.professionpro.enums;

public enum Activity 
{
	BREACKING, //Blocks breaking
	PLACING, //Blocks placing
	FEEDING, //Entitys Feeding
	INTERACTINGENTITY, //General Entity Interact (Milking, Mooshrow shearing)
	INTERACTINGBLOCKS, //General Block Interact (Lever, Buttons, Sign etc)
	KILLING, //Killing Entity
	FISHING, //Fishing
	SHEARING, //Sheep shearing
	BREEDING, //Breed entitys (Maybe?! EntityBreedEvent = Testing)
	FIGHTING, //Fighting with Entitys (Maybe?! EntityDamageEvent = Testing)
	TRAVELING, //Traveling MoveEvent
	TAMING, //Entity Tame = TameEvent
	TRADING, //Trading with Entitys (Maybe by replenish? With a Radius for all Players?)
	WRITING, //Write a Book (PlayerEditBookEvent​)
	HARVESTING, //Harvesting a Block (After Spigot it is another Event PlayerHarvestBlockEvent)
	FERTILIZING, //BlockFertilizeEvent​
	RAIDING, //Finish a Raid
	COLORING, //Sheep Coloring (Maybe?! = SheepDyeWoolEvent​)
	SLEEPING, //TimeSkipEvent (LivingEntiy isSleeping)
	
	CRAFTING, //Workbench interacting
	COMPOSTING, //Composter, InventoryMoveItemEvent
	BURNING, //if a Item is used as Fuel in a Furnance (FurnaceBurnEvent)
	MELTING, //Normal Furnace
	SMELTING, //Blast Furnace
	COOKING, //Smoker
	MAPPING, //Cartography Table
	STONECUTTING, //Stonecutter
	GRINDING, //Grindstone
	SMITHING, //Smithing Table,
	WEAVING, //Loom
	REPAIRING, //Anvil
	BREWING, //Brewstand
	ENCHANTING, //Enchanting Table
	WASHING, //Banner to Cauldron
}
