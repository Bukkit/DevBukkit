package com.cogito.bukkit.dev;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.block.Action;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.SignChangeEvent;
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
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInventoryEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import org.bukkit.util.config.Configuration;

/**
 * Miscellaneous administrative commands
 */
public class DevBukkit extends JavaPlugin {
    private final DevEntityListener entityListener = new DevEntityListener(this);
    private final DevBlockListener blockListener = new DevBlockListener(this);
    private final DevPlayerListener playerListener = new DevPlayerListener(this);
    private boolean debugGlobal;
    private Map<Class<?>, Boolean> debugPrivates;
    private Map<Class<?>, Boolean> debugDefaultees;
    private boolean cancelGlobal;
    private Map<Class<?>, Boolean> cancelPrivates;
    private Map<Class<?>, Boolean> cancelDefaultees;
    private Map<Player, Boolean> gods;
    private SortedMap<String, Class<?>> eventAliases;
    public static String debugMessageMode;
    public static String debugTargets;

    private void initialiseEventAliases() {
        //need to be in lower case
        //eventAliases.put("Event", Event.class);
        //entity event aliases
        eventAliases.put("entity", EntityEvent.class);
        eventAliases.put("entityc", EntityCombustEvent.class);
        eventAliases.put("entitydbb", EntityDamageByBlockEvent.class);
        eventAliases.put("entitydbe", EntityDamageByEntityEvent.class);
        eventAliases.put("entitydbp", EntityDamageByProjectileEvent.class);
        eventAliases.put("entityda", EntityDamageEvent.class);
        eventAliases.put("entityde", EntityDeathEvent.class);
        eventAliases.put("entitye", EntityExplodeEvent.class);
        eventAliases.put("entityi", EntityInteractEvent.class);
        eventAliases.put("entityt", EntityTargetEvent.class);
        eventAliases.put("explosionp", ExplosionPrimeEvent.class);
        eventAliases.put("creaturesp",CreatureSpawnEvent.class);
        //block event aliases
        eventAliases.put("block", BlockEvent.class);
        eventAliases.put("blockd", BlockDamageEvent.class);
        eventAliases.put("blockcb", BlockCanBuildEvent.class);
        eventAliases.put("blockft", BlockFromToEvent.class);
        eventAliases.put("blockig", BlockIgniteEvent.class);
        eventAliases.put("blockph", BlockPhysicsEvent.class);
        eventAliases.put("blockpl", BlockPlaceEvent.class);
        eventAliases.put("blockred", BlockRedstoneEvent.class);
        eventAliases.put("leavesd", LeavesDecayEvent.class);
        eventAliases.put("blockbu", BlockBurnEvent.class);
        eventAliases.put("blockbr", BlockBreakEvent.class);
        eventAliases.put("signc", SignChangeEvent.class);
        //player event aliases
        eventAliases.put("player", PlayerEvent.class);
        eventAliases.put("playeran", PlayerAnimationEvent.class);
        eventAliases.put("playerbede", PlayerBedEnterEvent.class);
        eventAliases.put("playerbedl", PlayerBedLeaveEvent.class);
        eventAliases.put("playerbe", PlayerBucketEmptyEvent.class);
        eventAliases.put("playerbf", PlayerBucketFillEvent.class);
        eventAliases.put("playerch", PlayerChatEvent.class);
        eventAliases.put("playercp", PlayerCommandPreprocessEvent.class);
        eventAliases.put("playerdr", PlayerDropItemEvent.class);
        eventAliases.put("playeret", PlayerEggThrowEvent.class);
        eventAliases.put("playerit", PlayerInteractEvent.class);
        eventAliases.put("playerinv", PlayerInventoryEvent.class);
        eventAliases.put("playerih", PlayerItemHeldEvent.class);
        eventAliases.put("playerj", PlayerJoinEvent.class);
        eventAliases.put("playerk", PlayerKickEvent.class);
        eventAliases.put("playerlo", PlayerLoginEvent.class);
        eventAliases.put("playermo", PlayerMoveEvent.class);
        eventAliases.put("playerpi", PlayerPickupItemEvent.class);
        eventAliases.put("playerpl", PlayerPreLoginEvent.class);
        eventAliases.put("playerq", PlayerQuitEvent.class);
        eventAliases.put("playerr", PlayerRespawnEvent.class);
        eventAliases.put("playerte", PlayerTeleportEvent.class);
        eventAliases.put("playeri", PlayerInteractEvent.class);
        eventAliases.put("playerts", PlayerToggleSneakEvent.class);
    }

