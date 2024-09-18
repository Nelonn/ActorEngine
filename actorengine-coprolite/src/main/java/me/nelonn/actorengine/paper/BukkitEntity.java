package me.nelonn.actorengine.paper;

import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;

public class BukkitEntity<M extends Entity, B extends CraftEntity> {
    private final M handle;
    private final BiFunction<CraftServer, M, B> factory;
    @Nullable private B value;

    public BukkitEntity(M handle, BiFunction<CraftServer, M, B> factory) {
        this.handle = handle;
        this.factory = factory;
    }

    public B getBukkitEntity() {
        if (this.value == null) {
            synchronized (this) {
                if (this.value == null) {
                    try {
                        this.value = factory.apply(handle.level().getCraftServer(), handle);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return this.value;
    }

    public B getBukkitEntityRaw() {
        return value;
    }

}
