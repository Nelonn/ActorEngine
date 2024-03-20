package me.nelonn.entitycomposer;

import me.nelonn.coprolite.api.PluginContainer;
import me.nelonn.coprolite.api.PluginInitializer;
import me.nelonn.coprolite.paper.CoprolitePlugin;
import me.nelonn.entitycomposer.api.EntityComposer;
import me.nelonn.entitycomposer.entity.AllEntities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityComposerMain implements PluginInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(EntityComposer.ID);

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
        EntityComposerCore core = new EntityComposerCore(LOGGER);
        //core.setShouldRemoveNonExistentActors(yamlConfiguration.getBoolean("remove-non-existent-actors"));
        core.setShouldRemoveNonExistentActors(true);
        CoprolitePlugin.ON_BOOTSTRAP.register(() -> {
            AllEntities.register();
        });
    }
}
