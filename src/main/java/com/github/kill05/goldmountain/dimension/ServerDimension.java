package com.github.kill05.goldmountain.dimension;

import com.github.kill05.goldmountain.dimension.entity.Entity;
import com.github.kill05.goldmountain.dimension.group.DimensionGroup;
import com.github.kill05.goldmountain.dimension.tile.ServerTile;
import org.apache.commons.lang3.Validate;

import java.util.*;

public class ServerDimension {

    private final Map<Integer, ServerTile> tileIDMap;
    private final Collection<Entity> entities;
    private final DimensionGroup dimensionGroup;
    private final int floor;
    private int ticksUntilReset;

    public ServerDimension(DimensionGroup group, int floor) {
        Validate.notNull(group, "Dimension Group can't be null.");
        this.dimensionGroup = group;
        this.floor = floor;
        this.tileIDMap = new HashMap<>();
        this.entities = new HashSet<>();
    }

    public void teleport(Entity entity, boolean syncFloor) {
        entity.teleport(getType());
    }


    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }


    public DimensionGroup getGroup() {
        return dimensionGroup;
    }

    public DimensionType getType() {
        return dimensionGroup.getType();
    }

    public int getFloor() {
        return floor;
    }
}
