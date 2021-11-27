package io.github.ryuu.adventurecraft.earlyrisers;

import com.chocohead.mm.api.ClassTinkerers;
import net.minecraft.client.options.Option;

public class EnumInitializer implements Runnable {
    @Override
    public void run() {
        ClassTinkerers.enumBuilder("net.minecraft.client.options.Option", String.class, boolean.class, boolean.class)
                .addEnum("AUTO_FAR_CLIP", "options.adjustFarClip", false, true)
                .build();

        ClassTinkerers.enumBuilder("net.minecraft.client.options.Option", String.class, boolean.class, boolean.class)
                .addEnum("GRASS_3D", "options.grass3d", false, true)
                .build();
    }
}
