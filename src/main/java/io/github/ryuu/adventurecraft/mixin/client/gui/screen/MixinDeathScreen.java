package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widgets.Button;
import org.lwjgl.opengl.GL11;

public class MixinDeathScreen extends Screen {
    public void init() {
        this.buttons.clear();
        this.buttons.add(new Button(1, this.width / 2 - 100, this.height / 4 + 72, "Respawn"));
        this.buttons.add(new Button(2, this.width / 2 - 100, this.height / 4 + 96, "Title menu"));
        if (this.minecraft.session == null) {
            ((Button)this.buttons.get(1)).active = false;
        }
    }

    protected void keyPressed(char character, int key) {
    }

    protected void buttonClicked(Button button) {
        if (button.id != 0) {
            // empty if block
        }
        if (button.id == 1) {
            this.minecraft.player.respawn();
            this.minecraft.openScreen(null);
        }
        if (button.id == 2) {
            this.minecraft.setLevel(null);
            this.minecraft.openScreen(new TitleScreen());
        }
    }

    public void render(int mouseX, int mouseY, float delta) {
        this.fillGradient(0, 0, this.width, this.height, 0x60500000, -1602211792);
        GL11.glPushMatrix();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        this.drawTextWithShadowCentred(this.textManager, "Game over!", this.width / 2 / 2, 30, 0xFFFFFF);
        GL11.glPopMatrix();
        super.render(mouseX, mouseY, delta);
    }

    public boolean isPauseScreen() {
        return false;
    }
}