package me.nelonn.entitycomposer.mixin;

import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "setRemoved", at = @At(value = "TAIL"))
    private void inject_setRemoved(Entity.RemovalReason reason, CallbackInfo ci) {
        Entity cur = (Entity) (Object) this;
        if (cur instanceof SetRemovedHandler handler) {
            handler.handleSetRemoved(reason);
        }
    }

}
