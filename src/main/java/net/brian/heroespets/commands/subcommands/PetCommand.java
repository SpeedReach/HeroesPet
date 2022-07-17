package net.brian.heroespets.commands.subcommands;

import net.brian.heroespets.HeroesPets;
import net.brian.heroespets.api.pets.Pet;
import net.brian.heroespets.api.players.PlayerPetProfile;
import net.brian.heroespets.commands.SubCommand;
import net.brian.playerdatasync.util.ReturnItems;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PetCommand extends SubCommand {

    //hp pet give player <id> <item,direct>

    public PetCommand(HeroesPets plugin) {
        super(plugin,"pet");

    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(args.length < 4) return;
        String type = args[3];
        Player player = Bukkit.getPlayer(args[2]);
        if(player == null) {
            sender.sendMessage("找不到玩家 "+args[2]);
            return;
        }
        plugin.getPetManager().getHeroesPet(type).ifPresent(heroesPet->{
            Pet pet = new Pet(heroesPet);
            if(args.length > 4) {
                if(args[4].equalsIgnoreCase("direct")){
                    PlayerPetProfile.get(player.getUniqueId()).ifPresent(profile -> profile.givePet(pet));
                    return;
                }
            }
            ReturnItems.process(player,pet.transferToItem());
            sender.sendMessage("給予"+player.getName()+" 寵物 "+heroesPet.getDisplay());
            return;
        });
    }
}
