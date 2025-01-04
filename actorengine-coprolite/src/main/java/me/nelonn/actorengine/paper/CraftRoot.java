package me.nelonn.actorengine.paper;

import me.nelonn.actorengine.api.Root;
import me.nelonn.actorengine.api.RootLike;
import net.minecraft.world.entity.Entity;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftEntity;
import org.jetbrains.annotations.NotNull;

public class CraftRoot extends CraftEntity implements RootLike {
    public CraftRoot(CraftServer server, Entity entity) {
        super(server, entity);
    }

    @Override
    public Root asRoot() {
        return ((RootLike) super.getHandle()).asRoot();
    }
}
