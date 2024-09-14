package me.nelonn.actorengine.component;

import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.Nullable;

public interface SaveLoadComponent {

    @Nullable Tag save();

    void load(Tag tag);

}