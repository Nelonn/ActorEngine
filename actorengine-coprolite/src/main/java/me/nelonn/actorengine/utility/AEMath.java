package me.nelonn.actorengine.utility;

import net.minecraft.util.Mth;
import org.joml.Vector3f;

public final class AEMath {

    public static final float DEGREES_TO_RADIANS = (float) Math.PI / 180.0F;
    public static final float RADIANS_TO_DEGREES = 180.0F / (float) Math.PI;

    public static float normalize90rad(float angle) {
        return normalize90(angle * RADIANS_TO_DEGREES) * DEGREES_TO_RADIANS;
    }

    public static float normalize360rad(float angle) {
        return normalize360(angle * RADIANS_TO_DEGREES) * DEGREES_TO_RADIANS;
    }

    public static float normalize360(float deg) {
        while (deg < -180) {
            deg += 360;
        }
        while (deg > 180) {
            deg -= 360;
        }
        return deg;
    }

    public static float normalize90(float deg) {
        return Mth.clamp(deg, -90, 90);
    }

    public static Vector3f rotateVector2D(float pitch, float yaw) {
        float f2 = pitch * DEGREES_TO_RADIANS;
        float f3 = -yaw * DEGREES_TO_RADIANS;
        float f4 = Mth.cos(f3);
        float f5 = Mth.sin(f3);
        float f6 = Mth.cos(f2);
        float f7 = Mth.sin(f2);
        return new Vector3f(f5 * f6, -f7, f4 * f6);
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
