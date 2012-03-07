package com.cogito.bukkit.dev;

import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Squid;

import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;

import org.bukkit.event.painting.PaintingBreakEvent;
import org.bukkit.event.painting.PaintingPlaceEvent;

/**
 * Handle all Entity related events
 */
public class DevEntityListener implements Listener {
    private final DevBukkit plugin;

    public DevEntityListener(DevBukkit instance) {
        plugin = instance;
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onCreeperPower(CreeperPowerEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityCombustByBlock(EntityCombustByBlockEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityCombustByEntity(EntityCombustByEntityEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Squid) {
            List<ItemStack> drops = event.getDrops();
            int count = drops.get(0).getAmount();
            drops.clear();
            drops.add(new ItemStack(Material.APPLE, count));
        }

        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityInteract(EntityInteractEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityPortalEnter(EntityPortalEnterEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityTame(EntityTameEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityTarget(EntityTargetEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityTeleport(EntityTeleportEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
   public void onFoodLevelChange(FoodLevelChangeEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onPigZap(PigZapEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onPaintingBreak(PaintingBreakEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onPaintingPlace(PaintingPlaceEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onSheepRegrowWool(SheepRegrowWoolEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onSheepDyeWool(SheepDyeWoolEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        plugin.cancelEvent(event);
        plugin.debugMessage(event);
        plugin.godMode(event);
    }
}