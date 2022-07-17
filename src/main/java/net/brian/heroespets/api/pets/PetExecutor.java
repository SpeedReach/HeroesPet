package net.brian.heroespets.api.pets;

import net.brian.heroespets.api.players.SpawnedPet;

import java.util.UUID;

public interface PetExecutor {

    void cache(SpawnedPet spawnedPet);

    void unRegister(UUID player);

}
