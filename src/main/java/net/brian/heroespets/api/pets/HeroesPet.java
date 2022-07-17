package net.brian.heroespets.api.pets;

import io.lumine.mythic.api.MythicProvider;
import io.lumine.mythic.api.exceptions.InvalidMobTypeException;
import io.lumine.mythic.bukkit.MythicBukkit;
import lombok.Getter;
import lombok.Setter;
import net.brian.heroespets.HeroesPets;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public abstract class HeroesPet {


    @Getter
    private final String id,mythicMob;

    @Getter
    @Setter
    protected String display = "";


    public HeroesPet(String id,String mythicMob){
        this.mythicMob = mythicMob;
        this.id = id;
    }

    public abstract String getEntityName(@Nullable Player player,Pet pet);

    public abstract ItemStack getIcon(@Nullable Player player,Pet pet);

    public abstract void tick(Player owner,Pet pet,long tickCounter);

    public Entity spawnPetEntity(Pet pet, Location loc) throws InvalidMobTypeException {
        Entity entity = MythicBukkit.inst().getAPIHelper().spawnMythicMob(mythicMob,loc,pet.getLevel());
        entity.setInvulnerable(true);
        return entity;
    }


}
