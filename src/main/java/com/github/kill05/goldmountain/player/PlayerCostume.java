package com.github.kill05.goldmountain.player;

import com.github.kill05.goldmountain.protocol.enums.Identifiable;

public enum PlayerCostume implements Identifiable<PlayerCostume> {
    UNKNOWN   (0xffff, "Unknown"),
    DEFAULT   (0x0000, "Default"),
    DRILL     (0x0097, "Drill"),
    BIG_HAND  (0x0098, "Big Hand"),
    GOLDEN    (0x0099, "Golden"),
    HAMMER    (0x009a, "Hammer"),
    SNIPER    (0x009b, "Sniper"),
    CHRISTMAS (0x009c, "Christmas"),
    SUMMER    (0x009d, "Summer"),
    SHIELD    (0x009e, "Shield"),
    NINJA     (0x009f, "Ninja"),
    PRISONER  (0x00a0, "Prisoner"),
    VALENTINE (0x00a1, "Valentine"),
    GHOST     (0x00a2, "Ghost"),
    SCIENTIST (0x00a3, "Scientist");

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
