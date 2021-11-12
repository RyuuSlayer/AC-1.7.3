package io.github.ryuu.adventurecraft.mixin;

import io.github.ryuu.adventurecraft.gui.GuiMapSelect;
import net.minecraft.class_520;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.DeleteConfirmationScreen;
import net.minecraft.client.gui.screen.EditLevelScreen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.level.storage.LevelMetadata;
import net.minecraft.level.storage.LevelStorage;
import net.minecraft.util.maths.MathsHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

public class MixinSelectWorldScreen extends Screen {
    private final DateFormat j;

    protected Screen a;

    protected String i;

    private boolean l;

    private int m;

    private List n;

    private class_569 o;

    private String p;

    private String q;

    private boolean r;

    private Button s;

    private Button t;

    private Button u;

    public MixinSelectWorldScreen(Screen guiscreen) {
        this.j = new SimpleDateFormat();
        this.i = "Select world";
        this.l = false;
        this.a = guiscreen;
    }

    static List a(MixinSelectWorldScreen guiselectworld) {
        return guiselectworld.n;
    }

    static int a(MixinSelectWorldScreen guiselectworld, int i) {
        return guiselectworld.m = i;
    }

    static int b(MixinSelectWorldScreen guiselectworld) {
        return guiselectworld.m;
    }

    static Button c(MixinSelectWorldScreen guiselectworld) {
        return guiselectworld.t;
    }

    static Button d(MixinSelectWorldScreen guiselectworld) {
        return guiselectworld.s;
    }

    static Button e(MixinSelectWorldScreen guiselectworld) {
        return guiselectworld.u;
    }

    static String f(MixinSelectWorldScreen guiselectworld) {
        return guiselectworld.p;
    }

    static DateFormat g(MixinSelectWorldScreen guiselectworld) {
        return guiselectworld.j;
    }

    static String h(MixinSelectWorldScreen guiselectworld) {
        return guiselectworld.q;
    }

    public void b() {
        TranslationStorage stringtranslate = TranslationStorage.a();
        this.i = stringtranslate.translate("selectWorld.title");
        this.p = stringtranslate.translate("selectWorld.world");
        this.q = stringtranslate.translate("selectWorld.conversion");
        l();
        this.o = new class_569(this);
        this.o.a(this.e, 4, 5);
        k();
    }

    private void l() {
        LevelStorage isaveformat = this.b.c();
        this.n = isaveformat.b();
        Collections.sort(this.n);
        this.m = -1;
    }

    protected String c(int i) {
        return ((LevelMetadata) this.n.get(i)).a();
    }

    protected String d(int i) {
        String s = ((LevelMetadata) this.n.get(i)).b();
        if (s == null || MathsHelper.a(s)) {
            TranslationStorage stringtranslate = TranslationStorage.a();
            s = stringtranslate.translate("selectWorld.world") + " " + (i + 1);
        }
        return s;
    }

    public void k() {
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        this.e.add(this.t = new Button(1, this.c / 2 - 152, this.d - 28, 100, 20, "Load Save"));
        this.e.add(this.u = new Button(2, this.c / 2 - 50, this.d - 28, 100, 20, stringtranslate.a("selectWorld.delete")));
        this.e.add(new Button(0, this.c / 2 + 52, this.d - 28, 100, 20, stringtranslate.a("gui.cancel")));
        this.t.g = false;
        this.u.g = false;
    }

    protected void a(Button guibutton) {
        if (!guibutton.g)
            return;
        if (guibutton.f == 2) {
            String s = d(this.m);
            if (s != null) {
                this.r = true;
                TranslationStorage stringtranslate = TranslationStorage.getInstance();
                String s1 = stringtranslate.a("selectWorld.deleteQuestion");
                String s2 = "'" + s + "' " + stringtranslate.a("selectWorld.deleteWarning");
                String s3 = stringtranslate.a("selectWorld.deleteButton");
                String s4 = stringtranslate.a("gui.cancel");
                DeleteConfirmationScreen guiyesno = new DeleteConfirmationScreen(this, s1, s2, s3, s4, this.m);
                this.b.a((Screen) guiyesno);
            }
        } else if (guibutton.f == 1) {
            e(this.m);
        } else if (guibutton.f == 3) {
            this.b.a((Screen) new GuiMapSelect(this, ""));
        } else if (guibutton.f == 6) {
            this.b.a((Screen) new EditLevelScreen(this, c(this.m)));
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
        this.b.c = (ob) new class_520(this.b);
        String s = c(i);
        if (s == null)
            s = "World" + i;
        this.b.a(s, d(i), 0L);
    }

    public void a(boolean flag, int i) {
        if (this.r) {
            this.r = false;
            if (flag) {
                LevelStorage isaveformat = this.b.c();
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
}
