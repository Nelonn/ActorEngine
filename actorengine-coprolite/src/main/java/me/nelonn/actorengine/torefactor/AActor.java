package me.nelonn.actorengine.torefactor;

import me.nelonn.actorengine.api.ActorEngine;
import me.nelonn.actorengine.api.Root;
import me.nelonn.actorengine.api.actor.Actor;
import me.nelonn.actorengine.api.actor.ActorType;
import me.nelonn.actorengine.component.AComponent;
import me.nelonn.actorengine.component.SaveLoadComponent;
import me.nelonn.actorengine.component.TangibleComponent;
import me.nelonn.actorengine.torefactor.animation.Animation;
import me.nelonn.actorengine.torefactor.animation.AnimationType;
import me.nelonn.actorengine.torefactor.rotation.MutRotation3d;
import me.nelonn.actorengine.torefactor.rotation.Rotation3d;
import me.nelonn.actorengine.torefactor.transform.Transform;
import me.nelonn.actorengine.torefactor.variable.FlexibleVariablesMap;
import me.nelonn.actorengine.torefactor.variable.VariableKey;
import me.nelonn.actorengine.torefactor.variable.VariablesMap;
import me.nelonn.bestvecs.ImmVec3d;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public abstract class AActor extends Actor {
    public static final VariableKey<Rotation3d> MAIN_ROTATION = new VariableKey<>("main_rotation", Rotation3d.class, Rotation3d.ZERO);
    private final Map<AnimationType<?>, Animation> playingAnimations = new HashMap<>();
    protected final MutRotation3d mainRotation = new MutRotation3d(0, 0, 0);
    protected FlexibleVariablesMap variables = new FlexibleVariablesMap();

    public AActor(@NotNull ActorType<?> type, @NotNull Root root) {
        super(type, root);
        variables.add(MAIN_ROTATION, (Supplier<Rotation3d>) this::getMainRotation);
    }

    // mutable copy
    public @NotNull MutRotation3d getMainRotation() {
        return mainRotation.mutableCopy();
    }

    public @NotNull Collection<Animation> getPlayingAnimations() {
        return playingAnimations.values();
    }

    public void playAnimation(@NotNull AnimationType<?> animationType) {
        Animation animation = animationType.create(this, variables);
        playingAnimations.put(animationType, animation);
    }

    public @NotNull VariablesMap getVariables() {
        return variables;
    }

    @Override
    public void compose() {
        super.compose();

        // TODO: refactor

        ImmVec3d rootPos = position().toImmutable();

        playingAnimations.values().removeIf(animation -> !animation.isRunning());

        for (AComponent component : getAllComponents()) {
            Transform transform = new Transform();
            if (!playingAnimations.isEmpty()) {
                for (Animation animation : playingAnimations.values()) {
                    try {
                        Transform animationTransform = animation.animate(component);
                        if (animationTransform != null) {
                            transform.add(animationTransform);
                        }
                    } catch (Throwable e) {
                        ActorEngine.get().getLogger().error("Unable to animate component", e);
                    }
                }
            }
            try {
                if (component instanceof TangibleComponent tangibleComponent) {
                    tangibleComponent.compose(level(), rootPos, transform);
                }
            } catch (Throwable e) {
                ActorEngine.get().getLogger().error("Unable to move component", e);
            }
        }
    }

    private static final String COMPONENTS_DATA = "ComponentsData";

    @Override
    public void save(@NotNull CompoundTag nbt) {
        super.save(nbt);

        Collection<SaveLoadComponent> toSave = getComponents(SaveLoadComponent.class);
        if (toSave.isEmpty()) return;

        CompoundTag componentsData;
        if (nbt.contains(COMPONENTS_DATA)) { // super.save(nbt); can be called after main save
            componentsData = nbt.getCompound(COMPONENTS_DATA);
        } else {
            componentsData = new CompoundTag();
            nbt.put(COMPONENTS_DATA, componentsData);
        }

        for (SaveLoadComponent component : toSave) {
            String name = ((AComponent) component).getName();
            Tag tag;
            try {
                tag = component.save();
            } catch (Throwable e) {
                getRoot().getActorEngine().getLogger().error("Unable to save component '{}'", name, e);
                continue;
            }
            if (tag == null) continue;
            componentsData.put(name, tag);
        }
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);

        if (!nbt.contains(COMPONENTS_DATA)) return;
        CompoundTag componentsData = nbt.getCompound(COMPONENTS_DATA);

        for (SaveLoadComponent component : getComponents(SaveLoadComponent.class)) {
            String name = ((AComponent) component).getName();
            if (!componentsData.contains(name)) continue;
            Tag data = componentsData.get(name);
            try {
                component.load(data);
            } catch (Throwable e) {
                getRoot().getActorEngine().getLogger().error("Unable to load component '{}'", name, e);
            }
        }
    }
}
