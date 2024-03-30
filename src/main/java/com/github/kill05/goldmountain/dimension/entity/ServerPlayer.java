package com.github.kill05.goldmountain.dimension.entity;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.protocol.PlayerConnection;
import com.github.kill05.goldmountain.protocol.PlayerController;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutHumanEntityUpdate;
import com.github.kill05.goldmountain.protocol.packets.io.PacketInOutPlayerUpdate;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutUpdateDimension;
import io.netty.channel.Channel;
import org.jetbrains.annotations.NotNull;

public class ServerPlayer extends HumanEntity {

    private final short id;
    private final PlayerConnection connection;
    //private ShadowClone shadowClone;

    private int totalLevel;

    public ServerPlayer(PlayerController controller, short id, Channel channel) {
        super(controller.getServer());
        this.connection = new PlayerConnection(this, channel);
        this.id = id;
    }

    public void tick() {
        super.tick();
        connection.tick();
        tickPosition();
    }

    // Check if player is on a loading zone
    private void tickPosition() {
        if(getLocation() == null || dimensionType != DimensionType.SPAWN) return;
        for (DimensionType type : DimensionType.values()) {
            if(!type.isOnLoadingZone(this)) continue;
            setDimension(type);
            return;
        }
    }


    @Override
    public void setDimension(@NotNull DimensionType type, int floor) {
        boolean syncFloor = this.dimensionType == type && floor - this.floor != 1;

        super.setDimension(type, floor);
        sendUpdateDimensionPacket(syncFloor);

        // Clear current location to avoid the player flashing for a bit on its old position
        checkpoints[0] = null;
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

    public void sendUpdateDimensionPacket(boolean syncFloor) {
        PacketOutUpdateDimension packet = new PacketOutUpdateDimension(dimensionType);

        if(syncFloor && dimensionType.hasMultipleFloors()) {
            for(int i = 0; i < floor; i++) {
                //todo: change with MultiPacket when tested
                connection.sendPacket(new PacketOutUpdateDimension(DimensionType.SPAWN));
                connection.sendPacket(packet);
            }

            return;
        }

        connection.sendPacket(packet);
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
