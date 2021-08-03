package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.entities.tile.TileEntityUrl;
import io.github.ryuu.adventurecraft.util.ClipboardHandler;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

public class GuiUrl extends da {
    private TileEntityUrl msg;

    private fd world;

    public GuiUrl(fd w, TileEntityUrl u) {
        this.world = w;
        this.msg = u;
    }

    public void b() {}

    protected void a(ke guibutton) {}

    protected void a(char c, int i) {
        super.a(c, i);
        if (i == 47 && (Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157) || Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220))) {
            this.msg.url = ClipboardHandler.getClipboard();
            this.world.b(this.msg.e, this.msg.g).g();
            return;
        }
        if (i == 14 && this.msg.url.length() > 0)
            this.msg.url = this.msg.url.substring(0, this.msg.url.length() - 1);
        if (fp.a.indexOf(c) >= 0 && this.msg.url.length() < 30)
            this.msg.url += c;
        this.world.b(this.msg.e, this.msg.g).g();
    }

    public void a(int i, int j, float f) {
        i();
        b(this.g, String.format("Url: '%s'", new Object[] { this.msg.url }), 4, 4, 14737632);
        super.a(i, j, f);
    }

    public static void showUI(fd w, TileEntityUrl tileEntityMsg) {
        Minecraft.minecraftInstance.a(new GuiUrl(w, tileEntityMsg));
    }
}
