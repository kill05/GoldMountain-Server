package com.github.kill05.goldmountain.proxy.gui.packet;

import com.github.kill05.goldmountain.connection.packet.Packet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class PacketPanel extends JPanel {

    private final JPacketList packetList;
    private final JTextArea packetInfoTextArea;
    private boolean enableLogging;

    public PacketPanel(String name) {
        setLayout(new BorderLayout());

        // Packet list
        this.packetList = new JPacketList(this);

        // Menu bar
        JMenuBar bar = new JMenuBar();
        bar.add(createNameTextField(name));
        bar.add(createLoggingButton());
        bar.add(createClearButton());

        add(bar, BorderLayout.NORTH);

        // Packet info panel
        this.packetInfoTextArea = new JTextArea();
        packetInfoTextArea.setEditable(false);
        packetInfoTextArea.setFocusable(false);

        // Split pane that holds list and packet debug
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(packetList),
                new JScrollPane(packetInfoTextArea)
        );

        splitPane.setDividerSize(3);
        splitPane.setDividerLocation(splitPane.getHeight());
        add(splitPane, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int paneHeight = splitPane.getHeight();
                splitPane.setDividerLocation(paneHeight * 6 / 10);
            }
        });
    }


    private JTextField createNameTextField(String name) {
        JTextField text = new JTextField(name);
        text.setEditable(false);
        text.setFocusable(false);
        text.setBorder(null);
        text.setFont(new Font("Arial", Font.BOLD, 16));
        return text;
    }

    private JButton createClearButton() {
        JButton clearButton = new JButton("Clear");
        clearButton.setFocusPainted(false);

        clearButton.addActionListener(event -> {
            packetList.clear();
        });
        return clearButton;
    }

    private JToggleButton createLoggingButton() {
        JToggleButton loggingButton = new JToggleButton("Enable logging");
        loggingButton.setFocusPainted(false);

        loggingButton.addActionListener(event -> {
            enableLogging = loggingButton.isSelected();
        });

        return loggingButton;
    }


    public void onPacketSent(Packet packet) {
        if (!enableLogging) return;
        packetList.addPacket(packet);
    }

    public JTextArea getPacketInfoTextArea() {
        return packetInfoTextArea;
    }

}
