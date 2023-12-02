package com.github.kill05.goldmountain.protocol.enums;

public interface Identifiable<E extends Enum<E>> {

    int getId();

    String getName();

    E getUnknown();

    default boolean isValid() {
        return this != getUnknown();
    }

}
