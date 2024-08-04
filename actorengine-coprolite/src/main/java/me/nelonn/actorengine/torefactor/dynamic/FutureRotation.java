package me.nelonn.actorengine.torefactor.dynamic;

import me.nelonn.actorengine.torefactor.rotation.MutRotation2d;
import me.nelonn.actorengine.torefactor.variable.VariablesMap;
import org.jetbrains.annotations.NotNull;

public interface FutureRotation {
    @NotNull
    MutRotation2d apply(@NotNull VariablesMap properties);
}