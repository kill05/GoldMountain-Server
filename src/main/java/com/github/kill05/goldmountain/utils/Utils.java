package com.github.kill05.goldmountain.utils;

public class Utils {

    public static String insertString(String str, String insert, int position) {
        return str.substring(0, position) + insert + str.substring(position);
    }

}
