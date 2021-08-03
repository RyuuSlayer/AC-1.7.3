package io.github.ryuu.adventurecraft.overrides;

import io.github.ryuu.adventurecraft.util.ClipboardHandler;
import org.lwjgl.input.Keyboard;

public class gc extends da {
    protected String a = "";

    private int i = 0;

    public void b() {
        Keyboard.enableRepeatEvents(true);
    }

    public void h() {
        Keyboard.enableRepeatEvents(false);
    }

    public void a() {
        this.i++;
    }

    protected void a(char c, int i) {
        if (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157) || Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220)) {
            if (i == 47) {
                this.a = ClipboardHandler.getClipboard();
                return;
            }
            if (i == 46) {
                ClipboardHandler.setClipboard(this.a);
                return;
            }
        }
        if (i == 1) {
            this.b.a(null);
            return;
        }
        if (i == 28) {
            String s = this.a.trim();
            if (s.length() > 0) {
                String s1 = this.a.trim();
                if (!this.b.b(s1))
                    this.b.h.a(s1);
            }
            if (this.b.r instanceof gc)
                this.b.a(null);
            return;
        }
        if (i == 14 && this.a.length() > 0)
            this.a = this.a.substring(0, this.a.length() - 1);
        if (j.indexOf(c) >= 0 && this.a.length() < 100)
            this.a += c;
    }

    public void a(int i, int j, float f) {
        a(2, this.d - 14, this.c - 2, this.d - 2, -2147483648);
        b(this.g, "> " + this.a + ((this.i / 6 % 2 != 0) ? "" : "_"), 4, this.d - 12, 14737632);
        super.a(i, j, f);
    }

    protected void a(int i, int j, int k) {
        if (k != 0)
            return;
        if (this.b.v.a == null) {
            super.a(i, j, k);
            return;
        }
        if (this.a.length() > 0 && !this.a.endsWith(" "))
            this.a += " ";
        this.a += this.b.v.a;
        byte byte0 = 100;
        if (this.a.length() > byte0)
            this.a = this.a.substring(0, byte0);
    }

    private static final String j = fp.a;
}
