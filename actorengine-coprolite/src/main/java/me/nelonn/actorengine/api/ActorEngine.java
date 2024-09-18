package me.nelonn.actorengine.api;

import me.nelonn.coprolite.api.CoproliteLoader;
import me.nelonn.actorengine.api.actor.ActorRegistry;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Objects;

public interface ActorEngine {
    String ID = "actorengine";

    static ActorEngine get() {
        return (ActorEngine) Objects.requireNonNull(CoproliteLoader.getInstance().getObjectShare().get(ID + ":core"), "ObjectShare: " + ID + ":core");
    }

    ActorRegistry actors();

    boolean isRemoveActorsOfANonexistentType();

    Logger getLogger();
}
