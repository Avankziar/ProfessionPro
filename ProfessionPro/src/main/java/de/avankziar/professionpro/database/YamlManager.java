package main.java.de.avankziar.professionpro.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import main.java.de.avankziar.professionpro.database.LanguageObject.LanguageType;

public class YamlManager
{
	private LanguageType languageType = LanguageType.GERMAN;
	private LanguageType defaultLanguageType = LanguageType.GERMAN;
	private static LinkedHashMap<String, LanguageObject> configKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, LanguageObject> commandsKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, LanguageObject> generalProfessionKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, LanguageObject> languageKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, LanguageObject> professionKeys = new LinkedHashMap<>();
	private static LinkedHashMap<String, LanguageObject> itemKeys = new LinkedHashMap<>();
	
	public YamlManager()
	{
		initConfig();
		initCommands();
		initGeneralProfession();
		initLanguage();
		initProfessions();
		initItems();
	}
	
	public LanguageType getLanguageType()
	{
		return languageType;
	}

	public void setLanguageType(LanguageType languageType)
	{
		this.languageType = languageType;
	}
	
	public LanguageType getDefaultLanguageType()
	{
		return defaultLanguageType;
	}
	
	public LinkedHashMap<String, LanguageObject> getConfigKey()
	{
		return configKeys;
	}
	
	public LinkedHashMap<String, LanguageObject> getCommandsKey()
	{
		return commandsKeys;
	}
	
	public LinkedHashMap<String, LanguageObject> getGeneralProfessionKeys()
	{
		return generalProfessionKeys;
	}
	
	public LinkedHashMap<String, LanguageObject> getLanguageKey()
	{
		return languageKeys;
	}
	
	public LinkedHashMap<String, LanguageObject> getProfessionKeys()
	{
		return professionKeys;
	}
	
	public LinkedHashMap<String, LanguageObject> getItemKeys()
	{
		return itemKeys;
	}

	public void setFileInput(YamlConfiguration yml, LinkedHashMap<String, LanguageObject> keyMap, String key, LanguageType languageType)
	{
		if(!keyMap.containsKey(key))
		{
			return;
		}
		if(yml.get(key) != null)
		{
			return;
		}
		if(keyMap
				.get(key)
				.languageValues
				.get(languageType)
				.length == 1)
		{
			yml.set(key,
				keyMap
				.get(key)
				.languageValues
				.get(languageType)[0]);
		} else
		{
			List<Object> list = Arrays.asList(keyMap.get(key).languageValues.get(languageType));
			ArrayList<String> stringList = new ArrayList<>();
			if(list instanceof List<?>)
			{
				for(Object o : list)
				{
					if(o instanceof String)
					{
						stringList.add((String) o);
					} else
					{
						stringList.add(o.toString());
					}
				}
			}
			yml.set(key, (List<String>) stringList);
		}
	}
	
