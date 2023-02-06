package me.mysticat.minecraft2d.listeners;

import me.mysticat.minecraft2d.enums.GameState;
import me.mysticat.minecraft2d.handlers.GameHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerSwapHandItemsListener implements Listener {

    @EventHandler
    public void playerSwapHandItemsEvent(PlayerSwapHandItemsEvent event) {
        if (GameHandler.globalGameState == GameState.MINECRAFT) {
            event.setCancelled(true);
        }
    }

}
