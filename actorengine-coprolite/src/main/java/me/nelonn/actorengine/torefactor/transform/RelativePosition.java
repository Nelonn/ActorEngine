package me.nelonn.actorengine.torefactor.transform;

import me.nelonn.actorengine.torefactor.variable.VariablesMap;
import me.nelonn.bestvecs.Vec3d;

@FunctionalInterface
public interface RelativePosition {

    Vec3d apply(VariablesMap rootProperties);

    
    static RelativePosition vec(Vec3d vec3d) {
        return (unused) -> vec3d;
    }

}
