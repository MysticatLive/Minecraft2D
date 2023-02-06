package me.mysticat.minecraft2d.listeners;

import me.mysticat.minecraft2d.handlers.GameHandler;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void playerJoinListener(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            Location spawn = new Location(GameHandler.world, GameHandler.LOBBY_X, GameHandler.LOBBY_Y, GameHandler.LOBBY_Z);
            player.teleport(spawn);
        }
    }

}
