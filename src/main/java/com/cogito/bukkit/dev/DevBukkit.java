package com.cogito.bukkit.dev;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map.Entry;
import java.util.*;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.block.*;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.event.painting.PaintingBreakByEntityEvent;
import org.bukkit.event.painting.PaintingBreakEvent;
import org.bukkit.event.painting.PaintingEvent;
import org.bukkit.event.painting.PaintingPlaceEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.MapInitializeEvent;
import org.bukkit.event.server.ServerEvent;
import org.bukkit.event.vehicle.*;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.event.weather.WeatherEvent;
import org.bukkit.event.world.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Miscellaneous administrative commands
 */
public class DevBukkit extends JavaPlugin {
    private boolean debugGlobal = false;
    private Map<Class<?>, Boolean> debugPrivates = new HashMap<Class<?>, Boolean>();
    private Map<Class<?>, Boolean> debugDefaultees = new HashMap<Class<?>, Boolean>();
    private boolean cancelGlobal = false;
    private Map<Class<?>, Boolean> cancelPrivates = new HashMap<Class<?>, Boolean>();
    private Map<Class<?>, Boolean> cancelDefaultees = new HashMap<Class<?>, Boolean>();
    private Map<Player, Boolean> gods = new HashMap<Player, Boolean>();
    private SortedMap<String, Class<?>> eventAliases = new TreeMap<String, Class<?>>();
    public static String debugMessageMode;
    public static String debugTargets;

