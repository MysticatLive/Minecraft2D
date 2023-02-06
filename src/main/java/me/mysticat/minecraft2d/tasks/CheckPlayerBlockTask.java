package me.mysticat.minecraft2d.tasks;

import me.mysticat.minecraft2d.Main;
import me.mysticat.minecraft2d.handlers.Engine;
import me.mysticat.minecraft2d.handlers.GameHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;

public class CheckPlayerBlockTask extends BukkitRunnable {

    public CheckPlayerBlockTask() {
        runTaskTimer(Main.plugin, 0L, 1L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Location location = player.getLocation();
            Collection<Entity> nearbyEntities = location.getNearbyEntities(1, 1, 1);
            nearbyEntities.forEach(entity -> {
                if (entity instanceof ItemFrame) {
                    ItemFrame itemFrame = (ItemFrame) entity;
                    ItemStack framedItem = itemFrame.getItem();
                    if (framedItem.getType() != Material.AIR) {
                        if (framedItem.getType() == Material.GOLDEN_PICKAXE) {
                            framedItem = new ItemStack(GameHandler.MINECRAFT_PICKAXE);
                        } else if (framedItem.getType() == Material.GOLDEN_SWORD) {
                            framedItem = new ItemStack(GameHandler.MINECRAFT_SWORD);
                        }
                        player.getInventory().addItem(framedItem);
                        itemFrame.setItem(new ItemStack(Material.AIR));
                        Engine.playSoundAtPlayer(player, GameHandler.MINECRAFT_PICKUP_SOUND, 1, 1);
                    }
                }
            });
        }
    }

}
