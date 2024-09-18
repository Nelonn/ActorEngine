package me.nelonn.actorengine.api.actor;

import me.nelonn.actorengine.api.ActorEngine;
import me.nelonn.actorengine.api.Root;
import me.nelonn.actorengine.entity.RootEntity;
import me.nelonn.flint.path.Key;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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
        RootEntity root = new RootEntity(world);
        world.addFreshEntity(root);
        T actor = create(root.asRoot());
        root.setOwner(actor);
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
