package net.brian.heroespets.api.players;

import io.lumine.mythic.api.exceptions.InvalidMobTypeException;
import lombok.Getter;
import net.brian.heroespets.HeroesPets;
import net.brian.heroespets.api.pets.Pet;
import net.brian.heroespets.configs.configs.Language;
import net.brian.heroespets.utils.Utils;
import net.brian.playerdatasync.PlayerDataSync;
import net.brian.playerdatasync.data.PlayerData;
import net.brian.playerdatasync.data.gson.PostProcessable;
import net.brian.playerdatasync.data.gson.QuitProcessable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerPetProfile extends PlayerData implements PostProcessable, QuitProcessable {

    private List<Pet> pets;

    @Getter
    private int size = 45;

    private transient SpawnedPet spawnedPet;

    @Getter
    private transient Player player;

    public PlayerPetProfile(UUID uuid){
        super(uuid);
        pets = new ArrayList<>();
    }



    public SpawnResult spawn(Pet pet){
        if(pet == null) return SpawnResult.PET_IS_NULL;
        if(spawnedPet != null && spawnedPet.getPet().equals(pet)) return SpawnResult.ALREADY_SPAWNED;
        despawn();
        Entity entity;
        try {
            entity = pet.getType().spawnPetEntity(pet,player.getLocation());
            entity.setCustomName(pet.getType().getEntityName(player,pet));
            entity.setCustomNameVisible(true);
            Utils.setPersistentOwner(entity,uuid);

        } catch (InvalidMobTypeException e) {
            e.printStackTrace();
            return SpawnResult.INVALID_MOB_TYPE;
        }
        spawnedPet = new SpawnedPet(pet,player,entity);
        HeroesPets.getPlugin().getPetExecutor().cache(spawnedPet);
        return SpawnResult.SUCCESS;
    }

    public SpawnResult spawn(int index){
        if(pets.size() <= index) return SpawnResult.INDEX_OUT_OF_BOUNDS;
        Pet pet = pets.get(index);
        return spawn(pet);
    }

    public void despawn(){
        if(spawnedPet != null && spawnedPet.getPetEntity().isValid()){
            spawnedPet.getPetEntity().remove();
            String petName = spawnedPet.getPet().getType().getDisplay();
            player.sendMessage(Language.DESPAWN_PET.replace("#pet_name#",petName));
            HeroesPets.getPlugin().getPetExecutor().unRegister(uuid);
            spawnedPet = null;
        }
    }

    public Optional<SpawnedPet> getSpawnedPet(){
        if(spawnedPet != null && spawnedPet.getPetEntity().isValid()){
            return Optional.of(spawnedPet);
        }
        return Optional.empty();
    }

    public void givePet(Pet pet){
        pets.add(pet);
    }

    public void removePet(Pet pet){
        pets.remove(pet);
    }

    public List<Pet> getPets() {
        return new ArrayList<>(pets);
    }

    @Override
    public void onQuit() {
        if(spawnedPet != null){
            spawnedPet.getPetEntity().remove();
        }
    }

    @Override
    public void gsonPostDeserialize(Player player) {
        this.player = player;
    }


    public static Optional<PlayerPetProfile> get(UUID uuid){
        return PlayerDataSync.getInstance().getData(uuid,PlayerPetProfile.class);
    }
}
