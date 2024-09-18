package me.nelonn.actorengine;

import me.nelonn.actorengine.api.ActorEngine;
import me.nelonn.actorengine.api.actor.ActorRegistry;
import me.nelonn.coprolite.api.CoproliteLoader;
import me.nelonn.coprolite.paper.std.registryaccessor.EntityTypeRegistry;
import org.slf4j.Logger;

public class ActorEngineCore implements ActorEngine {
    public static final String KEY = ActorEngine.ID + ":core";
    private final ActorRegistry actorRegistry = new ActorRegistry();
    private final Logger logger;
    private boolean removeActorsOfANonexistentType = false;

    public ActorEngineCore(Logger logger) {
        CoproliteLoader.getInstance().getObjectShare().put(KEY, this);
        this.logger = logger;
    }

    @Override
    public ActorRegistry actors() {
        return this.actorRegistry;
    }

    @Override
    public boolean isRemoveActorsOfANonexistentType() {
        return this.removeActorsOfANonexistentType;
    }

    public void setRemoveActorsOfANonexistentType(boolean removeActorsOfANonexistentType) {
        this.removeActorsOfANonexistentType = removeActorsOfANonexistentType;
    }

    @Override
    public Logger getLogger() {
        return this.logger;
    }

    static {
        EntityTypeRegistry.class.getClassLoader(); // initialize
    }
}
