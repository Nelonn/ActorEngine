package me.nelonn.actorengine.torefactor.animation;

import com.mojang.datafixers.util.Pair;
import me.nelonn.actorengine.torefactor.transform.Property;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.TreeMap;

public class KeyframeMap {
    private final TreeMap<Double, Keyframe> map = new TreeMap<>();

    public void add(@NotNull Keyframe keyframe) {
        map.put(keyframe.getTime(), keyframe);
    }

    public @Nullable Session bone(@NotNull String boneName) {
        TreeMap<Double, Keyframe> newMap = new TreeMap<>();
        newMap.putAll(map);
        newMap.values().removeIf(keyframe -> !keyframe.shouldAffectBone(boneName));
        if (newMap.isEmpty()) return null;
        return new Session(boneName, newMap);
    }

    public static class Session {
        private final String boneName;
        private final TreeMap<Double, Keyframe> map;

        public Session(@NotNull String boneName, @NotNull TreeMap<Double, Keyframe> map) {
            this.boneName = boneName;
            this.map = map;
        }

        public @Nullable Session property(@NotNull Property<?> property) {
            TreeMap<Double, Keyframe> newMap = new TreeMap<>();
            newMap.putAll(map);
            newMap.values().removeIf(keyframe -> !keyframe.shouldAffectBoneProperty(boneName, property));
            if (newMap.isEmpty()) return null;
            return new Session(boneName, newMap);
        }

        public @Nullable Keyframe findKeyframe(double time) {
            return map.get(time);
        }

        public @NotNull Pair<Keyframe, Keyframe> findSurroundingKeyframes(double targetTime) {
            Map.Entry<Double, Keyframe> floorEntry = map.floorEntry(targetTime);
            Map.Entry<Double, Keyframe> ceilingEntry = map.ceilingEntry(targetTime);
            return Pair.of(floorEntry != null ? floorEntry.getValue() : null,
                    ceilingEntry != null ? ceilingEntry.getValue() : null);
        }
    }

}
