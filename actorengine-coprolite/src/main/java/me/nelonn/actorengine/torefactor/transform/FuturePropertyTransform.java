package me.nelonn.actorengine.torefactor.transform;

import me.nelonn.actorengine.torefactor.variable.VariablesMap;

import java.util.function.Function;

public interface FuturePropertyTransform {

    Property<?> property();

    Object apply(VariablesMap actorProperties);

    static FuturePropertyTransform function(Property<?> property, Function<VariablesMap, ?> supplier) {
        return new FuturePropertyTransform() {
            @Override
            public Property<?> property() {
                return property;
            }

            @Override
            public Object apply(VariablesMap actorProperties) {
                return supplier.apply(actorProperties);
            }
        };
    }

    static FuturePropertyTransform zero(Property<?> property) {
        return function(property, properties -> property.getZero());
    }

}
