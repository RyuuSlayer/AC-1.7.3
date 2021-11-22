package io.github.ryuu.adventurecraft.mixin.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityCollisionParticle.class)
public class MixinEntityCollisionParticle extends Particle {

    @Shadow()
    private Entity field_537;

    private Entity field_538;

    private int field_539 = 0;

    private int field_540 = 0;

    private float field_541;

    public MixinEntityCollisionParticle(Level world, Entity entity, Entity entity1, float f) {
        super(world, entity.x, entity.y, entity.z, entity.velocityX, entity.velocityY, entity.velocityZ);
        this.field_537 = entity;
        this.field_538 = entity1;
        this.field_540 = 3;
        this.field_541 = f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_2002(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        float f6 = ((float) this.field_539 + f) / (float) this.field_540;
        f6 *= f6;
        double d = this.field_537.x;
        double d1 = this.field_537.y;
        double d2 = this.field_537.z;
        double d3 = this.field_538.prevRenderX + (this.field_538.x - this.field_538.prevRenderX) * (double) f;
        double d4 = this.field_538.prevRenderY + (this.field_538.y - this.field_538.prevRenderY) * (double) f + (double) this.field_541;
        double d5 = this.field_538.prevRenderZ + (this.field_538.z - this.field_538.prevRenderZ) * (double) f;
        double d6 = d + (d3 - d) * (double) f6;
        double d7 = d1 + (d4 - d1) * (double) f6;
        double d8 = d2 + (d5 - d2) * (double) f6;
        int i = MathsHelper.floor(d6);
        int j = MathsHelper.floor(d7 + (double) (this.standingEyeHeight / 2.0f));
        int k = MathsHelper.floor(d8);
        float f7 = this.level.getBrightness(i, j, k);
        GL11.glColor4f((float) f7, (float) f7, (float) f7, (float) 1.0f);
        EntityRenderDispatcher.INSTANCE.method_1920(this.field_537, (float) (d6 -= field_2645), (float) (d7 -= field_2646), (float) (d8 -= field_2647), this.field_537.yaw, f);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int method_2003() {
        return 5;
    }
}
