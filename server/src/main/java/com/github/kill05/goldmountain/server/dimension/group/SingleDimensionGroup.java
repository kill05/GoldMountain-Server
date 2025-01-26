package com.github.kill05.goldmountain.server.dimension.group;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.server.dimension.ServerDimension;
import com.github.kill05.goldmountain.server.entity.ServerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SingleDimensionGroup extends DimensionGroup {

    private ServerDimension dimension;

    public SingleDimensionGroup(DimensionType dimensionType) {
        super(dimensionType);
    }

    @Override
    public void tick() {
        dimension.tick();
    }

    @Override
    public ServerDimension getDimension(int floor) {
        return dimension;
    }

    @NotNull
    @Override
    public ServerDimension createDimension(int floor) {
        if(dimension != null) {
            throw new IllegalStateException("Single Dimension group already has a dimension.");
        }

        this.dimension = new ServerDimension(this, 0);
        return dimension;
    }

    @Override
    public void deleteDimension(int floor) {
        this.dimension = null;
    }

    @Override
    public List<ServerEntity> getEntities() {
        return new ArrayList<>(dimension.getEntities());
    }

    @Override
    public int getEntityCount() {
        return dimension.getEntityCount();
    }
}
