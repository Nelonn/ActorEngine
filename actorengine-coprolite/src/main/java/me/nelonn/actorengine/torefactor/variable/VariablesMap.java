package me.nelonn.actorengine.torefactor.variable;

public interface VariablesMap {
    <T> T get(VariableKey<T> property);

    VariablesMap EMPTY = new VariablesMap() {
        
        @Override
        public <T> T get(VariableKey<T> property) {
            throw new UnsupportedOperationException();
        }
    };
}
