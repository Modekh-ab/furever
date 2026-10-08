package net.mondless.furever.mixin;

//? if >=26.1 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.TitleScreen;
*///? } else {
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
//? }
import net.mondless.furever.config.FureverConfig;
import net.mondless.furever.render.FureverRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
abstract class TitleScreenMixin {
    //? if >=26.1 {
    /*@Inject(method = "extractRenderState", at = @At("TAIL"))
    private void furever$renderFox(GuiGraphicsExtractor context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (FureverConfig.get().shouldRenderOn((TitleScreen) (Object) this)) FureverRenderer.render(context);
    }
    *///? } else {
    @Inject(method = "render", at = @At("TAIL"))
    private void furever$renderFox(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (FureverConfig.get().shouldRenderOn((TitleScreen) (Object) this)) FureverRenderer.render(context);
    }
    //? }
}
