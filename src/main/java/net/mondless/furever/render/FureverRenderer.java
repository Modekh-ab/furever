package net.mondless.furever.render;

//? if >=26.1 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
*///? } else {
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
//? }
import net.mondless.furever.config.FureverConfig;
import net.mondless.furever.config.MobChoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;

//? if >=26.1 {
//? } else if >=1.21.6 {
/*import net.minecraft.client.gl.RenderPipelines;
*///? } else if >=1.21.2 {
/*import net.minecraft.client.render.RenderLayer;
*///? }

public final class FureverRenderer {
    private static final Logger LOGGER = LoggerFactory.getLogger("furever");
    private static final EnumSet<MobChoice> failedModels = EnumSet.noneOf(MobChoice.class);
    //? if >=26.1 {
    /*private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath("furever", "textures/gui/running_fox.png");
    *///? } else {
    private static final Identifier TEXTURE = Identifier.of("furever", "textures/gui/running_fox.png");
    //? }
    private static final int FRAME_WIDTH = 256;
    private static final int FRAME_HEIGHT = 128;
    private static final int FRAME_COUNT = 28;

    private static final float FOX_CENTER_X = 130.5F;
    private static final float FOX_CENTER_Y = 83.0F;
    private static final long BASE_FRAME_DURATION_MS = 50;
    private static int frame;
    private static long lastFrameTime;

    private FureverRenderer() { }

    //? if >=26.1 {
    /*public static void render(GuiGraphicsExtractor context) {
    *///? } else {
    public static void render(DrawContext context) {
    //? }
        FureverConfig config = FureverConfig.get();
        //? if >=26.1 {
        /*int screenWidth = context.guiWidth();
        int screenHeight = context.guiHeight();
        *///? } else {
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        //? }
        if (screenWidth <= 0 || screenHeight <= 0) return;
        if (config.mob != MobChoice.SPRITE_FOX && !failedModels.contains(config.mob)) {
            try {
                if (VanillaMobRenderer.render(context, config.mob, screenWidth, screenHeight, config)) return;
            } catch (RuntimeException exception) {
                failedModels.add(config.mob);
                LOGGER.error("Vanilla {} model could not be rendered; falling back to the original fox sprite", config.mob, exception);
            }
        }

        float targetWidth = screenWidth * 0.30F * config.scale;
        float scale = Math.min(targetWidth / FRAME_WIDTH,
                Math.min(screenWidth / (float) FRAME_WIDTH, screenHeight / (float) FRAME_HEIGHT));
        int width = Math.max(1, Math.round(FRAME_WIDTH * scale));
        int height = Math.max(1, Math.round(FRAME_HEIGHT * scale));
        int x = Math.round(screenWidth * config.xPercent / 100.0F - width * FOX_CENTER_X / FRAME_WIDTH);
        int y = Math.round(screenHeight * config.yPercent / 100.0F - height * FOX_CENTER_Y / FRAME_HEIGHT);

        long now = System.currentTimeMillis();
        long frameDuration = Math.max(1, Math.round(BASE_FRAME_DURATION_MS / Math.max(0.01F, config.speed)));
        if (now - lastFrameTime >= frameDuration) {
            frame = (frame + 1) % FRAME_COUNT;
            lastFrameTime = now;
        }

        //? if >=26.1 {
        /*context.blit(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y,
                0.0F, frame * (float) FRAME_HEIGHT, width, height,
                FRAME_WIDTH, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT * FRAME_COUNT);
        *///? } else if >=1.21.6 {
        /*context.drawTexture(RenderPipelines.GUI_TEXTURED, TEXTURE, x, y,
                0.0F, frame * (float) FRAME_HEIGHT, width, height,
                FRAME_WIDTH, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT * FRAME_COUNT);
        *///? } else if >=1.21.2 {
        /*context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x, y,
                0.0F, frame * (float) FRAME_HEIGHT, width, height,
                FRAME_WIDTH, FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT * FRAME_COUNT);
        *///? } else {
        context.drawTexture(TEXTURE, x, y, width, height,
                0.0F, frame * (float) FRAME_HEIGHT, FRAME_WIDTH, FRAME_HEIGHT,
                FRAME_WIDTH, FRAME_HEIGHT * FRAME_COUNT);
        //? }
    }
}
