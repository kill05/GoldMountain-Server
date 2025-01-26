package com.github.kill05.goldmountain.utils;

import org.joml.Vector2f;

public final class Utils {

    public static String vecToString(Vector2f vec) {
        return String.format("x: %s, y: %s", vec.x, vec.y);
    }

    public static boolean isSameTile(Vector2f vec1, Vector2f vec2) {
        return ((int) vec1.x) == ((int) vec2.x) &&
                ((int) vec1.y) == ((int) vec2.y);
    }

}
