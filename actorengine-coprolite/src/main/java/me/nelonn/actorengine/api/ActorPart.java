package me.nelonn.actorengine.api;

import me.nelonn.actorengine.api.actor.Actor;

import javax.annotation.Nullable;

public interface ActorPart {

    default @Nullable Actor getActor() {
        return null;
    }

}
