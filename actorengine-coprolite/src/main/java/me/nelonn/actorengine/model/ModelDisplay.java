package me.nelonn.actorengine.model;

import me.nelonn.actorengine.api.ActorPart;
import me.nelonn.actorengine.api.actor.Actor;
import me.nelonn.bestseat.Seat;
import me.nelonn.bestseat.SeatBehaviour;
import me.nelonn.bestseat.SeatLike;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ModelDisplay extends Display.ItemDisplay implements ActorPart, SeatLike {
    private final SeatBehaviour seat = new SeatBehaviour(this);
    @Nullable private Actor actor;

    public ModelDisplay(EntityType<?> type, Level world) {
        super(type, world);
    }

    public ModelDisplay(Level world) {
        this(EntityType.ITEM_DISPLAY, world);
    }

    @Override
    public boolean canChangeDimensions(Level from, Level to) {
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
    public @Nullable Actor getActor() {
        return actor;
    }

    public void setActor(@Nullable Actor actor) {
        this.actor = actor;
    }

    @Override
    public Seat asSeat() {
        return seat;
    }

    @Override
    protected void addPassenger(Entity passenger) {
        this.seat.addPassenger(passenger, super::addPassenger);
    }

    // Paper only
    @Override
    protected boolean removePassenger(Entity entity, boolean suppressCancellation) {
        return this.seat.removePassenger(entity, suppressCancellation, super::removePassenger);
    }

    @Override
    public @Nullable LivingEntity getControllingPassenger() {
        return this.seat.getControllingPassenger();
    }
}
