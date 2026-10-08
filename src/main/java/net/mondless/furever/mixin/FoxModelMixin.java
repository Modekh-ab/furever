package net.mondless.furever.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if >=26.1 {
/*import net.mondless.furever.render.FureverFoxRenderState;
import net.minecraft.client.model.animal.fox.FoxModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.FoxRenderState;
*///? } else {
import net.minecraft.client.render.entity.model.FoxEntityModel;
//? if >=1.21.6 {
/*import net.mondless.furever.render.FureverFoxRenderState;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.state.FoxEntityRenderState;
*///? }
//? }

//? if >=26.1 {
/*@Mixin(FoxModel.class)
*///? } else {
@Mixin(FoxEntityModel.class)
//? }
public abstract class FoxModelMixin {
    //? if >=26.1 {
    /*@Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/FoxRenderState;)V", at = @At("TAIL"))
    private void furever$bend(FoxRenderState state, CallbackInfo ci) {
        if (!(state instanceof FureverFoxRenderState furever)) return;
        ModelPart root = ((FoxModel) (Object) this).root();
        float bend = furever.turnBend;
        ModelPart body = root.getChild("body");
        body.yRot += bend * 0.45F;
        body.zRot -= 0.05F;
        body.xRot += furever.stridePhase * 0.055F;
        root.getChild("head").yRot += bend;
        root.getChild("head").xRot -= furever.stridePhase * 0.04F;
        body.getChild("tail").yRot -= bend * 1.25F;
        body.getChild("tail").xRot += furever.stridePhase * 0.10F;
    }
    *///? } else if >=1.21.6 {
    /*@Inject(method = "setAngles(Lnet/minecraft/client/render/entity/state/FoxEntityRenderState;)V", at = @At("TAIL"))
    private void furever$bend(FoxEntityRenderState state, CallbackInfo ci) {
        if (!(state instanceof FureverFoxRenderState furever)) return;
        ModelPart root = ((FoxEntityModel) (Object) this).getRootPart();
        float bend = furever.turnBend;
        ModelPart body = root.getChild("body");
        body.yaw += bend * 0.45F;
        body.roll -= 0.05F;
        body.pitch += furever.stridePhase * 0.055F;
        root.getChild("head").yaw += bend;
        root.getChild("head").pitch -= furever.stridePhase * 0.04F;
        body.getChild("tail").yaw -= bend * 1.25F;
        body.getChild("tail").pitch += furever.stridePhase * 0.10F;
    }
    *///? }
}
