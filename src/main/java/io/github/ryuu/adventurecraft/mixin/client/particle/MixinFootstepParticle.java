/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.lwjgl.opengl.GL11
 */
package io.github.ryuu.adventurecraft.mixin.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.FootstepParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;
import io.github.ryuu.adventurecraft.mixin.item.MixinLevel;
import io.github.ryuu.adventurecraft.mixin.item.MixinTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FootstepParticle.class)
public class MixinFootstepParticle extends Particle {

    @Shadow()
    private int field_849 = 0;

    private int field_850 = 0;

    private MixinTextureManager field_851;

    public MixinFootstepParticle(MixinTextureManager renderengine, MixinLevel world, double d, double d1, double d2) {
        super(world, d, d1, d2, 0.0, 0.0, 0.0);
        this.field_851 = renderengine;
        this.velocityZ = 0.0;
        this.velocityY = 0.0;
        this.velocityX = 0.0;
        this.field_850 = 200;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_2002(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        float f7;
        float f6 = ((float) this.field_849 + f) / (float) this.field_850;
        if ((f7 = 2.0f - (f6 *= f6) * 2.0f) > 1.0f) {
            f7 = 1.0f;
        }
        f7 *= 0.2f;
        GL11.glDisable((int) 2896);
        float f8 = 0.125f;
        float f9 = (float) (this.x - field_2645);
        float f10 = (float) (this.y - field_2646);
        float f11 = (float) (this.z - field_2647);
        float f12 = this.level.getBrightness(MathsHelper.floor(this.x), MathsHelper.floor(this.y), MathsHelper.floor(this.z));
        this.field_851.bindTexture(this.field_851.getTextureId("/misc/footprint.png"));
        GL11.glEnable((int) 3042);
        GL11.glBlendFunc((int) 770, (int) 771);
        tessellator.start();
        tessellator.colour(f12, f12, f12, f7);
        tessellator.vertex(f9 - f8, f10, f11 + f8, 0.0, 1.0);
        tessellator.vertex(f9 + f8, f10, f11 + f8, 1.0, 1.0);
        tessellator.vertex(f9 + f8, f10, f11 - f8, 1.0, 0.0);
        tessellator.vertex(f9 - f8, f10, f11 - f8, 0.0, 0.0);
        tessellator.draw();
        GL11.glDisable((int) 3042);
        GL11.glEnable((int) 2896);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void tick() {
        ++this.field_849;
        if (this.field_849 == this.field_850) {
            this.remove();
        }
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
