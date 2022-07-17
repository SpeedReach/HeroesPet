package net.brian.heroespets.commands.subcommands;

import net.brian.heroespets.HeroesPets;
import net.brian.heroespets.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {

    public ReloadCommand(HeroesPets plugin) {
        super(plugin,"reload");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(sender.hasPermission("heroespet.admin")){
            plugin.getConfigManager().reload();
        }
    }
}
