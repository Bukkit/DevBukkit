package com.cogito.bukkit.dev;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import org.bukkit.inventory.ItemStack;
import org.bukkit.event.inventory.FurnaceBurnEvent;
import org.bukkit.event.inventory.FurnaceSmeltEvent;

/**
 * Handle all Inventory related events
 */
public class DevInventoryListener implements Listener {
    private final DevBukkit plugin;

    public DevInventoryListener(DevBukkit instance) {
        plugin = instance;
    }

    @EventHandler
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);

        if (event.getFuel().getType().equals(Material.WEB)) {
            event.setBurnTime(100);
        } else if (event.getFuel().getType().equals(Material.BEDROCK)) {
            event.setBurning(false);
        } else if (event.getFuel().getType().equals(Material.STICK)) {
            event.setBurnTime(1000);
        } else if (event.getFuel().getType().equals(Material.LAVA_BUCKET)) {
            event.setBurning(false);
        }
    }

    @EventHandler
    public void onFurnaceSmelt(FurnaceSmeltEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);

        if (event.getSource().getType().equals(Material.SAND)) {
            event.setResult(new ItemStack(Material.BONE, 2, (short) 0));
        } else if (event.getSource().getType().equals(Material.APPLE)) {
            event.setResult(new ItemStack(Material.GOLDEN_APPLE, 2, (short) 0));
        }
    }
}
