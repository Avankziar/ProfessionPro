package main.java.de.avankziar.professionpro.objects;

import org.bukkit.Material;

public class Interaction
{
	private boolean usageStatus;
	private Material material;
	
	public Interaction(boolean useageStatus, Material material)
	{
		setUsageStatus(useageStatus);
		setMaterial(material);
	}

	public Material getMaterial()
	{
		return material;
	}

	public void setMaterial(Material material)
	{
		this.material = material;
	}

	public boolean isUsageStatus()
	{
		return usageStatus;
	}

	public void setUsageStatus(boolean usageStatus)
	{
		this.usageStatus = usageStatus;
	}

}
