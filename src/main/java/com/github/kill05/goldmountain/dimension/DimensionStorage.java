package com.github.kill05.goldmountain.dimension;

import com.github.kill05.goldmountain.dimension.group.DimensionGroup;
import com.github.kill05.goldmountain.dimension.group.MultiDimensionGroup;
import com.github.kill05.goldmountain.dimension.group.SingleDimensionGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DimensionStorage {

    private final Map<DimensionType, DimensionGroup> dimensionMap;

    public DimensionStorage() {
        this.dimensionMap = new HashMap<>();
        getOrCreateDimension(DimensionType.SPAWN, 0);
    }


    @Nullable
    public ServerDimension getDimensionNullable(DimensionType type, int floor) {
        return getGroup(type).getDimensionNullable(floor);
    }

    @NotNull
    public ServerDimension getOrCreateDimension(DimensionType type, int floor) {
        return getGroup(type).getOrCreateDimension(floor);
    }

    @NotNull
    public DimensionGroup getGroup(DimensionType type) {
        return dimensionMap.computeIfAbsent(type, this::createGroup);
    }

    private DimensionGroup createGroup(DimensionType type) {
        return type.hasMultipleFloors() ? new MultiDimensionGroup(type) : new SingleDimensionGroup(type);
    }

}
