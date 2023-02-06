package me.mysticat.minecraft2d.handlers;

import me.mysticat.minecraft2d.enums.Direction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AnimationHandler {

    /**
     * Updates the 2D animation of an entity
     * @param entity Entity to update
     * @param animationID Animation ID to change to
     */
    public static void changeAnimation(@NotNull LivingEntity entity, int animationID) {
        ItemStack helmet = Objects.requireNonNull(entity.getEquipment()).getHelmet();
        Material animationMaterial = Material.PHANTOM_MEMBRANE;
        if (helmet == null || helmet.getType() != animationMaterial) {
            helmet = new ItemStack(animationMaterial);
        }
        ItemMeta itemMeta = (helmet.hasItemMeta()) ? helmet.getItemMeta() : Bukkit.getItemFactory().getItemMeta(animationMaterial);
        itemMeta.setCustomModelData(animationID);
        helmet.setItemMeta(itemMeta);
        entity.getEquipment().setHelmet(helmet);
    }

    /**
     * Updates the 2D animation of an entity
     * @param entity Entity to update
     * @param direction Direction the entity is facing
     * @param leftAnimationID Animation ID to change to if the entity is facing left
     * @param rightAnimationID Animation ID to change to if the entity is facing right
     */
    public static void changeAnimationLeftRight(@NotNull LivingEntity entity, Direction direction, int leftAnimationID, int rightAnimationID) {
        if (direction == Direction.RIGHT) {
            changeAnimation(entity, rightAnimationID);
        } else {
            changeAnimation(entity, leftAnimationID);
        }
    }

}
