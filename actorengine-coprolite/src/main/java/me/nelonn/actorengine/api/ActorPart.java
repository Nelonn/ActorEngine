package me.nelonn.actorengine.api;

import me.nelonn.actorengine.api.actor.Actor;
import org.jetbrains.annotations.Nullable;

public interface ActorPart {

    @Nullable Actor getActor();

}
