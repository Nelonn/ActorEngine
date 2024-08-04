package me.nelonn.actorengine.torefactor.animation;

import me.nelonn.actorengine.torefactor.variable.VariablesMap;
import me.nelonn.actorengine.torefactor.AActor;
import org.jetbrains.annotations.NotNull;

public class SimpleAnimationType<T extends Animation> implements AnimationType<T> {
    private final Constructor<T> constructor;

    public SimpleAnimationType(@NotNull Constructor<T> constructor) {
        this.constructor = constructor;
    }

    @Override
    public @NotNull T create(@NotNull AActor actor, @NotNull VariablesMap properties) {
        return constructor.create(this, actor, properties);
    }

    @FunctionalInterface
    public interface Constructor<T extends Animation> {
        @NotNull T create(@NotNull AnimationType<T> type, @NotNull AActor actor, @NotNull VariablesMap properties);
    }
}
