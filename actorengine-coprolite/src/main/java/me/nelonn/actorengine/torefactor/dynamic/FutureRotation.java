package me.nelonn.actorengine.torefactor.dynamic;

import me.nelonn.actorengine.torefactor.rotation.MutRotation2d;
import me.nelonn.actorengine.torefactor.variable.VariablesMap;

public interface FutureRotation {
    MutRotation2d apply(VariablesMap properties);
}