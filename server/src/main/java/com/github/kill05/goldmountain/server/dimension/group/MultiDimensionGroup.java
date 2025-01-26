package com.github.kill05.goldmountain.server.dimension.group;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.server.dimension.ServerDimension;
import com.github.kill05.goldmountain.server.entity.ServerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
        if (floorDimensionMap.containsKey(floor)) {
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


    @Override
    public List<ServerEntity> getEntities() {
        List<ServerEntity> entities = new ArrayList<>();

        for (ServerDimension dimension : floorDimensionMap.values()) {
            entities.addAll(dimension.getEntities());
        }

        return entities;
    }

    @Override
    public int getEntityCount() {
        int amount = 0;

        for (ServerDimension dimension : floorDimensionMap.values()) {
            amount += dimension.getEntityCount();
        }

        return amount;
    }
}
