package pl.autosmell.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.autosmell.AutoSmell;

public class SmellCommand implements CommandExecutor {

    private final AutoSmell plugin = AutoSmell.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("§cYou must be a player!");
            return true;
        }
        if(!sender.hasPermission(plugin.getPermissionManager().getPermission("autosmell.use.smell"))) {
            sender.sendMessage("§cYou don't have permission!");
            return true;
        }

        return true;
    }
}
