package com.github.kill05.goldmountain.server.entity;

import com.github.kill05.goldmountain.entity.Entity;
import com.github.kill05.goldmountain.server.GMServer;
import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.server.dimension.ServerDimension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2f;

public abstract class ServerEntity implements Entity {

    protected final GMServer server;
    protected DimensionType dimensionType;
    protected int floor;

    protected final Vector2f[] checkpoints;
    protected int speed;

    public ServerEntity(GMServer server) {
        this.server = server;
        this.checkpoints = new Vector2f[4];
    }


    public static boolean debug = false;

    public void tick() {
        if (debug) System.out.printf("Ticked on tick %s!%n", server.getCurrentTick());
    }

    public @Nullable ServerDimension getDimension() {
        if (dimensionType == null) return null;
        return server.getDimensionController().getGeneratedDimension(dimensionType, floor);
    }

    public void setDimension(@NotNull DimensionType type, int floor) {
        setDimension(type, floor, false);
    }

    public void setDimension(@NotNull DimensionType type, int floor, boolean force) {
        if (!force && !type.isValidFloor(floor)) {
            throw new IllegalArgumentException(String.format("Floor %s is not a valid floor for dimension %s.", floor, type));
        }

        if (type == this.dimensionType && floor == this.floor) return;

        // Mark entity to be moved at the end of the tick
        server.getDimensionController().markToMove(this, type, floor);
    }

    public void setDimension(@NotNull DimensionType type) {
        setDimension(type, 0);
    }

    public void setDimensionUnsafe(@Nullable DimensionType type, int floor) {
        this.dimensionType = type;
        this.floor = floor;
    }

    public void remove() {
        server.getDimensionController().markToMove(this, null, 0);
    }


    public void descend() {
        setDimension(this.dimensionType, this.floor + 1);
    }

    @Override
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
    @Override
    public Vector2f[] getCheckpoints() {
        return checkpoints;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(short speed) {
        this.speed = speed;
    }

    public DimensionType getDimensionType() {
        return dimensionType;
    }

    public int getFloor() {
        return floor;
    }

    public GMServer getServer() {
        return server;
    }
}
