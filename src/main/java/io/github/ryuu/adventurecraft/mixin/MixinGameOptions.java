package io.github.ryuu.adventurecraft.mixin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import io.github.ryuu.adventurecraft.mixin.MixinTranslationStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.options.Option;
import net.minecraft.client.resource.language.TranslationStorage;
import org.lwjgl.input.Keyboard;

public class MixinGameOptions {
    private static final String[] J = new String[]{"options.renderDistance.veryFar", "options.renderDistance.far", "options.renderDistance.normal", "options.renderDistance.short", "options.renderDistance.tiny"};
    private static final String[] K = new String[]{"options.difficulty.peaceful", "options.difficulty.easy", "options.difficulty.normal", "options.difficulty.hard"};
    private static final String[] L = new String[]{"options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large"};
    private static final String[] M = new String[]{"performance.max", "performance.balanced", "performance.powersaver"};
    public float a;
    public float b;
    public float c;
    public boolean d;
    public int e;
    public boolean f;
    public boolean g;
    public boolean h;
    public int i;
    public boolean j;
    public boolean k;
    public String l;
    public KeyBinding m;
    public KeyBinding n;
    public KeyBinding o;
    public KeyBinding p;
    public KeyBinding q;
    public KeyBinding r;
    public KeyBinding s;
    public KeyBinding t;
    public KeyBinding u;
    public KeyBinding v;
    public KeyBinding[] w;
    public int y;
    public boolean z;
    public boolean A;
    public boolean B;
    public String C;
    public boolean D;
    public boolean E;
    public boolean F;
    public float G;
    public float H;
    public int I;
    public boolean autoFarClip;
    public boolean grass3d;
    protected Minecraft x;

    private File N;

    public MixinGameOptions(Minecraft minecraft, File file) {
        this.a = 1.0F;
        this.b = 1.0F;
        this.c = 0.5F;
        this.d = false;
        this.e = 1;
        this.f = true;
        this.g = false;
        this.h = false;
        this.i = 1;
        this.j = true;
        this.k = true;
        this.l = "Default";
        this.m = new KeyBinding("key.forward", 17);
        this.n = new KeyBinding("key.left", 30);
        this.o = new KeyBinding("key.back", 31);
        this.p = new KeyBinding("key.right", 32);
        this.q = new KeyBinding("key.jump", 57);
        this.r = new KeyBinding("key.inventory", 18);
        this.s = new KeyBinding("key.drop", 16);
        this.t = new KeyBinding("key.chat", 20);
        this.u = new KeyBinding("key.fog", 33);
        this.v = new KeyBinding("key.sneak", 42);
        this.w = new KeyBinding[]{this.m, this.n, this.o, this.p, this.q, this.v, this.s, this.r, this.t, this.u};
        this.y = 2;
        this.z = false;
        this.A = false;
        this.B = false;
        this.C = "";
        this.D = false;
        this.E = false;
        this.F = false;
        this.G = 1.0F;
        this.H = 1.0F;
        this.I = 0;
        this.x = minecraft;
        this.N = new File(file, "options.txt");
        this.autoFarClip = true;
        this.grass3d = true;
        a();
    }

    public MixinGameOptions() {
        this.a = 1.0F;
        this.b = 1.0F;
        this.c = 0.5F;
        this.d = false;
        this.e = 0;
        this.f = true;
        this.g = false;
        this.h = false;
        this.i = 1;
        this.j = true;
        this.k = true;
        this.l = "Default";
        this.m = new KeyBinding("key.forward", 17);
        this.n = new KeyBinding("key.left", 30);
        this.o = new KeyBinding("key.back", 31);
        this.p = new KeyBinding("key.right", 32);
        this.q = new KeyBinding("key.jump", 57);
        this.r = new KeyBinding("key.inventory", 18);
        this.s = new KeyBinding("key.drop", 16);
        this.t = new KeyBinding("key.chat", 20);
        this.u = new KeyBinding("key.fog", 33);
        this.v = new KeyBinding("key.sneak", 42);
        this.w = new KeyBinding[]{this.m, this.n, this.o, this.p, this.q, this.v, this.s, this.r, this.t, this.u};
        this.y = 2;
        this.z = false;
        this.A = false;
        this.B = false;
        this.C = "";
        this.D = false;
        this.E = false;
        this.F = false;
        this.G = 1.0F;
        this.H = 1.0F;
        this.I = 0;
    }

