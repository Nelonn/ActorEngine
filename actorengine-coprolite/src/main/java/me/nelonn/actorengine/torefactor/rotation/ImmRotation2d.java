package me.nelonn.actorengine.torefactor.rotation;

import me.nelonn.actorengine.utility.AEMath;

public class ImmRotation2d implements Rotation2d {
    private final float pitch;
    private final float yaw;

    public ImmRotation2d(float pitch, float yaw) {
        this.pitch = AEMath.normalize360rad(pitch);
        this.yaw = AEMath.normalize360rad(yaw);
    }

    @Override
    public float pitch() {
        return pitch;
    }

    @Override
    public float yaw() {
        return yaw;
    }

}
