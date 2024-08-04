package me.nelonn.actorengine;

import me.nelonn.coprolite.api.PluginContainer;
import me.nelonn.coprolite.api.PluginInitializer;
import me.nelonn.coprolite.paper.CoprolitePlugin;
import me.nelonn.actorengine.api.ActorEngine;
import me.nelonn.actorengine.entity.AllEntities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActorEngineMain implements PluginInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(ActorEngine.ID);

    @Override
    public void onInitialize(PluginContainer plugin) {
        /*Path dataDirectory = context.getDataDirectory();
        if (!dataDirectory.toFile().exists()) {
            dataDirectory.toFile().mkdirs();
        }
        File configFile = dataDirectory.resolve("config.yml").toFile();
        if (!configFile.exists()) {
            JarResources.extractFile(context.getPluginSource(), "resources/config.yml",
                    dataDirectory.resolve("config.yml").toFile());
        }
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(configFile);*/
        ActorEngineCore core = new ActorEngineCore(LOGGER);
        //core.setShouldRemoveNonExistentActors(yamlConfiguration.getBoolean("remove-actors-of-a-nonexistent-type"));
        core.setRemoveActorsOfANonexistentType(true);
        CoprolitePlugin.ON_BOOTSTRAP.register(() -> {
            AllEntities.register();
        });
    }
}
