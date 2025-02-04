package com.github.kill05.goldmountain.server.dimension;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.server.entity.ServerEntity;
import com.github.kill05.goldmountain.server.dimension.group.DimensionGroup;
import com.github.kill05.goldmountain.server.dimension.group.MultiDimensionGroup;
import com.github.kill05.goldmountain.server.dimension.group.SingleDimensionGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DimensionController {

    public static final Logger LOGGER = LoggerFactory.getLogger(DimensionController.class);
    private final Map<DimensionType, DimensionGroup> dimensionMap;
    private final Map<ServerEntity, DimensionInfo> entityToMoveMap; // Value is the future dimension of the entity
    private boolean tickedDimensions;

    public DimensionController() {
        this.dimensionMap = new HashMap<>();
        this.entityToMoveMap = new HashMap<>();

        getOrCreateDimension(DimensionType.SPAWN, 0);
    }

    public void preTick() {
        this.tickedDimensions = false;
    }

    public void tick() {
        for (DimensionGroup value : dimensionMap.values()) {
            value.tick();
        }

        this.tickedDimensions = true;

        // Process entities that need to be moved to another dimension
        for (Map.Entry<ServerEntity, DimensionInfo> entry : entityToMoveMap.entrySet()) {
            ServerEntity entity = entry.getKey();
            DimensionInfo info = entry.getValue();
            moveEntity(entity, info.type(), info.floor());
        }

        entityToMoveMap.clear();
    }


    public void markToMove(@NotNull ServerEntity entity, @Nullable DimensionType type, int floor) {
        if (tickedDimensions) {
            moveEntity(entity, type, floor);
            return;
        }

        entityToMoveMap.put(entity, new DimensionInfo(type, floor));
    }

    private void moveEntity(@NotNull ServerEntity entity, @Nullable DimensionType type, int floor) {
        if (type == null) {
            ServerDimension dimension = entity.getDimension();
            if (dimension == null) {
                LOGGER.warn("Tried to remove an entity with no dimension.");
                return;
            }

            LOGGER.debug("Removing entity from dimension {}, floor {}", dimension.getType(), dimension.getFloor());
            dimension.removeEntityUnsafe(entity);
            return;
        }

        LOGGER.debug("Moving entity to dimension {}, floor {}", type, floor);
        ServerDimension newDimension = getOrCreateDimension(type, floor);
        newDimension.moveEntityUnsafe(entity);
    }


    public @Nullable ServerDimension getDimension(@NotNull DimensionType type, int floor) {
        return getGroup(type).getDimension(floor);
    }

    public @NotNull ServerDimension getGeneratedDimension(@NotNull DimensionType type, int floor) {
        ServerDimension dimension = getDimension(type, floor);
        if (dimension == null)
            throw new IllegalStateException(String.format("No generated dimension of type %s and floor %s.", type, floor));

        return dimension;
    }

    public @NotNull ServerDimension getOrCreateDimension(@NotNull DimensionType type, int floor) {
        return getGroup(type).getOrCreateDimension(floor);
    }

    public @NotNull DimensionGroup getGroup(@NotNull DimensionType type) {
        return dimensionMap.computeIfAbsent(type, this::createGroup);
    }

    private @NotNull DimensionGroup createGroup(@NotNull DimensionType type) {
        return type.hasMultipleFloors() ? new MultiDimensionGroup(type) : new SingleDimensionGroup(type);
    }

}