    public String a(int i) {
        TranslationStorage stringtranslate = TranslationStorage.a();
        return stringtranslate.a((this.w[i]).a);
    }

    public String b(int i) {
        return Keyboard.getKeyName((this.w[i]).b);
    }

    public void a(int i, int j) {
        (this.w[i]).b = j;
        b();
    }

    public void a(Option enumoptions, float f) {
        if (enumoptions == Option.a) {
            this.a = f;
            this.x.B.a();
        }
        if (enumoptions == Option.b) {
            this.b = f;
            this.x.B.a();
        }
        if (enumoptions == Option.d)
            this.c = f;
    }

    public void a(Option enumoptions, int i) {
        if (enumoptions == Option.c)
            this.d = !this.d;
        if (enumoptions == Option.e) {
            this.e = (this.e + i) % 5;
            if (this.e < 0)
                this.e = 4;
        }
        if (enumoptions == Option.m)
            this.I = this.I + i & 0x3;
        if (enumoptions == Option.f)
            this.f = !this.f;
        if (enumoptions == Option.h) {
            this.h = !this.h;
            this.x.g.a();
        }
        if (enumoptions == Option.g) {
            this.g = !this.g;
            this.x.p.b();
        }
        if (enumoptions == Option.i)
            this.i = (this.i + i + 3) % 3;
        if (enumoptions == Option.j)
            this.y = this.y + i & 0x3;
        if (enumoptions == Option.k) {
            this.j = !this.j;
            this.x.g.a();
        }
        if (enumoptions == Option.l) {
            this.k = !this.k;
            this.x.g.a();
        }
        if (enumoptions == Option.AUTO_FAR_CLIP)
            this.autoFarClip = !this.autoFarClip;
        if (enumoptions == Option.GRASS_3D) {
            this.grass3d = !this.grass3d;
            this.x.g.a();
        }
        b();
    }

    public float a(Option enumoptions) {
        if (enumoptions == Option.a)
            return this.a;
        if (enumoptions == Option.b)
            return this.b;
        if (enumoptions == Option.d)
            return this.c;
        return 0.0F;
    }

    public boolean b(Option enumoptions) {
        switch (fq.a[enumoptions.ordinal()]) {
            case 1:
                return this.d;
            case 2:
                return this.f;
            case 3:
                return this.g;
            case 4:
                return this.h;
            case 5:
                return this.k;
            case 7:
                return this.autoFarClip;
            case 8:
                return this.grass3d;
        }
        return false;
    }

    public String c(Option enumoptions) {
        TranslationStorage stringtranslate = TranslationStorage.a();
        String s = stringtranslate.a(enumoptions.d()) + ": ";
        if (enumoptions.a()) {
            float f = a(enumoptions);
            if (enumoptions == Option.d) {
                if (f == 0.0F)
                    return s + stringtranslate.a("options.sensitivity.min");
                if (f == 1.0F)
                    return s + stringtranslate.a("options.sensitivity.max");
                return s + (int) (f * 200.0F) + "%";
            }
            if (f == 0.0F)
                return s + stringtranslate.a("options.off");
            return s + (int) (f * 100.0F) + "%";
        }
        if (enumoptions.b()) {
            boolean flag = b(enumoptions);
            if (flag)
                return s + stringtranslate.a("options.on");
            return s + stringtranslate.a("options.off");
        }
        if (enumoptions == Option.e)
            return s + stringtranslate.a(J[this.e]);
        if (enumoptions == Option.j)
            return s + stringtranslate.a(K[this.y]);
        if (enumoptions == Option.m)
            return s + stringtranslate.a(L[this.I]);
        if (enumoptions == Option.i)
            return s + do.a(M[this.i]);
        if (enumoptions == Option.k) {
            if (this.j)
                return s + stringtranslate.a("options.graphics.fancy");
            return s + stringtranslate.a("options.graphics.fast");
        }
        if (enumoptions == Option.AUTO_FAR_CLIP)
            return "Auto Far Clip: " + (this.autoFarClip ? "ON" : "OFF");
        if (enumoptions == Option.GRASS_3D)
            return "Grass 3D: " + (this.grass3d ? "ON" : "OFF");
        return s;
    }

