package com.github.kill05.goldmountain.proxy;

import com.github.kill05.goldmountain.proxy.connection.ProxyConnection;
import com.github.kill05.goldmountain.proxy.gui.ProxyFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Proxy server for gold mountain.<p>
 * Used to read client -> server and server -> client packets through a GUI.<p>
 * Used to reverse engineer GM's protocol.
 */
public class GMProxy {

    private static final Logger log = LoggerFactory.getLogger(GMProxy.class);
    private final ProxyFrame frame;
    private final ProxyConnection connection;

    public GMProxy() {
        long time = System.currentTimeMillis();

        this.connection = new ProxyConnection(this);
        this.frame = new ProxyFrame();

        frame.setVisible(true);

        log.info("Proxy started. ({}ms)", System.currentTimeMillis() - time);
    }

    public ProxyConnection getConnection() {
        return connection;
    }

    public ProxyFrame getFrame() {
        return frame;
    }
}
