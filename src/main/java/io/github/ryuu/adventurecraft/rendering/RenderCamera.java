package io.github.ryuu.adventurecraft.rendering;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.entities.EntityCamera;

public class RenderCamera extends LivingEntityRenderer {

    public RenderCamera(EntityModel modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    protected void method_821(LivingEntity entityliving, double d, double d1, double d2) {
        EntityCamera e = (EntityCamera) entityliving;
        this.method_818(entityliving, String.format((String) "%.2f", (Object[]) new Object[] { Float.valueOf((float) e.time) }), d, d1 - 1.5, d2, 64);
    }

    @Override
    public void render(Entity entity, double x, double y, double z, float f, float f1) {
        if (DebugMode.active) {
            super.render(entity, x, y, z, f, f1);
        }
    }
}
