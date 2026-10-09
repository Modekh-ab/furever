package net.mondless.furever.config;

public enum MobChoice {
    SPRITE_FOX,
    VANILLA_FOX,
    BEE,
    CREEPER,
    SLIME,
    RABBIT,
    BAT,
    COD,
    ZOMBIE,
    HUSK,
    DROWNED,
    PIGLIN,
    ZOMBIFIED_PIGLIN,
    PANDA,
    SHEEP,
    GOAT,
    WOLF,
    CAT,
    OCELOT,
    TURTLE,
    POLAR_BEAR,
    HOGLIN,
    ZOGLIN;

    public boolean supportsBaby() {
        return switch (this) {
            case VANILLA_FOX, RABBIT, ZOMBIE, HUSK, DROWNED, PIGLIN, ZOMBIFIED_PIGLIN,
                    PANDA, SHEEP, GOAT, WOLF, CAT, OCELOT, TURTLE, POLAR_BEAR, HOGLIN, ZOGLIN -> true;
            default -> false;
        };
    }
}
