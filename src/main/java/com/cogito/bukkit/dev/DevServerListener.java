package com.cogito.bukkit.dev;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.MapInitializeEvent;

/**
 * Handle all Entity related events
 */
public class DevServerListener implements Listener {
    private final DevBukkit plugin;

    public DevServerListener(DevBukkit instance) {
        plugin = instance;
    }

    @EventHandler
    public void onMapInitialize(MapInitializeEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }
}