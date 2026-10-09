package net.mondless.furever.config;

//? if >=26.1 {
/*import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
*///? } else {
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
//? }

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Texture choices backed by assets actually present in the running Minecraft version. */
public record MobTextureVariant(MobChoice mob, String id, String label, List<String> paths) {
    private static final Map<MobChoice, List<MobTextureVariant>> CHOICES = createChoices();
    private static final Map<MobChoice, List<MobTextureVariant>> AVAILABLE = new EnumMap<>(MobChoice.class);
    private static Object cachedResources;

    private static Map<MobChoice, List<MobTextureVariant>> createChoices() {
        Map<MobChoice, List<MobTextureVariant>> result = new EnumMap<>(MobChoice.class);
        add(result, MobChoice.CAT, "tabby,black,red,siamese,british_shorthair,calico,persian,ragdoll,white,jellie,all_black");
        add(result, MobChoice.WOLF, "pale,woods,ashen,black,chestnut,rusty,snowy,spotted,striped");
        add(result, MobChoice.FOX, "red,snow");
        add(result, MobChoice.RABBIT, "brown,white,black,white_splotched,gold,salt,evil");
        add(result, MobChoice.PARROT, "red_blue,blue,green,yellow_blue,grey");
        add(result, MobChoice.AXOLOTL, "wild,lucy,gold,cyan,blue");
        add(result, MobChoice.PANDA, "normal,lazy,worried,playful,brown,weak,aggressive");
        add(result, MobChoice.LLAMA, "creamy,white,brown,gray");
        add(result, MobChoice.TRADER_LLAMA, "creamy,white,brown,gray");
        add(result, MobChoice.HORSE, "white,creamy,chestnut,brown,black,gray,dark_brown");
        add(result, MobChoice.SHEEP, "white,orange,magenta,light_blue,yellow,lime,pink,gray,light_gray,cyan,purple,blue,brown,green,red,black");
        add(result, MobChoice.MOOSHROOM, "red,brown");
        add(result, MobChoice.FROG, "temperate,warm,cold");
        add(result, MobChoice.COW, "temperate,warm,cold");
        add(result, MobChoice.PIG, "temperate,warm,cold");
        add(result, MobChoice.CHICKEN, "temperate,warm,cold");
        add(result, MobChoice.SHULKER, "purple,white,orange,magenta,light_blue,yellow,lime,pink,gray,light_gray,cyan,blue,brown,green,red,black");
        add(result, MobChoice.ZOMBIE_NAUTILUS, "normal,coral");
        return result;
    }

    private static void add(Map<MobChoice, List<MobTextureVariant>> result, MobChoice mob, String ids) {
        List<MobTextureVariant> variants = new ArrayList<>();
        for (String id : ids.split(",")) variants.add(new MobTextureVariant(mob, id, title(id), paths(mob, id)));
        result.put(mob, List.copyOf(variants));
    }

    private static String title(String id) {
        StringBuilder text = new StringBuilder();
        for (String word : id.split("_")) {
            if (!text.isEmpty()) text.append(' ');
            text.append(word.substring(0, 1).toUpperCase(Locale.ROOT)).append(word.substring(1));
        }
        return text.toString();
    }

