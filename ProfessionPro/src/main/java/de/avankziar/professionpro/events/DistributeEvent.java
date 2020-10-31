package main.java.de.avankziar.professionpro.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import main.java.de.avankziar.professionpro.objectsprofession.ExchangeObject;
import main.java.de.avankziar.professionpro.objectsprofession.GeneralProfession;
import main.java.de.avankziar.professionpro.objectsprofession.KeyObject;
import main.java.de.avankziar.professionpro.objectsuser.PluginUser;

public class DistributeEvent extends Event
{
	private static final HandlerList HANDLERS = new HandlerList();
	
	private boolean isCancelled;
	private PluginUser user;
	private GeneralProfession profession;
	private int amount;
	private KeyObject keyObject;
	private ExchangeObject exchangeObject;
	
	public DistributeEvent(PluginUser user, GeneralProfession profession, int amount, KeyObject keyObject, ExchangeObject exchangeObject)
	{
		setUser(user);
		setProfession(profession);
		setAmount(amount);
		setKeyObject(keyObject);
		setExchangeObject(exchangeObject);
		setCancelled(false);
	}
	
	public HandlerList getHandlers() 
	{
	   return HANDLERS;
	}

	public static HandlerList getHandlerList() 
	{
	    return HANDLERS;
	}

	public boolean isCancelled()
	{
		return isCancelled;
	}

	public void setCancelled(boolean isCancelled)
	{
		this.isCancelled = isCancelled;
	}

	public PluginUser getUser()
	{
		return user;
	}

	public void setUser(PluginUser user)
	{
		this.user = user;
	}

	public GeneralProfession getProfession()
	{
		return profession;
	}

	public void setProfession(GeneralProfession profession)
	{
		this.profession = profession;
	}

	public KeyObject getKeyObject()
	{
		return keyObject;
	}

	public void setKeyObject(KeyObject keyObject)
	{
		this.keyObject = keyObject;
	}

	public ExchangeObject getExchangeObject()
	{
		return exchangeObject;
	}

	public void setExchangeObject(ExchangeObject exchangeObject)
	{
		this.exchangeObject = exchangeObject;
	}

	public int getAmount()
	{
		return amount;
	}

	public void setAmount(int amount)
	{
		this.amount = amount;
	}
}