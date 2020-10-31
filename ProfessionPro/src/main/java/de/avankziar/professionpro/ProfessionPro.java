package main.java.de.avankziar.professionpro;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.constructor.BaseConstructor;

import main.java.de.avankziar.professionpro.assistance.BackgroundTask;
import main.java.de.avankziar.professionpro.assistance.Utility;
import main.java.de.avankziar.professionpro.commands.tree.ArgumentModule;
import main.java.de.avankziar.professionpro.commands.tree.CommandConstructor;
import main.java.de.avankziar.professionpro.database.MysqlHandler;
import main.java.de.avankziar.professionpro.database.MysqlSetup;
import main.java.de.avankziar.professionpro.database.YamlHandler;
import main.java.de.avankziar.professionpro.metrics.Metrics;
import main.java.de.avankziar.professionpro.newobjects.JarLoader;
import net.milkbowl.vault.economy.Economy;

public class ProfessionPro  extends JavaPlugin
{
	public static Logger log;
	public String pluginName = "ProfessionPro";
	private static YamlHandler yamlHandler;
	private static MysqlSetup mysqlSetup;
	private static MysqlHandler mysqlHandler;
	private static Utility utility;
	private static ProfessionPro plugin;
	private static Economy eco;
	private static BackgroundTask backgroundTask;
	
	private ArrayList<String> players = new ArrayList<>();
	
	private ArrayList<CommandConstructor> commandTree;
	private ArrayList<BaseConstructor> helpList;
	private LinkedHashMap<String, ArgumentModule> argumentMap;
	public static String baseCommandI = "eco"; //Pfad angabe + ürspungliches Commandname
	public static String baseCommandII = "money";
	public static String baseCommandIII = "bank";
	
	public static String baseCommandIName = ""; //CustomCommand name
	public static String baseCommandIIName = "";
	public static String baseCommandIIIName = "";
	
	public static String infoCommandPath = "CmdEco";
	public static String infoCommand = "/"; //InfoComamnd
	
	public void onEnable()
	{
		plugin = this;
		log = getLogger();
		
		initLibaries();
		
		commandTree = new ArrayList<>();
		helpList = new ArrayList<>();
		argumentMap = new LinkedHashMap<>();
		
		yamlHandler = new YamlHandler(this);
		utility = new Utility(this);
		if (yamlHandler.get().getBoolean("Mysql.Status", false) == true)
		{
			mysqlHandler = new MysqlHandler(this);
			mysqlSetup = new MysqlSetup(this);
		} else
		{
			log.severe("MySQL is not set in the Plugin " + pluginName + "!");
			Bukkit.getPluginManager().getPlugin(pluginName).getPluginLoader().disablePlugin(this);
			return;
		}
		backgroundTask = new BackgroundTask(this);
		setupEconomy();
		setupStrings();
		setupCommandTree();
		ListenerSetup();
		setupBstats();
	}
	
	/*
	 * TODO NOTES
	 * - If the Profession change and the config boolean is true, update the userLevel in UserProfession
	 * - If a Booster is activited, send per bungee to all Server a Message to Update. If The same Booster is
	 *   activated, so extends the time only.
	 * - A Scheduler which keeps all Money and distributes a timeperiod x with the Level and Profession value.
	 */
	
	public void onDisable()
	{
		Bukkit.getScheduler().cancelTasks(this);
		HandlerList.unregisterAll(this);
		if (yamlHandler.get().getBoolean("Mysql.Status", false) == true)
		{
			if (mysqlSetup.getConnection() != null) 
			{
				mysqlSetup.closeConnection();
			}
		}
		log.info(pluginName + " is disabled!");
	}

	public static ProfessionPro getPlugin()
	{
		return plugin;
	}
	
	public YamlHandler getYamlHandler() 
	{
		return yamlHandler;
	}
	
	public MysqlSetup getMysqlSetup() 
	{
		return mysqlSetup;
	}
	
	public MysqlHandler getMysqlHandler()
	{
		return mysqlHandler;
	}
	
