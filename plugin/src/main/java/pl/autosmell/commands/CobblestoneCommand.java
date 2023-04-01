package pl.autosmell.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.autosmell.AutoSmell;
import pl.autosmell.data.DataHandler;

public class CobblestoneCommand implements CommandExecutor {

    private final AutoSmell plugin = AutoSmell.getInstance();
    private final DataHandler dh = plugin.getDataHandler();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(dh.getPrefix() + "§cYou must be a player!");
            return true;
        }
        if(!sender.hasPermission(plugin.getPermissionManager().getPermission("autosmell.use.cobblestone"))) {
            sender.sendMessage(dh.getPrefix() + dh.getNoPermission());
            return true;
        }
        boolean currentCobblestone = false;
        if(dh.getCobblestoneData().containsKey(sender.getName())) {
            if(dh.getCobblestoneData().get(sender.getName())) currentCobblestone = true;
        }
        dh.setCobblestone(sender.getName(), !currentCobblestone);
        currentCobblestone = !currentCobblestone;
        if(!currentCobblestone) {
            sender.sendMessage(dh.getPrefix() + dh.getSetCobblestone() + " " + dh.getSetOff());
        } else {
            sender.sendMessage(dh.getPrefix() + dh.getSetCobblestone() + " " + dh.getSetOn());
        }
        return true;
    }
}
