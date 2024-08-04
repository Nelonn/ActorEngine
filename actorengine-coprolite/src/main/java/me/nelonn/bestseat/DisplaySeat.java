package me.nelonn.bestseat;

import me.nelonn.actorengine.paper.BukkitEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.entity.CraftItemDisplay;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DisplaySeat extends Display.ItemDisplay implements SeatLike {
    private final BukkitEntity<DisplaySeat, CraftItemDisplay> bukkitEntity = new BukkitEntity<>(this, CraftItemDisplay::new);
    private final SeatBehaviour seat = new SeatBehaviour(this);

    public DisplaySeat(@NotNull EntityType<?> type, @NotNull Level world) {
        super(type, world);
    }

    @Override
    public @NotNull Seat asSeat() {
        return this.seat;
    }

    @Override
    protected void addPassenger(@NotNull Entity passenger) {
        this.seat.addPassenger(passenger, super::addPassenger);
    }

    // Paper only
    @Override
    protected boolean removePassenger(@NotNull Entity entity, boolean suppressCancellation) {
        return this.seat.removePassenger(entity, suppressCancellation, super::removePassenger);
    }

    @Override
    public void tick() {
        this.seat.tick();
    }

    @Override
    public @Nullable LivingEntity getControllingPassenger() {
        return this.seat.getControllingPassenger();
    }

    @Override
    protected void readAdditionalSaveData(@NotNull CompoundTag nbt) {
    }

    @Override
    protected void addAdditionalSaveData(@NotNull CompoundTag nbt) {
    }

    /*@Override
    public @NotNull Vec3 getDismountLocationForPassenger(@NotNull LivingEntity passenger) {
        DismountLocationGetter.Response dismountLocation = DismountLocationGetter.Response.DEFAULT;
        if (dismountLocationGetter != null) {
            Integer position = positionedPassengers.inverse().get(passenger);
            if (position != null) {
                dismountLocation = dismountLocationGetter.getDismountLocation(position, passenger);
            }
        }
        Vec3d position = dismountLocation.position();
        if (!dismountLocation.isAbsolute()) {
            position.add(this.getX(), this.getBoundingBox().maxY, this.getZ());
        }
        return new Vec3(position.x(), position.y(), position.z());
    }*/

    @Override
    protected boolean canAddPassenger(@NotNull Entity passenger) {
        return true;
    }

    @Override
    public void rideTick() {
    }

    @Override
    public boolean canChangeDimensions(@NotNull Level from, @NotNull Level to) {
        return false;
    }

    @Override
    public boolean dismountsUnderwater() {
        return false;
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public @NotNull CraftEntity getBukkitEntity() {
        return this.bukkitEntity.getBukkitEntity();
    }

    @Override
    public @NotNull CraftEntity getBukkitEntityRaw() {
        return this.bukkitEntity.getBukkitEntityRaw();
    }
}
