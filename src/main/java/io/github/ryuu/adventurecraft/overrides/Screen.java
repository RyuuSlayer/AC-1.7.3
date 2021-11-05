package io.github.ryuu.adventurecraft.overrides;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.render.Tessellator;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class Screen extends ub {
    public int c;
    public int d;
    public boolean f = false;
    public du h;
    public boolean disableInputGrabbing = false;
    protected Minecraft b;
    protected List e = new ArrayList();
    protected TextRenderer g;
    protected Button a = null;

    public static String d() {
        try {
            Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
            if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.stringFlavor)) {
                String s = (String) transferable.getTransferData(DataFlavor.stringFlavor);
                return s;
            }
        } catch (Exception exception) {
        }
        return null;
    }

    public void a(int i, int j, float f) {
        for (int k = 0; k < this.e.size(); k++) {
            Button guibutton = this.e.get(k);
            guibutton.a(this.b, i, j);
        }
    }

    protected void a(char c, int i) {
        if (i == 1) {
            this.b.a(null);
            this.b.g();
        }
    }

    protected void a(int i, int j, int k) {
        if (k == 0)
            for (int l = 0; l < this.e.size(); l++) {
                Button guibutton = this.e.get(l);
                if (guibutton.c(this.b, i, j)) {
                    this.a = guibutton;
                    this.b.B.a("random.click", 1.0F, 1.0F);
                    a(guibutton);
                }
            }
    }

    protected void b(int i, int j, int k) {
        if (this.a != null && k == 0) {
            this.a.a(i, j);
            this.a = null;
        }
    }

    protected void a(Button guibutton) {
    }

    public void a(Minecraft minecraft, int i, int j) {
        this.h = new du(minecraft);
        this.b = minecraft;
        this.g = minecraft.q;
        this.c = i;
        this.d = j;
        this.e.clear();
        b();
    }

    public void b() {
    }

    public void e() {
        for (; Mouse.next(); f()) ;
        for (; Keyboard.next(); g()) ;
    }

    public void f() {
        if (Mouse.getEventButtonState()) {
            int i = Mouse.getEventX() * this.c / this.b.d;
            int k = this.d - Mouse.getEventY() * this.d / this.b.e - 1;
            a(i, k, Mouse.getEventButton());
        } else {
            int j = Mouse.getEventX() * this.c / this.b.d;
            int l = this.d - Mouse.getEventY() * this.d / this.b.e - 1;
            b(j, l, Mouse.getEventButton());
        }
    }

    public void g() {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == 87) {
                this.b.j();
                return;
            }
            a(Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }
    }

    public void a() {
    }

    public void h() {
    }

    public void i() {
        a(0);
    }

    public void a(int i) {
        if (this.b.f != null) {
            a(0, 0, this.c, this.d, -1072689136, -804253680);
        } else {
            b(i);
        }
    }

    public void b(int i) {
        GL11.glDisable(2896);
        GL11.glDisable(2912);
        Tessellator tessellator = Tessellator.a;
        GL11.glBindTexture(3553, this.b.p.b("/gui/background.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        tessellator.b();
        tessellator.b(4210752);
        tessellator.a(0.0D, this.d, 0.0D, 0.0D, (this.d / f + i));
        tessellator.a(this.c, this.d, 0.0D, (this.c / f), (this.d / f + i));
        tessellator.a(this.c, 0.0D, 0.0D, (this.c / f), (0 + i));
        tessellator.a(0.0D, 0.0D, 0.0D, 0.0D, (0 + i));
        tessellator.a();
    }

    public boolean c() {
        return true;
    }

    public void a(boolean flag, int i) {
    }

    public void j() {
    }
}
