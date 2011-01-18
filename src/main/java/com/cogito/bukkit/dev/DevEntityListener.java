
package com.cogito.bukkit.dev;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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
        if(plugin.debug){
            System.out.println(event.getCause() + "("+event.getDamage()+"): "
                    + event.getEntity().getClass().getSimpleName()
                    + " was damaged by block "
                    + event.getDamager().getClass().getSimpleName()
                    + "."
                    );
        }
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
        
    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(plugin.debug){
        System.out.println(
                event.getCause() + "("+event.getDamage()+"): "
                + event.getEntity().getClass().getSimpleName()
                + " was damaged by "
                + event.getDamager().getClass().getSimpleName()
                + "."
                );
        }
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }
    
    public void onEntityDamageByProjectile(EntityDamageByProjectileEvent event) {
        if(plugin.debug){
            System.out.println(
                    event.getCause() + "("+event.getDamage()+"): "
                    + event.getEntity().getClass().getSimpleName()
                    + " was damaged by a projectile ("
                    + event.getDamager().getClass().getSimpleName()
                    + ")."
                    );
        }
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }
    
    public void onEntityCombust(EntityCombustEvent event) {
        if(plugin.debug){
            System.out.println(
                    event.getEntity() + ": "
                    + event.getEntity().getClass().getSimpleName()
                    + " caught fire."
                    );
        }
        if(event.getEntity() instanceof Player){
            if(plugin.isGod((Player) event.getEntity())){
                event.setCancelled(true);
            }
        }
    }

    public void onEntityDamage(EntityDamageEvent event) {
    }

    
}
