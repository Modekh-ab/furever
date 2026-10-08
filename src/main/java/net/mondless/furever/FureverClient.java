package net.mondless.furever;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
//? if >=26.1 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
*///? } else {
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.world.LevelLoadingScreen;
//? }
import net.mondless.furever.config.FureverConfig;
import net.mondless.furever.config.ScreenCatalog;
import net.mondless.furever.compat.FureverPositionScreen;
import net.mondless.furever.render.FureverRenderer;

public final class FureverClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FureverConfig.load();
        //? if >=26.1 {
        /*ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            ScreenCatalog.record(screen.getClass());
            ScreenEvents.afterExtract(screen).register(FureverClient::afterScreenRender);
        });
        *///? } else {
        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            ScreenCatalog.record(screen.getClass());
            ScreenEvents.afterRender(screen).register(FureverClient::afterScreenRender);
        });
        //? }
    }

    //? if >=26.1 {
    /*private static void afterScreenRender(Screen screen, GuiGraphicsExtractor context,
    *///? } else {
    private static void afterScreenRender(Screen screen, DrawContext context,
    //? }
                                          int mouseX, int mouseY, float delta) {
        if (!(screen instanceof TitleScreen) && !(screen instanceof LevelLoadingScreen)
                && !(screen instanceof FureverPositionScreen)
                && FureverConfig.get().shouldRenderOn(screen)) {
            FureverRenderer.render(context);
        }
    }
}
