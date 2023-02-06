package me.mysticat.minecraft2d.listeners;

import me.mysticat.minecraft2d.enums.Direction;
import me.mysticat.minecraft2d.handlers.Engine;
import me.mysticat.minecraft2d.handlers.GameHandler;
import me.mysticat.minecraft2d.handlers.PlayerAnimationHandler;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.jetbrains.annotations.NotNull;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void playerMoveListener(@NotNull PlayerMoveEvent event) {
        //init values
        Player player = event.getPlayer();
        PlayerAnimationHandler.xSpeed = getXSpeed(event);
        PlayerAnimationHandler.ySpeed = getYSpeed(event);
        PlayerAnimationHandler.zSpeed = getZSpeed(event);
        PlayerAnimationHandler.direction = getDirection(PlayerAnimationHandler.xSpeed);
        if (((Entity)player).isOnGround()) {
            PlayerAnimationHandler.ySpeed = 0;
        }
        if (playerRanIntoBlock(player)) {
            PlayerAnimationHandler.xSpeed = 0;
        }
        //debug values
        debug();
    }

    private static boolean playerRanIntoBlock(Player player) {
        double distance = 0.6;
        double blockX = (PlayerAnimationHandler.direction == Direction.RIGHT) ? distance : -distance;
        Block inTheWay = player.getLocation().add(blockX, 0, 0).getBlock();
        return ((!inTheWay.isPassable() || !inTheWay.getRelative(0, 1, 0).isPassable() && inTheWay.getType() != Material.OAK_DOOR));
    }

    private static Direction getDirection(double speed) {
        if (speed > 0) {
            return Direction.RIGHT;
        }
        else if (speed < 0) {
            return Direction.LEFT;
        }
        return PlayerAnimationHandler.direction;
    }

    private static double getXSpeed(@NotNull PlayerMoveEvent event) {
        return event.getTo().getX() - event.getFrom().getX();
    }

    private static double getYSpeed(@NotNull PlayerMoveEvent event) {
        return event.getTo().getY() - event.getFrom().getY();
    }

    private static double getZSpeed(@NotNull PlayerMoveEvent event) {
        return event.getTo().getZ() - event.getFrom().getZ();
    }

    private void debug() {
        if (GameHandler.DEBUG_ANIM) Engine.announceToAllPlayers(
                "dir: " + PlayerAnimationHandler.direction
                        + "\nspd: " + PlayerAnimationHandler.xSpeed
                        + "\nair: " + PlayerAnimationHandler.ySpeed
                        + "\n------\n");
    }

}
