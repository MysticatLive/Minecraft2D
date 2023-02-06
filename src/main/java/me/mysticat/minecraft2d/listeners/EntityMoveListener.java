package me.mysticat.minecraft2d.listeners;

import io.papermc.paper.event.entity.EntityMoveEvent;
import me.mysticat.minecraft2d.enums.Direction;
import me.mysticat.minecraft2d.enums.GameState;
import me.mysticat.minecraft2d.handlers.GameHandler;
import me.mysticat.minecraft2d.handlers.ZombieAnimationHandler;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class EntityMoveListener implements Listener {

    @EventHandler
    public void entityMoveListener(EntityMoveEvent event) {
        Entity entity = event.getEntity();
        if (GameHandler.globalGameState == GameState.MINECRAFT && entity instanceof Zombie) {
            ZombieAnimationHandler.xSpeed = getXSpeed(event);
            ZombieAnimationHandler.direction = getDirection(ZombieAnimationHandler.xSpeed);
            LivingEntity armorStand = GameHandler.ZOMBIE_ANIM_LIST.get(entity);
            if (armorStand != null) {
                ZombieAnimationHandler.updateAnimation(armorStand);
            }
        }
    }

    private static Direction getDirection(double speed) {
        if (speed > 0) {
            return Direction.RIGHT;
        }
        else if (speed < 0) {
            return Direction.LEFT;
        }
        return ZombieAnimationHandler.direction;
    }

    private static double getXSpeed(@NotNull EntityMoveEvent event) {
        return event.getTo().getX() - event.getFrom().getX();
    }

}
