package me.nelonn.entitycomposer.api.actor;

import me.nelonn.bestvecs.MutVec3d;
import me.nelonn.entitycomposer.api.Root;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Actor {
    private final ActorType<?> type;
    protected final Root root; // protected to optimize the use of the get root method
    private boolean shouldCallBeginPlay = true;

    public Actor(@NotNull ActorType<?> type, @NotNull Root root) {
        this.type = type;
        this.root = root;
    }

    public void assemble(@Nullable CompoundTag nbt) {
        preLoadInitialize();
        if (nbt != null) {
            load(nbt);
        }
        postLoadInitialize();
        compose();
    }

    /**
     * The name of the method reflects its essence
     */
    public void preLoadInitialize() {
    }

    public void save(@NotNull CompoundTag nbt) {
    }

    public void load(@NotNull CompoundTag nbt) {
    }

    /**
     * The name of the method reflects its essence
     */
    public void postLoadInitialize() {
    }

    /**
     * This method is usually used to teleport child entities to an actor
     */
    public void compose() {
    }

    /**
     * It can be redefined if the actor has some kind of movement grid
     * @param x global x coordinate
     * @param y global y coordinate
     * @param z global z coordinate
     */
    public void moveTo(double x, double y, double z) {
        ((Entity) root.asEntity()).moveTo(x, y, z);
        compose();
    }

    /**
     * Called on the first tick
     */
    public void beginPlay() {
    }

    public void tick() {
        if (shouldCallBeginPlay) {
            shouldCallBeginPlay = false;
            beginPlay();
        }
    }

    public void remove(@NotNull Entity.RemovalReason reason) {
        root.asEntity().setRemoved(reason);
    }

    public void onRootRemoved(@NotNull Entity.RemovalReason reason) {
        discard();
    }

    public void discard() {
    }

    public @NotNull Level level() {
        return this.root.level();
    }

    public @NotNull MutVec3d position() {
        return this.root.position();
    }

    public @NotNull ActorType<?> getType() {
        return this.type;
    }

    public @NotNull Root getRoot() {
        return this.root;
    }

    @Override
    public String toString() {
        return this.type.getKey().toString();
    }
}
