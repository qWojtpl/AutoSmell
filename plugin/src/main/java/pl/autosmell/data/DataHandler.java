package pl.autosmell.data;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.autosmell.AutoSmell;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Getter
public class DataHandler {

    private final AutoSmell plugin = AutoSmell.getInstance();
    private String prefix;
    private String noPermission;
    private String setAutosmell;
    private String setCobblestone;
    private String setOn;
    private String setOff;
    private String autoSmellPermission;
    private String noCobblestonePermission;
    private String managePermission;
    private final HashMap<String, Boolean> smellData = new HashMap<>();
    private final HashMap<String, Boolean> cobblestoneData = new HashMap<>();
    private final List<Material> cobblestoneBlocks = new ArrayList<>();

    public void loadConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if(!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(configFile);
        prefix = yml.getString("config.prefix");
        noPermission = yml.getString("config.noPermission");
        setAutosmell = yml.getString("config.setAutoSmell");
        setCobblestone = yml.getString("config.setCobblestone");
        setOn = yml.getString("config.setOn");
        setOff = yml.getString("config.setOff");
        autoSmellPermission = yml.getString("config.autoSmellPermission");
        noCobblestonePermission = yml.getString("config.noCobblestonePermission");
        managePermission = yml.getString("config.managePermission");
        List<String> potList = yml.getStringList("config.cobblestoneBlocks");
        for(String block : potList) {
            if(Material.getMaterial(block) == null) {
                plugin.getLogger().severe("Cannot find material: " + block);
            } else {
                cobblestoneBlocks.add(Material.getMaterial(block));
            }
        }
        File dir = new File(plugin.getDataFolder(), "/data/");
        File smellFile = new File(plugin.getDataFolder(), "/data/smellData.yml");
        File cobblestoneFile = new File(plugin.getDataFolder(), "/data/cobblestoneData.yml");
        dir.mkdir();
        if(!smellFile.exists()) {
            try {
                smellFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Cannot create smellData.yml");
            }
        }
        if(!cobblestoneFile.exists()) {
            try {
                cobblestoneFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Cannot create cobblestoneData.yml");
            }
        }
        YamlConfiguration smellYML = YamlConfiguration.loadConfiguration(smellFile);
        ConfigurationSection section = smellYML.getConfigurationSection("data");
        if(section != null) {
            for(String player : section.getKeys(false)) {
                if(smellYML.getBoolean("data." + player)) {
                    smellData.put(player, true);
                }
            }
        }
        YamlConfiguration cobblestoneYML = YamlConfiguration.loadConfiguration(cobblestoneFile);
        section = cobblestoneYML.getConfigurationSection("data");
        if(section != null) {
            for(String player : section.getKeys(false)) {
                if(cobblestoneYML.getBoolean("data." + player)) {
                    cobblestoneData.put(player, true);
                }
            }
        }
    }

    public void setSmell(String playerName, boolean b) {
        if(b) {
            getSmellData().put(playerName, b);
        } else {
            getSmellData().remove(playerName);
        }
    }

    public void setCobblestone(String playerName, boolean b) {
        if(b) {
            getCobblestoneData().put(playerName, b);
        } else {
            getCobblestoneData().remove(playerName);
        }
    }

    @SneakyThrows
    public void save() {
        File smellFile = new File(plugin.getDataFolder(), "/data/smellData.yml");
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(smellFile);
        for(String player : getSmellData().keySet()) {
            if(getSmellData().get(player)) {
                yml.set("data." + player, true);
            } else {
                yml.set("data." + player, null);
            }
        }
        yml.save(smellFile);
        File cobblestoneFile = new File(plugin.getDataFolder(), "/data/cobblestoneData.yml");
        yml = YamlConfiguration.loadConfiguration(cobblestoneFile);
        for(String player : getCobblestoneData().keySet()) {
            if(getCobblestoneData().get(player)) {
                yml.set("data." + player, true);
            } else {
                yml.set("data." + player, null);
            }
        }
        yml.save(cobblestoneFile);
    }

    public void reload() {
        save();
        plugin.
        loadConfig();
    }

}
