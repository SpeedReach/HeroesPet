package net.brian.heroespets;

import net.brian.heroespets.api.pets.PetExecutor;
import net.brian.heroespets.api.pets.PetManager;
import net.brian.heroespets.api.players.PlayerPetProfile;
import net.brian.heroespets.commands.CommandManager;
import net.brian.heroespets.configs.ConfigManager;
import net.brian.heroespets.configs.configs.Language;
import net.brian.heroespets.configs.configs.MenuSettings;
import net.brian.heroespets.core.listeners.PetListeners;
import net.brian.heroespets.core.listeners.RedeemPet;
import net.brian.heroespets.core.pets.PetExecutorImpl;
import net.brian.heroespets.core.pets.PetManagerImpl;
import net.brian.playerdatasync.PlayerDataSync;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HeroesPets extends JavaPlugin {

    private static HeroesPets plugin;

    private PetManager petManager;
    private PetExecutor petExecutor;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        PlayerDataSync.getInstance().register("heroes_pet", PlayerPetProfile.class);
        petManager = new PetManagerImpl(this);
        petExecutor = new PetExecutorImpl(this);
        configManager = new ConfigManager(this);
        new CommandManager(this);
        registerListeners();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new PetListeners(),this);
        Bukkit.getPluginManager().registerEvents(new RedeemPet(),this);
    }


    public static HeroesPets getPlugin() {
        return plugin;
    }

    public PetManager getPetManager() {
        return petManager;
    }

    public PetExecutor getPetExecutor() {
        return petExecutor;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
