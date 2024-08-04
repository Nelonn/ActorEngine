package me.nelonn.actorengine.torefactor.variable;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FlexibleVariablesMap implements VariablesMap {
    private final Map<String, PropertyContainer<?>> map = new HashMap<>();

    private <T> void add(@NotNull String key, @NotNull PropertyContainer<T> container) {
        map.put(key, container);
    }

    public <T> void add(@NotNull String key, @NotNull T value, @NotNull Class<T> type) {
        add(key, new PropertyObject<>(type, value));
    }

    public <T> void add(@NotNull VariableKey<T> property, @NotNull T value) {
        add(property.getName(), value, property.getType());
    }

    public <T> void add(@NotNull String key, @NotNull Supplier<T> supplier, @NotNull Class<T> type) {
        add(key, new PropertyStream<>(type, supplier));
    }

    public <T> void add(@NotNull VariableKey<T> property, @NotNull Supplier<T> supplier) {
        add(property.getName(), supplier, property.getType());
    }

    public boolean remove(@NotNull String key) {
        return map.remove(key) != null;
    }

    public boolean remove(@NotNull VariableKey<?> property) {
        return remove(property.getName());
    }

    @Override
    public <T> @NotNull T get(@NotNull VariableKey<T> property) {
        PropertyContainer<?> container = map.get(property.getName());
        if (container == null) return property.getDefaultValue();
        Class<T> propertyType = property.getType();
        if (!propertyType.isAssignableFrom(container.getType())) return property.getDefaultValue();
        Object value = container.getValue();
        if (!propertyType.isInstance(value)) return property.getDefaultValue();
        return propertyType.cast(value);
    }

    public interface PropertyContainer<T> {
        @NotNull Class<T> getType();

        @NotNull T getValue();
    }

    public static class PropertyObject<T> implements PropertyContainer<T> {
        private final Class<T> type;
        private final T value;

        public PropertyObject(@NotNull Class<T> type, @NotNull T value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public @NotNull Class<T> getType() {
            return type;
        }

        @Override
        public @NotNull T getValue() {
            return value;
        }
    }

    public static class PropertyStream<T> implements PropertyContainer<T> {
        private final Class<T> type;
        private final Supplier<T> supplier;

        public PropertyStream(@NotNull Class<T> type, @NotNull Supplier<T> supplier) {
            this.type = type;
            this.supplier = supplier;
        }

        @Override
        public @NotNull Class<T> getType() {
            return type;
        }

        @Override
        public @NotNull T getValue() {
            return supplier.get();
        }
    }
}
