package net.mondless.furever.compat;

//? if >=26.1 {
/*import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
*///? } else {
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
//? }
//? if >=1.21.9 && <26.1 {
/*import net.minecraft.client.gui.Click;
import net.minecraft.client.input.KeyInput;
*///? }
import net.mondless.furever.config.FureverConfig;
import net.mondless.furever.render.FureverRenderer;
//? if <26.1 {
import org.lwjgl.glfw.GLFW;
//? }

public final class FureverPositionScreen extends Screen {
    //? if >=26.1 {
    /*private static final int MOUSE_LEFT = InputConstants.MOUSE_BUTTON_LEFT;
    private static final int KEY_LEFT = InputConstants.KEY_LEFT;
    private static final int KEY_RIGHT = InputConstants.KEY_RIGHT;
    private static final int KEY_UP = InputConstants.KEY_UP;
    private static final int KEY_DOWN = InputConstants.KEY_DOWN;
    *///? } else {
    private static final int MOUSE_LEFT = GLFW.GLFW_MOUSE_BUTTON_LEFT;
    private static final int KEY_LEFT = GLFW.GLFW_KEY_LEFT;
    private static final int KEY_RIGHT = GLFW.GLFW_KEY_RIGHT;
    private static final int KEY_UP = GLFW.GLFW_KEY_UP;
    private static final int KEY_DOWN = GLFW.GLFW_KEY_DOWN;
    //? }
    private final Screen settingsParent;
    private boolean dragging;
    private boolean finishing;

    FureverPositionScreen(Screen settingsParent) {
        //? if >=26.1 {
        /*super(Component.translatable("text.furever.position_title"));
        *///? } else {
        super(Text.translatable("text.furever.position_title"));
        //? }
        this.settingsParent = settingsParent;
    }

    @Override
    protected void init() {
        //? if >=26.1 {
        /*addRenderableWidget(Button.builder(Component.translatable("text.furever.done"), button -> finish())
                .bounds(width / 2 - 50, height - 28, 100, 20).build());
        *///? } else {
        addDrawableChild(ButtonWidget.builder(Text.translatable("text.furever.done"), button -> finish())
                .dimensions(width / 2 - 50, height - 28, 100, 20).build());
        //? }
    }

    //? if >=26.1 {
    /*@Override
    public void extractRenderState(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta) {
        super.extractRenderState(context, mouseX, mouseY, delta);
        FureverRenderer.render(context);
        context.centeredText(font, Component.translatable("text.furever.position_hint"), width / 2, 12, 0xFFFFFF);
        context.centeredText(font, Component.translatable("text.furever.position_arrows"), width / 2, height - 42, 0xFFFFFF);
    }
    *///? } else {
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        FureverRenderer.render(context);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("text.furever.position_hint"), width / 2, 12, 0xFFFFFF);
        context.drawCenteredTextWithShadow(textRenderer, Text.translatable("text.furever.position_arrows"), width / 2, height - 42, 0xFFFFFF);
    }
    //? }

    //? if >=26.1 {
    /*@Override
    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        if (super.mouseClicked(event, doubleClick)) return true;
        if (event.button() != MOUSE_LEFT) return false;
        dragging = true;
        place(event.x(), event.y());
        return true;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double deltaX, double deltaY) {
        if (dragging) { place(event.x(), event.y()); return true; }
        return super.mouseDragged(event, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(MouseButtonEvent event) {
        if (dragging && event.button() == MOUSE_LEFT) {
            place(event.x(), event.y());
            dragging = false;
            return true;
        }
        return super.mouseReleased(event);
    }

    @Override
    public boolean keyPressed(KeyEvent event) {
        if (nudge(event.key())) return true;
        return super.keyPressed(event);
    }

    @Override
    public void onClose() { finish(); }
    *///? } else if >=1.21.9 {
    /*@Override
    public boolean mouseClicked(Click click, boolean doubleClick) {
        if (super.mouseClicked(click, doubleClick)) return true;
        if (click.button() != MOUSE_LEFT) return false;
        dragging = true;
        place(click.x(), click.y());
        return true;
    }

    @Override
    public boolean mouseDragged(Click click, double deltaX, double deltaY) {
        if (dragging) { place(click.x(), click.y()); return true; }
        return super.mouseDragged(click, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(Click click) {
        if (dragging && click.button() == MOUSE_LEFT) {
            place(click.x(), click.y());
            dragging = false;
            return true;
        }
        return super.mouseReleased(click);
    }

    @Override
    public boolean keyPressed(KeyInput input) {
        if (nudge(input.key())) return true;
        return super.keyPressed(input);
    }

    @Override
    public void close() { finish(); }
    *///? } else {
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) return true;
        if (button != MOUSE_LEFT) return false;
        dragging = true;
        place(mouseX, mouseY);
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragging) { place(mouseX, mouseY); return true; }
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (dragging && button == MOUSE_LEFT) {
            place(mouseX, mouseY);
            dragging = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (nudge(keyCode)) return true;
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void close() { finish(); }
    //? }

    private void place(double mouseX, double mouseY) {
        FureverConfig config = FureverConfig.get();
        if (width > 0) config.xPercent = Math.clamp((int) Math.round(mouseX * 100.0 / width), -100, 200);
        if (height > 0) config.yPercent = Math.clamp((int) Math.round(mouseY * 100.0 / height), -100, 200);
    }

    private boolean nudge(int keyCode) {
        FureverConfig config = FureverConfig.get();
        switch (keyCode) {
            case KEY_LEFT -> config.xPercent--;
            case KEY_RIGHT -> config.xPercent++;
            case KEY_UP -> config.yPercent--;
            case KEY_DOWN -> config.yPercent++;
            default -> { return false; }
        }
        config.xPercent = Math.clamp(config.xPercent, -100, 200);
        config.yPercent = Math.clamp(config.yPercent, -100, 200);
        return true;
    }

    private void finish() {
        if (finishing) return;
        finishing = true;
        FureverConfig.save();
        //? if >=26.2 {
        /*minecraft.setScreenAndShow(FureverConfigScreen.create(settingsParent));
        *///? } else if >=26.1 {
        /*minecraft.setScreen(FureverConfigScreen.create(settingsParent));
        *///? } else {
        client.setScreen(FureverConfigScreen.create(settingsParent));
        //? }
    }
}
