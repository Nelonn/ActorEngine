package me.nelonn.actorengine.mixin;

import me.nelonn.actorengine.api.ActorEngine;
import me.nelonn.actorengine.api.ActorPart;
import me.nelonn.actorengine.api.actor.Actor;
import me.nelonn.actorengine.api.actor.ActorType;
import me.nelonn.actorengine.utility.RootHandle;
import me.nelonn.actorengine.utility.EntityRootAccessor;
import me.nelonn.flint.path.Key;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Locale;

@Mixin(Entity.class)
public abstract class EntityMixin implements ActorPart, EntityRootAccessor {
    @Unique
    private @Nullable RootHandle actorEngine$rootHandle = null;

    @Unique
    public String actorEngine$fmtIdentityForLogging() {
        Entity ent = (Entity) (Object) this;
        return "'" + ent.getEncodeId() + "' " + actorEngine$fmtCoordsForLogging();
    }

    @Unique
    public String actorEngine$fmtCoordsForLogging() {
        Entity ent = (Entity) (Object) this;
        return String.format("[x=%.2f, y=%.2f, z=%.2f]", ent.getX(), ent.getY(), ent.getZ());
    }

    @Override
    public @Nullable Actor getActor() {
        return actorEngine$rootHandle == null ? null : actorEngine$rootHandle.getActor();
    }

    @Override
    public @Nullable RootHandle actorEngine$getRootHandle() {
        return actorEngine$rootHandle;
    }

    @Override
    public void actorEngine$setRootHandle(@Nullable RootHandle rootHandle) {
        this.actorEngine$rootHandle = rootHandle;
    }

    @Inject(method = "setRemoved(Lnet/minecraft/world/entity/Entity$RemovalReason;Lorg/bukkit/event/entity/EntityRemoveEvent$Cause;)V", at = @At(value = "INVOKE", target = "Lorg/bukkit/craftbukkit/event/CraftEventFactory;callEntityRemoveEvent(Lnet/minecraft/world/entity/Entity;Lorg/bukkit/event/entity/EntityRemoveEvent$Cause;)V"))
    private void inject_setRemoved(Entity.RemovalReason reason, EntityRemoveEvent.Cause cause, CallbackInfo ci) {
        if (actorEngine$rootHandle != null) {
            actorEngine$rootHandle.handleSetRemoved(reason);
        }
    }

    @Inject(method = "saveWithoutId(Lnet/minecraft/nbt/CompoundTag;Z)Lnet/minecraft/nbt/CompoundTag;", at = @At("TAIL"))
    private void inject_saveWithoutId(CompoundTag nbt, boolean includeAll, CallbackInfoReturnable<CompoundTag> cir) {
        if (actorEngine$rootHandle == null) return;
        if (actorEngine$rootHandle.getRecoveryData() != null) {
            nbt.put(ActorEngine.ID, actorEngine$rootHandle.getRecoveryData());
        } else if (actorEngine$rootHandle != null) {
            Actor actor = actorEngine$rootHandle.getActor();
            if (actor != null) {
                CompoundTag actorData = new CompoundTag();
                actorData.putString("Type", actor.getType().getKey().toString());
                CompoundTag subTag = new CompoundTag();
                actor.save(subTag);
                actorData.put("Data", subTag);
                nbt.put(ActorEngine.ID, actorData);
            }
        }
    }

    @Inject(method = "load", at = @At("TAIL"))
    private void inject_load(CompoundTag nbt, CallbackInfo ci) {
        if (!nbt.contains(ActorEngine.ID)) return;
        ActorEngine actorEngine = ActorEngine.get();
        CompoundTag actorData = nbt.getCompound(ActorEngine.ID);
        String typeString = actorData.getString("Type");
        if (typeString.isEmpty()) {
            actorEngine.getLogger().warn("Removed actor with empty type identifier {}", actorEngine$fmtCoordsForLogging());
            ((Entity) (Object) this).setRemoved(Entity.RemovalReason.DISCARDED);
            return;
        }
        typeString = typeString.toLowerCase(Locale.ENGLISH);
        Key typeId = Key.tryOrNull(typeString);
        if (typeId == null) {
            actorEngine.getLogger().warn("Removed actor with invalid type identifier '{}' {}", typeString, actorEngine$fmtIdentityForLogging());
            ((Entity) (Object) this).setRemoved(Entity.RemovalReason.DISCARDED);
            return;
        }
        this.actorEngine$rootHandle = new RootHandle((Entity) (Object) this);
        ActorType<?> actorType = actorEngine.actors().get(typeId);
        if (actorType == null) {
            if (actorEngine.isRemoveActorsOfANonexistentType()) {
                actorEngine.getLogger().debug("Removed actor with non existent type '{}' {}", typeId, actorEngine$fmtIdentityForLogging());
                ((Entity) (Object) this).setRemoved(Entity.RemovalReason.DISCARDED);
            } else {
                actorEngine$rootHandle.setRecoveryData(actorData);
            }
            return;
        }
        Actor actor = actorType.create(actorEngine$rootHandle.asRoot());
        actorEngine$rootHandle.setActor(actor);
        try {
            actor.assemble(actorData.getCompound("Data"));
        } catch (Throwable e) {
            try {
                actor.destroy();
            } catch (Throwable ignored) {
            }
            actorEngine$rootHandle.setRecoveryData(actorData);
            actorEngine.getLogger().debug("Failed to assemble actor {}", actorEngine$fmtIdentityForLogging(), e);
        }
    }

    @Inject(method = "getDimensions", at = @At("HEAD"), cancellable = true)
    public void inject_getDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if (actorEngine$rootHandle != null) {
            cir.setReturnValue(actorEngine$rootHandle.getDimensions());
        }
    }

    @Inject(method = "shouldBeSaved", at = @At("HEAD"), cancellable = true)
    public void inject_shouldBeSaved(CallbackInfoReturnable<Boolean> cir) {
        if (actorEngine$rootHandle != null && !actorEngine$rootHandle.isShouldBeSaved()) {
            cir.setReturnValue(false);
        }
    }

}
