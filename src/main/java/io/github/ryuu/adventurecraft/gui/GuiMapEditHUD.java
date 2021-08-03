package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.MapEditing;
import net.minecraft.client.Minecraft;
import net.minecraft.level.Level;
import org.lwjgl.input.Keyboard;

public class GuiMapEditHUD extends da {
    private final Level world;

    private long clickedTime;

    private final GuiEditPalette palette;

    public GuiMapEditHUD(Level w) {
        this.world = w;
        DebugMode.editMode = true;
        if (DebugMode.mapEditing == null) {
            DebugMode.mapEditing = new MapEditing(Minecraft.minecraftInstance, w);
        } else {
            DebugMode.mapEditing.updateWorld(w);
        }
        this.palette = new GuiEditPalette();
    }

    public void b() {
    }

    protected void a(ke guibutton) {
    }

    public void g() {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == 87) {
                this.b.j();
                return;
            }
            a(Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }
        this.b.h.a(Keyboard.getEventKey(), Keyboard.getEventKeyState());
    }

    protected void a(char c, int i) {
        if (i == 1) {
            this.b.a(null);
            this.b.g();
            DebugMode.editMode = false;
        }
    }

    protected void a(int i, int j, int k) {
        if (this.palette.mouseClicked(i, j, k, this.b, this.c, this.d))
            return;
        if (k == 0) {
            long curTime = this.world.t();
            if (this.clickedTime != curTime)
                DebugMode.mapEditing.paint();
        } else if (k == 1) {
            this.b.C.a();
            this.b.N = true;
        }
    }

    protected void b(int i, int j, int k) {
        if (this.a != null && k == 0) {
            this.a.a(i, j);
            this.a = null;
        } else if (k == 1) {
            this.b.N = false;
            this.b.C.b();
        }
    }

    public void a(int i, int j, float f) {
        super.a(i, j, f);
        this.palette.drawPalette(this.b, this.g, this.c, this.d);
    }

    public boolean c() {
        return false;
    }
}