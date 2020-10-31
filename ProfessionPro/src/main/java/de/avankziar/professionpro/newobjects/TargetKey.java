package main.java.de.avankziar.professionpro.newobjects;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class TargetKey
{
	private Material material;
	private EntityType entityType;
	
	public TargetKey(Material material, EntityType entityType)
	{
		setMaterial(material);
		setEntityType(entityType);
	}

	public Material getMaterial()
	{
		return material;
	}

	public void setMaterial(Material material)
	{
		this.material = material;
	}

	public EntityType getEntityType()
	{
		return entityType;
	}

	public void setEntityType(EntityType entityType)
	{
		this.entityType = entityType;
	}
}
