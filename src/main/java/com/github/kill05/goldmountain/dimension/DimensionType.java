package com.github.kill05.goldmountain.dimension;

import com.github.kill05.goldmountain.protocol.enums.Identifiable;
import org.joml.Vector2f;

public enum DimensionType implements Identifiable<DimensionType> {
    UNKNOWN              (0x00, "Unknown",                  0.0f,  0.0f),
    SPAWN                (0x02, "Spawn",                    2.5f,  7.5f),

    MINE_FREE            (0x04, "Free Mine",                2.5f,  5.5f),
    MINE_GOLD            (0x05, "Gold Mine",                10.5f, 5.5f),
    MINE_DIAMOND         (0x06, "Diamond Mine",             14.5f, 5.5f),
    MINE_FANTASY         (0x07, "Fantasy mine",             18.5f, 5.5f),
    MINE_DESERT          (0x0d, "Desert Mine",              22.5f, 5.5f),
    MINE_FOREST          (0x08, "Forest Mine",              26.5f, 5.5f),
    MINE_ICE             (0x0e, "Ice Mine",                 30.5f, 5.5f),
    MINE_SEA             (0x09, "Sea Mine",                 34.5f, 5.5f),
    MINE_MAGMA           (0x0a, "Magma Mine",               30.5f, 10.5f),
    MINE_SPACE           (0x0b, "Space Mine",               34.5f, 10.5f),
    MINE_RUIN            (0x0c, "Ruin Mine",                26.5f, 15.5f),
    MINE_TIME            (0x0f, "Time Mine",                30.5f, 15.5f),
    MINE_HEAVEN          (0x10, "Mine of Heaven",           34.5f, 15.5f),
    MINE_ABYSS           (0x1f, "Mine of Abyss",            38.5f, 15.5f),
    MINE_INFINITY        (0x1d, "Mine of Infinity",         2.5f,  15.5f),
    MINE_DAILY           (0x23, "Daily Mine",               6.5f,  15.5f),

    TRIAL_SPEED          (0x14, "Trial of Speed",           6.5f, 10.5f),
    TRIAL_SPEED_HARD     (0x15, "Trial of Speed (Hard)",    18.5f, 10.5f),
    TRIAL_POWER          (0x16, "Trial of Power",           10.5f, 10.5f),
    TRIAL_POWER_HARD     (0x17, "Trial of Power (Hard)",    14.5f, 10.5f),
    TRIAL_POWER_II       (0x18, "Trial of Power II",        14.5f, 15.5f),
    TRIAL_POWER_II_HARD  (0x19, "Trial of Power II (Hard)", 18.5f, 15.5f),
    TRIAL_SKILL          (0x1a, "Trial of Skill",           10.5f, 15.5f),


    TEMPLE_REINCARNATION (0x03, "Temple of Reincarnation", 2.5f,  10.5f);
    //TEMPLE_OF_ENGRAVING     (0x, "Temple of Engraving", 0f,  0f),
    private final int id;
    private final String name;
    private final Vector2f loadingZone;

    DimensionType(int id, String name, float x, float y) {
        this.id = id;
        this.name = name;
        this.loadingZone = new Vector2f(x, y);
    }


    public Vector2f getLoadingZonePos() {
        return new Vector2f(loadingZone);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DimensionType getUnknown() {
        return UNKNOWN;
    }
}
