package pl.autosmell.events;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.autosmell.AutoSmell;
import pl.autosmell.data.DataHandler;

import java.util.Random;

public class Events implements Listener {

    private final AutoSmell plugin = AutoSmell.getInstance();
    private final DataHandler dataHandler = plugin.getDataHandler();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent event) {
        if(event.isCancelled()) return;
        if(!event.getPlayer().hasPermission(plugin.getPermissionManager().getPermission(dataHandler.getNoCobblestonePermission()))) return;
        if(!dataHandler.getCobblestoneData().containsKey(event.getPlayer().getName())) return;
        if(!dataHandler.getCobblestoneData().get(event.getPlayer().getName())) return;
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        updateDurability(item, event.getPlayer());
        for(Material m : dataHandler.getCobblestoneBlocks()) {
            if(event.getBlock().getType().equals(m)) {
                event.setCancelled(true);
                event.getBlock().setType(Material.AIR);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreakOre(BlockBreakEvent event) {
        if(event.isCancelled()) return;
        if(!event.getPlayer().hasPermission(plugin.getPermissionManager().getPermission(dataHandler.getAutoSmellPermission()))) return;
        if(event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.SILK_TOUCH) > 0) return;
        if(!dataHandler.getSmellData().containsKey(event.getPlayer().getName())) return;
        if(!dataHandler.getSmellData().get(event.getPlayer().getName())) return;
        Location blockLoc = event.getBlock().getLocation();
        Material blockType = event.getBlock().getType();
        int fortuneLevel = event.getPlayer().getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
        int fortuneCount = 0;
        if(fortuneLevel > 0) {
            int min = 0;
            Random rand = new Random();
            fortuneCount = rand.nextInt((fortuneLevel - min) + 1) + min;
        }
        event.setCancelled(true);
        if(blockType.equals(Material.ANCIENT_DEBRIS)) {
            if(hasSpace(event.getPlayer(), Material.NETHERITE_SCRAP)) {
                event.getPlayer().getInventory().addItem(new ItemStack(Material.NETHERITE_SCRAP));
            } else {
                blockLoc.getWorld().dropItem(blockLoc, new ItemStack(Material.NETHERITE_SCRAP));
            }
        } else if(blockType.equals(Material.IRON_ORE) || blockType.equals(Material.DEEPSLATE_IRON_ORE)) {
            int count = 1 + fortuneCount;
            for (int i = 0; i < count; i++) {
                if (hasSpace(event.getPlayer(), Material.IRON_INGOT)) {
                    event.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_INGOT));
                } else {
                    blockLoc.getWorld().dropItem(blockLoc, new ItemStack(Material.IRON_INGOT));
                }
            }
        } else if(blockType.equals(Material.GOLD_ORE) || blockType.equals(Material.DEEPSLATE_GOLD_ORE)) {
            int count = 1 + fortuneCount;
            for (int i = 0; i < count; i++) {
                if (hasSpace(event.getPlayer(), Material.GOLD_INGOT)) {
                    event.getPlayer().getInventory().addItem(new ItemStack(Material.GOLD_INGOT));
                } else {
                    blockLoc.getWorld().dropItem(blockLoc, new ItemStack(Material.GOLD_INGOT));
                }
            }
        } else if(blockType.equals(Material.COPPER_ORE) || blockType.equals(Material.DEEPSLATE_COPPER_ORE)) {
            Random rand = new Random();
            int min = 2 * (fortuneLevel + 1);
            int max = 5 * (fortuneLevel + 1);
            int count = (rand.nextInt((max - min) + 1) + min);
            for (int i = 0; i < count; i++) {
                if (hasSpace(event.getPlayer(), Material.COPPER_INGOT)) {
                    event.getPlayer().getInventory().addItem(new ItemStack(Material.COPPER_INGOT));
                } else {
                    blockLoc.getWorld().dropItem(blockLoc, new ItemStack(Material.COPPER_INGOT));
                }
            }
        } else {
            event.setCancelled(false);
        }
        if(event.isCancelled()) {
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
            updateDurability(item, event.getPlayer());
            event.getBlock().setType(Material.AIR);
        }
    }

    public boolean hasSpace(Player player, Material material) {
        Inventory playerInventory = player.getInventory();
        for(int i = 0; i < 36; i++) {
            if(player.getInventory().getItem(i) != null) {
                if(playerInventory.getItem(i).getType().equals(Material.AIR)) {
                    return true;
                } else if(playerInventory.getItem(i).getType().equals(material)) {
                    if(playerInventory.getItem(i).getAmount() < 64) {
                        return true;
                    }
                }
            } else {
                return true;
            }
        }
        return false;
    }

    public void updateDurability(ItemStack item, Player player) {
        if(!item.getType().equals(Material.AIR)) {
            int max = item.getType().getMaxDurability();
            if(max == 0) return;
            if(!item.getItemMeta().isUnbreakable()) {
                int enchantmentLevel = item.getItemMeta().getEnchantLevel(Enchantment.DURABILITY);
                if(enchantmentLevel > 0) {
                    int chance = 100 / (enchantmentLevel + 1);
                    int random = (int) Math.floor(Math.random() * (100 + 1));
                    if (random < chance) {
                        item.setDurability((short) (item.getDurability() + 1));
                        if(item.getDurability() >= max) {
                            player.getInventory().setItem(player.getPlayer().getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
                        }
                    }
                } else {
                    item.setDurability((short) (item.getDurability() + 1));
                    if(item.getDurability() >= max) {
                        player.getInventory().setItem(player.getPlayer().getInventory().getHeldItemSlot(), new ItemStack(Material.AIR));
                        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
                    }
                }
            }
        }
    }

}
