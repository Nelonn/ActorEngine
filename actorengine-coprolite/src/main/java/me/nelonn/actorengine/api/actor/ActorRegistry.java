package me.nelonn.actorengine.api.actor;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import me.nelonn.flint.path.Key;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class ActorRegistry implements Iterable<ActorType<?>> {
    private final BiMap<Key, ActorType<?>> map = HashBiMap.create();

    public ActorRegistry() {
    }

    public void register(Key name, ActorType<?> type) {
        map.put(name, type);
    }

    public @Nullable ActorType<?> unregister(Key name) {
        return map.remove(name);
    }

    public @Nullable ActorType<?> get(Key name) {
        return map.get(name);
    }

    public Optional<ActorType<?>> getOptional(Key name) {
        return Optional.ofNullable(map.get(name));
    }

    public @Nullable Key getKey(ActorType<?> type) {
        return map.inverse().get(type);
    }

    public Optional<Key> getKeyOptional(ActorType<?> type) {
        return Optional.ofNullable(map.inverse().get(type));
    }

    public Set<Key> keySet() {
        return Collections.unmodifiableSet(map.keySet());
    }

    
    @Override
    public Iterator<ActorType<?>> iterator() {
        return Collections.unmodifiableCollection(map.values()).iterator();
    }
}
