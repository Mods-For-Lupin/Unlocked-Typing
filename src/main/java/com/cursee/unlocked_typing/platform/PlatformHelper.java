package com.cursee.unlocked_typing.platform;

import java.nio.file.Path;

public class PlatformHelper {

    public static String getPlatformName() {
        String name = "Unknown";
        /*? if fabric { */
        name = "Fabric";
        /*? } else if neoforge { */
        /* name = "NeoForge"; */
        /*? } */
        return name;
    }

    public static boolean isModLoaded(String modId) {
        boolean loaded = false;
        /*? if fabric { */
        loaded = net.fabricmc.loader.api.FabricLoader.getInstance().isModLoaded(modId);
        /*? } else if neoforge { */
        /* loaded = net.neoforged.fml.ModList.get().isLoaded(modId); */
        /*? } */
        return loaded;
    }

    public static boolean isDevelopmentEnvironment() {
        boolean dev = false;
        /*? if fabric { */
        dev = net.fabricmc.loader.api.FabricLoader.getInstance().isDevelopmentEnvironment();
        /*? } else if neoforge { */
        /* dev = !net.neoforged.fml.loading.FMLLoader.getCurrent().isProduction(); */
        /*? } */
        return dev;
    }

    public static Path getGameDirectory() {
        Path path = null;
        /*? if fabric { */
        path = net.fabricmc.loader.api.FabricLoader.getInstance().getGameDir();
        /*? } else if neoforge { */
        /* path = net.neoforged.fml.loading.FMLLoader.getCurrent().getGameDir(); */
        /*? } */
        return path;
    }

    public static Path getConfigDirectory() {
        Path path = null;
        /*? if fabric { */
        path = net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir();
        /*? } else if neoforge { */
        /* path = getGameDirectory().resolve("config"); */
        /*? } */
        return path;
    }
}
