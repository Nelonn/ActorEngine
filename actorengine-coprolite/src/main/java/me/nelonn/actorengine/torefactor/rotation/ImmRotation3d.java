package me.nelonn.actorengine.torefactor.rotation;

import me.nelonn.actorengine.utility.AEMath;

public class ImmRotation3d extends ImmRotation2d implements Rotation3d {
    private final float roll;

    public ImmRotation3d(float roll, float pitch, float yaw) {
        super(pitch, yaw);
        this.roll = AEMath.normalize360rad(roll);
    }

    @Override
    public float roll() {
        return this.roll;
    }

}
