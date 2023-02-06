package me.mysticat.minecraft2d.handlers;

import me.mysticat.minecraft2d.enums.Direction;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class PlayerAnimationHandler {

    public static Direction direction = Direction.RIGHT;
    public static double xSpeed = 0;
    public static double ySpeed = 0;
    public static double zSpeed = 0;
    public static boolean isPunching = false;

    public static void updateAnimation(Player player) {
        //update animations
        switch (GameHandler.globalGameState) {
            case MINECRAFT: { minecraftAnimationHandler(player);  break; }
            case MARIO: { marioAnimationHandler(player); break; }
            default: break;
        }
        isPunching = false;
    }

    private static void minecraftAnimationHandler(Player player) {
        //punching animation
        if (isPunching) {
            AnimationHandler.changeAnimationLeftRight(player,  direction, 20, 19);
        }
        //crouching animation
        else if (player.isSneaking()) {
            AnimationHandler.changeAnimationLeftRight(player, direction, 22, 21);
        }
        //jumping animation
        else if (ySpeed > 0) {
            AnimationHandler.changeAnimationLeftRight(player, direction, 16, 15);
        }
        //falling animation
        else if (ySpeed < 0) {
            AnimationHandler.changeAnimationLeftRight(player, direction, 18, 17);
        }
        //walking animation
        else if (Math.abs(xSpeed) > 0.1) {
            AnimationHandler.changeAnimationLeftRight(player, direction, 14, 13);
        }
        //idle animation
        else {
            AnimationHandler.changeAnimationLeftRight(player, direction, 12, 11);
        }
    }

    private static void marioAnimationHandler(Player player) {
        //jumping animation
        if (ySpeed > 0) {
            AnimationHandler.changeAnimationLeftRight(player, direction, 6, 5);
        }
        //falling animation
        else if (ySpeed < 0) {
            AnimationHandler.changeAnimationLeftRight(player, direction, 8, 7);
        }
        //running animation
        else if (Math.abs(xSpeed) > 0.1) {
            if (player.hasPotionEffect(PotionEffectType.SPEED)) { AnimationHandler.changeAnimationLeftRight(player, direction, 10, 9); }
            else { AnimationHandler.changeAnimationLeftRight(player, direction, 4, 3); }
        }
        //idle animation
        else {
            AnimationHandler.changeAnimationLeftRight(player, direction, 2, 1);
        }
    }

}
