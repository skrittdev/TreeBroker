package ru.skritt.treebroker;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

public class Commands implements CommandExecutor {
    private Treebroker plugin;
    public Config config;

    public Commands(Treebroker plugin, Config config) {
        this.plugin = plugin;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("treebroker.commands"))
        {
            sender.sendMessage(ChatColor.RED+"You don't have permission to use this command");
            return false;
        }
        String name;
        try {
            name = args[0];
        }
        catch (ArrayIndexOutOfBoundsException e)
                {
                    name="";
                }
        switch (name)
        {
            case "reload":{
                config.initConfig();
                sender.sendMessage(config.prefix+"Config reloaded!");
            } break;
            case "info":{
                PluginDescriptionFile pdf = plugin.getDescription();
                sender.sendMessage(config.prefix+"Plugin info:\n" +
                        "- Version: "+ChatColor.RED+pdf.getVersion()+"\n" +
                        ChatColor.GREEN+"- Author: "+ChatColor.RED+pdf.getAuthors()+"\n");
            } break;
            case "gmuse":{
                if(config.canUseInGm)
                {
                    config.setCanUseInGm(false);
                    sender.sendMessage(config.prefix+"Plugin using in creative disabled");
                } else {
                    config.setCanUseInGm(true);
                    sender.sendMessage(config.prefix+"Plugin using in creative enabled");
                }
            } break;
            case "noaxeuse":{
                if(config.canHandDestroy)
                {
                    config.setCanHandDestroy(false);
                    sender.sendMessage(config.prefix+"Plugin using without axe disabled");
                } else {
                    config.setCanHandDestroy(true);
                    sender.sendMessage(config.prefix+"Plugin using without axe enabled");
                }
            } break;
            case "setmaxblocks":{
                if(args.length == 2)
                {
                    config.setMaxBlocks(Integer.valueOf(args[1]));
                    sender.sendMessage(config.prefix+"Max blocks value now set to "+Integer.valueOf(args[1]));
                } else
                    sender.sendMessage(ChatColor.RED+"/trb setmaxblocks <count>");
            } break;
            default:{
                sender.sendMessage(ChatColor.GREEN+"/trb help - Commands list (This text)\n" +
                        "/trb info - Display plugin info\n" +
                        "/trb reload - Reload plugin config\n" +
                        "/trb gmuse - Enable/Disable plugin use in Creative\n" +
                        "/trb noaxeuse - Enable/Disable plugin use without Axe\n" +
                        "/trb setmaxblocks <count> - Set Max Blocks count for destroy at once");
            }
        }
        return true;
    }
}
