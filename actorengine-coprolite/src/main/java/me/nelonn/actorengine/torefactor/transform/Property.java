package me.nelonn.actorengine.torefactor.transform;

import me.nelonn.actorengine.torefactor.rotation.Rotation2d;
import me.nelonn.actorengine.utility.Enum;
import me.nelonn.bestvecs.ImmVec3d;
import me.nelonn.bestvecs.ImmVec3f;
import me.nelonn.bestvecs.Vec3d;
import me.nelonn.bestvecs.Vec3f;
import org.joml.Quaternionf;

public final class Property<T> {
    public static final Property<Vec3d> POSITION = new Property<>(Operators.VEC3D, ImmVec3d.ZERO);
    public static final Property<Rotation2d> ROTATION = new Property<>(Operators.ROTATION2D, Rotation2d.ZERO);
    public static final Property<Vec3f> DISPLAY_TRANSLATION = new Property<>(Operators.VEC3F, ImmVec3f.ZERO);
    public static final Property<Quaternionf> DISPLAY_LEFT_ROTATION = new Property<>(Operators.QUATERNIONF, new Quaternionf());
    public static final Property<Vec3f> DISPLAY_SCALE = new Property<>(Operators.VEC3F, ImmVec3f.ONE);
    public static final Property<Quaternionf> DISPLAY_RIGHT_ROTATION = new Property<>(Operators.QUATERNIONF, new Quaternionf());
    private static final Enum<Property> ENUM = new Enum<>(Property.class);
    private final Operators<T> operators;
    private final T zero;

    private Property(Operators<T> operators, T zero) {
        this.operators = operators;
        this.zero = zero;
    }

    public Operators<T> getOperators() {
        return operators;
    }

    public Object getZero() {
        return zero;
    }

    
    public String name() {
        return ENUM.name(this);
    }

    public int ordinal() {
        return ENUM.ordinal(this);
    }

    @Override
    public String toString() {
        return name();
    }

    public static Property<?>[] values() {
        return ENUM.values().toArray(Property[]::new);
    }
}
