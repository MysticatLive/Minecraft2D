package me.mysticat.minecraft2d.permissions;

import org.bukkit.command.CommandSender;

public class GeneralPermissions {

    public static String permRoot = "mc2d";

    public static boolean fullPerms(CommandSender sender) { return sender.hasPermission(permRoot + ".*"); }
}
