package com.github.kill05.goldmountain.player;

import com.github.kill05.goldmountain.protocol.enums.Identifiable;

public enum PlayerAction implements Identifiable<PlayerAction> {
    UNKNOWN (0x00000000, "Unknown"),
    NOTHING (0xffffffff, "Nothing"),
    MINING  (0x29000000, "Mining");

    private final int id;
    private final String name;

    PlayerAction(int id, String name) {
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
    public PlayerAction getUnknown() {
        return UNKNOWN;
    }


    @Override
    public String toString() {
        return name;
    }

}
