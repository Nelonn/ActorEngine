package me.nelonn.actorengine.api.actor;

import me.nelonn.actorengine.api.ActorEngine;
import me.nelonn.actorengine.api.Root;
import me.nelonn.actorengine.utility.RootHandle;
import me.nelonn.actorengine.utility.EntityRootAccessor;
import me.nelonn.flint.path.Key;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Marker;
import net.minecraft.world.level.Level;

public class ActorType<T extends Actor> {
    private final ActorFactory<T> factory;

    public ActorType(ActorFactory<T> factory) {
        this.factory = factory;
    }

    public Key getKey() {
        return ActorEngine.get().actors().getKeyOptional(this).orElseThrow(() ->
                new IllegalStateException("Actor type from package '" + factory.getClass().getPackageName() + "' not registered"));
    }

    public T spawn(Level world) {
        Marker marker = new Marker(EntityType.MARKER, world);
        RootHandle rootHandle = new RootHandle(marker);
        ((EntityRootAccessor) (Object) marker).actorEngine$setRootHandle(rootHandle);
        world.addFreshEntity(marker);
        T actor = create(rootHandle.asRoot());
        rootHandle.setActor(actor);
        actor.assemble(null);
        return actor;
    }

    public T create(Root world) {
        return this.factory.create(this, world);
    }

    public interface ActorFactory<T extends Actor> {
        T create(ActorType<T> type, Root root);
    }
}
