package net.brian.heroespets.core.pets;

import net.brian.heroespets.api.pets.HeroesPet;
import net.brian.heroespets.api.pets.Pet;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class InvalidPet extends HeroesPet {

    ItemStack icon;

    public InvalidPet(String id) {
        super("invalid","");
        icon = new ItemStack(Material.STONE);
        ItemMeta meta = icon.getItemMeta();
        meta.setDisplayName("Invalid Pet: "+id);
        List<String> lore = new ArrayList<>();
        lore.add("找不到此id的寵物");
        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    @Override
    public String getEntityName(@Nullable Player player, Pet pet) {
        return "";
    }

    @Override
    public ItemStack getIcon(@Nullable Player player, Pet pet) {
        return icon;
    }

    @Override
    public void tick(Player owner, Pet pet, long tickCounter) {
    }
}
