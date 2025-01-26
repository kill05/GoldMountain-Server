package com.github.kill05.goldmountain.server.dimension;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.server.entity.ServerEntity;
import com.github.kill05.goldmountain.server.dimension.group.DimensionGroup;
import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.function.Consumer;

public class ServerDimension {

    //private final Map<Integer, ServerTile> tileIDMap;
    private final Collection<ServerEntity> entities;
    private final DimensionGroup dimensionGroup;
    private final int floor;
    //private int ticksUntilReset;

    public ServerDimension(DimensionGroup group, int floor) {
        Validate.notNull(group, "Dimension Group can't be null.");
        this.dimensionGroup = group;
        this.floor = floor;
        //this.tileIDMap = new HashMap<>();
        this.entities = new HashSet<>();

        DimensionController.LOGGER.debug("Created dimension with type {} and floor {}.", group.getType(), floor);
    }

    //todo: fix a bug where if a player disconnects, the entity is still there
    public void tick() {
        for (ServerEntity entity : entities) {
            entity.tick();
        }
    }

    public void moveEntityUnsafe(ServerEntity entity) {
        ServerDimension oldDimension = entity.getDimension();
        if(oldDimension != null) oldDimension.entities.remove(entity);

        entity.setDimensionUnsafe(getType(), getFloor());
        entities.add(entity);
    }

    public void removeEntityUnsafe(ServerEntity entity) {
        entity.setDimensionUnsafe(null, 0);
        entities.remove(entity);
    }


    public Collection<ServerEntity> getEntities() {
        return Collections.unmodifiableCollection(entities);
    }

    public int getEntityCount() {
        return entities.size();
    }

    public void forEachEntity(Consumer<ServerEntity> consumer) {
        for (ServerEntity entity : entities) {
            consumer.accept(entity);
        }
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