	@SuppressWarnings("unused")
	public void initConfig() //TODO:Config
	{
		Base:
		{
			configKeys.put("Language"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"English"}));
			configKeys.put("Prefix"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"&7[&2AdvancedEconomyPlus&7] &r"}));
			configKeys.put("Bungee"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					false}));
		}
		Mysql:
		{
			configKeys.put("Mysql.Status"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					false}));
			configKeys.put("Mysql.Host"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"127.0.0.1"}));
			configKeys.put("Mysql.Port"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					3306}));
			configKeys.put("Mysql.DatabaseName"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"mydatabase"}));
			configKeys.put("Mysql.SSLEnabled"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					false}));
			configKeys.put("Mysql.AutoReconnect"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					true}));
			configKeys.put("Mysql.VerifyServerCertificate"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					false}));
			configKeys.put("Mysql.User"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"admin"}));
			configKeys.put("Mysql.Password"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"not_0123456789"}));
			configKeys.put("Mysql.TableNameI"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"economyPlayerData"}));
			configKeys.put("Mysql.TableNameII"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"economyBankData"}));
			configKeys.put("Mysql.TableNameIII"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"economyActionLogger"}));
			configKeys.put("Mysql.TableNameIV"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"economyTrendLogger"}));
			configKeys.put("Mysql.TableNameV"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"economyStandingOrder"}));
			configKeys.put("Mysql.TableNameVI"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"economyLoan"}));
			configKeys.put("Mysql.TableNameVII"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"economyLoggerSettingsPreset"}));
		}
		Generator:
		{
			configKeys.put("Identifier.Click"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"click"}));
			configKeys.put("Identifier.Hover"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"hover"}));
			configKeys.put("Seperator.BetweenFunction"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"~"}));
			configKeys.put("Seperator.WhithinFuction"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"@"}));
			configKeys.put("Seperator.Space"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"+"}));
			configKeys.put("Seperator.HoverNewLine"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"~!~"}));
		}
		MechanicSettings:
		{
			configKeys.put("Use.PlayerAccount"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					true}));
			configKeys.put("Use.Bank"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					false}));
			configKeys.put("Use.StandingOrder"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					false}));
			configKeys.put("Use.Loan"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					false}));
		}
		EconomySettings:
		{
			configKeys.put("StartMoney"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					0.0}));
			configKeys.put("CurrencyNameSingular"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"Euro"}));
			configKeys.put("CurrencyNamePlural"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"Euros"}));
			configKeys.put("TrendLogger.ValueIsStabil"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					1000.0}));
			configKeys.put("GraficSpaceSymbol"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"ˉ"}));
			configKeys.put("GraficPointSymbol"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"x"}));
		}
		JobsRebornHook:
		{
			configKeys.put("JobsRebornHookTaskTimer"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"0", "15", "30", "45"}));
		}
		BankSettings:
		{
			configKeys.put("ReservedNames"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"YourSever", "YourServerMk2"}));
			configKeys.put("BankAccountFromat"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					"FOUR_DIGITS_TIMES_THREE"}));
		}
		RepeatingTimes:
		{
			configKeys.put("StandingOrderRepeatTime"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					180}));
			configKeys.put("LoanRepaymentRepeatTime"
					, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
					180}));
		}
	}
	
	@SuppressWarnings("unused") //TODO:Commands
	public void initCommands()
	{
		String path = "";
		Econ:
		{
			commandsInput("eco", "econ", "eco.cmd.eco", "/econ [pagenumber]", "/econ ",
					"&c/econ &f| Infoseite für alle Befehle.",
					"&c/econ &f| Info page for all commands.",
					"Base and Info Command");
			comEcon();
		}
		Money:
		{
			commandsInput("money", "money", "eco.cmd.money", "/money", "/money",
					"&c/money &f| Zeigt dein Guthaben an.",
					"&c/money &f| Shows your balance.",
					"Display your balance");			
			comMoney();
			comMoneyLoggerSettings();
			comMoneyLoan();
			comMoneyStandingOrder();
		}
	}
	
	private void commandsInput(String path, String name, String basePermission, 
			String suggestion, String commandString,
			String helpInfoGerman, String helpInfoEnglish, String explanation)
	{
		commandsKeys.put(path+".Name"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
				name}));
		commandsKeys.put(path+".Permission"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
				basePermission}));
		commandsKeys.put(path+".Suggestion"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
				suggestion}));
		commandsKeys.put(path+".CommandString"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
				commandString}));
		commandsKeys.put(path+".HelpInfo"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN, LanguageType.ENGLISH}, new Object[] {
				helpInfoGerman,
				helpInfoEnglish}));
		commandsKeys.put(path+".Explanation"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
				explanation}));
	}
	
	private void argumentInput(String path, String argument, String basePermission, 
			String suggestion, String commandString,
			String helpInfoGerman, String helpInfoEnglish, String explanation)
	{
		commandsKeys.put(path+".Argument"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
				argument}));
		commandsKeys.put(path+".Permission"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
				basePermission+"."+argument}));
		commandsKeys.put(path+".Suggestion"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
				suggestion}));
		commandsKeys.put(path+".CommandString"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
				commandString}));
		commandsKeys.put(path+".HelpInfo"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN, LanguageType.ENGLISH}, new Object[] {
				helpInfoGerman,
				helpInfoEnglish}));
		commandsKeys.put(path+".Explanation"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
				explanation}));
	}
	
	private void comEcon()
	{
		String basePermission = "eco.cmd.eco";
		argumentInput("eco_deletelog", "deletelog", basePermission,
				"/econ deletelog <id>", "/econ deletelog ",
				"&c/econ deletelog &f| Löscht den Log-Eintrag. AdminBefehl.",
				"&c/econ deletelog &f| Deletes the log entry. Admin command.",
				"Delete the log with the <id>");
		
		argumentInput("eco_player", "player", basePermission,
				"/econ player <player>", "/econ player ",
				"&c/econ player <Spielername> &f| Zeigt alle Infos zum Spieler an.",
				"&c/econ player <player name> &f| Shows all information about the player.",
				"Show the players balance uuid etc.");
		
		argumentInput("eco_recomment", "recomment", basePermission,
				"/econ recomment <id> <message>", "/econ recomment ",
				"&c/econ recomment <Id> <Note> &f| Ändert die angehängte Notiz des Log-Eintrages.",
				"&c/econ recomment <Id> <Note> &f| Changes the attached note of the log entry.",
				"Rewrite a log entry.");
	}
	
	private void comMoney()
	{
		String basePermission = "eco.cmd.money";
		argumentInput("money_freeze", "freeze", basePermission,
				"/money freete <player>", "/money freeze ",
				"&c/money freeze <Spielername> &f| Friert das Spielerkonto ein oder gibt es frei.",
				"&c/money freeze <player name> &f| Freezes or releases the player account.",
				"Freeze the playeraccount");
		
		argumentInput("money_give", "give", basePermission,
				"/money give <player> <value> <customTo> <customOrderer> [note]", "/money give ",
				"&c/money give <Spielername> <Betrag> &f| Überweist den Betrag auf das Spielerkonto.",
				"&c/money give <player name> <amount> &f| Transfers the amount to the players balance.",
				"Give the player from the void money" + 
				" customTo = The fake Player/Npc/whatever from the money may comes. (It comes always from the void btw.^^)" + 
				" customOrderer = The fake Player/Npc/whatever where the orderer of this is. Can you use by console use^^.");
		
		argumentInput("money_pay", "pay", basePermission,
				"/money pay <player> <value> [note]", "/money pay ",
				"&c/money pay <Spielername> <Betrag> &f| Zahlt dem Spieler den Betrag.",
				"&c/money pay <player name> &f| Pays the player the amount",
				"Player to player paying.");
		
		argumentInput("money_set", "set", basePermission,
				"/money set <player> <value> <customTo> <customOrderer> [note]", "/money set ",
				"&c/money set <Spielername> <Betrag> &f| Setzt das Guthaben des Spielers auf den gewünschten Betrag.",
				"&c/money set <playername> <amount> &f| Sets the players balance to the desired amount.",
				"Set the balance of the player." + 
				" customTo = The fake Player/Npc/whatever where the money may go/comes." +
				" (It goes/comes always in the void btw.^^)" + 
				" customOrderer = The fake Player/Npc/whatever where the orderer of this is. Can you use by console use^^.");
		
		argumentInput("money_take", "take", basePermission,
				"/money take <player> <value> <customTo> <customOrderer> [note]", "/money take ",
				"&c/money take <Spielername> <Betrag> &f| Zieht den Betrag vom Spielerkonto ab.",
				"&c/money take <playername> <amount> &f| Deduct the amount from the player balance.",
				"Take the money from a player in the void" + 
				" customTo = The fake Player/Npc/whatever where the money may go. (It goes always in the void btw.^^)" + 
				" customOrderer = The fake Player/Npc/whatever where the orderer of this is. Can you use by console use^^.");
		
		argumentInput("money_toggle", "toggle", basePermission,
				"/money toggle", "/money toggle ",
				"&c/money toggle &f| Schaltet Nachrichten aus und ein, die als Überweisung auf euer Spielerkonto eingehen.",
				"&c/money toggle &f| Enables and disables messages that are sent to your player account as a bank transfer.",
				"Toggle to see money payment messages");
		
		argumentInput("money_top", "top", basePermission,
				"/money top [pagenumber]", "/money top ",
				"&c/money top [Seitenzahl] &f| Zeigt die Liste der bestbetuchten Spieler an.",
				"&c/money top [page number] &f| Shows the list of the best players.",
				"Show the top balance players.");
	}
	
	private void comMoneyLoggerSettings()
	{
		String basePermission = "eco.cmd.money";
		argumentInput("money_loggersettings", "loggersettings", basePermission,
				"/money loggersetting", "/money loggersetting ",
				"&c/money loggersettings &f| ",
				"&c/money loggersettings &f| ",
				"Open the Gui for the loggersettings for Action- and trendlog");
		
		basePermission = "eco.cmd.money.loggersettings";
		argumentInput("money_loggersettings_gui", "gui", basePermission,
				"/money loggersetting gui [playername] [page] [methode]", "/money loggersetting gui ",
				"&c/money loggersetting gui [Spielername] [Seitenzahl] [Methode] &f| Öffnet die Gui und mit angegebenen Argumenten gibt es die Daten in Form von Log, Diagram etc. aus.",
				"&c/money loggersetting gui [playername] [page] [methode] &f| Opens the gui and with given arguments it outputs the data in form of log, diagram etc.",
				"Open the Gui for the loggersettings for Action- and trendlog. And display choosen settings as log, diagram etc.");
		
		argumentInput("money_loggersettings_other", "other", basePermission,
				"/money loggersetting other [playername]", "/money loggersetting other ",
				"&c/money loggersetting other [Spielername] &f| Öffnet die Gui eines anderen Spielers.",
				"&c/money loggersetting other [playername] &f| Opens the gui of another player.",
				"Open the Gui for the loggersettings of a other player for Action- and trendlog");
		
		argumentInput("money_loggersettings_text", "text", basePermission,
				"/money loggersetting text <Text...>", "/money loggersetting text ",
				"&c/money loggersetting text <Text...> &f| Texteditor für bestimmte Parameter der Gui.",
				"&c/money loggersetting text <Text...> &f| Text editor for certain parameters of the Gui.",
				"Set a Searchtext for the comment, from, to or orderer value. (Always gui first!)");
	}
	
	private void comMoneyLoan()
	{		
		String basePermission = "eco.cmd.money";
		argumentInput("money_loan", "loan", basePermission,
				"/money loan", "/money loan ",
				"&c/money loan &f| Zwischenbefehl",
				"&c/money loan &f| Default command",
				"Default Command");
		
		argumentInput("money_loans", "loans", basePermission,
				"/money loans [page] [player]", "/money loans ",
				"&c/money Loans [Seitenzahl] [Spielername] &f| Zeigt alle Kredite des Spielers an.",
				"&c/money loans [pagenumber] [playername] &f| Shows all loans of the player.",
				"Show loans of the player");
		
		basePermission = "eco.cmd.money.loan";
		argumentInput("money_loan_accept", "accept", basePermission,
				"/money loan accept [confirm]", "/money loan accept ",
				"&c/money loan accept [bestätigen] &f| Akzeptiert einen Kreditvorschlag.",
				"&c/money loan accept [confirm] &f| Accept a loanproposal.",
				"Accept the loanproposal from the other player.");
		
		argumentInput("money_loan_amount", "amount", basePermission,
				"/money loan amount <totalamount> <amountratio> <interest>", "/money loan amount ",
				"&c/money loan amount <gesamtbetrag> <ratenbetrag> <zinzen in %> &f| Setzt für den Gesamtbetrag, Ratenbetrag und die Zinsen für einen Kredit, welcher sich noch in Bearbeitung befindet.",
				"&c/money loan amount  <totalamount> <amountratio> <interest in %> &f| Sets the total amount, installment amount and interest for a loan that is still being processed.",
				"Set to a loan in workprogress the totalamount, amountratio and interest.");
		
		argumentInput("money_loan_cancel", "cancel", basePermission,
				"/money loan cancel", "/money loan cancel ",
				"&c/money loan cancel &f| Bricht die Krediterstellung ab.",
				"&c/money loan cancel &f| Cancels the credit creation.",
				"Cancel the loan work in progress.");
		
		argumentInput("money_loan_create", "create", basePermission,
				"/money loan create <name> <from> <to>", "/money loan create ",
				"&c/money loan create <Name> <Von> <Zu> &f| Erstellt einen Kreditvorschlag.",
				"&c/money loan create <name> <from> <to> &f| Create a loanproposal.",
				"Create a work in progress loanproposal");
		
		argumentInput("money_loan_forgive", "forgive", basePermission,
				"/money loan forgive <id> [confirm]", "/money loan forgive ",
				"&c/money loan forgive <id> &f| Der Restbetrag des Kredits wird vergeben.",
				"&c/money loan forgive <id> &f| The remaining amount of the loan will be forgiven.",
				"Forgive the restamount of the loan.");
		
		argumentInput("money_loan_info", "info", basePermission,
				"/money loan info [id]", "/money loan info ",
				"&c/money loan info [id] &f| Zeigt alle Infos zu allen Krediten an. Ohne Id, wird der Kreditvorschlag angezeigt.",
				"&c/money loan info [id] &f| Shows all information about all loans. Without Id, the loan proposal is displayed.",
				"Show all info to a loan. By no id, than is the work in progress loan");
		
		argumentInput("money_loan_inherit", "inherit", basePermission,
				"/money loan inherit <id> <playername>", "/money loan inherit ",
				"&c/money loan inherit <id> <Spielername> &f| Lässt den Spieler den Kredit erben. Somit muss er nun zahlen. Adminbefehl um bei Betrugsfall mit einem 2. Account, diesen dann zu belasten.",
				"&c/money loan inherit <id> <playername> &f| Lets the player inherit the loan. So now he must pay. Admin command to debit a 2nd account in case of fraud with a 2nd account.",
				"Let the player inherit the loans to pay. Admincommand to inherit loans to player whitch cheats with a second acc.");
		
		argumentInput("money_loan_list", "list", basePermission,
				"/money loan list [page]", "/money loan list ",
				"&c/money loan list [Seitenzahl] &f| Zeigt seitenbasiert alle Kredite als Liste.",
				"&c/money loan list [page] &f| Shows all loans in a page-based list.",
				"Show a list of all loans of all the player");
		
		argumentInput("money_loan_pause", "pause", basePermission,
				"/money loan pause <id>", "/money loan pause ",
				"&c/money loan pause <id> &f| Pausiert oder nimmt die Zahlungen des Kredits wieder auf. Nur für den Krediteigentümer möglich!",
				"&c/money loan pause <id> &f| Pauses or resumes payments on the loan. Only possible for the loan owner!",
				"Pause or unpause a loanrepayment");
		
		
		argumentInput("money_loan_payback", "payback", basePermission,
				"/money loan payback <id>", "/money loan payback ",
				"&c/money loan payback <id> &f| Zahlt dem Spieler den Rest des Kredites zurück als Admin.",
				"&c/money loan payback <id> &f| Pay the player back the rest of the loan as admin.",
				"As admin payback the rest amount to the player.");
		
		argumentInput("money_loan_reject", "reject", basePermission,
				"/money loan reject", "/money loan reject ",
				"&c/money loan reject &f| Lehnt einen Kreditvorschlag ab.",
				"&c/money loan reject &f| Rejects a loan proposal.",
				"Reject a loan proposal");
		
		argumentInput("money_loan_repay", "repay", basePermission,
				"/money loan repay <id> <amount>", "/money loan repay ",
				"&c/money loan repay <id> <Betrag> &f| Zahlt einen Betrag vom Kredit ab.",
				"&c/money loan repay <id> <amount> &f| Pays an amount off the loan.",
				"Repay the amount to a loan.");
		
		argumentInput("money_loan_send", "send", basePermission,
				"/money loan send <player>", "/money loan send ",
				"&c/money loan send <spielername> &f| Sendet einen Kreditvorschlag einem Spieler.",
				"&c/money loan send <player> &f| Sends a loan proposal to a player.",
				"Send the player a loan proposal");
		
		argumentInput("money_loan_time", "time", basePermission,
				"/money loan time <starttime> <endtime> <repeatingtime>", "/money loan time ",
				"&c/money loan time <startdatum|dd.MM.yyyy-HH:mm> <entdatum|dd.MM.yyyy-HH:mm> <ratenzyklus|dd-HH:mm> &f| Setzt die Zeiten für den Kreditvorschlag.",
				"&c/money loan time <starttime|dd.MM.yyyy-HH:mm> <endtime|dd.MM.yyyy-HH:mm> <repeatingtime|dd-HH:mm> &f| Sets the times for the loan proposal.",
				"Set the times. Starttime and Endtime in <dd.MM.yyyy-HH:mm> and RepeatingTime in <dd-HH:mm> format");
		
		argumentInput("money_loan_transfer", "", basePermission,
				"/money loan transfer <id> <player>", "/money loan transfer ",
				"&c/money loan transfer <id> <Spielername> &f| Überträgt den Eigentümerstatus und Rückzahlrecht an den Spieler.",
				"&c/money loan transfer <id> <player> &f| Transfers the ownership status and repayment right to the player.",
				"Transfer the ownerstatus of your loan to the other player.");
		
		
	}
	
	private void comMoneyStandingOrder()
	{
		String basePermission = "eco.cmd.money";
		argumentInput("money_standingorder", "standingorder", basePermission,
				"/money standingorder", "/money standingorder ",
				"&c/money standingorder &f| Zwischenbefehl",
				"",
				"Default Command");
		
		argumentInput("money_standingorders", "standingorders", basePermission,
				"/money standingorders [page] [playername]", "/money standingorders ",
				"&c/money standingorders [Seitenzahl] [Spielername] &f| Listet alle Daueraufträge des Spielers auf.",
				"",
				"Shows all standingorders of a Player.");
		
		basePermission = "eco.cmd.money.standingorder";
		argumentInput("money_standingorder_amount", "amount", basePermission,
				"/money standingorder amount <amount>", "/money standingorder amount ",
				"&c/money standingorder amount <Betrag> &f| Setzt den Betrag für ein noch wartenden Dauerauftrag.",
				"",
				"Set to a waiting standingorder the amount");
		
		argumentInput("money_standingorder_cancel", "cancel", basePermission,
				"/money standingorder cancel", "/money standingorder cancel ",
				"&c/money standingorder cancel &f| Bricht den noch wartenden Dauerauftrag ab.",
				"",
				"Cancel the waiting standing order.");
		
		argumentInput("money_standingorder_create", "create", basePermission,
				"/money standingorder create <name> <from> <to>", "/money standingorder create ",
				"&c/money standingorder create <name> <Von> <Zu> &f| Erstellt einen wartenden Dauerauftrag. Durch weitere Einstellung wird dieser finalisiert.",
				"",
				"Create a Standingorder. Additional Settings must be set.");
		
		argumentInput("money_standingorder_delete", "delete", basePermission,
				"/money standingorder delete <id>", "/money standingorder delete ",
				"&c/money standingorder delete <id> &f| Löscht den schon existierenden Dauerauftrag.",
				"",
				"Delete the standing order.");
		
		argumentInput("money_standingorder_info", "info", basePermission,
				"/money standingorder info [id]", "/money standingorder info ",
				"&c/money standingorder info [id] &f| Zeigt alle Info zu einem Dauerauftrag an. Bei keiner Angabe, zeigt es den noch wartenden Dauerauftrag.",
				"",
				"Show Infos about standingorder.");
		
		argumentInput("money_standingorder_list", "list", basePermission,
				"/money standingorder list [page]", "/money standingorder list ",
				"&c/money standingorder list [Seitenzahl] &f| Listet alle Daueraufträge von allen Spielern auf.",
				"",
				"Show a list of all standingorders from all players.");
		
		argumentInput("money_standingorder_pause", "pause", basePermission,
				"/money standingorder pause <id>", "/money standingorder pause ",
				"&c/money standingorder pause <ID> &f| Pausiert den Dauerauftrag. Falls er vorher abgebrochen wurde, setzte er den Status zurück.",
				"",
				"Paused a standingorder or cancelled the cancelstatus.");
		
		argumentInput("money_standingorder_repeatingtime", "repeatingtime", basePermission,
				"/money standingorder repeatingtime <dd-HH:mm value>", "/money standingorder repeatingtime ",
				"&c/money standingorder repeatingtime <dd-HH:mm Wert> &f| Setzt eine Wiederholungsvariable, welche im dd-HH:mm Format geschrieben werden muss.",
				"",
				"Set the repeating time of the waiting standing order. Must use the specific >dd-HH:mm< Pattern. For example 01-23:50 for 1 day, 23 hours and 50 seconds.");
		
		argumentInput("money_standingorder_starttime", "starttime", basePermission,
				"/money standingorder starttime <dd.MM.yyyy-HH:mm value>", "/money standingorder starttime ",
				"&c/money standingorder starttime <dd.MM.yyyy-HH:mm Wert> &f| Setzt das Startdatum. Es müssen vorher alle anderen Eigenschaften gesetzt sein, denn dieser Befehl startet auch gleichzeitig den Dauerauftrag!",
				"",
				"Set the starttime in <dd.MM.yyyy-HH:mm> format. And Starts the standing order!");
	}
	
	public void initLanguage() //TODO:Language
	{
		languageKeys.put("NoPermission"
			, new LanguageObject(new LanguageType[] {LanguageType.GERMAN, LanguageType.ENGLISH}, new Object[] {
			"&cDu hast dafür keine Rechte!",
			"&cYou have no rights!"}));
		/*languageKeys.put(""
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN, LanguageType.ENGLISH}, new Object[] {
				"",
				""}));*/
	}
	
	public void initGeneralProfession() //TODO:GeneralProfession
	{
		
	}
	
	public void initProfessions() //TODO:Profession
	{
		professionKeys.put("ReferenceName"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"SMITH"}));
		professionKeys.put("DisplayName"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"Smith"}));
		professionKeys.put("PerCommandDirectAccessible"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						true}));
		professionKeys.put("MaxLevel"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						100}));
		professionKeys.put("LevelEquation"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"StartValue*(1-e^(-CurveValue*Level))"}));
		professionKeys.put("LevelValues"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"StartValue = 10000",
						"CurveValue = 0.03"}));
		professionKeys.put("MoneyMultiplicatorPerLevelEquation"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"1+Level*EndLevelMultiplicator-EndLevelMultiplicator"}));
		professionKeys.put("MoneyMultiplicatorPerLevelValues"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"EndLevelMultiplicator = 0.01",
						"Dummy1 = 4.0"}));
		professionKeys.put("ProfessionExpMultiplicatorPerLevelEquation"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"1+Level*EndLevelMultiplicator-EndLevelMultiplicator"}));
		professionKeys.put("ProfessionExpMultiplicatorPerLevelValues"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"EndLevelMultiplicator = 0.01",
						"Dummy1 = 4.0"}));
		professionKeys.put("ItemMultiplicatorPerLevelEquation"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"1+Level*EndLevelMultiplicator-EndLevelMultiplicator"}));
		professionKeys.put("ItemMultiplicatorPerLevelValues"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"EndLevelMultiplicator = 0.01",
						"Dummy1 = 4.0"}));
		professionKeys.put("VanillaExpMultiplicatorPerLevelEquation"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"1+Level*EndLevelMultiplicator-EndLevelMultiplicator"}));
		professionKeys.put("MoneyMultiplicatorPerLevelValues"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"EndLevelMultiplicator = 0.01",
						"Dummy1 = 4.0"}));
		professionKeys.put("PaymentPlan"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"CRAFTING@IRON_HOE;AIR@1.0;1.0;1.0;1.0",
						"CRAFTING@IRON_AXE;AIR@2.0;3.0;1.0;1.0",
						"CRAFTING@IRON_SHOVEL;AIR@1.5;1.2;1.0;1.0",
						"CRAFTING@IRON_PICKAXE;AIR@3.0;2.0;2.0;1.0",
						"CRAFTING@IRON_SWORD;AIR@4.0;0.5;3.0;1.0"}));
		professionKeys.put("PaymentOptionType"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"MONEY_AND_PROFESSIONEXP_AND_ITEM"}));
		professionKeys.put("Upgradeable"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						true}));
		professionKeys.put("PossibleUpgrades"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						//Target@Amount1;Material1|Amount2;Material2@Amount3;CustomItem3|...
						"WEAPONSMITH;50@5000.0@IRON;256|IRON_AXE;5@NULL;0|NULL;0",
						"ARMORSMITH;50@5000.0@IRON;256|IRON_BOOTS;5@NULL;0|NULL;0"}));
		professionKeys.put("Fusionable"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						true}));
		professionKeys.put("PossibleFusions"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						//Target@Profession1;Level1|Profession2;Level2;...@Amount1;Material1|Amount2;Material2;...@Amount3;CustomItem3|...
						"BLOODYSMITH@5000.0@SMITH;75|BUTCH;50@IRON;256|IRON_SWORD;5@NULL;0|NULL;0",
						"POLLACK@5000.0@SMITH;75|LUMBERJACK;50@IRON;256|IRON_AXE;5@NULL;0|NULL;0"}));
		professionKeys.put("CraftableType"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"WHITELIST"}));
		professionKeys.put("CraftableTypeLinkedWithLevels"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						true}));
		professionKeys.put("CraftableItems"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"IRON_AXE;1",
						"IRON_HOE;1",
						"IRON_SWORD;10"}));
		professionKeys.put("CraftableCustomItems"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"BloodyAxe;10",
						"ScissorsOfDoom;10"}));
		professionKeys.put("PossibleInteraction"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"WORKINGBENCH;UNKNOW;1",
						"FURNANCE;UNKNOW;1",
						"BLAST_FURNANCE;UNKNOW;20"}));
		professionKeys.put("InteractableType"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"WHITELIST"}));
		professionKeys.put("InteractableTypeLinkedWithLevels"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						true}));
	}
	
	public void initItems()
	{
		itemKeys.put("DisplayName"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"&cThe &6goddamn &4bloody &bA&7x&9e"}));
		itemKeys.put("Material"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"IRON_AXE"}));
		itemKeys.put("isUnbreakable"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						false}));
		itemKeys.put("Attributes"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"GENERIC_ATTACK_DAMAGE;10.0",
						"GENERIC_LUCK;73"}));
		itemKeys.put("Itemflag"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						""}));
		itemKeys.put("Enchantments"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"DURABILITY;4",
						"DAMAGE_ALL:6"}));
		itemKeys.put("PotionTyp"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"ABSORPTION;9600;2"}));
		itemKeys.put("Lore"
				, new LanguageObject(new LanguageType[] {LanguageType.GERMAN}, new Object[] {
						"&6----------------------------------",
						"&eA bloodstained rusty iron axe.",
						"&cMany have succumbed to the bloodlust of the owner.",
						"&4Are you the next swinging one?",
						"&6----------------------------------",
						"&eStats",
						"5 &cHearts &7Base Damage",
						"7 &ePercent &7more Dropbase Luck",
						"&6----------------------------------",
						"&dEnchantments",
						"&9Durability &fIV",
						"&9Damage All Targets &fVI"
						}));
	}
}
