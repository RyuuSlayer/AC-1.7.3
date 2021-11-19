/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 */
package io.github.ryuu.adventurecraft.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_290;
import net.minecraft.client.render.Tessellator;
import net.minecraft.util.maths.Vec3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Class_552.class)
public class MixinClass_552 {

    @Shadow()
    public class_290[] field_2518;

    public int field_2519 = 0;

    private boolean field_2520 = false;

    public MixinClass_552(class_290[] apositiontexturevertex) {
        this.field_2518 = apositiontexturevertex;
        this.field_2519 = apositiontexturevertex.length;
    }

    public MixinClass_552(class_290[] apositiontexturevertex, int i, int j, int k, int l) {
        this(apositiontexturevertex, i, j, k, l, 64, 32);
    }

    public MixinClass_552(class_290[] apositiontexturevertex, int i, int j, int k, int l, int tw, int th) {
        this(apositiontexturevertex);
        float f = 0.0015625f;
        float f1 = 0.003125f;
        if (k < i) {
            f = -f;
        }
        if (l < j) {
            f1 = -f1;
        }
        apositiontexturevertex[0] = apositiontexturevertex[0].method_983((float) k / (float) tw - f, (float) j / (float) th + f1);
        apositiontexturevertex[1] = apositiontexturevertex[1].method_983((float) i / (float) tw + f, (float) j / (float) th + f1);
        apositiontexturevertex[2] = apositiontexturevertex[2].method_983((float) i / (float) tw + f, (float) l / (float) th - f1);
        apositiontexturevertex[3] = apositiontexturevertex[3].method_983((float) k / (float) tw - f, (float) l / (float) th - f1);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1925() {
        class_290[] apositiontexturevertex = new class_290[this.field_2518.length];
        for (int i = 0; i < this.field_2518.length; ++i) {
            apositiontexturevertex[i] = this.field_2518[this.field_2518.length - i - 1];
        }
        this.field_2518 = apositiontexturevertex;
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Overwrite()
    public void method_1926(Tessellator tessellator, float f) {
        Vec3f vec3d = this.field_2518[1].field_1146.method_1307(this.field_2518[0].field_1146);
        Vec3f vec3d1 = this.field_2518[1].field_1146.method_1307(this.field_2518[2].field_1146);
        Vec3f vec3d2 = vec3d1.method_1309(vec3d).method_1296();
        tessellator.start();
        if (this.field_2520) {
            tessellator.method_1697(-((float) vec3d2.x), -((float) vec3d2.y), -((float) vec3d2.z));
        } else {
            tessellator.method_1697((float) vec3d2.x, (float) vec3d2.y, (float) vec3d2.z);
        }
        for (int i = 0; i < 4; ++i) {
            class_290 positiontexturevertex = this.field_2518[i];
            tessellator.vertex((float) positiontexturevertex.field_1146.x * f, (float) positiontexturevertex.field_1146.y * f, (float) positiontexturevertex.field_1146.z * f, positiontexturevertex.field_1147, positiontexturevertex.field_1148);
        }
        tessellator.draw();
    }
}
