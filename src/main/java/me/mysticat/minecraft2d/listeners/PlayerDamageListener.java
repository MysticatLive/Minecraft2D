package me.mysticat.minecraft2d.listeners;

import me.mysticat.minecraft2d.enums.GameState;
import me.mysticat.minecraft2d.handlers.AnimationHandler;
import me.mysticat.minecraft2d.handlers.GameHandler;
import me.mysticat.minecraft2d.handlers.PlayerAnimationHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    public void playerDamageListener(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Player player = (Player)entity;
            if (GameHandler.globalGameState == GameState.MINECRAFT) {
                AnimationHandler.changeAnimationLeftRight(player, PlayerAnimationHandler.direction, 24, 23);
            }
        }
    }

}
