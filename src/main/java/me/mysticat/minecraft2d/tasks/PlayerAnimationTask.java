package me.mysticat.minecraft2d.tasks;

import me.mysticat.minecraft2d.Main;
import me.mysticat.minecraft2d.handlers.PlayerAnimationHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerAnimationTask extends BukkitRunnable {

    public PlayerAnimationTask() {
        runTaskTimer(Main.plugin, 0L, 1L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerAnimationHandler.updateAnimation(player);
        }
    }

}
