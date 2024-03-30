package com.github.kill05.goldmountain.dimension.entity;

import com.github.kill05.goldmountain.protocol.PlayerConnection;
import com.github.kill05.goldmountain.protocol.PlayerController;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutHumanEntityUpdate;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutPlayerUpdate;
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
        super.tick();
    }

    @Override
    public void update(PacketInOutHumanEntityUpdate packet) {
        if(!(packet instanceof PacketInOutPlayerUpdate updatePacket)) {
            throw new IllegalArgumentException("Packet must be a player update packet!");
        }

        this.totalLevel = updatePacket.getTotalLevel();
        this.costume = updatePacket.getCostume();
        this.speed = updatePacket.getSpeed();
        this.checkpoints = updatePacket.getCheckpoints();
        this.targetTile = updatePacket.getTargetTile();
    }


    public int getTotalLevel() {
        return totalLevel;
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
