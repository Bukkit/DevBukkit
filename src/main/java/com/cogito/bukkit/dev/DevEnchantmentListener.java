package com.cogito.bukkit.dev;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;

/**
 * Handle all Enchantment related events
 */
public class DevEnchantmentListener implements Listener {
    private final DevBukkit plugin;

    public DevEnchantmentListener(DevBukkit instance) {
        plugin = instance;
    }

    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }
}