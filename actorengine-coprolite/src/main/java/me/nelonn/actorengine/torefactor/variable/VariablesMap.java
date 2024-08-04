package me.nelonn.actorengine.torefactor.variable;

import org.jetbrains.annotations.NotNull;

public interface VariablesMap {
    <T> @NotNull T get(@NotNull VariableKey<T> property);

    VariablesMap EMPTY = new VariablesMap() {
        @NotNull
        @Override
        public <T> T get(@NotNull VariableKey<T> property) {
            throw new UnsupportedOperationException();
        }
    };
}
