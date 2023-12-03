package com.github.kill05.goldmountain.utils;

import org.joml.Vector2f;

public class Utils {

    public static String insertString(String str, String insert, int position) {
        return str.substring(0, position) + insert + str.substring(position);
    }

    public static String vecToString(Vector2f vec) {
        return String.format("x: %s, y: %s", vec.x, vec.y);
    }

}
