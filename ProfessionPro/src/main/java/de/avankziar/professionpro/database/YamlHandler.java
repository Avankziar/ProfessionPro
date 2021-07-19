package main.java.de.avankziar.professionpro.database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FileUtils;

import main.java.de.avankziar.professionpro.ProfessionPro;
import main.java.de.avankziar.professionpro.database.LanguageObject.LanguageType;

public class YamlHandler
{
	private ProfessionPro plugin;
	private File config = null;
	private YamlConfiguration cfg = new YamlConfiguration();
	
	private File commands = null;
	private YamlConfiguration com = new YamlConfiguration();
	
	private String languages;
	private File language = null;
	private YamlConfiguration lang = new YamlConfiguration();
	
	private LinkedHashMap<String, YamlConfiguration> professions = new LinkedHashMap<>();
	private LinkedHashMap<String, YamlConfiguration> items = new LinkedHashMap<>();

	public YamlHandler(ProfessionPro plugin) throws IOException 
	{
		this.plugin = plugin;
		loadYamlHandler();
	}
	
	public YamlConfiguration getConfig()
	{
		return cfg;
	}
	
	public YamlConfiguration getCom()
	{
		return com;
	}
	
	public YamlConfiguration getL()
	{
		return lang;
	}
	
	public YamlConfiguration getProfessions(String referenceName)
	{
		return (professions.containsKey(referenceName)) ? professions.get(referenceName) : null;
	}
	
	public YamlConfiguration getItem(String referenceName)
	{
		return (items.containsKey(referenceName)) ? items.get(referenceName) : null;
	}
	
	public boolean loadYamlHandler() throws IOException
	{
		if(!mkdirStaticFiles())
		{
			return false;
		}
		
		if(!mkdirDynamicFiles()) //Per "Thing" per example languages, one file
		{
			return false;
		}
		return true;
	}
	
