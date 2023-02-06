package me.mysticat.minecraft2d.listeners;

import me.mysticat.minecraft2d.enums.GameState;
import me.mysticat.minecraft2d.handlers.Engine;
import me.mysticat.minecraft2d.handlers.GameHandler;
import me.mysticat.minecraft2d.tasks.InventoryCloseTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class InventoryOpenListener implements Listener {

    @EventHandler
    public void inventoryOpenListener(InventoryOpenEvent event) {
        Inventory inventory = event.getInventory();
        Engine.announceToAllPlayers("1");
        if (inventory instanceof PlayerInventory && GameHandler.globalGameState == GameState.MINECRAFT) {
            Engine.announceToAllPlayers("2");
            inventory.close();
            event.setCancelled(true);
            new InventoryCloseTask((PlayerInventory)inventory);
        }
    }

}
