package me.nelonn.actorengine.torefactor.transform;

import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FutureBoneTransform {
    private final Map<Property<?>, FuturePropertyTransform> map = new HashMap<>();
    private final Set<Property<?>> affects = Collections.unmodifiableSet(map.keySet());

    public FutureBoneTransform() {
    }

    public void add(FuturePropertyTransform propertyTransform) {
        map.put(propertyTransform.property(), propertyTransform);
    }

    public Set<Property<?>> affects() {
        return affects;
    }

    public @Nullable FuturePropertyTransform getTransform(Property<?> property) {
        return map.get(property);
    }
}
