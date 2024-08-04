package me.nelonn.bestseat;

import me.nelonn.coprolite.paper.std.registryaccessor.AttributeAccessor;
import me.nelonn.actorengine.paper.BukkitEntity;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import org.bukkit.craftbukkit.entity.CraftArmorStand;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.util.Objects.requireNonNull;

public class ArmorStandSeat extends ArmorStand implements SeatLike {
    private final BukkitEntity<ArmorStandSeat, CraftArmorStand> bukkitEntity = new BukkitEntity<>(this, CraftArmorStand::new);
    private final SeatBehaviour seat = new SeatBehaviour(this);

    public ArmorStandSeat(@NotNull EntityType<? extends ArmorStand> type, @NotNull Level world) {
        super(type, world);
        persist = false;

        setInvisible(true);
        setNoGravity(true);
        setMarker(true);
        setInvulnerable(true);
        setSmall(true);
        setNoBasePlate(true);

        setRot(0, 0);
        yRotO = getYRot();
        setYBodyRot(yRotO);

        requireNonNull(getAttribute(Attributes.MAX_HEALTH)).setBaseValue(1f);
    }

    public void rotate(float yRot) {
        yRotO = getYRot();
        setYRot(yRot);
    }

    @Override
    public @NotNull Seat asSeat() {
        return this.seat;
    }

    @NotNull
    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes();
    }

    @Override
    @Nullable
    public AttributeInstance getAttribute(@NotNull Holder<Attribute> attribute) {
        return AttributeAccessor.getAttribute(this, attribute.value(), ArmorStandSeat::createAttributes);
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
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
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
        return super.getDismountLocationForPassenger(passenger);
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
    public boolean isAffectedByFluids() {
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
    public void remove(@NotNull RemovalReason reason) {
        super.remove(reason);
    }

    public @NotNull CraftEntity getBukkitEntity() {
        return this.bukkitEntity.getBukkitEntity();
    }

    public @NotNull CraftEntity getBukkitEntityRaw() {
        return this.bukkitEntity.getBukkitEntityRaw();
    }
}
