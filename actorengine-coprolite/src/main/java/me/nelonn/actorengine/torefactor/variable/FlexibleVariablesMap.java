package me.nelonn.actorengine.torefactor.variable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class FlexibleVariablesMap implements VariablesMap {
    private final Map<String, PropertyContainer<?>> map = new HashMap<>();

    private <T> void add(String key, PropertyContainer<T> container) {
        map.put(key, container);
    }

    public <T> void add(String key, T value, Class<T> type) {
        add(key, new PropertyObject<>(type, value));
    }

    public <T> void add(VariableKey<T> property, T value) {
        add(property.getName(), value, property.getType());
    }

    public <T> void add(String key, Supplier<T> supplier, Class<T> type) {
        add(key, new PropertyStream<>(type, supplier));
    }

    public <T> void add(VariableKey<T> property, Supplier<T> supplier) {
        add(property.getName(), supplier, property.getType());
    }

    public boolean remove(String key) {
        return map.remove(key) != null;
    }

    public boolean remove(VariableKey<?> property) {
        return remove(property.getName());
    }

    @Override
    public <T> T get(VariableKey<T> property) {
        PropertyContainer<?> container = map.get(property.getName());
        if (container == null) return property.getDefaultValue();
        Class<T> propertyType = property.getType();
        if (!propertyType.isAssignableFrom(container.getType())) return property.getDefaultValue();
        Object value = container.getValue();
        if (!propertyType.isInstance(value)) return property.getDefaultValue();
        return propertyType.cast(value);
    }

    public interface PropertyContainer<T> {
        Class<T> getType();

        T getValue();
    }

    public static class PropertyObject<T> implements PropertyContainer<T> {
        private final Class<T> type;
        private final T value;

        public PropertyObject(Class<T> type, T value) {
            this.type = type;
            this.value = value;
        }

        @Override
        public Class<T> getType() {
            return type;
        }

        @Override
        public T getValue() {
            return value;
        }
    }

    public static class PropertyStream<T> implements PropertyContainer<T> {
        private final Class<T> type;
        private final Supplier<T> supplier;

        public PropertyStream(Class<T> type, Supplier<T> supplier) {
            this.type = type;
            this.supplier = supplier;
        }

        @Override
        public Class<T> getType() {
            return type;
        }

        @Override
        public T getValue() {
            return supplier.get();
        }
    }
}
