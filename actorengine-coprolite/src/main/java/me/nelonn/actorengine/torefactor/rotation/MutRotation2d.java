package me.nelonn.actorengine.torefactor.rotation;

import me.nelonn.actorengine.utility.AEMath;
import org.jetbrains.annotations.NotNull;

public class MutRotation2d implements Rotation2d {
    private float pitch;
    private float yaw;

    public MutRotation2d(float pitch, float yaw) {
        this.pitch = AEMath.normalize360rad(pitch);
        this.yaw = AEMath.normalize360rad(yaw);
    }

    @Override
    public float pitch() {
        return pitch;
    }

    @NotNull
    public MutRotation2d pitch(float pitch) {
        this.pitch = AEMath.normalize360rad(pitch);
        return this;
    }

    @Override
    public float yaw() {
        return yaw;
    }

    @NotNull
    public MutRotation2d yaw(float yaw) {
        this.yaw = AEMath.normalize360rad(yaw);
        return this;
    }
}
