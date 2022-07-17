package net.brian.heroespets.commands;

import net.brian.heroespets.HeroesPets;
import net.brian.heroespets.api.players.PlayerPetProfile;
import net.brian.heroespets.commands.subcommands.ExpCommand;
import net.brian.heroespets.commands.subcommands.PetCommand;
import net.brian.heroespets.commands.subcommands.ReloadCommand;
import net.brian.heroespets.core.gui.PetMenu;
import net.brian.heroespets.core.gui.PetMenuController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandManager implements CommandExecutor {

    public List<SubCommand> subCommands = new ArrayList<>();

    public CommandManager(HeroesPets plugin){
        plugin.getCommand("HeroesPet").setExecutor(this);

        subCommands.add(new ReloadCommand(plugin));
        subCommands.add(new ExpCommand(plugin));
        subCommands.add(new PetCommand(plugin));
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            if(sender instanceof Player player){
                PlayerPetProfile.get(player.getUniqueId()).ifPresent(PetMenuController::new);
            }

            return true;
        }
        String cmd = args[0];
        getCmd(cmd).ifPresent(subCommand -> {
            subCommand.onCommand(sender,args);
        });
        return true;
    }

    public Optional<SubCommand> getCmd(String arg){
        for (SubCommand subCommand : subCommands) {
            if(subCommand.getName().equalsIgnoreCase(arg)){
                return Optional.of(subCommand);
            }
        }
        return Optional.empty();
    }
}
