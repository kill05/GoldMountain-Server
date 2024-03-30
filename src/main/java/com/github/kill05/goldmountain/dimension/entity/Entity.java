package com.github.kill05.goldmountain.dimension.entity;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.dimension.ServerDimension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;

public abstract class Entity {

    protected final GMServer server;
    protected DimensionType dimensionType;
    protected int floor;
    protected Vector2f[] checkpoints;
    protected short speed;

    public Entity(GMServer server) {
        this.server = server;
        this.checkpoints = new Vector2f[4];
    }


    public void tick() {

    }


    public @Nullable ServerDimension getDimension() {
        if(dimensionType == null) return null;
        return server.getDimensionController().getOrCreateDimension(dimensionType, floor);
    }

    public void setDimension(@NotNull DimensionType type, int floor) {
        if(!type.isValidFloor(floor)) {
            throw new IllegalArgumentException(String.format("Floor %s is not a valid floor for dimension %s.", floor, type));
        }

        // getDimension returns two different dimensions because the type and floor fields are different between each call
        ServerDimension dimension = getDimension();
        if(dimension != null) dimension.removeEntityUnsafe(this);

        this.dimensionType = type;
        this.floor = floor;
        getDimension().addEntityUnsafe(this);
    }

    public void setDimension(DimensionType type) {
        setDimension(type, 0);
    }

    public void descend() {
        setDimension(this.dimensionType, this.floor + 1);
    }


    public Vector2f getLocation() {
        return checkpoints[0];
    }

    /**
     * Method to get the checkpoints the entity will follow to get to its destination.
     * It's not sure yet whether these waypoints exist for each entity or they are exclusive
     * to human entities (player and shadow clone).
     * <p>
     * The first waypoint is the current location of the entity, and the other 3 are the next 3 points
     * the entity will travel to.
     * <p>
     * If the first waypoint is the same as the second one, that means the entity is standing still.
     *
     * @return the 4 checkpoints the entity will travel to to move towards its target
     */
    public Vector2f[] getCheckpoints() {
        return checkpoints;
    }

    public short getSpeed() {
        return speed;
    }


    public GMServer getServer() {
        return server;
    }
}