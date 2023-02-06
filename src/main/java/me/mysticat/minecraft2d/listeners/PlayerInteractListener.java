package me.mysticat.minecraft2d.listeners;

import me.mysticat.minecraft2d.enums.GameState;
import me.mysticat.minecraft2d.handlers.PlayerAnimationHandler;
import me.mysticat.minecraft2d.handlers.GameHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void playerInteractListener(@NotNull PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();
        //left click
        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            PlayerAnimationHandler.isPunching = true;
            GameHandler.breakBlock(player);
            GameHandler.attackEntities(player);
        }
        //right click
        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (GameHandler.globalGameState == GameState.MINECRAFT) {
                GameHandler.placeBlock(player);
            }
            else if (GameHandler.globalGameState == GameState.MARIO) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5, 4, true, false));
            }
        }
    }

}
