package io.github.ryuu.adventurecraft.extensions.client.particle;

import net.minecraft.entity.Entity;
import net.minecraft.util.maths.Box;

import java.util.List;

public interface ExParticleManager {

    List<Entity> getEffectsWithinAABB(Box axisalignedbb);
}
