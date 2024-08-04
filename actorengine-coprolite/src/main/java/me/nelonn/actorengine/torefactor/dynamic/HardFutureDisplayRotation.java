package me.nelonn.actorengine.torefactor.dynamic;

import me.nelonn.actorengine.torefactor.rotation.Rotation3d;
import me.nelonn.actorengine.torefactor.variable.VariableKey;
import me.nelonn.actorengine.torefactor.variable.VariablesMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

public class HardFutureDisplayRotation implements FutureDisplayRotation {
    private final VariableKey<Rotation3d> source;
    private final boolean useRoll;
    private final boolean usePitch;
    private final boolean useYaw;
    private final float addRoll;
    private final float addPitch;
    private final float addYaw;

    public HardFutureDisplayRotation(@Nullable String source, boolean useRoll, boolean usePitch, boolean useYaw, float addRoll, float addPitch, float addYaw) {
        this.source = source != null ? new VariableKey<>(source, Rotation3d.class, Rotation3d.ZERO) : null;
        this.useRoll = useRoll;
        this.usePitch = usePitch;
        this.useYaw = useYaw;
        this.addRoll = addRoll;
        this.addPitch = addPitch;
        this.addYaw = addYaw;
    }

    @Override
    public @NotNull Quaternionf apply(@NotNull VariablesMap properties) {
        float roll = (float) Math.toRadians(getAddRoll());
        float pitch = (float) Math.toRadians(getAddPitch());
        float yaw = (float) Math.toRadians(getAddYaw());
        Quaternionf quaternion;
        if (source != null) {
            Rotation3d rot = properties.get(source);
            if (isUseRoll() && isUsePitch() && isUseYaw()) {
                quaternion = rot.quaternion();
            } else {
                if (isUseRoll()) {
                    roll += rot.roll();
                }
                if (isUsePitch()) {
                    pitch += rot.pitch();
                }
                if (isUseYaw()) {
                    yaw += rot.yaw();
                }
                quaternion = new Quaternionf().rotateXYZ(pitch, -yaw, roll);
            }
        } else {
            quaternion = new Quaternionf().rotateXYZ(pitch, -yaw, roll);
        }
        return quaternion;
    }

    @Nullable
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

    public float getAddRoll() {
        return addRoll;
    }

    public float getAddPitch() {
        return addPitch;
    }

    public float getAddYaw() {
        return addYaw;
    }

    @Override
    public String toString() {
        return "HardFutureDisplayRotation{" +
                "source=" + source +
                ", useRoll=" + useRoll +
                ", usePitch=" + usePitch +
                ", useYaw=" + useYaw +
                ", addRoll=" + addRoll +
                ", addPitch=" + addPitch +
                ", addYaw=" + addYaw +
                '}';
    }
}
