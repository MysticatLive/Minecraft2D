package me.mysticat.minecraft2d.permissions;

import org.bukkit.command.CommandSender;

public class MC2DPermissions {

    private static final String permPrefix = GeneralPermissions.permRoot + ".mc2d";

    public static boolean permissionToStartGame(CommandSender sender) {
        return GeneralPermissions.fullPerms(sender)
                || sender.hasPermission(permPrefix)
                || sender.hasPermission(permPrefix + ".playgame");
    }

}
