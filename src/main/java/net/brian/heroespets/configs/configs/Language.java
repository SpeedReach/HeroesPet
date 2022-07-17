package net.brian.heroespets.configs.configs;

import net.brian.playerdatasync.config.SpigotConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class Language extends SpigotConfig {


    // #pet_name#
    public static String SPAWN_PET;
    public static String DESPAWN_PET;
    public static String BAG_FULL;
    public static String TELEPORTED;





    public Language(JavaPlugin plugin) {
        super(plugin, "language.yml");
        reload();
    }


    @Override
    public void reload(){
        SPAWN_PET = configuration.getString("spawn_pet","");
        DESPAWN_PET = configuration.getString("despawn_pet","");
        BAG_FULL = configuration.getString("bag_full","");
        TELEPORTED = configuration.getString("teleport","");
    }


}
