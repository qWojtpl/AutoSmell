package pl.autosmell.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.autosmell.AutoSmell;
import pl.autosmell.data.DataHandler;

public class SmellCommand implements CommandExecutor {

    private final AutoSmell plugin = AutoSmell.getInstance();
    private final DataHandler dh = plugin.getDataHandler();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(dh.getPrefix() + "§cYou must be a player!");
            return true;
        }
        if(!sender.hasPermission(plugin.getPermissionManager().getPermission("autosmell.use.smell"))) {
            sender.sendMessage(dh.getPrefix() + dh.getNoPermission());
            return true;
        }
        boolean currentSmell = false;
        if(dh.getSmellData().containsKey(sender.getName())) {
            if(dh.getSmellData().get(sender.getName())) currentSmell = true;
        }
        dh.setSmell(sender.getName(), !currentSmell);
        currentSmell = !currentSmell;
        if(!currentSmell) {
            sender.sendMessage(dh.getPrefix() + dh.getSetAutosmell() + " " + dh.getSetOff());
        } else {
            sender.sendMessage(dh.getPrefix() + dh.getSetAutosmell() + " " + dh.getSetOn());
        }
        return true;
    }
}
