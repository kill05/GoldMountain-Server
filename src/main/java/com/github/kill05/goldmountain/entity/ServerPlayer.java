package com.github.kill05.goldmountain.entity;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.protocol.PlayerConnection;
import io.netty.channel.Channel;

public class ServerPlayer extends HumanEntity {

    private final short id;
    private final PlayerConnection connection;
    private ShadowClone shadowClone;

    private DimensionType dimension;
    private int totalLevel;

    public ServerPlayer(short id, Channel channel) {
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

    @Override
    public DimensionType getDimension() {
        return dimension;
    }

    @Override
    protected void setDimension(DimensionType dimension) {
        this.dimension = dimension;
    }


    public int getTotalLevel() {
        return totalLevel;
    }
}
