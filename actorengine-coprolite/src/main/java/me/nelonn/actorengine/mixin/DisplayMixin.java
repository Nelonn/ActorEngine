package me.nelonn.actorengine.mixin;

import me.nelonn.actorengine.api.ActorPart;
import me.nelonn.actorengine.api.actor.Actor;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Display.class)
public class DisplayMixin {

    @Inject(method = "tick", at = @At("HEAD"))
    private void inject_tick(CallbackInfo ci) {
        @Nullable Actor actor = ((ActorPart) (Object) this).getActor();
        if (actor == null) return;
        if (actor.getRoot().asEntity() != ((Entity) (Object) this)) return;
        actor.tick();
    }

}
