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
        public Vec3d addStrict(Vec3d first, Vec3d second) {
            return first.add(second);
        }

        @Override
        public Vec3d lerpStrict(Vec3d from, Vec3d to, float delta) {
            return from.lerp(to,  delta);
        }
    };
    public static final Operators<Rotation2d> ROTATION2D = new Operators<>() {
        @Override
        public Rotation2d addStrict(Rotation2d first, Rotation2d second) {
            return first.add(second);
        }

        @Override
        public Rotation2d lerpStrict(Rotation2d from, Rotation2d to, float delta) {
            return from.lerp(to, delta);
        }
    };
    public static final Operators<Vec3f> VEC3F = new Operators<>() {
        @Override
        public Vec3f addStrict(Vec3f first, Vec3f second) {
            return first.add(second);
        }

        @Override
        public Vec3f lerpStrict(Vec3f from, Vec3f to, float delta) {
            return from.lerp(to, delta);
        }
    };
    public static final Operators<Quaternionf> QUATERNIONF = new Operators<>() {
        @Override
        public Quaternionf addStrict(Quaternionf first, Quaternionf second) {
            Quaternionf result = new Quaternionf();
            return first.mul(second, result);
        }

        @Override
        public Quaternionf lerpStrict(Quaternionf from, Quaternionf to, float delta) {
            Quaternionf result = new Quaternionf();
            return from.slerp(to, delta, result);
        }
    };

    public Object add(Object first, Object second) {
        return addStrict((T) first, (T) second);
    }

    public abstract T addStrict(T first, T second);

    public Object lerp(Object from, Object to, float delta) {
        return lerpStrict((T) from, (T) to, delta);
    }

    public abstract T lerpStrict(T from, T to, float delta);
}
