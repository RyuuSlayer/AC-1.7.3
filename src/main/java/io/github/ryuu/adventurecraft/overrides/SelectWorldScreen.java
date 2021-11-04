package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.gui.GuiMapSelect;
import io.github.ryuu.adventurecraft.mixin.MixinTranslationStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class SelectWorldScreen extends da {
    private final DateFormat j;

    protected da a;

    protected String i;

    private boolean l;

    private int m;

    private List n;

    private mg o;

    private String p;

    private String q;

    private boolean r;

    private ke s;

    private ke t;

    private ke u;

    public SelectWorldScreen(da guiscreen) {
        this.j = new SimpleDateFormat();
        this.i = "Select world";
        this.l = false;
        this.a = guiscreen;
    }

    public void b() {
        MixinTranslationStorage stringtranslate = MixinTranslationStorage.a();
        this.i = stringtranslate.a("selectWorld.title");
        this.p = stringtranslate.a("selectWorld.world");
        this.q = stringtranslate.a("selectWorld.conversion");
        l();
        this.o = new mg(this);
        this.o.a(this.e, 4, 5);
        k();
    }

    private void l() {
        nl isaveformat = this.b.c();
        this.n = isaveformat.b();
        Collections.sort(this.n);
        this.m = -1;
    }

    protected String c(int i) {
        return ((vb) this.n.get(i)).a();
    }

    protected String d(int i) {
        String s = ((vb) this.n.get(i)).b();
        if (s == null || in.a(s)) {
            MixinTranslationStorage stringtranslate = MixinTranslationStorage.a();
            s = stringtranslate.a("selectWorld.world") + " " + (i + 1);
        }
        return s;
    }

    public void k() {
        MixinTranslationStorage stringtranslate = MixinTranslationStorage.a();
        this.e.add(this.t = new ke(1, this.c / 2 - 152, this.d - 28, 100, 20, "Load Save"));
        this.e.add(this.u = new ke(2, this.c / 2 - 50, this.d - 28, 100, 20, stringtranslate.a("selectWorld.delete")));
        this.e.add(new ke(0, this.c / 2 + 52, this.d - 28, 100, 20, stringtranslate.a("gui.cancel")));
        this.t.g = false;
        this.u.g = false;
    }

    protected void a(ke guibutton) {
        if (!guibutton.g)
            return;
        if (guibutton.f == 2) {
            String s = d(this.m);
            if (s != null) {
                this.r = true;
                MixinTranslationStorage stringtranslate = MixinTranslationStorage.a();
                String s1 = stringtranslate.a("selectWorld.deleteQuestion");
                String s2 = "'" + s + "' " + stringtranslate.a("selectWorld.deleteWarning");
                String s3 = stringtranslate.a("selectWorld.deleteButton");
                String s4 = stringtranslate.a("gui.cancel");
                qt guiyesno = new qt(this, s1, s2, s3, s4, this.m);
                this.b.a((da) guiyesno);
            }
        } else if (guibutton.f == 1) {
            e(this.m);
        } else if (guibutton.f == 3) {
            this.b.a((da) new GuiMapSelect(this, ""));
        } else if (guibutton.f == 6) {
            this.b.a((da) new jk(this, c(this.m)));
        } else if (guibutton.f == 0) {
            this.b.a(this.a);
        } else {
            this.o.a(guibutton);
        }
    }

    public void e(int i) {
        this.b.a(null);
        if (this.l)
            return;
        this.l = true;
        this.b.c = (ob) new os(this.b);
        String s = c(i);
        if (s == null)
            s = "World" + i;
        this.b.a(s, d(i), 0L);
    }

    public void a(boolean flag, int i) {
        if (this.r) {
            this.r = false;
            if (flag) {
                nl isaveformat = this.b.c();
                isaveformat.c();
                isaveformat.c(c(i));
                l();
            }
            this.b.a(this);
        }
    }

    public void a(int i, int j, float f) {
        this.o.a(i, j, f);
        a(this.g, this.i, this.c / 2, 20, 16777215);
        super.a(i, j, f);
    }

    static List a(SelectWorldScreen guiselectworld) {
        return guiselectworld.n;
    }

    static int a(SelectWorldScreen guiselectworld, int i) {
        return guiselectworld.m = i;
    }

    static int b(SelectWorldScreen guiselectworld) {
        return guiselectworld.m;
    }

    static ke c(SelectWorldScreen guiselectworld) {
        return guiselectworld.t;
    }

    static ke d(SelectWorldScreen guiselectworld) {
        return guiselectworld.s;
    }

    static ke e(SelectWorldScreen guiselectworld) {
        return guiselectworld.u;
    }

    static String f(SelectWorldScreen guiselectworld) {
        return guiselectworld.p;
    }

    static DateFormat g(SelectWorldScreen guiselectworld) {
        return guiselectworld.j;
    }

    static String h(SelectWorldScreen guiselectworld) {
        return guiselectworld.q;
    }
}
