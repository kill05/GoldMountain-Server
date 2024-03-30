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
    public void tick() {
        for (ServerDimension dimension : floorDimensionMap.values()) {
            dimension.tick();
        }
    }

    @Override
    public ServerDimension getDimension(int floor) {
        return floorDimensionMap.get(floor);
    }

    @Override
    public @NotNull ServerDimension createDimension(int floor) {
        if(floorDimensionMap.containsKey(floor)) {
            throw new IllegalStateException(String.format("Multi Dimension group already has a dimension for floor %s.", floor));
        }

        ServerDimension dimension = new ServerDimension(this, floor);
        floorDimensionMap.put(floor, dimension);
        return dimension;
    }

    @Override
    public void deleteDimension(int floor) {
        floorDimensionMap.remove(floor);
    }
}
