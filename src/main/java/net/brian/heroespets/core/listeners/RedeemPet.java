package net.brian.heroespets.core.listeners;

import net.brian.heroespets.api.pets.Pet;
import net.brian.heroespets.api.players.PlayerPetProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Optional;


public class RedeemPet implements Listener {


    @EventHandler(ignoreCancelled = true)
    public void onClick(PlayerInteractEvent event){
        ItemStack itemStack = event.getItem();
        if(itemStack != null){
            Optional<Pet> pet = Pet.fromItem(itemStack);
            if(pet.isEmpty()) return;
            event.setCancelled(true);
            PlayerPetProfile.get(event.getPlayer().getUniqueId()).ifPresent(profile->{
                if(profile.getPets().size() < profile.getSize()){
                    profile.givePet(pet.get());
                    itemStack.setAmount(itemStack.getAmount()-1);
                }
            });
        }
    }


}
