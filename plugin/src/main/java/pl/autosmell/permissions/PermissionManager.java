package pl.autosmell.permissions;

import org.bukkit.permissions.Permission;
import pl.autosmell.AutoSmell;
import pl.autosmell.data.DataHandler;

import java.util.HashMap;

public class PermissionManager {

    private final HashMap<String, Permission> permissions = new HashMap<>();
    private final DataHandler dataHandler = AutoSmell.getInstance().getDataHandler();

    public PermissionManager() {
        registerAll();
    }

    public void registerAll() {
        clearPermissions();
        registerPermission(dataHandler.getAutoSmellPermission(), "Use autosmell feature");
        registerPermission(dataHandler.getNoCobblestonePermission(), "Use no cobblestone feature");
        registerPermission(dataHandler.getManagePermission(), "Manage AutoSmell plugin");
    }

    public void registerPermission(String permission, String description) {
        Permission perm = new Permission(permission, description);
        permissions.put(permission, perm);
    }

    public void clearPermissions() {
        permissions.clear();
    }

    public Permission getPermission(String permission) {
        return permissions.get(permission);
    }

}
