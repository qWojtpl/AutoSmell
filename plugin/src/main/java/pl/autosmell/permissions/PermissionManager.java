package pl.autosmell.permissions;

import org.bukkit.permissions.Permission;

import java.util.HashMap;

public class PermissionManager {

    private final HashMap<String, Permission> permissions = new HashMap<>();

    public PermissionManager() {
        registerPermission("autosmell.use.smell", "Use autosmell feature");
        registerPermission("autosmell.use.cobblestone", "Use no cobblestone feature");
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
