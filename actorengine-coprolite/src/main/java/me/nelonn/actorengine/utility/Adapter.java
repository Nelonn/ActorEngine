package me.nelonn.actorengine.utility;

import me.nelonn.bestvecs.Vec3d;
import me.nelonn.bestvecs.Vec3f;
import me.nelonn.flint.path.Key;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.joml.Vector3f;

public final class Adapter {

    @Contract("null -> null; !null -> !null")
    public static Vec3 adapt(Vec3d vec3d) {
        if (vec3d == null) return null;
        return new Vec3(vec3d.x(), vec3d.y(), vec3d.z());
    }

    @Contract("null -> null; !null -> !null")
    public static Vec3d adapt(Vec3 vec3d) {
        if (vec3d == null) return null;
        return Vec3d.mutable(vec3d.x(), vec3d.y(), vec3d.z());
    }

    @Contract("null -> null; !null -> !null")
    public static Vector3f adapt(Vec3f vec3f) {
        if (vec3f == null) return null;
        return new Vector3f(vec3f.x(), vec3f.y(), vec3f.z());
    }

    @Contract("null -> null; !null -> !null")
    public static Key adaptKey(ResourceLocation resourceLocation) {
        if (resourceLocation == null) return null;
        return Key.of(resourceLocation.getNamespace(), resourceLocation.getPath());
    }

    private Adapter() {
        throw new UnsupportedOperationException();
    }
}
