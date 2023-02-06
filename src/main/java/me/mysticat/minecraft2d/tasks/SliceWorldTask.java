package me.mysticat.minecraft2d.tasks;

import me.mysticat.minecraft2d.Main;
import me.mysticat.minecraft2d.enums.Axis;
import me.mysticat.minecraft2d.handlers.Engine;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

public class SliceWorldTask extends BukkitRunnable {

    private final World world;
    private final Axis axis;
    private final int lowestY;
    private final int lowestZ;
    private final int highestX;
    private final int highestY;
    private final int highestZ;
    private final int middleX;
    private final int middleY;
    private final int middleZ;
    private int i;

    public SliceWorldTask(World world, Axis axis, int x1, int y1, int z1, int x2, int y2, int z2) {
        //init
        this.world = world;
        this.axis = axis;

        //sort parameters into top-right and bottom-left corners
        int lowestX = Math.min(x1, x2);
        this.lowestY = Math.min(y1, y2);
        this.lowestZ = Math.min(z1, z2);
        this.highestX = Math.max(x1, x2);
        this.highestY = Math.max(y1, y2);
        this.highestZ = Math.max(z1, z2);

        //set the middle line not to slice
        middleX = Engine.centerOfCoordinates(lowestX,highestX);
        middleY = Engine.centerOfCoordinates(lowestY,highestY);
        middleZ = Engine.centerOfCoordinates(lowestZ,highestZ);
        i = lowestX - 1;
        runTaskTimer(Main.plugin, 1L, 2L);
    }

    @Override
    public void run() {
        //set all blocks between them to fallingSand except for the center of axis
        for (int j = lowestY; j < highestY; j++) {
            for (int k = lowestZ; k < highestZ; k++) {
                Block nextBlock = world.getBlockAt(i, j, k);
                if (
                        (nextBlock.getType() != Material.AIR) &&
                                (
                                    (axis == Axis.Z && i != middleX) ||
                                    (axis == Axis.Y && j != middleY) ||
                                    (axis == Axis.X && k != middleZ)
                                )
                ) {
                    Engine.convertBlockToTempFallingBlock(nextBlock);
                }
            }
        }
        if (i == highestX) cancel();
        i++;
    }

}
