package net.mondless.furever.compat;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.api.Requirement;
import me.shedaniel.clothconfig2.gui.ClothConfigScreen;
import me.shedaniel.clothconfig2.gui.entries.DropdownBoxEntry;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
//? if >=26.1 {
/*import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
*///? } else {
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
//? }
import net.mondless.furever.config.FureverConfig;
import net.mondless.furever.config.MobChoice;
import net.mondless.furever.config.ScreenCatalog;
import net.mondless.furever.config.ScreenMode;

import java.util.Locale;
import java.util.Map;
import java.util.Arrays;

final class FureverConfigScreen {
    private FureverConfigScreen() { }

    static Screen create(Screen parent) {
        FureverConfig config = FureverConfig.get();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(translatable("text.furever.title"))
                .setAfterInitConsumer(screen -> addPositionButton(screen, parent));
        ConfigCategory category = builder.getOrCreateCategory(literal("General"));
        ConfigCategory mobCategory = builder.getOrCreateCategory(literal("Mobs"));
        ConfigCategory customCategory = builder.getOrCreateCategory(literal("Custom screens"));
        ConfigEntryBuilder entries = builder.entryBuilder();

        MobChoice initialMob = config.mob;
        DropdownBoxEntry<MobChoice> mobEntry = entries.startDropdownMenu(translatable("text.furever.mob"), config.mob,
                        value -> parseMob(value, config.mob),
                        value -> translatable("text.furever.mob." + value.name().toLowerCase(Locale.ROOT)))
                .setSelections(Arrays.asList(MobChoice.values()))
                .setDefaultValue(MobChoice.SPRITE_FOX)
                .setSaveConsumer(value -> {
                    if (config.mob != value) config.baby = false;
                    config.mob = value;
                }).build();
        mobCategory.addEntry(mobEntry);
        mobCategory.addEntry(entries.startBooleanToggle(translatable("text.furever.baby"), config.baby)
                .setDefaultValue(false).setSaveConsumer(value -> config.baby = mobEntry.getValue() == initialMob && value)
                .setDisplayRequirement(() -> mobEntry.getValue().supportsBaby()).build());
        Requirement vanillaModel = () -> mobEntry.getValue() != MobChoice.SPRITE_FOX;

        category.addEntry(entries.startBooleanToggle(translatable("text.furever.enabled"), config.enabled)
                .setDefaultValue(true).setSaveConsumer(value -> config.enabled = value).build());
        category.addEntry(entries.startEnumSelector(translatable("text.furever.screen_mode"), ScreenMode.class, config.screenMode)
                .setDefaultValue(ScreenMode.DEFAULT).setTooltip(translatable("text.furever.screen_mode.tooltip"))
                .setSaveConsumer(value -> config.screenMode = value).build());

        for (Map.Entry<String, String> option : ScreenCatalog.choices().entrySet()) {
            String className = option.getKey();
            customCategory.addEntry(entries.startBooleanToggle(literal(option.getValue()), config.selectedScreens.contains(className))
                    .setDefaultValue(false).setTooltip(literal(className))
                    .setSaveConsumer(selected -> {
                        if (selected) config.selectedScreens.add(className);
                        else config.selectedScreens.remove(className);
                    }).build());
        }
        customCategory.addEntry(entries.startStrField(translatable("text.furever.custom_screens"), config.customScreens)
                .setDefaultValue("").setTooltip(translatable("text.furever.custom_screens.tooltip"))
                .setSaveConsumer(value -> config.customScreens = value).build());
        category.addEntry(entries.startIntSlider(translatable("text.furever.x"), config.xPercent, -100, 200)
                .setDefaultValue(91).setTooltip(translatable("text.furever.position.tooltip"))
                .setSaveConsumer(value -> config.xPercent = value).build());
        category.addEntry(entries.startIntSlider(translatable("text.furever.y"), config.yPercent, -100, 200)
                .setDefaultValue(82).setTooltip(translatable("text.furever.position.tooltip"))
                .setSaveConsumer(value -> config.yPercent = value).build());
        category.addEntry(entries.startIntSlider(translatable("text.furever.scale"), Math.round(config.scale * 100), 25, 400)
                .setDefaultValue(100).setTextGetter(FureverConfigScreen::multiplierLabel)
                .setSaveConsumer(value -> config.scale = value / 100.0F).build());
        category.addEntry(entries.startIntSlider(translatable("text.furever.speed"), Math.round(config.speed * 100), 10, 500)
                .setDefaultValue(100).setTextGetter(FureverConfigScreen::multiplierLabel)
                .setSaveConsumer(value -> config.speed = value / 100.0F).build());
        category.addEntry(entries.startIntSlider(translatable("text.furever.orbit_radius"), Math.round(config.orbitRadius * 100), 5, 150)
                .setDefaultValue(45).setTextGetter(FureverConfigScreen::percentLabel)
                .setTooltip(translatable("text.furever.orbit_radius.tooltip"))
                .setSaveConsumer(value -> config.orbitRadius = value / 100.0F)
                .setDisplayRequirement(vanillaModel).build());
        category.addEntry(entries.startBooleanToggle(translatable("text.furever.clockwise"), config.clockwise)
                .setDefaultValue(false).setTooltip(translatable("text.furever.clockwise.tooltip"))
                .setSaveConsumer(value -> config.clockwise = value)
                .setDisplayRequirement(vanillaModel).build());
        category.addEntry(entries.startIntSlider(translatable("text.furever.camera_angle"), config.cameraAngle, -75, 75)
                .setDefaultValue(30).setTextGetter(FureverConfigScreen::angleLabel)
                .setTooltip(translatable("text.furever.camera_angle.tooltip"))
                .setSaveConsumer(value -> config.cameraAngle = value)
                .setDisplayRequirement(vanillaModel).build());

        builder.setSavingRunnable(FureverConfig::save);
        return builder.build();
    }

