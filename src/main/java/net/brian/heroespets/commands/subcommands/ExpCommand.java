package net.brian.heroespets.commands.subcommands;

import net.brian.heroespets.HeroesPets;
import net.brian.heroespets.api.players.PlayerPetProfile;
import net.brian.heroespets.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ExpCommand extends SubCommand {

    public ExpCommand(HeroesPets plugin) {
        super(plugin, "exp");
    }


    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(!sender.hasPermission("heroespet.admin")) return;
        //hp exp give player 20
        if(args.length < 4) return;
        Player player = Bukkit.getPlayer(args[2]);
        int amount = Integer.parseInt(args[3]);
        if(player == null) return;
        PlayerPetProfile.get(player.getUniqueId()).flatMap(PlayerPetProfile::getSpawnedPet)
                .ifPresent(spawnedPet -> spawnedPet.addExp(amount));

    }
}
