package me.mysticat.minecraft2d;

import me.mysticat.minecraft2d.commands.GameSelectorCommand;
import me.mysticat.minecraft2d.commands.SliceWorldCommand;
import me.mysticat.minecraft2d.enums.GameState;
import me.mysticat.minecraft2d.handlers.Engine;
import me.mysticat.minecraft2d.handlers.GameHandler;
import me.mysticat.minecraft2d.listeners.*;
import me.mysticat.minecraft2d.tab.GameSelectorTab;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Main extends JavaPlugin {

    public static Plugin plugin;
    public static GameHandler gameHandler;

    @Override
    public void onEnable() {
        plugin = this;
        GameHandler.globalGameState = GameState.LOBBY;
        init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void init() {
        initCommands();
        initListeners();
    }

    private void initListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        List<? extends Listener> listenerList = Arrays.asList(
                new PlayerMoveListener(),
                new PlayerInteractListener(),
                new PlayerDamageListener(),
                new PlayerDropItemListener(),
                new PlayerSwapHandItemsListener(),
                new PlayerJoinListener(),
                new InventoryOpenListener(),
                new InventoryClickListener(),
                new EntityToggleSwimListener(),
                new EntityMoveListener(),
                new EntitySpawnListener()
        );
        Engine.initListener(pluginManager, listenerList, this);
    }

    private void initCommands() {
        Objects.requireNonNull(getCommand("sliceworld")).setExecutor(new SliceWorldCommand());
        Objects.requireNonNull(getCommand("playgame")).setExecutor(new GameSelectorCommand());
        Objects.requireNonNull(getCommand("playgame")).setTabCompleter(new GameSelectorTab());
    }
}
