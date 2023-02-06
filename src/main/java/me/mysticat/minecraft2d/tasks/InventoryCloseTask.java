package me.mysticat.minecraft2d.tasks;

import me.mysticat.minecraft2d.Main;
import me.mysticat.minecraft2d.handlers.Engine;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryCloseTask extends BukkitRunnable {

    private final PlayerInventory playerInventory;

    public InventoryCloseTask(PlayerInventory playerInventory) {
        this.playerInventory = playerInventory;
        runTaskLater(Main.plugin, 1L);
    }

    @Override
    public void run() {
        Engine.announceToAllPlayers("3");
        playerInventory.close();
        cancel();
    }

}
