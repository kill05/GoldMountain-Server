package com.github.kill05.goldmountain.protocol;

import com.github.kill05.goldmountain.dimension.entity.ServerPlayer;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutPlayerUpdate;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutShadowCloneUpdate;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerConnection {

    private final ServerPlayer player;
    private final Channel channel;
    private final List<Packet> packetQueue;

    private int ticksSinceUpdate;
    private int ticksSinceCloneUpdate;

    public PlayerConnection(ServerPlayer player, Channel channel) {
        this.player = player;
        this.channel = channel;
        this.packetQueue = Collections.synchronizedList(new ArrayList<>());
    }

    public void sendPacket(Packet packet) {
        synchronized (packetQueue) {
            packetQueue.add(packet);
        }
    }

    public void processPacketQueue() {

    }


    public void tick() {
        ticksSinceUpdate++;
        ticksSinceCloneUpdate++;
    }


    public void handlePlayerUpdate(PacketInOutPlayerUpdate packet) {
        ticksSinceUpdate = 0;
    }

    public void handleCloneUpdate(PacketInOutShadowCloneUpdate packet) {
        ticksSinceCloneUpdate = 0;
    }


    public void updateDimension(boolean syncFloor) {

    }


    public ServerPlayer getPlayer() {
        return player;
    }

    public Channel getChannel() {
        return channel;
    }
}
