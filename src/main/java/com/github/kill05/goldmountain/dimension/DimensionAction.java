package com.github.kill05.goldmountain.dimension;

import com.github.kill05.goldmountain.protocol.enums.Identifiable;

public enum DimensionAction implements Identifiable<DimensionAction> {
    LOGIN(0x07, "Login"),
    CHANGE_DIMENSION(0x04, "Change Dimension");

    private final int id;
    private final String name;

    DimensionAction(int id, String name) {
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
    public DimensionAction getUnknown() {
        return null;
    }
}
