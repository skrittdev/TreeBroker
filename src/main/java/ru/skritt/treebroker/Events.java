package ru.skritt.treebroker;

import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class Events implements Listener {
    private Treebroker plugin;
    Config config;
    Plugin core;
    CoreProtectAPI api;
    Tree tree;

    public Events(Treebroker plugin,Config config) {
        this.config=config;
        this.plugin=plugin;
        this.core = plugin.getServer().getPluginManager().getPlugin("CoreProtect");
        this.api=((CoreProtect) core).getAPI();
        this.tree = new Tree(config.maxBlocks);
        if (!core.isEnabled()) {
            plugin.getLogger().warning("CoreProtect needed for this plugin!");
            plugin.getPluginLoader().disablePlugin(plugin);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        if (player.hasPermission("treebroker.use"))
        {
            Block block = event.getBlock();
            List<String[]> look=api.blockLookup(block,0);
            if ((block.getType().equals(Material.LOG) ||
                    block.getType().equals(Material.LOG_2) ||
                    block.getType().equals(Material.HUGE_MUSHROOM_1) ||
                    block.getType().equals(Material.HUGE_MUSHROOM_2)) && look.isEmpty())
            {
                int x = block.getX();
                int y = block.getY();
                int z = block.getZ();
                if((!config.canHandDestroy && !player.getItemInHand().getType().toString().contains("AXE")) ||
                        (!config.canUseInGm && player.getGameMode() == GameMode.CREATIVE))
                    return;
                int blocks = tree.breakTree(0, player, x, y, z);
                if (blocks > config.maxBlocks * 2 / 3) {
                    player.sendMessage(config.prefix + "You destroyed " + blocks + " blocks.");
                }
            }
        }
    }
}
