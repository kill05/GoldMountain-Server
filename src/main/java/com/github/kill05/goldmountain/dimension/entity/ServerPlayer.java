package com.github.kill05.goldmountain.dimension.entity;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.protocol.PlayerConnection;
import io.netty.channel.Channel;

public class ServerPlayer extends HumanEntity {

    private final short id;
    private final PlayerConnection connection;
    private ShadowClone shadowClone;

    private int totalLevel;

    public ServerPlayer(GMServer server, short id, Channel channel) {
        super(server);
        this.connection = new PlayerConnection(this, channel);
        this.id = id;
    }


    public void tick() {
        connection.tick();
    }


    @Override
    public short getId() {
        return id;
    }

    @Override
    public PlayerConnection getConnection() {
        return connection;
    }



    public int getTotalLevel() {
        return totalLevel;
    }
}
