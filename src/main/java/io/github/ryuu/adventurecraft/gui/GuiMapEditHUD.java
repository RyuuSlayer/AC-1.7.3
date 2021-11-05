package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.MapEditing;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.level.Level;
import org.lwjgl.input.Keyboard;

public class GuiMapEditHUD extends Screen {
    private final Level world;
    private final GuiEditPalette palette;
    private long clickedTime;

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

    @Override
    public void init() {
    }

    @Override
    protected void buttonClicked(Button guibutton) {
    }

    @Override
    public void onKeyboardEvent() {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == 87) {
                this.minecraft.toggleFullscreen();
                return;
            }
            keyPressed(Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }
        this.minecraft.player.method_136(Keyboard.getEventKey(), Keyboard.getEventKeyState());
    }

    @Override
    protected void keyPressed(char c, int i) {
        if (i == 1) {
            this.minecraft.openScreen(null);
            this.minecraft.lockCursor();
            DebugMode.editMode = false;
        }
    }

    @Override
    protected void mouseClicked(int i, int j, int k) {
        if (this.palette.mouseClicked(i, j, k, this.minecraft, this.width, this.height))
            return;
        if (k == 0) {
            long curTime = this.world.getLevelTime();
            if (this.clickedTime != curTime)
                DebugMode.mapEditing.paint();
        } else if (k == 1) {
            this.minecraft.field_2767.method_1970();
            this.minecraft.field_2778 = true;
        }
    }

    @Override
    protected void mouseReleased(int i, int j, int k) {
        if (this.lastClickedButton != null && k == 0) {
            this.lastClickedButton.mouseReleased(i, j);
            this.lastClickedButton = null;
        } else if (k == 1) {
            this.minecraft.field_2778 = true;
            this.minecraft.field_2767.method_1971();
        }
    }

    @Override
    public void render(int i, int j, float f) {
        super.render(i, j, f);
        this.palette.drawPalette(this.minecraft, this.textManager, this.width, this.height);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}