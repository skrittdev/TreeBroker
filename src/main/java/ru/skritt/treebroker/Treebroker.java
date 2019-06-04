package ru.skritt.treebroker;

import org.bukkit.plugin.java.JavaPlugin;

public final class Treebroker extends JavaPlugin {

    public Config config = new Config(this);

    @Override
    public void onEnable() {
        config.initConfig();
        getServer().getPluginManager().registerEvents(new Events(this,config), this);
        getLogger().info("Plugin enabled!");
        getCommand("trb").setExecutor(new Commands(this,config));
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled!");
    }

    public void onLoad()
    {
        super.onLoad();
        config.initConfig();
    }
}
