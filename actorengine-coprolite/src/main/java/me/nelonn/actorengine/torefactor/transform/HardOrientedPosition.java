package me.nelonn.actorengine.torefactor.transform;

import me.nelonn.actorengine.torefactor.rotation.Rotation3d;
import me.nelonn.actorengine.torefactor.variable.VariableKey;
import me.nelonn.actorengine.torefactor.variable.VariablesMap;
import me.nelonn.bestvecs.ImmVec3d;
import me.nelonn.bestvecs.MutVec3d;
import me.nelonn.bestvecs.Vec3d;
import me.nelonn.actorengine.utility.AEMath;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

public class HardOrientedPosition implements RelativePosition {
    private final VariableKey<Rotation3d> source;
    private final boolean useRoll;
    private final boolean usePitch;
    private final boolean useYaw;
    private final ImmVec3d coordinates;

    public HardOrientedPosition(@NotNull String source, boolean useRoll, boolean usePitch, boolean useYaw, @NotNull Vec3d coordinates) {
        this.source = new VariableKey<>(source, Rotation3d.class, Rotation3d.ZERO);
        this.useRoll = useRoll;
        this.usePitch = usePitch;
        this.useYaw = useYaw;
        this.coordinates = coordinates.toImmutable();
    }

    @NotNull
    public MutVec3d apply(@NotNull Quaternionf quaternion) {
        return AEMath.rotateVector(coordinates, quaternion);
    }

    @NotNull
    public MutVec3d apply(float roll, float pitch, float yaw) {
        roll = isUseRoll() ? roll : 0;
        pitch = isUsePitch() ? pitch : 0;
        yaw = isUseYaw() ? yaw : 0;
        Quaternionf quaternion = new Quaternionf();
        quaternion.rotateLocalX(pitch);
        quaternion.rotateLocalY(-yaw);
        quaternion.rotateLocalZ(roll);
        return apply(quaternion);
    }

    @NotNull
    public MutVec3d apply(@NotNull Rotation3d rotation3d) {
        if (isUsePitch() && isUseYaw() && isUseRoll()) {
            return apply(rotation3d.quaternion());
        } else {
            return apply(rotation3d.roll(), rotation3d.pitch(), rotation3d.yaw());
        }
    }

    @Override
    @NotNull
    public Vec3d apply(@NotNull VariablesMap rootProperties) {
        return apply(rootProperties.get(source));
    }

    @NotNull
    public VariableKey<Rotation3d> getSource() {
        return source;
    }

    public boolean isUseRoll() {
        return useRoll;
    }

    public boolean isUsePitch() {
        return usePitch;
    }

    public boolean isUseYaw() {
        return useYaw;
    }

    @NotNull
    public ImmVec3d getCoordinates() {
        return coordinates;
    }
}
