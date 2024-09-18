package me.nelonn.actorengine.torefactor.animation;

import me.nelonn.actorengine.torefactor.variable.VariablesMap;
import me.nelonn.actorengine.torefactor.AActor;
import org.jetbrains.annotations.NotNull;

public interface AnimationType<T extends Animation> {
    T create(AActor actor, VariablesMap properties);
}
