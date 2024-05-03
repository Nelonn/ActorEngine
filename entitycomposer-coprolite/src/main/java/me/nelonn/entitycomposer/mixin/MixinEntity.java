package me.nelonn.entitycomposer.mixin;

import net.minecraft.world.entity.Entity;
import org.bukkit.event.entity.EntityRemoveEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "setRemoved(Lnet/minecraft/world/entity/Entity$RemovalReason;Lorg/bukkit/event/entity/EntityRemoveEvent$Cause;)V", at = @At(value = "INVOKE", target = "Lorg/bukkit/craftbukkit/v1_20_R3/event/CraftEventFactory;callEntityRemoveEvent(Lnet/minecraft/world/entity/Entity;Lorg/bukkit/event/entity/EntityRemoveEvent$Cause;)V"))
    private void inject_setRemoved(Entity.RemovalReason reason, EntityRemoveEvent.Cause cause, CallbackInfo ci) {
        Entity cur = (Entity) (Object) this;
        if (cur instanceof SetRemovedHandler handler) {
            handler.handleSetRemoved(reason);
        }
    }

}
