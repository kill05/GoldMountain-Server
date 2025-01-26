package com.github.kill05.goldmountain.proxy.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ProxyFrame extends JFrame {

    private final JSplitPane packetSplitPanel;

    public ProxyFrame() throws HeadlessException {
        setTitle("GM Proxy");
        setSize(800, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.packetSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new PacketPanel(), new PacketPanel());
        this.packetSplitPanel.setDividerSize(5);
        updateSplitPanel();

        add(packetSplitPanel);

        // Listeners
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateSplitPanel();
            }
        });
    }

    private void updateSplitPanel() {
        this.packetSplitPanel.setDividerLocation(getSize().width / 2);
    }


}
