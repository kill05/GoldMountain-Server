package com.github.kill05.goldmountain.dimension.group;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.dimension.ServerDimension;
import org.jetbrains.annotations.NotNull;

public class SingleDimensionGroup extends DimensionGroup {

    private ServerDimension dimension;

    public SingleDimensionGroup(DimensionType dimensionType) {
        super(dimensionType);
    }

    @Override
    public ServerDimension getDimensionNullable(int floor) {
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
}
