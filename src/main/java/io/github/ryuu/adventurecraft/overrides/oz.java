package io.github.ryuu.adventurecraft.overrides;

public class oz extends da {
    private int a = 0;

    private int i = 0;

    public void b() {
        this.a = 0;
        this.e.clear();
        byte byte0 = -16;
        this.e.add(new ke(1, this.c / 2 - 100, this.d / 4 + 120 + byte0, "Save and quit to title"));
        if (this.b.l())
            ((ke) this.e.get(0)).e = "Disconnect";
        this.e.add(new ke(4, this.c / 2 - 100, this.d / 4 + 24 + byte0, "Back to game"));
        this.e.add(new ke(0, this.c / 2 - 100, this.d / 4 + 96 + byte0, "Options..."));
        this.e.add(new ke(5, this.c / 2 - 100, this.d / 4 + 48 + byte0, 98, 20, do.a("gui.achievements")))
        this.e.add(new ke(6, this.c / 2 + 2, this.d / 4 + 48 + byte0, 98, 20, do.a("gui.stats")))
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0)
            this.b.a((da) new co(this, this.b.z));
        if (guibutton.f == 1) {
            this.b.I.a(jl.j, 1);
            if (this.b.l())
                this.b.f.q();
            this.b.a(null);
            this.b.v.scriptUI.clear();
            this.b.cameraActive = false;
            this.b.a((da) new fu());
            this.b.B.stopMusic();
        }
        if (guibutton.f == 4) {
            this.b.a(null);
            this.b.g();
        }
        if (guibutton.f == 5)
            this.b.a((da) new xm(this.b.I));
        if (guibutton.f == 6)
            this.b.a((da) new dv(this, this.b.I));
    }

    public void a() {
        super.a();
        this.i++;
    }

    public void a(int i, int j, float f) {
        i();
        boolean flag = !this.b.f.a(this.a++);
        if (flag || this.i < 20) {
            float f1 = ((this.i % 10) + f) / 10.0F;
            f1 = in.a(f1 * 3.141593F * 2.0F) * 0.2F + 0.8F;
            int k = (int) (255.0F * f1);
            b(this.g, "Saving level..", 8, this.d - 16, k << 16 | k << 8 | k);
        }
        a(this.g, "Game menu", this.c / 2, 40, 16777215);
        super.a(i, j, f);
    }
}