    public void onDisable() {
        //PluginManager pm = getServer().getPluginManager();
    }

    public void onEnable() {
        //TODO integrate persistence when we get it
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
        setDebugMode(PlayerEvent.class,false,false);
        setCancelMode(EntityEvent.class,false,false);
        setCancelMode(BlockEvent.class,false,false);
        setCancelMode(PlayerEvent.class,false,false);

        PluginManager pm = getServer().getPluginManager();
        //entity events
        pm.registerEvent(Event.Type.ENTITY_COMBUST, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_TARGET, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.ENTITY_INTERACT, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.EXPLOSION_PRIME, entityListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Priority.Normal, this);

        //block events
        pm.registerEvent(Event.Type.BLOCK_CANBUILD, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_DAMAGE, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_FROMTO, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_IGNITE, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_PHYSICS, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_PLACE, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.LEAVES_DECAY, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_BREAK, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.BLOCK_BURN, blockListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.SIGN_CHANGE, blockListener, Priority.Normal, this);

        //player events
        pm.registerEvent(Event.Type.PLAYER_INTERACT, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_ANIMATION, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_BED_ENTER, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_BED_LEAVE, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_BUCKET_EMPTY, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_BUCKET_FILL, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_CHAT, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_DROP_ITEM, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_EGG_THROW, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_INVENTORY, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_ITEM_HELD, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_KICK, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_LOGIN, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_MOVE, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_PICKUP_ITEM, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_PRELOGIN, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_RESPAWN, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_TELEPORT, playerListener, Priority.Normal, this);

        loadConfig();
        saveConfig();
        // Output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }

    public void loadConfig() {
        try {
            Configuration config = this.getConfiguration();
            debugMessageMode = config.getString("debugMessageMode", "console");
            debugTargets = config.getString("debugTargets","");

        } catch (Exception e) {
            getServer().getLogger().severe("Exception while loading DevBukkit/config.yml");
        }
    }

    public void saveConfig() {
        Configuration config = getConfiguration();
        config.setProperty("debugMessageMode", debugMessageMode);
        config.setProperty("debugTargets", debugTargets);
        config.save();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String[] trimmedArgs = args;
        String commandName = command.getName().toLowerCase();

        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
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
                            infoMessage(sender, "Event " + args[1] + " debug mode on" + (priv ? " (private)." : "."));
                            setDebugMode(eventClass, true, priv);
                        } else if (args[2].equalsIgnoreCase("off")) {
                            infoMessage(sender, "Event " + args[1] + " debug mode off" + (priv ? " (private)." : "."));
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
                            infoMessage(sender, "Event " + args[1] + " cancel mode on" + (priv ? " (private)." : "."));
                            setCancelMode(eventClass, true, priv);
                        } else if (args[2].equalsIgnoreCase("off")) {
                            infoMessage(sender, "Event " + args[1] + " cancel mode off" + (priv ? " (private)." : "."));
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
                    infoMessage(sender, "God mode " + (godModeToggle(player) ? "on" : "off") + ".");
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
                debugMessage("Item durability: " + player.getItemInHand().getDurability());
                debugMessage("Item materialdata: " + player.getItemInHand().getData());
                if (player.getItemInHand().getData() != null) debugMessage("Item data: " + player.getItemInHand().getData().getData());
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
        sender.sendMessage(eventStatus("block"));
        sender.sendMessage(eventStatus("entity"));
        sender.sendMessage(eventStatus("player"));
        sender.sendMessage("Use help <name> to get help on a specific event. ");

    }

    private void printHelp(CommandSender sender, Class<?> eventClass) {
        printHelpHeader(sender);
        for(Entry<String, Class<?>> entry : eventAliases.entrySet()){
            if(eventClass.isAssignableFrom(entry.getValue())){
                sender.sendMessage(eventStatus(entry.getKey()));
            }
        }
    }

    private void printHelpHeader(CommandSender sender) {
        sender.sendMessage(ChatColor.GOLD + "== Dev Help ==");
        if(sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage((isGod(player) ? ChatColor.GREEN:ChatColor.RED) + "GOD MODE IS " + (isGod(player) ? "ON" : "OFF"));
        }
        sender.sendMessage((debugGlobal ? ChatColor.GREEN:ChatColor.RED) + "DEBUG MODE IS " + (debugGlobal ? "ON" : "OFF"));
        sender.sendMessage((cancelGlobal ? ChatColor.GREEN:ChatColor.RED) + "CANCEL MODE IS "+ (cancelGlobal ? "ON" : "OFF"));
    }

    public void debugMessage(String string) {
        if (debugMessageMode.equals("first")) {
            Player[] players = getServer().getOnlinePlayers();

            Player player = players[0];
            player.sendMessage(string);
        } else if (debugMessageMode.equals("all")) {
            getServer().broadcastMessage(string);
        }  else if (debugMessageMode.equals("targets")) {
            String[] tmp = debugTargets.split(",");
            List<Player> matchedPlayers = new ArrayList<Player>();

            for (String i : tmp) {
                if (i.equals("")) continue;

                matchedPlayers = getServer().matchPlayer(i);
                if (matchedPlayers.isEmpty()) continue;
                matchedPlayers.get(0).sendMessage(string);
            }
        } else {
            System.out.println(string);
        }
    }

    public void debugMessage(Event event) {
        if(debug(event.getClass())){
            debugMessage(debugString(event));
        }
    }

    /**
     * Checks and cancels an event if it's event class has been specified to be cancelled.
     *
     * @param event The event to check, and cancel if necessary.
     */
    public void cancelEvent(Event event){
        if (cancel(event.getClass())) {
            ((Cancellable) event).setCancelled(true);
        }
    }

    /**
     * Perform actions for an event depending on the player's god mode status.
     * Any damage event will have the damage set to 0.
     *
     * @param event
     */
    public void godMode(Event event) {
        if (event instanceof EntityEvent) {
            Entity entity = ((EntityEvent) event).getEntity();

            // we cancel entities targeting any god that didn't strike first
            if (event instanceof EntityTargetEvent) {
                if (((EntityTargetEvent) event).getReason() != TargetReason.TARGET_ATTACKED_ENTITY){

                    entity = ((EntityTargetEvent) event).getTarget();
                    if (entity instanceof Player) {
                        Player player = (Player) entity;
                        if (isGod(player)) {
                            ((Cancellable) event).setCancelled(true);
                        }
                    }
                }
            }

            // affect the player in some way, if god.
            if (entity instanceof Player) {
                Player player = (Player) entity;
                if (isGod(player)) {
                    if (event instanceof EntityDamageEvent) {
                        ((EntityDamageEvent) event).setDamage(0);
                    }
                }
            }
        }
    }

    private String eventStatus(String eventAlias) {
        Class<?> eventClass = eventAliases.get(eventAlias);
        return (debug(eventClass) ? ChatColor.GREEN:ChatColor.RED) + "D " + (cancel(eventClass) ? ChatColor.GREEN:ChatColor.RED) + "C | " + ChatColor.WHITE + eventAlias + " -> " + eventClass.getSimpleName();
    }

    private String debugString(Event event) {
        Event e = event;
        String message = e.getClass().getSimpleName() + " [" + e.getType() + "]";
        if (e instanceof BlockEvent) {
            if (e instanceof SignChangeEvent) {
                SignChangeEvent signc = (SignChangeEvent) e;

                message += " A sign was modified to: " + signc.getLine(0) + " // " + signc.getLine(1) + " // " + signc.getLine(2) + " // " + signc.getLine(3);
            } else {
                BlockEvent blockEvent = (BlockEvent) e;
                Block block = blockEvent.getBlock();

                message += " (" + block.getX() + " " + block.getY() + " " + block.getZ()+ ") " + block.getType() + "{" + block.getData() + "}";
            }
        } else if (e instanceof EntityEvent) {
            Entity entity = ((EntityEvent) e).getEntity();
            message += " " + entity.getClass().getSimpleName() + "[" + entity.getEntityId() + "]";
            if (e instanceof EntityExplodeEvent) {
                EntityExplodeEvent entitye = (EntityExplodeEvent) e;
                Location location = entitye.getLocation();

                message += " exploded at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof EntityCombustEvent) {
                EntityCombustEvent entityc = (EntityCombustEvent) e;
                entity = entityc.getEntity();
                Location location = entity.getLocation();

                message += " caught fire at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof EntityDeathEvent) {
                EntityDeathEvent entityde = (EntityDeathEvent) e;
                entity = entityde.getEntity();
                Location location = entity.getLocation();

                message += " died at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof EntityTargetEvent) {
                EntityTargetEvent entityt = (EntityTargetEvent) e;
                Entity target = entityt.getTarget();

                if (target == null) {
                    message += " tried to target a dead entity ("+ entityt.getReason() + ")";
                } else {
                    message += " has targeted " + target.getClass().getSimpleName() + "[" + target.getEntityId() + "]" + " (" + entityt.getReason() + ")";
                }
            } else if (e instanceof EntityDamageEvent) {
                message += " was damaged";
                if (e instanceof EntityDamageByBlockEvent) {
                    EntityDamageByBlockEvent entitydbb = (EntityDamageByBlockEvent) e;
                    Block block = entitydbb.getDamager();

                    message += " by " + ((block == null) ? "'null'" : "a block of " + block.getClass().getSimpleName());
                } else if (e instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent entitydbe = (EntityDamageByEntityEvent) e;
                    Entity damager = entitydbe.getDamager();

                    message += " by ";
                    if (e instanceof EntityDamageByProjectileEvent) {
                        EntityDamageByProjectileEvent entitydbp = (EntityDamageByProjectileEvent) e;

                        message += "a projectile (" + entitydbp.getProjectile().getClass().getSimpleName() + ") from ";
                    }
                    message += damager.getClass().getSimpleName() + "[" + damager.getEntityId() + "]";
                }
                message += " (" + ((EntityDamageEvent) event).getCause() + ")";
            } else if (e instanceof ExplosionPrimeEvent) {
                ExplosionPrimeEvent explosionp = (ExplosionPrimeEvent) e;
                entity = explosionp.getEntity();
                Location location = entity.getLocation();

                message += " exploded (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof EntityInteractEvent) {
                EntityInteractEvent entityit = (EntityInteractEvent) e;

                message += " interacted with " + ((EntityInteractEvent)e).getBlock().getType() + " at (" + ((EntityInteractEvent)e).getBlock().getX() + ", " + ((EntityInteractEvent)e).getBlock().getY() + ", " + ((EntityInteractEvent)e).getBlock().getZ() + ")";
            } else if (e instanceof CreatureSpawnEvent) {
                CreatureSpawnEvent creaturesp = (CreatureSpawnEvent) e;
                Location location = creaturesp.getLocation();

                message += " spawned at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            }
        } else if (e instanceof PlayerEvent) {
            Player player = ((PlayerEvent) e).getPlayer();
            byte itemInHandData;
            short itemInHandDurability;
            message += " "+player.getName();

            if (e instanceof PlayerInteractEvent) {
                PlayerInteractEvent playerit = (PlayerInteractEvent) e;
                String itemInHand;

                if (player.getItemInHand().getType() == Material.AIR) {
                    itemInHand = "nothing";
                    itemInHandData = -1;
                    itemInHandDurability = 0;
                } else {
                    itemInHand = player.getItemInHand().getType().toString();
                    itemInHandData = player.getItemInHand().getData() != null ? player.getItemInHand().getData().getData() : -1;
                    itemInHandDurability = player.getItemInHand().getDurability();
                }

                if (playerit.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Block block = playerit.getClickedBlock();

                    message += " right clicked " + block.getType() + "{" + block.getData() + "} (" + block.getX() + ", " + block.getY() + ", " + block.getZ() + ") with " + itemInHand + " in hand [Data: " + itemInHandData + "; Dura: " + itemInHandDurability + "]";
                } else if (playerit.getAction() == Action.RIGHT_CLICK_AIR) {
                    Block block = playerit.getClickedBlock();

                    message += " right clicked AIR with " + itemInHand + " in hand [Data: " + itemInHandData + "; Dura: " + itemInHandDurability + "]";
                } else if (playerit.getAction() == Action.LEFT_CLICK_BLOCK) {
                    Block block = playerit.getClickedBlock();

                    message += " left clicked " + block.getType() + "{" + block.getData() + "} (" + block.getX() + ", " + block.getY() + ", " + block.getZ() + ") with " + itemInHand + " in hand [Data: " + itemInHandData + "; Dura: " + itemInHandDurability + "]";
                } else if (playerit.getAction() == Action.LEFT_CLICK_AIR) {
                    Block block = playerit.getClickedBlock();

                    message += " left clicked AIR with " + itemInHand + " in hand [Data: " + itemInHandData + "; Dura: " + itemInHandDurability + "]";
                }
            }
        }
        message += ".";
        return message;
    }

    private boolean godModeToggle(Player player) {
        if (isGod(player)) {
            return setGodMode(player, false);
        } else{
            return setGodMode(player, true);
        }
    }

    private boolean setGodMode(Player player, boolean iAmGod){
        if (player.isOp()) {
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
        return debugGlobal ? (debugPrivates.containsKey(eventClass) ? debugPrivates.get(eventClass):debugDefaultee(eventClass)) : false;
    }

    private void debugModeToggle() {
        debugGlobal = debugGlobal?false:true;
    }

    private void setDebugMode(boolean debug) {
        debugGlobal = debug;
    }

    private void setDebugMode(Class<?> eventClass, boolean debug, boolean priv){
        if (priv) {
            debugPrivates.put(eventClass, Boolean.valueOf(debug));
        } else {
            debugDefaultees.put(eventClass, Boolean.valueOf(debug));
            debugPrivates.remove(eventClass);
        }

        if (debug) {
            debugGlobal = true;
        }
    }

    private boolean debugDefaultee(Class<?> eventClass) {
        if (eventClass.getSuperclass() != null) {
            return debugDefaultees.containsKey(eventClass) ? debugDefaultees.get(eventClass) : true && debugDefaultee(eventClass.getSuperclass());
        } else {
            return true;
        }
    }

    private boolean cancel(Class<?> eventClass) {
        if (Cancellable.class.isAssignableFrom(eventClass)) {
            return cancelGlobal ? (cancelPrivates.containsKey(eventClass) ? cancelPrivates.get(eventClass) : cancelDefaultee(eventClass)) : false;
        } else {
            return false;
        }
    }

    private void cancelModeToggle() {
        cancelGlobal = cancelGlobal ? false : true;
    }

    private void setCancelMode(boolean cancel) {
        this.cancelGlobal = cancel;
    }

    private void setCancelMode(Class<?> eventClass, boolean cancel, boolean priv) {
        if (priv) {
            cancelPrivates.put(eventClass, Boolean.valueOf(cancel));
        } else {
            cancelDefaultees.put(eventClass, Boolean.valueOf(cancel));
            cancelPrivates.remove(eventClass);
        }

        if (cancel) {
            cancelGlobal = true;
        }
    }

    private Boolean cancelDefaultee(Class<?> eventClass) {
        if (eventClass.getSuperclass() != null) {
            return cancelDefaultees.containsKey(eventClass) ? cancelDefaultees.get(eventClass) : true && cancelDefaultee(eventClass.getSuperclass());
        } else {
            return true;
        }
    }
}