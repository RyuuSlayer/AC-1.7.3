package io.github.ryuu.adventurecraft.mixin.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TileParticle.class)
public class MixinTileParticle extends Particle {

    @Shadow()
    private Tile field_2383;

    public MixinTileParticle(Level world, double d, double d1, double d2, double d3, double d4, double d5, Tile block, int i, int j) {
        super(world, d, d1, d2, d3, d4, d5);
        this.field_2383 = block;
        this.field_2635 = block.getTextureForSide(i, j);
        this.field_2641 = block.field_1927;
        this.field_2644 = 0.6f;
        this.field_2643 = 0.6f;
        this.field_2642 = 0.6f;
        this.field_2640 /= 2.0f;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public TileParticle method_1856(int i, int j, int k) {
        if (this.field_2383 == Tile.GRASS) {
            return this;
        }
        int l = this.field_2383.getTint(this.level, i, j, k);
        this.field_2642 *= (float) (l >> 16 & 0xFF) / 255.0f;
        this.field_2643 *= (float) (l >> 8 & 0xFF) / 255.0f;
        this.field_2644 *= (float) (l & 0xFF) / 255.0f;
        return this;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public int method_2003() {
        int t = this.field_2383.getTextureNum();
        if (t == 2) {
            return 3;
        }
        if (t == 3) {
            return 4;
        }
        return 1;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void method_2002(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5) {
        float f6 = ((float) (this.field_2635 % 16) + this.field_2636 / 4.0f) / 16.0f;
        float f7 = f6 + 0.01560938f;
        float f8 = ((float) (this.field_2635 / 16) + this.field_2637 / 4.0f) / 16.0f;
        float f9 = f8 + 0.01560938f;
        float f10 = 0.1f * this.field_2640;
        float f11 = (float) (this.prevX + (this.x - this.prevX) * (double) f - field_2645);
        float f12 = (float) (this.prevY + (this.y - this.prevY) * (double) f - field_2646);
        float f13 = (float) (this.prevZ + (this.z - this.prevZ) * (double) f - field_2647);
        float f14 = this.getBrightnessAtEyes(f);
        tessellator.colour(f14 * this.field_2642, f14 * this.field_2643, f14 * this.field_2644);
        tessellator.vertex(f11 - f1 * f10 - f4 * f10, f12 - f2 * f10, f13 - f3 * f10 - f5 * f10, f6, f9);
        tessellator.vertex(f11 - f1 * f10 + f4 * f10, f12 + f2 * f10, f13 - f3 * f10 + f5 * f10, f6, f8);
        tessellator.vertex(f11 + f1 * f10 + f4 * f10, f12 + f2 * f10, f13 + f3 * f10 + f5 * f10, f7, f8);
        tessellator.vertex(f11 + f1 * f10 - f4 * f10, f12 - f2 * f10, f13 + f3 * f10 - f5 * f10, f7, f9);
    }
}
