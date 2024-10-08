package me.nelonn.actorengine.entity;

import me.nelonn.bestvecs.MutVec3d;
import me.nelonn.actorengine.api.ActorEngine;
import me.nelonn.actorengine.api.Root;
import me.nelonn.actorengine.api.actor.Actor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RootDelegate implements Root {
    private final RootEntity handle;

    public RootDelegate(RootEntity handle) {
        this.handle = handle;
    }

    @Override
    public Entity asEntity() {
        return handle;
    }

    @Override
    public ActorEngine getActorEngine() {
        return handle.getActorEngine();
    }

    @Override
    public Level level() {
        return handle.level();
    }

    @Override
    public MutVec3d position() {
        return new MutVec3d(handle.getX(), handle.getY(), handle.getZ());
    }

    @Override
    public void moveTo(double x, double y, double z) {
        handle.moveTo(x, y, z);
    }

    @Override
    public @Nullable Actor getActor() {
        return handle.getActor();
    }

    @Override
    public void setActor(@Nullable Actor actor) {
        handle.setActor(actor);
    }

    @Override
    public boolean isShouldBeSaved() {
        return handle.isShouldBeSaved();
    }

    @Override
    public void setShouldBeSaved(boolean flag) {
        handle.setShouldBeSaved(true);
    }
}
