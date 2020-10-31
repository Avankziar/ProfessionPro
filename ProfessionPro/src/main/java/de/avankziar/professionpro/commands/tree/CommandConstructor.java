package main.java.de.avankziar.professionpro.commands.tree;

import java.util.ArrayList;

import main.java.de.avankziar.professionpro.ProfessionPro;

public class CommandConstructor extends BaseConstructor
{
    public ArrayList<ArgumentConstructor> subcommands;
    public ArrayList<String> tablist;

	public CommandConstructor(ProfessionPro plugin, String path,
			boolean canAccessConsole,
    		ArgumentConstructor...argumentConstructors)
    {
		super(plugin.getYamlHandler().getCom().getString(path+".Name"),
				path,
				plugin.getYamlHandler().getCom().getString(path+".Permission"),
				plugin.getYamlHandler().getCom().getString(path+".Suggestion"),
				canAccessConsole);
        this.subcommands = new ArrayList<>();
        this.tablist = new ArrayList<>();
        for(ArgumentConstructor ac : argumentConstructors)
        {
        	this.subcommands.add(ac);
        	this.tablist.add(ac.getName());
        }
        plugin.getCommandTree().add(this);
    }
}