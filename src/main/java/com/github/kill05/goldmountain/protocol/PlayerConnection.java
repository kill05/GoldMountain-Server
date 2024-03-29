package com.github.kill05.goldmountain.protocol;

import com.github.kill05.goldmountain.dimension.entity.ServerPlayer;
import com.github.kill05.goldmountain.protocol.packets.Packet;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutPlayerUpdate;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutShadowCloneUpdate;
import io.netty.channel.Channel;

import java.util.Collection;

public class PlayerConnection {

    private final ServerPlayer player;
    private final Channel channel;

    private int ticksSinceUpdate;
    private int ticksSinceCloneUpdate;

    public PlayerConnection(ServerPlayer player, Channel channel) {
        this.player = player;
        this.channel = channel;
    }

    public void sendPacket(Packet packet) {
        channel.writeAndFlush(packet);
    }

    public void processPacketQueue() {

    }


    public void tick() {
        PacketInOutPlayerUpdate updatePacket = new PacketInOutPlayerUpdate(player);
        Collection<ServerPlayer> players = player.getServer().getPlayerController().getPlayers();
        for (ServerPlayer serverPlayer : players) {
            if(serverPlayer == player) continue;
            serverPlayer.getConnection().sendPacket(updatePacket);
        }

        ticksSinceUpdate++;
        ticksSinceCloneUpdate++;
    }


    public void handlePlayerUpdate(PacketInOutPlayerUpdate packet) {
        player.updateTotalLevel(packet.getTotalLevel());
        player.updateCostume(packet.getCostume());
        player.updateSpeed(packet.getSpeed());
        player.updateCheckpoints(packet.getCheckpoints());

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
