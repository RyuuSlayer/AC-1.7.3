package io.github.ryuu.adventurecraft.overrides;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import io.github.ryuu.adventurecraft.scripting.ScriptModel;
import io.github.ryuu.adventurecraft.util.Version;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class fu extends da {
    public fu() {
        ScriptModel.clearAll();
        Minecraft.minecraftInstance.B.stopMusic();
        this.i = 0.0F;
        this.j = "missingno";
        try {
            ArrayList<String> arraylist = new ArrayList();
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(fu.class.getResourceAsStream("/title/splashes.txt"), StandardCharsets.UTF_8));
            String s = "";
            String s1;
            while ((s1 = bufferedreader.readLine()) != null) {
                s1 = s1.trim();
                if (s1.length() > 0)
                    arraylist.add(s1);
            }
            this.j = arraylist.get(a.nextInt(arraylist.size()));
        } catch (Exception exception) {
        }
    }

    public void a() {
        this.i++;
    }

    protected void a(char c, int i) {
    }

    public void b() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(2) + 1 == 11 && calendar.get(5) == 9) {
            this.j = "Happy birthday, ez!";
        } else if (calendar.get(2) + 1 == 6 && calendar.get(5) == 1) {
            this.j = "Happy birthday, Notch!";
        } else if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
            this.j = "Merry X-mas!";
        } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
            this.j = "Happy new year!";
        }
        this.j = "A Minecraft Total Conversion!";
        nh stringtranslate = nh.a();
        int i = this.d / 4 + 48;
        this.e.add(new ke(6, this.c / 2 - 100, i, "New Save"));
        this.e.add(new ke(1, this.c / 2 - 100, i + 22, "Load Save"));
        this.e.add(new ke(7, this.c / 2 - 100, i + 44, "Craft a Map"));
        this.e.add(new ke(5, this.c / 2 - 100, i + 66, "Download a Map"));
        if (this.b.n) {
            this.e.add(new ke(0, this.c / 2 - 100, i + 88, stringtranslate.a("menu.options")));
        } else {
            this.e.add(new ke(0, this.c / 2 - 100, i + 88 + 11, 98, 20, stringtranslate.a("menu.options")));
            this.e.add(new ke(4, this.c / 2 + 2, i + 88 + 11, 98, 20, stringtranslate.a("menu.quit")));
        }
        if (this.b.k == null)
            this.l.g = false;
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0) {
            this.b.a((da) new co(this, this.b.z));
        } else if (guibutton.f == 1) {
            this.b.a(new rq(this));
        } else if (guibutton.f == 2) {
            this.b.a((da) new lq(this));
        } else if (guibutton.f == 3) {
            this.b.a((da) new ft(this));
        } else if (guibutton.f == 4) {
            this.b.f();
        } else if (guibutton.f == 5) {
            this.b.a((da) new AC_GuiMapDownload(this));
        } else if (guibutton.f == 6) {
            this.b.a((da) new AC_GuiMapSelect(this, ""));
        } else if (guibutton.f == 7) {
            this.b.a((da) new AC_GuiMapSelect(this, null));
        }
    }

    public void a(int i, int j, float f) {
        i();
        nw tessellator = nw.a;
        int c = 320;
        int k = this.c / 2 - c / 2;
        byte byte0 = 30;
        GL11.glBindTexture(3553, this.b.p.b("/acLogo.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        b(k + 0, byte0 + 0, 0, 0, 256, 31);
        b(k + 256, byte0 + 0, 0, 128, 64, 31);
        tessellator.b(16777215);
        GL11.glPushMatrix();
        GL11.glTranslatef((this.c / 2 + 90), 70.0F, 0.0F);
        GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
        float f1 = 1.8F - in.e(in.a((float) (System.currentTimeMillis() % 1000L) / 1000.0F * 3.141593F * 2.0F) * 0.1F);
        f1 = f1 * 100.0F / (this.g.a(this.j) + 32);
        GL11.glScalef(f1, f1, f1);
        a(this.g, this.j, 0, -8, 16776960);
        GL11.glPopMatrix();
        b(this.g, Version.version, 2, 2, 5263440);
        String s = "Copyright Mojang AB. Do not distribute.";
        b(this.g, s, this.c - this.g.a(s) - 2, this.d - 10, 16777215);
        super.a(i, j, f);
    }

    private static final Random a = new Random();

    private float i;

    private String j;

    private ke l;
}
