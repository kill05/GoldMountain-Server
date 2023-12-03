package com.github.kill05.goldmountain;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

public class Main {

    public static void main(String[] args) {
        Configurator.setLevel("io.netty", Level.INFO);
        new GMServer();
    }

}
