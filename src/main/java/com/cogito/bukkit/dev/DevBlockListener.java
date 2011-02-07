package com.cogito.bukkit.dev;

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

/**
 * Handles all events thrown in relation to Blocks
 * 
 * @author durron597
 */
public class DevBlockListener extends BlockListener {
    private final DevBukkit plugin;
    
    public DevBlockListener(DevBukkit instance) {
        plugin = instance;
    }

    /**
     * Called when a block is damaged (or broken)
     *
     * @param event Relevant event details
     */
    public void onBlockDamage(BlockDamageEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
    }

    /**
     * Called when we try to place a block, to see if we can build it
     */
    public void onBlockCanBuild(BlockCanBuildEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
    }

    /**
     * Called when a block flows (water/lava)
     *
     * @param event Relevant event details
     */
    public void onBlockFlow(BlockFromToEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
    }

    /**
     * Called when a block gets ignited
     *
     * @param event Relevant event details
     */
    public void onBlockIgnite(BlockIgniteEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
    }

    /**
     * Called when block physics occurs
     *
     * @param event Relevant event details
     */
    public void onBlockPhysics(BlockPhysicsEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
    }

    /**
     * Called when a player places a block
     *
     * @param event Relevant event details
     */
    public void onBlockPlace(BlockPlaceEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
    }
    
    /**
     * Called when a block is interacted with
     * 
     * @param event Relevant event details
     */
    public void onBlockInteract(BlockInteractEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
    }

    /**
     * Called when a player right clicks a block
     *
     * @param event Relevant event details
     */
    public void onBlockRightClick(BlockRightClickEvent event) {
        if (event.getItemInHand().getTypeId() == 341) {
            plugin.debugMessage("Block name: " + event.getBlock().getType());
            plugin.debugMessage("Block location: " + event.getBlock().getX() + ", " + event.getBlock().getY() + ", " + event.getBlock().getZ());
            plugin.debugMessage("Block data: " + event.getBlock().getData());
            plugin.debugMessage("Block LightLevel: " + event.getBlock().getLightLevel());
            plugin.debugMessage("Block Chunk: " + event.getBlock().getChunk().toString());
        }
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
    }

    /**
     * Called when redstone changes
     * From: the source of the redstone change
     * To: The redstone dust that changed
     *
     * @param event Relevant event details
     */
    public void onBlockRedstoneChange(BlockFromToEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
    }

    /**
     * Called when leaves are decaying naturally
     *
     * @param event Relevant event details
     */
    public void onLeavesDecay(LeavesDecayEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
    }
    
    public void onBlockBurn(BlockBurnEvent event) {
        if(plugin.debug(event.getClass())){
            System.out.println(plugin.debugString(event));
        }
    }

}
