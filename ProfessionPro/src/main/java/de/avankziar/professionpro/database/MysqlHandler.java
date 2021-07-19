package main.java.de.avankziar.professionpro.database;

import java.io.IOException;
import java.util.ArrayList;

import main.java.de.avankziar.professionpro.ProfessionPro;
import main.java.de.avankziar.professionpro.database.tables.TableI;
import main.java.de.avankziar.professionpro.database.tables.TableII;

public class MysqlHandler implements TableI, TableII//, //TableIII, //TableIV, TableV, TableVI
{
	public enum Type
	{
		PLUGINUSER, USERPROFESSION, BOOSTER; //ACTIONLOG, TRENDLOG
	}
	
	private ProfessionPro plugin;
	public String tableNameI; //Spieler
	public String tableNameII; //Jobs der Spieler
	public String tableNameIII; //Boosters
	public String tableNameIV; //ActionLog
	public String tableNameV; //Trendlog
	public String tableNameVI; //
	
	public MysqlHandler(ProfessionPro plugin) 
	{
		this.plugin = plugin;
		loadMysqlHandler();
	}
	
	public boolean loadMysqlHandler()
	{
		tableNameI = plugin.getYamlHandler().getConfig().getString("Mysql.TableNameI");
		if(tableNameI == null)
		{
			return false;
		}
		tableNameII = plugin.getYamlHandler().getConfig().getString("Mysql.TableNameII");
		if(tableNameII == null)
		{
			return false;
		}
		tableNameIII = plugin.getYamlHandler().getConfig().getString("Mysql.TableNameIII");
		if(tableNameIII == null)
		{
			return false;
		}
		/*tableNameIV = plugin.getYamlHandler().get().getString("Mysql.TableNameIV");
		if(tableNameIV == null)
		{
			return false;
		}
		tableNameV = plugin.getYamlHandler().get().getString("Mysql.TableNameV");
		if(tableNameV == null)
		{
			return false;
		}
		tableNameVI = plugin.getYamlHandler().get().getString("Mysql.TableNameVI");
		if(tableNameVI == null)
		{
			return false;
		}*/
		return true;
	}
	
