package me.nelonn.bestseat;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class SeatBehaviour implements Seat {
    private final Entity handle;
    private final BiMap<Integer, Entity> positionedPassengers = HashBiMap.create(1);
    private boolean allPreventDismount = false;
    private final Map<UUID, DismountState> perPlayerDismount = new HashMap<>();
    @Nullable private Seat.DismountLocationGetter dismountLocationGetter = null;
    @Nullable private Seat.EventListener eventListener = null;
    @Nullable private Runnable tickListener = null;

    public SeatBehaviour(Entity handle) {
        this.handle = handle;
    }

    @Override
    public @Nullable Entity getPassenger(int position) {
        return positionedPassengers.get(position);
    }

    @Override
    public void sit(int position, Entity entity) {
        if (entity.startRiding(this.handle, true)) {
            positionedPassengers.put(position, entity);
            if (eventListener != null) {
                eventListener.passengerAddedToPosition(position, entity);
            }
        }
    }

    @Override
    public boolean isLastDismount(int position) {
        Entity entity = positionedPassengers.get(position);
        if (entity instanceof ServerPlayer player) {
            me.nelonn.bestseat.DismountState state = perPlayerDismount.get(player.getUUID());
            if (state != null) {
                return state.isLastDismount();
            }
        }
        return false;
    }

    @Override
    public void setLastDismount(UUID playerId, boolean lastDismount) {
        this.perPlayerDismount.computeIfAbsent(playerId, unused -> new me.nelonn.bestseat.DismountState()).setLastDismount(lastDismount);
    }

    @Override
    public boolean isAllPreventDismount() {
        return this.allPreventDismount;
    }

    @Override
    public void setAllPreventDismount(boolean allPreventDismount) {
        this.allPreventDismount = allPreventDismount;
    }

    @Override
    public void setPreventDismount(UUID playerId, boolean preventDismount) {
        if (preventDismount) {
            me.nelonn.bestseat.DismountState state = this.perPlayerDismount.computeIfAbsent(playerId, unused -> new me.nelonn.bestseat.DismountState());
            state.setPreventDismount(true);
        } else {
            if (!allPreventDismount) {
                this.perPlayerDismount.remove(playerId);
            } else {
                DismountState state = this.perPlayerDismount.get(playerId);
                if (state != null) {
                    state.setPreventDismount(false);
                }
            }
        }
    }

    @Override
    public boolean isPreventDismount(UUID playerId) {
        return isAllPreventDismount() || perPlayerDismount.containsKey(playerId);
    }

    @Override
    public @Nullable DismountLocationGetter getDismountLocationGetter() {
        return this.dismountLocationGetter;
    }

    @Override
    public void setDismountLocationGetter(@Nullable Seat.DismountLocationGetter dismountLocationGetter) {
        this.dismountLocationGetter = dismountLocationGetter;
    }

    @Override
    public @Nullable EventListener getEventListener() {
        return this.eventListener;
    }

    @Override
    public void setEventListener(@Nullable Seat.EventListener eventListener) {
        this.eventListener = eventListener;
    }

    @Override
    public @Nullable Runnable getTickListener() {
        return this.tickListener;
    }

    @Override
    public void setTickListener(@Nullable Runnable tickListener) {
        this.tickListener = tickListener;
    }

    public void addPassenger(Entity passenger, Consumer<Entity> _super) {
        _super.accept(passenger);
        if (eventListener != null) {
            eventListener.passengerAdded(passenger);
        }
    }

    // Paper only
    public boolean removePassenger(Entity entity, boolean suppressCancellation, BiFunction<Entity, Boolean, Boolean> _super) {
        if (_super.apply(entity, suppressCancellation)) {
            Integer position = positionedPassengers.inverse().remove(entity);
            if (eventListener != null) {
                eventListener.passengerRemoved(position, entity);
            }
            return true;
        }
        return false;
    }

    public void tick() {
        if (this.tickListener != null) {
            this.tickListener.run();
        }
    }

    public @Nullable LivingEntity getControllingPassenger() {
        return getPassenger(0) instanceof LivingEntity livingEntity ? livingEntity : null;
    }
}
