package ru.skritt.treebroker;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;

public class Config {
    private Treebroker plugin;
    public boolean canHandDestroy=false;
    public boolean canUseInGm=false;
    public int maxBlocks = 1000;
    public final String prefix;
    private static final String configMaxBlocks = "max_destroy_at_once";
    private static final String configHandDestroy = "can_destroy_without_axe";
    private static final String configGmUse = "can_use_at_gm";
    private static final String header = configMaxBlocks+" - Maximum number of blocks that can be destroyed at once\n" +
            configHandDestroy+" - If player can destroy a tree with TreeBroker without having an axe\n"+
            configGmUse+" - If player can use plugin at Gamemode 1";
    FileConfiguration config;

    public Config(Treebroker plugin) {
        this.plugin=plugin;
        this.prefix = ChatColor.DARK_GREEN + "[" + plugin.getName() + "] " + ChatColor.GREEN;
        config = plugin.getConfig();
    }

    public void initConfig()
    {
        plugin.reloadConfig();
        FileConfigurationOptions opts = config.options();
        config.addDefault(configMaxBlocks, Integer.valueOf(this.maxBlocks));
        config.addDefault(configHandDestroy, false);
        config.addDefault(configGmUse, false);
        opts.header(header);
        opts.copyDefaults(true);
        opts.copyHeader();
        plugin.saveConfig();
        this.maxBlocks = config.getInt(configMaxBlocks, this.maxBlocks);
        this.canHandDestroy = config.getBoolean(configHandDestroy,false);
        this.canUseInGm = config.getBoolean(configGmUse,false);
    }

    public void setCanHandDestroy(boolean canHandDestroy) {
        this.canHandDestroy = canHandDestroy;
        config.set(configHandDestroy,canHandDestroy);
        plugin.saveConfig();
    }

    public void setCanUseInGm(boolean canUseInGm) {
        this.canUseInGm = canUseInGm;
        config.set(configGmUse,canUseInGm);
        plugin.saveConfig();
    }

    public void setMaxBlocks(int maxBlocks) {
        this.maxBlocks = maxBlocks;
        config.set(configMaxBlocks,maxBlocks);
        plugin.saveConfig();
    }
}
