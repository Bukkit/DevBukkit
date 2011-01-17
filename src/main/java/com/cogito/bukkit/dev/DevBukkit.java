
package com.cogito.bukkit.dev;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
 * Miscellaneous administrative commands
 *
 * @author Cogito
 */
public class DevBukkit extends JavaPlugin {
    private final DevEntityListener entityListener = new DevEntityListener(this);

    public DevBukkit(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
    }

    public void onDisable() {
        PluginManager pm = getServer().getPluginManager();
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.ENTITY_COMBUST, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGED, entityListener, Priority.Normal, this);
        //pm.registerEvent(Event.Type.ENTITY_DAMAGEDBY_BLOCK, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGEDBY_ENTITY, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGEDBY_PROJECTILE, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Priority.Normal, this);
        
        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    
    public void onCommand(Player player, String command, String[] args) {
        String[] split = args;

        if (command.equalsIgnoreCase("/dev")) {
            if (split.length > 1) {
                if (split[1].equalsIgnoreCase("debug")) {
                    if (split.length == 2) {
                        player.sendMessage(ChatColor.RED + "Dev: debug mode toggle.");
                        entityListener.debugToggle();
                    } else if (split.length == 3) {
                        if (split[2].equalsIgnoreCase("on")) {
                            player.sendMessage(ChatColor.RED + "Dev: debug mode on.");
                            entityListener.debugOn();
                        } else if (split[2].equalsIgnoreCase("off")) {
                            player.sendMessage(ChatColor.RED + "Dev: debug mode off.");
                            entityListener.debugOn();
                        } else {
                            sendUsage(player);
                        }
                    }
                } else if (split[1].equalsIgnoreCase("god")) {
                    if (split.length == 2) {
                        player.sendMessage(ChatColor.RED + "Dev: god mode on.");
                        entityListener.godMode(player, true);
                    } else if (split.length == 3) {
                        if (split[2].equalsIgnoreCase("on")) {
                            player.sendMessage(ChatColor.RED + "Dev: god mode on.");
                            entityListener.godMode(player, true);
                        } else if (split[2].equalsIgnoreCase("off")) {
                            player.sendMessage(ChatColor.RED + "Dev: god mode off.");
                            entityListener.godMode(player, false);
                        } else {
                            sendUsage(player);
                        }
                    }
                } else{
                    sendUsage(player);
                }
            } else {
                sendUsage(player);
            }

        } 
    }
    
    public void sendUsage(Player player){
        player.sendMessage(ChatColor.RED + "Incorrect usage of command /dev. Examples:");
        player.sendMessage(ChatColor.RED + "/dev debug [on|off] - toggle full debug mode.");
        player.sendMessage(ChatColor.RED + "/dev god [on|off] - toggle god mode.");
    }
}
