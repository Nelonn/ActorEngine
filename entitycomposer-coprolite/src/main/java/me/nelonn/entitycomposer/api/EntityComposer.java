package me.nelonn.entitycomposer.api;

import me.nelonn.coprolite.api.CoproliteLoader;
import me.nelonn.entitycomposer.api.actor.ActorRegistry;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Objects;

public interface EntityComposer {
    String ID = "entitycomposer";

    static @NotNull EntityComposer get() {
        return (EntityComposer) Objects.requireNonNull(CoproliteLoader.getInstance().getObjectShare().get(ID + ":core"), "EntityComposer");
    }

    @NotNull ActorRegistry actors();

    boolean shouldRemoveNonExistentActors();

    @NotNull Logger getLogger();
}
