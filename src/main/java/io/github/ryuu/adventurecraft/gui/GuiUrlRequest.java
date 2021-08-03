package io.github.ryuu.adventurecraft.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import net.minecraft.client.Minecraft;

public class GuiUrlRequest extends da {
    private String url;

    private String msg;

    public GuiUrlRequest(String u) {
        this(u, "The map wants you to goto");
    }

    public GuiUrlRequest(String u, String m) {
        this.url = u;
        this.msg = m;
    }

    public void a() {}

    public void b() {
        this.e.add(new ab(0, this.c / 2 - 75, this.d / 2 + 10, "Open URL"));
        this.e.add(new ab(1, this.c / 2 - 75, this.d / 2 + 32, "Don't Open"));
    }

    protected void a(ke guibutton) {
        if (guibutton.f == 0) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.BROWSE))
                try {
                    desktop.browse(new URI(this.url));
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
        }
        this.b.a(null);
    }

    public void a(int i, int j, float f) {
        a(0, 0, this.c, this.d, -2147483648);
        b(this.g, this.msg, this.c / 2 - this.g.a(this.msg) / 2, this.d / 2 - 15, 14737632);
        b(this.g, this.url, this.c / 2 - this.g.a(this.url) / 2, this.d / 2, 14737632);
        super.a(i, j, f);
    }

    public static void showUI(String url) {
        Minecraft.minecraftInstance.a(new GuiUrlRequest(url));
    }

    public static void showUI(String url, String msg) {
        Minecraft.minecraftInstance.a(new GuiUrlRequest(url, msg));
    }

    public boolean c() {
        return true;
    }
}
