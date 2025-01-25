package com.github.kill05.goldmountain.server.dimension.tile;

import com.github.kill05.goldmountain.proxy.Identifiable;

@SuppressWarnings("unused")
public enum TileType implements Identifiable<TileType> {
    UNKNOWN(0xff, 0x00, "Unknown"),

    // Generic
    SEAL_STONE(0x9b, 0x00, "Seal Stone"),
    DAILY_MINE_DISPLAY(0x9c, 0x00, "Wall"),

    // Stones
    STONE(0x5c, 0x03, "Stone"),
    STONE_ROUND(0xbd, 0x03, "Stone"),
    TREE(0x5d, 0x01, "Tree"),
    RUIN_STONE(0x5e, 0x03, "Ruin Stone"),
    SNOWBALL(0x94, 0x00, "Snowball"),

    // Free and gold mine
    BRONZE(0x5f, 0x00, "Bronze"),
    IRON(0x60, 0x00, "Iron"),
    SILVER(0x61, 0x00, "Silver"),
    GOLD(0x62, 0x00, "Gold"),
    PLATINUM(0x76, 0x00, "Platinum"),
    PYRITE(0x0b, 0x00, "Pyrite"),
    LAPIS_LAZULI(0x0c, 0x00, "Lapis Lazuli"),

    // Gems
    EMERALD(0x63, 0x00, "Emerald"),
    SAPPHIRE(0x64, 0x00, "Sapphire"),
    RUBY(0x67, 0x00, "Ruby"),
    DIAMOND(0x68, 0x00, "Diamond"),
    PINK_DIAMOND(0x69, 0x00, "Pink Diamond"),

    // Fantasy mine
    MYTHRIL(0x6b, 0x00, "Mythril"),
    ORICHALCUM(0x6c, 0x00, "Orichalcum"),
    ADAMANTIUM(0x6d, 0x00, "Adamantium"),
    BLOODSTONE(0x6e, 0x00, "Bloodstone"),
    DARK_MATTER(0x70, 0x00, "Dark Matter"),

    // Desert mine
    FINE_STONE(0x87, 0x00, "Fine Stone"),
    MARBLE(0x88, 0x00, "Marble"),
    TRACES_OF_ARTIFACT(0x89, 0x00, "Traces of Artifact"),
    EMERALD_SCARAB(0x8a, 0x00, "Emerald Scarab"),
    SARCOPHAGUS(0x8a, 0x00, "Sarcophagus"),

    // Forest mine
    GOLDEN_MUSHROOM(0x71, 0x00, "Golden Mushroom"),
    GOLDEN_APPLE_TREE(0x72, 0x00, "Golden Apple Tree"),
    AMBER(0x74, 0x00, "Amber"),
    SPIRIT_STONE(0x75, 0x00, "Spirit Stone"),

    // Ice mine
    BLUE_ICE(0x8c, 0x00, "Blue Ice"),
    CRYSTAL(0x8d, 0x00, "Crystal"),
    YETI_FOOTPRINT(0x8e, 0x00, "Yeti's Footprint"),
    CORE_OF_WINTER(0x8f, 0x00, "Core of Winter"),

    // Sea mine
    CORAL_REEF(0x77, 0x00, "Coral Reef"),
    AQUAMARINE(0x78, 0x00, "Aquamarine"),
    PEARL_SHELL(0x79, 0x00, "Pearl Shell"),
    BLACK_PEARL_SHELL(0x7a, 0x00, "Black Pearl Shell"),

    // Magma mine
    SULFUR(0x7b, 0x00, "Sulfur"),
    OBSIDIAN(0x7c, 0x00, "Obsidian"),
    FLAME_CRYSTAL(0x7d, 0x00, "Flame Crystal"),
    RED_SUN_STONE(0x7e, 0x00, "Red Sun Stone"),

    // Space mine
    STARDUST(0x7f, 0x00, "Stardust"),
    LITTLE_PLANET(0x80, 0x00, "Little Planet"),
    LITTLE_SUN(0x81, 0x00, "Little Sun"),
    RAINBOW_STAR(0x82, 0x00, "Rainbow Star"),

    // Ruin mine
    RUIN_BLOCK(0x83, 0x00, "Ruin Block"),
    RUIN_WELL(0x84, 0x00, "Ruin Well"),
    RUIN_PILLAR(0x85, 0x00, "Ruin Pillar"),
    RELIC_CHEST(0x86, 0x00, "Relic Chest"),

    // Time mine
    INDICATOR_OF_TIME(0x90, 0x00, "Indicator of Time"),
    AKASHIC_RECORDS(0x91, 0x00, "Akashic Records"),
    BREAK_OF_DIMENSION(0x92, 0x00, "Break of Dimension"),
    TIME_MACHINE(0x93, 0x00, "Time Machine"),

    // Heaven mine
    CLOUD(0x1a, 0x00, "Cloud"),
    NIMBUS(0x0f, 0x00, "Nimbus"),
    TORNADO(0x03, 0x00, "Tornado"),
    ANCIENT_EGG(0x57, 0x00, "Ancient Egg"),

    // Abyss mine
    ABYSS_STONE(0x07, 0x00, "Abyss Stone"),
    DEATH_FLOWER(0x02, 0x00, "Death Flower"),
    HELLFIRE(0x26, 0x00, "Hellfire"),

    // Infinity mine
    INFINITIUM_HQ(0x2b, 0x00, "Infinitium"),
    INFINITIUM_LQ(0x2e, 0x00, "Infinitium"),

    // Dimension mine
    DIMENSIONLITE(0xad, 0x00, "Dimensionlite"),
    EXTRATERRESTRIAL_MATERIAL(0xc7, 0x00, "Extraterrestrial Material"),
    VISION_STONE(0x33, 0x00, "Vision Stone"),

    // Engrave stones
    STONE_SPEED(0x18, 0x00, "Speed"),
    STONE_STRENGTH(0x00, 0x00, "Strength"),
    STONE_SKILL(0x1d, 0x00, "Skill"),

    // Boss related
    LIFE_STONE(0x95, 0x00, "Life Stone"),
    SOUL_STONE(0x96, 0x00, "Soul Stone"),
    MUCUS_CHUNK(0x97, 0x00, "Mucus Chunk"),
    BOSS_TREE(0x99, 0x00, "Tree"),
    BOSS_TREE_2(0x9a, 0x00, "Tree");


    private final int id;
    private final String name;
    private final int health;

    TileType(int id, int health, String name) {
        this.id = id;
        this.health = health;
        this.name = name;
    }

    public short actionCode() {
        return (short) getId();
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public TileType getUnknown() {
        return UNKNOWN;
    }
}
