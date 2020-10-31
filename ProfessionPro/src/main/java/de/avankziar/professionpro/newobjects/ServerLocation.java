package main.java.de.avankziar.professionpro.newobjects;

import org.bukkit.Location;

public class ServerLocation
{
	private String server;
	private String worldName;
	private double x = 0.0;
	private double y = 0.0;
	private double z = 0.0;
	private float yaw = 0;
	private float pitch = 0;
	
	public ServerLocation(String fromMysql)
	{
		String[] s = fromMysql.split(";");
		setServer(s[0]);
		setWorldName(s[1]);
		try {
			setX(Double.parseDouble(s[2]));
			setY(Double.parseDouble(s[3]));
			setZ(Double.parseDouble(s[4]));
			setYaw(Float.parseFloat(s[5]));
			setPitch(Float.parseFloat(s[6]));
		} catch(NumberFormatException e)
		{
			setX(0);
			setY(0);
			setZ(0);
			setYaw(0);
			setPitch(0);
		}
	}
	
	public ServerLocation(String server, Location location)
	{
		setServer(server);
		setWorldName(location.getWorld().getName());
		setX(location.getX());
		setY(location.getY());
		setZ(location.getZ());
		setYaw(location.getYaw());
		setPitch(location.getPitch());
	}
	
	public ServerLocation(String server, String worldName, double x, double y, double z, float yaw, float pitch)
	{
		setServer(server);
		setWorldName(worldName);
		setX(x);
		setY(y);
		setZ(z);
		setYaw(yaw);
		setPitch(pitch);
	}
	
	public String getMysqlString()
	{
		return server+";"+worldName+";"+x+";"+y+";"+z+";"+yaw+";"+pitch;
	}
	
	public boolean equalsLocation(String serverName, Location location)
	{
		if(serverName == null || location == null)
		{
			return false;
		}
		if(server.equals(serverName) && 
				location.getX() == x && location.getY() == y && location.getZ() == z &&
				location.getYaw() == yaw && location.getPitch() == pitch)
		{
			return true;
		}
		return false;
	}
	
	public boolean equalsLocation(ServerLocation location)
	{
		if(location == null)
		{
			return false;
		}
		if(server.equals(location.getServer()) && 
				location.getX() == x && location.getY() == y && location.getZ() == z &&
				location.getYaw() == yaw && location.getPitch() == pitch)
		{
			return true;
		}
		return false;
	}
	
	public boolean equalsBlockLocation(String serverName, Location location)
	{
		if(serverName == null || location == null)
		{
			return false;
		}
		if(server.equals(serverName) && 
				location.getBlockX() == (int) x && location.getBlockY() == (int) y && location.getBlockZ() == (int) z)
		{
			return true;
		}
		return false;
	}

	public String getServer()
	{
		return server;
	}

	public void setServer(String server)
	{
		this.server = server;
	}

	public String getWorldName()
	{
		return worldName;
	}

	public void setWorldName(String worldName)
	{
		this.worldName = worldName;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getZ()
	{
		return z;
	}

	public void setZ(double z)
	{
		this.z = z;
	}

	public float getYaw()
	{
		return yaw;
	}

	public void setYaw(float yaw)
	{
		this.yaw = yaw;
	}

	public float getPitch()
	{
		return pitch;
	}

	public void setPitch(float pitch)
	{
		this.pitch = pitch;
	}

}
