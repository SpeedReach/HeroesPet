package net.brian.heroespets.core.listeners;

import net.brian.heroespets.api.players.PlayerPetProfile;
import net.brian.heroespets.utils.namespaces.PetNameSpaces;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class PetListeners implements Listener {


    @EventHandler
    public void onDeath(EntityDeathEvent event){
        String owner = event.getEntity().getPersistentDataContainer().get(PetNameSpaces.OWNER,PersistentDataType.STRING);
        if(owner != null){
            PlayerPetProfile.get(UUID.fromString(owner)).ifPresent(PlayerPetProfile::despawn);
        }
    }

}
