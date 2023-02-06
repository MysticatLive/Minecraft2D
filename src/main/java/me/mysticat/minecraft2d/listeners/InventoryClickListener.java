package me.mysticat.minecraft2d.listeners;

import me.mysticat.minecraft2d.enums.GameState;
import me.mysticat.minecraft2d.handlers.GameHandler;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void inventoryClickListener(InventoryClickEvent event) {
        Entity entity = event.getWhoClicked();
        if (entity instanceof Player && ((Player) entity).getGameMode() == GameMode.ADVENTURE && GameHandler.globalGameState == GameState.MINECRAFT) {
            event.setCancelled(true);
        }
    }

}
