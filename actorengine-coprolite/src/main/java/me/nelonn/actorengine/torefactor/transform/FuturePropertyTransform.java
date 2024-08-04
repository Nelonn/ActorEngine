package me.nelonn.actorengine.torefactor.transform;

import me.nelonn.actorengine.torefactor.variable.VariablesMap;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public interface FuturePropertyTransform {

    @NotNull
    Property<?> property();

    @NotNull Object apply(@NotNull VariablesMap actorProperties);

    static @NotNull FuturePropertyTransform function(@NotNull Property<?> property, @NotNull Function<VariablesMap, ?> supplier) {
        return new FuturePropertyTransform() {
            @Override
            public @NotNull Property<?> property() {
                return property;
            }

            @Override
            public @NotNull Object apply(@NotNull VariablesMap actorProperties) {
                return supplier.apply(actorProperties);
            }
        };
    }

    static @NotNull FuturePropertyTransform zero(@NotNull Property<?> property) {
        return function(property, properties -> property.getZero());
    }

}
