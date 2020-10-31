package main.java.de.avankziar.professionpro.commands.tree;

import java.io.IOException;

import org.bukkit.command.CommandSender;

import main.java.de.avankziar.professionpro.ProfessionPro;

public abstract class ArgumentModule
{
	public ArgumentConstructor argumentConstructor;

    public ArgumentModule(ProfessionPro plugin, ArgumentConstructor argumentConstructor)
    {
       this.argumentConstructor = argumentConstructor;
       plugin.getArgumentMap().put(argumentConstructor.getPath(), this);
    }
    
    //This method will process the command.
    public abstract void run(CommandSender sender, String[] args) throws IOException;

}
