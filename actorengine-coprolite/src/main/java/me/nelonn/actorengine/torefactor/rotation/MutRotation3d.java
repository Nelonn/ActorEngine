package me.nelonn.actorengine.torefactor.rotation;

import me.nelonn.actorengine.utility.AEMath;
import org.jetbrains.annotations.NotNull;

public class MutRotation3d extends MutRotation2d implements Rotation3d {
    private float roll;

    public MutRotation3d(float roll, float pitch, float yaw) {
        super(pitch, yaw);
        this.roll = AEMath.normalize360rad(roll);
    }

    @Override
    public float roll() {
        return this.roll;
    }

    @NotNull
    public MutRotation3d roll(float roll) {
        this.roll = AEMath.normalize360rad(roll);
        return this;
    }

    @Override
    public @NotNull MutRotation3d pitch(float pitch) {
        return (MutRotation3d) super.pitch(pitch);
    }

    @Override
    public @NotNull MutRotation3d yaw(float yaw) {
        return (MutRotation3d) super.yaw(yaw);
    }
}
