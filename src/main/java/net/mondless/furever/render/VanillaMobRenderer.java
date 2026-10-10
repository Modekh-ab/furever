package net.mondless.furever.render;

import net.mondless.furever.config.FureverConfig;
import net.mondless.furever.config.MobChoice;
import net.mondless.furever.config.MobTextureVariant;
//? if >=1.21.6 {
/*import org.joml.Quaternionf;
import org.joml.Vector3f;
*///? }

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
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.client.renderer.entity.state.PiglinRenderState;
import net.minecraft.client.renderer.entity.state.ZombifiedPiglinRenderState;
import net.minecraft.client.renderer.entity.state.PandaRenderState;
import net.minecraft.client.renderer.entity.state.SheepRenderState;
import net.minecraft.client.renderer.entity.state.GoatRenderState;
import net.minecraft.client.renderer.entity.state.WolfRenderState;
import net.minecraft.client.renderer.entity.state.CatRenderState;
import net.minecraft.client.renderer.entity.state.FelineRenderState;
import net.minecraft.client.renderer.entity.state.TurtleRenderState;
import net.minecraft.client.renderer.entity.state.PolarBearRenderState;
import net.minecraft.client.renderer.entity.state.HoglinRenderState;
import net.minecraft.client.renderer.entity.state.EnderDragonRenderState;
import net.minecraft.client.renderer.entity.state.ParrotRenderState;
import net.minecraft.client.renderer.entity.state.PhantomRenderState;
import net.minecraft.client.renderer.entity.state.SquidRenderState;
import net.minecraft.client.renderer.entity.state.DolphinRenderState;
import net.minecraft.world.entity.monster.piglin.PiglinArmPose;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.animal.fox.Fox;
import net.minecraft.world.entity.animal.rabbit.Rabbit;
import net.minecraft.world.entity.animal.panda.Panda;
import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.DyeColor;
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
import net.minecraft.client.render.entity.state.ZombieEntityRenderState;
import net.minecraft.client.render.entity.state.PiglinEntityRenderState;
import net.minecraft.client.render.entity.state.ZombifiedPiglinEntityRenderState;
import net.minecraft.client.render.entity.state.PandaEntityRenderState;
import net.minecraft.client.render.entity.state.SheepEntityRenderState;
import net.minecraft.client.render.entity.state.GoatEntityRenderState;
import net.minecraft.client.render.entity.state.WolfEntityRenderState;
import net.minecraft.client.render.entity.state.CatEntityRenderState;
import net.minecraft.client.render.entity.state.FelineEntityRenderState;
import net.minecraft.client.render.entity.state.TurtleEntityRenderState;
import net.minecraft.client.render.entity.state.PolarBearEntityRenderState;
import net.minecraft.client.render.entity.state.HoglinEntityRenderState;
import net.minecraft.client.render.entity.state.EnderDragonEntityRenderState;
import net.minecraft.client.render.entity.state.ParrotEntityRenderState;
import net.minecraft.client.render.entity.state.PhantomEntityRenderState;
import net.minecraft.client.render.entity.state.SquidEntityRenderState;
import net.minecraft.client.render.entity.state.DolphinEntityRenderState;
import net.minecraft.entity.mob.PiglinActivity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.entity.passive.PandaEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
*///? } else {
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BeeEntityModel;
import net.minecraft.client.render.entity.model.CreeperEntityModel;
import net.minecraft.client.render.entity.model.FoxEntityModel;
import net.minecraft.client.render.entity.model.SlimeEntityModel;
import net.minecraft.client.render.entity.model.RabbitEntityModel;
import net.minecraft.client.render.entity.model.BatEntityModel;
import net.minecraft.client.render.entity.model.CodEntityModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.DrownedEntityModel;
import net.minecraft.client.render.entity.model.PiglinEntityModel;
import net.minecraft.client.render.entity.model.PandaEntityModel;
import net.minecraft.client.render.entity.model.SheepEntityModel;
import net.minecraft.client.render.entity.model.SheepWoolEntityModel;
import net.minecraft.client.render.entity.model.GoatEntityModel;
import net.minecraft.client.render.entity.model.WolfEntityModel;
import net.minecraft.client.render.entity.model.OcelotEntityModel;
import net.minecraft.client.render.entity.model.TurtleEntityModel;
import net.minecraft.client.render.entity.model.PolarBearEntityModel;
import net.minecraft.client.render.entity.model.HoglinEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.DyeColor;
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
        if (mob.movement() == MobChoice.Movement.JUMP) {
            double hopSeconds = mob == MobChoice.SLIME || mob == MobChoice.MAGMA_CUBE || mob == MobChoice.SULFUR_CUBE
                    ? SLIME_HOP_SECONDS : RABBIT_HOP_SECONDS;
            int hopsPerLap = mob == MobChoice.SLIME || mob == MobChoice.MAGMA_CUBE || mob == MobChoice.SULFUR_CUBE
                    ? SLIME_HOPS_PER_LAP : RABBIT_HOPS_PER_LAP;
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
        int x = mob.movement() == MobChoice.Movement.IDLE ? centerX
                : Math.round(centerX + (float) Math.cos(angle) * radius);
        float hover = switch (mob.movement()) {
            case FLY -> size * (0.10F + (float) Math.sin(elapsed * 8.0) * 0.05F);
            case SWIM, FLOAT -> size * (float) Math.sin(elapsed * 5.0) * 0.03F;
            default -> 0.0F;
        };

        int y = Math.round(centerY + (mob.movement() == MobChoice.Movement.IDLE
                ? 0.0F : (float) Math.sin(angle) * radius) - jump - hover);

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
    private static final Identifier ZOMBIE_TEXTURE = Identifier.of("minecraft", "textures/entity/zombie/zombie.png");
    private static final Identifier HUSK_TEXTURE = Identifier.of("minecraft", "textures/entity/zombie/husk.png");
    private static final Identifier DROWNED_TEXTURE = Identifier.of("minecraft", "textures/entity/zombie/drowned.png");
    private static final Identifier PIGLIN_TEXTURE = Identifier.of("minecraft", "textures/entity/piglin/piglin.png");
    private static final Identifier ZOMBIFIED_PIGLIN_TEXTURE = Identifier.of("minecraft", "textures/entity/piglin/zombified_piglin.png");
    private static final Identifier PANDA_TEXTURE = Identifier.of("minecraft", "textures/entity/panda/panda.png");
    private static final Identifier SHEEP_TEXTURE = Identifier.of("minecraft", "textures/entity/sheep/sheep.png");
    //? if >=1.21.5 {
    /*private static final Identifier SHEEP_WOOL_TEXTURE = Identifier.of("minecraft", "textures/entity/sheep/sheep_wool.png");
    *///? } else {
    private static final Identifier SHEEP_WOOL_TEXTURE = Identifier.of("minecraft", "textures/entity/sheep/sheep_fur.png");
    //? }
    private static final Identifier GOAT_TEXTURE = Identifier.of("minecraft", "textures/entity/goat/goat.png");
    private static final Identifier WOLF_TEXTURE = Identifier.of("minecraft", "textures/entity/wolf/wolf.png");
    private static final Identifier CAT_TEXTURE = Identifier.of("minecraft", "textures/entity/cat/tabby.png");
    private static final Identifier OCELOT_TEXTURE = Identifier.of("minecraft", "textures/entity/cat/ocelot.png");
    private static final Identifier TURTLE_TEXTURE = Identifier.of("minecraft", "textures/entity/turtle/big_sea_turtle.png");
    private static final Identifier POLAR_BEAR_TEXTURE = Identifier.of("minecraft", "textures/entity/bear/polarbear.png");
    private static final Identifier HOGLIN_TEXTURE = Identifier.of("minecraft", "textures/entity/hoglin/hoglin.png");
    private static final Identifier ZOGLIN_TEXTURE = Identifier.of("minecraft", "textures/entity/hoglin/zoglin.png");
    private static ModelPart foxModel;
    private static ModelPart beeModel;
    private static ModelPart creeperModel;
    private static ModelPart slimeModel;
    private static ModelPart rabbitModel;
    private static ModelPart batModel;
    private static ModelPart codModel;
    private static ModelPart zombieModel;
    private static ModelPart drownedModel;
    private static ModelPart piglinModel;
    private static ModelPart pandaModel;
    private static ModelPart sheepModel;
    private static ModelPart sheepWoolModel;
    private static ModelPart goatModel;
    private static ModelPart wolfModel;
    private static ModelPart ocelotModel;
    private static ModelPart turtleModel;
    private static ModelPart polarBearModel;
    private static ModelPart hoglinModel;
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
            case FOX -> 0.29F;
            case BEE -> 0.43F;
            case CREEPER -> 0.30F;
            case SLIME -> 0.40F;
            case RABBIT -> 0.36F;
            case BAT -> 0.36F;
            case COD -> 0.42F;
            case ZOMBIE, HUSK, DROWNED -> 0.27F;
            case PIGLIN, ZOMBIFIED_PIGLIN -> 0.27F;
            case PANDA, SHEEP, GOAT, POLAR_BEAR, HOGLIN, ZOGLIN -> 0.29F;
            case WOLF, CAT, OCELOT -> 0.32F;
            case TURTLE -> 0.35F;
            case ENDER_DRAGON -> 0.045F;
            case GHAST, HAPPY_GHAST -> 0.11F;
            case WITHER, WARDEN, RAVAGER, ELDER_GUARDIAN -> 0.18F;
            default -> 0.29F;
        };
        //? if <1.21.6 {
        float babyScale = config.baby && mob.supportsBaby() ? 0.55F : 1.0F;
        //? } else {
        /*float babyScale = 1.0F;
        *///? }
        float renderSize = box * modelScale * DEFAULT_MODEL_SCALE * babyScale;
        Orbit orbit = orbit(centerX, centerY, box * modelScale, config.speed, config.orbitRadius,
                config.cameraAngle, config.clockwise, mob);
        float time = orbit.animationTime();
        int turnSign = config.clockwise ? 1 : -1;

        //? if >=26.1 {
        /*EntityRenderState state = createState(mob, time, turnSign, config.baby, config.textureVariant);
        if (Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(state) == null) return false;
        Quaternionf rotation = new Quaternionf().rotateZ((float) Math.PI)
                .rotateX((float) Math.toRadians(-config.cameraAngle)).rotateY(orbit.facing());
        context.entity(state, renderSize,
                new Vector3f(0.0F, state.boundingBoxHeight * 0.5F, 0.0F),
                rotation, new Quaternionf(), orbit.x() - box / 2, orbit.y() - box / 2,
                orbit.x() + box / 2, orbit.y() + box / 2);
        *///? } else if >=1.21.6 {
        /*EntityRenderState state = createState(mob, time, turnSign, config.baby, config.textureVariant);
        if (MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(state) == null) return false;
        Quaternionf rotation = new Quaternionf().rotateZ((float) Math.PI)
                .rotateX((float) Math.toRadians(-config.cameraAngle)).rotateY(orbit.facing());
        context.addEntity(state, renderSize,
                new Vector3f(0.0F, state.height * 0.5F, 0.0F),
                rotation, new Quaternionf(), orbit.x() - box / 2, orbit.y() - box / 2,
                orbit.x() + box / 2, orbit.y() + box / 2);
        *///? } else {
        if (!legacyCustomModel(mob))
            return LegacyMobRenderer.render(context, mob, orbit.x(), orbit.y(), renderSize,
                    orbit.facing(), config.cameraAngle, time, config.textureVariant, config.baby);
        ModelPart model = getModel(mob);
        model.traverse().forEach(ModelPart::resetTransform);
        animate(model, mob, time, turnSign);
        //? if <=1.21.1 {
        context.draw(() -> drawLegacy(context, context.getVertexConsumers(), model, mob,
                orbit.x(), orbit.y(), renderSize, orbit.facing(), config.cameraAngle, time,
                config.textureVariant, config.baby));
        //? } else {
        /*context.draw(provider -> drawLegacy(context, provider, model, mob,
                orbit.x(), orbit.y(), renderSize, orbit.facing(), config.cameraAngle, time,
                config.textureVariant, config.baby));
        *///? }
        //? }
        return true;
    }

    //? if >=26.1 {
    /*private static EntityRenderState createState(MobChoice mob, float time, int turnSign, boolean baby,
                                                 String variantId) {
        if (mob == MobChoice.ENDER_DRAGON) {
            EnderDragonRenderState dragon = new EnderDragonRenderState();
            dragon.entityType = getType(mob);
            dragon.ageInTicks = time * 20.0F;
            dragon.boundingBoxWidth = 8.0F;
            dragon.boundingBoxHeight = 8.0F;
            dragon.lightCoords = 0xF000F0;
            dragon.flapTime = time * 0.7F;
            for (int i = 0; i < 64; i++) dragon.flightHistory.record(0.0, 0.0F);
            return dragon;
        }
        LivingEntityRenderState state = switch (mob) {
            case FOX -> {
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
            case ZOMBIE, HUSK, DROWNED -> new ZombieRenderState();
            case PIGLIN -> {
                PiglinRenderState piglin = new PiglinRenderState();
                piglin.armPose = PiglinArmPose.DEFAULT;
                yield piglin;
            }
            case ZOMBIFIED_PIGLIN -> new ZombifiedPiglinRenderState();
            case PANDA -> {
                PandaRenderState panda = new PandaRenderState();
                panda.variant = Panda.Gene.NORMAL;
                yield panda;
            }
            case SHEEP -> {
                SheepRenderState sheep = new SheepRenderState();
                sheep.woolColor = DyeColor.WHITE;
                yield sheep;
            }
            case GOAT -> {
                GoatRenderState goat = new GoatRenderState();
                goat.hasLeftHorn = true;
                goat.hasRightHorn = true;
                yield goat;
            }
            case WOLF -> {
                WolfRenderState wolf = new WolfRenderState();
                wolf.texture = Identifier.fromNamespaceAndPath("minecraft",
                        baby ? "textures/entity/wolf/wolf_baby.png" : "textures/entity/wolf/wolf.png");
                yield wolf;
            }
            case CAT -> {
                CatRenderState cat = new CatRenderState();
                cat.texture = Identifier.fromNamespaceAndPath("minecraft",
                        baby ? "textures/entity/cat/cat_tabby_baby.png" : "textures/entity/cat/cat_tabby.png");
                yield cat;
            }
            case OCELOT -> new FelineRenderState();
            case TURTLE -> {
                TurtleRenderState turtle = new TurtleRenderState();
                turtle.isOnLand = false;
                yield turtle;
            }
            case POLAR_BEAR -> new PolarBearRenderState();
            case HOGLIN, ZOGLIN -> new HoglinRenderState();
            case PARROT -> {
                ParrotRenderState parrot = new ParrotRenderState();
                parrot.flapAngle = time * 18.0F;
                yield parrot;
            }
            case PHANTOM -> {
                PhantomRenderState phantom = new PhantomRenderState();
                phantom.flapTime = time * 18.0F;
                yield phantom;
            }
            case SQUID, GLOW_SQUID -> {
                SquidRenderState squid = new SquidRenderState();
                squid.tentacleAngle = time * 8.0F;
                yield squid;
            }
            case DOLPHIN -> {
                DolphinRenderState dolphin = new DolphinRenderState();
                dolphin.isMoving = true;
                yield dolphin;
            }
            default -> {
                EntityRenderState lookup = new EntityRenderState();
                lookup.entityType = getType(mob);
                var renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(lookup);
                yield (LivingEntityRenderState) renderer.createRenderState();
            }
        };
        state.entityType = getType(mob);
        if (state instanceof SlimeRenderState slimeState) {
            slimeState.size = 2;
            slimeState.squish = slimeSquish(time);
        }
        state.ageInTicks = time * 20.0F;
        state.isBaby = baby && mob.supportsBaby();
        state.isInWater = mob.movement() == MobChoice.Movement.SWIM;
        state.walkAnimationPos = time * STRIDE_RATE;
        state.walkAnimationSpeed = mob == MobChoice.SLIME ? 0.0F : 0.65F;
        state.scale = 1.0F;
        state.ageScale = 1.0F;
        state.pose = Pose.STANDING;
        state.boundingBoxWidth = state.entityType.getDimensions().width();
        state.boundingBoxHeight = state.entityType.getDimensions().height();
        state.lightCoords = 0xF000F0;
        ModernMobStateDefaults.apply(state, mob, variantId, baby);
        return state;
    }
    *///? } else if >=1.21.6 {
    /*private static EntityRenderState createState(MobChoice mob, float time, int turnSign, boolean baby,
                                                 String variantId) {
        if (mob == MobChoice.ENDER_DRAGON) {
            EnderDragonEntityRenderState dragon = new EnderDragonEntityRenderState();
            dragon.entityType = EntityType.get(mob.id()).orElseThrow();
            dragon.age = time * 20.0F;
            dragon.width = 8.0F;
            dragon.height = 8.0F;
            dragon.wingPosition = time * 0.7F;
            for (int i = 0; i < 64; i++) dragon.frameTracker.tick(0.0, 0.0F);
            setLight(dragon);
            return dragon;
        }
        LivingEntityRenderState state = switch (mob) {
            case FOX -> {
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
            case ZOMBIE, HUSK, DROWNED -> new ZombieEntityRenderState();
            case PIGLIN -> {
                PiglinEntityRenderState piglin = new PiglinEntityRenderState();
                piglin.activity = PiglinActivity.DEFAULT;
                yield piglin;
            }
            case ZOMBIFIED_PIGLIN -> new ZombifiedPiglinEntityRenderState();
            case PANDA -> {
                PandaEntityRenderState panda = new PandaEntityRenderState();
                panda.gene = PandaEntity.Gene.NORMAL;
                yield panda;
            }
            case SHEEP -> {
                SheepEntityRenderState sheep = new SheepEntityRenderState();
                sheep.color = DyeColor.WHITE;
                yield sheep;
            }
            case GOAT -> {
                GoatEntityRenderState goat = new GoatEntityRenderState();
                goat.hasLeftHorn = true;
                goat.hasRightHorn = true;
                yield goat;
            }
            case WOLF -> {
                WolfEntityRenderState wolf = new WolfEntityRenderState();
                wolf.texture = Identifier.of("minecraft", "textures/entity/wolf/wolf.png");
                yield wolf;
            }
            case CAT -> {
                CatEntityRenderState cat = new CatEntityRenderState();
                cat.texture = Identifier.of("minecraft", "textures/entity/cat/tabby.png");
                yield cat;
            }
            case OCELOT -> new FelineEntityRenderState();
            case TURTLE -> {
                TurtleEntityRenderState turtle = new TurtleEntityRenderState();
                turtle.onLand = false;
                yield turtle;
            }
            case POLAR_BEAR -> new PolarBearEntityRenderState();
            case HOGLIN, ZOGLIN -> new HoglinEntityRenderState();
            case PARROT -> {
                ParrotEntityRenderState parrot = new ParrotEntityRenderState();
                parrot.flapAngle = time * 18.0F;
                yield parrot;
            }
            case PHANTOM -> {
                PhantomEntityRenderState phantom = new PhantomEntityRenderState();
                phantom.wingFlapProgress = time * 18.0F;
                yield phantom;
            }
            case SQUID, GLOW_SQUID -> {
                SquidEntityRenderState squid = new SquidEntityRenderState();
                squid.tentacleAngle = time * 8.0F;
                yield squid;
            }
            case DOLPHIN -> {
                DolphinEntityRenderState dolphin = new DolphinEntityRenderState();
                dolphin.moving = true;
                yield dolphin;
            }
            default -> {
                EntityRenderState lookup = new EntityRenderState();
                lookup.entityType = EntityType.get(mob.id()).orElseThrow();
                var renderer = MinecraftClient.getInstance().getEntityRenderDispatcher().getRenderer(lookup);
                yield (LivingEntityRenderState) renderer.createRenderState();
            }
        };
        state.entityType = switch (mob) {
            case FOX -> EntityType.FOX;
            case BEE -> EntityType.BEE;
            case CREEPER -> EntityType.CREEPER;
            case SLIME -> EntityType.SLIME;
            case RABBIT -> EntityType.RABBIT;
            case BAT -> EntityType.BAT;
            case COD -> EntityType.COD;
            case ZOMBIE -> EntityType.ZOMBIE;
            case HUSK -> EntityType.HUSK;
            case DROWNED -> EntityType.DROWNED;
            case PIGLIN -> EntityType.PIGLIN;
            case ZOMBIFIED_PIGLIN -> EntityType.ZOMBIFIED_PIGLIN;
            case PANDA -> EntityType.PANDA;
            case SHEEP -> EntityType.SHEEP;
            case GOAT -> EntityType.GOAT;
            case WOLF -> EntityType.WOLF;
            case CAT -> EntityType.CAT;
            case OCELOT -> EntityType.OCELOT;
            case TURTLE -> EntityType.TURTLE;
            case POLAR_BEAR -> EntityType.POLAR_BEAR;
            case HOGLIN -> EntityType.HOGLIN;
            case ZOGLIN -> EntityType.ZOGLIN;
            default -> EntityType.get(mob.id()).orElseThrow();
        };
        if (state instanceof SlimeEntityRenderState slimeState) {
            slimeState.size = 2;
            slimeState.stretch = slimeSquish(time);
        }
        state.age = time * 20.0F;
        state.baby = baby && mob.supportsBaby();
        state.touchingWater = mob.movement() == MobChoice.Movement.SWIM;
        state.limbSwingAnimationProgress = time * STRIDE_RATE;
        state.limbSwingAmplitude = mob == MobChoice.SLIME ? 0.0F : 0.65F;
        state.baseScale = 1.0F;
        state.ageScale = 1.0F;
        state.pose = EntityPose.STANDING;
        state.width = state.entityType.getDimensions().width();
        state.height = state.entityType.getDimensions().height();
        setLight(state);
        ModernMobStateDefaults.apply(state, mob, variantId, baby);
        return state;
    }
    *///? } else {
    private static ModelPart getModel(MobChoice mob) {
        return switch (mob) {
            case FOX -> {
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
            case ZOMBIE, HUSK -> {
                if (zombieModel == null) zombieModel = TexturedModelData.of(
                        BipedEntityModel.getModelData(Dilation.NONE, 0.0F), 64, 64).createModel();
                yield zombieModel;
            }
            case DROWNED -> {
                if (drownedModel == null) drownedModel = DrownedEntityModel.getTexturedModelData(Dilation.NONE).createModel();
                yield drownedModel;
            }
            case PIGLIN, ZOMBIFIED_PIGLIN -> {
                if (piglinModel == null) piglinModel = TexturedModelData.of(
                        PiglinEntityModel.getModelData(Dilation.NONE), 64, 64).createModel();
                yield piglinModel;
            }
            case PANDA -> {
                if (pandaModel == null) pandaModel = PandaEntityModel.getTexturedModelData().createModel();
                yield pandaModel;
            }
            case SHEEP -> {
                if (sheepModel == null) sheepModel = SheepEntityModel.getTexturedModelData().createModel();
                yield sheepModel;
            }
            case GOAT -> {
                if (goatModel == null) goatModel = GoatEntityModel.getTexturedModelData().createModel();
                yield goatModel;
            }
            case WOLF -> {
                if (wolfModel == null) wolfModel = TexturedModelData.of(
                        WolfEntityModel.getTexturedModelData(Dilation.NONE), 64, 32).createModel();
                yield wolfModel;
            }
            case CAT, OCELOT -> {
                if (ocelotModel == null) ocelotModel = TexturedModelData.of(
                        OcelotEntityModel.getModelData(Dilation.NONE), 64, 32).createModel();
                yield ocelotModel;
            }
            case TURTLE -> {
                if (turtleModel == null) turtleModel = TurtleEntityModel.getTexturedModelData().createModel();
                yield turtleModel;
            }
            case POLAR_BEAR -> {
                //? if >=1.21.5 {
                /*if (polarBearModel == null) polarBearModel = PolarBearEntityModel.getTexturedModelData(false).createModel();
                *///? } else {
                if (polarBearModel == null) polarBearModel = PolarBearEntityModel.getTexturedModelData().createModel();
                //? }
                yield polarBearModel;
            }
            case HOGLIN, ZOGLIN -> {
                if (hoglinModel == null) hoglinModel = HoglinEntityModel.getTexturedModelData().createModel();
                yield hoglinModel;
            }
            default -> throw new IllegalArgumentException("No legacy model: " + mob);
        };
    }

    private static boolean legacyCustomModel(MobChoice mob) {
        return switch (mob) {
            case FOX, BEE, CREEPER, SLIME, RABBIT, BAT, COD, ZOMBIE, HUSK,
                    DROWNED, PIGLIN, ZOMBIFIED_PIGLIN, PANDA, SHEEP, GOAT,
                    WOLF, CAT, OCELOT, TURTLE, POLAR_BEAR, HOGLIN, ZOGLIN -> true;
            default -> false;
        };
    }

    private static void animate(ModelPart root, MobChoice mob, float time, int turnSign) {
        float phase = time * STRIDE_RATE;
        float step = (float) Math.sin(phase) * 0.55F;
        switch (mob) {
            case FOX -> {
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
            case PANDA, SHEEP, GOAT, WOLF, CAT, OCELOT, POLAR_BEAR, HOGLIN, ZOGLIN -> {
                root.getChild("right_hind_leg").pitch = step;
                root.getChild("left_hind_leg").pitch = -step;
                root.getChild("right_front_leg").pitch = -step;
                root.getChild("left_front_leg").pitch = step;
                if (mob == MobChoice.WOLF) root.getChild("tail").yaw = (float) Math.sin(time * 7.0F) * 0.2F;
                if (mob == MobChoice.CAT || mob == MobChoice.OCELOT)
                    root.getChild("tail2").yaw = (float) Math.sin(time * 4.0F) * 0.15F;
                if (mob == MobChoice.HOGLIN || mob == MobChoice.ZOGLIN)
                    root.getChild("head").pitch += (float) Math.sin(phase * 0.5F) * 0.07F;
            }
            case TURTLE -> {
                float paddle = (float) Math.sin(time * 9.0F) * 0.65F;
                root.getChild("right_front_leg").roll = paddle;
                root.getChild("left_front_leg").roll = -paddle;
                root.getChild("right_hind_leg").roll = paddle * 0.45F;
                root.getChild("left_hind_leg").roll = -paddle * 0.45F;
            }
            case ZOMBIE, HUSK, DROWNED, PIGLIN, ZOMBIFIED_PIGLIN -> {
                root.getChild("right_leg").pitch = step;
                root.getChild("left_leg").pitch = -step;
                boolean zombieLike = mob == MobChoice.ZOMBIE || mob == MobChoice.HUSK
                        || mob == MobChoice.DROWNED || mob == MobChoice.ZOMBIFIED_PIGLIN;
                root.getChild("right_arm").pitch = (zombieLike ? -1.1F : 0.0F) - step * 0.6F;
                root.getChild("left_arm").pitch = (zombieLike ? -1.1F : 0.0F) + step * 0.6F;
                if (mob == MobChoice.PIGLIN || mob == MobChoice.ZOMBIFIED_PIGLIN) {
                    ModelPart head = root.getChild("head");
                    head.getChild("right_ear").roll += (float) Math.sin(time * 4.0F) * 0.08F;
                    head.getChild("left_ear").roll -= (float) Math.sin(time * 4.0F) * 0.08F;
                }
            }
            default -> { }
        }
    }

    private static void drawLegacy(DrawContext context, VertexConsumerProvider provider, ModelPart model,
                                   MobChoice mob, int centerX, int centerY, float size, float facing,
                                   int cameraAngle, float time, String variantId, boolean baby) {
        Identifier texture = switch (mob) {
            case FOX -> FOX_TEXTURE;
            case BEE -> BEE_TEXTURE;
            case CREEPER -> CREEPER_TEXTURE;
            case SLIME -> SLIME_TEXTURE;
            case RABBIT -> RABBIT_TEXTURE;
            case BAT -> BAT_TEXTURE;
            case COD -> COD_TEXTURE;
            case ZOMBIE -> ZOMBIE_TEXTURE;
            case HUSK -> HUSK_TEXTURE;
            case DROWNED -> DROWNED_TEXTURE;
            case PIGLIN -> PIGLIN_TEXTURE;
            case ZOMBIFIED_PIGLIN -> ZOMBIFIED_PIGLIN_TEXTURE;
            case PANDA -> PANDA_TEXTURE;
            case SHEEP -> SHEEP_TEXTURE;
            case GOAT -> GOAT_TEXTURE;
            case WOLF -> WOLF_TEXTURE;
            case CAT -> CAT_TEXTURE;
            case OCELOT -> OCELOT_TEXTURE;
            case TURTLE -> TURTLE_TEXTURE;
            case POLAR_BEAR -> POLAR_BEAR_TEXTURE;
            case HOGLIN -> HOGLIN_TEXTURE;
            case ZOGLIN -> ZOGLIN_TEXTURE;
            default -> throw new IllegalArgumentException("No legacy texture: " + mob);
        };
        MobTextureVariant variant = MobTextureVariant.selected(mob, variantId);
        if (mob != MobChoice.SHEEP && variant != null && variant.texturePath(baby) != null)
            texture = Identifier.of("minecraft", variant.texturePath(baby));
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(centerX, centerY - size * 0.5F + (mob == MobChoice.BEE ? Math.sin(time * 5.0F) * 5.0F : 0.0F), 200.0F);
        if (mob == MobChoice.SLIME) {
            float squish = slimeSquish(time);
            float width = size * (1.0F - squish * 0.45F);
            matrices.scale(width, size * (1.0F + squish * 0.70F), width);
        } else {
            matrices.scale(size, size, size);
        }
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-cameraAngle));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(facing));
        RenderLayer layer = mob == MobChoice.SLIME
                ? RenderLayer.getEntityTranslucent(texture) : RenderLayer.getEntityCutoutNoCull(texture);
        model.render(matrices, provider.getBuffer(layer), 0xF000F0, OverlayTexture.DEFAULT_UV);
        if (mob == MobChoice.SHEEP) {
            if (sheepWoolModel == null) sheepWoolModel = SheepWoolEntityModel.getTexturedModelData().createModel();
            sheepWoolModel.traverse().forEach(ModelPart::resetTransform);
            animate(sheepWoolModel, mob, time, 1);
            DyeColor woolColor = variant == null ? DyeColor.WHITE
                    : DyeColor.valueOf(variant.id().toUpperCase(java.util.Locale.ROOT));
            sheepWoolModel.render(matrices, provider.getBuffer(RenderLayer.getEntityCutoutNoCull(SHEEP_WOOL_TEXTURE)),
                    0xF000F0, OverlayTexture.DEFAULT_UV, 0xFF000000 | woolColor.getEntityColor());
        }
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
            case FOX -> EntityTypes.FOX;
            case BEE -> EntityTypes.BEE;
            case CREEPER -> EntityTypes.CREEPER;
            case SLIME -> EntityTypes.SLIME;
            case RABBIT -> EntityTypes.RABBIT;
            case BAT -> EntityTypes.BAT;
            case COD -> EntityTypes.COD;
            case ZOMBIE -> EntityTypes.ZOMBIE;
            case HUSK -> EntityTypes.HUSK;
            case DROWNED -> EntityTypes.DROWNED;
            case PIGLIN -> EntityTypes.PIGLIN;
            case ZOMBIFIED_PIGLIN -> EntityTypes.ZOMBIFIED_PIGLIN;
            case PANDA -> EntityTypes.PANDA;
            case SHEEP -> EntityTypes.SHEEP;
            case GOAT -> EntityTypes.GOAT;
            case WOLF -> EntityTypes.WOLF;
            case CAT -> EntityTypes.CAT;
            case OCELOT -> EntityTypes.OCELOT;
            case TURTLE -> EntityTypes.TURTLE;
            case POLAR_BEAR -> EntityTypes.POLAR_BEAR;
            case HOGLIN -> EntityTypes.HOGLIN;
            case ZOGLIN -> EntityTypes.ZOGLIN;
            default -> BuiltInRegistries.ENTITY_TYPE.getOptional(
                    Identifier.fromNamespaceAndPath("minecraft", mob.id())).orElseThrow();
        };
    }
    *///? } else if >=26.1 {
    /*private static EntityType<?> getType(MobChoice mob) {
        return switch (mob) {
            case FOX -> EntityType.FOX;
            case BEE -> EntityType.BEE;
            case CREEPER -> EntityType.CREEPER;
            case SLIME -> EntityType.SLIME;
            case RABBIT -> EntityType.RABBIT;
            case BAT -> EntityType.BAT;
            case COD -> EntityType.COD;
            case ZOMBIE -> EntityType.ZOMBIE;
            case HUSK -> EntityType.HUSK;
            case DROWNED -> EntityType.DROWNED;
            case PIGLIN -> EntityType.PIGLIN;
            case ZOMBIFIED_PIGLIN -> EntityType.ZOMBIFIED_PIGLIN;
            case PANDA -> EntityType.PANDA;
            case SHEEP -> EntityType.SHEEP;
            case GOAT -> EntityType.GOAT;
            case WOLF -> EntityType.WOLF;
            case CAT -> EntityType.CAT;
            case OCELOT -> EntityType.OCELOT;
            case TURTLE -> EntityType.TURTLE;
            case POLAR_BEAR -> EntityType.POLAR_BEAR;
            case HOGLIN -> EntityType.HOGLIN;
            case ZOGLIN -> EntityType.ZOGLIN;
            default -> BuiltInRegistries.ENTITY_TYPE.getOptional(
                    Identifier.fromNamespaceAndPath("minecraft", mob.id())).orElseThrow();
        };
    }
    *///? }
}
