package io.github.ryuu.adventurecraft.overrides;

import java.util.Random;

public class rb extends kd {
    public pg a(Random random) {
        if (random.nextInt(5) == 0)
            return (pg)new k();
        if (random.nextInt(3) == 0)
            return (pg)new ih();
        return (pg)new yh();
    }
}
