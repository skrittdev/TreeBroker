package ru.skritt.treebroker;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Tree {
    private int maxBlocks;

    public Tree(int maxBlocks) {
        this.maxBlocks=maxBlocks;
    }

    public int breakTree(int brokenBlocks, Player player, int x, int y, int z)
    {
        World world = player.getWorld();
        Block block = world.getBlockAt(x, y, z);
        Material type = block.getType();
        if (((type.equals(Material.LOG)) ||
                (type.equals(Material.LOG_2)) ||
                (type.equals(Material.LEAVES)) ||
                (type.equals(Material.LEAVES_2)) ||
                (type.equals(Material.HUGE_MUSHROOM_1)) ||
                (type.equals(Material.HUGE_MUSHROOM_2))) &&
                (brokenBlocks < this.maxBlocks))
        {
            block.breakNaturally();
            brokenBlocks++;
            if ((type.equals(Material.LOG)) ||
                    (type.equals(Material.LOG_2)) ||
                    (type.equals(Material.HUGE_MUSHROOM_1)) ||
                    (type.equals(Material.HUGE_MUSHROOM_2))) {
                try
                {
                    brokenBlocks = breakTree(brokenBlocks, player, x, y + 1, z);
                    brokenBlocks = breakTree(brokenBlocks, player, x, y, z + 1);
                    brokenBlocks = breakTree(brokenBlocks, player, x + 1, y, z);

                    brokenBlocks = breakTree(brokenBlocks, player, x - 1, y, z);
                    brokenBlocks = breakTree(brokenBlocks, player, x, y, z - 1);
                    brokenBlocks = breakTree(brokenBlocks, player, x, y - 1, z);

                    brokenBlocks = breakTree(brokenBlocks, player, x + 1, y, z + 1);
                    brokenBlocks = breakTree(brokenBlocks, player, x + 1, y, z - 1);
                    brokenBlocks = breakTree(brokenBlocks, player, x - 1, y, z + 1);
                    brokenBlocks = breakTree(brokenBlocks, player, x - 1, y, z - 1);
                }
                catch (StackOverflowError localStackOverflowError) {}
            }
        }
        return brokenBlocks;
    }
}
