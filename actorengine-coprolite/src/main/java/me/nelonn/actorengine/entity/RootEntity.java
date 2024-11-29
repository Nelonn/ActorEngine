package me.nelonn.actorengine.entity;

import me.nelonn.actorengine.api.ActorPart;
import me.nelonn.actorengine.api.Root;
import me.nelonn.actorengine.api.actor.Actor;
import me.nelonn.actorengine.utility.EntityRootAccessor;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public final class RootEntity {

    public static boolean isRoot(Entity entity) {
        return getAsRoot(entity) != null;
    }

    public static @Nullable Root getAsRoot(Entity entity) {
        return ((EntityRootAccessor) entity).actorEngine$getRootHandle();
    }

    public static @Nullable Actor getActor(Entity entity) {
        return ((Object) entity) instanceof ActorPart actorPart ? actorPart.getActor() : null;
    }

    private RootEntity() {
        throw new UnsupportedOperationException();
    }
}
