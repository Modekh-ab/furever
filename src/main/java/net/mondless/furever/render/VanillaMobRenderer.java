package net.mondless.furever.render;

import net.mondless.furever.config.FureverConfig;
import net.mondless.furever.config.MobChoice;
import org.joml.Quaternionf;
import org.joml.Vector3f;

//? if >=26.2 {
/*import net.minecraft.world.entity.EntityTypes;
*///? }

//? if >=26.1 {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.state.BeeRenderState;
import net.minecraft.client.renderer.entity.state.CreeperRenderState;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.SlimeRenderState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.fox.Fox;
*///? } else if >=1.21.6 {
/*import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.state.BeeEntityRenderState;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.SlimeEntityRenderState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.FoxEntity;
*///? } else {
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.FoxEntityModel;
import net.minecraft.client.render.entity.model.SlimeEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
//? }

/** Uses Minecraft's own model geometry and textures, not bundled sprite sheets. */
public final class VanillaMobRenderer {
    private static final double LOOP_SECONDS = 3.5;

    private VanillaMobRenderer() { }

    private record Orbit(int x, int y, float facing, float animationTime) { }

    private static Orbit orbit(int centerX, int centerY, float size, float speed, int cameraAngle) {
        double elapsed = System.nanoTime() * 1.0e-9 * speed;
        double angle = (elapsed % LOOP_SECONDS) * (Math.PI * 2.0 / LOOP_SECONDS);
        float radius = size * 0.38F;
        int x = Math.round(centerX + (float) Math.cos(angle) * radius);
        int y = Math.round(centerY + (float) Math.sin(angle) * radius * 0.55F);
        // Match the visible tangent of the projected ellipse, not just its phase.
        double pitchProjection = Math.sin(Math.toRadians(cameraAngle));
        if (Math.abs(pitchProjection) < 0.15) {
            pitchProjection = Math.copySign(0.15, cameraAngle < 0 ? -1.0 : 1.0);
        }
        float facing = (float) Math.atan2(-Math.sin(angle),
                0.55 * Math.cos(angle) / pitchProjection) + (float) Math.PI;
        return new Orbit(x, y, facing,
                (float) (elapsed % 10000.0));
    }

    //? if <1.21.6 {
    private static final Identifier FOX_TEXTURE = Identifier.of("minecraft", "textures/entity/fox/fox.png");
    private static final Identifier BEE_TEXTURE = Identifier.of("minecraft", "textures/entity/bee/bee.png");
    private static final Identifier CREEPER_TEXTURE = Identifier.of("minecraft", "textures/entity/creeper/creeper.png");
    private static final Identifier SLIME_TEXTURE = Identifier.of("minecraft", "textures/entity/slime/slime.png");
    private static ModelPart foxModel;
    private static ModelPart beeModel;
    private static ModelPart creeperModel;
    private static ModelPart slimeModel;
    //? }

    //? if >=26.1 {
    /*public static boolean render(GuiGraphicsExtractor context, MobChoice mob, int screenWidth, int screenHeight, FureverConfig config) {
    *///? } else {
    public static boolean render(DrawContext context, MobChoice mob, int screenWidth, int screenHeight, FureverConfig config) {
    //? }
        int centerX = Math.round(screenWidth * config.xPercent / 100.0F);
        int centerY = Math.round(screenHeight * config.yPercent / 100.0F);
        int box = Math.max(16, Math.round(Math.min(screenWidth * 0.30F * config.scale, screenHeight * 0.65F)));
        float modelScale = switch (mob) {
            case VANILLA_FOX -> 0.32F;
            case BEE -> 0.65F;
            case CREEPER -> 0.37F;
            case SLIME -> 0.55F;
            default -> 0.32F;
        };
        Orbit orbit = orbit(centerX, centerY, box * modelScale, config.speed, config.cameraAngle);
        float time = orbit.animationTime();

        //? if >=26.1 {
        /*EntityRenderState state = createState(mob, time);
        if (Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(state) == null) return false;
        Quaternionf rotation = new Quaternionf().rotateZ((float) Math.PI)
                .rotateX((float) Math.toRadians(-config.cameraAngle)).rotateY(orbit.facing());
        context.entity(state, box * modelScale,
                new Vector3f(0.0F, state.boundingBoxHeight * 0.5F, 0.0F),
                rotation, new Quaternionf(), orbit.x() - box / 2, orbit.y() - box / 2,
                orbit.x() + box / 2, orbit.y() + box / 2);
        *///? } else if >=1.21.6 {
        /*EntityRenderState state = createState(mob, time);
        if (MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(state) == null) return false;
        Quaternionf rotation = new Quaternionf().rotateZ((float) Math.PI)
                .rotateX((float) Math.toRadians(-config.cameraAngle)).rotateY(orbit.facing());
        context.addEntity(state, box * modelScale,
                new Vector3f(0.0F, state.height * 0.5F, 0.0F),
                rotation, new Quaternionf(), orbit.x() - box / 2, orbit.y() - box / 2,
                orbit.x() + box / 2, orbit.y() + box / 2);
        *///? } else {
        ModelPart model = getModel(mob);
        model.traverse().forEach(ModelPart::resetTransform);
        animate(model, mob, time);
        //? if <=1.21.1 {
        context.draw(() -> drawLegacy(context, context.getVertexConsumers(), model, mob,
                orbit.x(), orbit.y(), box * modelScale, orbit.facing(), config.cameraAngle, time));
        //? } else {
        /*context.draw(provider -> drawLegacy(context, provider, model, mob,
                orbit.x(), orbit.y(), box * modelScale, orbit.facing(), config.cameraAngle, time));
        *///? }
        //? }
        return true;
    }

