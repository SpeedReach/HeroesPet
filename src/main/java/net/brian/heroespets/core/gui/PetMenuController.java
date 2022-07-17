package net.brian.heroespets.core.gui;

import net.brian.heroespets.HeroesPets;
import net.brian.heroespets.api.pets.Pet;
import net.brian.heroespets.api.players.PlayerPetProfile;
import net.brian.heroespets.configs.configs.Language;
import net.brian.heroespets.configs.configs.MenuSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PetMenuController implements Listener {

    private final PlayerPetProfile profile;
    private final Player viewer;
    private final PetMenu petMenu;

    public PetMenuController(PlayerPetProfile profile){
        this.profile = profile;
        petMenu = new PetMenu(profile);
        viewer = profile.getPlayer();
        viewer.openInventory(petMenu.getView());
        Bukkit.getPluginManager().registerEvents(this,HeroesPets.getPlugin());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(!event.getWhoClicked().equals(viewer)) return;
        event.setCancelled(true);
        Inventory clickedInv = event.getClickedInventory();
        if(clickedInv == null) return;
        if(!clickedInv.getType().equals(InventoryType.CHEST)) return;

        int clickedPetIndex = MenuSettings.PET_SLOTS().indexOf(event.getSlot());
        if(clickedPetIndex != -1){
            List<Pet> pets = profile.getPets();
            if(pets.size() <= clickedPetIndex) return;
            Pet pet = pets.get(clickedPetIndex);
            if(event.isShiftClick()){
                ItemStack itemStack = pet.transferToItem();
                if(viewer.getInventory().addItem(itemStack).isEmpty()){
                    profile.removePet(pet);
                    petMenu.refresh();
                }
                else{
                    viewer.sendMessage(Language.BAG_FULL);
                }
            }
            else {
                profile.getSpawnedPet().ifPresentOrElse(spawnedPet ->{
                    if(spawnedPet.getPet().equals(pet)){
                        profile.despawn();
                        petMenu.refresh();
                    }
                    else{
                        profile.despawn();
                        profile.spawn(pet);
                        petMenu.refresh();
                    }
                },()->{
                    profile.spawn(pet);
                    petMenu.refresh();
                });
            }
            return;
        }

        if(MenuSettings.TP_SLOTS().contains(event.getSlot())){
            profile.getSpawnedPet().ifPresent(spawnedPet -> {
                spawnedPet.getPetEntity().teleport(viewer.getLocation());
                String msg = Language.TELEPORTED.replace("#pet_name#",spawnedPet.getPet().getType().getDisplay());
                viewer.sendMessage(msg);
            });
            return;
        }

        if(MenuSettings.DESPAWN_SLOTS().contains(event.getSlot())){
            profile.despawn();
            petMenu.refresh();
            return;
        }

    }



}
