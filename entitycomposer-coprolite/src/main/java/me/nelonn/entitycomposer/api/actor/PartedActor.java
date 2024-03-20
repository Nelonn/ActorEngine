package me.nelonn.entitycomposer.api.actor;

import me.nelonn.entitycomposer.api.Root;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PartedActor extends Actor {
    private final Map<String, Entity> namedParts = new HashMap<>();
    private final Collection<Map.Entry<String, Entity>> namedPartsSet = Collections.unmodifiableCollection(namedParts.entrySet());

    public PartedActor(@NotNull ActorType<?> type, @NotNull Root root) {
        super(type, root);
    }

    public void addNamedPart(@NotNull String name, @NotNull Entity entity) {
        name = name.toLowerCase(Locale.ENGLISH);
        if (name.equals("root")) {
            throw new IllegalArgumentException("You cannot add part with name 'root'");
        }
        namedParts.put(name, entity);
        Entity root = getRoot().asEntity();
        entity.startRiding(root);
        level().addFreshEntity(entity);
    }

    public @Nullable Entity getNamedPart(@NotNull String name) {
        name = name.toLowerCase(Locale.ENGLISH);
        if (name.equals("root")) return getRoot().asEntity();
        return namedParts.get(name);
    }

    public @NotNull Collection<Map.Entry<String, Entity>> getNamedBones() {
        return namedPartsSet;
    }
}
