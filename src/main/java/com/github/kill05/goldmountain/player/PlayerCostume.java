package com.github.kill05.goldmountain.player;

import com.github.kill05.goldmountain.protocol.enums.Identifiable;

public enum PlayerCostume implements Identifiable<PlayerCostume> {
    UNKNOWN   (0xffff, "Unknown"),
    DEFAULT   (0x0000, "Default"),
    DRILL     (0x9700, "Drill"),
    BIG_HAND  (0x9800, "Big Hand"),
    GOLDEN    (0x9900, "Golden"),
    HAMMER    (0x9a00, "Hammer"),
    SNIPER    (0x9b00, "Sniper"),
    CHRISTMAS (0x9c00, "Christmas"),
    SUMMER    (0x9d00, "Summer"),
    SHIELD    (0x9e00, "Shield"),
    NINJA     (0x9f00, "Ninja"),
    PRISONER  (0xa000, "Prisoner"),
    VALENTINE (0xa100, "Valentine"),
    GHOST     (0xa200, "Ghost"),
    SCIENTIST (0xa300, "Scientist");

    private final int id;
    private final String name;

    PlayerCostume(int id, String name) {
        this.id = id;
        this.name = name;
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
    public PlayerCostume getUnknown() {
        return UNKNOWN;
    }


    @Override
    public String toString() {
        return name;
    }

}
