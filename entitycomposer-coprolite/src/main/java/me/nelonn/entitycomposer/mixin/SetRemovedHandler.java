package me.nelonn.entitycomposer.mixin;

import net.minecraft.world.entity.Entity;

public interface SetRemovedHandler {

    void handleSetRemoved(Entity.RemovalReason reason);

}
