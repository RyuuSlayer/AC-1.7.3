/*
 * Decompiled with CFR 0.0.8 (FabricMC 66e13396).
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.fabricmc.api.EnvType
 *  net.fabricmc.api.Environment
 *  org.lwjgl.opengl.GL11
 */
package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.Screen;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widgets.Button;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import io.github.ryuu.adventurecraft.mixin.item.MixinScreen;
import io.github.ryuu.adventurecraft.mixin.item.MixinTitleScreen;

@Mixin(DeathScreen.class)
public class MixinDeathScreen extends MixinScreen {

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void init() {
        this.buttons.clear();
        this.buttons.add((Object) new Button(1, this.width / 2 - 100, this.height / 4 + 72, "Respawn"));
        this.buttons.add((Object) new Button(2, this.width / 2 - 100, this.height / 4 + 96, "Title menu"));
        if (this.minecraft.session == null) {
            ((Button) this.buttons.get((int) 1)).active = false;
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void keyPressed(char character, int key) {
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
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
            this.minecraft.openScreen(new MixinTitleScreen());
        }
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public void render(int mouseX, int mouseY, float delta) {
        this.fillGradient(0, 0, this.width, this.height, 0x60500000, -1602211792);
        GL11.glPushMatrix();
        GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
        this.drawTextWithShadowCentred(this.textManager, "Game over!", this.width / 2 / 2, 30, 0xFFFFFF);
        GL11.glPopMatrix();
        super.render(mouseX, mouseY, delta);
    }

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    public boolean isPauseScreen() {
        return false;
    }
}
