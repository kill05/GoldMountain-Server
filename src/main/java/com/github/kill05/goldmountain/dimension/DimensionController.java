package com.github.kill05.goldmountain.dimension;

import com.github.kill05.goldmountain.dimension.entity.Entity;
import com.github.kill05.goldmountain.dimension.group.DimensionGroup;
import com.github.kill05.goldmountain.dimension.group.MultiDimensionGroup;
import com.github.kill05.goldmountain.dimension.group.SingleDimensionGroup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DimensionController {

    public static final Logger LOGGER = LogManager.getLogger(DimensionController.class);
    private final Map<DimensionType, DimensionGroup> dimensionMap;
    private final Map<Entity, ServerDimension> entityToMoveMap; // ServerDimension is the previous dimension of the entity (can be null)

    public DimensionController() {
        this.dimensionMap = new HashMap<>();
        this.entityToMoveMap = new HashMap<>();

        getOrCreateDimension(DimensionType.SPAWN, 0);
    }


    public void tick() {
        for (DimensionGroup value : dimensionMap.values()) {
            value.tick();
        }

        // Process entities that need to be moved to another dimension
        for (Map.Entry<Entity, ServerDimension> entry : entityToMoveMap.entrySet()) {
            Entity entity = entry.getKey();
            ServerDimension dimension = entity.getDimension();

            if(dimension == null) throw new IllegalStateException("Entity with no dimension was marked to be moved.");
            dimension.moveEntityUnsafe(entity, entry.getValue());
        }


    }


    public void markToMove(@NotNull Entity entity, @Nullable ServerDimension oldDimension) {
        entityToMoveMap.put(entity, oldDimension);
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
