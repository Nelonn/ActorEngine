package me.nelonn.actorengine.component;

import me.nelonn.actorengine.torefactor.AActor;
import me.nelonn.actorengine.torefactor.transform.Property;
import me.nelonn.actorengine.torefactor.transform.RelativePosition;
import org.jetbrains.annotations.NotNull;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.Locale;
import java.util.Set;

public abstract class AComponent {
    public static final Set<Property<?>> DEFAULT_PROPERTIES = Set.of(Property.POSITION);
    private final AActor actor;
    private final String name;
    private RelativePosition position;

    public AComponent(@NotNull AActor actor, @NotNull String name) {
        this.actor = actor;
        this.name = name.toLowerCase(Locale.ENGLISH);
    }

    @OverridingMethodsMustInvokeSuper
    public void beginPlay() {
    }

    @OverridingMethodsMustInvokeSuper
    public void tick(float dt) {
    }

    @OverridingMethodsMustInvokeSuper
    public void destroy() {
    }

    public @NotNull Set<Property<?>> getSupportedProperties() {
        return DEFAULT_PROPERTIES;
    }

    public @NotNull AActor getActor() {
        return actor;
    }

    public @NotNull String getName() {
        return name;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '[' + getName() + ']';
    }
}
