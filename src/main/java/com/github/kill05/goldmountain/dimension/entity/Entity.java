package com.github.kill05.goldmountain.dimension.entity;

import com.github.kill05.goldmountain.GMServer;
import com.github.kill05.goldmountain.dimension.DimensionType;
import org.joml.Vector2f;

public abstract class Entity {

    protected final GMServer server;
    protected DimensionType dimensionType;
    protected float floor;
    protected Vector2f[] checkpoints;
    protected short speed;

    public Entity(GMServer server) {
        this.server = server;
        this.checkpoints = new Vector2f[4];
    }


    public void tick() {

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
