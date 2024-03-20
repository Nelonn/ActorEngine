package me.nelonn.entitycomposer.entity;

import me.nelonn.coprolite.paper.std.registryaccessor.RegistryAccessor;
import me.nelonn.entitycomposer.api.EntityComposer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import org.jetbrains.annotations.NotNull;

public class AllEntities {
    public static final EntityType<RootEntity> ROOT = register("root", EntityType.Builder.of(RootEntity::new, MobCategory.MISC).fireImmune().noSummon().sized(0.0F, 0.0F).clientTrackingRange(0).updateInterval(1), EntityType.MARKER);

    @NotNull
    public static <T extends Entity> EntityType<T> register(@NotNull String id, @NotNull EntityType.Builder<?> type, @NotNull EntityType<?> clientSide) {
        return RegistryAccessor.entityType().register(EntityComposer.ID + ':' + id, type, clientSide);
    }

    public static void register() {
    }

}
