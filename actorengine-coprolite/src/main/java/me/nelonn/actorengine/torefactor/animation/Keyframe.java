package me.nelonn.actorengine.torefactor.animation;

import me.nelonn.actorengine.torefactor.transform.FutureBoneTransform;
import me.nelonn.actorengine.torefactor.transform.FuturePropertyTransform;
import me.nelonn.actorengine.torefactor.transform.Property;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class Keyframe {
    public static Keyframe zero(String boneName, double time, Property<?> property) {
        Keyframe result = new Keyframe(time);
        FutureBoneTransform futureBoneTransform = new FutureBoneTransform();
        futureBoneTransform.add(FuturePropertyTransform.zero(property));
        result.getBoneTransforms().put(boneName, futureBoneTransform);
        return result;
    }

    private final double time; // seconds, tick = 0.05 s
    private final Map<String, FutureBoneTransform> boneTransforms = new HashMap<>();

    public Keyframe(double time) {
        this.time = time;
    }

    public double getTime() {
        return time;
    }

    public boolean shouldAffectBone(String name) {
        return boneTransforms.containsKey(name);
    }

    public boolean shouldAffectBoneProperty(String boneName, Property<?> property) {
        return getBoneTransform(boneName).affects().contains(property);
    }

    public FutureBoneTransform getBoneTransform(String boneName) {
        return requireNonNull(boneTransforms.get(boneName), boneName);
    }

    public Map<String, FutureBoneTransform> getBoneTransforms() {
        return boneTransforms;
    }
}
