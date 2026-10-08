package net.mondless.furever.config;

import java.util.LinkedHashMap;
import java.util.Map;

public final class ScreenCatalog {
    private static final Map<String, String> SCREENS = new LinkedHashMap<>();

    static {
        //? if >=26.1 {
        /*String base = "net.minecraft.client.gui.screens.";
        addIfPresent(base + "TitleScreen", "Title screen");
        addIfPresent(base + "LoadingOverlay", "Resource loading");
        addIfPresent(base + "LevelLoadingScreen", "World loading");
        addIfPresent(base + "options.OptionsScreen", "Options");
        addIfPresent(base + "PauseScreen", "Pause menu");
        addIfPresent(base + "worldselection.SelectWorldScreen", "Singleplayer worlds");
        addIfPresent(base + "multiplayer.JoinMultiplayerScreen", "Multiplayer");
        addIfPresent(base + "inventory.InventoryScreen", "Inventory");
        addIfPresent(base + "ChatScreen", "Chat");
        *///? } else {
        String base = "net.minecraft.client.gui.screen.";
        addIfPresent(base + "TitleScreen", "Title screen");
        addIfPresent(base + "SplashOverlay", "Resource loading");
        addIfPresent(base + "world.LevelLoadingScreen", "World loading");
        addIfPresent(base + "option.OptionsScreen", "Options");
        addIfPresent(base + "GameMenuScreen", "Pause menu");
        addIfPresent(base + "world.SelectWorldScreen", "Singleplayer worlds");
        addIfPresent(base + "multiplayer.MultiplayerScreen", "Multiplayer");
        addIfPresent(base + "ingame.InventoryScreen", "Inventory");
        addIfPresent(base + "ChatScreen", "Chat");
        //? }
        addIfPresent("com.terraformersmc.modmenu.gui.ModsScreen", "Mods");
    }

    private ScreenCatalog() { }

    private static void addIfPresent(String name, String label) {
        try {
            Class.forName(name, false, ScreenCatalog.class.getClassLoader());
            SCREENS.put(name, label);
        } catch (ClassNotFoundException | LinkageError ignored) {
        }
    }

    public static synchronized void record(Class<?> screenClass) {
        SCREENS.putIfAbsent(screenClass.getName(), screenClass.getSimpleName());
    }

    public static synchronized Map<String, String> choices() {
        return new LinkedHashMap<>(SCREENS);
    }
}
