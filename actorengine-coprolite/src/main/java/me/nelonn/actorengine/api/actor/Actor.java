package me.nelonn.actorengine.api.actor;

import me.nelonn.actorengine.api.ActorEngine;
import me.nelonn.actorengine.api.Root;
import me.nelonn.actorengine.component.AComponent;
import me.nelonn.bestvecs.MutVec3d;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.*;
import java.util.stream.Collectors;

public class Actor {
    private final ActorType<?> type;
    private boolean hasBegunPlay = false;
    private double lastTickTime = (double) System.currentTimeMillis() / 1000.0D;
    protected final Root root;

    // ECS
    private final Map<String, AComponent> components = new HashMap<>();
    private final Collection<AComponent> allComponents = Collections.unmodifiableCollection(components.values());
    
    public Actor(@NotNull ActorType<?> type, @NotNull Root root) {
        this.type = type;
        this.root = root;
    }

    public final void assemble(@Nullable CompoundTag nbt) {
        preLoadInitialize();
        if (nbt != null) {
            load(nbt);
        }
        postLoadInitialize();
        compose();
    }

    @OverridingMethodsMustInvokeSuper
    public void preLoadInitialize() {
    }

    @OverridingMethodsMustInvokeSuper
    public void save(@NotNull CompoundTag nbt) {
    }

    @OverridingMethodsMustInvokeSuper
    public void load(@NotNull CompoundTag nbt) {
    }

    @OverridingMethodsMustInvokeSuper
    public void postLoadInitialize() {
    }

    /**
     * This method is usually used to teleport child entities to an actor
     */
    @OverridingMethodsMustInvokeSuper
    public void compose() {
    }

    /**
     * It can be redefined if the actor has some kind of movement grid
     * @param x global x coordinate
     * @param y global y coordinate
     * @param z global z coordinate
     */
    @OverridingMethodsMustInvokeSuper
    public void moveTo(double x, double y, double z) {
        ((Entity) root.asEntity()).moveTo(x, y, z);
        compose();
    }

    /**
     * Called on the first tick
     */
    @OverridingMethodsMustInvokeSuper
    public void beginPlay() {
        for (AComponent component : allComponents) {
            component.beginPlay(); // Do not catch exceptions to prevent the use of an incomplete actor
        }
    }

    /**
     * Tick
     * @param dt time in seconds since last tick
     */
    @OverridingMethodsMustInvokeSuper
    public void tick(float dt) {
        for (AComponent component : allComponents) {
            component.tick(dt); // Do not catch exceptions to prevent the use of an incomplete actor
        }
    }

    /**
     * Destroy is required!
     */
    @OverridingMethodsMustInvokeSuper
    public void destroy() {
        for (AComponent component : getAllComponents()) {
            try {
                component.destroy();
            } catch (Throwable e) {
                ActorEngine.get().getLogger().error("Unable to remove component '{}'", component.getName(), e);
            }
        }
    }

    public void remove(@NotNull Entity.RemovalReason reason) {
        root.asEntity().setRemoved(reason);
    }

    public <T extends AComponent> T addComponent(@NotNull T component) {
        return addComponent(component, false);
    }

    public <T extends AComponent> T addComponent(@NotNull T component, boolean replace) {
        String componentName = component.getName();
        if (componentName.equals("actor") || componentName.equals("root") || componentName.equals("this") || componentName.equals("self")) {
            throw new IllegalArgumentException("Component name '" + componentName + "' is illegal");
        }
        if (components.containsKey(componentName) && !replace) {
            throw new RuntimeException("Component '" + componentName + "' already exists");
        }
        components.put(componentName, component);
        return component;
    }

    public boolean hasComponent(@NotNull String name) {
        return components.containsKey(name.toLowerCase(Locale.ENGLISH));
    }

    public <T extends AComponent> boolean hasComponent(@NotNull String name, @NotNull Class<T> type) {
        AComponent component = components.get(name.toLowerCase(Locale.ENGLISH));
        return component != null && type.isAssignableFrom(component.getClass());
    }

    public @Nullable AComponent removeComponent(@NotNull String name) {
        return components.remove(name.toLowerCase(Locale.ENGLISH));
    }

    public @Nullable AComponent getComponentNullable(@NotNull String name) {
        return components.get(name.toLowerCase(Locale.ENGLISH));
    }

    // maybe <T extends AComponent>? I'm more comfortable without
    public <T> @Nullable T getComponentNullable(@NotNull String name, @NotNull Class<T> type) {
        AComponent component = components.get(name.toLowerCase(Locale.ENGLISH));
        if (component == null || !type.isAssignableFrom(component.getClass())) return null;
        return type.cast(component);
    }

    public @NotNull AComponent getComponent(@NotNull String name) {
        name = name.toLowerCase(Locale.ENGLISH);
        AComponent component = components.get(name);
        if (component == null) {
            throw new RuntimeException("Component '" + name + "' not found");
        }
        return component;
    }

    // maybe <T extends AComponent>? I'm more comfortable without
    public <T> @NotNull T getComponent(@NotNull String name, @NotNull Class<T> type) {
        name = name.toLowerCase(Locale.ENGLISH);
        AComponent component = components.get(name);
        if (component == null) {
            throw new RuntimeException("Component '" + name + "' not found");
        }
        if (!type.isAssignableFrom(component.getClass())) {
            throw new RuntimeException("Component '" + name + "' is not what was expected, expected '" + type.getTypeName() + "', got '" + component.getClass().getTypeName() + "'");
        }
        return type.cast(component);
    }

    public <T> @NotNull Collection<T> getComponents(@NotNull Class<T> type) {
        return getAllComponents().stream()
                .filter(component -> type.isAssignableFrom(component.getClass()))
                .map(type::cast)
                .toList();
    }

    public @NotNull Collection<AComponent> getAllComponents() {
        return allComponents;
    }

    public @NotNull Level level() {
        return this.root.level();
    }

    public @NotNull MutVec3d position() {
        return this.root.position();
    }

    public @NotNull ActorType<?> getType() {
        return this.type;
    }

    public @NotNull Root getRoot() {
        return this.root;
    }

    @ApiStatus.Internal
    public final void tick() {
        if (!hasBegunPlay) {
            hasBegunPlay = true;
            beginPlay();
        }
        double current = (double) System.currentTimeMillis() / 1000.0D;
        float deltaTime = (float) (current - this.lastTickTime);
        this.lastTickTime = current;
        tick(deltaTime);
        compose();
    }

    @ApiStatus.Internal
    public void onRootRemoved(@NotNull Entity.RemovalReason reason) {
        destroy();
    }

    @Override
    public String toString() {
        return this.type.getKey().toString();
    }
}
