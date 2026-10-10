package net.mondless.furever.config;

//? if >=26.1 {
/*import net.minecraft.world.entity.EntityType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.network.chat.Component;
*///? } else {
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;
//? }
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public enum MobChoice {
    SPRITE_FOX,
    FOX,
    ALLAY, ARMADILLO, AXOLOTL,
    BAT, BEE, BLAZE, BOGGED, BREEZE,
    CAMEL, CAMEL_HUSK, CAT, CAVE_SPIDER, CHICKEN, COD, COPPER_GOLEM, COW, CREAKING, CREEPER,
    DOLPHIN, DONKEY, DROWNED,
    ELDER_GUARDIAN, ENDER_DRAGON, ENDERMAN, ENDERMITE, EVOKER,
    FROG,
    GHAST, GLOW_SQUID, GOAT, GUARDIAN,
    HAPPY_GHAST, HOGLIN, HORSE, HUSK,
    IRON_GOLEM,
    LLAMA,
    MAGMA_CUBE, MOOSHROOM, MULE,
    NAUTILUS,
    OCELOT,
    PANDA, PARCHED, PARROT, PHANTOM, PIG, PIGLIN, PIGLIN_BRUTE, PILLAGER, POLAR_BEAR, PUFFERFISH,
    RABBIT, RAVAGER,
    SALMON, SHEEP, SHULKER, SILVERFISH, SKELETON, SKELETON_HORSE, SLIME, SNIFFER, SNOW_GOLEM, SPIDER, SQUID, STRAY, STRIDER, SULFUR_CUBE,
    TADPOLE, TRADER_LLAMA, TROPICAL_FISH, TURTLE,
    VEX, VILLAGER, VINDICATOR,
    WANDERING_TRADER, WARDEN, WITCH, WITHER, WITHER_SKELETON, WOLF,
    ZOGLIN, ZOMBIE, ZOMBIE_HORSE, ZOMBIE_NAUTILUS, ZOMBIE_VILLAGER, ZOMBIFIED_PIGLIN;

    public enum Movement {
        RUN, FLY, SWIM, JUMP, CRAWL, FLOAT, IDLE
    }

    public String id() {
        return this == FOX || this == SPRITE_FOX ? "fox" : name().toLowerCase(Locale.ROOT);
    }

    public String displayNameKey() {
        return this == SPRITE_FOX ? "text.furever.mob.sprite_fox" : "entity.minecraft." + id();
    }

    @Override
    public String toString() {
        //? if >=26.1 {
        /*return Component.translatable(displayNameKey()).getString();
        *///? } else {
        return Text.translatable(displayNameKey()).getString();
        //? }
    }

    public boolean isAvailable() {
        if (this == SPRITE_FOX) return true;
        //? if >=26.1 {
        /*return BuiltInRegistries.ENTITY_TYPE.containsKey(Identifier.fromNamespaceAndPath("minecraft", id()));
         *///? } else {
        return EntityType.get(id()).isPresent();
        //? }
    }

    public static List<MobChoice> available() {
        return Arrays.stream(values()).filter(MobChoice::isAvailable).toList();
    }

    public Movement movement() {
        return switch (this) {
            case BEE, BAT, ALLAY, BLAZE, ENDER_DRAGON, GHAST, HAPPY_GHAST,
                 PARROT, PHANTOM, VEX, WITHER -> Movement.FLY;
            case COD, AXOLOTL, DOLPHIN, ELDER_GUARDIAN, GLOW_SQUID, GUARDIAN,
                 NAUTILUS, PUFFERFISH, SALMON, SQUID, TADPOLE, TROPICAL_FISH,
                 TURTLE, ZOMBIE_NAUTILUS -> Movement.SWIM;
            case SLIME, MAGMA_CUBE, SULFUR_CUBE, RABBIT, FROG, BREEZE -> Movement.JUMP;
            case CAVE_SPIDER, SPIDER, ENDERMITE, SILVERFISH -> Movement.CRAWL;
            case SHULKER -> Movement.IDLE;
            case STRIDER -> Movement.FLOAT;
            default -> Movement.RUN;
        };
    }

    public boolean supportsBaby() {
        return switch (this) {
            case FOX, BEE, RABBIT, ZOMBIE, HUSK, DROWNED, PIGLIN, ZOMBIFIED_PIGLIN,
                 PANDA, SHEEP, GOAT, WOLF, CAT, OCELOT, TURTLE, POLAR_BEAR, HOGLIN, ZOGLIN,
                 ARMADILLO, AXOLOTL, CAMEL, CAMEL_HUSK, CHICKEN, COW, DONKEY, HORSE,
                 LLAMA, MOOSHROOM, MULE, PIG, SKELETON_HORSE, SNIFFER, STRIDER,
                 TRADER_LLAMA, VILLAGER, ZOMBIE_HORSE, ZOMBIE_VILLAGER,
                 HAPPY_GHAST, NAUTILUS, ZOMBIE_NAUTILUS -> true;
            default -> false;
        };
    }
}
