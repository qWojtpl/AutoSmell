package pl.autosmell.events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import pl.autosmell.AutoSmell;
import pl.autosmell.data.DataHandler;

public class Events implements Listener {

    private final AutoSmell plugin = AutoSmell.getInstance();
    private final DataHandler dataHandler = plugin.getDataHandler();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        if(event.isCancelled()) return;
        if(!event.getPlayer().hasPermission(plugin.getPermissionManager().getPermission("autosmell.use.cobblestone"))) return;
        if(!dataHandler.getCobblestoneData().containsKey(event.getPlayer().getName())) return;
        if(!dataHandler.getCobblestoneData().get(event.getPlayer().getName())) return;
        for(Material m : dataHandler.getCobblestoneBlocks()) {
            if(event.getBlock().getType().equals(m)) {
                event.setCancelled(true);
                event.getBlock().setType(Material.AIR);
            }
        }
    }

}
