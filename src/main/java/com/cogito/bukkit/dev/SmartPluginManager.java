package com.cogito.bukkit.dev;

import java.io.File;
import java.util.Set;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.*;

public class SmartPluginManager implements PluginManager {

    private final DevBukkit plugin;
    private final PluginManager parent;

    public SmartPluginManager(DevBukkit plugin, PluginManager parent) {
        this.plugin = plugin;
        this.parent = parent;
    }

    public void registerInterface(Class<? extends PluginLoader> loader) throws IllegalArgumentException {
        parent.registerInterface(loader);
    }

    public Plugin getPlugin(String name) {
        return parent.getPlugin(name);
    }

    public Plugin[] getPlugins() {
        return parent.getPlugins();
    }

    public boolean isPluginEnabled(String name) {
        return parent.isPluginEnabled(name);
    }

    public boolean isPluginEnabled(Plugin plugin) {
        return parent.isPluginEnabled(plugin);
    }

    public Plugin loadPlugin(File file) throws InvalidPluginException, InvalidDescriptionException, UnknownDependencyException {
        return parent.loadPlugin(file);
    }

    public Plugin[] loadPlugins(File directory) {
        return parent.loadPlugins(directory);
    }

    public void disablePlugins() {
        parent.disablePlugins();
    }

    public void clearPlugins() {
        parent.clearPlugins();
    }

    public void callEvent(Event event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
        parent.callEvent(event);
    }

    public void registerEvents(Listener listener, Plugin plugin) {
        parent.registerEvents(listener, plugin);
    }

    public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin) {
        parent.registerEvent(event, listener, priority, executor, plugin);
    }

    public void registerEvent(Class<? extends Event> event, Listener listener, EventPriority priority, EventExecutor executor, Plugin plugin, boolean ignoreCancelled) {
        parent.registerEvent(event, listener, priority, executor, plugin, ignoreCancelled);
    }

    public void enablePlugin(Plugin plugin) {
        parent.enablePlugin(plugin);
    }

    public void disablePlugin(Plugin plugin) {
        parent.disablePlugin(plugin);
    }

    public Permission getPermission(String name) {
        return parent.getPermission(name);
    }

    public void addPermission(Permission perm) {
        parent.addPermission(perm);
    }

    public void removePermission(Permission perm) {
        parent.removePermission(perm);
    }

    public void removePermission(String name) {
        parent.removePermission(name);
    }

    public Set<Permission> getDefaultPermissions(boolean op) {
        return parent.getDefaultPermissions(op);
    }

    public void recalculatePermissionDefaults(Permission perm) {
        parent.recalculatePermissionDefaults(perm);
    }

    public void subscribeToPermission(String permission, Permissible permissible) {
        parent.subscribeToPermission(permission, permissible);
    }

    public void unsubscribeFromPermission(String permission, Permissible permissible) {
        parent.unsubscribeFromPermission(permission, permissible);
    }

    public Set<Permissible> getPermissionSubscriptions(String permission) {
        return parent.getPermissionSubscriptions(permission);
    }

    public void subscribeToDefaultPerms(boolean op, Permissible permissible) {
        parent.subscribeToDefaultPerms(op, permissible);
    }

    public void unsubscribeFromDefaultPerms(boolean op, Permissible permissible) {
        parent.unsubscribeFromDefaultPerms(op, permissible);
    }

    public Set<Permissible> getDefaultPermSubscriptions(boolean op) {
        return parent.getDefaultPermSubscriptions(op);
    }

    public Set<Permission> getPermissions() {
        return parent.getPermissions();
    }

    public boolean useTimings() {
        return parent.useTimings();
    }
}
