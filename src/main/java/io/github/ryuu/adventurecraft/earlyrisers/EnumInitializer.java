package io.github.ryuu.adventurecraft.earlyrisers;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EnumInitializer implements Runnable {

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

        this.addOptions(remapper);
    }

    private void addOptions(MappingResolver remapper) {
        String optionEnum = remapper.mapClassName("intermediary", "net.minecraft.class_271");

        ClassTinkerers.enumBuilder(optionEnum, String.class, boolean.class, boolean.class)
                .addEnum("AUTO_FAR_CLIP", "options.adjustFarClip", false, true)
                .build();

        ClassTinkerers.enumBuilder(optionEnum, String.class, boolean.class, boolean.class)
                .addEnum("GRASS_3D", "options.grass3d", false, true)
                .build();
    }
}
