package main.java.de.avankziar.professionpro.handler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import main.java.de.avankziar.professionpro.objects.Booster;
import main.java.de.avankziar.professionpro.objectsprofession.UserProfession;
import main.java.de.avankziar.professionpro.objectsuser.PluginUser;

public class ConvertHandler
{
	public static ArrayList<PluginUser> convertListI(ArrayList<?> list)
	{
		ArrayList<PluginUser> el = new ArrayList<>();
		for(Object o : list)
		{
			if(o instanceof PluginUser)
			{
				el.add((PluginUser) o);
			} else
			{
				return null;
			}
		}
		return el;
	}
	
	public static ArrayList<UserProfession> convertListII(ArrayList<?> list)
	{
		ArrayList<UserProfession> el = new ArrayList<>();
		for(Object o : list)
		{
			if(o instanceof UserProfession)
			{
				el.add((UserProfession) o);
			} else
			{
				return null;
			}
		}
		return el;
	}
	
	public static ArrayList<Booster> convertListIII(ArrayList<?> list)
	{
		ArrayList<Booster> el = new ArrayList<>();
		for(Object o : list)
		{
			if(o instanceof Booster)
			{
				el.add((Booster) o);
			} else
			{
				return null;
			}
		}
		return el;
	}
	
	/*public static ArrayList<TrendLogger> convertListIV(ArrayList<?> list)
	{
		ArrayList<TrendLogger> el = new ArrayList<>();
		for(Object o : list)
		{
			if(o instanceof TrendLogger)
			{
				el.add((TrendLogger) o);
			} else
			{
				return null;
			}
		}
		return el;
	}
	
	public static ArrayList<StandingOrder> convertListV(ArrayList<?> list)
	{
		ArrayList<StandingOrder> el = new ArrayList<>();
		for(Object o : list)
		{
			if(o instanceof StandingOrder)
			{
				el.add((StandingOrder) o);
			} else
			{
				return null;
			}
		}
		return el;
	}
	
	public static ArrayList<DebtRepayment> convertListVI(ArrayList<?> list)
	{
		ArrayList<DebtRepayment> el = new ArrayList<>();
		for(Object o : list)
		{
			if(o instanceof DebtRepayment)
			{
				el.add((DebtRepayment) o);
			} else
			{
				return null;
			}
		}
		return el;
	}
	
	public static LocalDateTime deserialised(String datetime)
	{
		LocalDateTime dt = LocalDateTime.parse((CharSequence) datetime,
				DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
		return dt;
	}*/
	
	public static String serialised(LocalDateTime dt)
	{
		String MM = "";
		int month = 0;
		if(dt.getMonthValue()<10)
		{
			MM+=month;
		}
		MM += dt.getMonthValue();
		String dd = "";
		int day = 0;
		if(dt.getDayOfMonth()<10)
		{
			dd+=day;
		}
		dd +=dt.getDayOfMonth();
		String hh = "";
		int hour = 0;
		if(dt.getHour()<10)
		{
			hh+=hour;
		}
		hh += dt.getHour();
		String mm = "";
		int min = 0;
		if(dt.getMinute()<10)
		{
			mm+=min;
		}
		mm += dt.getMinute();
		String ss = "";
		int sec = 0;
		if(dt.getSecond()<10)
		{
			ss+=sec;
		}
		ss += dt.getSecond();
		return dd+"."+MM+"."+dt.getYear()+" "+hh+":"+mm+":"+ss;
	}
	
	public static LocalDate deserialisedDate(String date)
	{
		LocalDate d = LocalDate.parse((CharSequence) date,
				DateTimeFormatter.ofPattern("dd.MM.yyyy"));
		return d;
	}
	
	public static String serialised(LocalDate d)
	{
		String mm = "";
		int month = 0;
		if(d.getMonthValue()<10)
		{
			mm+=month;
		}
		mm += d.getMonthValue();
		String dd = "";
		int day = 0;
		if(d.getDayOfMonth()<10)
		{
			dd+=day;
		}
		dd +=d.getDayOfMonth();
		return dd+"."+mm+"."+d.getYear();
	}
	
	public static String serialised(Location location)
	{
		return location.getWorld().getName()+";"
				+location.getX()+";"+location.getY()+";"+location.getZ()+";"
				+location.getYaw()+";"+location.getPitch();
	}
	
	public static Location deserialised(String location)
	{
		String[] l = location.split(";");
		return new Location(Bukkit.getWorld(l[0]),
				Double.parseDouble(l[1]),
				Double.parseDouble(l[2]),
				Double.parseDouble(l[3]),
				Float.parseFloat(l[4]),
				Float.parseFloat(l[5]));
	}
	
	public static String serialised(ArrayList<Location> locations)
	{
		String s = "";
		for(Location location : locations)
		{
			s += location.getWorld().getName()+";"
					+location.getX()+";"+location.getY()+";"+location.getZ()+";"
					+location.getYaw()+";"+location.getPitch()+"@";
		}
		return s;
	}
	
	public static ArrayList<Location> deserialisedArray(String location)
	{
		ArrayList<Location> list = new ArrayList<>();
		String[] a = location.split("@");
		for(String l : a)
		{
			String[] lo = l.split(";");
			Location loc = new Location(Bukkit.getWorld(lo[0]),
					Double.parseDouble(lo[1]),
					Double.parseDouble(lo[2]),
					Double.parseDouble(lo[3]),
					Float.parseFloat(lo[4]),
					Float.parseFloat(lo[5]));
			list.add(loc);
		}
		return list;
	}
}
