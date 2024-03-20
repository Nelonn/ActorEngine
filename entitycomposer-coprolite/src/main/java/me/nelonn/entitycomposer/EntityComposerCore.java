package me.nelonn.entitycomposer;

import me.nelonn.coprolite.api.CoproliteLoader;
import me.nelonn.coprolite.paper.std.registryaccessor.EntityTypeRegistry;
import me.nelonn.entitycomposer.api.EntityComposer;
import me.nelonn.entitycomposer.api.actor.ActorRegistry;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class EntityComposerCore implements EntityComposer {
    public static final String KEY = EntityComposer.ID + ":core";
    private final ActorRegistry actorRegistry = new ActorRegistry();
    private final Logger logger;
    private boolean shouldRemoveNonExistentActors = false;

    public EntityComposerCore(@NotNull Logger logger) {
        CoproliteLoader.getInstance().getObjectShare().put(KEY, this);
        this.logger = logger;
    }

    @Override
    public @NotNull ActorRegistry actors() {
        return this.actorRegistry;
    }

    @Override
    public boolean shouldRemoveNonExistentActors() {
        return this.shouldRemoveNonExistentActors;
    }

    public void setShouldRemoveNonExistentActors(boolean shouldRemoveNonExistentActors) {
        this.shouldRemoveNonExistentActors = shouldRemoveNonExistentActors;
    }

    @Override
    public @NotNull Logger getLogger() {
        return this.logger;
    }

    static {
        EntityTypeRegistry.class.getClassLoader(); // initialize
    }
}
