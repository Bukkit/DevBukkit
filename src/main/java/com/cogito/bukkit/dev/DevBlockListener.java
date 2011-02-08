package com.cogito.bukkit.dev;

import org.bukkit.Material;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockInteractEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockRightClickEvent;
import org.bukkit.event.block.LeavesDecayEvent;

public class DevBlockListener extends BlockListener {
    private final DevBukkit plugin;
    
    public DevBlockListener(DevBukkit instance) {
        plugin = instance;
    }

    public void onBlockDamage(BlockDamageEvent event) {
        plugin.debugMessage(event);
    }

    public void onBlockCanBuild(BlockCanBuildEvent event) {
        plugin.debugMessage(event);
    }

    public void onBlockFlow(BlockFromToEvent event) {
        plugin.debugMessage(event);
    }

    public void onBlockIgnite(BlockIgniteEvent event) {
        plugin.debugMessage(event);
    }

    public void onBlockPhysics(BlockPhysicsEvent event) {
        plugin.debugMessage(event);
    }

    public void onBlockPlace(BlockPlaceEvent event) {
        plugin.debugMessage(event);
    }

    public void onBlockInteract(BlockInteractEvent event) {
        plugin.debugMessage(event);
    }

    public void onBlockRightClick(BlockRightClickEvent event) {
        if (event.getItemInHand().getType() == Material.SLIME_BALL) {
            plugin.debugMessage("Block name: " + event.getBlock().getType());
            plugin.debugMessage("Block location: " + event.getBlock().getX() + ", " + event.getBlock().getY() + ", " + event.getBlock().getZ());
            plugin.debugMessage("Block data: " + event.getBlock().getData());
            plugin.debugMessage("Block LightLevel: " + event.getBlock().getLightLevel());
            plugin.debugMessage("Block Chunk: " + event.getBlock().getChunk().toString());
        }
        plugin.debugMessage(event);
    }

    public void onBlockRedstoneChange(BlockFromToEvent event) {
        plugin.debugMessage(event);
    }

    public void onLeavesDecay(LeavesDecayEvent event) {
        plugin.debugMessage(event);
    }
    
    public void onBlockBurn(BlockBurnEvent event) {
        plugin.debugMessage(event);
    }

    public void onBlockBreak(BlockBreakEvent event) {
        plugin.debugMessage(event);
    }

}
