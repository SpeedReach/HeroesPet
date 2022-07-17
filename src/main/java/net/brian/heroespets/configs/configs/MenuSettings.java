package net.brian.heroespets.configs.configs;

import me.clip.placeholderapi.PlaceholderAPI;
import net.brian.playerdatasync.config.SpigotConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.BatchUpdateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class MenuSettings extends SpigotConfig {

    public static String MENU_TITLE;
    public static String CURRENT_PET_LORE;
    public static int PETS_PER_PAGE;

    private static char PET_CHAR,TP_CHAR,DESPAWN_CHAR;

    public static final HashMap<Character,List<Integer>> OUTLAY_MAP = new HashMap<>();
    public static final HashMap<Character,Icon> ICONS = new HashMap<>();

    public MenuSettings(JavaPlugin plugin) {
        super(plugin, "pet_menu.yml");
        reload();
    }


    public static List<Integer> TP_SLOTS(){
        List<Integer> list = OUTLAY_MAP.get(TP_CHAR);
        if(list == null) return new ArrayList<>();
        return list;
    }

    public static List<Integer> DESPAWN_SLOTS() {
        List<Integer> list = OUTLAY_MAP.get(DESPAWN_CHAR);
        if(list == null) return new ArrayList<>();
        return list;
    }

    public static List<Integer> PET_SLOTS(){
        List<Integer> list = OUTLAY_MAP.get(PET_CHAR);
        if(list == null) return new ArrayList<>();
        return list;
    }

    @Override
    public void reload(){
        loadFile();
        OUTLAY_MAP.clear();
        ICONS.clear();
        MENU_TITLE = configuration.getString("title","");
        CURRENT_PET_LORE = configuration.getString("current_pet","");
        List<String> outlay = configuration.getStringList("elements.outlay");
        int[] row = {0};
        for (int i=0;i<outlay.size();i++){
            String line = outlay.get(row[0]);
            for(int j=0;j<9;j++){
                int finalJ = j;
                OUTLAY_MAP.compute(line.charAt(j),(k, v)->{
                    if(v == null) v = new ArrayList<>();
                    v.add(row[0]*9+ finalJ);
                    return v;
                });
            }
            row[0]++;
        }
        PET_CHAR = configuration.getString("elements.pet","p").charAt(0);
        TP_CHAR = configuration.getString("elements.teleport","t").charAt(0);
        DESPAWN_CHAR = configuration.getString("elements.despawn","d").charAt(0);
        ConfigurationSection section = configuration.getConfigurationSection("elements.icons");
        if(section != null){
            for (String key : section.getKeys(false)) {
                ICONS.put(key.charAt(0),new Icon(section.getConfigurationSection(key)));
            }
        }

        PETS_PER_PAGE = OUTLAY_MAP.get(PET_CHAR).size();
    }


    public static class Icon{

        String display;
        Material material;
        List<String> lores;

        public Icon(ConfigurationSection section){
            display = section.getString("display");
            material = Material.valueOf(section.getString("material"));
            lores = section.getStringList("lore");
        }

        public ItemStack getItem(Player player){
            ItemStack itemStack = new ItemStack(material);
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(PlaceholderAPI.setPlaceholders(player,display));
            meta.setLore(PlaceholderAPI.setPlaceholders(player,lores));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,ItemFlag.HIDE_ENCHANTS);
            itemStack.setItemMeta(meta);
            return itemStack;
        }

    }

}
