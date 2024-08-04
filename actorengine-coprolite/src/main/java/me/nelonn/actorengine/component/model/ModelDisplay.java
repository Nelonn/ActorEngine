package me.nelonn.actorengine.component.model;

import me.nelonn.actorengine.api.ActorPart;
import me.nelonn.actorengine.api.actor.Actor;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModelDisplay extends Display.ItemDisplay implements ActorPart {
    private Actor actor;

    public ModelDisplay(EntityType<?> type, Level world) {
        super(type, world);
    }

    public ModelDisplay(Level world) {
        this(EntityType.ITEM_DISPLAY, world);
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public boolean canChangeDimensions(@NotNull Level from, @NotNull Level to) {
        return false;
    }

    @Override
    public @Nullable Actor getActor() {
        return actor;
    }

    public void setActor(@Nullable Actor actor) {
        this.actor = actor;
    }
}