    private void initialiseEventAliases() {
        // need to be in lower case
        // eventAliases.put("Event", Event.class);
        // block event aliases
        eventAliases.put("block", BlockEvent.class);
        eventAliases.put("blockbr", BlockBreakEvent.class);
        eventAliases.put("blockbu", BlockBurnEvent.class);
        eventAliases.put("blockcb", BlockCanBuildEvent.class);
        eventAliases.put("blockd", BlockDamageEvent.class);
        eventAliases.put("blockdi", BlockDispenseEvent.class);
        eventAliases.put("blockfa", BlockFadeEvent.class);
        eventAliases.put("blockfo", BlockFormEvent.class);
        eventAliases.put("blockft", BlockFromToEvent.class);
        eventAliases.put("blockig", BlockIgniteEvent.class);
        eventAliases.put("blockph", BlockPhysicsEvent.class);
        eventAliases.put("blockpi", BlockPistonEvent.class);
        eventAliases.put("blockpie", BlockPistonExtendEvent.class);
        eventAliases.put("blockpir", BlockPistonRetractEvent.class);
        eventAliases.put("blockpl", BlockPlaceEvent.class);
        eventAliases.put("blockred", BlockRedstoneEvent.class);
        eventAliases.put("blocksp", BlockSpreadEvent.class);
        eventAliases.put("leavesd", LeavesDecayEvent.class);
        eventAliases.put("signc", SignChangeEvent.class);
        // enchantment event aliases
        eventAliases.put("enchanti", EnchantItemEvent.class);
        eventAliases.put("enchantpi", PrepareItemEnchantEvent.class);
        // entity event aliases
        eventAliases.put("entity", EntityEvent.class);
        eventAliases.put("creaturesp",CreatureSpawnEvent.class);
        eventAliases.put("creeperp", CreeperPowerEvent.class);
        eventAliases.put("entitycbb", EntityCombustByBlockEvent.class);
        eventAliases.put("entitycbe", EntityCombustByEntityEvent.class);
        eventAliases.put("entityc", EntityCombustEvent.class);
        eventAliases.put("entitycb", EntityChangeBlockEvent.class);
        eventAliases.put("entitydbb", EntityDamageByBlockEvent.class);
        eventAliases.put("entitydbe", EntityDamageByEntityEvent.class);
        eventAliases.put("entityda", EntityDamageEvent.class);
        eventAliases.put("entityde", EntityDeathEvent.class);
        eventAliases.put("entitye", EntityExplodeEvent.class);
        eventAliases.put("entityit", EntityInteractEvent.class);
        eventAliases.put("entitype", EntityPortalEnterEvent.class);
        eventAliases.put("entityrh", EntityRegainHealthEvent.class);
        eventAliases.put("entityta",EntityTameEvent.class);
        eventAliases.put("entityt", EntityTargetEvent.class);
        eventAliases.put("entityte", EntityTeleportEvent.class);
        eventAliases.put("explosionp", ExplosionPrimeEvent.class);
        eventAliases.put("foodlc", FoodLevelChangeEvent.class);
        eventAliases.put("items", ItemSpawnEvent.class);
        eventAliases.put("pigz", PigZapEvent.class);
        eventAliases.put("pots", PotionSplashEvent.class);
        eventAliases.put("projh", ProjectileHitEvent.class);
        eventAliases.put("sheeprw", SheepRegrowWoolEvent.class);
        eventAliases.put("sheepdw", SheepDyeWoolEvent.class);
        // painting event aliases
        eventAliases.put("painting", PaintingEvent.class);
        eventAliases.put("paintingbe", PaintingBreakByEntityEvent.class);
        eventAliases.put("paintingb", PaintingBreakEvent.class);
        eventAliases.put("paintingp", PaintingPlaceEvent.class);
        // player event aliases
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
        eventAliases.put("playerite", PlayerInteractEntityEvent.class);
        eventAliases.put("playerit", PlayerInteractEvent.class);
        eventAliases.put("playerih", PlayerItemHeldEvent.class);
        eventAliases.put("playerj", PlayerJoinEvent.class);
        eventAliases.put("playerk", PlayerKickEvent.class);
        eventAliases.put("playerlo", PlayerLoginEvent.class);
        eventAliases.put("playermo", PlayerMoveEvent.class);
        eventAliases.put("playerpi", PlayerPickupItemEvent.class);
        eventAliases.put("playerpo", PlayerPortalEvent.class);
        eventAliases.put("playerpl", PlayerPreLoginEvent.class);
        eventAliases.put("playerq", PlayerQuitEvent.class);
        eventAliases.put("playerr", PlayerRespawnEvent.class);
        eventAliases.put("playersh", PlayerShearEntityEvent.class);
        eventAliases.put("playerte", PlayerTeleportEvent.class);
        eventAliases.put("playerts", PlayerToggleSneakEvent.class);
        eventAliases.put("playerfe", PlayerFishEvent.class);
        // inventory event aliases
        eventAliases.put("furnaceb", FurnaceBurnEvent.class);
        eventAliases.put("furnaces", FurnaceSmeltEvent.class);
        // server event aliases
        eventAliases.put("server", ServerEvent.class);
        eventAliases.put("mapinit", MapInitializeEvent.class);
        // vehicle event aliases
        eventAliases.put("vehicle", VehicleEvent.class);
        eventAliases.put("vehbco", VehicleBlockCollisionEvent.class);
        eventAliases.put("vehco", VehicleCollisionEvent.class);
        eventAliases.put("vehcr", VehicleCreateEvent.class);
        eventAliases.put("vehda", VehicleDamageEvent.class);
        eventAliases.put("vehde", VehicleDestroyEvent.class);
        eventAliases.put("vehen", VehicleEnterEvent.class);
        eventAliases.put("veheco", VehicleEntityCollisionEvent.class);
        eventAliases.put("vehx", VehicleExitEvent.class);
        eventAliases.put("vehm", VehicleMoveEvent.class);
        eventAliases.put("vehu", VehicleUpdateEvent.class);
        // weather event aliases
        eventAliases.put("weather", WeatherEvent.class);
        eventAliases.put("lightings", LightningStrikeEvent.class);
        eventAliases.put("thunderc", ThunderChangeEvent.class);
        eventAliases.put("weatherc", WeatherChangeEvent.class);
        // world event aliases
        eventAliases.put("chunk", ChunkEvent.class);
        eventAliases.put("chunkl", ChunkLoadEvent.class);
        eventAliases.put("chunkp", ChunkPopulateEvent.class);
        eventAliases.put("chunku", ChunkUnloadEvent.class);
        eventAliases.put("portalc", PortalCreateEvent.class);
        eventAliases.put("spawnc", SpawnChangeEvent.class);
        eventAliases.put("world", WorldEvent.class);
        eventAliases.put("structg", StructureGrowEvent.class);
        eventAliases.put("worldi", WorldInitEvent.class);
        eventAliases.put("worldl", WorldLoadEvent.class);
        eventAliases.put("worlds", WorldSaveEvent.class);
        eventAliases.put("worldu", WorldUnloadEvent.class);
    }

