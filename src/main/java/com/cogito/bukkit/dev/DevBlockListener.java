package com.cogito.bukkit.dev;

import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.block.SignChangeEvent;

public class DevBlockListener extends BlockListener {
    private final DevBukkit plugin;

    public DevBlockListener(DevBukkit instance) {
        plugin = instance;
    }

    public void onBlockDamage(BlockDamageEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onBlockCanBuild(BlockCanBuildEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onBlockFromTo(BlockFromToEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onBlockIgnite(BlockIgniteEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onBlockPhysics(BlockPhysicsEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onBlockPlace(BlockPlaceEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onBlockRedstoneChange(BlockFromToEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onLeavesDecay(LeavesDecayEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onBlockBurn(BlockBurnEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onBlockBreak(BlockBreakEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onSignChange(SignChangeEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }
}