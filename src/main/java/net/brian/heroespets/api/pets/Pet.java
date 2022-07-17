package net.brian.heroespets.api.pets;

import lombok.Getter;
import net.brian.heroespets.HeroesPets;
import net.brian.heroespets.configs.PetLeveling;
import net.brian.heroespets.core.pets.InvalidPet;
import net.brian.heroespets.utils.namespaces.PetNameSpaces;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

public class Pet {

    @Getter
    private int level = 1, tier = 1;
    private int exp = 0;
    private String petType;

    @Getter
    private HeroesPet type;

    public Pet(@NotNull HeroesPet type){
        this.type = type;
        petType = type.getId();
    }



    public void addExp(int amount){
        amount += exp;
        while (PetLeveling.getMaxLevel(tier) > level){
            int required = PetLeveling.getRequiredExp(tier,level);
            if(amount < required) break;
            amount -= required;
            level++;
        }
        exp = amount;
    }

    public ItemStack transferToItem(){
        ItemStack itemStack = type.getIcon(null,this);
        ItemMeta meta = itemStack.getItemMeta();
        PersistentDataContainer dataContainer = meta.getPersistentDataContainer();
        dataContainer.set(PetNameSpaces.EXP, PersistentDataType.INTEGER,exp);
        dataContainer.set(PetNameSpaces.TIER, PersistentDataType.INTEGER,tier);
        dataContainer.set(PetNameSpaces.TYPE, PersistentDataType.STRING,type.getId());
        dataContainer.set(PetNameSpaces.LEVEL, PersistentDataType.INTEGER,level);
        dataContainer.set(PetNameSpaces.UNSTACK_ABLE,PersistentDataType.STRING, UUID.randomUUID().toString());
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static Optional<Pet> fromItem(ItemStack itemStack){
        if(itemStack == null || !itemStack.hasItemMeta()) return Optional.empty();
        PersistentDataContainer dataContainer = itemStack.getItemMeta().getPersistentDataContainer();
        String type = dataContainer.get(PetNameSpaces.TYPE, PersistentDataType.STRING);
        if(type == null) return Optional.empty();
        Optional<HeroesPet> heroesPetOpt = HeroesPets.getPlugin().getPetManager().getHeroesPet(type);
        HeroesPet heroesPet;
        if(heroesPetOpt.isEmpty()) heroesPet = new InvalidPet(type);
        else heroesPet = heroesPetOpt.get();
        int exp = dataContainer.getOrDefault(PetNameSpaces.EXP,PersistentDataType.INTEGER,0);
        int tier = dataContainer.getOrDefault(PetNameSpaces.TIER,PersistentDataType.INTEGER,1);
        int level = dataContainer.getOrDefault(PetNameSpaces.LEVEL,PersistentDataType.INTEGER,1);
        Pet pet = new Pet(heroesPet);
        pet.level = level;
        pet.exp = exp;
        pet.tier = tier;
        return Optional.of(pet);
    }

    public void refreshUpStream(){
        HeroesPets.getPlugin().getPetManager().getHeroesPet(petType).ifPresentOrElse(heroesPet -> {
            this.type = heroesPet;
        },()->{
            this.type = new InvalidPet(petType);
        });
    }

}
