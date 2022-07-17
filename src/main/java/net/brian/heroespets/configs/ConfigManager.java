package net.brian.heroespets.configs;

import net.brian.heroespets.HeroesPets;
import net.brian.heroespets.configs.configs.Language;
import net.brian.heroespets.configs.configs.MenuSettings;
import net.brian.playerdatasync.config.SpigotConfig;

import java.util.ArrayList;
import java.util.List;

public class ConfigManager {

    List<SpigotConfig> spigotConfigs;

    public ConfigManager(HeroesPets plugin){
        spigotConfigs = new ArrayList<>();
        spigotConfigs.add(new Language(plugin));
        spigotConfigs.add(new MenuSettings(plugin));

    }

    public void reload(){
        spigotConfigs.forEach(SpigotConfig::reload);
    }

}
