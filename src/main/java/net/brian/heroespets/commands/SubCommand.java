package net.brian.heroespets.commands;

import net.brian.heroespets.HeroesPets;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    protected final String name;
    protected final HeroesPets plugin;

    public SubCommand(HeroesPets plugin,String name){
        this.plugin = plugin;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public abstract void onCommand(CommandSender sender,String[] args);
}
