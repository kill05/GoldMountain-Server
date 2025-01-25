package com.github.kill05.goldmountain.proxy.gui;

import javax.swing.*;
import java.awt.*;

public class ProxyFrame extends JFrame {

    public ProxyFrame() throws HeadlessException {
        setTitle("GM Proxy");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(new ProxyPanel());
    }
}
