package me.nelonn.entitycomposer.paper;

import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiFunction;

public class BukkitEntity<M extends Entity, B extends CraftEntity> {
    private final M handle;
    private final BiFunction<CraftServer, M, B> factory;
    private B value;

    public BukkitEntity(@NotNull M handle, @NotNull BiFunction<CraftServer, M, B> factory) {
        this.handle = handle;
        this.factory = factory;
    }

    public @NotNull B getBukkitEntity() {
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
