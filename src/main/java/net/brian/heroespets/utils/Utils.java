package net.brian.heroespets.utils;

import net.brian.heroespets.utils.namespaces.PetNameSpaces;
import org.bukkit.entity.Entity;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class Utils {

    public static void setPersistentOwner(Entity entity, UUID owner){
        entity.getPersistentDataContainer().set(PetNameSpaces.OWNER, PersistentDataType.STRING,owner.toString());
    }

}
