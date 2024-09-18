package me.nelonn.actorengine.entity;

import me.nelonn.actorengine.api.ActorEngine;
import me.nelonn.coprolite.paper.std.registryaccessor.RegistryAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class AllEntities {
    public static final EntityType<RootEntity> ROOT = register("root", EntityType.Builder.of(RootEntity::new, MobCategory.MISC).fireImmune().noSummon().sized(0.0F, 0.0F).clientTrackingRange(0).updateInterval(Integer.MAX_VALUE), EntityType.MARKER);

    public static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<?> type, EntityType<?> dataFixerAnalog) {
        return RegistryAccessor.entityType().register(ActorEngine.ID + ':' + id, type, dataFixerAnalog);
    }

    public static void register() {
    }

}