    private static List<String> paths(MobChoice mob, String id) {
        return switch (mob) {
            case CAT -> List.of("textures/entity/cat/cat_" + id + ".png", "textures/entity/cat/" + id + ".png");
            case WOLF -> id.equals("pale")
                    ? List.of("textures/entity/wolf/wolf.png")
                    : List.of("textures/entity/wolf/wolf_" + id + ".png");
            case FOX -> id.equals("red") ? List.of("textures/entity/fox/fox.png")
                    : List.of("textures/entity/fox/fox_snow.png", "textures/entity/fox/snow_fox.png");
            case RABBIT -> List.of("textures/entity/rabbit/rabbit_" + (id.equals("evil") ? "caerbannog" : id) + ".png",
                    "textures/entity/rabbit/" + (id.equals("evil") ? "caerbannog" : id) + ".png");
            case PARROT -> List.of("textures/entity/parrot/parrot_" + id + ".png");
            case AXOLOTL -> List.of("textures/entity/axolotl/axolotl_" + id + ".png");
            case PANDA -> id.equals("normal") ? List.of("textures/entity/panda/panda.png")
                    : List.of("textures/entity/panda/panda_" + id + ".png", "textures/entity/panda/" + id + "_panda.png");
            case LLAMA, TRADER_LLAMA -> List.of("textures/entity/llama/llama_" + id + ".png",
                    "textures/entity/llama/" + id + ".png");
            case HORSE -> List.of("textures/entity/horse/horse_" + id.replace("dark_brown", "darkbrown") + ".png");
            case SHEEP -> List.of("textures/entity/sheep/sheep_wool.png", "textures/entity/sheep/sheep_fur.png");
            case MOOSHROOM -> List.of("textures/entity/cow/mooshroom_" + id + ".png", "textures/entity/cow/" + id + "_mooshroom.png");
            case FROG -> List.of("textures/entity/frog/frog_" + id + ".png", "textures/entity/frog/" + id + "_frog.png");
            case COW -> List.of("textures/entity/cow/cow_" + id + ".png", "textures/entity/cow/" + id + "_cow.png");
            case PIG -> List.of("textures/entity/pig/pig_" + id + ".png", "textures/entity/pig/" + id + "_pig.png");
            case CHICKEN -> List.of("textures/entity/chicken/chicken_" + id + ".png", "textures/entity/chicken/" + id + "_chicken.png");
            case SHULKER -> List.of("textures/entity/shulker/shulker_" + id + ".png");
            case ZOMBIE_NAUTILUS -> List.of("textures/entity/nautilus/zombie_nautilus"
                    + (id.equals("coral") ? "_coral" : "") + ".png");
            default -> List.of();
        };
    }

    public static List<MobTextureVariant> available(MobChoice mob) {
        Object resources = resources();
        if (resources != cachedResources) {
            AVAILABLE.clear();
            cachedResources = resources;
        }
        return AVAILABLE.computeIfAbsent(mob, key -> CHOICES.getOrDefault(key, List.of()).stream()
                .filter(v -> v.texturePath(false) != null).toList());
    }

    public static MobTextureVariant selected(MobChoice mob, String id) {
        List<MobTextureVariant> available = available(mob);
        for (MobTextureVariant variant : available) if (variant.id.equals(id)) return variant;
        return available.isEmpty() ? null : available.get(0);
    }

    public static boolean belongsTo(MobChoice mob, String id) {
        return CHOICES.getOrDefault(mob, List.of()).stream().anyMatch(v -> v.id.equals(id));
    }

    public String texturePath(boolean baby) {
        for (String path : paths) {
            if (baby) {
                String babyPath = path.substring(0, path.length() - 4) + "_baby.png";
                if (exists(babyPath)) return babyPath;
            }
            if (exists(path)) return path;
        }
        return null;
    }

    public String assetId(boolean baby) {
        String path = texturePath(baby);
        return path == null ? null : path.substring("textures/".length(), path.length() - ".png".length());
    }

    private static boolean exists(String path) {
        //? if >=26.1 {
        /*return Minecraft.getInstance().getResourceManager()
                .getResource(Identifier.fromNamespaceAndPath("minecraft", path)).isPresent();
        *///? } else {
        return MinecraftClient.getInstance().getResourceManager()
                .getResource(Identifier.of("minecraft", path)).isPresent();
        //? }
    }

    private static Object resources() {
        //? if >=26.1 {
        /*return Minecraft.getInstance().getResourceManager();
        *///? } else {
        return MinecraftClient.getInstance().getResourceManager();
        //? }
    }
}
