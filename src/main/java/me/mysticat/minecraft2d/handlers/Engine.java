package me.mysticat.minecraft2d.handlers;

import me.mysticat.minecraft2d.enums.Axis;
import me.mysticat.minecraft2d.resources.Messages;
import me.mysticat.minecraft2d.tasks.DeleteEntityAfterTimeTask;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Engine {

    /**
     * Initialize a Listener to the Plugin
     * @param pluginManager PluginManager to register with
     * @param listenerList List of listeners to register
     * @param plugin Plugin to register to
     */
    public static void initListener(PluginManager pluginManager, @NotNull List<? extends Listener> listenerList, Plugin plugin) {
        for (Listener listener : listenerList) {
            pluginManager.registerEvents(listener, plugin);
        }
    }

    /**
     * Test if a command sender is a player
     * @param sender Sender of the command
     * @return If the sender is a player
     */
    public static @NotNull Boolean senderIsPlayer(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Messages.onlyPlayersMessage);
            return false;
        }
        return true;
    }

    /**
     * Test if a string can be parsed into an integer
     * @param string String to test
     * @return If string is an integer
     */
    public static @NotNull Boolean isInt(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException numberFormatException) {
            return false;
        }
        return true;
    }

    /**
     * Test if a string can be parsed into an axis
     * @param string String to test
     * @return If string is an axis
     */
    @Contract(pure = true)
    public static @NotNull Boolean isAxis(String string) {
        return getAxisFromString(string) != null;
    }

    /**
     * Convert a string to an Axis
     * @param string String to convert
     * @return Axis from string or null
     */
    @Contract(pure = true)
    public static @Nullable Axis getAxisFromString(@NotNull String string) {
        switch(string.toLowerCase()) {
            case "x": return Axis.X;
            case "y": return Axis.Y;
            case "z": return Axis.Z;
            default: return null;
        }
    }

    /**
     * Return the center coordinate of two coordinates
     * @param coordinate1 First coordinate to compare
     * @param coordinate2 Second coordinate to compare
     * @return The center coordinate of the two inputs
     */
    public static int centerOfCoordinates(int coordinate1, int coordinate2) {
        int smallestCoordinate = Math.min(coordinate1, coordinate2);
        int largestCoordinate = Math.max(coordinate1, coordinate2);
        return (smallestCoordinate + (Math.abs(largestCoordinate - smallestCoordinate) / 2));
    }

    /**
     * Replace a block with a fallingBlock entity
     * @param block Block to replace
     * @return New fallingBlock entity
     */
    @SuppressWarnings("unused")
    public static @NotNull FallingBlock convertBlockToFallingBlock(@NotNull Block block) {
        Location location = block.getLocation();
        BlockData blockData = block.getBlockData();
        block.setType(Material.AIR);
        return block.getWorld().spawnFallingBlock(location.add(0.5, 0, 0.5), blockData);
    }

    /**
     * Replace a block with a fallingBlock entity
     * @param block Block to replace
     */
    public static void convertBlockToTempFallingBlock(@NotNull Block block) {
        Location location = block.getLocation();
        BlockData blockData = block.getBlockData();
        block.setType(Material.AIR);
        FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(location.add(0.5, 0, 0.5), blockData);
        new DeleteEntityAfterTimeTask(fallingBlock);
    }

    /**
     * Plays a sound effect at the player's location
     * @param player Player to target
     * @param sound Sound to play
     * @param volume Sound volume
     * @param pitch Sound pitch
     */
    public static void playSoundAtPlayer(@NotNull Player player, Sound sound, float volume, float pitch) {
        Location location = player.getLocation();
        location.getWorld().playSound(location, sound, volume, pitch);
    }

    /**
     * Set the game mode of all players online
     * @param gameMode Game mode to set to
     */
    public static void setAllPlayersGameModes(GameMode gameMode) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.setGameMode(gameMode);
        }
    }

    /**
     * Give every player an item
     * @param itemStack Item to give
     * @param slot Slot to assign it to
     */
    @SuppressWarnings("unused")
    public static void giveAllPlayersItem(ItemStack itemStack, int slot) {
        if (slot < 0 || slot > 27) return;
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().setItem(slot, itemStack);
        }
    }

    /**
     * Clear the inventories of every player online
     */
    public static void clearAllPlayersInventories() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
        }
    }

    /**
     * Clear the potion effects of every player online
     */
    public static void clearAllPlayersEffects() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }
        }
    }

    /**
     * Teleport all players to a location
     * @param x X coordinate
     * @param y Y coordinate
     * @param z Z coordinate
     */
    public static void teleportAllPlayers(double x, double y, double z) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Location location = new Location(Bukkit.getWorld("world"), x, y, z);
            player.teleport(location);
        }
    }

    /**
     * Send a message to every player online
     * @param string Message to send
     */
    public static void announceToAllPlayers(String string) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', string));
        }
    }

}