	public Utility getUtility()
	{
		return utility;
	}
	
	public static BackgroundTask getBackgroundTask()
	{
		return backgroundTask;
	}
	
	private void setupStrings()
	{
		//Hier baseCommands
		baseCommandIName = plugin.getYamlHandler().getCom().getString(baseCommandI+".Name");
		baseCommandIIName = plugin.getYamlHandler().getCom().getString(baseCommandII+".Name");
		baseCommandIIIName = plugin.getYamlHandler().getCom().getString(baseCommandIII+".Name");
		
		//Zuletzt infoCommand deklarieren
		infoCommand += baseCommandIName;
	}
	
	private void setupCommandTree()
	{
		LinkedHashMap<Integer, ArrayList<String>> playerMap = new LinkedHashMap<>();
		
		ArrayList<String> playerarray = getPlayers();
		Collections.sort(playerarray);
		playerMap.put(1, playerarray);
		
		/*LinkedHashMap<Integer, ArrayList<String>> lhmmode = new LinkedHashMap<>(); 
		List<PluginUser.Mode> modes = new ArrayList<PluginUser.Mode>(EnumSet.allOf(PluginUser.Mode.class));
		ArrayList<String> modeList = new ArrayList<String>();
		for(PluginUser.Mode m : modes) {modeList.add(m.toString());}
		lhmmode.put(1, modeList);*/
		
		/*ArgumentConstructor deletelog = new ArgumentConstructor(yamlHandler, baseCommandI+"_deletelog", 0, 1, 1, null);
		ArgumentConstructor player = new ArgumentConstructor(yamlHandler, baseCommandI+"_player", 0, 1, 1, null);
		ArgumentConstructor recomment = new ArgumentConstructor(yamlHandler, baseCommandI+"_recomment", 0, 2, 999, null);
		
		CommandConstructor eco = new CommandConstructor(plugin, baseCommandI,
				deletelog, player, recomment);
		
		ArgumentConstructor debt_accept = new ArgumentConstructor(yamlHandler, baseCommandII+"_debt_accept", 1, 1, 2, null);
		ArgumentConstructor debt_amount = new ArgumentConstructor(yamlHandler, baseCommandII+"_debt_amount", 1, 4, 4, null);
		ArgumentConstructor debt_cancel = new ArgumentConstructor(yamlHandler, baseCommandII+"_debt_cancel", 1, 1, 1, null);
		ArgumentConstructor debt_create = new ArgumentConstructor(yamlHandler, baseCommandII+"_debt_create", 1, 4, 4, null);
		ArgumentConstructor debt_forgive = new ArgumentConstructor(yamlHandler, baseCommandII+"_debt_forgive", 1, 2, 3, null);
		ArgumentConstructor debt_info = new ArgumentConstructor(yamlHandler, baseCommandII+"_debt_info", 1, 2, 2, null);
		ArgumentConstructor debt_list = new ArgumentConstructor(yamlHandler, baseCommandII+"_debt_list", 1, 1, 2, null);
		ArgumentConstructor debt_pause = new ArgumentConstructor(yamlHandler, baseCommandII+"_debt_pause", 1, 2, 2, null);
		ArgumentConstructor debt_reject = new ArgumentConstructor(yamlHandler, baseCommandII+"_debt_reject", 1, 1, 1, null);
		ArgumentConstructor debt_repay = new ArgumentConstructor(yamlHandler, baseCommandII+"_debt_repay", 1, 3, 4, null);
		ArgumentConstructor debt_send = new ArgumentConstructor(yamlHandler, baseCommandII+"_debt_send", 1, 2, 2, null);
		ArgumentConstructor debt_time = new ArgumentConstructor(yamlHandler, baseCommandII+"_debt_time", 1, 4, 4, null);
		ArgumentConstructor debt = new ArgumentConstructor(yamlHandler, baseCommandII+"_debt", 0, 0, 0, null,
				debt_accept, debt_amount, debt_cancel, debt_create, debt_forgive, debt_info, debt_list, debt_pause,
				debt_reject, debt_repay, debt_send, debt_time);
		
		ArgumentConstructor debts = new ArgumentConstructor(yamlHandler, baseCommandII+"_debts", 0, 0, 2, null);
		
		ArgumentConstructor fl_between = new ArgumentConstructor(yamlHandler, baseCommandII+"_filterlog_between", 1, 3, 5, null);
		ArgumentConstructor fl_comment = new ArgumentConstructor(yamlHandler, baseCommandII+"_filterlog_comment", 1, 2, 4, null);
		ArgumentConstructor fl_commentasc = new ArgumentConstructor(yamlHandler, baseCommandII+"_filterlog_commentasc", 1, 2, 4, null);
		ArgumentConstructor fl_commentdesc = new ArgumentConstructor(yamlHandler, baseCommandII+"_filterlog_commentdesc", 1, 2, 4, null);
		ArgumentConstructor fl_from = new ArgumentConstructor(yamlHandler, baseCommandII+"_filterlog_from", 1, 2, 4, null);
		ArgumentConstructor fl_greaterthan = new ArgumentConstructor(yamlHandler, baseCommandII+"_filterlog_greaterthan", 1, 2, 4, null);
		ArgumentConstructor fl_lessthan = new ArgumentConstructor(yamlHandler, baseCommandII+"_filterlog_lessthan", 1, 2, 4, null);
		ArgumentConstructor fl_orderer = new ArgumentConstructor(yamlHandler, baseCommandII+"_filterlog_orderer", 1, 2, 4, null);
		ArgumentConstructor fl_sortasc = new ArgumentConstructor(yamlHandler, baseCommandII+"_filterlog_sortasc", 1, 1, 3, null);
		ArgumentConstructor fl_sortdesc = new ArgumentConstructor(yamlHandler, baseCommandII+"_filterlog_sortdesc", 1, 1, 3, null);
		ArgumentConstructor fl_to = new ArgumentConstructor(yamlHandler, baseCommandII+"_filterlog_to", 1, 2, 4, null);
		ArgumentConstructor filterlog = new ArgumentConstructor(yamlHandler, baseCommandII+"_filterlog", 0, 0, 0, null,
				fl_between, fl_comment, fl_commentasc, fl_commentdesc, fl_from, fl_greaterthan, fl_lessthan,
				fl_orderer, fl_sortasc, fl_sortdesc, fl_to);
		
		ArgumentConstructor freeze = new ArgumentConstructor(yamlHandler, baseCommandII+"_freeze", 0, 1, 1, null);
		ArgumentConstructor give = new ArgumentConstructor(yamlHandler, baseCommandII+"_give", 0, 2, 999, null);
		ArgumentConstructor log = new ArgumentConstructor(yamlHandler, baseCommandII+"_log", 0, 0, 2, null);
		ArgumentConstructor pay = new ArgumentConstructor(yamlHandler, baseCommandII+"_pay", 0, 2, 999, null);
		
		ArgumentConstructor storder_amount = new ArgumentConstructor(yamlHandler, baseCommandII+"_standingorder_amount", 1, 2, 2, null);
		ArgumentConstructor storder_cancel = new ArgumentConstructor(yamlHandler, baseCommandII+"_standingorder_cancel", 1, 1, 1, null);
		ArgumentConstructor storder_create = new ArgumentConstructor(yamlHandler, baseCommandII+"_standingorder_create", 1, 4, 4, null);
		ArgumentConstructor storder_delete = new ArgumentConstructor(yamlHandler, baseCommandII+"_standingorder_delete", 1, 2, 2, null);
		ArgumentConstructor storder_info = new ArgumentConstructor(yamlHandler, baseCommandII+"_standingorder_info", 1, 1, 2, null);
		ArgumentConstructor storder_list = new ArgumentConstructor(yamlHandler, baseCommandII+"_standingorder_list", 1, 1, 3, null);
		ArgumentConstructor storder_pause = new ArgumentConstructor(yamlHandler, baseCommandII+"_standingorder_pause", 1, 2, 2, null);
		ArgumentConstructor storder_rt = new ArgumentConstructor(yamlHandler, baseCommandII+"_standingorder_repeatingtime", 1, 2, 2, null);
		ArgumentConstructor storder_st = new ArgumentConstructor(yamlHandler, baseCommandII+"_standingorder_starttime", 1, 2, 2, null);
		ArgumentConstructor storder = new ArgumentConstructor(yamlHandler, baseCommandII+"_standingorder", 0, 0, 0, null,
				storder_amount, storder_cancel, storder_create, storder_delete, storder_info,
				storder_list, storder_pause, storder_rt, storder_st);
		
		ArgumentConstructor take = new ArgumentConstructor(yamlHandler, baseCommandII+"_take", 0, 2, 999, null);
		ArgumentConstructor toggle = new ArgumentConstructor(yamlHandler, baseCommandII+"_toggle", 0, 0, 0, null);
		ArgumentConstructor top = new ArgumentConstructor(yamlHandler, baseCommandII+"_top", 0, 0, 1, null);
		
		ArgumentConstructor trend_diagram = new ArgumentConstructor(yamlHandler, baseCommandII+"_trend_diagram", 1, 1, 3, null);
		ArgumentConstructor trend_grafic = new ArgumentConstructor(yamlHandler, baseCommandII+"_trend_grafic", 1, 1, 3, null);
		ArgumentConstructor trend_log = new ArgumentConstructor(yamlHandler, baseCommandII+"_trend_log", 1, 1, 3, null);
		ArgumentConstructor trend = new ArgumentConstructor(yamlHandler, baseCommandII+"_trend", 0, 0, 0, null,
				trend_diagram, trend_grafic, trend_log);
		
		CommandConstructor money = new CommandConstructor(plugin, baseCommandII,
				debt, debts, filterlog, freeze, give, log, pay, storder, take, toggle, top, trend);
		
		CommandConstructor bank = new CommandConstructor(plugin, baseCommandIII);
		
		if(eco.getPath() == null)
		{
			AdvancedEconomy.log.info("path == null");
		}
		if(eco.getName() == null)
		{
			AdvancedEconomy.log.info("name == null");
		}
		registerCommand(eco.getPath(), eco.getName());
		getCommand(eco.getName()).setExecutor(new EcoCommandExecutor(plugin, eco));
		getCommand(eco.getName()).setTabCompleter(new TABCompletion(plugin));
		
		registerCommand(money.getPath(), money.getName());
		getCommand(money.getName()).setExecutor(new EcoCommandExecutor(plugin, money));
		getCommand(money.getName()).setTabCompleter(new TABCompletion(plugin));
		
		registerCommand(bank.getPath(), bank.getName());
		getCommand(bank.getName()).setExecutor(new EcoCommandExecutor(plugin, bank));
		getCommand(bank.getName()).setTabCompleter(new TABCompletion(plugin));
		
		addingHelps(
				eco, 
					deletelog, player, recomment,
				money,
					debt, debt_accept, debt_amount, debt_cancel, debt_create, debt_forgive, debt_info, debt_list,
						debt_pause, debt_reject, debt_repay, debt_send, debt_time,
					debts,
					filterlog, fl_between, fl_comment, fl_commentasc, fl_commentdesc, fl_from, fl_greaterthan, fl_lessthan,
						fl_orderer, fl_sortasc, fl_sortdesc, fl_to, 
					freeze, give, log, pay,
					storder, storder_amount, storder_cancel, storder_create, storder_delete, storder_info, storder_list,
						storder_pause, storder_rt, storder_st,
					take, toggle, top,
					trend, trend_diagram, trend_grafic, trend_log,
				bank);
		
		new ARGEcoDeleteLog(plugin, deletelog);
		new ARGEcoPlayer(plugin, player);
		new ARGEcoReComment(plugin, recomment);
		
		new ARGMoneyDebt(plugin, debt);
		new ARGMoneyDebt_Accept(plugin, debt_accept);
		new ARGMoneyDebt_Amount(plugin, debt_amount);
		new ARGMoneyDebt_Cancel(plugin, debt_cancel);
		new ARGMoneyDebt_Create(plugin, debt_create);
		new ARGMoneyDebt_Forgive(plugin, debt_forgive);
		new ARGMoneyDebt_Info(plugin, debt_info);
		new ARGMoneyDebt_List(plugin, debt_list);
		new ARGMoneyDebt_Pause(plugin, debt_pause);
		new ARGMoneyDebt_Reject(plugin, debt_reject);
		new ARGMoneyDebt_Repay(plugin, debt_repay);
		new ARGMoneyDebt_Send(plugin, debt_send);
		new ARGMoneyDebt_Time(plugin, debt_time);
		
		new ARGMoneyDebts(plugin, debts);
		
		new ARGMoneyFilterLog(plugin, filterlog);
		new ARGMoneyFilterLog_Between(plugin, fl_between);
		new ARGMoneyFilterLog_Comment(plugin, fl_comment);
		new ARGMoneyFilterLog_CommentAscending(plugin, fl_commentasc);
		new ARGMoneyFilterLog_CommentDescending(plugin, fl_commentdesc);
		new ARGMoneyFilterLog_From(plugin, fl_from);
		new ARGMoneyFilterLog_GreaterThan(plugin, fl_greaterthan);
		new ARGMoneyFilterLog_LessThan(plugin, fl_lessthan);
		new ARGMoneyFilterLog_Orderer(plugin, fl_orderer);
		new ARGMoneyFilterLog_SortAscending(plugin, fl_sortasc);
		new ARGMoneyFilterLog_SortDescending(plugin, fl_sortdesc);
		new ARGMoneyFilterLog_To(plugin, fl_to);
		new ARGMoneyFreeze(plugin, freeze);
		//new ARGMoneyGetTotal(plugin);
		new ARGMoneyGive(plugin, give);
		new ARGMoneyLog(plugin, log);
		new ARGMoneyPay(plugin, pay);
		
		new ARGMoneyStandingOrder(plugin, storder);
		new ARGMoneyStandingOrder_Amount(plugin, storder_amount);
		new ARGMoneyStandingOrder_Cancel(plugin, storder_cancel);
		new ARGMoneyStandingOrder_Create(plugin, storder_create);
		new ARGMoneyStandingOrder_Delete(plugin, storder_delete);
		new ARGMoneyStandingOrder_Info(plugin, storder_info);
		new ARGMoneyStandingOrder_List(plugin, storder_list);
		new ARGMoneyStandingOrder_Pause(plugin, storder_pause);
		new ARGMoneyStandingOrder_Repeatingtime(plugin, storder_rt);
		new ARGMoneyStandingOrder_Starttime(plugin, storder_st);
		
		new ARGMoneyTake(plugin, take);
		new ARGMoneyToggle(plugin, toggle);
		new ARGMoneyTop(plugin, top);
		new ARGMoneyTrend(plugin, trend);
		new ARGMoneyTrend_Diagram(plugin, trend_diagram);
		new ARGMoneyTrend_Grafic(plugin, trend_grafic);
		new ARGMoneyTrend_Log(plugin, trend_log);*/
	}
	
