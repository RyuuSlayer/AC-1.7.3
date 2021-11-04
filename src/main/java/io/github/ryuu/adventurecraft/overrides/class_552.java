package io.github.ryuu.adventurecraft.overrides;

import net.minecraft.class_290;
import net.minecraft.client.render.Tessellator;
import net.minecraft.util.maths.Vec3f;

public class class_552 {
    public class_290[] a;

    public int b;

    private final boolean c;

    public class_552(class_290[] apositiontexturevertex) {
        this.b = 0;
        this.c = false;
        this.a = apositiontexturevertex;
        this.b = apositiontexturevertex.length;
    }

    public class_552(class_290[] apositiontexturevertex, int i, int j, int k, int l) {
        this(apositiontexturevertex, i, j, k, l, 64, 32);
    }

    public class_552(class_290[] apositiontexturevertex, int i, int j, int k, int l, int tw, int th) {
        this(apositiontexturevertex);
        float f = 0.0015625F;
        float f1 = 0.003125F;
        if (k < i)
            f = -f;
        if (l < j)
            f1 = -f1;
        apositiontexturevertex[0] = apositiontexturevertex[0].a(k / tw - f, j / th + f1);
        apositiontexturevertex[1] = apositiontexturevertex[1].a(i / tw + f, j / th + f1);
        apositiontexturevertex[2] = apositiontexturevertex[2].a(i / tw + f, l / th - f1);
        apositiontexturevertex[3] = apositiontexturevertex[3].a(k / tw - f, l / th - f1);
    }

    public void a() {
        class_290[] apositiontexturevertex = new class_290[this.a.length];
        for (int i = 0; i < this.a.length; i++)
            apositiontexturevertex[i] = this.a[this.a.length - i - 1];
        this.a = apositiontexturevertex;
    }

    public void a(Tessellator tessellator, float f) {
        Vec3f vec3d = (this.a[1]).a.a((this.a[0]).a);
        Vec3f vec3d1 = (this.a[1]).a.a((this.a[2]).a);
        Vec3f vec3d2 = vec3d1.b(vec3d).c();
        tessellator.b();
        if (this.c) {
            tessellator.b(-((float) vec3d2.a), -((float) vec3d2.b), -((float) vec3d2.c));
        } else {
            tessellator.b((float) vec3d2.a, (float) vec3d2.b, (float) vec3d2.c);
        }
        for (int i = 0; i < 4; i++) {
            class_290 positiontexturevertex = this.a[i];
            tessellator.a(((float) positiontexturevertex.a.a * f), ((float) positiontexturevertex.a.b * f), ((float) positiontexturevertex.a.c * f), positiontexturevertex.b, positiontexturevertex.c);
        }
        tessellator.a();
    }
}
