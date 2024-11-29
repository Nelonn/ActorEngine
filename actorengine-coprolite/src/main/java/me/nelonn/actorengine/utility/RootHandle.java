package me.nelonn.actorengine.utility;

import me.nelonn.bestvecs.MutVec3d;
import me.nelonn.actorengine.api.ActorEngine;
import me.nelonn.actorengine.api.Root;
import me.nelonn.actorengine.api.actor.Actor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class RootHandle implements Root {
    public static final boolean configVisibleByDefault = false;
    private final Entity entity;
    private final ActorEngine actorEngine;
    private @Nullable Actor actor;
    private @Nullable CompoundTag recoveryData = null;
    private boolean shouldBeSaved = true;
    private EntityDimensions dimensions = EntityDimensions.scalable(0.0F, 0.0F);

    public RootHandle(Entity entity) {
        this.entity = entity;
        this.actorEngine = ActorEngine.get();
        entity.visibleByDefault = configVisibleByDefault;
    }

    @Override
    public Entity asEntity() {
        return entity;
    }

    @Override
    public ActorEngine getActorEngine() {
        return actorEngine;
    }

    @Override
    public Level level() {
        return entity.level();
    }

    @Override
    public MutVec3d position() {
        return new MutVec3d(entity.getX(), entity.getY(), entity.getZ());
    }

    @Override
    public void moveTo(double x, double y, double z) {
        entity.moveTo(x, y, z);
    }

    @Override
    public @Nullable Actor getActor() {
        return actor;
    }

    @Override
    public void setActor(@Nullable Actor actor) {
        this.actor = actor;
    }

    @Override
    public boolean isShouldBeSaved() {
        return shouldBeSaved;
    }

    @Override
    public void setShouldBeSaved(boolean flag) {
        shouldBeSaved = flag;
    }

    public void handleSetRemoved(Entity.RemovalReason reason) {
        if (this.actor != null) {
            this.actor.onRootRemoved(reason);
        }
    }

    public EntityDimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(EntityDimensions dimensions) {
        this.dimensions = dimensions;
        entity.refreshDimensions();
    }

    public @Nullable CompoundTag getRecoveryData() {
        return recoveryData;
    }

    public void setRecoveryData(@Nullable CompoundTag recoveryData) {
        this.recoveryData = recoveryData;
    }
}
