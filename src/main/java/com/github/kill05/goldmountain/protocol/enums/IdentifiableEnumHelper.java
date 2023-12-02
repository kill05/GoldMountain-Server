package com.github.kill05.goldmountain.protocol.enums;

public final class IdentifiableEnumHelper {

    private IdentifiableEnumHelper() {}

    public static <E extends Enum<E> & Identifiable<E>> E fromId(Class<E> enumClass, int id) {
        E[] enums = enumClass.getEnumConstants();
        if(enums.length == 0) throw new IllegalStateException(String.format("Found no constants for Identifiable enum %s.", enumClass));

        for(E anEnum : enums) {
            if(anEnum.getId() == id) return anEnum;
        }

        return enums[0].getUnknown();
    }

}
