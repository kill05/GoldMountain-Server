package com.github.kill05.goldmountain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Identifiable<E extends Enum<E>> {

    int getId();

    String getName();

    E getUnknown();

    default boolean isValid() {
        return this != getUnknown();
    }


    /**
     * Tries to get an identifiable enum constant from its id.
     * Throws an exception if there is no value associated with the id.
     *
     * @param enumClass the enum class
     * @param id the id
     * @return the constant with the given id
     * @param <E> the identifiable enum type
     * @throws IllegalArgumentException if there is no value associated with the id.
     */
    @NotNull
    static <E extends Enum<E> & Identifiable<E>> E fromId(@NotNull Class<E> enumClass, int id) throws IllegalArgumentException {
        E value = fromIdNullable(enumClass, id);

        if (value == null) {
            return enumClass.getEnumConstants()[0].getUnknown();
        }

        return value;
    }

    /**
     * Tries to get an identifiable enum constant from its id.
     * Returns {@link Identifiable#getUnknown()} if there is no value associated with the id.
     *
     * @param enumClass the enum class
     * @param id the id
     * @return the constant with the given id
     * @param <E> the identifiable enum type
     */
    @NotNull
    static <E extends Enum<E> & Identifiable<E>> E fromIdOrUnknown(@NotNull Class<E> enumClass, int id) {
        E value = fromIdNullable(enumClass, id);

        if (value == null) {
            return enumClass.getEnumConstants()[0].getUnknown();
        }

        return value;
    }

    /**
     * Tries to get an identifiable enum constant from its id, or null if there is no value associated with the id.
     *
     * @param enumClass the enum class
     * @param id the id
     * @return the constant with the given id
     * @param <E> the identifiable enum type
     */
    @Nullable
    private static <E extends Enum<E> & Identifiable<E>> E fromIdNullable(@NotNull Class<E> enumClass, int id) {
        E[] enums = enumClass.getEnumConstants();

        if (enums.length == 0) {
            throw new IllegalStateException(String.format("Found no constants for Identifiable enum %s.", enumClass));
        }

        for (E anEnum : enums) {
            if (anEnum.getId() == id) return anEnum;
        }

        return null;
    }

}
