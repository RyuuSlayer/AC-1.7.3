package io.github.ryuu.adventurecraft.mixin.overrides;

import net.minecraft.class_97;
import net.minecraft.client.gui.screen.SelectWorldScreen;
import net.minecraft.client.render.Tessellator;
import net.minecraft.level.storage.LevelMetadata;
import net.minecraft.util.maths.MathsHelper;

import java.util.Date;

class mg extends class_97 {
    final net.minecraft.client.gui.screen.SelectWorldScreen a;

    public mg(SelectWorldScreen guiselectworld) {
        super(guiselectworld.b, guiselectworld.c, guiselectworld.d, 32, guiselectworld.d - 32, 36);
        this.a = guiselectworld;
    }

    protected int a() {
        return SelectWorldScreen.a(this.a).size();
    }

    protected void a(int i, boolean flag) {
        SelectWorldScreen.a(this.a, i);
        boolean flag1 = (SelectWorldScreen.b(this.a) >= 0 && SelectWorldScreen.b(this.a) < a());
        (SelectWorldScreen.c(this.a)).g = flag1;
        (SelectWorldScreen.e(this.a)).g = flag1;
        if (flag && flag1)
            this.a.e(i);
    }

    protected boolean c_(int i) {
        return (i == SelectWorldScreen.b(this.a));
    }

    protected int b() {
        return SelectWorldScreen.a(this.a).size() * 36;
    }

    protected void c() {
        this.a.i();
    }

    protected void a(int i, int j, int k, int l, Tessellator tessellator) {
        LevelMetadata saveformatcomparator = SelectWorldScreen.a(this.a).get(i);
        String s = saveformatcomparator.b();
        if (s == null || MathsHelper.a(s))
            s = SelectWorldScreen.f(this.a) + " " + (i + 1);
        String s1 = saveformatcomparator.a();
        s1 = s1 + " (" + SelectWorldScreen.g(this.a).format(new Date(saveformatcomparator.e()));
        long l1 = saveformatcomparator.c();
        s1 = s1 + ", " + ((float) (l1 / 1024L * 100L / 1024L) / 100.0F) + " MB)";
        String s2 = "";
        if (saveformatcomparator.d())
            s2 = SelectWorldScreen.h(this.a) + " " + s2;
        this.a.b(this.a.g, s, j + 2, k + 1, 16777215);
        this.a.b(this.a.g, s1, j + 2, k + 12, 8421504);
        this.a.b(this.a.g, s2, j + 2, k + 12 + 10, 8421504);
    }
}
