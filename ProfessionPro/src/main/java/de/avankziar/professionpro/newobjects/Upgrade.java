package main.java.de.avankziar.professionpro.newobjects;

public class Upgrade
{
	private String neededProfessionReference;
	private int neededLevel;
	private Cost cost;
	
	public Upgrade(){}
	
	public Upgrade(String neededProfessionReference, int neededLevel,
			Cost cost)
	{
		setNeededProfessionReference(neededProfessionReference);
		setNeededLevel(neededLevel);
		setCost(cost);
	}
	
	public boolean hasNeededProfession(PPUser user)
	{
		if(user.getUserActiveProfessions().containsKey(neededProfessionReference))
		{
			if(neededLevel <= user.getUserActiveProfessions().get(neededProfessionReference).getActualProfessionLevel().getLevel())
			{
				return true;
			}
		}
		if(user.getUserInactiveProfessions().containsKey(neededProfessionReference))
		{
			if(neededLevel <= user.getUserInactiveProfessions().get(neededProfessionReference).getActualProfessionLevel().getLevel())
			{
				return true;
			}
		}
		return false;
	}

	public String getNeededProfessionReference()
	{
		return neededProfessionReference;
	}

	public void setNeededProfessionReference(String neededProfessionReference)
	{
		this.neededProfessionReference = neededProfessionReference;
	}

	public int getNeededLevel()
	{
		return neededLevel;
	}

	public void setNeededLevel(int neededLevel)
	{
		this.neededLevel = neededLevel;
	}

	public Cost getCost()
	{
		return cost;
	}

	public void setCost(Cost cost)
	{
		this.cost = cost;
	}
}
