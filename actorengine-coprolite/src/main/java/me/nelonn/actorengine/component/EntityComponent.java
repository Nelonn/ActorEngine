package me.nelonn.actorengine.component;

import me.nelonn.actorengine.torefactor.AActor;
import me.nelonn.actorengine.torefactor.transform.Property;
import me.nelonn.actorengine.torefactor.transform.Transform;
import me.nelonn.bestvecs.ImmVec3d;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.Set;

public class EntityComponent extends TangibleComponent {
    public static final Set<Property<?>> PROPERTIES = Set.of(Property.POSITION, Property.ROTATION);
    private final Entity entity;

    public EntityComponent(AActor actor, String name, Entity entity, boolean addToWorld) {
        super(actor, name);
        this.entity = entity;
        if (addToWorld) {
            getActor().level().addFreshEntity(entity);
        }
    }

    public EntityComponent(AActor actor, String name, Entity entity) {
        this(actor, name, entity, true);
    }

    @Override
    public void moveTo(double x, double y, double z) {
        if (Double.isNaN(x) || Double.isNaN(y) || Double.isNaN(z)) {
            System.err.println("NaN");
            return;
        }
        entity.moveTo(x, y, z);
    }

    @Override
    public void destroy() {
        super.destroy();
        entity.discard();
    }

    @Override
    public void applyTransforms(Level world, ImmVec3d rootPos, Transform transform) {
        if (!entity.level().equals(world)) {
            entity.setLevel(world);
        }
        super.applyTransforms(world, rootPos, transform);
    }

    @Override
    public void compose(Level world, ImmVec3d rootPos, Transform transform) {
        super.compose(world, rootPos, transform);
    }

    
    public Entity getEntity() {
        return entity;
    }

    @Override
    public Set<Property<?>> getSupportedProperties() {
        return PROPERTIES;
    }
}