	public void ListenerSetup()
	{
		PluginManager pm = getServer().getPluginManager();
		//getServer().getMessenger().registerOutgoingPluginChannel(this, "advanceeconomy:spigottobungee");
		//pm.registerEvents(new PlayerListener(plugin), plugin);
	}
	
	public boolean reload()
	{
		if(!yamlHandler.loadYamlHandler())
		{
			return false;
		}
		if(yamlHandler.get().getBoolean("Mysql.Status", false))
		{
			mysqlSetup.closeConnection();
			if(!mysqlHandler.loadMysqlHandler())
			{
				return false;
			}
			if(!mysqlSetup.loadMysqlSetup())
			{
				return false;
			}
		} else
		{
			return false;
		}
		return true;
	}
	
	public ArrayList<BaseConstructor> getHelpList()
	{
		return helpList;
	}
	
	public void addingHelps(BaseConstructor... objects)
	{
		for(BaseConstructor bc : objects)
		{
			helpList.add(bc);
		}
	}
	
	public ArrayList<CommandConstructor> getCommandTree()
	{
		return commandTree;
	}
	
	public CommandConstructor getCommandFromPath(String commandpath)
	{
		CommandConstructor cc = null;
		for(CommandConstructor coco : getCommandTree())
		{
			if(coco.getPath().equalsIgnoreCase(commandpath))
			{
				cc = coco;
				break;
			}
		}
		return cc;
	}
	
