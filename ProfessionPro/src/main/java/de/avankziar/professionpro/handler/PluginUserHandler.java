package main.java.de.avankziar.professionpro.handler;

import java.util.LinkedHashMap;
import java.util.UUID;

import main.java.de.avankziar.professionpro.enums.Activity;
import main.java.de.avankziar.professionpro.objectsprofession.ExchangeObject;
import main.java.de.avankziar.professionpro.objectsprofession.KeyObject;
import main.java.de.avankziar.professionpro.objectsuser.PluginUser;

public class PluginUserHandler
{
	private static LinkedHashMap<UUID, PluginUser> users = new LinkedHashMap<>();
	private static LinkedHashMap<UUID, LinkedHashMap<Integer, 
		LinkedHashMap<Activity, LinkedHashMap<KeyObject, ExchangeObject>>>> lastActivitys = new LinkedHashMap<>();
	
	public static LinkedHashMap<UUID, PluginUser> getUsers()
	{
		return users;
	}
	
	public static void addUser(PluginUser pluginUser)
	{
		users.put(pluginUser.getUuid(), pluginUser);
	}
	
	public static void removeUser(UUID uuid)
	{
		users.remove(uuid);
	}
	
	public static PluginUser getUser(UUID uuid)
	{
		return users.get(uuid);
	}
	
	public static PluginUser getUser(String name)
	{
		for(PluginUser user : users.values())
		{
			if(user.getName().equals(name))
			{
				return user;
			}
		}
		return null;
	}
	
	public static ExchangeObject getLastActivity(UUID uuid, int id, Activity activity, KeyObject keyObject)
	{
		return lastActivitys.get(uuid).get(id).get(activity).get(keyObject);
	}
	
	public static void addingLastActivity(UUID uuid, int id, Activity activity, KeyObject keyObject, ExchangeObject exchangeObject)
	{
		if(lastActivitys.containsKey(uuid))
		{
			LinkedHashMap<Integer, LinkedHashMap<Activity, LinkedHashMap<KeyObject, ExchangeObject>>> iake = lastActivitys.get(uuid);
			if(iake.containsKey(id))
			{
				LinkedHashMap<Activity, LinkedHashMap<KeyObject, ExchangeObject>> ake = iake.get(id);
				if(ake.containsKey(activity))
				{
					LinkedHashMap<KeyObject, ExchangeObject> ke = ake.get(activity);
					if(ke.containsKey(keyObject))
					{
						return;
					} else
					{
						ke.put(keyObject, exchangeObject);
						ake.replace(activity, ke);
						iake.replace(id, ake);
						lastActivitys.replace(uuid, iake);
						return;
					}
				} else
				{
					LinkedHashMap<KeyObject, ExchangeObject> ke = new LinkedHashMap<>();
					ke.put(keyObject, exchangeObject);
					ake.put(activity, ke);
					iake.replace(id, ake);
					lastActivitys.replace(uuid, iake);
					return;
				}
			} else
			{
				LinkedHashMap<KeyObject, ExchangeObject> ke = new LinkedHashMap<>();
				ke.put(keyObject, exchangeObject);
				LinkedHashMap<Activity, LinkedHashMap<KeyObject, ExchangeObject>> ake = new LinkedHashMap<>();
				ake.put(activity, ke);
				iake.put(id, ake);
				lastActivitys.replace(uuid, iake);
				return;
			}
		} else
		{
			LinkedHashMap<KeyObject, ExchangeObject> ke = new LinkedHashMap<>();
			ke.put(keyObject, exchangeObject);
			LinkedHashMap<Activity, LinkedHashMap<KeyObject, ExchangeObject>> ake = new LinkedHashMap<>();
			ake.put(activity, ke);
			LinkedHashMap<Integer, LinkedHashMap<Activity, LinkedHashMap<KeyObject, ExchangeObject>>> iake = new LinkedHashMap<>();
			iake.put(id, ake);
			lastActivitys.put(uuid, iake);
			return;
		}
	}
}
