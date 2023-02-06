package me.mysticat.minecraft2d.listeners;

import me.mysticat.minecraft2d.enums.GameState;
import me.mysticat.minecraft2d.handlers.GameHandler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleSwimEvent;

public class EntityToggleSwimListener implements Listener {

    @EventHandler
    public void entityToggleSwimListener(EntityToggleSwimEvent event) {
        if (GameHandler.globalGameState == GameState.MINECRAFT) {
            event.setCancelled(true);
        }
    }

}
