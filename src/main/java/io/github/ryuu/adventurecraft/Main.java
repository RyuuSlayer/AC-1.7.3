package io.github.ryuu.adventurecraft;

//import io.github.minecraftcursedlegacy.api.config.Configs;
//import io.github.minecraftcursedlegacy.api.registry.Id;

import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {

    public static long[] updateTimes;
    public static long updateRendererTime;

    static {
        updateTimes = new long[512];
    }

    //private static WritableConfig config;

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        System.out.println("Hello Fabric world!");

        // example config
        //try {
        //    config = Configs.loadOrCreate(new Id("modid", "example"), ConfigTemplate.builder().addContainer("exampleContainer", container -> container.addDataEntry("someData", "0.5")).build());
        //} catch (IOException e) {
        //    throw new RuntimeException(e);
        //}

        //System.out.println(config.getDoubleValue("exampleContainer.someData"));

    }
}
