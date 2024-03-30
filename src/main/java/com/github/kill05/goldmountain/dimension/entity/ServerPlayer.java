package com.github.kill05.goldmountain.dimension.entity;

import com.github.kill05.goldmountain.protocol.PlayerConnection;
import com.github.kill05.goldmountain.protocol.PlayerController;
import io.netty.channel.Channel;

public class ServerPlayer extends HumanEntity {

    private final short id;
    private final PlayerConnection connection;
    private ShadowClone shadowClone;

    private int totalLevel;

    public ServerPlayer(PlayerController controller, short id, Channel channel) {
        super(controller.getServer());
        this.connection = new PlayerConnection(this, channel);
        this.id = id;
    }


    public void tick() {
        connection.tick();
    }


    public int getTotalLevel() {
        return totalLevel;
    }

    public void updateTotalLevel(int totalLevel) {
        this.totalLevel = totalLevel;
    }


    @Override
    public short getId() {
        return id;
    }

    @Override
    public PlayerConnection getConnection() {
        return connection;
    }
}
