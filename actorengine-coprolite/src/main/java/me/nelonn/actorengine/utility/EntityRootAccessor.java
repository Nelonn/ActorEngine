package me.nelonn.actorengine.utility;

import javax.annotation.Nullable;

public interface EntityRootAccessor {

    default @Nullable RootHandle actorEngine$getRootHandle() {
        return null;
    }

    default void actorEngine$setRootHandle(@Nullable RootHandle rootHandle) {}

}
