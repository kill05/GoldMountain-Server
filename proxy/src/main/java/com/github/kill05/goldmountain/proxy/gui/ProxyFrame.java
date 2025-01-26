package com.github.kill05.goldmountain.proxy.gui;

import com.github.kill05.goldmountain.proxy.gui.packet.PacketPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ProxyFrame extends JFrame {

    private final PacketPanel clientToProxyPanel;
    private final PacketPanel serverToProxyPanel;

    public ProxyFrame() throws HeadlessException {
        setTitle("GM Proxy");
        setSize(800, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.clientToProxyPanel = new PacketPanel("Client -> Proxy");
        this.serverToProxyPanel = new PacketPanel("Server -> Proxy");

        JSplitPane packetSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, clientToProxyPanel, serverToProxyPanel);
        packetSplitPanel.setDividerSize(5);
        add(packetSplitPanel);

        // Listeners
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                packetSplitPanel.setDividerLocation(getSize().width / 2);
            }
        });
    }

    public PacketPanel getClientToProxyPanel() {
        return clientToProxyPanel;
    }

    public PacketPanel getServerToProxyPanel() {
        return serverToProxyPanel;
    }
}
