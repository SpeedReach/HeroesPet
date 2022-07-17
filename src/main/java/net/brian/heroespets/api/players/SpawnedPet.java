package net.brian.heroespets.api.players;

import lombok.Getter;
import net.brian.heroespets.api.pets.Pet;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnedPet {

    @Getter
    private final Player owner;
    @Getter
    private final Pet pet;
    @Getter
    private final Entity petEntity;


    public SpawnedPet(@NotNull Pet pet,@NotNull Player owner,@NotNull Entity petEntity){
        this.pet = pet;
        this.owner = owner;
        this.petEntity = petEntity;
    }

    public void addExp(int exp){
        pet.addExp(exp);
    }


}
