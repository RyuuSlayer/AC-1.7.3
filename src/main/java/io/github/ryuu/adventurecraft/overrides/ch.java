package io.github.ryuu.adventurecraft.overrides;

import org.lwjgl.opengl.GL11;

public class ch extends Screen {
    public void b() {
        this.e.clear();
        this.e.add(new ke(1, this.c / 2 - 100, this.d / 4 + 72, "Respawn"));
        this.e.add(new ke(2, this.c / 2 - 100, this.d / 4 + 96, "Title menu"));
        if (this.b.k == null)
            ((ke)this.e.get(1)).g = false;
    }

    protected void a(char c, int i) {}

    protected void a(ke guibutton) {
        // Byte code:
        //   0: aload_1
        //   1: getfield f : I
        //   4: ifeq -> 7
        //   7: aload_1
        //   8: getfield f : I
        //   11: iconst_1
        //   12: if_icmpne -> 33
        //   15: aload_0
        //   16: getfield b : Lnet/minecraft/client/Minecraft;
        //   19: getfield h : Ldc;
        //   22: invokevirtual p_ : ()V
        //   25: aload_0
        //   26: getfield b : Lnet/minecraft/client/Minecraft;
        //   29: aconst_null
        //   30: invokevirtual a : (Lda;)V
        //   33: aload_1
        //   34: getfield f : I
        //   37: iconst_2
        //   38: if_icmpne -> 63
        //   41: aload_0
        //   42: getfield b : Lnet/minecraft/client/Minecraft;
        //   45: aconst_null
        //   46: invokevirtual a : (Lfd;)V
        //   49: aload_0
        //   50: getfield b : Lnet/minecraft/client/Minecraft;
        //   53: new fu
        //   56: dup
        //   57: invokespecial <init> : ()V
        //   60: invokevirtual a : (Lda;)V
        //   63: return
        // Line number table:
        //   Java source line number -> byte code offset
        //   #38	-> 0
        //   #39	-> 7
        //   #41	-> 15
        //   #42	-> 25
        //   #44	-> 33
        //   #46	-> 41
        //   #47	-> 49
        //   #49	-> 63
        // Local variable table:
        //   start	length	slot	name	descriptor
        //   0	64	0	this	Lch;
        //   0	64	1	guibutton	Lke;
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, 1615855616, -1602211792);
        GL11.glPushMatrix();
        GL11.glScalef(2.0F, 2.0F, 2.0F);
        a(this.g, "Game over!", this.c / 2 / 2, 30, 16777215);
        GL11.glPopMatrix();
        super.a(i, j, f);
    }

    public boolean c() {
        return false;
    }
}
