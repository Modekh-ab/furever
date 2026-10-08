package net.mondless.furever.render;

//? if >=26.1 {
/*import net.minecraft.client.renderer.entity.state.FoxRenderState;

public final class FureverFoxRenderState extends FoxRenderState {
    public float turnBend;
    public float stridePhase;
}
*///? } else if >=1.21.6 {
/*import net.minecraft.client.render.entity.state.FoxEntityRenderState;

public final class FureverFoxRenderState extends FoxEntityRenderState {
    public float turnBend;
    public float stridePhase;
}
*///? }