    @Override
    public void onEnable() {
        //TODO integrate persistence when we get it
        initialiseEventAliases();
        setDebugMode(BlockEvent.class,false,false);
        setDebugMode(EntityEvent.class,false,false);
        setDebugMode(PaintingEvent.class,false,false);
        setDebugMode(PlayerEvent.class,false,false);
        setDebugMode(ServerEvent.class,false,false);
        setDebugMode(VehicleEvent.class,false,false);
        setDebugMode(WeatherEvent.class,false,false);
        setDebugMode(WorldEvent.class,false,false);
        setCancelMode(BlockEvent.class,false,false);
        setCancelMode(EntityEvent.class,false,false);
        setCancelMode(PaintingEvent.class,false,false);
        setCancelMode(PlayerEvent.class,false,false);
        setCancelMode(ServerEvent.class,false,false);
        setCancelMode(VehicleEvent.class,false,false);
        setCancelMode(WeatherEvent.class,false,false);
        setCancelMode(WorldEvent.class,false,false);

        try {
            Field pluginManager = getServer().getClass().getDeclaredField("pluginManager");
            pluginManager.setAccessible(true);
            Field modifiers = Field.class.getDeclaredField("modifiers");
            modifiers.setAccessible(true);
            modifiers.setInt(pluginManager, pluginManager.getModifiers() & ~Modifier.FINAL);
            pluginManager.set(getServer(), new SmartPluginManager(this, getServer().getPluginManager()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        loadConfig();
        saveConfig();

        // Register custom shapeless recipe tests
        final ShapelessRecipe ice = new ShapelessRecipe(new ItemStack(Material.ICE, 4));
        ice.addIngredient(Material.WATER_BUCKET);
        ice.addIngredient(Material.SNOW_BALL);
        getServer().addRecipe(ice);

        // Register custom shaped recipe tests
        final ShapedRecipe ironfence = new ShapedRecipe(new ItemStack(Material.IRON_FENCE, 10));
        ironfence.shape("X X", "   ", "X X");
        ironfence.setIngredient('X', Material.BUCKET);
        getServer().addRecipe(ironfence);

        // Output some info so we can check all is well
        PluginDescriptionFile pdfFile = this.getDescription();
        System.out.println( pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!" );
    }

    public void loadConfig() {
        try {
            debugMessageMode = getConfig().getString("debugMessageMode", "console");
            debugTargets = getConfig().getString("debugTargets", "");

        } catch (Exception e) {
            getServer().getLogger().severe("Exception while loading DevBukkit/config.yml");
        }
    }

    public void saveConfig() {
        PluginManager pm = getServer().getPluginManager();
        getConfig().set("debugMessageMode", debugMessageMode);
        getConfig().set("debugTargets", debugTargets);
        try {
            getConfig().save(this.getDataFolder().getPath() + "/config.yml");
        } catch (IOException e) {
            getServer().getLogger().severe("Exception while saving DevBukkit/config.yml");
        }
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

        return commandName.equals("dev") && parseCommands(sender, player, trimmedArgs);
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
                debugMessage("Item data: " + (player.getItemInHand().getData() != null ? player.getItemInHand().getData().getData() : "null"));
                debugMessage("Item max stack size: " + (player.getItemInHand().getTypeId() == 0 ? 1 : player.getItemInHand().getMaxStackSize()));
                debugMessage("In Vehicle: " + player.isInsideVehicle());
                debugMessage("Vehicle: " + player.getVehicle());
            } else if (args[0].equalsIgnoreCase("reset")) {
                setDebugMode(BlockEvent.class,false,false);
                setDebugMode(EntityEvent.class,false,false);
                setDebugMode(PaintingEvent.class,false,false);
                setDebugMode(PlayerEvent.class,false,false);
                setDebugMode(ServerEvent.class,false,false);
                setDebugMode(VehicleEvent.class,false,false);
                setDebugMode(WeatherEvent.class,false,false);
                setDebugMode(WorldEvent.class,false,false);
                setCancelMode(BlockEvent.class,false,false);
                setCancelMode(EntityEvent.class,false,false);
                setCancelMode(PaintingEvent.class,false,false);
                setCancelMode(PlayerEvent.class,false,false);
                setCancelMode(ServerEvent.class,false,false);
                setCancelMode(VehicleEvent.class,false,false);
                setCancelMode(WeatherEvent.class,false,false);
                setCancelMode(WorldEvent.class,false,false);
            } else {
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
        sender.sendMessage(eventStatus("painting"));
        sender.sendMessage(eventStatus("player"));
        sender.sendMessage(eventStatus("server"));
        sender.sendMessage(eventStatus("vehicle"));
        sender.sendMessage(eventStatus("weather"));
        sender.sendMessage(eventStatus("world"));
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
        String message = e.getClass().getSimpleName();
        String isCancelled;

        if (Cancellable.class.isAssignableFrom(e.getClass())) {
            isCancelled = ((Cancellable) e).isCancelled() ? ChatColor.RED + "{C}" + ChatColor.WHITE : ChatColor.GREEN + "{C}"  + ChatColor.WHITE;
            message += " " + isCancelled;
        }

        if (e instanceof BlockEvent) {
            Block block = ((BlockEvent) e).getBlock();
            if (e instanceof BlockBreakEvent) {
                BlockBreakEvent blockbr = (BlockBreakEvent) e;
                Location location = block.getLocation();

                message += " " + blockbr.getPlayer().getName() + " broke a block of " + block.getType() + "[" + block.getData() + "] at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof BlockBurnEvent) {
                BlockBurnEvent blockbu = (BlockBurnEvent) e;
                Location location = block.getLocation();

                message += " " + block.getType() + "[" + block.getData() + "]" + " was burned at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            /*} else if (e instanceof BlockCanBuildEvent) {
                BlockCanBuildEvent blockcb = (BlockCanBuildEvent) e;
                Location location = block.getLocation(); */
            } else if (e instanceof BlockDamageEvent) {
                BlockDamageEvent blockd = (BlockDamageEvent) e;
                Location location = block.getLocation();

                message += " " + blockd.getPlayer().getName() + " damaged a block of " + block.getType() + "[" + block.getData() + "] at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof BlockDispenseEvent) {
                BlockDispenseEvent blockdi = (BlockDispenseEvent) e;
                Location location = block.getLocation();

                message += " " + block.getType() + " dispensed some " + blockdi.getItem().getType() + "[" + blockdi.getItem().getData().getData() + "] at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof BlockFadeEvent) {
                BlockFadeEvent blockfa = (BlockFadeEvent) e;

                message += " " + blockfa.getNewState().getType() + " is fading at (" + block.getX() + ", " + block.getY() + ", " + block.getZ()+ ")";
            } else if (e instanceof BlockFormEvent) {
                BlockFormEvent blockfo = (BlockFormEvent) e;

                if (e instanceof BlockSpreadEvent) {
                    BlockSpreadEvent blocksp = (BlockSpreadEvent) e;
                    Block blockFrom = blocksp.getSource();

                    message += " " + blocksp.getNewState().getType() + " spread from (" + blockFrom.getX() + ", " + blockFrom.getY() + ", " + blockFrom.getZ() + ") to (" + block.getX() + ", " + block.getY() + ", " + block.getZ()+ ")";
                } else {
                    message += " " + blockfo.getNewState().getType() + " formed at (" + block.getX() + ", " + block.getY() + ", " + block.getZ()+ ")";
                }
            /*} else if (e instanceof BlockFromToEvent) {*/
            } else if (e instanceof BlockIgniteEvent) {
                BlockIgniteEvent blockig = (BlockIgniteEvent) e;
                Location location = block.getLocation();

                message += " " + block.getType() + "[" + block.getData() + "]" + " ignited at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            /*} else if (e instanceof BlockPhysicsEvent) {
            } else if (e instanceof BlockPistonEvent) {*/
            } else if (e instanceof BlockPlaceEvent) {
                BlockPlaceEvent blockpl = (BlockPlaceEvent) e;
                Location location = block.getLocation();

                message += " " + blockpl.getPlayer().getName() + " placed a block of " + block.getType() + "[" + block.getData() + "] at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof BlockRedstoneEvent) {
                BlockRedstoneEvent blockred = (BlockRedstoneEvent) e;

                message += " (" + block.getX() + ", " + block.getY() + ", " + block.getZ()+ ") " + block.getType() + "{" + block.getData() + "}, Old: " + blockred.getOldCurrent() + ", New: " + blockred.getNewCurrent();
            /*} else if (e instanceof EntityBlockFormEvent) {
            } else if (e instanceof LeavesDecayEvent) {*/
            } else if (e instanceof SignChangeEvent) {
                SignChangeEvent signc = (SignChangeEvent) e;

                message += " A sign was modified to: " + signc.getLine(0) + " // " + signc.getLine(1) + " // " + signc.getLine(2) + " // " + signc.getLine(3);
            } else {
                BlockEvent blockEvent = (BlockEvent) e;

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
                message += " was damaged (" + ((EntityDamageEvent) e).getDamage() + ")";
                if (e instanceof EntityDamageByBlockEvent) {
                    EntityDamageByBlockEvent entitydbb = (EntityDamageByBlockEvent) e;
                    Block block = entitydbb.getDamager();

                    message += " by " + ((block == null) ? "'null'" : "a block of " + block.getClass().getSimpleName());
                } else if (e instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent entitydbe = (EntityDamageByEntityEvent) e;
                    Entity damager = entitydbe.getDamager();

                    message += " by ";
                    if (damager != null) {
                        message += damager.getClass().getSimpleName() + "[" + damager .getEntityId() + "]";
                    } else {
                        message += "a non-existant damager";
                    }
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

                message += " spawned at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")[" + location.getWorld().getName() + "] Reason: " + creaturesp.getSpawnReason();
            } else if (e instanceof EntityRegainHealthEvent) {
                EntityRegainHealthEvent entityrh = (EntityRegainHealthEvent) e;

                message += " regained " + entityrh.getAmount() + " health. Reason: " + entityrh.getRegainReason();
            } else if (e instanceof FoodLevelChangeEvent) {
                FoodLevelChangeEvent foodlc = (FoodLevelChangeEvent) e;

                message += "'s hunger changed (" + foodlc.getFoodLevel() + ")";
            } else if (e instanceof EntityChangeBlockEvent) {
                EntityChangeBlockEvent entitych = (EntityChangeBlockEvent) e;
                Location location = entitych.getEntity().getLocation();

                message += " changed a block of " + entitych.getBlock().getType().name() + " at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof SheepRegrowWoolEvent) {
                SheepRegrowWoolEvent sheeprw = (SheepRegrowWoolEvent) e;
                Location location = sheeprw.getEntity().getLocation();

                message += " regrew its wool at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof  SheepDyeWoolEvent) {
                SheepDyeWoolEvent sheepdw = (SheepDyeWoolEvent) e;
                Location location = sheepdw.getEntity().getLocation();

                message += " was dyed " + sheepdw.getColor().name() + " at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            }
        } else if (e instanceof PlayerEvent) {
            Player player = ((PlayerEvent) e).getPlayer();
            byte itemInHandData;
            short itemInHandDurability;
            message += " "+player.getName();

            if (e instanceof PlayerAnimationEvent) {
                PlayerAnimationEvent playeran = (PlayerAnimationEvent) e;

                if (playeran.getAnimationType() == org.bukkit.event.player.PlayerAnimationType.ARM_SWING) {
                    message += " swung their arm";
                }
            } else if (e instanceof PlayerBedEnterEvent) {
                PlayerBedEnterEvent playerbede = (PlayerBedEnterEvent) e;
                Block bed = playerbede.getBed();
                Location location = bed.getLocation();

                message += " entered a bed at: " + location.getX() + ", " + location.getY() + ", " + location.getZ();
            } else if (e instanceof PlayerBedLeaveEvent) {
                PlayerBedLeaveEvent playerbedl = (PlayerBedLeaveEvent) e;
                Block bed = playerbedl.getBed();
                Location location = bed.getLocation();

                message += " left a bed at: " + location.getX() + ", " + location.getY() + ", " + location.getZ();
            } else if (e instanceof PlayerBucketEmptyEvent) {
                // Todo: construct debug message.
                PlayerBucketEmptyEvent playerbe = (PlayerBucketEmptyEvent) e;
            } else if (e instanceof PlayerBucketFillEvent) {
                // Todo: construct debug message.
                PlayerBucketFillEvent playerbf = (PlayerBucketFillEvent) e;
            } else if (e instanceof PlayerChatEvent) {
                PlayerChatEvent playerch = (PlayerChatEvent) e;
                message += " said: " + playerch.getMessage();
            } else if (e instanceof PlayerCommandPreprocessEvent) {
                PlayerCommandPreprocessEvent playercp = (PlayerCommandPreprocessEvent) e;

                message += " typed: " + playercp.getMessage();
            } else if (e instanceof PlayerDropItemEvent) {
                PlayerDropItemEvent playerdr = (PlayerDropItemEvent) e;
                Item droppedItem = playerdr.getItemDrop();
                Location location = playerdr.getItemDrop().getLocation();

                message += " dropped " + droppedItem.getItemStack().getType() + " at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof PlayerEggThrowEvent) {
                PlayerEggThrowEvent playeret = (PlayerEggThrowEvent) e;
                Egg egg = playeret.getEgg();
                Location location = egg.getLocation();

                message += " threw a " + playeret.getHatchType() + " egg at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")" + (playeret.isHatching() ? " that will " : " that will not ") + "hatch";
            } else if (e instanceof PlayerInteractEntityEvent) {
                PlayerInteractEntityEvent playerite = (PlayerInteractEntityEvent) e;
                Entity entity = playerite.getRightClicked();
                Location location = entity.getLocation();

                message += " right clicked a " + entity.getClass().getSimpleName() + " at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof PlayerInteractEvent) {
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
            } else if (e instanceof PlayerItemHeldEvent) {
                PlayerItemHeldEvent playerih = (PlayerItemHeldEvent) e;
                int previousSlot = playerih.getPreviousSlot();
                int newSlot = playerih.getNewSlot();
                ItemStack from = player.getInventory().getItem(previousSlot);
                ItemStack to = player.getInventory().getItem(newSlot);

                message += " switched the item they were holding from: " + from.getType() + "[Slot" + previousSlot + "], to: " + to.getType() + "[Slot" + newSlot + "]";
            } else if (e instanceof PlayerJoinEvent) {
                PlayerJoinEvent playerj = (PlayerJoinEvent) e;
                Location location = player.getLocation();

                message += " joined at ([" + location.getWorld().getName() + "] " + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof PlayerKickEvent) {
                PlayerKickEvent playerk = (PlayerKickEvent) e;

                message += " was kicked for: " + playerk.getReason();
            } else if (e instanceof PlayerLoginEvent) {
                PlayerLoginEvent playerlo = (PlayerLoginEvent) e;

                message += " tried to login. Result: " + playerlo.getResult();
            } else if (e instanceof PlayerMoveEvent) {
                PlayerMoveEvent playermo = (PlayerMoveEvent) e;
                Location from = playermo.getFrom();
                Location to = playermo.getTo();

                if (e instanceof PlayerTeleportEvent) {
                    PlayerTeleportEvent playerte = (PlayerTeleportEvent) e;

                    message += " from: [" + from.getWorld().getName()  + "] " + "(" + from.getX() + ", " + from.getY() + ", " + from.getZ() + "); to: [" + to.getWorld().getName()  + "] " + "(" + to.getX() + ", " + to.getY() + ", " + to.getZ() + ")";
                } else if (e instanceof PlayerPortalEvent) {
                    PlayerPortalEvent playerpo = (PlayerPortalEvent) e;

                    message += " from: [" + from.getWorld().getName()  + "] " + "(" + from.getX() + ", " + from.getY() + ", " + from.getZ() + "); to: [" + to.getWorld().getName()  + "] " + "(" + to.getX() + ", " + to.getY() + ", " + to.getZ() + ")";
                } else {
                    message += " from (" + from.getX() + ", " + from.getY() + ", " + from.getZ() + "); to (" + to.getX() + ", " + to.getY() + ", " + to.getZ() + ")";
                }
            } else if (e instanceof PlayerPickupItemEvent) {
                PlayerPickupItemEvent playerpi = (PlayerPickupItemEvent) e;
                Item item = playerpi.getItem();
                Location location = item.getLocation();

                message += " picked up " + item.getItemStack().getType() + "[" + item.getItemStack().getAmount() +  "]" + " at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ") with " +  playerpi.getRemaining() + " remaining on the ground";
            } else if (e instanceof PlayerPreLoginEvent) {
                // Todo: construct debug message.
                PlayerPreLoginEvent playerpl = (PlayerPreLoginEvent) e;
            } else if (e instanceof PlayerQuitEvent) {
                PlayerQuitEvent playerq = (PlayerQuitEvent) e;
                Location location = player.getLocation();

                message += " quit the server while located at ([" + location.getWorld().getName() + "] " + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof PlayerRespawnEvent) {
                PlayerRespawnEvent playerr = (PlayerRespawnEvent) e;
                Location location = playerr.getRespawnLocation();

                message += " (re)spawned at (" + location.getX() + ", " + location.getY() + ", " + location.getZ() + ")";
            } else if (e instanceof PlayerToggleSneakEvent) {
                PlayerToggleSneakEvent playerts = (PlayerToggleSneakEvent) e;

                message += " toggled sneaking " + (player.isSneaking() ? " on" : " off");
            } else if (e instanceof PlayerFishEvent) {
                PlayerFishEvent playerfe = (PlayerFishEvent) e;

                message += " tried fishing at (" + player.getLocation().getX() + ", " + player.getLocation().getY() + ", " + player.getLocation().getZ() + "). State: " + playerfe.getState() + ". Caught: " +  (playerfe.getCaught() == null ? "nothing" : playerfe.getCaught().getClass().getSimpleName());
            } else if (e instanceof PlayerShearEntityEvent) {
                PlayerShearEntityEvent playersh = (PlayerShearEntityEvent) e;

                message += " sheared a " + playersh.getEntity().getClass().getSimpleName();
            }
        } else if (e instanceof FurnaceBurnEvent) {
                FurnaceBurnEvent furnaceb = (FurnaceBurnEvent) e;
                Block furnace = furnaceb.getFurnace();

                message += " (" + furnace.getX() + ", " + furnace.getY() + ", " + furnace.getZ() + ") Fuel: " + furnaceb.getFuel().getType() + "; Burning time: " + furnaceb.getBurnTime();
        } else if (e instanceof FurnaceSmeltEvent) {
            FurnaceSmeltEvent furnaces = (FurnaceSmeltEvent) e;
            Block furnace = furnaces.getFurnace();

            message += " (" + furnace.getX() + ", " + furnace.getY() + ", " + furnace.getZ() + ") Source: " + furnaces.getSource().getType() + "; Result: " + furnaces.getResult().getType();
        } else if (e instanceof WorldEvent) {
            if (e instanceof ChunkEvent) {
                Chunk chunk = ((ChunkEvent) e).getChunk();
                if (e instanceof ChunkLoadEvent) {
                    ChunkLoadEvent chunkl = (ChunkLoadEvent) e;

                } else if (e instanceof ChunkUnloadEvent) {
                    ChunkUnloadEvent chunku = (ChunkUnloadEvent) e;

                    message += " chunk unloaded at (" + chunk.getX() + ", " + chunk.getZ() + ")";

                }
            }
        } else if (e instanceof VehicleEvent) {
            if (e instanceof VehicleCollisionEvent) {
                VehicleCollisionEvent veh = (VehicleCollisionEvent) e;
                Vehicle vehicle = veh.getVehicle();

                if (e instanceof VehicleBlockCollisionEvent) {
                    VehicleBlockCollisionEvent vehbco = (VehicleBlockCollisionEvent) e;
                    Block block = vehbco.getBlock();

                    message += " vehicle collided with a block of " + block.getType() + " at (" + vehicle.getLocation().getX() + ", " + vehicle.getLocation().getY() + ", " + vehicle.getLocation().getZ() + ") [" + vehicle.getWorld().getName() + "]";
                } else if (e instanceof VehicleEntityCollisionEvent) {
                    VehicleEntityCollisionEvent veheco = (VehicleEntityCollisionEvent) e;

                    message += " vehicle collided with a " + veheco.getEntity().getClass().getSimpleName() + " at (" + vehicle.getLocation().getX() + ", " + vehicle.getLocation().getY() + ", " + vehicle.getLocation().getZ() + ") [" + vehicle.getWorld().getName() + "]";
                } else {
                    message += " vehicle collided at (" + vehicle.getLocation().getX() + ", " + vehicle.getLocation().getY() + ", " + vehicle.getLocation().getZ() + ") [" + vehicle.getWorld().getName() + "]";
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