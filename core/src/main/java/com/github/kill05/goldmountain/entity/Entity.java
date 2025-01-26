package com.github.kill05.goldmountain.entity;

import org.joml.Vector2f;

public interface Entity {

    default Vector2f getLocation() {
        return getCheckpoints()[0];
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
    Vector2f[] getCheckpoints();

}
