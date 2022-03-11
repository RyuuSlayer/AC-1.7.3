package io.github.ryuu.adventurecraft.gui;

import io.github.ryuu.adventurecraft.mixin.client.AccessMinecraft;
import io.github.ryuu.adventurecraft.mixin.client.gui.AccessScreen;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.MapEditing;
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
            DebugMode.mapEditing = new MapEditing(AccessMinecraft.getInstance(), w);
        } else {
            DebugMode.mapEditing.updateWorld(w);
        }
        this.palette = new GuiEditPalette();
    }

    @Override
    public void init() {
    }

    @Override
    protected void buttonClicked(Button button) {
    }

    @Override
    public void onKeyboardEvent() {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() == 87) {
                this.minecraft.toggleFullscreen();
                return;
            }
            this.keyPressed(Keyboard.getEventCharacter(), Keyboard.getEventKey());
        }
        this.minecraft.player.method_136(Keyboard.getEventKey(), Keyboard.getEventKeyState());
    }

    @Override
    protected void keyPressed(char character, int key) {
        if (key == 1) {
            this.minecraft.openScreen(null);
            this.minecraft.lockCursor();
            DebugMode.editMode = false;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        if (this.palette.mouseClicked(mouseX, mouseY, button, this.minecraft, this.width, this.height)) {
            return;
        }
        if (button == 0) {
            long curTime = this.world.getLevelTime();
            if (this.clickedTime != curTime) {
                DebugMode.mapEditing.paint();
            }
        } else if (button == 1) {
            this.minecraft.field_2767.method_1970();
            this.minecraft.field_2778 = true;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int button) {
        if (((AccessScreen) this).getLastClickedButton() != null && button == 0) {
            ((AccessScreen) this).getLastClickedButton().mouseReleased(mouseX, mouseY);
            ((AccessScreen) this).setLastClickedButton(null);
        } else if (button == 1) {
            this.minecraft.field_2778 = false;
            this.minecraft.field_2767.method_1971();
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float delta) {
        super.render(mouseX, mouseY, delta);
        this.palette.drawPalette(this.minecraft, this.textManager, this.width, this.height);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
