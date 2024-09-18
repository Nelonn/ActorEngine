package me.nelonn.actorengine.torefactor.dynamic;

import me.nelonn.actorengine.torefactor.rotation.MutRotation2d;
import me.nelonn.actorengine.torefactor.rotation.Rotation2d;
import me.nelonn.actorengine.torefactor.variable.VariableKey;
import me.nelonn.actorengine.torefactor.variable.VariablesMap;
import org.jetbrains.annotations.Nullable;

public class HardFutureRotation implements FutureRotation {
    private final VariableKey<Rotation2d> source;
    private final boolean usePitch;
    private final boolean useYaw;
    private final float addPitch;
    private final float addYaw;

    public HardFutureRotation(@Nullable String source, boolean usePitch, boolean useYaw, float addPitch, float addYaw) {
        this.source = source != null ? new VariableKey<>(source, Rotation2d.class, Rotation2d.ZERO) : null;
        this.usePitch = usePitch;
        this.useYaw = useYaw;
        this.addPitch = addPitch;
        this.addYaw = addYaw;
    }

    @Override
    public MutRotation2d apply(VariablesMap properties) {
        float pitch = getAddPitch();
        float yaw = getAddYaw();
        VariableKey<Rotation2d> source = getSource();
        if (source != null) {
            Rotation2d rot = properties.get(source);
            if (isUsePitch()) {
                pitch += rot.pitch();
            }
            if (isUseYaw()) {
                yaw += rot.yaw();
            }
        }
        return new MutRotation2d(pitch, yaw);
    }

    @Nullable
    public VariableKey<Rotation2d> getSource() {
        return source;
    }

    public boolean isUsePitch() {
        return usePitch;
    }

    public boolean isUseYaw() {
        return useYaw;
    }

    public float getAddPitch() {
        return addPitch;
    }

    public float getAddYaw() {
        return addYaw;
    }
}
