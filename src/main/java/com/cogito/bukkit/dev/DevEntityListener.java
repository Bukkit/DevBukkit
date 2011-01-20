
package com.cogito.bukkit.dev;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntityListener;

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
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
        
    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }
    
    public void onEntityDamageByProjectile(EntityDamageByProjectileEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }
    
    public void onEntityCombust(EntityCombustEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }

    public void onEntityDamage(EntityDamageEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }
    
    public void onEntityExplode(EntityExplodeEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }

    
}