    //? if >=26.1 {
    /*private static EntityRenderState createState(MobChoice mob, float time) {
        LivingEntityRenderState state = switch (mob) {
            case VANILLA_FOX -> {
                FureverFoxRenderState fox = new FureverFoxRenderState();
                fox.variant = Fox.Variant.RED;
                fox.turnBend = -foxBend(time);
                fox.stridePhase = (float) Math.sin(time * 12.0F);
                yield fox;
            }
            case BEE -> {
                BeeRenderState bee = new BeeRenderState();
                bee.isOnGround = false;
                yield bee;
            }
            case CREEPER -> new CreeperRenderState();
            case SLIME -> {
                SlimeRenderState slime = new SlimeRenderState();
                slime.size = 2;
                slime.squish = (float) (0.25 + 0.2 * Math.sin(time * 7.0F));
                yield slime;
            }
            default -> throw new IllegalArgumentException("Not a model choice: " + mob);
        };
        state.entityType = getType(mob);
        state.ageInTicks = time * 20.0F;
        state.walkAnimationPos = time * 12.0F;
        state.walkAnimationSpeed = mob == MobChoice.SLIME ? 0.0F : 0.8F;
        state.scale = 1.0F;
        state.ageScale = 1.0F;
        state.pose = Pose.STANDING;
        state.boundingBoxWidth = mob == MobChoice.SLIME ? 1.0F : 0.9F;
        state.boundingBoxHeight = switch (mob) {
            case BEE -> 0.6F;
            case CREEPER -> 1.7F;
            case SLIME -> 1.0F;
            default -> 0.8F;
        };
        state.lightCoords = 0xF000F0;
        return state;
    }
    *///? } else if >=1.21.6 {
    /*private static EntityRenderState createState(MobChoice mob, float time) {
        LivingEntityRenderState state = switch (mob) {
            case VANILLA_FOX -> {
                FureverFoxRenderState fox = new FureverFoxRenderState();
                fox.type = FoxEntity.Variant.RED;
                fox.turnBend = -foxBend(time);
                fox.stridePhase = (float) Math.sin(time * 12.0F);
                yield fox;
            }
            case BEE -> {
                BeeEntityRenderState bee = new BeeEntityRenderState();
                bee.stoppedOnGround = false;
                yield bee;
            }
            case CREEPER -> new CreeperEntityRenderState();
            case SLIME -> {
                SlimeEntityRenderState slime = new SlimeEntityRenderState();
                slime.size = 2;
                slime.stretch = (float) (0.25 + 0.2 * Math.sin(time * 7.0F));
                yield slime;
            }
            default -> throw new IllegalArgumentException("Not a model choice: " + mob);
        };
        state.entityType = switch (mob) {
            case VANILLA_FOX -> EntityType.FOX;
            case BEE -> EntityType.BEE;
            case CREEPER -> EntityType.CREEPER;
            case SLIME -> EntityType.SLIME;
            default -> throw new IllegalArgumentException("Not a model choice: " + mob);
        };
        state.age = time * 20.0F;
        state.limbSwingAnimationProgress = time * 12.0F;
        state.limbSwingAmplitude = mob == MobChoice.SLIME ? 0.0F : 0.8F;
        state.baseScale = 1.0F;
        state.ageScale = 1.0F;
        state.pose = EntityPose.STANDING;
        state.width = mob == MobChoice.SLIME ? 1.0F : 0.9F;
        state.height = switch (mob) {
            case BEE -> 0.6F;
            case CREEPER -> 1.7F;
            case SLIME -> 1.0F;
            default -> 0.8F;
        };
        setLight(state);
        return state;
    }
    *///? } else {
    private static ModelPart getModel(MobChoice mob) {
        return switch (mob) {
            case VANILLA_FOX -> {
                if (foxModel == null) foxModel = FoxEntityModel.getTexturedModelData().createModel();
                yield foxModel;
            }
            case BEE -> {
                if (beeModel == null) beeModel = BeeEntityModel.getTexturedModelData().createModel();
                yield beeModel;
            }
            case CREEPER -> {
                if (creeperModel == null) creeperModel = CreeperEntityModel.getTexturedModelData(Dilation.NONE).createModel();
                yield creeperModel;
            }
            case SLIME -> {
                if (slimeModel == null) slimeModel = SlimeEntityModel.getInnerTexturedModelData().createModel();
                yield slimeModel;
            }
            default -> throw new IllegalArgumentException("Not a model choice: " + mob);
        };
    }

