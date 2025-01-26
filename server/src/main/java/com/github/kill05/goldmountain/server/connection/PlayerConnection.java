package com.github.kill05.goldmountain.server.connection;

import com.github.kill05.goldmountain.server.GMServer;
import com.github.kill05.goldmountain.server.dimension.ServerDimension;
import com.github.kill05.goldmountain.server.entity.player.ServerPlayerEntity;
import com.github.kill05.goldmountain.server.entity.player.ServerPlayer;
import com.github.kill05.goldmountain.connection.packet.Packet;
import com.github.kill05.goldmountain.connection.packet.packets.CloneUpdatePacket;
import com.github.kill05.goldmountain.connection.packet.packets.PlayerUpdatePacket;
import io.netty.channel.Channel;

public class PlayerConnection {

    public static final int PLAYER_TIMEOUT = GMServer.TARGET_TPS * 2;

    private final ServerPlayer player;
    private final Channel channel;

    private int ticksSinceUpdate;

    public PlayerConnection(ServerPlayer player, Channel channel) {
        this.player = player;
        this.channel = channel;
    }


    public void tick() {
        ServerDimension dimension = player.getDimension();
        if(dimension == null) return;

        dimension.forEachEntity(entity -> {
            if(!(entity instanceof ServerPlayerEntity other)) return;
            sendPacket(new PlayerUpdatePacket(other));
        });
    }


    public void sendPacket(Packet packet) {
        if(!isActive()) return;
        channel.writeAndFlush(packet);
    }


    public void handlePlayerUpdate(PlayerUpdatePacket packet) {
        player.update(packet);
        ticksSinceUpdate = 0;
    }

    public void handleCloneUpdate(CloneUpdatePacket packet) {
    }


    public boolean isActive() {
        return ticksSinceUpdate < PLAYER_TIMEOUT;
    }

    public ServerPlayer getPlayer() {
        return player;
    }

    public Channel getChannel() {
        return channel;
    }
}
