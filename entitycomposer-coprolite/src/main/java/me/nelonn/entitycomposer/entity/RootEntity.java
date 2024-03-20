package me.nelonn.entitycomposer.entity;

import me.nelonn.entitycomposer.api.EntityComposer;
import me.nelonn.entitycomposer.api.Root;
import me.nelonn.entitycomposer.api.RootLike;
import me.nelonn.entitycomposer.api.actor.Actor;
import me.nelonn.entitycomposer.api.actor.ActorType;
import me.nelonn.entitycomposer.mixin.SetRemovedHandler;
import me.nelonn.entitycomposer.paper.BukkitEntity;
import me.nelonn.entitycomposer.paper.CraftRoot;
import me.nelonn.flint.path.Key;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public class RootEntity extends Entity implements RootLike, SetRemovedHandler {
    public static final boolean configVisibleByDefault = false;
    private final BukkitEntity<RootEntity, CraftRoot> bukkitEntity = new BukkitEntity<>(this, CraftRoot::new); // Paper
    private final EntityComposer entityComposer;
    private Actor actor;
    private EntityDimensions dimensions = EntityDimensions.scalable(0.0F, 0.0F);
    private boolean shouldBeSaved = true;
    private CompoundTag nbt;
    private final RootDelegate root;

    public RootEntity(EntityType<?> type, Level world) {
        super(type, world);
        this.entityComposer = EntityComposer.get();
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

    public @NotNull EntityComposer getEntityComposer() {
        return entityComposer;
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
    protected void defineSynchedData() {
    }

    @Override
    public boolean save(@NotNull CompoundTag nbt) {
        boolean result = false;
        try {
            result = super.save(nbt);
        } catch (Throwable e) {
            entityComposer.getLogger().error("Failed to save '" + this.getEncodeId() + "'", e);
        }
        return result;
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        if (this.actor == null) {
            if (this.nbt != null) {
                nbt.merge(this.nbt);
            }
            return;
        }
        nbt.putString("EntityComposer.ActorType", this.actor.getType().getKey().toString());
        CompoundTag subTag = new CompoundTag();
        this.actor.save(subTag);
        nbt.put("EntityComposer.ActorData", subTag);
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        try {
            String typeString = nbt.getString("EntityComposer.ActorType");
            if (typeString.isEmpty()) {
                entityComposer.getLogger().warn(String.format("Removed actor with empty type identifier [x=%.2f, y=%.2f, z=%.2f]", getX(), getY(), getZ()));
                setRemoved(RemovalReason.DISCARDED);
                return;
            }
            typeString = typeString.toLowerCase(Locale.ENGLISH);
            Key typeId = Key.tryOrNull(typeString);
            if (typeId == null) {
                entityComposer.getLogger().warn(String.format("Removed actor with invalid type identifier '%s' [x=%.2f, y=%.2f, z=%.2f]", typeString, getX(), getY(), getZ()));
                setRemoved(RemovalReason.DISCARDED);
                return;
            }
            ActorType<?> actorType = entityComposer.actors().get(typeId);
            if (actorType == null) {
                if (entityComposer.shouldRemoveNonExistentActors()) {
                    entityComposer.getLogger().debug(String.format("Removed actor with non existent type '%s' [x=%.2f, y=%.2f, z=%.2f]", typeId, getX(), getY(), getZ()));
                    setRemoved(RemovalReason.DISCARDED);
                } else {
                    this.nbt = nbt;
                }
                return;
            }
            this.actor = actorType.create(asRoot());
            this.actor.assemble(nbt.getCompound("EntityComposer.ActorData"));
        } catch (Throwable e) {
            entityComposer.getLogger().warn(String.format("Failed to load '%s' [x=%.2f, y=%.2f, z=%.2f]", getEncodeId(), getX(), getY(), getZ()), e);
        }
    }

    @Override
    public void tick() {
        if (this.actor != null) {
            this.actor.tick();
        }
    }

    // called by MixinEntity
    public void handleSetRemoved(@NotNull RemovalReason reason) {
        if (this.actor != null) {
            this.actor.onRootRemoved(reason);
        }
    }

    @Override
    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        throw new IllegalStateException("Root entities should never be sent");
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