	public CommandConstructor getCommandFromCommandString(String command)
	{
		CommandConstructor cc = null;
		for(CommandConstructor coco : getCommandTree())
		{
			if(coco.getName().equalsIgnoreCase(command))
			{
				cc = coco;
				break;
			}
		}
		return cc;
	}

	public LinkedHashMap<String, ArgumentModule> getArgumentMap()
	{
		return argumentMap;
	}
	
	public ArrayList<String> getPlayers()
	{
		return players;
	}

	public void setPlayers(ArrayList<String> players)
	{
		this.players = players;
	}
	
	public void registerCommand(String... aliases) 
	{
		PluginCommand command = getCommand(aliases[0], plugin);
	 
		command.setAliases(Arrays.asList(aliases));
		getCommandMap().register(plugin.getDescription().getName(), command);
	}
	 
	private static PluginCommand getCommand(String name, ProfessionPro plugin) 
	{
		PluginCommand command = null;
	 
		try {
			Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			c.setAccessible(true);
	 
			command = c.newInstance(name, plugin);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	 
		return command;
	}
	 
	private static CommandMap getCommandMap() 
	{
		CommandMap commandMap = null;
	 
		try {
			if (Bukkit.getPluginManager() instanceof SimplePluginManager) 
			{
				Field f = SimplePluginManager.class.getDeclaredField("commandMap");
				f.setAccessible(true);
	 
				commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	 
		return commandMap;
	}
	
	private boolean setupEconomy()
    {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) 
        {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (rsp == null) 
        {
            return false;
        }
        eco = rsp.getProvider();
        return eco != null;
    }
	
	public static Economy getEco()
	{
		return eco;
	}
	
	public boolean existHook(String externPluginName)
	{
		if(plugin.getServer().getPluginManager().getPlugin(externPluginName) == null)
		{
			return false;
		}
		return true;
	}
	
	public void setupBstats()
	{
		int pluginId = 8653;
        new Metrics(this, pluginId);
	}
	
	private void initLibaries()
	{
		try {
            final File[] libs = new File[] {
                    new File(getDataFolder(), "MathParser.org-mXparser-4.4.2.jar")};
            for (final File lib : libs) {
                if (!lib.exists()) {
                    JarLoader.extractFromJar(lib.getName(),
                            lib.getAbsolutePath());
                }
            }
            for (final File lib : libs) {
                if (!lib.exists()) {
                    getLogger().warning(
                            "There was a critical error loading My plugin! Could not find lib: "
                                    + lib.getName());
                    Bukkit.getServer().getPluginManager().disablePlugin(this);
                    return;
                }
                addClassPath(JarLoader.getJarUrl(lib));
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
	}
	
	private void addClassPath(final URL url) throws IOException 
	{
        final URLClassLoader sysloader = (URLClassLoader) ClassLoader
                .getSystemClassLoader();
        final Class<URLClassLoader> sysclass = URLClassLoader.class;
        try {
            final Method method = sysclass.getDeclaredMethod("addURL",
                    new Class[] { URL.class });
            method.setAccessible(true);
            method.invoke(sysloader, new Object[] { url });
        } catch (final Throwable t) {
            t.printStackTrace();
            throw new IOException("Error adding " + url
                    + " to system classloader");
        }
    }
}