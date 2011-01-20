
package com.cogito.bukkit.dev;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityEvent;
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
    private boolean debugGlobal;
    private boolean debugKill;
    private Map<Class<?>, Boolean> debugDefaultees;
    private Map<Class<?>, Boolean> debugPrivates;
    private Map<Player, Boolean> gods;
    private SortedMap<String, Class<?>> eventAliases;

    public DevBukkit(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
        debugGlobal = false;
        debugKill = false;
        debugDefaultees = new HashMap<Class<?>, Boolean>();
        debugPrivates = new HashMap<Class<?>, Boolean>();
        gods = new HashMap<Player, Boolean>();
        eventAliases = new TreeMap<String, Class<?>>();
        initialiseEventAliases();
    }

    private void initialiseEventAliases() {
        //eventAliases.put("Event", Event.class);
        eventAliases.put("Entity", EntityEvent.class);
        eventAliases.put("EntityDBB", EntityDamageByBlockEvent.class);
        eventAliases.put("EntityDBE", EntityDamageByEntityEvent.class);
        eventAliases.put("EntityDBP", EntityDamageByProjectileEvent.class);
        eventAliases.put("EntityC", EntityCombustEvent.class);
        eventAliases.put("EntityD", EntityDamageEvent.class);
    }

    public void onDisable() {
        PluginManager pm = getServer().getPluginManager();
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.ENTITY_COMBUST, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGED, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGEDBY_BLOCK, entityListener, Priority.Normal, this);
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
                            setDebugMode(true);
                        } else if (split[2].equalsIgnoreCase("off")) {
                            player.sendMessage(ChatColor.RED + "Dev: debug mode off.");
                            setDebugMode(false);
                        } else if (split[2].equalsIgnoreCase("kill")) {
                            player.sendMessage(ChatColor.RED + "Dev: killed debug mode.");
                            killDebugMode(true);
                        } else {
                            return false;
                        }
                    }
                } else if (split[1].equalsIgnoreCase("events")) {
                    printEventAliases(player);
                } else if (split[1].equalsIgnoreCase("event")) {
                     if(split.length > 2 && eventAliases.containsKey(split[2])){
                         Class<?> eventClass = eventAliases.get(split[2]);
                         if(split.length < 4){
                             return false;
                         } else if (split[3].equalsIgnoreCase("on")) {
                            player.sendMessage(ChatColor.RED + "Dev: event "+split[2]+" debug mode on.");
                            setDebugMode(eventClass, true);
                        } else if (split[3].equalsIgnoreCase("off")) {
                            player.sendMessage(ChatColor.RED + "Dev: event "+split[2]+" debug mode off.");
                            setDebugMode(eventClass, false);
                        } else if (split[3].equalsIgnoreCase("default")) {
                            player.sendMessage(ChatColor.RED + "Dev: event "+split[2]+" debug mode set to default.");
                            setDefaultMode(eventClass, true);
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                } else if (split[1].equalsIgnoreCase("god")) {
                    if (split.length == 2) {
                        player.sendMessage(ChatColor.RED + "Dev: god mode on.");
                        godModeToggle(player);
                    } else if (split.length == 3) {
                        if (split[2].equalsIgnoreCase("on")) {
                            player.sendMessage(ChatColor.RED + "Dev: god mode on.");
                            setGodMode(player, true);
                        } else if (split[2].equalsIgnoreCase("off")) {
                            player.sendMessage(ChatColor.RED + "Dev: god mode off.");
                            setGodMode(player, false);
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
    
    private void printEventAliases(Player player) {
        player.sendMessage(ChatColor.RED + "== Event Aliases ==");
        for(Entry<String, Class<?>> entry : eventAliases.entrySet()){
            player.sendMessage(ChatColor.RED + entry.getKey()+" -> " + entry.getValue().getSimpleName());
        }
    }

    private void killDebugMode(boolean kill) {
        this.debugKill = kill;
    }

    private void debugModeToggle() {
        if(debugGlobal){
            setDebugMode(false);
        } else{
            setDebugMode(true);
        }
    }

    private void setDebugMode(boolean debug) {
        this.debugGlobal = debug;
        if(debug){
            killDebugMode(false);
        }
    }

    private void godModeToggle(Player player) {
        if(isGod(player)){
            setGodMode(player, false);
        } else{
            setGodMode(player, true);
        }
    }

    private void setGodMode(Player player, boolean iAmGod){
        gods.put(player, Boolean.valueOf(iAmGod));
    }

    public boolean isGod(Player player) {
        return gods.containsKey(player)?gods.get(player):false;
    }

    public String debugString(EntityDamageByBlockEvent event){
        return
            event.getCause() + "("+event.getDamage()+"): "
            + event.getEntity().getClass().getSimpleName()
            + "["+event.getEntity().getEntityId()+"]"
            + " was damaged by block "
            + ((event.getDamager() == null)?"null":event.getDamager().getClass().getSimpleName())
            + ".";
    }

    public String debugString(EntityDamageByEntityEvent event){
        return
            event.getCause() + "("+event.getDamage()+"): "
            + event.getEntity().getClass().getSimpleName()
            + "["+event.getEntity().getEntityId()+"]"
            + " was damaged by "
            + event.getDamager().getClass().getSimpleName()
            + "["+event.getDamager().getEntityId()+"]"
            + ".";
    }

    public String debugString(EntityDamageByProjectileEvent event) {
        return 
            event.getCause() + "("+event.getDamage()+"): "
            + event.getEntity().getClass().getSimpleName()
            + "["+event.getEntity().getEntityId()+"]"
            + " was damaged by a projectile ("
            + event.getDamager().getClass().getSimpleName()
            + "["+event.getDamager().getEntityId()+"]"
            + ").";
    }

    public String debugString(EntityCombustEvent event) {
        return 
            event.getType() + ": "
            + event.getEntity().getClass().getSimpleName()
            + "["+event.getEntity().getEntityId()+"]"
            + " caught fire.";
    }

    public String debugString(EntityDamageEvent event) {
        return 
            event.getCause() + "("+event.getDamage()+"): "
            + event.getEntity().getClass().getSimpleName()
            + "["+event.getEntity().getEntityId()+"]"
            + " was damaged.";
    }

    private boolean debugPrivate(Class<?> eventClass) {
        return debugPrivates.containsKey(eventClass)?debugPrivates.get(eventClass):true;
    }
    
    private void setDefaultMode(Class<?> eventClass, boolean eventDefault){
        debugDefaultees.put(eventClass, eventDefault);
    }
    
    private void setDebugMode(Class<?> eventClass, boolean debug){
        debugPrivates.put(eventClass, Boolean.valueOf(debug));
        setDefaultMode(eventClass, false);
    }

    private boolean defaultee(Class<?> eventClass) {
        if(eventClass.getSuperclass() != null){
            return defaultee(eventClass.getSuperclass())
                && debugDefaultees.containsKey(eventClass)?debugDefaultees.get(eventClass):true;
        } else {
            return true;
        }
    }

    public boolean debug(Class<?> eventClass) {
        boolean defaulting = defaultee(eventClass);
        return !debugKill && (defaulting && debugGlobal) || (!defaulting && debugPrivate(eventClass));
    }
}
