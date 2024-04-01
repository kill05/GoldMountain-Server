package com.github.kill05.goldmountain.protocol;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.dimension.ServerDimension;
import com.github.kill05.goldmountain.dimension.entity.player.PlayerEntity;
import com.github.kill05.goldmountain.dimension.entity.player.ServerPlayer;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import com.github.kill05.goldmountain.protocol.packets.io.CloneUpdatePacket;
import com.github.kill05.goldmountain.protocol.packets.io.PlayerUpdatePacket;
import io.netty.channel.Channel;

public class PlayerConnection {

    public static int PLAYER_TIMEOUT = GMServer.TARGET_TPS * 2;

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
            if(!(entity instanceof PlayerEntity other)) return;
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
