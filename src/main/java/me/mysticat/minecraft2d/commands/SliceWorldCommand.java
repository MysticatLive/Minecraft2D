package me.mysticat.minecraft2d.commands;

import me.mysticat.minecraft2d.handlers.Engine;
import me.mysticat.minecraft2d.tasks.SliceWorldTask;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SliceWorldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        //init
        if (!Engine.senderIsPlayer(sender)) return false;
        Player player = (Player) sender;

        if (args.length == 7) {
            //test if all parameters are given
            if (!verifyArgs(args)) {
                return true;
            }
            //run command
            new SliceWorldTask(
                    player.getWorld(),
                    Engine.getAxisFromString(args[6]),
                    Integer.parseInt(args[0]),
                    Integer.parseInt(args[1]),
                    Integer.parseInt(args[2]),
                    Integer.parseInt(args[3]),
                    Integer.parseInt(args[4]),
                    Integer.parseInt(args[5])
            );
        }
        return false;
    }

    private @NotNull Boolean verifyArgs(String[] args) {
        //test if last arg is proper axis
        if (!Engine.isAxis(args[6])) {
            return false;
        }
        //test if coordinate args are proper integers
        for (int i = 0; i < 6; i++) {
            if (!Engine.isInt(args[i])) {
                return false;
            }
        }
        return true;
    }
}