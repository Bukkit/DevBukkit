package com.cogito.bukkit.dev;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class DevPlayerListener extends PlayerListener {

    private final DevBukkit plugin;
    protected DevPlayerListener(DevBukkit plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand().getType() == Material.SLIME_BALL &&
                event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            plugin.debugMessage("Block name: " + event.getClickedBlock().getType());
            plugin.debugMessage("Block location: " + event.getClickedBlock().getX() + ", " + event.getClickedBlock().getY() + ", " + event.getClickedBlock().getZ());
            plugin.debugMessage("Block data: " + event.getClickedBlock().getData());
            plugin.debugMessage("Block LightLevel: " + event.getClickedBlock().getLightLevel());
            plugin.debugMessage("Block Chunk: " + event.getClickedBlock().getChunk().toString());
        }
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

}
