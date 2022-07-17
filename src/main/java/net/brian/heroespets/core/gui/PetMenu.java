package net.brian.heroespets.core.gui;

import net.brian.heroespets.api.pets.Pet;
import net.brian.heroespets.api.players.PlayerPetProfile;
import net.brian.heroespets.api.players.SpawnedPet;
import net.brian.heroespets.configs.configs.MenuSettings;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PetMenu {

    private final PlayerPetProfile profile;
    private final Inventory inventory;

    public PetMenu(PlayerPetProfile profile){
        this.profile = profile;
        inventory = Bukkit.createInventory(null,54, MenuSettings.MENU_TITLE);
        refresh();
    }


    public void refresh(){
        inventory.clear();
        generateOutLay();
        int index = 0;
        Pet currentPet = null;
        boolean hasSpawnedPet = false;
        Optional<SpawnedPet> spawnedPetOptional = profile.getSpawnedPet();
        if(spawnedPetOptional.isPresent()){
            currentPet = spawnedPetOptional.get().getPet();
            hasSpawnedPet = true;
        }

        for (Pet pet : profile.getPets()) {
            if(index < MenuSettings.PETS_PER_PAGE){
                ItemStack itemStack = pet.getType().getIcon(profile.getPlayer(),pet);
                if(hasSpawnedPet && currentPet.equals(pet)){
                    addSpawnedLore(itemStack);
                }
                inventory.setItem(index,itemStack);
            }
            index++;
        }
    }

    private void addSpawnedLore(ItemStack itemStack){
        ItemMeta meta = itemStack.getItemMeta();
        meta.addEnchant(Enchantment.ARROW_DAMAGE,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        List<String> lore = meta.getLore();
        if(lore == null) lore = new ArrayList<>();
        lore.add("");
        lore.add(MenuSettings.CURRENT_PET_LORE);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    private void generateOutLay(){
        MenuSettings.OUTLAY_MAP.forEach((c,slots)->{
            MenuSettings.Icon icon = MenuSettings.ICONS.get(c);
            if(icon != null){
                ItemStack itemStack= icon.getItem(profile.getPlayer());
                slots.forEach(slot->inventory.setItem(slot,itemStack));
            }
        });
    }

    public Inventory getView(){
        return inventory;
    }

}
