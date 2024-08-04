package me.nelonn.actorengine;

public interface EntityDimensionsAccessor {

    default void actorEngine$setCentered(boolean centered) {
        throw new UnsupportedOperationException();
    }

}
