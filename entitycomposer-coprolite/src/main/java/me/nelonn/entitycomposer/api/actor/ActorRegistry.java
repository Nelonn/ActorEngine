package me.nelonn.entitycomposer.api.actor;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.nelonn.flint.path.Key;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class ActorRegistry implements Iterable<ActorType<?>> {
    private final BiMap<Key, ActorType<?>> map = HashBiMap.create();

    public ActorRegistry() {
    }

    public void register(@NotNull Key name, @NotNull ActorType<?> type) {
        map.put(name, type);
    }

    public @Nullable ActorType<?> unregister(@NotNull Key name) {
        return map.remove(name);
    }

    public @Nullable ActorType<?> get(@NotNull Key name) {
        return map.get(name);
    }

    public @NotNull Optional<ActorType<?>> getOptional(@NotNull Key name) {
        return Optional.ofNullable(map.get(name));
    }

    public @Nullable Key getKey(@NotNull ActorType<?> type) {
        return map.inverse().get(type);
    }

    public @NotNull Optional<Key> getKeyOptional(@NotNull ActorType<?> type) {
        return Optional.ofNullable(map.inverse().get(type));
    }

    public @NotNull Set<Key> keySet() {
        return Collections.unmodifiableSet(map.keySet());
    }

    @NotNull
    @Override
    public Iterator<ActorType<?>> iterator() {
        return Collections.unmodifiableCollection(map.values()).iterator();
    }
}
