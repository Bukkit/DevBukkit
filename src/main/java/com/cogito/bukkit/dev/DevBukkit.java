
package com.cogito.bukkit.dev;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;


/**
 * Miscellaneous administrative commands
 *
 * @author Cogito
 */
public class DevBukkit extends JavaPlugin {
    private final DevEntityListener entityListener = new DevEntityListener(this);
    public boolean debug;
    private Map<Player, Boolean> gods;

    public DevBukkit(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
        debug = false;
        gods = new HashMap<Player, Boolean>();
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
    
    public boolean onCommand(Player player, Command command, String commandLabel, String[] args) {
        String[] split = args;
        if (command.getName().equalsIgnoreCase("dev")) {
            if (split.length > 1) {
                if (split[1].equalsIgnoreCase("debug")) {
                    if (split.length == 2) {
                        player.sendMessage(ChatColor.RED + "Dev: debug mode toggle.");
                        debugModeToggle();
                    } else if (split.length == 3) {
                        if (split[2].equalsIgnoreCase("on")) {
                            player.sendMessage(ChatColor.RED + "Dev: debug mode on.");
                            debugMode(true);
                        } else if (split[2].equalsIgnoreCase("off")) {
                            player.sendMessage(ChatColor.RED + "Dev: debug mode off.");
                            debugMode(false);
                        } else {
                            return false;
                        }
                    }
                } else if (split[1].equalsIgnoreCase("god")) {
                    if (split.length == 2) {
                        player.sendMessage(ChatColor.RED + "Dev: god mode on.");
                        godModeToggle(player);
                    } else if (split.length == 3) {
                        if (split[2].equalsIgnoreCase("on")) {
                            player.sendMessage(ChatColor.RED + "Dev: god mode on.");
                            godMode(player, true);
                        } else if (split[2].equalsIgnoreCase("off")) {
                            player.sendMessage(ChatColor.RED + "Dev: god mode off.");
                            godMode(player, false);
                        } else {
                            return false;
                        }
                    }
                } else{
                    return false;
                }
            } else {
                return false;
            }
            return true;
        }
        return false;
    }
    
    public void debugModeToggle() {
        if(debug){
            debugMode(false);
        } else{
            debugMode(true);
        }
    }

    public void debugMode(boolean debug) {
        this.debug = debug;
    }
    
    public void godModeToggle(Player player) {
        if(isGod(player)){
            godMode(player, false);
        } else{
            godMode(player, true);
        }
    }
    
    public void godMode(Player player, boolean iAmGod){
        gods.put(player, Boolean.valueOf(iAmGod));
    }
    
    public boolean isGod(Player player) {
        return gods.containsKey(player)?gods.get(player):false;
    }
}
