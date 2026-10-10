package net.mondless.furever.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
//? if >=26.1 {
/*import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
*///? } else {
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
//? }

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public final class FureverConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("furever.json");
    private static FureverConfig INSTANCE = new FureverConfig();
    private static boolean loaded;

    public boolean enabled = true;
    public ScreenMode screenMode = ScreenMode.DEFAULT;
    public MobChoice mob = MobChoice.SPRITE_FOX;
    public String textureVariant = "";
    public boolean baby = false;

    public String customScreens = "";

    public Set<String> selectedScreens = new LinkedHashSet<>();

    public int xPercent = 91;
    public int yPercent = 82;
    public float scale = 1.0F;

    public float speed = 1.0F;

    public float orbitRadius = 0.45F;
    public boolean clockwise = false;

    public int cameraAngle = 30;

    public static FureverConfig get() {
        if (!loaded) load();
        return INSTANCE;
    }

    public static void load() {
        if (loaded) return;
        if (!Files.exists(PATH)) {
            loaded = true;
            save();
            return;
        }
        try (Reader reader = Files.newBufferedReader(PATH)) {
            FureverConfig loaded = GSON.fromJson(reader, FureverConfig.class);
            if (loaded != null) INSTANCE = loaded;
        } catch (Exception ignored) {
        }
        INSTANCE.sanitize();
        loaded = true;
    }

    public static void save() {
        INSTANCE.sanitize();
        try {
            Files.createDirectories(PATH.getParent());
            try (Writer writer = Files.newBufferedWriter(PATH)) {
                GSON.toJson(INSTANCE, writer);
            }
        } catch (IOException ignored) {
        }
    }

    public boolean shouldRenderOn(Screen screen) {
        if (!enabled) return false;
        return switch (screenMode) {
            case DEFAULT -> screen instanceof TitleScreen;
            case ALL_MENUS -> true;
            case CUSTOM -> matchesCustomScreen(screen.getClass().getName());
        };
    }

    public boolean shouldRenderOnLoadingScreen(Class<?> screenClass) {
        if (!enabled) return false;
        return switch (screenMode) {
            case DEFAULT, ALL_MENUS -> true;
            case CUSTOM -> matchesCustomScreen(screenClass.getName());
        };
    }

    private boolean matchesCustomScreen(String className) {
        return selectedScreens.contains(className) || customScreenNames().contains(className);
    }

    private Set<String> customScreenNames() {
        Set<String> result = new HashSet<>();
        Arrays.stream(customScreens.split(","))
                .map(String::trim).filter(s -> !s.isEmpty()).forEach(result::add);
        return result;
    }

    public void sanitize() {
        if (screenMode == null) screenMode = ScreenMode.DEFAULT;
        if (mob == null || !mob.isAvailable()) mob = MobChoice.SPRITE_FOX;
        if (textureVariant == null || !MobTextureVariant.belongsTo(mob, textureVariant)) textureVariant = "";
        if (!mob.supportsBaby()) baby = false;
        xPercent = Math.clamp(xPercent, -100, 200);
        yPercent = Math.clamp(yPercent, -100, 200);
        scale = Math.clamp(scale, 0.25F, 4.0F);
        speed = Math.clamp(speed, 0.1F, 5.0F);
        orbitRadius = Math.clamp(orbitRadius, 0.05F, 1.5F);
        cameraAngle = Math.clamp(cameraAngle, -75, 75);
        if (customScreens == null) customScreens = "";
        if (selectedScreens == null) selectedScreens = new LinkedHashSet<>();
    }
}
