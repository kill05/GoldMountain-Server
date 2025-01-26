package com.github.kill05.goldmountain.proxy.connection;

import com.github.kill05.goldmountain.connection.packet.Packet;
import com.github.kill05.goldmountain.proxy.gui.packet.PacketPanel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import org.jetbrains.annotations.NotNull;

public class ProxyPacketHandler extends ChannelDuplexHandler {

    private final Channel forwardChannel;
    private final PacketPanel panel;

    public ProxyPacketHandler(Channel forwardChannel, PacketPanel panel) {
        this.forwardChannel = forwardChannel;
        this.panel = panel;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        panel.onPacketSent((Packet) msg);
        forwardChannel.writeAndFlush(msg);
    }


    @NotNull
    public Channel getForwardChannel() {
        return forwardChannel;
    }
}
