package com.github.kill05.goldmountain.dimension;

import com.github.kill05.goldmountain.protocol.enums.Identifiable;
import org.joml.Vector2f;

public enum DimensionType implements Identifiable<DimensionType> {
    UNKNOWN                 (0x00, "Unknown",                     null),
    SPAWN                   (0x02, "Spawn",                       null),

    MINE_FREE               (0x04, "Free Mine",                   new Vector2f(2.5f,  5.5f)),
    MINE_GOLD               (0x05, "Gold Mine",                   new Vector2f(10.5f, 5.5f)),
    MINE_DIAMOND            (0x06, "Diamond Mine",                new Vector2f(14.5f, 5.5f)),
    MINE_FANTASY            (0x07, "Fantasy mine",                new Vector2f(18.5f, 5.5f)),
    MINE_DESERT             (0x0d, "Desert Mine",                 new Vector2f(22.5f, 5.5f)),
    MINE_FOREST             (0x08, "Forest Mine",                 new Vector2f(26.5f, 5.5f)),
    MINE_ICE                (0x0e, "Ice Mine",                    new Vector2f(30.5f, 5.5f)),
    MINE_SEA                (0x09, "Sea Mine",                    new Vector2f(34.5f, 5.5f)),
    MINE_MAGMA              (0x0a, "Magma Mine",                  new Vector2f(30.5f, 10.5f)),
    MINE_SPACE              (0x0b, "Space Mine",                  new Vector2f(34.5f, 10.5f)),
    MINE_RUIN               (0x0c, "Ruin Mine",                   new Vector2f(26.5f, 15.5f)),
    MINE_TIME               (0x0f, "Time Mine",                   new Vector2f(30.5f, 15.5f)),
    MINE_HEAVEN             (0x10, "Mine of Heaven",              new Vector2f(34.5f, 15.5f)),
    MINE_ABYSS              (0x1f, "Mine of Abyss",               new Vector2f(38.5f, 15.5f)),
    MINE_INFINITY           (0x1d, "Mine of Infinity",            new Vector2f(2.5f,  15.5f)),
    MINE_DIMENSION          (0x20, "Mine of Dimension",           new Vector2f(6.5f,  19.5f)),
    MINE_DAILY              (0x23, "Daily Mine",                  new Vector2f(6.5f,  15.5f)),

    TRIAL_SPEED             (0x14, "Trial of Speed",              new Vector2f(6.5f,  10.5f)),
    TRIAL_SPEED_HARD        (0x15, "Trial of Speed (Hard)",       new Vector2f(18.5f, 10.5f)),
    TRIAL_POWER             (0x16, "Trial of Power",              new Vector2f(10.5f, 10.5f)),
    TRIAL_POWER_HARD        (0x17, "Trial of Power (Hard)",       new Vector2f(14.5f, 10.5f)),
    TRIAL_POWER_II          (0x18, "Trial of Power II",           new Vector2f(14.5f, 15.5f)),
    TRIAL_POWER_II_HARD     (0x19, "Trial of Power II (Hard)",    new Vector2f(18.5f, 15.5f)),
    TRIAL_SKILL             (0x1a, "Trial of Skill",              new Vector2f(10.5f, 15.5f)),

    HIDDEN_ROOM             (0x13, "Hidden Room",                 null),
    MINE_HEAVEN_DEEP        (0x1c, "Deep " + MINE_HEAVEN.name,    null),
    MINE_ABYSS_DEEP         (0x21, "Deep " + MINE_ABYSS.name,     null),
    MINE_DIMENSION_DEEP     (0x22, "Deep " + MINE_DIMENSION.name, null),

    TEMPLE_OF_REINCARNATION (0x03, "Temple of Reincarnation",     new Vector2f(2.5f,  10.5f)),
    TEMPLE_OF_ENGRAVING     (0x1e, "Temple of Engraving",         new Vector2f(2.5f,  19.5f)),

    UNKNOWN_0x23            (0x23, "Unknown (0x23)",              null),
    UNKNOWN_0x24            (0x24, "Unknown (0x24)",              null),
    UNKNOWN_0x25            (0x25, "Unknown (0x25)",              null);


    private final int id;
    private final String name;
    private final Vector2f loadingZone;

    DimensionType(int id, String name, Vector2f vector2f) {
        this.id = id;
        this.name = name;
        this.loadingZone = vector2f;
    }


    public Vector2f getLoadingZonePos() {
        return loadingZone != null ? new Vector2f(loadingZone) : null;
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