    private static void openPositionScreen(Screen settings, Screen parent) {
        ((ClothConfigScreen) settings).saveAll(false);
        //? if >=26.2 {
        /*Screens.getMinecraft(settings).setScreenAndShow(new FureverPositionScreen(parent));
        *///? } else if >=26.1 {
        /*Screens.getMinecraft(settings).setScreen(new FureverPositionScreen(parent));
        *///? } else {
        Screens.getClient(settings).setScreen(new FureverPositionScreen(parent));
        //? }
    }

    private static void addPositionButton(Screen settings, Screen parent) {
        int x = settings.width - 94;
        int y = settings.height - 28;
        //? if >=26.1 {
        /*Screens.getWidgets(settings).add(Button.builder(translatable("text.furever.place"),
                button -> openPositionScreen(settings, parent)).bounds(x, y, 86, 20).build());
        *///? } else {
        Screens.getButtons(settings).add(ButtonWidget.builder(translatable("text.furever.place"),
                button -> openPositionScreen(settings, parent)).dimensions(x, y, 86, 20).build());
        //? }
    }

    private static MobChoice parseMob(String input, MobChoice fallback) {
        String key = input.trim().toUpperCase(Locale.ROOT).replace(' ', '_');
        if (key.equals("FOX")) return MobChoice.VANILLA_FOX;
        if (key.equals("NEOFORGE_FOX")) return MobChoice.SPRITE_FOX;
        try { return MobChoice.valueOf(key); }
        catch (IllegalArgumentException ignored) { return fallback; }
    }

    //? if >=26.1 {
    /*private static Component translatable(String key) { return Component.translatable(key); }
    private static Component literal(String value) { return Component.literal(value); }
    private static Component multiplierLabel(int value) { return Component.literal(String.format(Locale.ROOT, "%.2fx", value / 100.0)); }
    private static Component percentLabel(int value) { return Component.literal(value + "%"); }
    private static Component angleLabel(int value) { return Component.literal(value + "°"); }
    *///? } else {
    private static Text translatable(String key) { return Text.translatable(key); }
    private static Text literal(String value) { return Text.literal(value); }
    private static Text multiplierLabel(int value) { return Text.literal(String.format(Locale.ROOT, "%.2fx", value / 100.0)); }
    private static Text percentLabel(int value) { return Text.literal(value + "%"); }
    private static Text angleLabel(int value) { return Text.literal(value + "°"); }
    //? }
}
