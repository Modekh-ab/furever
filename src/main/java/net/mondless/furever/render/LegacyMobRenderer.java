//? if <1.21.6 {
package net.mondless.furever.render;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.mondless.furever.config.MobChoice;
import net.mondless.furever.config.MobTextureVariant;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class LegacyMobRenderer {
    private static final Map<MobChoice, Identifier> TEXTURES = new EnumMap<>(MobChoice.class);

    private LegacyMobRenderer() { }

    static boolean render(DrawContext context, MobChoice mob, int x, int y, float size,
                          float facing, int cameraAngle, float time, String variantId, boolean baby) {
        EntityType<?> type = EntityType.get(mob.id()).orElse(null);
        if (type == null) return false;
        EntityRenderer renderer = renderer(type);
        EntityModel<?> model = renderer == null ? null : model(renderer);
        MobTextureVariant variant = MobTextureVariant.selected(mob, variantId);
        String variantPath = variant == null ? null : variant.texturePath(baby);
        Identifier texture = variantPath == null
                ? (renderer == null ? null : texture(renderer, mob))
                : Identifier.of("minecraft", variantPath);
        if (model == null || texture == null) return false;
        animate(model, mob, time);
        //? if <=1.21.1 {
        context.draw(() -> draw(context, context.getVertexConsumers(), model, texture, mob,
                x, y, size, facing, cameraAngle));
        //? } else {
        /*context.draw(provider -> draw(context, provider, model, texture, mob,
                x, y, size, facing, cameraAngle));
        *///? }
        return true;
    }

    private static EntityRenderer renderer(EntityType<?> type) {
        EntityRenderDispatcher dispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        for (Field field : dispatcher.getClass().getDeclaredFields()) {
            if (!Map.class.isAssignableFrom(field.getType())) continue;
            try {
                field.setAccessible(true);
                if (field.get(dispatcher) instanceof Map<?, ?> map && map.get(type) instanceof EntityRenderer result)
                    return result;
            } catch (ReflectiveOperationException | RuntimeException ignored) { }
        }
        return null;
    }

    private static EntityModel<?> model(EntityRenderer renderer) {
        for (Class<?> owner = renderer.getClass(); owner != null; owner = owner.getSuperclass()) {
            for (Field field : owner.getDeclaredFields()) {
                if (!EntityModel.class.isAssignableFrom(field.getType())) continue;
                try {
                    field.setAccessible(true);
                    if (field.get(renderer) instanceof EntityModel<?> model) return model;
                } catch (ReflectiveOperationException | RuntimeException ignored) { }
            }
        }
        return null;
    }

    private static Identifier texture(EntityRenderer renderer, MobChoice mob) {
        if (TEXTURES.containsKey(mob)) return TEXTURES.get(mob);
        Identifier result = resolveTexture(renderer, mob);
        TEXTURES.put(mob, result);
        return result;
    }

    private static Identifier resolveTexture(EntityRenderer renderer, MobChoice mob) {
        String common = switch (mob) {
            case ENDER_DRAGON -> "textures/entity/enderdragon/dragon.png";
            case MAGMA_CUBE -> "textures/entity/slime/magmacube.png";
            case ELDER_GUARDIAN -> "textures/entity/guardian_elder.png";
            case GUARDIAN -> "textures/entity/guardian.png";
            case SKELETON_HORSE -> "textures/entity/horse/horse_skeleton.png";
            case ZOMBIE_HORSE -> "textures/entity/horse/horse_zombie.png";
            case HORSE -> "textures/entity/horse/horse_white.png";
            case TROPICAL_FISH -> "textures/entity/fish/tropical_a.png";
            case BOGGED -> "textures/entity/skeleton/bogged.png";
            case STRAY -> "textures/entity/skeleton/stray.png";
            case SHULKER -> "textures/entity/shulker/shulker.png";
            default -> null;
        };
        if (common != null) {
            Identifier candidate = Identifier.of("minecraft", common);
            if (exists(candidate)) return candidate;
        }
        String id = mob.id();
        for (String path : List.of("textures/entity/" + id + "/" + id + ".png",
                "textures/entity/" + id + ".png")) {
            Identifier candidate = Identifier.of("minecraft", path);
            if (exists(candidate)) return candidate;
        }
        for (Method method : renderer.getClass().getMethods()) {
            if (method.getParameterCount() != 1 || method.getReturnType() != Identifier.class) continue;
            try {
                if (method.invoke(renderer, new Object[] {null}) instanceof Identifier found && exists(found))
                    return found;
            } catch (ReflectiveOperationException | RuntimeException ignored) { }
        }
        for (Class<?> owner = renderer.getClass(); owner != null; owner = owner.getSuperclass()) {
            for (Field field : owner.getDeclaredFields()) {
                if (field.getType() != Identifier.class || !Modifier.isStatic(field.getModifiers())) continue;
                try {
                    field.setAccessible(true);
                    if (field.get(null) instanceof Identifier found && exists(found)) return found;
                } catch (ReflectiveOperationException | RuntimeException ignored) { }
            }
        }
        List<String> paths = new ArrayList<>();
        paths.add("textures/entity/" + id + "/" + id + ".png");
        paths.add("textures/entity/" + id + ".png");
        paths.add("textures/entity/" + id + "/" + id + "_white.png");
        paths.add("textures/entity/" + id + "/temperate_" + id + ".png");
        paths.add("textures/entity/" + id + "/" + id + "_temperate.png");
        paths.add("textures/entity/horse/" + id + ".png");
        paths.add("textures/entity/fish/" + id + ".png");
        paths.add("textures/entity/illager/" + id + ".png");
        for (String path : paths) {
            Identifier candidate = Identifier.of("minecraft", path);
            if (exists(candidate)) return candidate;
        }
        String needle = id.replace("_", "");
        return MinecraftClient.getInstance().getResourceManager()
                .findResources("textures/entity", candidate -> {
                    String path = candidate.getPath().replace("_", "");
                    return path.endsWith(".png") && path.contains(needle)
                            && !path.contains("eyes") && !path.contains("overlay")
                            && !path.contains("armor") && !path.contains("saddle");
                }).keySet().stream().min((a, b) -> Integer.compare(a.getPath().length(), b.getPath().length()))
                .orElse(null);
    }

    private static boolean exists(Identifier texture) {
        return MinecraftClient.getInstance().getResourceManager().getResource(texture).isPresent();
    }

    private static void animate(EntityModel<?> model, MobChoice mob, float time) {
        Set<ModelPart> seen = Collections.newSetFromMap(new IdentityHashMap<>());
        List<ModelPart> parts = new ArrayList<>();
        for (Class<?> owner = model.getClass(); owner != null; owner = owner.getSuperclass()) {
            for (Field field : owner.getDeclaredFields()) {
                if (!ModelPart.class.isAssignableFrom(field.getType())) continue;
                try {
                    field.setAccessible(true);
                    if (field.get(model) instanceof ModelPart part && seen.add(part)) parts.add(part);
                } catch (ReflectiveOperationException | RuntimeException ignored) { }
            }
        }
        for (ModelPart part : parts) part.traverse().forEach(ModelPart::resetTransform);
        for (ModelPart part : parts) animateChildren(part, mob, time, seen);
    }

    private static void animateChildren(ModelPart part, MobChoice mob, float time, Set<ModelPart> seen) {
        for (Field field : ModelPart.class.getDeclaredFields()) {
            if (!Map.class.isAssignableFrom(field.getType())) continue;
            try {
                field.setAccessible(true);
                if (!(field.get(part) instanceof Map<?, ?> map)) continue;
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    if (!(entry.getKey() instanceof String name) || !(entry.getValue() instanceof ModelPart child)) continue;
                    movePart(child, name, mob, time);
                    if (seen.add(child)) animateChildren(child, mob, time, seen);
                }
                return;
            } catch (ReflectiveOperationException | RuntimeException ignored) { }
        }
    }

    private static void movePart(ModelPart part, String name, MobChoice mob, float time) {
        String key = name.toLowerCase(java.util.Locale.ROOT);
        float wave = (float) Math.sin(time * 16.0F);
        switch (mob.movement()) {
            case RUN, CRAWL -> {
                if (key.contains("leg") || key.contains("arm")) {
                    float side = key.contains("left") ? -1.0F : 1.0F;
                    float front = key.contains("front") ? -1.0F : 1.0F;
                    part.pitch += wave * side * front * 0.55F;
                }
            }
            case FLY -> {
                if (key.contains("wing"))
                    part.roll += (float) Math.sin(time * 19.0F) * (key.contains("left") ? -0.7F : 0.7F);
            }
            case SWIM -> {
                if (key.contains("tail") || key.contains("fin")) part.yaw += wave * 0.4F;
                if (key.contains("leg") || key.contains("flipper"))
                    part.roll += wave * (key.contains("left") ? -0.5F : 0.5F);
            }
            case JUMP -> {
                if (key.contains("leg")) part.pitch += (float) Math.sin(time * 9.0F) * 0.6F;
            }
            case FLOAT, IDLE -> { }
        }
    }

    private static void draw(DrawContext context, VertexConsumerProvider provider, EntityModel<?> model,
                             Identifier texture, MobChoice mob, int x, int y, float size,
                             float facing, int cameraAngle) {
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(x, y + size * 0.5F, 200.0F);
        matrices.scale(size, -size, size);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-cameraAngle));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation(-facing));
        var vertices = provider.getBuffer(RenderLayer.getEntityCutoutNoCull(texture));
        if (mob == MobChoice.ENDER_DRAGON) {
            List<ModelPart> roots = parts(model);
            Set<ModelPart> nested = Collections.newSetFromMap(new IdentityHashMap<>());
            for (ModelPart root : roots) root.traverse().skip(1).forEach(nested::add);
            for (ModelPart root : roots) if (!nested.contains(root)) root.render(matrices, vertices, 0xF000F0, 0);
        } else {
            model.render(matrices, vertices, 0xF000F0, 0);
        }
        matrices.pop();
    }

    private static List<ModelPart> parts(EntityModel<?> model) {
        List<ModelPart> result = new ArrayList<>();
        for (Class<?> owner = model.getClass(); owner != null; owner = owner.getSuperclass()) {
            for (Field field : owner.getDeclaredFields()) {
                if (!ModelPart.class.isAssignableFrom(field.getType())) continue;
                try {
                    field.setAccessible(true);
                    if (field.get(model) instanceof ModelPart part && !result.contains(part)) result.add(part);
                } catch (ReflectiveOperationException | RuntimeException ignored) { }
            }
        }
        return result;
    }
}
//? }
