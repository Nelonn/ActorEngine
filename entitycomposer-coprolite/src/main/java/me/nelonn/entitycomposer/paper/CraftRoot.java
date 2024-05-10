package me.nelonn.entitycomposer.paper;

import me.nelonn.entitycomposer.api.Root;
import me.nelonn.entitycomposer.api.RootLike;
import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.jetbrains.annotations.NotNull;

public class CraftRoot extends CraftEntity implements RootLike {
    public CraftRoot(CraftServer server, Entity entity) {
        super(server, entity);
    }

    @Override
    public @NotNull Root asRoot() {
        return ((RootLike) super.getHandle()).asRoot();
    }
}
