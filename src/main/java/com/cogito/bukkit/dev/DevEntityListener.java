
package com.cogito.bukkit.dev;

import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

import org.bukkit.entity.Entity;

/**
 * Handle all Player related events
 * 
 * @author Cogito
 */
public class DevEntityListener extends EntityListener {
    private final DevBukkit plugin;
    
    private boolean debug;

    public DevEntityListener(DevBukkit instance) {
        plugin = instance;
        debug = true;
    }
    
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        if(debug){
            String entityDescriptor = getDescriptor(event.getEntity());
            System.out.println("An instance of "+entityDescriptor+" was damaged by a block "+event.getDamager().getType().toString());
        }
    }

    private String getDescriptor(Entity entity) {
        // TODO work out how to do this well
        return entity.getClass().getName();
    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
    }
    
    public void onEntityDamageByProjectile(EntityDamageByProjectileEvent event) {
    }
    
    public void onEntityCombust(EntityCombustEvent event) {
    }
    
    public void onEntityDamage(EntityDamageEvent event) {
    }
}
