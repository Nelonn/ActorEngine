package me.nelonn.actorengine.utility;

import com.google.common.collect.ImmutableMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class Enum<T> {
    private final Class<T> type;
    private final Map<T, Entry> entries;
    private final Set<T> values;

    public Enum(Class<T> type) {
        this.type = type;
        int ordinal = 0;
        ImmutableMap.Builder<T, Entry> builder = new ImmutableMap.Builder<>();
        for (Field field : type.getDeclaredFields()) {
            int modifiers = field.getModifiers();
            if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers) || !Modifier.isFinal(modifiers) ||
                    !type.isAssignableFrom(field.getType())) continue;
            try {
                T instance = (T) field.get(null);
                builder.put(instance, new Entry(field.getName(), ordinal++));
            } catch (Exception e) {
                throw new RuntimeException("Enum failed", e);
            }
        }
        entries = builder.build();
        values = Collections.unmodifiableSet(entries.keySet());
    }

    public String name(T instance) {
        return entries.get(instance).name();
    }

    public int ordinal(T instance) {
        return entries.get(instance).ordinal();
    }

    public Set<T> values() {
        return values;
    }

    public static class Entry {
        private final String name;

        
        public final String name() {
            return name;
        }

        private final int ordinal;

        @Range(from = 0, to = Integer.MAX_VALUE)
        public final int ordinal() {
            return ordinal;
        }

        public Entry(String name, int ordinal) {
            this.name = name;
            this.ordinal = ordinal;
        }

        public String toString() {
            return name;
        }

        public final boolean equals(Object other) {
            return this == other;
        }

        public final int hashCode() {
            return super.hashCode();
        }

        protected final Object clone() throws CloneNotSupportedException {
            throw new CloneNotSupportedException();
        }
    }

}
