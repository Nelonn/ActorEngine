package me.nelonn.actorengine.utility;

import me.nelonn.bestvecs.MutVec3d;
import me.nelonn.bestvecs.Vec3d;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3d;

public final class AEMath {

    public static final float DEGREES_TO_RADIANS = (float) Math.PI / 180.0F;
    public static final float RADIANS_TO_DEGREES = 180.0F / (float) Math.PI;

    public static float normalize90rad(float angle) {
        return normalize90deg(angle * RADIANS_TO_DEGREES) * DEGREES_TO_RADIANS;
    }

    public static float normalize90deg(float degrees) {
        return Mth.clamp(degrees, -90.0F, 90.0F);
    }

    public static float normalize360rad(float angle) {
        return normalizeAxis(angle * RADIANS_TO_DEGREES) * DEGREES_TO_RADIANS;
    }

    public static float normalizeAxis(float degrees) {
        degrees %= 360.0F;
        if (degrees >= 180.0F) {
            degrees -= 360.0F;
        } else if (degrees < -180.0F) {
            degrees += 360.0F;
        }
        return degrees;
    }

    public static MutVec3d rotateVector2D(float pitch, float yaw) {
        float f2 = pitch * 0.017453292F;
        float f3 = -yaw * 0.017453292F;
        float f4 = Mth.cos(f3);
        float f5 = Mth.sin(f3);
        float f6 = Mth.cos(f2);
        float f7 = Mth.sin(f2);
        return new MutVec3d(f5 * f6, -f7, f4 * f6);
    }

    public static @NotNull MutVec3d rotateVector(@NotNull Vec3d coordinates, @NotNull Quaternionf quaternion) {
        MutVec3d coords = coordinates instanceof MutVec3d mutVec3d ? mutVec3d : coordinates.mutableCopy();
        Vector3d vec = new Vector3d(coords.x(), coords.y(), coords.z());
        quaternion.transform(vec);
        return coords.with(vec.x(), vec.y(), vec.z());
    }

    public static float delta(double current, double min, double max) {
        if (current <= min) return 0;
        if (current >= max) return 1;
        return (float) (Math.abs(current - min) / Math.abs(max - min));
    }

    private AEMath() {
        throw new UnsupportedOperationException();
    }
}
