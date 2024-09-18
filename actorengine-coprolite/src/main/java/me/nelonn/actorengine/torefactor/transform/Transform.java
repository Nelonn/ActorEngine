package me.nelonn.actorengine.torefactor.transform;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class Transform {
    private final Map<Property<?>, Object> map = new HashMap<>();

    public <T> @Nullable T getRaw(Property<?> property) {
        return (T) map.get(property);
    }

    public <T> @Nullable T get(Property<T> property) {
        return getRaw(property);
    }

    public <T> void setRaw(Property<?> property, T value) {
        map.put(property, value);
    }

    public <T> void set(Property<T> property, T value) {
        setRaw(property, value);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public Set<Property<?>> keySet() {
        return map.keySet();
    }

    public Transform copy() {
        Transform transform = new Transform();
        transform.map.putAll(map);
        return transform;
    }

    public void add(Transform other) {
        for (Map.Entry<Property<?>, Object> entry : other.map.entrySet()) {
            Property<?> key = entry.getKey();
            Object value = entry.getValue();
            if (map.containsKey(key)) {
                Object existing = map.get(key);
                map.put(key, key.getOperators().add(existing, value));
            } else {
                map.put(key, value);
            }
        }
    }

    public void lerp(Transform other, float delta) {
        for (Map.Entry<Property<?>, Object> entry : other.map.entrySet()) {
            Property<?> key = entry.getKey();
            Object value = entry.getValue();

            if (map.containsKey(key)) {
                Object existing = map.get(key);
                map.put(key, key.getOperators().lerp(existing, value, delta));
            } else {
                map.put(key, value);
            }
        }
    }
}
