package me.mysticat.minecraft2d.handlers;

import me.mysticat.minecraft2d.enums.Direction;
import me.mysticat.minecraft2d.enums.GameState;
import me.mysticat.minecraft2d.tasks.*;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GameHandler {

    public static World world = null;
    public static GameState globalGameState;
    public static List<BukkitRunnable> runningTasks = new ArrayList<>();
    public static Boolean placeBlockCoolDownActive = false;
    public static final HashMap<Zombie, ArmorStand> ZOMBIE_ANIM_LIST = new HashMap<>();
    public static final List<Material> BLOCK_BREAK_LIST = Arrays.asList(Material.OAK_LOG, Material.OAK_LEAVES, Material.BIRCH_LOG, Material.BIRCH_LEAVES,
            Material.SPONGE, Material.WET_SPONGE);
    public static final List<Material> PICKAXE_BREAK_LIST = Arrays.asList(Material.OAK_LOG, Material.OAK_LEAVES, Material.BIRCH_LOG, Material.BIRCH_LEAVES,
            Material.COAL_ORE, Material.IRON_ORE, Material.DIAMOND_ORE, Material.NETHER_QUARTZ_ORE, Material.NETHER_GOLD_ORE,
            Material.SPONGE, Material.WET_SPONGE);
    public static final List<Material> BLOCK_UNPLACEABLE_LIST = Arrays.asList(Material.AIR, Material.WATER, Material.LAVA);
    public static final Material MINECRAFT_PICKAXE = Material.IRON_PICKAXE;
    public static final Material MINECRAFT_SWORD = Material.IRON_SWORD;
    public static final Material MINECRAFT_WIN_CONDITION = Material.DIAMOND_ORE;
    public static final String MINECRAFT_VICTORY_TEXT = "MINECRAFT!";
    public static final Boolean ANNOUNCE_GAME_SELECTION = false;
    public static final Boolean DEBUG_ANIM = false;
    public static final Boolean DEBUG_DIRECTION = false;
    public static final Boolean DISABLE_FOR_CREATIVE = true;
    public static final Boolean DISABLE_FOR_SPECTATOR = true;
    public static final int NUM_GAMES = 1;
    public static final int LOBBY_X = 0;
    public static final int LOBBY_Y = 50;
    public static final int LOBBY_Z = 0;
    private static final double MINECRAFT_X = 30.5;
    private static final double MINECRAFT_Y = -14;
    private static final double MINECRAFT_Z = 10;
    private static final double MINECRAFT_Z_OFFSET = 0.5;
    private static final int MINECRAFT_YAW = 180;
    private static final int MINECRAFT_PITCH = 0;
    private static final int MARIO_X = 30;
    private static final int MARIO_Y = -14;
    private static final int MARIO_Z = 10;
    private static final double MARIO_Z_OFFSET = 0.5;
    private static final int MARIO_YAW = 180;
    private static final int MARIO_PITCH = 0;
    public static final Sound MINECRAFT_PICKUP_SOUND = Sound.ENTITY_ITEM_PICKUP;
    private static final Sound MINECRAFT_BREAK_SOUND = Sound.BLOCK_WOOD_BREAK;
    private static final Sound MINECRAFT_PLACE_SOUND = Sound.BLOCK_WOOD_PLACE;
    private static final Sound MINECRAFT_ATTACK_SOUND = Sound.ENTITY_PLAYER_ATTACK_SWEEP;

    /**
     * Begin a 2D game
     * @param gameState Game time to play
     */
    public static void startGame(GameState gameState) {
        globalGameState = gameState;
        reset();
        switch(globalGameState) {
            case MINECRAFT: { initGameMinecraft(); break; }
            case MARIO: { initGameMario(); break; }
            default: { initGameLobby(); break; }
        }
        if (ANNOUNCE_GAME_SELECTION) { Engine.announceToAllPlayers("Complete!"); }
    }

    /**
     * Break a block from a player
     * @param player Player to use
     */
    public static void breakBlock(@NotNull Player player) {
        if (player.getGameMode() == GameMode.ADVENTURE) {
            Block blockToBreak = getBlockBreakArea(player);
            if (blockToBreak == null) { return; }
            if (playerCanBreakBlock(player, blockToBreak)) {
                if (blockToBreak.getType() == MINECRAFT_WIN_CONDITION) {
                    //noinspection deprecation
                    player.sendTitle(GameHandler.MINECRAFT_VICTORY_TEXT, "", 2, 80, 10);
                }
                player.getInventory().addItem(new ItemStack(blockToBreak.getType()));
                blockToBreak.setType(Material.AIR);
                Engine.playSoundAtPlayer(player, MINECRAFT_BREAK_SOUND, 1, 1);
            }
        }
    }

    /**
     * Place a block from a player
     * @param player Player to use
     */
    public static void placeBlock(@NotNull Player player) {
        if (player.getGameMode() == GameMode.ADVENTURE && !placeBlockCoolDownActive) {
            //init
            Block blockToPlace = getBlockPlaceArea(player);
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            Material blockType = itemInHand.getType();
            if (blockToPlace == null) { return; }
            new PlaceBlockCoolDownTask(1);

            //place block
            if (blockType.isBlock() && blockToPlace.getType() == Material.AIR) {
                Engine.playSoundAtPlayer(player, MINECRAFT_PLACE_SOUND, 1, 1);
                blockToPlace.setType(blockType);
                int amount = itemInHand.getAmount();
                itemInHand.setAmount(amount - 1);
            }
        }
    }

    /**
     * Damages all entities in front of the 2D player
     * @param player Player to use
     */
    public static void attackEntities(@NotNull Player player) {
        int xDir = (PlayerAnimationHandler.direction == Direction.RIGHT) ? 1 : -1;
        int crouching = (player.isSneaking()) ? 0 : 1;
        int damage = (player.getInventory().getItemInMainHand().getType() == MINECRAFT_SWORD) ? 8 : 3;
        double velocityDampener = 0.25;
        Location location = player.getLocation().add(xDir, crouching, 0);
        Collection<Entity> nearbyEntities = location.getNearbyEntities(xDir, 1, 1);
        nearbyEntities.forEach(entity -> {
            if (entity instanceof Zombie) {
                ((Zombie) entity).damage(damage);
                entity.setVelocity(new Vector(xDir * velocityDampener, 1 * velocityDampener, 0));
                Engine.playSoundAtPlayer(player, MINECRAFT_ATTACK_SOUND, 1, 1);
            }
        });
    }

    private static @Nullable Block getBlockBreakArea(@NotNull Player player) {
        //init
        Block playerLocationBlock = player.getLocation().getBlock();
        int blockX = (PlayerAnimationHandler.direction == Direction.RIGHT) ? 1 : -1;
        Block eyeLevel = playerLocationBlock.getRelative(blockX, 1, 0);
        Block footLevel = playerLocationBlock.getRelative(blockX, 0, 0);
        Block bridgeLevel = playerLocationBlock.getRelative(blockX, -1, 0);
        Block belowLevel = playerLocationBlock.getRelative(0, -1, 0);

        //if standing
        if (!player.isSneaking()) {
            if (playerCanBreakBlock(player, eyeLevel)) { return eyeLevel; }
        }
        else {
            if (playerCanBreakBlock(player, footLevel)) { return footLevel; }
            else if (playerCanBreakBlock(player, bridgeLevel)) { return bridgeLevel; }
            else if (playerCanBreakBlock(player, belowLevel)) { return belowLevel; }
        }
        return null;
    }

    private static boolean playerCanBreakBlock(Player player, @NotNull Block block) {
        return (
                BLOCK_BREAK_LIST.contains(block.getType())
                || (player.getInventory().getItemInMainHand().getType() == MINECRAFT_PICKAXE && PICKAXE_BREAK_LIST.contains(block.getType()))
        );
    }

    private static @Nullable Block getBlockPlaceArea(@NotNull Player player) {
        //init
        Block playerLocationBlock = player.getLocation().getBlock();
        int blockX = (PlayerAnimationHandler.direction == Direction.RIGHT) ? 1 : -1;
        Block playerLocationSafetyBlock = player.getLocation().add(blockX * 0.3, 0, 0).getBlock();
        //block locations
        Block eyeLevel = playerLocationSafetyBlock.getRelative(blockX, 1, 0);
        Block footLevel = playerLocationSafetyBlock.getRelative(blockX, 0, 0);
        Block bridgeLevel = playerLocationBlock.getRelative(blockX, -1, 0);
        Block belowLevel = playerLocationBlock.getRelative(0, -1, 0);

        //if standing
        if (!player.isSneaking()) {
            //try to place block below player
            if (!((Entity)player).isOnGround() && isBlockPlaceable(belowLevel)) { return belowLevel; }
            //try to place block at eye level
            else if (isBlockPlaceable(eyeLevel) && (player.getLocation().distance(eyeLevel.getLocation()) > 1)) { return eyeLevel; }
        }
        //if crouching
        else {
            //try to place block at bridge level
            if (isBlockPlaceable(bridgeLevel)) { return bridgeLevel; }
            //try to place block at foot level
            else if (isBlockPlaceable(footLevel)) { return footLevel; }
        }
        return null;
    }

    private static boolean isBlockPlaceable(@NotNull Block block) {
        boolean placeable = true;
        if (!BLOCK_UNPLACEABLE_LIST.contains(block.getType())) {
            placeable = false;
        }
        else if (
                BLOCK_UNPLACEABLE_LIST.contains(block.getRelative(BlockFace.UP).getType())
                && BLOCK_UNPLACEABLE_LIST.contains(block.getRelative(BlockFace.DOWN).getType())
                && BLOCK_UNPLACEABLE_LIST.contains(block.getRelative(BlockFace.EAST).getType())
                && BLOCK_UNPLACEABLE_LIST.contains(block.getRelative(BlockFace.WEST).getType())
        ) {
            placeable = false;
        }
        return placeable;
    }

    private static void wipeAllGameEntities() {
        world.getEntities().forEach(entity -> {
            if (globalGameState == GameState.MINECRAFT && (entity instanceof Zombie || entity instanceof ArmorStand)) {
                entity.remove();
            }
        });
    }

    private static void reset() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            world = player.getWorld();
        }
        clearTasks();
        Engine.clearAllPlayersInventories();
        Engine.clearAllPlayersEffects();
    }

    private static void initGameMinecraft() {
        if (ANNOUNCE_GAME_SELECTION) { Engine.announceToAllPlayers("> Loading Game \"Minecraft\""); }
        Engine.teleportAllPlayers(MINECRAFT_X, MINECRAFT_Y, MINECRAFT_Z);
        Engine.setAllPlayersGameModes(GameMode.ADVENTURE);
        wipeAllGameEntities();
        runningTasks.add(new ForceCameraDirectionTask(MINECRAFT_YAW, MINECRAFT_PITCH, MINECRAFT_Z, MINECRAFT_Z_OFFSET));
        runningTasks.add(new PlayerInvisibleTask());
        runningTasks.add(new PlayerAnimationTask());
        runningTasks.add(new CheckPlayerBlockTask());
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 100000, 0, true, false));
        }
        if (world != null) {
            world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
            world.setGameRule(GameRule.DO_FIRE_TICK, false);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.DO_TILE_DROPS, false);
            world.setGameRule(GameRule.COMMAND_BLOCK_OUTPUT, false);
            world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
            world.setDifficulty(Difficulty.EASY);
            world.setTime(18000); //midnight
        }
    }

    private static void initGameMario() {
        if (ANNOUNCE_GAME_SELECTION) { Engine.announceToAllPlayers("> Loading Game \"Mario\""); }
        Engine.teleportAllPlayers(MARIO_X, MARIO_Y, MARIO_Z);
        Engine.setAllPlayersGameModes(GameMode.ADVENTURE);
        runningTasks.add(new ForceCameraDirectionTask(MARIO_YAW, MARIO_PITCH, MARIO_Z, MARIO_Z_OFFSET));
        runningTasks.add(new PlayerInvisibleTask());
        runningTasks.add(new PlayerAnimationTask());
    }

    private static void initGameLobby() {
        if (ANNOUNCE_GAME_SELECTION) { Engine.announceToAllPlayers("> Unloading Game"); }
        Engine.teleportAllPlayers(LOBBY_X, LOBBY_Y, LOBBY_Z);
        Engine.setAllPlayersGameModes(GameMode.ADVENTURE);
    }

    private static void clearTasks() {
        for (BukkitRunnable task : runningTasks) {
            task.cancel();
        }
        runningTasks.clear();
    }

    public static boolean playerIsPlayableGameMode(@NotNull Player player) {
        GameMode gameMode = player.getGameMode();
        return (!(
                (GameHandler.DISABLE_FOR_CREATIVE && gameMode == GameMode.CREATIVE)
                || (GameHandler.DISABLE_FOR_SPECTATOR && gameMode == GameMode.SPECTATOR)
        ));
    }
}