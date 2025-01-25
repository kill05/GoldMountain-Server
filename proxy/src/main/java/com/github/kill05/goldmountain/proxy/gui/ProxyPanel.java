package com.github.kill05.goldmountain.proxy.gui;

import javax.swing.*;
import java.awt.*;

public class ProxyPanel extends JPanel {

    public ProxyPanel() {
        setLayout(new GridLayout(1, 2));

        add(new PacketPanel());
        add(new PacketPanel());
    }
}
