
package com.cogito.bukkit.dev;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.event.entity.EntityCombustEvent;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Handle all Player related events
 * 
 * @author Cogito
 */
public class DevEntityListener extends EntityListener {
    private final DevBukkit plugin;
    
    private boolean debug;
    private Map<Player, Boolean> gods;

    public DevEntityListener(DevBukkit instance) {
        plugin = instance;
        debug = false;
        gods = new HashMap<Player, Boolean>();
    }
    
    public void onEntityDamageByBlock(EntityDamageByBlockEvent event) {
        if(debug){
            System.out.println(event.getCause() + "("+event.getDamage()+"): "
                    + event.getEntity().getClass().getSimpleName()
                    + " was damaged by block "
                    + event.getDamager().getClass().getSimpleName()
                    + "."
                    );
        }
        if(event.getEntity() instanceof Player){
            event.setCancelled(true);
        }
        
    }

    private String getDescriptor(Entity entity) {
        // TODO work out how to do this well
        return entity.getClass().getName();
    }

    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(debug){
        System.out.println(
                event.getCause() + "("+event.getDamage()+"): "
                + event.getEntity().getClass().getSimpleName()
                + " was damaged by "
                + event.getDamager().getClass().getSimpleName()
                + "."
                );
        }
        if(event.getEntity() instanceof Player){
            event.setCancelled(true);
        }
    }
    
    public void onEntityDamageByProjectile(EntityDamageByProjectileEvent event) {
        if(debug){
            System.out.println(
                    event.getCause() + "("+event.getDamage()+"): "
                    + event.getEntity().getClass().getSimpleName()
                    + " was damaged by a projectile ("
                    + event.getDamager().getClass().getSimpleName()
                    + ")."
                    );
            }
            if(event.getEntity() instanceof Player){
                event.setCancelled(true);
            }
    }
    
    public void onEntityCombust(EntityCombustEvent event) {
        if(debug){
            System.out.println(
                    event.getEntity() + ": "
                    + event.getEntity().getClass().getSimpleName()
                    + " caught fire."
                    );
            }
            if(event.getEntity() instanceof Player){
                if(isGod((Player) event.getEntity())){
                    event.setCancelled(true);
                }
            }
    }
    
    private boolean isGod(Player player) {
        return gods.containsKey(player);
    }

    public void onEntityDamage(EntityDamageEvent event) {
    }

    public void debugToggle() {
        if(debug){
            debug = false;
        } else{
            debug = true;
        }
    }

    public void debugOn() {
        debug = true;
    }
    public void debugOff() {
        debug = false;
    }
    
    public void godMode(Player player, boolean iAmGod){
        gods.put(player, Boolean.valueOf(iAmGod));
    }
}
