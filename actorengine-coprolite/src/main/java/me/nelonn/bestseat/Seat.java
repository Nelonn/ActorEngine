package me.nelonn.bestseat;

import me.nelonn.bestvecs.ImmVec3d;
import me.nelonn.bestvecs.Vec3d;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.UUID;

public interface Seat extends SeatLike {

    @Nullable Entity getPassenger(int position);

    void sit(int position, Entity entity);

    @Nullable
    default DriverInput getDriverInput(int position) {
        Entity entity = getPassenger(position);
        if (!(entity instanceof LivingEntity livingEntity)) return null;
        return DriverInput.obtain(livingEntity, isLastDismount(position));
    }

    boolean isLastDismount(int position);

    void setLastDismount(UUID playerId, boolean lastDismount);

    boolean isAllPreventDismount();

    void setAllPreventDismount(boolean allPreventDismount);

    void setPreventDismount(UUID playerId, boolean preventDismount);

    boolean isPreventDismount(UUID playerId);

    @Nullable DismountLocationGetter getDismountLocationGetter();

    void setDismountLocationGetter(@Nullable DismountLocationGetter dismountLocationGetter);

    @Nullable EventListener getEventListener();

    void setEventListener(@Nullable EventListener eventListener);

    @Nullable Runnable getTickListener();

    void setTickListener(@Nullable Runnable tickListener);

    @Override
    default Seat asSeat() {
        return this;
    }

    interface EventListener {

        default void passengerAdded(Entity entity) {
        }

        default void passengerAddedToPosition(@Nullable Integer position, Entity entity) {
        }

        default void passengerRemoved(@Nullable Integer position, Entity entity) {
        }

    }

    @FunctionalInterface
    interface DismountLocationGetter {
        Response getDismountLocation(int position, Entity entity);

        record Response(Vec3d position, boolean isAbsolute) {
            public static final Response DEFAULT = new Response(ImmVec3d.ZERO, false);
        }
    }
}
