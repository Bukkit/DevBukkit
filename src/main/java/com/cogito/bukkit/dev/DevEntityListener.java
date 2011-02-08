
package com.cogito.bukkit.dev;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.EntityTargetEvent.TargetReason;

/**
 * Handle all Player related events
 * 
 * @author Cogito
 */
public class DevEntityListener extends EntityListener {
    private final DevBukkit plugin;

    public DevEntityListener(DevBukkit instance) {
        plugin = instance;
    }
    
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        plugin.debugMessage(event);
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
        
    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        plugin.debugMessage(event);
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }
    
    public void onEntityDamageByProjectile(EntityDamageByProjectileEvent event) {
        plugin.debugMessage(event);
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }
    
    public void onEntityCombust(EntityCombustEvent event) {
        plugin.debugMessage(event);
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }

    public void onEntityDamage(EntityDamageEvent event) {
        plugin.debugMessage(event);
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }
    
    public void onEntityExplode(EntityExplodeEvent event) {
        plugin.debugMessage(event);
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }

    public void onEntityDeath(EntityDeathEvent event) {
        plugin.debugMessage(event);
    }

    public void onEntityTarget(EntityTargetEvent event) {
        plugin.debugMessage(event);
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity()) && event.getReason() != TargetReason.TARGET_ATTACKED_ENTITY){
                event.setCancelled(true);
            }
        }
    }
}
