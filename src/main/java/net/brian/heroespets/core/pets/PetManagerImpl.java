package net.brian.heroespets.core.pets;

import net.brian.heroespets.HeroesPets;
import net.brian.heroespets.api.pets.HeroesPet;
import net.brian.heroespets.api.pets.Pet;
import net.brian.heroespets.api.pets.PetManager;
import net.brian.heroespets.api.players.PlayerPetProfile;
import net.brian.playerdatasync.PlayerDataSync;

import java.net.PortUnreachableException;
import java.util.HashMap;
import java.util.Optional;

public class PetManagerImpl implements PetManager{

    private final HeroesPets plugin;
    private final HashMap<String,HeroesPet> pets;

    public PetManagerImpl(HeroesPets plugin){
        this.plugin = plugin;
        this.pets = new HashMap<>();
    }

    @Override
    public void register(HeroesPet heroesPet) {
        pets.put(heroesPet.getId(),heroesPet);
    }

    @Override
    public void clear() {
        pets.clear();
    }

    @Override
    public void refresh() {
        PlayerDataSync.getInstance().getPlayerDatas()
                .tableMap.get(PlayerPetProfile.class)
                .cacheData.values().forEach(profile->{
                    PlayerPetProfile petProfile = (PlayerPetProfile) profile;
                    petProfile.getPets().forEach(Pet::refreshUpStream);
                });
    }


    @Override
    public Optional<HeroesPet> getHeroesPet(String type) {
        return Optional.ofNullable(pets.get(type));
    }
}
