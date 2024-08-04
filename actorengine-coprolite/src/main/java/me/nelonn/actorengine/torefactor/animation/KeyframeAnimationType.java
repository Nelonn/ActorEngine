package me.nelonn.actorengine.torefactor.animation;

import me.nelonn.actorengine.torefactor.AActor;
import me.nelonn.actorengine.torefactor.variable.VariablesMap;
import org.jetbrains.annotations.NotNull;

public class KeyframeAnimationType implements AnimationType<KeyframeAnimation> {
    private final double duration; // seconds, tick = 0.05 s
    private final boolean loop;
    private final KeyframeMap frames;

    public KeyframeAnimationType(double duration, boolean loop, @NotNull KeyframeMap frames) {
        this.duration = duration;
        this.loop = loop;
        this.frames = frames;
    }

    public double getDuration() {
        return duration;
    }

    public boolean isLoop() {
        return loop;
    }

    public @NotNull KeyframeMap getFrames() {
        return frames;
    }

    @Override
    public @NotNull KeyframeAnimation create(@NotNull AActor actor, @NotNull VariablesMap properties) {
        return new KeyframeAnimation(this, actor, properties);
    }
}
