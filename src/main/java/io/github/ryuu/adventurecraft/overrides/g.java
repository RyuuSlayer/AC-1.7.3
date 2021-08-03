package io.github.ryuu.adventurecraft.overrides;

import java.util.Random;

public class g extends Biome {
    public pg a(Random random) {
        if (random.nextInt(3) == 0)
            return (pg) new pw();
        return (pg) new ws();
    }
}
