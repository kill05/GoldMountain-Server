package com.github.kill05.goldmountain.dimension;

import com.github.kill05.goldmountain.dimension.entity.Entity;
import com.github.kill05.goldmountain.protocol.enums.Identifiable;
import com.github.kill05.goldmountain.utils.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;

public enum DimensionType implements Identifiable<DimensionType> {
    UNKNOWN(0x00, 1, "Unknown", null),
    SPAWN(0x02, 1, "Spawn", null),

    MINE_FREE(0x04, 5, "Free Mine", new Vector2f(2.5f, 5.5f)),
    MINE_GOLD(0x05, 10, "Gold Mine", new Vector2f(10.5f, 5.5f)),
    MINE_DIAMOND(0x06, 10, "Diamond Mine", new Vector2f(14.5f, 5.5f)),
    MINE_FANTASY(0x07, 10, "Fantasy mine", new Vector2f(18.5f, 5.5f)),
    MINE_DESERT(0x0d, 10, "Desert Mine", new Vector2f(22.5f, 5.5f)),
    MINE_FOREST(0x08, 10, "Forest Mine", new Vector2f(26.5f, 5.5f)),
    MINE_ICE(0x0e, 10, "Ice Mine", new Vector2f(30.5f, 5.5f)),
    MINE_SEA(0x09, 10, "Sea Mine", new Vector2f(34.5f, 5.5f)),
    MINE_MAGMA(0x0a, 10, "Magma Mine", new Vector2f(30.5f, 10.5f)),
    MINE_SPACE(0x0b, 10, "Space Mine", new Vector2f(34.5f, 10.5f)),
    MINE_RUIN(0x0c, 10, "Ruin Mine", new Vector2f(26.5f, 15.5f)),
    MINE_TIME(0x0f, 10, "Time Mine", new Vector2f(30.5f, 15.5f)),
    MINE_HEAVEN(0x10, 10, "Mine of Heaven", new Vector2f(34.5f, 15.5f)),
    MINE_ABYSS(0x1f, 5, "Mine of Abyss", new Vector2f(38.5f, 15.5f)),
    MINE_INFINITY(0x1d, 10, "Mine of Infinity", new Vector2f(2.5f, 15.5f)),
    MINE_DIMENSION(0x20, 10, "Mine of Dimension", new Vector2f(6.5f, 19.5f)),
    MINE_ELECTRONIC(0x28, 10, "Electronic Mine", new Vector2f()), //todo: spawn pos
    MINE_DAILY(0x23, 3, "Daily Mine", new Vector2f(6.5f, 15.5f)),

    TRIAL_SPEED(0x14, 1, "Trial of Speed", new Vector2f(6.5f, 10.5f)),
    TRIAL_SPEED_HARD(0x15, -1, "Trial of Speed (Hard)", new Vector2f(18.5f, 10.5f)),
    TRIAL_POWER(0x16, 10, "Trial of Power", new Vector2f(10.5f, 10.5f)),
    TRIAL_POWER_HARD(0x17, 10, "Trial of Power (Hard)", new Vector2f(14.5f, 10.5f)),
    TRIAL_POWER_II(0x18, 10, "Trial of Power II", new Vector2f(14.5f, 15.5f)),
    TRIAL_POWER_II_HARD(0x19, 10, "Trial of Power II (Hard)", new Vector2f(18.5f, 15.5f)),
    TRIAL_SKILL(0x1a, 10, "Trial of Skill", new Vector2f(10.5f, 15.5f)),

    HIDDEN_ROOM(0x13, 1, "Hidden Room", null),

    MINE_HEAVEN_DEEP(0x1c, 1, MINE_HEAVEN, "Deep " + MINE_HEAVEN.name, null),
    MINE_ABYSS_DEEP(0x21, 1, MINE_ABYSS, "Deep " + MINE_ABYSS.name, null),
    MINE_DIMENSION_DEEP(0x22, 1, MINE_ABYSS, "Deep " + MINE_DIMENSION.name, null),

    ENIGMA_ROOM(0x29, 1, "Enigma Room", null), // F10 of the electronic mine
    SOLVED_ENIGMA_ROOM(0x2a, 1, "Solved Enigma Room", null), // Accessed after solving the enigma in the enigma room

    TEMPLE_OF_REINCARNATION(0x03, 1, "Temple of Reincarnation", new Vector2f(2.5f, 10.5f)),
    TEMPLE_OF_ENGRAVING(0x1e, 1, "Temple of Engraving", new Vector2f(2.5f, 19.5f)),

    UNKNOWN_0x23(0x23, 1, "Unknown (0x23)", null),
    UNKNOWN_0x24(0x24, 1, "Unknown (0x24)", null),
    UNKNOWN_0x25(0x25, 1, "Unknown (0x25)", null), // Beach with mountain
    UNKNOWN_0x26(0x26, 1, "Unknown (0x26)", null), // Spawn but underground
    UNKNOWN_0x27(0x27, 1, "Unknown (0x27)", null); // Same as beach above, but you spawn in the sea and there is a 5-minute timer


    private final int id;
    private final int maxFloor;
    private final DimensionType lastFloor;
    private final String name;
    private final Vector2f loadingZone;

    DimensionType(int id, int maxFloor, DimensionType lastFloor, String name, Vector2f vector2f) {
        if (maxFloor < 1 && maxFloor != -1) {
            throw new IllegalArgumentException("Max floor must be either -1 (infinite) or higher than 0.");
        }

        this.id = id;
        this.maxFloor = maxFloor;
        this.lastFloor = lastFloor;
        this.name = name;
        this.loadingZone = vector2f;
    }

    DimensionType(int id, int maxFloor, String name, Vector2f loadingZone) {
        this(id, maxFloor, null, name, loadingZone);
    }


    public int getMaxFloor() {
        return maxFloor;
    }

    public boolean hasMultipleFloors() {
        return maxFloor > 0;
    }

    public boolean hasInfiniteFloors() {
        return maxFloor < 0;
    }

    public boolean isValidFloor(int floor) {
        return floor >= 0 && (hasInfiniteFloors() || floor < getMaxFloor());
    }


    @NotNull
    public DimensionType getLastFloor() {
        return lastFloor != null ? lastFloor : this;
    }

    @Nullable
    public Vector2f getLoadingZone() {
        return loadingZone != null ? new Vector2f(loadingZone) : null;
    }

    public boolean hasLoadingZone() {
        return loadingZone != null;
    }

    public boolean isOnLoadingZone(Entity entity) {
        if (!hasLoadingZone()) return false;
        return Utils.isSameTile(entity.getLocation(), loadingZone);
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
