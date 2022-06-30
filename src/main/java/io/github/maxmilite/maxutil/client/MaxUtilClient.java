package io.github.maxmilite.maxutil.client;

import io.github.maxmilite.maxutil.config.Binding;
import io.github.maxmilite.maxutil.config.ConfigManager;
import io.github.maxmilite.maxutil.events.KeyEvent;
import io.github.maxmilite.maxutil.module.ModuleManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

@Environment(EnvType.CLIENT)
public class MaxUtilClient implements ClientModInitializer {

    public static MaxUtilClient INSTANCE;
    public static final String MOD_ID = "maxutil";
    public static MinecraftClient mc;
    public static final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString(), MOD_ID);
    public static final Logger LOG = LoggerFactory.getLogger("MaxUtil");
    public static ModuleManager manager;
    public static Binding binding;
    public static ConfigManager config;

    @Override
    public void onInitializeClient() {

        if (INSTANCE == null) {
            INSTANCE = this;
        }

        // Preload process
        LOG.info("Initializing MaxUtil...");

        mc = MinecraftClient.getInstance();

        manager = new ModuleManager();

        binding = new Binding();

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            KeyEvent.keyPress();
        });

        config = new ConfigManager();


        LOG.info("Maxutil Initialized.");
    }
}
