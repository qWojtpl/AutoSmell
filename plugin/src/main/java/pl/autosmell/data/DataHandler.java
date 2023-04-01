package pl.autosmell.data;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.autosmell.AutoSmell;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Getter
public class DataHandler {

    private final AutoSmell plugin = AutoSmell.getInstance();
    private String prefix;
    private String noPermission;
    private final HashMap<String, Boolean> smellData = new HashMap<>();
    private final HashMap<String, Boolean> cobblestoneData = new HashMap<>();

    public void loadConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if(!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        YamlConfiguration yml = YamlConfiguration.loadConfiguration(configFile);
        prefix = yml.getString("config.prefix");
        noPermission = yml.getString("config.noPermission");
        if(prefix == null) {
            prefix = "§2[§eAS§2]";
        }
        if(noPermission == null) {
            noPermission = "§cYou don't have permission!";
        }
        File smellFile = new File(plugin.getDataFolder() + "/data/", "smellData.yml");
        File cobblestoneFile = new File(plugin.getDataFolder() + "/data/", "cobblestoneData.yml");
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
    }

}
