package com.github.kill05.goldmountain.proxy;

import com.github.kill05.goldmountain.proxy.gui.ProxyFrame;

/**
 * Proxy server for gold mountain.<p>
 * Used to read client -> server and server -> client packets through a GUI.<p>
 * Used to reverse engineer GM's protocol.
 */
public class GMProxy {

    private final ProxyFrame frame;

    public GMProxy() {
        this.frame = new ProxyFrame();

        frame.setVisible(true);
    }

    public ProxyFrame getFrame() {
        return frame;
    }
}
