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
import net.minecraft.client.renderer.entity.state.RabbitRenderState;
import net.minecraft.client.renderer.entity.state.BatRenderState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
*///? } else if >=1.21.6 {
/*import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.state.BeeEntityRenderState;
import net.minecraft.client.render.entity.state.CreeperEntityRenderState;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.SlimeEntityRenderState;
import net.minecraft.client.render.entity.state.RabbitEntityRenderState;
import net.minecraft.client.render.entity.state.BatEntityRenderState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.RabbitEntity;
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
import net.minecraft.client.render.entity.model.RabbitEntityModel;
import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.client.render.entity.model.CodEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
//? }

public final class VanillaMobRenderer {
    private static final double LOOP_SECONDS = 2.8;
    private static final double SLIME_HOP_SECONDS = 0.9;
    private static final int SLIME_HOPS_PER_LAP = 8;
    private static final double RABBIT_HOP_SECONDS = 0.75;
    private static final int RABBIT_HOPS_PER_LAP = 10;
    private static final float STRIDE_RATE = 18.0F;
    private static final float DEFAULT_MODEL_SCALE = 0.55F;

    private VanillaMobRenderer() { }

    private record Orbit(int x, int y, float facing, float animationTime) { }

    private static Orbit orbit(int centerX, int centerY, float size, float speed, float radiusScale,
                               int cameraAngle, boolean clockwise, MobChoice mob) {
        double elapsed = System.nanoTime() * 1.0e-9 * speed;
        int direction = clockwise ? 1 : -1;
        double angle;
        float jump = 0.0F;
        if (mob == MobChoice.SLIME || mob == MobChoice.RABBIT) {
            double hopSeconds = mob == MobChoice.SLIME ? SLIME_HOP_SECONDS : RABBIT_HOP_SECONDS;
            int hopsPerLap = mob == MobChoice.SLIME ? SLIME_HOPS_PER_LAP : RABBIT_HOPS_PER_LAP;
            double hop = elapsed / hopSeconds;
            double progress = Math.clamp((hop - Math.floor(hop) - 0.12) / 0.70, 0.0, 1.0);
            angle = direction * ((Math.floor(hop) + progress) % hopsPerLap)
                    * (Math.PI * 2.0 / hopsPerLap);
            jump = (float) Math.sin(progress * Math.PI) * size
                    * (mob == MobChoice.SLIME ? 0.24F : 0.30F);
        } else {
            angle = direction * (elapsed % LOOP_SECONDS) * (Math.PI * 2.0 / LOOP_SECONDS);
        }
        float radius = size * radiusScale;
        int x = Math.round(centerX + (float) Math.cos(angle) * radius);
        float hover = mob == MobChoice.BAT ? size * (0.10F + (float) Math.sin(elapsed * 8.0) * 0.05F)
                : mob == MobChoice.COD ? size * (float) Math.sin(elapsed * 5.0) * 0.03F : 0.0F;
        int y = Math.round(centerY + (float) Math.sin(angle) * radius - jump - hover);

        double pitchProjection = Math.sin(Math.toRadians(cameraAngle));
        if (Math.abs(pitchProjection) < 0.15) {
            pitchProjection = Math.copySign(0.15, cameraAngle < 0 ? -1.0 : 1.0);
        }
        float facing = (float) Math.atan2(-direction * Math.sin(angle),
                direction * Math.cos(angle) / pitchProjection) + (float) Math.PI;
        return new Orbit(x, y, facing,
                (float) (elapsed % 10000.0));
    }

    //? if <1.21.6 {
    private static final Identifier FOX_TEXTURE = Identifier.of("minecraft", "textures/entity/fox/fox.png");
    private static final Identifier BEE_TEXTURE = Identifier.of("minecraft", "textures/entity/bee/bee.png");
    private static final Identifier CREEPER_TEXTURE = Identifier.of("minecraft", "textures/entity/creeper/creeper.png");
    private static final Identifier SLIME_TEXTURE = Identifier.of("minecraft", "textures/entity/slime/slime.png");
    private static final Identifier RABBIT_TEXTURE = Identifier.of("minecraft", "textures/entity/rabbit/brown.png");
    private static final Identifier BAT_TEXTURE = Identifier.of("minecraft", "textures/entity/bat.png");
    private static final Identifier COD_TEXTURE = Identifier.of("minecraft", "textures/entity/fish/cod.png");
    private static ModelPart foxModel;
    private static ModelPart beeModel;
    private static ModelPart creeperModel;
    private static ModelPart slimeModel;
    private static ModelPart rabbitModel;
    private static ModelPart batModel;
    private static ModelPart codModel;
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
            case VANILLA_FOX -> 0.29F;
            case BEE -> 0.43F;
            case CREEPER -> 0.30F;
            case SLIME -> 0.40F;
            case RABBIT -> 0.36F;
            case BAT -> 0.36F;
            case COD -> 0.42F;
            default -> 0.29F;
        };
        float renderSize = box * modelScale * DEFAULT_MODEL_SCALE;
        Orbit orbit = orbit(centerX, centerY, box * modelScale, config.speed, config.orbitRadius,
                config.cameraAngle, config.clockwise, mob);
        float time = orbit.animationTime();
        int turnSign = config.clockwise ? 1 : -1;

        //? if >=26.1 {
        /*EntityRenderState state = createState(mob, time, turnSign);
        if (Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(state) == null) return false;
        Quaternionf rotation = new Quaternionf().rotateZ((float) Math.PI)
                .rotateX((float) Math.toRadians(-config.cameraAngle)).rotateY(orbit.facing());
        context.entity(state, renderSize,
                new Vector3f(0.0F, state.boundingBoxHeight * 0.5F, 0.0F),
                rotation, new Quaternionf(), orbit.x() - box / 2, orbit.y() - box / 2,
                orbit.x() + box / 2, orbit.y() + box / 2);
        *///? } else if >=1.21.6 {
        /*EntityRenderState state = createState(mob, time, turnSign);
        if (MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(state) == null) return false;
        Quaternionf rotation = new Quaternionf().rotateZ((float) Math.PI)
                .rotateX((float) Math.toRadians(-config.cameraAngle)).rotateY(orbit.facing());
        context.addEntity(state, renderSize,
                new Vector3f(0.0F, state.height * 0.5F, 0.0F),
                rotation, new Quaternionf(), orbit.x() - box / 2, orbit.y() - box / 2,
                orbit.x() + box / 2, orbit.y() + box / 2);
        *///? } else {
        ModelPart model = getModel(mob);
        model.traverse().forEach(ModelPart::resetTransform);
        animate(model, mob, time, turnSign);
        //? if <=1.21.1 {
        context.draw(() -> drawLegacy(context, context.getVertexConsumers(), model, mob,
                orbit.x(), orbit.y(), renderSize, orbit.facing(), config.cameraAngle, time));
        //? } else {
        /*context.draw(provider -> drawLegacy(context, provider, model, mob,
                orbit.x(), orbit.y(), renderSize, orbit.facing(), config.cameraAngle, time));
        *///? }
        //? }
        return true;
    }

    //? if >=26.1 {
    /*private static EntityRenderState createState(MobChoice mob, float time, int turnSign) {
        LivingEntityRenderState state = switch (mob) {
            case VANILLA_FOX -> {
                FureverFoxRenderState fox = new FureverFoxRenderState();
                fox.variant = Fox.Variant.RED;
                fox.turnBend = turnSign * foxBend(time);
                fox.stridePhase = (float) Math.sin(time * STRIDE_RATE);
                fox.gallopTime = time * STRIDE_RATE;
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
                slime.squish = slimeSquish(time);
                yield slime;
            }
            case RABBIT -> {
                RabbitRenderState rabbit = new RabbitRenderState();
                rabbit.variant = Rabbit.Variant.BROWN;
                rabbit.jumpCompletion = hopProgress(time, RABBIT_HOP_SECONDS);
                rabbit.hopAnimationState.start(0);
                yield rabbit;
            }
            case BAT -> {
                BatRenderState bat = new BatRenderState();
                bat.isResting = false;
                bat.flyAnimationState.start(0);
                yield bat;
            }
            case COD -> {
                LivingEntityRenderState cod = new LivingEntityRenderState();
                cod.isInWater = true;
                yield cod;
            }
            default -> throw new IllegalArgumentException("Not a model choice: " + mob);
        };
        state.entityType = getType(mob);
        state.ageInTicks = time * 20.0F;
        state.walkAnimationPos = time * STRIDE_RATE;
        state.walkAnimationSpeed = mob == MobChoice.SLIME ? 0.0F : 0.65F;
        state.scale = 1.0F;
        state.ageScale = 1.0F;
        state.pose = Pose.STANDING;
        state.boundingBoxWidth = mob == MobChoice.SLIME ? 1.0F : 0.9F;
        state.boundingBoxHeight = switch (mob) {
            case BEE -> 0.6F;
            case CREEPER -> 1.7F;
            case SLIME -> 1.0F;
            case RABBIT -> 0.5F;
            case BAT -> 0.9F;
            case COD -> 0.3F;
            default -> 0.8F;
        };
        state.lightCoords = 0xF000F0;
        return state;
    }
    *///? } else if >=1.21.6 {
    /*private static EntityRenderState createState(MobChoice mob, float time, int turnSign) {
        LivingEntityRenderState state = switch (mob) {
            case VANILLA_FOX -> {
                FureverFoxRenderState fox = new FureverFoxRenderState();
                fox.type = FoxEntity.Variant.RED;
                fox.turnBend = turnSign * foxBend(time);
                fox.stridePhase = (float) Math.sin(time * STRIDE_RATE);
                fox.gallopTime = time * STRIDE_RATE;
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
                slime.stretch = slimeSquish(time);
                yield slime;
            }
            case RABBIT -> {
                RabbitEntityRenderState rabbit = new RabbitEntityRenderState();
                rabbit.type = RabbitEntity.Variant.BROWN;
                rabbit.jumpProgress = hopProgress(time, RABBIT_HOP_SECONDS);
                yield rabbit;
            }
            case BAT -> {
                BatEntityRenderState bat = new BatEntityRenderState();
                bat.roosting = false;
                bat.flyingAnimationState.start(0);
                yield bat;
            }
            case COD -> {
                LivingEntityRenderState cod = new LivingEntityRenderState();
                cod.touchingWater = true;
                yield cod;
            }
            default -> throw new IllegalArgumentException("Not a model choice: " + mob);
        };
        state.entityType = switch (mob) {
            case VANILLA_FOX -> EntityType.FOX;
            case BEE -> EntityType.BEE;
            case CREEPER -> EntityType.CREEPER;
            case SLIME -> EntityType.SLIME;
            case RABBIT -> EntityType.RABBIT;
            case BAT -> EntityType.BAT;
            case COD -> EntityType.COD;
            default -> throw new IllegalArgumentException("Not a model choice: " + mob);
        };
        state.age = time * 20.0F;
        state.limbSwingAnimationProgress = time * STRIDE_RATE;
        state.limbSwingAmplitude = mob == MobChoice.SLIME ? 0.0F : 0.65F;
        state.baseScale = 1.0F;
        state.ageScale = 1.0F;
        state.pose = EntityPose.STANDING;
        state.width = mob == MobChoice.SLIME ? 1.0F : 0.9F;
        state.height = switch (mob) {
            case BEE -> 0.6F;
            case CREEPER -> 1.7F;
            case SLIME -> 1.0F;
            case RABBIT -> 0.5F;
            case BAT -> 0.9F;
            case COD -> 0.3F;
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
            case RABBIT -> {
                //? if >=1.21.2 {
                /*if (rabbitModel == null) rabbitModel = RabbitEntityModel.getTexturedModelData(false).createModel();
                *///? } else {
                if (rabbitModel == null) rabbitModel = RabbitEntityModel.getTexturedModelData().createModel();
                //? }
                yield rabbitModel;
            }
            case BAT -> {
                if (batModel == null) batModel = BatEntityModel.getTexturedModelData().createModel();
                yield batModel;
            }
            case COD -> {
                if (codModel == null) codModel = CodEntityModel.getTexturedModelData().createModel();
                yield codModel;
            }
            default -> throw new IllegalArgumentException("Not a model choice: " + mob);
        };
    }

    private static void animate(ModelPart root, MobChoice mob, float time, int turnSign) {
        float phase = time * STRIDE_RATE;
        float step = (float) Math.sin(phase) * 0.55F;
        switch (mob) {
            case VANILLA_FOX -> {
                root.getChild("right_hind_leg").pitch = step;
                root.getChild("left_hind_leg").pitch = -step;
                root.getChild("right_front_leg").pitch = -step;
                root.getChild("left_front_leg").pitch = step;
                float bend = -turnSign * foxBend(time);
                float flow = (float) Math.sin(phase * 0.5F - 0.35F);
                root.getChild("body").yaw += bend * 0.45F + flow * 0.08F;
                root.getChild("head").yaw += bend + flow * 0.04F;
                root.getChild("body").getChild("tail").yaw -= bend * 1.25F;
                root.getChild("body").getChild("tail").yaw += (float) Math.sin(phase * 0.5F - 0.8F) * 0.22F;
                root.getChild("body").roll += -0.05F + flow * 0.06F;
                root.getChild("body").pitch += step * 0.08F;
                root.getChild("head").pitch -= step * 0.06F;
                root.getChild("body").getChild("tail").pitch += step * 0.14F
                        + (float) Math.sin(phase * 0.5F - 1.0F) * 0.12F;
                root.getChild("body").getChild("tail").roll = (float) Math.sin(phase * 0.5F - 1.2F) * 0.11F;
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
            case RABBIT -> {
                float leap = (float) Math.sin(Math.PI * hopProgress(time, RABBIT_HOP_SECONDS));
                root.getChild("left_haunch").pitch += leap * 0.87F;
                root.getChild("right_haunch").pitch += leap * 0.87F;
                root.getChild("left_front_leg").pitch -= leap * 0.70F;
                root.getChild("right_front_leg").pitch -= leap * 0.70F;
            }
            case BAT -> {
                float flap = (float) Math.cos(time * 18.0F) * 0.8F;
                root.getChild("body").getChild("right_wing").yaw = flap;
                root.getChild("body").getChild("left_wing").yaw = -flap;
            }
            case COD -> root.getChild("tail_fin").yaw = (float) Math.sin(time * 13.0F) * 0.4F;
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
            case RABBIT -> RABBIT_TEXTURE;
            case BAT -> BAT_TEXTURE;
            case COD -> COD_TEXTURE;
            default -> throw new IllegalArgumentException("Not a model choice: " + mob);
        };
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(centerX, centerY + size * 0.5F + (mob == MobChoice.BEE ? Math.sin(time * 5.0F) * 5.0F : 0.0F), 200.0F);
        if (mob == MobChoice.SLIME) {
            float squish = slimeSquish(time);
            float width = size * (1.0F - squish * 0.45F);
            matrices.scale(width, -size * (1.0F + squish * 0.70F), width);
        } else {
            matrices.scale(size, -size, size);
        }
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-cameraAngle));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(-facing));
        RenderLayer layer = mob == MobChoice.SLIME
                ? RenderLayer.getEntityTranslucent(texture) : RenderLayer.getEntityCutoutNoCull(texture);
        model.render(matrices, provider.getBuffer(layer), 0xF000F0, 0);
        matrices.pop();
    }
    //? }

    private static float foxBend(float time) {
        return 0.22F + (float) Math.sin(time * STRIDE_RATE * 0.5F) * 0.075F;
    }

    private static float slimeSquish(float time) {
        double hop = time / SLIME_HOP_SECONDS;
        double phase = hop - Math.floor(hop);
        double travel = Math.clamp((phase - 0.12) / 0.70, 0.0, 1.0);
        float airborneStretch = (float) Math.sin(Math.PI * travel) * 0.13F;
        double landingPhase = (phase - 0.86) / 0.075;
        float landingSquash = (float) Math.exp(-landingPhase * landingPhase) * 0.20F;
        return airborneStretch - landingSquash;
    }

    private static float hopProgress(float time, double hopSeconds) {
        double hop = time / hopSeconds;
        return (float) Math.clamp((hop - Math.floor(hop) - 0.12) / 0.70, 0.0, 1.0);
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
            case RABBIT -> EntityTypes.RABBIT;
            case BAT -> EntityTypes.BAT;
            case COD -> EntityTypes.COD;
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
            case RABBIT -> EntityType.RABBIT;
            case BAT -> EntityType.BAT;
            case COD -> EntityType.COD;
            default -> throw new IllegalArgumentException("Not a model choice: " + mob);
        };
    }
    *///? }
}
