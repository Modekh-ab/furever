//? if >=1.21.6 {
package net.mondless.furever.render;

//? if >=26.1 {
/*import net.minecraft.resources.Identifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.npc.villager.VillagerData;
*///? } else {
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerData;
//? }
import net.mondless.furever.config.MobChoice;
import net.mondless.furever.config.MobTextureVariant;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

final class ModernMobStateDefaults {
    private ModernMobStateDefaults() {
    }

    static void apply(Object state, MobChoice mob, String variantId, boolean baby) {
        MobTextureVariant selected = MobTextureVariant.selected(mob, variantId);
        boolean horseCoatSet = false;
        for (Field field : state.getClass().getFields()) {
            if (Modifier.isStatic(field.getModifiers())) continue;
            try {
                if (selected != null && field.getType() == Identifier.class
                        && (mob == MobChoice.CAT || mob == MobChoice.WOLF || mob == MobChoice.FROG)) {
                    String path = selected.texturePath(baby);
                    if (path != null) {
                        field.set(state, id(path));
                        continue;
                    }
                }
                if (selected != null && field.getType().isEnum()
                        && mob != MobChoice.CAT && mob != MobChoice.WOLF
                        && (mob != MobChoice.HORSE || !horseCoatSet)) {
                    String choice = selected.id().toUpperCase(java.util.Locale.ROOT);
                    for (Object value : field.getType().getEnumConstants()) {
                        if (((Enum<?>) value).name().equals(choice)) {
                            field.set(state, value);
                            if (mob == MobChoice.HORSE) horseCoatSet = true;
                            break;
                        }
                    }
                }
                if (field.get(state) != null) continue;
                if (field.getType().isEnum()) {
                    Object[] values = field.getType().getEnumConstants();
                    if (values.length > 0) field.set(state, values[0]);
                } else if (field.getType() == VillagerData.class) {
                    field.set(state, villagerData());
                } else if (field.getType() == Identifier.class && mob == MobChoice.FROG) {
                    field.set(state, id("textures/entity/frog/temperate_frog.png"));
                } else if (switch (mob) {
                    case COW, PIG, CHICKEN, ZOMBIE_NAUTILUS -> true;
                    default -> false;
                } && field.getType().isRecord()) {
                    Object variant = defaultVariant(field.getType(), mob, selected, baby);
                    if (variant != null) field.set(state, variant);
                }
            } catch (ReflectiveOperationException | RuntimeException ignored) {
            }
        }
    }

    private static Object defaultVariant(Class<?> variantType, MobChoice mob,
                                         MobTextureVariant selected, boolean baby) throws ReflectiveOperationException {
        String texture = selected == null ? switch (mob) {
            case COW -> "entity/cow/temperate_cow";
            case PIG -> "entity/pig/temperate_pig";
            case CHICKEN -> "entity/chicken/temperate_chicken";
            case ZOMBIE_NAUTILUS -> "entity/nautilus/zombie_nautilus";
            default -> throw new IllegalArgumentException();
        } : selected.assetId(false);
        Class<?> modelType = null;
        for (Class<?> nested : variantType.getDeclaredClasses()) {
            if (nested.isEnum()) {
                modelType = nested;
                break;
            }
        }
        if (modelType == null) return null;
        Object model = modelType.getEnumConstants()[0];
        if (selected != null) {
            for (Object value : modelType.getEnumConstants()) {
                if (((Enum<?>) value).name().equalsIgnoreCase(selected.id())) {
                    model = value;
                    break;
                }
            }
        }

        for (Constructor<?> variantConstructor : variantType.getConstructors()) {
            Class<?>[] types = variantConstructor.getParameterTypes();
            if (types.length < 2 || types.length > 3) continue;
            Object[] args = new Object[types.length];
            boolean valid = true;
            for (int i = 0; i < types.length; i++) {
                if (i == 0) args[i] = modelAndTexture(types[i], model, texture);
                else if (types[i].isRecord() && empty(types[i]) != null) args[i] = empty(types[i]);
                else if (types[i].isRecord()) args[i] = resourceTexture(types[i],
                        selected != null && selected.assetId(true) != null ? selected.assetId(true) : texture + "_baby");
                else valid = false;
                if (args[i] == null) valid = false;
            }
            if (valid) return variantConstructor.newInstance(args);
        }
        return null;
    }

    private static Object empty(Class<?> type) throws IllegalAccessException {
        for (Field field : type.getFields()) {
            if (Modifier.isStatic(field.getModifiers()) && field.getType() == type)
                return field.get(null);
        }
        return null;
    }

    private static Object modelAndTexture(Class<?> type, Object model, String texture)
            throws ReflectiveOperationException {
        for (Constructor<?> constructor : type.getConstructors()) {
            Class<?>[] parameters = constructor.getParameterTypes();
            if (parameters.length == 2 && parameters[1] == Identifier.class)
                return constructor.newInstance(model, id(texture));
        }
        return null;
    }

    private static Object resourceTexture(Class<?> type, String texture)
            throws ReflectiveOperationException {
        for (Constructor<?> constructor : type.getConstructors()) {
            Class<?>[] parameters = constructor.getParameterTypes();
            if (parameters.length == 1 && parameters[0] == Identifier.class)
                return constructor.newInstance(id(texture));
        }
        return null;
    }

    private static Identifier id(String path) {
        //? if >=26.1 {
        /*return Identifier.fromNamespaceAndPath("minecraft", path);
        *///? } else {
        return Identifier.of("minecraft", path);
        //? }
    }

    private static VillagerData villagerData() {
        //? if >=26.1 {
        /*return new VillagerData(
                BuiltInRegistries.VILLAGER_TYPE.get(BuiltInRegistries.VILLAGER_TYPE.getDefaultKey()).orElseThrow(),
                BuiltInRegistries.VILLAGER_PROFESSION.get(BuiltInRegistries.VILLAGER_PROFESSION.getDefaultKey()).orElseThrow(),
                1);
        *///? } else {
        //? if <=1.21.1 {
        return new VillagerData(
                Registries.VILLAGER_TYPE.get(Registries.VILLAGER_TYPE.getDefaultId()),
                Registries.VILLAGER_PROFESSION.get(Registries.VILLAGER_PROFESSION.getDefaultId()),
                1);
        //? } else {
        /*return new VillagerData(
                Registries.VILLAGER_TYPE.getEntry(Registries.VILLAGER_TYPE.getDefaultId()).orElseThrow(),
                Registries.VILLAGER_PROFESSION.getEntry(Registries.VILLAGER_PROFESSION.getDefaultId()).orElseThrow(),
                1);
        *///? }
        //? }
    }
}
//? }