    public void a() {
        try {
            if (!this.N.exists())
                return;
            BufferedReader bufferedreader = new BufferedReader(new FileReader(this.N));
            for (String s = ""; (s = bufferedreader.readLine()) != null; ) {
                try {
                    String[] as = s.split(":");
                    if (as[0].equals("music"))
                        this.a = a(as[1]);
                    if (as[0].equals("sound"))
                        this.b = a(as[1]);
                    if (as[0].equals("mouseSensitivity"))
                        this.c = a(as[1]);
                    if (as[0].equals("invertYMouse"))
                        this.d = as[1].equals("true");
                    if (as[0].equals("viewDistance"))
                        this.e = Integer.parseInt(as[1]);
                    if (as[0].equals("guiScale"))
                        this.I = Integer.parseInt(as[1]);
                    if (as[0].equals("bobView"))
                        this.f = as[1].equals("true");
                    if (as[0].equals("anaglyph3d"))
                        this.g = as[1].equals("true");
                    if (as[0].equals("advancedOpengl"))
                        this.h = as[1].equals("true");
                    if (as[0].equals("fpsLimit"))
                        this.i = Integer.parseInt(as[1]);
                    if (as[0].equals("difficulty"))
                        this.y = Integer.parseInt(as[1]);
                    if (as[0].equals("fancyGraphics"))
                        this.j = as[1].equals("true");
                    if (as[0].equals("ao"))
                        this.k = as[1].equals("true");
                    if (as[0].equals("skin"))
                        this.l = as[1];
                    if (as[0].equals("lastServer") && as.length >= 2)
                        this.C = as[1];
                    if (as[0].equals("autoFarClip"))
                        this.autoFarClip = as[1].equals("true");
                    if (as[0].equals("grass3d"))
                        this.grass3d = as[1].equals("true");
                    int i = 0;
                    while (i < this.w.length) {
                        if (as[0].equals("key_" + (this.w[i]).a))
                            (this.w[i]).b = Integer.parseInt(as[1]);
                        i++;
                    }
                } catch (Exception exception1) {
                    System.out.println("Skipping bad option: " + s);
                }
            }
            bufferedreader.close();
        } catch (Exception exception) {
            System.out.println("Failed to load options");
            exception.printStackTrace();
        }
    }

    private float a(String s) {
        if (s.equals("true"))
            return 1.0F;
        if (s.equals("false"))
            return 0.0F;
        return Float.parseFloat(s);
    }

    public void b() {
        try {
            PrintWriter printwriter = new PrintWriter(new FileWriter(this.N));
            printwriter.println("music:" + this.a);
            printwriter.println("sound:" + this.b);
            printwriter.println("invertYMouse:" + this.d);
            printwriter.println("mouseSensitivity:" + this.c);
            printwriter.println("viewDistance:" + this.e);
            printwriter.println("guiScale:" + this.I);
            printwriter.println("bobView:" + this.f);
            printwriter.println("anaglyph3d:" + this.g);
            printwriter.println("advancedOpengl:" + this.h);
            printwriter.println("fpsLimit:" + this.i);
            printwriter.println("difficulty:" + this.y);
            printwriter.println("fancyGraphics:" + this.j);
            printwriter.println("ao:" + this.k);
            printwriter.println("skin:" + this.l);
            printwriter.println("lastServer:" + this.C);
            printwriter.println("autoFarClip:" + this.autoFarClip);
            printwriter.println("grass3d:" + this.grass3d);
            for (int i = 0; i < this.w.length; i++)
                printwriter.println("key_" + (this.w[i]).a + ":" + (this.w[i]).b);
            printwriter.close();
        } catch (Exception exception) {
            System.out.println("Failed to save options");
            exception.printStackTrace();
        }
    }
}
