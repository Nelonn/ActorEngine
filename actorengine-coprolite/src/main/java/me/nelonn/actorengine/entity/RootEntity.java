package me.nelonn.actorengine.entity;

import me.nelonn.actorengine.api.ActorEngine;
import me.nelonn.actorengine.api.Root;
import me.nelonn.actorengine.api.RootLike;
import me.nelonn.actorengine.api.actor.Actor;
import me.nelonn.actorengine.api.actor.ActorType;
import me.nelonn.actorengine.utility.SetRemovedHandler;
import me.nelonn.actorengine.paper.BukkitEntity;
import me.nelonn.actorengine.paper.CraftRoot;
import me.nelonn.flint.path.Key;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class RootEntity extends Entity implements RootLike, SetRemovedHandler {
    public static final boolean configVisibleByDefault = false;
    private final BukkitEntity<RootEntity, CraftRoot> bukkitEntity = new BukkitEntity<>(this, CraftRoot::new); // Paper
    private final ActorEngine actorEngine;
    private Actor actor;
    private EntityDimensions dimensions = EntityDimensions.scalable(0.0F, 0.0F);
    private boolean shouldBeSaved = true;
    private CompoundTag savedNbt;
    private final RootDelegate root;

    public RootEntity(EntityType<?> type, Level world) {
        super(type, world);
        this.actorEngine = ActorEngine.get();
        this.visibleByDefault = configVisibleByDefault;
        this.root = new RootDelegate(this);
    }

    public RootEntity(Level world) {
        this(AllEntities.ROOT, world);
    }

    public @Nullable Actor getOwner() {
        return actor;
    }

    public void setOwner(@Nullable Actor owner) {
        this.actor = owner;
    }

    public @NotNull EntityDimensions getDimensions() {
        return dimensions;
    }

    public void setDimensions(@NotNull EntityDimensions dimensions) {
        this.dimensions = dimensions;
        this.refreshDimensions();
    }

    public @NotNull ActorEngine getActorEngine() {
        return actorEngine;
    }

    public @Nullable Actor getActor() {
        return this.actor;
    }

    public void setActor(@Nullable Actor actor) {
        this.actor = actor;
    }

    @Override
    public @NotNull Root asRoot() {
        return root;
    }

    // Paper/Bukkit start
    @Override
    public @NotNull CraftEntity getBukkitEntity() {
        return this.bukkitEntity.getBukkitEntity();
    }

    @Override
    public @NotNull CraftEntity getBukkitEntityRaw() {
        return this.bukkitEntity.getBukkitEntityRaw();
    }
    // Paper/Bukkit end

    @Override
    protected void defineSynchedData(@NotNull SynchedEntityData.Builder builder) {
    }

    @Override
    public boolean save(@NotNull CompoundTag nbt) {
        boolean result = false;
        try {
            result = super.save(nbt);
        } catch (Throwable e) {
            actorEngine.getLogger().error("Failed to save '" + this.getEncodeId() + "'", e);
        }
        return result;
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        if (this.actor == null) {
            if (this.savedNbt != null) {
                nbt.merge(this.savedNbt);
            }
            return;
        }
        nbt.putString(ActorEngine.ID + ".ActorType", this.actor.getType().getKey().toString());
        CompoundTag subTag = new CompoundTag();
        this.actor.save(subTag);
        nbt.put(ActorEngine.ID + ".ActorData", subTag);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        try {
            String typeString = nbt.getString(ActorEngine.ID + ".ActorType");
            if (typeString.isEmpty()) {
                actorEngine.getLogger().warn(String.format("Removed actor with empty type identifier [x=%.2f, y=%.2f, z=%.2f]", getX(), getY(), getZ()));
                setRemoved(RemovalReason.DISCARDED);
                return;
            }
            typeString = typeString.toLowerCase(Locale.ENGLISH);
            Key typeId = Key.tryOrNull(typeString);
            if (typeId == null) {
                actorEngine.getLogger().warn(String.format("Removed actor with invalid type identifier '%s' [x=%.2f, y=%.2f, z=%.2f]", typeString, getX(), getY(), getZ()));
                setRemoved(RemovalReason.DISCARDED);
                return;
            }
            ActorType<?> actorType = actorEngine.actors().get(typeId);
            if (actorType == null) {
                if (actorEngine.isRemoveActorsOfANonexistentType()) {
                    actorEngine.getLogger().debug(String.format("Removed actor with non existent type '%s' [x=%.2f, y=%.2f, z=%.2f]", typeId, getX(), getY(), getZ()));
                    setRemoved(RemovalReason.DISCARDED);
                } else {
                    this.savedNbt = nbt;
                }
                return;
            }
            this.actor = actorType.create(asRoot());
            this.actor.assemble(nbt.getCompound(ActorEngine.ID + ".ActorData"));
        } catch (Throwable e) {
            savedNbt = nbt;
            actorEngine.getLogger().warn(String.format("Failed to load '%s' [x=%.2f, y=%.2f, z=%.2f]", getEncodeId(), getX(), getY(), getZ()), e);
        }
    }

    @Override
    public void tick() {
        if (this.actor != null) {
            this.actor.tick();
        }
    }

    /**
     * called by MixinEntity
     */
    @ApiStatus.Internal
    public void handleSetRemoved(@NotNull RemovalReason reason) {
        if (this.actor != null) {
            this.actor.onRootRemoved(reason);
        }
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket(@NotNull ServerEntity entityTrackerEntry) {
        throw new IllegalStateException("Root entity should never be sent");
    }

    @Override
    public @NotNull EntityDimensions getDimensions(@NotNull Pose pose) {
        return dimensions;
    }

    @Override
    public boolean isAlwaysTicking() {
        return true;
    }

    public boolean isShouldBeSaved() {
        return shouldBeSaved;
    }

    public void setShouldBeSaved(boolean shouldBeSaved) {
        this.shouldBeSaved = shouldBeSaved;
    }

    @Override
    public boolean shouldBeSaved() {
        if (!shouldBeSaved) return false;
        return super.shouldBeSaved();
    }
}
