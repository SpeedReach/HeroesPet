package net.brian.heroespets.utils.namespaces;


import net.brian.heroespets.HeroesPets;
import net.brian.heroespets.api.pets.HeroesPet;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Wither;

import javax.naming.Name;

public class PetNameSpaces {

    //Entities
    public static final NamespacedKey OWNER = new NamespacedKey(HeroesPets.getPlugin(),"owner");


    //Items
    public static final NamespacedKey TYPE = new NamespacedKey(HeroesPets.getPlugin(),"type");
    public static final NamespacedKey LEVEL = new NamespacedKey(HeroesPets.getPlugin(),"level");
    public static final NamespacedKey TIER = new NamespacedKey(HeroesPets.getPlugin(),"tier");
    public static final NamespacedKey EXP = new NamespacedKey(HeroesPets.getPlugin(),"exp");
    public static final NamespacedKey UNSTACK_ABLE = new NamespacedKey(HeroesPets.getPlugin(),"unstacked");

}
