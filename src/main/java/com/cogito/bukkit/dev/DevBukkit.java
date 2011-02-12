
package com.cogito.bukkit.dev;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockInteractEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.BlockRightClickEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


/**
 * Miscellaneous administrative commands
 *
 * @author Cogito
 */
public class DevBukkit extends JavaPlugin {
    private final DevEntityListener entityListener = new DevEntityListener(this);
    private final DevBlockListener blockListener = new DevBlockListener(this);
    private boolean debugGlobal;
    private Map<Class<?>, Boolean> debugPrivates;
    private Map<Class<?>, Boolean> debugDefaultees;
    private boolean cancelGlobal;
    private Map<Class<?>, Boolean> cancelPrivates;
    private Map<Class<?>, Boolean> cancelDefaultees;
    private Map<Player, Boolean> gods;
    private SortedMap<String, Class<?>> eventAliases;

    public DevBukkit(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);

        debugGlobal = false;
        debugDefaultees = new HashMap<Class<?>, Boolean>();
        debugPrivates = new HashMap<Class<?>, Boolean>();

        cancelGlobal = false;
        cancelDefaultees = new HashMap<Class<?>, Boolean>();
        cancelPrivates = new HashMap<Class<?>, Boolean>();

        gods = new HashMap<Player, Boolean>();
        eventAliases = new TreeMap<String, Class<?>>();
        initialiseEventAliases();
        setDebugMode(EntityEvent.class,false,false);
        setDebugMode(BlockEvent.class,false,false);
    }

    private void initialiseEventAliases() {
        //need to be in lower case
        //eventAliases.put("Event", Event.class);
        eventAliases.put("entity", EntityEvent.class);
        eventAliases.put("entityc", EntityCombustEvent.class);
        eventAliases.put("entitydbb", EntityDamageByBlockEvent.class);
        eventAliases.put("entitydbe", EntityDamageByEntityEvent.class);
        eventAliases.put("entitydbp", EntityDamageByProjectileEvent.class);
        eventAliases.put("entityda", EntityDamageEvent.class);
        eventAliases.put("entityde", EntityDeathEvent.class);
        eventAliases.put("entitye", EntityExplodeEvent.class);
        eventAliases.put("entityt", EntityTargetEvent.class);
        //block event aliases
        eventAliases.put("block", BlockEvent.class);
        eventAliases.put("blockd", BlockDamageEvent.class);
        eventAliases.put("blockcb", BlockCanBuildEvent.class);
        eventAliases.put("blockft", BlockFromToEvent.class);
        eventAliases.put("blockig", BlockIgniteEvent.class);
        eventAliases.put("blockin", BlockInteractEvent.class);
        eventAliases.put("blockph", BlockPhysicsEvent.class);
        eventAliases.put("blockpl", BlockPlaceEvent.class);
        eventAliases.put("blockred", BlockRedstoneEvent.class);
        eventAliases.put("blockrc", BlockRightClickEvent.class);
        eventAliases.put("leavesd", LeavesDecayEvent.class);
        eventAliases.put("blockbu", BlockBurnEvent.class);
        eventAliases.put("blockbr", BlockBreakEvent.class);
    }

    public void onDisable() {
        //PluginManager pm = getServer().getPluginManager();
    }

    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        //entity events
        pm.registerEvent(Event.Type.ENTITY_COMBUST, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGED, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGEDBY_BLOCK, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGEDBY_ENTITY, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGEDBY_PROJECTILE, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_TARGET, entityListener, Priority.Normal, this);
        
        //block events
        pm.registerEvent(Event.Type.BLOCK_CANBUILD, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DAMAGED, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_FLOW, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_IGNITE, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_INTERACT, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_PHYSICS, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_PLACED, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_RIGHTCLICKED, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.LEAVES_DECAY, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_BURN, blockListener, Priority.Normal, this);
        
        // EXAMPLE: Custom code, here we just output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }
    
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String[] trimmedArgs = args;
        String commandName = command.getName().toLowerCase();
        
        Player player = null;
        if (sender instanceof Player) {
            player = (Player)sender;
        } else if (args.length > 0){
            // Assume first parameter of args is a player and remove it. null if player not found. 
            player = getServer().getPlayer(args[0]);
            trimmedArgs = new String[args.length-1];
            for(int i = 1; i < args.length; i++)
                trimmedArgs[i-1] = args[i];
        }

        if (commandName.equals("dev")) {
            return parseCommands(sender, player, trimmedArgs);
        }
        return false;
    }
    
    public boolean parseCommands(CommandSender sender, Player player, String[] args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("debug") || args[0].equalsIgnoreCase("d")) {
                if (args.length == 1) {
                    infoMessage(sender, "Debug mode toggle.");
                    debugModeToggle();
                } else if (args.length >= 2) {
                    if (args[1].equalsIgnoreCase("on")) {
                        infoMessage(sender, "Debug mode on.");
                        setDebugMode(true);
                    } else if (args[1].equalsIgnoreCase("off")) {
                        infoMessage(sender, "Debug mode off.");
                        setDebugMode(false);
                    } else if(args.length > 2 && eventAliases.containsKey(args[1].toLowerCase())){
                        Class<?> eventClass = eventAliases.get(args[1].toLowerCase());
                        boolean priv = false;
                        if(args.length > 3 && args[3].equalsIgnoreCase("p")){
                            priv = true;
                        } 
                        if (args[2].equalsIgnoreCase("on")) {
                            infoMessage(sender, "Event "+args[1]+" debug mode on"+(priv?" (private).":"."));
                            setDebugMode(eventClass, true, priv);
                        } else if (args[2].equalsIgnoreCase("off")) {
                            infoMessage(sender, "Event "+args[1]+" debug mode off"+(priv?" (private).":"."));
                            setDebugMode(eventClass, false, priv);
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            } else if (args[0].equalsIgnoreCase("cancel") || args[0].equalsIgnoreCase("c")) {
                if (args.length == 1) {
                    infoMessage(sender, "Cancel mode toggle.");
                    cancelModeToggle();
                } else if (args.length >= 2) {
                    if (args[1].equalsIgnoreCase("on")) {
                        infoMessage(sender, "Cancel mode on.");
                        setCancelMode(true);
                    } else if (args[1].equalsIgnoreCase("off")) {
                        infoMessage(sender, "Cancel mode off.");
                        setCancelMode(false);
                    } else if(args.length > 2 && eventAliases.containsKey(args[1].toLowerCase())){
                        Class<?> eventClass = eventAliases.get(args[1].toLowerCase());
                        boolean priv = false;
                        if(args.length > 3 && args[3].equalsIgnoreCase("p")){
                            priv = true;
                        } 
                        if (args[2].equalsIgnoreCase("on")) {
                            infoMessage(sender, "Event "+args[1]+" cancel mode on"+(priv?" (private).":"."));
                            setCancelMode(eventClass, true, priv);
                        } else if (args[2].equalsIgnoreCase("off")) {
                            infoMessage(sender, "Event "+args[1]+" cancel mode off"+(priv?" (private).":"."));
                            setCancelMode(eventClass, false, priv);
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            } else if (args[0].equalsIgnoreCase("help")) {
                if(args.length > 1 && eventAliases.containsKey(args[1].toLowerCase())){
                    printHelp(sender, eventAliases.get(args[1].toLowerCase()));
                } else {
                    printHelp(sender);
                }
            } else if (args[0].equalsIgnoreCase("god") && player != null) {
                if (args.length == 1) {
                    infoMessage(sender, "God mode "+(godModeToggle(player)?"on":"off")+".");
                } else if (args.length == 2) {
                    if (args[1].equalsIgnoreCase("on")) {
                        if(setGodMode(player, true)){
                            infoMessage(sender, "God mode on.");
                        }
                    } else if (args[1].equalsIgnoreCase("off")) {
                        if(setGodMode(player, false)){
                            infoMessage(sender, "God mode off.");
                        }
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("getdata") && player != null) {
                debugMessage("Item name: " + player.getItemInHand().getType());
                debugMessage("Item ID: " + player.getItemInHand().getTypeId());
                debugMessage("Item count: " + player.getItemInHand().getAmount());
                debugMessage("Item data: " + player.getItemInHand().getDurability());
                int maxStackSize = player.getItemInHand().getTypeId() == 0 ? 1 : player.getItemInHand().getMaxStackSize();
                debugMessage("Item max stack size: " + maxStackSize);
            } else{
                return false;
            }
        } else {
            return false;
        }
        return true;
    }

    private void infoMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.RED + "Dev: " + message);
    }
    
    private void printHelp(CommandSender sender) {
        printHelpHeader(sender);
        Class<?> topLevelClass = BlockEvent.class;
        sender.sendMessage((debug(topLevelClass)?ChatColor.GREEN:ChatColor.RED) + "block -> " + topLevelClass.getSimpleName());
        topLevelClass = EntityEvent.class;
        sender.sendMessage((debug(topLevelClass)?ChatColor.GREEN:ChatColor.RED) + "entity -> " + topLevelClass.getSimpleName());
        sender.sendMessage("Use help <name> to get help on a specific event. ");
        
    }
    
    private void printHelp(CommandSender sender, Class<?> eventClass) {
        printHelpHeader(sender);
        for(Entry<String, Class<?>> entry : eventAliases.entrySet()){
            if(eventClass.isAssignableFrom(entry.getValue())){
                sender.sendMessage((debug(entry.getValue())?ChatColor.GREEN:ChatColor.RED)+"D "
                                  +(cancel(entry.getValue())?ChatColor.GREEN:ChatColor.RED)+"C | "
                                  +ChatColor.WHITE+entry.getKey() + " -> " + entry.getValue().getSimpleName());
            }
        }
    }

    private void printHelpHeader(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "== Dev Help ==");
        if(sender instanceof Player){
            Player player = (Player)sender;
            player.sendMessage((isGod(player)?ChatColor.GREEN:ChatColor.RED) + "GOD MODE "+(isGod(player)?"ON":"OFF"));
        }
        sender.sendMessage((debugGlobal?ChatColor.GREEN:ChatColor.RED) + "DEBUG MODE "+(debugGlobal?"ON":"OFF"));
        sender.sendMessage((cancelGlobal?ChatColor.GREEN:ChatColor.RED) + "CANCEL MODE "+(cancelGlobal?"ON":"OFF"));
    }
    
    public void debugMessage(String string) {
        System.out.println(string);
    }

    public void debugMessage(Event event) {
        if(debug(event.getClass())){
            debugMessage(debugString(event));
        }
    }

    public void cancelEvent(Event event){
        if (cancel(event.getClass())) {
            ((Cancellable) event).setCancelled(true);
        }
    }
    /**
     * Perform actions for an event depending on the player's god mode status.
     * @param event
     */
    public void godMode(Event event) {
        if (event instanceof EntityEvent) {
            Entity entity = ((EntityEvent) event).getEntity();
            
            if(event instanceof EntityTargetEvent){
                if(((EntityTargetEvent) event).getReason() != TargetReason.TARGET_ATTACKED_ENTITY){
                    // we will cancel target events on god's that didn't start the fight
                    entity = ((EntityTargetEvent) event).getTarget();
                    if(entity instanceof Player){
                        Player player = (Player) entity;
                        if(isGod(player)){
                            ((Cancellable) event).setCancelled(true);
                        }
                    }
                }
            }
            
            // affect the player in some way, if god.
            if(entity instanceof Player){
                Player player = (Player) entity;
                if(isGod(player)){
                    if (event instanceof EntityDamageEvent) {
                        ((EntityDamageEvent) event).setDamage(0);
                    }
                }
            }
        }
    }

    private String debugString(Event event) {
        Event e = event;
        String message = e.getClass().getSimpleName()+" ["+e.getType()+"]";
        if (e instanceof BlockEvent) {
            message += " ("+((BlockEvent) e).getBlock().getX()+" "+((BlockEvent) e).getBlock().getY()+" "+((BlockEvent) e).getBlock().getZ()+") "
                     + ((BlockEvent) e).getBlock().getType();
        } else
        if (e instanceof EntityEvent) {
            message += " "+((EntityEvent) e).getEntity().getClass().getSimpleName()
                     + "["+((EntityEvent) e).getEntity().getEntityId()+"]";
            if (e instanceof EntityExplodeEvent) {
                message += " exploded";
            } else if (e instanceof EntityCombustEvent) {
                message += " caught fire";
            } else if (e instanceof EntityDeathEvent) {
                message += " died";
            } else if (e instanceof EntityTargetEvent) {
                Entity target = ((EntityTargetEvent) e).getTarget();
                message += " has targeted " + target.getClass().getSimpleName()+"["+target.getEntityId()+"]"
                         + " ("+((EntityTargetEvent) e).getReason()+")";
            } else if (e instanceof EntityDamageEvent) {
                message += " was damaged";
                if (e instanceof EntityDamageByBlockEvent) {
                    Block block = ((EntityDamageByBlockEvent) event).getDamager();
                    message += " by "+((block == null)?"'null'":"a block of "+block.getClass().getSimpleName());
                } else if (e instanceof EntityDamageByEntityEvent) {
                    Entity damager = ((EntityDamageByEntityEvent) event).getDamager();
                    message += " by ";
                    if (e instanceof EntityDamageByProjectileEvent) {
                        message += "a projectile ("+((EntityDamageByProjectileEvent) e).getProjectile().getClass().getSimpleName()+") from ";
                    }
                    message += damager.getClass().getSimpleName()+"["+damager.getEntityId()+"]";
                }
                message += " ("+((EntityDamageEvent) event).getCause()+")";
            }
        }
        message += ".";
        return message;
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

    /**
     * If this player is a DevGod.
     * 
     * @param player the Player to check
     * @return if this Player is a DevGod
     */
    public boolean isGod(Player player) {
        return gods.containsKey(player)?gods.get(player):false;
    }

    private boolean debug(Class<?> eventClass) {
        // if debug killed, return false.
        // if a private debug setting is set, return that value;
        // otherwise, recursively check the default value and return if all super defaults are on.
        return debugGlobal?(debugPrivates.containsKey(eventClass)?debugPrivates.get(eventClass):debugDefaultee(eventClass)):false;
    }

    private void debugModeToggle() {
        debugGlobal = debugGlobal?false:true;
    }

    private void setDebugMode(boolean debug) {
        debugGlobal = debug;
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

    private boolean debugDefaultee(Class<?> eventClass) {
        if(eventClass.getSuperclass() != null){
            return debugDefaultees.containsKey(eventClass)?debugDefaultees.get(eventClass):true &&
                   debugDefaultee(eventClass.getSuperclass());
        } else {
            return true;
        }
    }

    private boolean cancel(Class<?> eventClass) {
        if (Cancellable.class.isAssignableFrom(eventClass)) {
            return cancelGlobal?(cancelPrivates.containsKey(eventClass)?cancelPrivates.get(eventClass):cancelDefaultee(eventClass)):false;
        } else {
            return false;
        }
        }

    private void cancelModeToggle() {
        cancelGlobal = cancelGlobal?false:true;
    }

    private void setCancelMode(boolean cancel) {
        this.cancelGlobal = cancel;
    }

    private void setCancelMode(Class<?> eventClass, boolean cancel, boolean priv) {
        if(priv){
            cancelPrivates.put(eventClass, Boolean.valueOf(cancel));
        } else {
            cancelDefaultees.put(eventClass, Boolean.valueOf(cancel));
            cancelPrivates.remove(eventClass);
        }
        if(cancel){
            cancelGlobal = true;
        }
    }

    private Boolean cancelDefaultee(Class<?> eventClass) {
        if(eventClass.getSuperclass() != null){
            return cancelDefaultees.containsKey(eventClass)?cancelDefaultees.get(eventClass):true &&
                   cancelDefaultee(eventClass.getSuperclass());
        } else {
            return true;
        }
    }
}
