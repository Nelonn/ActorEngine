package me.nelonn.actorengine.torefactor.transform;

import me.nelonn.actorengine.torefactor.rotation.Rotation2d;
import me.nelonn.bestvecs.Vec3d;
import me.nelonn.bestvecs.Vec3f;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

@SuppressWarnings("unchecked")
public abstract class Operators<T> {
    public static final Operators<Vec3d> VEC3D = new Operators<>() {
        @Override
        public @NotNull Vec3d addStrict(@NotNull Vec3d first, @NotNull Vec3d second) {
            return first.add(second);
        }

        @Override
        public @NotNull Vec3d lerpStrict(@NotNull Vec3d from, @NotNull Vec3d to, float delta) {
            return from.lerp(to,  delta);
        }
    };
    public static final Operators<Rotation2d> ROTATION2D = new Operators<>() {
        @Override
        public @NotNull Rotation2d addStrict(@NotNull Rotation2d first, @NotNull Rotation2d second) {
            return first.add(second);
        }

        @Override
        public @NotNull Rotation2d lerpStrict(@NotNull Rotation2d from, @NotNull Rotation2d to, float delta) {
            return from.lerp(to, delta);
        }
    };
    public static final Operators<Vec3f> VEC3F = new Operators<>() {
        @Override
        public @NotNull Vec3f addStrict(@NotNull Vec3f first, @NotNull Vec3f second) {
            return first.add(second);
        }

        @Override
        public @NotNull Vec3f lerpStrict(@NotNull Vec3f from, @NotNull Vec3f to, float delta) {
            return from.lerp(to, delta);
        }
    };
    public static final Operators<Quaternionf> QUATERNIONF = new Operators<>() {
        @Override
        public @NotNull Quaternionf addStrict(@NotNull Quaternionf first, @NotNull Quaternionf second) {
            Quaternionf result = new Quaternionf();
            return first.mul(second, result);
        }

        @Override
        public @NotNull Quaternionf lerpStrict(@NotNull Quaternionf from, @NotNull Quaternionf to, float delta) {
            Quaternionf result = new Quaternionf();
            return from.slerp(to, delta, result);
        }
    };

    public @NotNull Object add(@NotNull Object first, @NotNull Object second) {
        return addStrict((T) first, (T) second);
    }

    public abstract @NotNull T addStrict(@NotNull T first, @NotNull T second);

    public @NotNull Object lerp(@NotNull Object from, @NotNull Object to, float delta) {
        return lerpStrict((T) from, (T) to, delta);
    }

    public abstract @NotNull T lerpStrict(@NotNull T from, @NotNull T to, float delta);
}
