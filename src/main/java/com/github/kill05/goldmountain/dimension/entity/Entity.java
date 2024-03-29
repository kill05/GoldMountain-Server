package com.github.kill05.goldmountain.dimension.entity;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.dimension.ServerDimension;
import com.github.kill05.goldmountain.protocol.packets.out.PacketOutChangeDimension;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;

public abstract class Entity {

    protected final GMServer server;

    protected DimensionType currentDimension;
    protected int currentFloor;

    protected final Vector2f location;
    protected final Vector2f firstTargetLocation;
    protected final Vector2f secondTargetLocation;
    protected final Vector2f thirdTargetLocation;
    protected short speed;


    public Entity(GMServer server) {
        this.server = server;
        this.location = new Vector2f();
        this.firstTargetLocation = new Vector2f();
        this.secondTargetLocation = new Vector2f();
        this.thirdTargetLocation = new Vector2f();
    }


    public ServerDimension getDimension() {
        throw new NotImplementedException();
    }

    public DimensionType getDimensionType() {
        return currentDimension != null ? currentDimension : DimensionType.SPAWN;
    }

    public int getFloor() {
        return currentFloor;
    }


    public void teleport(@Nullable DimensionType type, int floor) {
        if(getDimensionType() == type) return;
        setDimensionAndNotify(type, floor);

        syncPosition();
    }

    public void teleport(@Nullable DimensionType type) {
        teleport(type, 0);
    }

    public void teleportSpawn() {
        teleport(DimensionType.SPAWN);
    }


    public void descend(boolean bypassLimit) {
        DimensionType nextDim = currentDimension;
        if(currentDimension == null || !currentDimension.hasMultipleFloors()) return;
        if(!bypassLimit && currentFloor >= currentDimension.getMaxFloor()) return;

        // check if the entity is getting teleported to last floor
        if(currentFloor + 1 == currentDimension.getMaxFloor()) {
            nextDim = currentDimension.getLastFloor();
        }

        setDimensionAndNotify(nextDim, currentFloor + 1);
        sendDimensionPacket();
    }


    private void sendDimensionPacket() {
        if(!(this instanceof ServerPlayer player)) return;
        player.getConnection().sendPacket(new PacketOutChangeDimension(getDimensionType()));
    }

    public void syncPosition() {
        if(!(this instanceof ServerPlayer player)) return;
        for(int i = 0; i <= currentFloor; i++) {
            sendDimensionPacket();
        }
    }

    private void setDimensionAndNotify(@Nullable DimensionType type, int floor) {
        throw new NotImplementedException();
        /*
        ServerDimension oldDim = getDimension();
        ServerDimension newDim = server.getDimensionStorage().getOrCreateDimension(type, floor);

        this.currentDimension = type;
        this.currentFloor = floor;

        oldDim.removeEntity(this);
        newDim.addEntity(this);

         */
    }


    public Vector2f getLocation() {
        return location;
    }

    public Vector2f getFirstTargetLocation() {
        return firstTargetLocation;
    }

    public Vector2f getSecondTargetLocation() {
        return secondTargetLocation;
    }

    public Vector2f getThirdTargetLocation() {
        return thirdTargetLocation;
    }

    public short getSpeed() {
        return speed;
    }

}
