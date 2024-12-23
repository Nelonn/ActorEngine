package me.nelonn.actorengine.component;

import me.nelonn.actorengine.torefactor.AActor;
import me.nelonn.actorengine.torefactor.transform.Property;
import me.nelonn.actorengine.torefactor.transform.RelativePosition;
import me.nelonn.actorengine.torefactor.transform.Transform;
import me.nelonn.bestvecs.ImmVec3d;
import me.nelonn.bestvecs.MutVec3d;
import me.nelonn.bestvecs.Vec3d;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class TangibleComponent extends AComponent {
    @Nullable private RelativePosition position;

    public TangibleComponent(AActor actor, String name) {
        super(actor, name);
    }

    public abstract void moveTo(double x, double y, double z);

    public void compose(Level world, ImmVec3d rootPos, Transform transform) {
        Transform baseTransform = this.baseTransform();
        baseTransform.add(transform);
        applyTransforms(world, rootPos, baseTransform);
    }

    public void applyTransforms(Level world, ImmVec3d rootPos, Transform transform) {
        MutVec3d pos = rootPos.mutableCopy();
        Vec3d transformPosition = transform.get(Property.POSITION);
        if (transformPosition != null) {
            pos.add(transformPosition);
        }
        if (Double.isNaN(pos.x()) || Double.isNaN(pos.y()) || Double.isNaN(pos.z())) {
            getActor().getRoot().getActorEngine().getLogger().error("Got NaN when composing '{}'", getName());
            return;
        }
        moveTo(pos.x(), pos.y(), pos.z());
    }

    public Transform baseTransform() {
        Transform transform = new Transform();
        if (position == null) return transform;
        Vec3d positionVec = position.apply(getActor().getVariables());
        transform.set(Property.POSITION, positionVec);
        return transform;
    }

    public @Nullable RelativePosition getPosition() {
        return position;
    }

    public void setPosition(@Nullable RelativePosition position) {
        this.position = position;
    }
}