    private static void animate(ModelPart root, MobChoice mob, float time) {
        float step = (float) Math.sin(time * 12.0F) * 0.7F;
        switch (mob) {
            case VANILLA_FOX -> {
                root.getChild("right_hind_leg").pitch = step;
                root.getChild("left_hind_leg").pitch = -step;
                root.getChild("right_front_leg").pitch = -step;
                root.getChild("left_front_leg").pitch = step;
                float bend = foxBend(time);
                root.getChild("body").yaw += bend * 0.45F;
                root.getChild("head").yaw += bend;
                root.getChild("body").getChild("tail").yaw -= bend * 1.25F;
                root.getChild("body").roll -= 0.05F;
                root.getChild("body").pitch += step * 0.08F;
                root.getChild("head").pitch -= step * 0.06F;
                root.getChild("body").getChild("tail").pitch += step * 0.14F;
                root.getChild("body").getChild("tail").roll = (float) Math.sin(time * 5.0F) * 0.15F;
            }
            case BEE -> {
                ModelPart bone = root.getChild("bone");
                bone.getChild("right_wing").roll = (float) Math.sin(time * 30.0F) * 0.7F;
                bone.getChild("left_wing").roll = -bone.getChild("right_wing").roll;
            }
            case CREEPER -> {
                root.getChild("right_hind_leg").pitch = step;
                root.getChild("left_hind_leg").pitch = -step;
                root.getChild("right_front_leg").pitch = -step;
                root.getChild("left_front_leg").pitch = step;
            }
            case SLIME -> { }
            default -> { }
        }
    }

    private static void drawLegacy(DrawContext context, VertexConsumerProvider provider, ModelPart model,
                                   MobChoice mob, int centerX, int centerY, float size, float facing,
                                   int cameraAngle, float time) {
        Identifier texture = switch (mob) {
            case VANILLA_FOX -> FOX_TEXTURE;
            case BEE -> BEE_TEXTURE;
            case CREEPER -> CREEPER_TEXTURE;
            case SLIME -> SLIME_TEXTURE;
            default -> throw new IllegalArgumentException("Not a model choice: " + mob);
        };
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(centerX, centerY + size * 0.5F + (mob == MobChoice.BEE ? Math.sin(time * 5.0F) * 5.0F : 0.0F), 200.0F);
        if (mob == MobChoice.SLIME) size *= 1.0F + (float) Math.sin(time * 7.0F) * 0.08F;
        matrices.scale(size, -size, size);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-cameraAngle));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(-facing));
        RenderLayer layer = mob == MobChoice.SLIME
                ? RenderLayer.getEntityTranslucent(texture) : RenderLayer.getEntityCutoutNoCull(texture);
        model.render(matrices, provider.getBuffer(layer), 0xF000F0, 0);
        matrices.pop();
    }
    //? }

    private static float foxBend(float time) {
        return 0.30F + (float) Math.sin(time * 12.0F) * 0.065F;
    }

    //? if >=1.21.9 && <26.1 {
    /*private static void setLight(EntityRenderState state) { state.light = 0xF000F0; }
    *///? } else if >=1.21.6 && <1.21.9 {
    /*private static void setLight(EntityRenderState state) { }
    *///? }

    //? if >=26.2 {
    /*private static EntityType<?> getType(MobChoice mob) {
        return switch (mob) {
            case VANILLA_FOX -> EntityTypes.FOX;
            case BEE -> EntityTypes.BEE;
            case CREEPER -> EntityTypes.CREEPER;
            case SLIME -> EntityTypes.SLIME;
            default -> throw new IllegalArgumentException("Not a model choice: " + mob);
        };
    }
    *///? } else if >=26.1 {
    /*private static EntityType<?> getType(MobChoice mob) {
        return switch (mob) {
            case VANILLA_FOX -> EntityType.FOX;
            case BEE -> EntityType.BEE;
            case CREEPER -> EntityType.CREEPER;
            case SLIME -> EntityType.SLIME;
            default -> throw new IllegalArgumentException("Not a model choice: " + mob);
        };
    }
    *///? }
}
