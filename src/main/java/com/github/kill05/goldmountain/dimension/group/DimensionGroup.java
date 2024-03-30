package com.github.kill05.goldmountain.dimension.group;

import com.github.kill05.goldmountain.dimension.DimensionType;
import com.github.kill05.goldmountain.dimension.ServerDimension;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class DimensionGroup {

    private final DimensionType dimensionType;

    protected DimensionGroup(DimensionType dimensionType) {
        Validate.notNull(dimensionType, "Dimension Type can't be null.");
        this.dimensionType = dimensionType;
    }


    public abstract void tick();

    public abstract @Nullable ServerDimension getDimension(int floor);

    protected abstract @NotNull ServerDimension createDimension(int floor);

    public abstract void deleteDimension(int floor);


    public boolean dimensionExists(int floor) {
        return getDimension(floor) != null;
    }

    public @NotNull ServerDimension getOrCreateDimension(int floor) {
        ServerDimension dimension = getDimension(floor);
        if(dimension != null) return dimension;

        return createDimension(floor);
    }


    public DimensionType getType() {
        return dimensionType;
    }
}