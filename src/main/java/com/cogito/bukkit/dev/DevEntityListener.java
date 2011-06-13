package com.cogito.bukkit.dev;

import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;

/**
 * Handle all Player related events
 */
public class DevEntityListener extends EntityListener {
    private final DevBukkit plugin;

    public DevEntityListener(DevBukkit instance) {
        plugin = instance;
    }

    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onEntityDamageByProjectile(EntityDamageByProjectileEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onEntityCombust(EntityCombustEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onEntityDamage(EntityDamageEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onEntityExplode(EntityExplodeEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onEntityDeath(EntityDeathEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onEntityTarget(EntityTargetEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }
    public void onEntityInteract(EntityInteractEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }
    public void onExplosionPrime(ExplosionPrimeEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }
}