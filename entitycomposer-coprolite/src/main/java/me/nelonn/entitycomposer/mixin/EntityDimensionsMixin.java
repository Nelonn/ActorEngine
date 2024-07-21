package me.nelonn.entitycomposer.mixin;

import me.nelonn.entitycomposer.EntityDimensionsAccessor;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityDimensions.class)
public abstract class EntityDimensionsMixin implements EntityDimensionsAccessor {

    @Unique
    private boolean entityComposer$centered = false;

    @Override
    public void entityComposer$setCentered(boolean centered) {
        this.entityComposer$centered = centered;
    }

    @Inject(method = "makeBoundingBox(DDD)Lnet/minecraft/world/phys/AABB;", at = @At("RETURN"), cancellable = true)
    public void makeBoundingBox(double x, double y, double z, CallbackInfoReturnable<AABB> cir) {
        if (!entityComposer$centered) return;
        AABB aabb = cir.getReturnValue();
        double halfHeight = (aabb.maxY - aabb.minY) / 2;
        cir.setReturnValue(new AABB(
                aabb.minX, aabb.minY - halfHeight, aabb.minZ,
                aabb.maxX, aabb.maxY - halfHeight, aabb.maxZ
        ));
    }

}
