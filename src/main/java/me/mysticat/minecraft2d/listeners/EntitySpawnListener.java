package me.mysticat.minecraft2d.listeners;

import me.mysticat.minecraft2d.enums.GameState;
import me.mysticat.minecraft2d.handlers.GameHandler;
import me.mysticat.minecraft2d.handlers.ZombieAnimationHandler;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class EntitySpawnListener implements Listener {

    @EventHandler
    public void entitySpawnListener(EntitySpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Zombie && GameHandler.globalGameState == GameState.MINECRAFT) {
            World world = entity.getWorld();
            ArmorStand armorStand = (ArmorStand)world.spawnEntity(entity.getLocation(), EntityType.ARMOR_STAND);
            Zombie zombie = (Zombie)entity;
            zombie.setInvisible(true);
            zombie.setCollidable(false);
            armorStand.setInvisible(true);
            armorStand.setCanMove(false);
            armorStand.setCollidable(false);
            armorStand.setInvulnerable(true);
            GameHandler.ZOMBIE_ANIM_LIST.put((Zombie)entity, armorStand);
            ZombieAnimationHandler.updateAnimation(armorStand);
        }
    }

}
