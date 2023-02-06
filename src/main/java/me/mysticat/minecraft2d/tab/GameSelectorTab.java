package me.mysticat.minecraft2d.tab;

import me.mysticat.minecraft2d.handlers.GameHandler;
import me.mysticat.minecraft2d.permissions.MC2DPermissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameSelectorTab implements TabCompleter, Listener {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> tab = new ArrayList<>();
        if (args.length == 1 && MC2DPermissions.permissionToStartGame(sender)) {
            for (int i = 0; i <= GameHandler.NUM_GAMES; i++) {
                tab.add("" + i);
            }
        }
        Collections.sort(tab);
        return tab;
    }

}
