package me.mysticat.minecraft2d.tasks;

import me.mysticat.minecraft2d.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerInvisibleTask extends BukkitRunnable {

    public PlayerInvisibleTask() {
        runTaskTimer(Main.plugin, 0L, 1L);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            //make player invisible
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 10, 1, true, false));
        }
    }

}