	public boolean exist(Type type, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.existI(plugin, whereColumn, whereObject);
		case USERPROFESSION:
			return TableII.super.existII(plugin, whereColumn, whereObject);
		case BOOSTER:
			return TableIII.super.existIII(plugin, whereColumn, whereObject);
		/*case TREND:
			return TableIV.super.existIV(plugin, whereColumn, whereObject);
		case STANDINGORDER:
			return TableV.super.existV(plugin, whereColumn, whereObject);
		case DEBTREPAYMENT:
			return TableVI.super.existVI(plugin, whereColumn, whereObject);*/
		}
		return false;
	}
	
	public boolean create(Type type, Object object)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.createI(plugin, object);
		case USERPROFESSION:
			return TableII.super.createII(plugin, object);
		case BOOSTER:
			return TableIII.super.createIII(plugin, object);
		/*case TREND:
			return TableIV.super.createIV(plugin, object);
		case STANDINGORDER:
			return TableV.super.createV(plugin, object);
		case DEBTREPAYMENT:
			return TableVI.super.createVI(plugin, object);*/
		}
		return false;
	}
	
	public boolean updateData(Type type, Object object, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.updateDataI(plugin, object, whereColumn, whereObject);
		case USERPROFESSION:
			return TableII.super.updateDataII(plugin, object, whereColumn, whereObject);
		case BOOSTER:
			return TableIII.super.updateDataIII(plugin, object, whereColumn, whereObject);
		/*case TREND:
			return TableIV.super.updateDataIV(plugin, object, whereColumn, whereObject);
		case STANDINGORDER:
			return TableV.super.updateDataV(plugin, object, whereColumn, whereObject);
		case DEBTREPAYMENT:
			return TableVI.super.updateDataVI(plugin, object, whereColumn, whereObject);*/
		}
		return false;
	}
	
	public Object getData(Type type, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getDataI(plugin, whereColumn, whereObject);
		case USERPROFESSION:
			return TableII.super.getDataII(plugin, whereColumn, whereObject);
		case BOOSTER:
			return TableIII.super.getDataIII(plugin, whereColumn, whereObject);
		/*case TREND:
			return TableIV.super.getDataIV(plugin, whereColumn, whereObject);
		case STANDINGORDER:
			return TableV.super.getDataV(plugin, whereColumn, whereObject);
		case DEBTREPAYMENT:
			return TableVI.super.getDataVI(plugin, whereColumn, whereObject);*/
		}
		return null;
	}
	
	public boolean deleteData(Type type, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.deleteDataI(plugin, whereColumn, whereObject);
		case USERPROFESSION:
			return TableII.super.deleteDataII(plugin, whereColumn, whereObject);
		case BOOSTER:
			return TableIII.super.deleteDataIII(plugin, whereColumn, whereObject);
		/*case TREND:
			return TableIV.super.deleteDataIV(plugin, whereColumn, whereObject);
		case STANDINGORDER:
			return TableV.super.deleteDataV(plugin, whereColumn, whereObject);
		case DEBTREPAYMENT:
			return TableVI.super.deleteDataVI(plugin, whereColumn, whereObject);*/
		}
		return false;
	}
	
	public int lastID(Type type)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.lastIDI(plugin);
		case USERPROFESSION:
			return TableII.super.lastIDII(plugin);
		case BOOSTER:
			return TableIII.super.lastIDIII(plugin);
		/*case TREND:
			return TableIV.super.lastIDIV(plugin);
		case STANDINGORDER:
			return TableV.super.lastIDV(plugin);
		case DEBTREPAYMENT:
			return TableVI.super.lastIDVI(plugin);*/
		}
		return 0;
	}
	
	public int countWhereID(Type type, String whereColumn, Object... whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.countWhereIDI(plugin, whereColumn, whereObject);
		case USERPROFESSION:
			return TableII.super.countWhereIDII(plugin, whereColumn, whereObject);
		case BOOSTER:
			return TableIII.super.countWhereIDIII(plugin, whereColumn, whereObject);
		/*case TREND:
			return TableIV.super.countWhereIDIV(plugin, whereColumn, whereObject);
		case STANDINGORDER:
			return TableV.super.countWhereIDV(plugin, whereColumn, whereObject);
		case DEBTREPAYMENT:
			return TableVI.super.countWhereIDVI(plugin, whereColumn, whereObject);*/
		}
		return 0;
	}
	
	public ArrayList<?> getList(Type type, String orderByColumn, boolean desc, int start, int end, String whereColumn, Object...whereObject)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getListI(plugin, orderByColumn, start, end, whereColumn, whereObject);
		case USERPROFESSION:
			return TableII.super.getListII(plugin, orderByColumn, start, end, whereColumn, whereObject);
		case BOOSTER:
			return TableIII.super.getListIII(plugin, orderByColumn, desc, start, end, whereColumn, whereObject);
		/*case TREND:
			return TableIV.super.getListIV(plugin, orderByColumn, start, end, whereColumn, whereObject);
		case STANDINGORDER:
			return TableV.super.getListV(plugin, orderByColumn, start, end, whereColumn, whereObject);
		case DEBTREPAYMENT:
			return TableVI.super.getListVI(plugin, orderByColumn, start, end, whereColumn, whereObject);*/
		}
		return null;
	}
	
	public ArrayList<?> getTop(Type type, String orderByColumn, int start, int end)
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getTopI(plugin, orderByColumn, start, end);
		case USERPROFESSION:
			return TableII.super.getTopII(plugin, orderByColumn, start, end);
		case BOOSTER:
			return TableIII.super.getTopIII(plugin, orderByColumn, start, end);
		/*case TREND:
			return TableIV.super.getTopIV(plugin, orderByColumn, start, end);
		case STANDINGORDER:
			return TableV.super.getTopV(plugin, orderByColumn, start, end);
		case DEBTREPAYMENT:
			return TableVI.super.getTopVI(plugin, orderByColumn, start, end);*/
		}
		return null;
	}
	
	public ArrayList<?> getAllListAt(Type type, String orderByColumn,
			boolean desc, String whereColumn, Object...whereObject) throws IOException
	{
		switch(type)
		{
		case PLUGINUSER:
			return TableI.super.getAllListAtI(plugin, orderByColumn, whereColumn, whereObject);
		case USERPROFESSION:
			return TableII.super.getAllListAtII(plugin, orderByColumn, whereColumn, whereObject);
		case BOOSTER:
			return TableIII.super.getAllListAtIII(plugin, orderByColumn, whereColumn, whereObject);
		/*case TREND:
			return TableIV.super.getAllListAtIV(plugin, orderByColumn, whereColumn, whereObject);
		case STANDINGORDER:
			return TableV.super.getAllListAtV(plugin, orderByColumn, whereColumn, whereObject);
		case DEBTREPAYMENT:
			return TableVI.super.getAllListAtVI(plugin, orderByColumn, whereColumn, whereObject);*/
		}
		return null;
	}
}
