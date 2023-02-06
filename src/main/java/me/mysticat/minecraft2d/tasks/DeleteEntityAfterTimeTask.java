package me.mysticat.minecraft2d.tasks;

import me.mysticat.minecraft2d.Main;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;

public class DeleteEntityAfterTimeTask extends BukkitRunnable {

    private final Entity entity;

    public DeleteEntityAfterTimeTask(Entity entityToDelete) {
        this.entity = entityToDelete;
        runTaskTimer(Main.plugin, 20L, 1L);
    }

    @Override
    public void run() {
        entity.remove();
        cancel();
    }

}
