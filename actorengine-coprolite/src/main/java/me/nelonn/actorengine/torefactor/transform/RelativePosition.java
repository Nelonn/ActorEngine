package me.nelonn.actorengine.torefactor.transform;

import me.nelonn.actorengine.torefactor.variable.VariablesMap;
import me.nelonn.bestvecs.Vec3d;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RelativePosition {

    @NotNull Vec3d apply(@NotNull VariablesMap rootProperties);

    @NotNull
    static RelativePosition vec(@NotNull Vec3d vec3d) {
        return (unused) -> vec3d;
    }

}
