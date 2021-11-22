package io.github.ryuu.adventurecraft.mixin.client.gui.screen;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(DeathScreen.class)
public class MixinDeathScreen extends Screen {

    /**
     * @author Ryuu, TechPizza, Phil
     */
    @Override
    @Overwrite()
    protected void buttonClicked(Button button) {
        if (button.id != 0) {
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
}
