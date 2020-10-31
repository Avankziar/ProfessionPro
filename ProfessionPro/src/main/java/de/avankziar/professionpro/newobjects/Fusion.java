package main.java.de.avankziar.professionpro.newobjects;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class Fusion
{
	private LinkedHashMap<String, Integer> neededProfession = new LinkedHashMap<>();
	private Cost cost;
	
	public Fusion(){}
	
	public Fusion(LinkedHashMap<String, Integer> neededProfessionLevel,
			Cost cost)
	{
		setNeededProfessionLevel(neededProfessionLevel);
		setCost(cost);
	}
	
	public boolean hasNeededProfession(PPUser user)
	{
		int needed = neededProfession.size();
		int actual = 0;
		for(Entry<String, Integer> sets : neededProfession.entrySet())
		{
			if(user.getUserActiveProfessions().containsKey(sets.getKey()))
			{
				if(sets.getValue() <= user.getUserActiveProfessions().get(sets.getKey()).getActualProfessionLevel().getLevel())
				{
					actual++;
					continue;
				}
			}
			if(user.getUserInactiveProfessions().containsKey(sets.getKey()))
			{
				if(sets.getValue() <= user.getUserInactiveProfessions().get(sets.getKey()).getActualProfessionLevel().getLevel())
				{
					actual++;
					continue;
				}
			}
		}
		return (actual == needed) ? true : false;
	}

	public LinkedHashMap<String, Integer> getNeededProfessionLevel()
	{
		return neededProfession;
	}

	public void setNeededProfessionLevel(LinkedHashMap<String, Integer> neededProfessionLevel)
	{
		this.neededProfession = neededProfessionLevel;
	}
	
	public void addNeededProfession(String referenceName, int level)
	{
		if(neededProfession.containsKey(referenceName))
		{
			neededProfession.replace(referenceName, level);
		} else
		{
			neededProfession.put(referenceName, level);
		}
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
