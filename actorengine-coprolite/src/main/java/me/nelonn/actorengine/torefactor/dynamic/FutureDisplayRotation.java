package me.nelonn.actorengine.torefactor.dynamic;

import me.nelonn.actorengine.torefactor.variable.VariablesMap;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

public interface FutureDisplayRotation {
    Quaternionf apply(VariablesMap properties);
}
