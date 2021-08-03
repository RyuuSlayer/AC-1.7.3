package io.github.ryuu.adventurecraft.overrides;

import java.util.Date;

class mg extends lg {
    final rq a;

    public mg(rq guiselectworld) {
        super(guiselectworld.b, guiselectworld.c, guiselectworld.d, 32, guiselectworld.d - 32, 36);
        this.a = guiselectworld;
    }

    protected int a() {
        return rq.a(this.a).size();
    }

    protected void a(int i, boolean flag) {
        rq.a(this.a, i);
        boolean flag1 = (rq.b(this.a) >= 0 && rq.b(this.a) < a());
        (rq.c(this.a)).g = flag1;
        (rq.e(this.a)).g = flag1;
        if (flag && flag1)
            this.a.e(i);
    }

    protected boolean c_(int i) {
        return (i == rq.b(this.a));
    }

    protected int b() {
        return rq.a(this.a).size() * 36;
    }

    protected void c() {
        this.a.i();
    }

    protected void a(int i, int j, int k, int l, nw tessellator) {
        vb saveformatcomparator = rq.a(this.a).get(i);
        String s = saveformatcomparator.b();
        if (s == null || in.a(s))
            s = rq.f(this.a) + " " + (i + 1);
        String s1 = saveformatcomparator.a();
        s1 = s1 + " (" + rq.g(this.a).format(new Date(saveformatcomparator.e()));
        long l1 = saveformatcomparator.c();
        s1 = s1 + ", " + ((float) (l1 / 1024L * 100L / 1024L) / 100.0F) + " MB)";
        String s2 = "";
        if (saveformatcomparator.d())
            s2 = rq.h(this.a) + " " + s2;
        this.a.b(this.a.g, s, j + 2, k + 1, 16777215);
        this.a.b(this.a.g, s1, j + 2, k + 12, 8421504);
        this.a.b(this.a.g, s2, j + 2, k + 12 + 10, 8421504);
    }
}
