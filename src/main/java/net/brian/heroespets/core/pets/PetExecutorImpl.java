package net.brian.heroespets.core.pets;

import io.lumine.mythic.api.volatilecode.handlers.VolatileAIHandler;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import net.brian.heroespets.HeroesPets;
import net.brian.heroespets.api.pets.PetExecutor;
import net.brian.heroespets.api.players.SpawnedPet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.*;

public class PetExecutorImpl implements PetExecutor  {

    private final HeroesPets plugin;
    private final VolatileAIHandler aiHandler;

    private final HashMap<UUID,SpawnedPet> spawnedPets;


    public PetExecutorImpl(HeroesPets plugin){
        this.plugin = plugin;
        spawnedPets = new HashMap<>();
        aiHandler = MythicBukkit.inst().getVolatileCodeHandler().getAIHandler();
        startScheduler();
    }


    @Override
    public void cache(SpawnedPet spawnedPet) {
        spawnedPets.put(spawnedPet.getOwner().getUniqueId(),spawnedPet);
    }

    @Override
    public void unRegister(UUID player) {
        spawnedPets.remove(player);
    }


    private void startScheduler(){
        long[] tick = {0};
        Bukkit.getScheduler().runTaskTimer(plugin,()->{
            tick[0]++;
            Iterator<Map.Entry<UUID,SpawnedPet>> it = spawnedPets.entrySet().iterator();
            while (it.hasNext()){
                Map.Entry<UUID,SpawnedPet> entry = it.next();
                SpawnedPet spawnedPet = entry.getValue();
                if(!spawnedPet.getPetEntity().isValid()){
                    it.remove();
                    continue;
                }
                Player player = spawnedPet.getOwner();
                spawnedPet.getPet().getType().tick(player,spawnedPet.getPet(),tick[0]);
                Location targetLoc = player.getLocation();
                Location mobLoc = spawnedPet.getPetEntity().getLocation();
                if(!targetLoc.getWorld().equals(mobLoc.getWorld()) || targetLoc.distance(mobLoc) > 10){
                    spawnedPet.getPetEntity().teleport(player);
                }
                else {
                    aiHandler.navigateToLocation(
                            BukkitAdapter.adapt(spawnedPet.getPetEntity()),
                            BukkitAdapter.adapt(targetLoc),
                            1f
                    );
                }
            }
        },0,1);
    }

}
