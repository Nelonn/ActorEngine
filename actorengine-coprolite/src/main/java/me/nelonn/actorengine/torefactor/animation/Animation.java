package me.nelonn.actorengine.torefactor.animation;

import me.nelonn.actorengine.torefactor.AActor;
import me.nelonn.actorengine.component.AComponent;
import me.nelonn.actorengine.torefactor.variable.VariablesMap;
import me.nelonn.actorengine.torefactor.transform.Transform;
import me.nelonn.actorengine.api.actor.Actor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Animation {
    private final AnimationType<?> type;
    private final Actor actor;
    private final VariablesMap variables;

    public Animation(AnimationType<?> type, AActor actor, VariablesMap variables) {
        this.type = type;
        this.actor = actor;
        this.variables = variables;
    }

    public AnimationType<?> getType() {
        return type;
    }

    public Actor getActor() {
        return actor;
    }

    public VariablesMap getVariables() {
        return variables;
    }

    public abstract boolean isRunning();

    public abstract @Nullable Transform animate(AComponent bone);

    public boolean isSame(Animation other) {
        return this.getType().equals(other.getType());
    }
}
