package pl.autosmell;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import pl.autosmell.commands.CobblestoneCommand;
import pl.autosmell.commands.SmellCommand;
import pl.autosmell.data.DataHandler;
import pl.autosmell.events.Events;
import pl.autosmell.permissions.PermissionManager;

@Getter
public final class AutoSmell extends JavaPlugin {

    private static AutoSmell main;
    private DataHandler dataHandler;
    private PermissionManager permissionManager;

    @Override
    public void onEnable() {
        main = this;
        this.dataHandler = new DataHandler();
        dataHandler.loadConfig();
        this.permissionManager = new PermissionManager();
        getServer().getPluginManager().registerEvents(new Events(), this);
        getCommand("cobblestone").setExecutor(new CobblestoneCommand());
        getCommand("autosmell").setExecutor(new SmellCommand());
        getLogger().info("Loaded!");
    }

    @Override
    public void onDisable() {
        dataHandler.save();
        getLogger().info("Bye!");
    }

    public static AutoSmell getInstance() {
        return main;
    }
}
