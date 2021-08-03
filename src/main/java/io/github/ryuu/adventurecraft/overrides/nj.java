package io.github.ryuu.adventurecraft.overrides;

public class nj extends da {
    private da i;

    protected String a;

    private kv j;

    public nj(da guiscreen, kv gamesettings) {
        this.a = "Video Settings";
        this.i = guiscreen;
        this.j = gamesettings;
    }

    public void b() {
        nh stringtranslate = nh.a();
        this.a = stringtranslate.a("options.videoTitle");
        int i = 0;
        ht[] aenumoptions = l;
        int j = aenumoptions.length;
        for (int k = 0; k < j; k++) {
            ht enumoptions = aenumoptions[k];
            if (!enumoptions.a()) {
                this.e.add(new ab(enumoptions.c(), this.c / 2 - 155 + i % 2 * 160, this.d / 6 + 24 * (i >> 1), enumoptions, this.j.c(enumoptions)));
            } else {
                this.e.add(new vz(enumoptions.c(), this.c / 2 - 155 + i % 2 * 160, this.d / 6 + 24 * (i >> 1), enumoptions, this.j.c(enumoptions), this.j.a(enumoptions)));
            }
            i++;
        }
        this.e.add(new ke(200, this.c / 2 - 100, this.d / 6 + 168, stringtranslate.a("gui.done")));
    }

    protected void a(ke guibutton) {
        if (!guibutton.g)
            return;
        if (guibutton.f < 100 && guibutton instanceof ab) {
            this.j.a(((ab)guibutton).a(), 1);
            guibutton.e = this.j.c(ht.a(guibutton.f));
        }
        if (guibutton.f == 200) {
            this.b.z.b();
            this.b.a(this.i);
        }
        qq scaledresolution = new qq(this.b.z, this.b.d, this.b.e);
        int i = scaledresolution.a();
        int j = scaledresolution.b();
        a(this.b, i, j);
    }

    public void a(int i, int j, float f) {
        i();
        a(this.g, this.a, this.c / 2, 20, 16777215);
        super.a(i, j, f);
    }

    private static ht[] l = new ht[] { ht.k, ht.e, ht.l, ht.i, ht.g, ht.f, ht.m, ht.h, ht.AUTO_FAR_CLIP, ht.GRASS_3D };
}
