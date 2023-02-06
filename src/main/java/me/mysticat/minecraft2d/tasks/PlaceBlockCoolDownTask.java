package me.mysticat.minecraft2d.tasks;

import me.mysticat.minecraft2d.Main;
import me.mysticat.minecraft2d.handlers.GameHandler;
import org.bukkit.scheduler.BukkitRunnable;

public class PlaceBlockCoolDownTask extends BukkitRunnable {

    public PlaceBlockCoolDownTask(int delayInTicks) {
        GameHandler.placeBlockCoolDownActive = true;
        runTaskTimer(Main.plugin, delayInTicks, 0L);
    }

    @Override
    public void run() {
        GameHandler.placeBlockCoolDownActive = false;
        cancel();
    }

}
