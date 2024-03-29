package com.github.kill05.goldmountain.dimension.group;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.dimension.ServerDimension;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MultiDimensionGroup extends DimensionGroup {

    private final Map<Integer, ServerDimension> floorDimensionMap;

    public MultiDimensionGroup(DimensionType dimensionType) {
        super(dimensionType);
        this.floorDimensionMap = new HashMap<>();
    }

    @Override
    public ServerDimension getDimensionNullable(int floor) {
        return floorDimensionMap.get(floor);
    }

    @NotNull
    @Override
    public ServerDimension createDimension(int floor) {
        ServerDimension dimension = new ServerDimension(this, floor);
        if(floorDimensionMap.put(floor, dimension) != null) {
            throw new IllegalStateException(String.format("Multi Dimension group already has a dimension for floor %s.", floor));
        }

        return dimension;
    }

    @Override
    public void deleteDimension(int floor) {
        floorDimensionMap.remove(floor);
    }
}
