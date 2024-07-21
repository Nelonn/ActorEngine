package me.nelonn.entitycomposer;

public interface EntityDimensionsAccessor {

    default void entityComposer$setCentered(boolean centered) {
        throw new UnsupportedOperationException();
    }

}
