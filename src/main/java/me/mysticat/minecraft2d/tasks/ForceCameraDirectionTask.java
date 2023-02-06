package me.mysticat.minecraft2d.tasks;

import me.mysticat.minecraft2d.Main;
import me.mysticat.minecraft2d.handlers.GameHandler;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ForceCameraDirectionTask extends BukkitRunnable {

    private final int lockedYaw;
    private final int lockedPitch;
    private final double lockedZAxisCoordinate;

    public ForceCameraDirectionTask(int lockedYaw, int lockedPitch, double lockedZAxisCoordinate, double zOffset) {
        this.lockedYaw = (lockedYaw == 180) ? -180 : lockedYaw;
        this.lockedPitch = (lockedPitch == 180) ? -180 : lockedPitch;
        this.lockedZAxisCoordinate = lockedZAxisCoordinate + zOffset;
        runTaskTimer(Main.plugin, 0L, 4L);
    }

    @Override
    public void run() {
        //force players
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (GameHandler.playerIsPlayableGameMode(player)) {
                Location location = player.getLocation();
                if (location.getYaw() != lockedYaw || location.getPitch() != lockedPitch || location.getZ() != lockedZAxisCoordinate) {
                    if (GameHandler.DEBUG_DIRECTION) debugDirection(player, location);
                    resetDirection(player, location);
                }
            }
        }
        //force zombies
        List<Zombie> deadZombies = new ArrayList<>();
        GameHandler.ZOMBIE_ANIM_LIST.forEach((zombie, armorStand) -> {
            if (!zombie.isDead()) {
                Location location = zombie.getLocation();
                location.setYaw(lockedYaw);
                location.setPitch(lockedPitch);
                location.setY(location.getY() + 0.1);
                location.setZ(lockedZAxisCoordinate);
                armorStand.teleport(location);
            } else {
                deadZombies.add(zombie);
            }
        });
        deadZombies.forEach(zombie -> {
            GameHandler.ZOMBIE_ANIM_LIST.get(zombie).remove();
            GameHandler.ZOMBIE_ANIM_LIST.remove(zombie);
        });
    }

    private void resetDirection(@NotNull Player player, @NotNull Location location) {
        Vector velocity = player.getVelocity();
        location.setYaw(lockedYaw);
        location.setPitch(lockedPitch);
        location.setZ(lockedZAxisCoordinate);
        player.teleport(location);
        player.setVelocity(velocity);
    }

    @SuppressWarnings("unused")
    private void debugDirection(Player player, Location location) {
        player.sendMessage("Your Yaw/Pitch/Z: " + location.getYaw() + "/" + location.getPitch() + "/" + location.getZ());
        player.sendMessage("Expected Yaw/Pitch/Z: " + lockedYaw + "/" + lockedPitch + "/" + lockedZAxisCoordinate);
    }

}
