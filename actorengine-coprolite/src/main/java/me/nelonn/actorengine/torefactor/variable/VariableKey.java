package me.nelonn.actorengine.torefactor.variable;

public class VariableKey<T> {
    private final String name;
    private final Class<T> type;
    private final T defaultValue;

    public VariableKey(String name, Class<T> type, final T defaultValue) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    public Class<T> getType() {
        return type;
    }

    public T getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String toString() {
        return name + '[' + type + ", " + defaultValue + ']';
    }
}
