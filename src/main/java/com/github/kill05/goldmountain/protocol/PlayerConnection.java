package com.github.kill05.goldmountain.protocol;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.dimension.entity.ServerPlayer;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutPlayerUpdate;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutShadowCloneUpdate;
import io.netty.channel.Channel;

import java.util.Collection;

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
        PacketInOutPlayerUpdate update = new PacketInOutPlayerUpdate(player);
        sendPacketToOthers(update);
    }


    public void sendPacket(Packet packet) {
        if(!isActive()) return;
        channel.writeAndFlush(packet);
    }

    public void sendPacketToOthers(Packet packet) {
        Collection<ServerPlayer> players = player.getServer().getPlayerController().getPlayers();
        for (ServerPlayer serverPlayer : players) {
            if(serverPlayer == player) continue;
            serverPlayer.getConnection().sendPacket(packet);
        }
    }


    public void handlePlayerUpdate(PacketInOutPlayerUpdate packet) {
        player.update(packet);
        ticksSinceUpdate = 0;
    }

    public void handleCloneUpdate(PacketInOutShadowCloneUpdate packet) {
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