	public boolean mkdirStaticFiles() throws IOException
	{
		//Erstellen aller Werte FÜR die Config.yml
		plugin.setYamlManager(new YamlManager());
		
		//Initialisierung der config.yml
		config = new File(plugin.getDataFolder(), "config.yml");
		if(!config.exists()) 
		{
			ProfessionPro.log.info("Create config.yml...");
			try
			{
				//Erstellung einer "leere" config.yml
				FileUtils.copyToFile(plugin.getResource("default.yml"), config);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		//Laden der config.yml
		if(!loadYamlTask(config, cfg, "config.yml"))
		{
			return false;
		}
		
		//Niederschreiben aller Werte für die Datei
		writeFile(config, cfg, plugin.getYamlManager().getConfigKey());
		
		languages = cfg.getString("Language", "ENGLISH").toUpperCase();
		
		commands = new File(plugin.getDataFolder(), "commands.yml");
		if(!commands.exists()) 
		{
			ProfessionPro.log.info("Create commands.yml...");
			try
			{
				//Erstellung einer "leere" config.yml
				FileUtils.copyToFile(plugin.getResource("default.yml"), commands);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		if(!loadYamlTask(commands, com, "commands.yml"))
		{
			return false;
		}
		writeFile(commands, com, plugin.getYamlManager().getCommandsKey());
		return true;
	}
	
	private boolean mkdirDynamicFiles() throws IOException
	{
		//Vergleich der Sprachen
		List<LanguageObject.LanguageType> types = new ArrayList<LanguageObject.LanguageType>(EnumSet.allOf(LanguageObject.LanguageType.class));
		LanguageType languageType = LanguageType.ENGLISH;
		for(LanguageType type : types)
		{
			if(type.toString().equals(languages))
			{
				languageType = type;
				break;
			}
		}
		//Setzen der Sprache
		plugin.getYamlManager().setLanguageType(languageType);
		
		if(!mkdirLanguage())
		{
			return false;
		}
		
		if(!mkdirItems())
		{
			return false;
		}
		
		if(!mkdirProfessions())
		{
			return false;
		}
		return true;
	}
	
	private boolean mkdirLanguage() throws IOException
	{
		String languageString = plugin.getYamlManager().getLanguageType().toString().toLowerCase();
		File directory = new File(plugin.getDataFolder()+"/Languages/");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		language = new File(directory.getPath(), languageString+".yml");
		if(!language.exists()) 
		{
			ProfessionPro.log.info("Create %lang%.yml...".replace("%lang%", languageString));
			try
			{
				FileUtils.copyToFile(plugin.getResource("default.yml"), language);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		//Laden der Datei
		if(!loadYamlTask(language, lang, languageString+".yml"))
		{
			return false;
		}
		//Niederschreiben aller Werte in die Datei
		writeFile(language, lang, plugin.getYamlManager().getLanguageKey());
		return true;
	}
	
	private boolean mkdirItems() throws IOException
	{
		File directory = new File(plugin.getDataFolder()+"/CustomItems/");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		List<String> referenceList = cfg.getStringList("EnabledItems");
		if(referenceList.isEmpty())
		{
			return true;
		}
		for(String referenceName : referenceList)
		{
			File item = new File(directory.getPath(), referenceName+".yml");
			if(!item.exists()) 
			{
				ProfessionPro.log.info("Create %item%.yml...".replace("%item%", referenceName));
				try
				{
					FileUtils.copyToFile(plugin.getResource("default.yml"), item);
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			YamlConfiguration itm = new YamlConfiguration();
			//Laden der Datei
			if(!loadYamlTask(item, itm, referenceName+".yml"))
			{
				return false;
			}
			//Niederschreiben aller Werte in die Datei
			writeFile(item, itm, plugin.getYamlManager().getItemKeys());
			items.put(referenceName, itm);
		}
		return true;
	}
	
	private boolean mkdirProfessions() throws IOException
	{
		File directory = new File(plugin.getDataFolder()+"/Professions/");
		if(!directory.exists())
		{
			directory.mkdir();
		}
		List<String> referenceList = cfg.getStringList("EnabledProfessions");
		if(referenceList.isEmpty())
		{
			return true;
		}
		for(String referenceName : referenceList)
		{
			File profession = new File(directory.getPath(), referenceName+".yml");
			if(!profession.exists()) 
			{
				ProfessionPro.log.info("Create %prof%.yml...".replace("%prof%", referenceName));
				try
				{
					FileUtils.copyToFile(plugin.getResource("default.yml"), profession);
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			YamlConfiguration pfs = new YamlConfiguration();
			//Laden der Datei
			if(!loadYamlTask(profession, pfs, referenceName+".yml"))
			{
				return false;
			}
			//Niederschreiben aller Werte in die Datei
			writeFile(profession, pfs, plugin.getYamlManager().getProfessionKeys());
			professions.put(referenceName, pfs);
		}
		return true;
	}
	
	private boolean loadYamlTask(File file, YamlConfiguration yaml, String filename)
	{
		try 
		{
			yaml.load(file);
		} catch (IOException | InvalidConfigurationException e) 
		{
			ProfessionPro.log.severe(
					"Could not load the %file% file! You need to regenerate the %file%! Error: ".replace("%file%", filename)
					+ e.getMessage());
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private boolean writeFile(File file, YamlConfiguration yml, LinkedHashMap<String, LanguageObject> keyMap) throws IOException
	{
		for(String key : keyMap.keySet())
		{
			LanguageObject languageObject = keyMap.get(key);
			if(languageObject.languageValues.containsKey(plugin.getYamlManager().getLanguageType()) == true)
			{
				plugin.getYamlManager().setFileInput(yml, keyMap, key, plugin.getYamlManager().getLanguageType());
			} else if(languageObject.languageValues.containsKey(plugin.getYamlManager().getDefaultLanguageType()) == true)
			{
				plugin.getYamlManager().setFileInput(yml, keyMap, key, plugin.getYamlManager().getDefaultLanguageType());
			}
		}
		yml.save(file);
		return true;
	}
}