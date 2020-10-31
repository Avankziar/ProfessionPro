package main.java.de.avankziar.professionpro.objectsprofession;

import java.util.UUID;

public class UserProfession
{
	private UUID uuid; //Playeruuid
	private String name; //Playername
	private GeneralProfession profession;
	private Level userLevel;
	private double madeExp; //RAM value of made exp
	private boolean active;
	
	public UserProfession(UUID uuid, String name, GeneralProfession profession, Level userLevel, double madeExp, boolean active)
	{
		setUuid(uuid);
		setName(name);
		setProfession(profession);
		setUserLevel(userLevel);
		setMadeExp(madeExp);
		setActive(active);
	}
	
	public GeneralProfession getProfession()
	{
		return profession;
	}

	public void setProfession(GeneralProfession profession)
	{
		this.profession = profession;
	}

	public Level getUserLevel()
	{
		return userLevel;
	}

	public void setUserLevel(Level userLevel)
	{
		this.userLevel = userLevel;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public UUID getUuid()
	{
		return uuid;
	}

	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public double getMadeExp()
	{
		return madeExp;
	}

	public void setMadeExp(double madeExp)
	{
		this.madeExp = madeExp;
	}
}
