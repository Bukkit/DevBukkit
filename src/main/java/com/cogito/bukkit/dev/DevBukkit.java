
package com.cogito.bukkit.dev;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
import org.bukkit.event.entity.EntityExplodeEvent;
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
    private Map<Class<?>, Boolean> debugDefaultees;
    private Map<Class<?>, Boolean> debugPrivates;
    private Map<Player, Boolean> gods;
    private SortedMap<String, Class<?>> eventAliases;

    public DevBukkit(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
        debugGlobal = false;
        debugDefaultees = new HashMap<Class<?>, Boolean>();
        debugPrivates = new HashMap<Class<?>, Boolean>();
        gods = new HashMap<Player, Boolean>();
        eventAliases = new TreeMap<String, Class<?>>();
        initialiseEventAliases();
    }

    private void initialiseEventAliases() {
        //need to be in lower case
        //eventAliases.put("Event", Event.class);
        eventAliases.put("entity", EntityEvent.class);
        eventAliases.put("entityc", EntityCombustEvent.class);
        eventAliases.put("entitydbb", EntityDamageByBlockEvent.class);
        eventAliases.put("entitydbe", EntityDamageByEntityEvent.class);
        eventAliases.put("entitydbp", EntityDamageByProjectileEvent.class);
        eventAliases.put("entityd", EntityDamageEvent.class);
        eventAliases.put("entitye", EntityExplodeEvent.class);
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
                    } else if (split.length >= 3) {
                        if (split[2].equalsIgnoreCase("on")) {
                            player.sendMessage(ChatColor.RED + "Dev: debug mode on.");
                            setDebugMode(true);
                        } else if (split[2].equalsIgnoreCase("off")) {
                            player.sendMessage(ChatColor.RED + "Dev: debug mode off.");
                            setDebugMode(false);
                        } else if(split.length > 3 && eventAliases.containsKey(split[2].toLowerCase())){
                            Class<?> eventClass = eventAliases.get(split[2]);
                            boolean priv = false;
                            if(split.length > 4 && split[4].equalsIgnoreCase("p")){
                                priv = true;
                            } 
                            if (split[3].equalsIgnoreCase("on")) {
                                player.sendMessage(ChatColor.RED + "Dev: event "+split[2]+" debug mode on"+(priv?" (private).":"."));
                                setDebugMode(eventClass, true, priv);
                            } else if (split[3].equalsIgnoreCase("off")) {
                                player.sendMessage(ChatColor.RED + "Dev: event "+split[2]+" debug mode off"+(priv?" (private).":"."));
                                setDebugMode(eventClass, false, priv);
                            } else {
                                return false;
                            }
                       } else {
                            return false;
                        }
                    }
                } else if (split[1].equalsIgnoreCase("help")) {
                    printHelp(player);
                } else if (split[1].equalsIgnoreCase("god")) {
                    if (split.length == 2) {
                        player.sendMessage(ChatColor.RED + "Dev: god mode "+(godModeToggle(player)?"on":"off")+".");
                    } else if (split.length == 3) {
                        if (split[2].equalsIgnoreCase("on")) {
                            if(setGodMode(player, true)){
                                player.sendMessage(ChatColor.RED + "Dev: god mode on.");
                            }
                        } else if (split[2].equalsIgnoreCase("off")) {
                            if(setGodMode(player, false)){
                                player.sendMessage(ChatColor.RED + "Dev: god mode off.");
                            }
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
    
    private void printHelp(Player player) {
        player.sendMessage(ChatColor.RED + "== Dev Help ==");
        for(Entry<String, Class<?>> entry : eventAliases.entrySet()){
            player.sendMessage((debug(entry.getValue())?ChatColor.GREEN:ChatColor.RED) + entry.getKey() + " -> " + entry.getValue().getSimpleName());
        }
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
    }

    private boolean godModeToggle(Player player) {
        if(isGod(player)){
            return setGodMode(player, false);
        } else{
            return setGodMode(player, true);
        }
    }

    private boolean setGodMode(Player player, boolean iAmGod){
        if(player.isOp()){
            gods.put(player, Boolean.valueOf(iAmGod));
            return iAmGod;
        } else {
            return false;
        }
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
    
    public String debugString(EntityExplodeEvent event) {
        return 
            event.getType() + ": "
            + event.getEntity().getClass().getSimpleName()
            + "["+event.getEntity().getEntityId()+"]"
            + " exploded.";
    }

    private void setDebugMode(Class<?> eventClass, boolean debug, boolean priv){
        if(priv){
            debugPrivates.put(eventClass, Boolean.valueOf(debug));
        } else {
            debugDefaultees.put(eventClass, Boolean.valueOf(debug));
            debugPrivates.remove(eventClass);
        }
        if(debug){
            debugGlobal = true;
        }
    }

    private boolean defaultee(Class<?> eventClass) {
        if(eventClass.getSuperclass() != null){
            return debugDefaultees.containsKey(eventClass)?debugDefaultees.get(eventClass):true &&
                   defaultee(eventClass.getSuperclass());
        } else {
            return true;
        }
    }

    public boolean debug(Class<?> eventClass) {
        // if debug killed, return false.
        // if a private debug setting is set, return that value;
        // otherwise, recursively check the default value and return if all super defaults are on.
        return debugGlobal?(debugPrivates.containsKey(eventClass)?debugPrivates.get(eventClass):defaultee(eventClass)):false;
    }
}
