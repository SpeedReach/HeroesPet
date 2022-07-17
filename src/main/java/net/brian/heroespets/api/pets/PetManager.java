package net.brian.heroespets.api.pets;

import net.brian.heroespets.api.players.SpawnedPet;

import java.util.Optional;

public interface PetManager {

    void register(HeroesPet heroesPet);

    void clear();

    void refresh();

    Optional<HeroesPet> getHeroesPet(String type);

}
