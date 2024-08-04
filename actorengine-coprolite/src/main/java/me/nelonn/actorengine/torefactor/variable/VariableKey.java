package me.nelonn.actorengine.torefactor.variable;

import org.jetbrains.annotations.NotNull;

public class VariableKey<T> {
    private final String name;
    private final Class<T> type;
    private final T defaultValue;

    public VariableKey(@NotNull String name, @NotNull Class<T> type, @NotNull final T defaultValue) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull Class<T> getType() {
        return type;
    }

    public @NotNull T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String toString() {
        return name + '[' + type + ", " + defaultValue + ']';
    }
}
