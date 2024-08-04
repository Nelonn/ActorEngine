package me.nelonn.actorengine.utility;

import net.minecraft.world.entity.Entity;

public interface SetRemovedHandler {

    void handleSetRemoved(Entity.RemovalReason reason);

}
