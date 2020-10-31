package main.java.de.avankziar.professionpro.distributeListener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import main.java.de.avankziar.professionpro.events.DistributeEvent;
import main.java.de.avankziar.professionpro.objectsprofession.ExchangeObject;
import main.java.de.avankziar.professionpro.objectsprofession.GeneralProfession;
import main.java.de.avankziar.professionpro.objectsprofession.UserProfession;
import main.java.de.avankziar.professionpro.objectsuser.PluginUser;

public class DistributeListener implements Listener
{	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onDistribute(DistributeEvent event)
	{
		if(event.isCancelled())
		{
			return;
		}
		PluginUser user = event.getUser();
		GeneralProfession profession = event.getProfession();
		UserProfession userProfession = user.getProfession().get(profession.getId());
		ExchangeObject exo = event.getExchangeObject();
		final int amount = event.getAmount();
		double exp = amount
				* exo.getExp()
				* userProfession.getUserLevel().getPerLevelExpMulti(); //TODO Booser adden
		double money = amount
				* exo.getMoney()
				* userProfession.getUserLevel().getPerLevelMoneyMulti(); //TODO Booser adden
		userProfession.setMadeExp(userProfession.getMadeExp() + exp);
		user.setMadeMoney(user.getMadeMoney() + money);
	}
}
