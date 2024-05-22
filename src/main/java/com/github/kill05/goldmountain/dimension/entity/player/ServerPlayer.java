package com.github.kill05.goldmountain.dimension.entity.player;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.protocol.PlayerConnection;
import com.github.kill05.goldmountain.protocol.ConnectionController;
import com.github.kill05.goldmountain.protocol.packets.io.HumanUpdatePacket;
import com.github.kill05.goldmountain.protocol.packets.io.PlayerUpdatePacket;
import com.github.kill05.goldmountain.protocol.packets.out.UpdateDimensionPacket;
import io.netty.channel.Channel;
import org.jetbrains.annotations.NotNull;

public class ServerPlayer extends PlayerEntity {

    private final PlayerConnection connection;
    //private ShadowClone shadowClone;
    private int totalLevel;

    public ServerPlayer(ConnectionController controller, int id, Channel channel) {
        super(controller.getServer(), id);
        this.connection = new PlayerConnection(this, channel);
    }

    public void tick() {
        super.tick();
        connection.tick();
        checkLoadingZone();
    }

    private void checkLoadingZone() {
        if(getLocation() == null) return;

        if(dimensionType == DimensionType.SPAWN) {
            for (DimensionType type : DimensionType.values()) {
                if (!type.isOnLoadingZone(this)) continue;
                setDimension(type);
                return;
            }
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
    public void update(HumanUpdatePacket packet) {
        if(!(packet instanceof PlayerUpdatePacket updatePacket)) {
            throw new IllegalArgumentException("Packet must be a player update packet!");
        }

        this.totalLevel = updatePacket.totalLevel();
        this.costume = updatePacket.getCostume();
        this.speed = updatePacket.getSpeed();
        this.targetTile = updatePacket.getTargetTile();

        for(int i = 0; i < this.checkpoints.length; i++) {
            this.checkpoints[i] = updatePacket.getCheckpoints()[i];
        }
    }

    public void sendUpdateDimensionPacket(boolean syncFloor) {
        UpdateDimensionPacket packet = new UpdateDimensionPacket(dimensionType);

        if(syncFloor && dimensionType.hasMultipleFloors()) {
            for(int i = 0; i < floor; i++) {
                //todo: change with MultiPacket when tested
                connection.sendPacket(new UpdateDimensionPacket(DimensionType.SPAWN));
                connection.sendPacket(packet);
            }

            return;
        }

        connection.sendPacket(packet);
    }

    @Override
    public PlayerConnection getConnection() {
        return connection;
    }

    @Override
    public int getTotalLevel() {
        return totalLevel;
    }



}
