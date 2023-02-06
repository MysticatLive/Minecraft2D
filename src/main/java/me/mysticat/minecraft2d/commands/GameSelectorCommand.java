package me.mysticat.minecraft2d.commands;

import me.mysticat.minecraft2d.enums.GameState;
import me.mysticat.minecraft2d.handlers.Engine;
import me.mysticat.minecraft2d.handlers.GameHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GameSelectorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        //select game state
        if (args.length == 1 && Engine.isInt(args[0])) {
            int arg = Integer.parseInt(args[0]);
            GameState gameState = getGameStateFromArg(arg);
            if (gameState != null) {
                GameHandler.startGame(gameState);
                return false;
            }
        }
        return true;
    }

    @Contract(pure = true)
    private @Nullable GameState getGameStateFromArg(int arg) {
        switch(arg) {
            case 0: return GameState.LOBBY;
            case 1: return GameState.MINECRAFT;
            case 2: return GameState.MARIO;
            default: return null;
        }
    }

}
