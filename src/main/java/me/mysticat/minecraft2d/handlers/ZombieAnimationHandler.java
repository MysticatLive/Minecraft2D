package me.mysticat.minecraft2d.handlers;

import me.mysticat.minecraft2d.enums.Direction;
import org.bukkit.entity.LivingEntity;

public class ZombieAnimationHandler {

    public static Direction direction = Direction.RIGHT;
    public static double xSpeed = 0;

    public static void updateAnimation(LivingEntity entity) {
        //walking animation
        if (Math.abs(xSpeed) > 0.1) {
            AnimationHandler.changeAnimationLeftRight(entity, direction, 26, 25);
        }
        //idle animation
        else {
            AnimationHandler.changeAnimationLeftRight(entity, direction, 28, 27);
        }
    }

}
