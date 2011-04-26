package com.cogito.bukkit.dev;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInventoryEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

/**
 * Handle all Player related events
 *
 * @author EvilSeph
 */
public class DevPlayerListener extends PlayerListener {
    private final DevBukkit plugin;

    public DevPlayerListener(DevBukkit instance) {
        plugin = instance;
    }

    public void onPlayerAnimation(PlayerAnimationEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerBedLeave(PlayerBedLeaveEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerBucketFill(PlayerBucketFillEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerChat(PlayerChatEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerDropItem(PlayerDropItemEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerEggThrow(PlayerEggThrowEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getPlayer().getItemInHand().getType() == Material.SLIME_BALL && event.getAction() == Action.RIGHT_CLICK_BLOCK) {
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

    public void onPlayerInventory(PlayerInventoryEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerItemHeld(PlayerItemHeldEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerKick(PlayerKickEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerLogin(PlayerLoginEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerMove(PlayerMoveEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerPreLogin(PlayerPreLoginEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerRespawn(PlayerRespawnEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerTeleport(PlayerTeleportEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }

    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        plugin.debugMessage(event);
        plugin.cancelEvent(event);
        plugin.godMode(event);
    }
}