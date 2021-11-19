package io.github.ryuu.adventurecraft.mixin.client.gui.screen.container;

import net.minecraft.achievement.Achievements;
import net.minecraft.client.gui.screen.AchievementsScreen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.screen.container.ContainerScreen;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.client.render.RenderHelper;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.entity.player.Player;
import org.lwjgl.opengl.GL11;

public class MixinPlayerInventoryScreen extends ContainerScreen {
    private float mouseX;
    private float mouseY;

    public MixinPlayerInventoryScreen(Player entityplayer) {
        super(entityplayer.playerContainer);
        this.passEvents = true;
        entityplayer.increaseStat(Achievements.OPEN_INVENTORY, 1);
    }

    public void init() {
        this.buttons.clear();
    }

    protected void renderForeground() {
    }

    public void render(int mouseX, int mouseY, float delta) {
        super.render(mouseX, mouseY, delta);
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    protected void renderContainerBackground(float f) {
        int i = this.minecraft.textureManager.getTextureId("/gui/inventory.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.textureManager.bindTexture(i);
        int j = (this.width - this.containerWidth) / 2;
        int k = (this.height - this.containerHeight) / 2;
        this.blit(j, k, 0, 0, this.containerWidth, this.containerHeight);
        i = this.minecraft.textureManager.getTextureId("/gui/heartPiece.png");
        this.minecraft.textureManager.bindTexture(i);
        this.blit(j + 89, k + 6, this.minecraft.player.numHeartPieces * 32, 0, 32, 32);
        GL11.glEnable(32826);
        GL11.glEnable(2903);
        GL11.glPushMatrix();
        GL11.glTranslatef((float)(j + 51), (float)(k + 75), (float)50.0f);
        float f1 = 30.0f;
        GL11.glScalef(-f1, f1, f1);
        GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
        float f2 = this.minecraft.player.field_1012;
        float f3 = this.minecraft.player.yaw;
        float f4 = this.minecraft.player.pitch;
        float f5 = (float)(j + 51) - this.mouseX;
        float f6 = (float)(k + 75 - 50) - this.mouseY;
        GL11.glRotatef(135.0f, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableLighting();
        GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-((float)Math.atan(f6 / 40.0f)) * 20.0f, 1.0f, 0.0f, 0.0f);
        this.minecraft.player.field_1012 = (float)Math.atan(f5 / 40.0f) * 20.0f;
        this.minecraft.player.yaw = (float)Math.atan(f5 / 40.0f) * 40.0f;
        this.minecraft.player.pitch = -((float)Math.atan(f6 / 40.0f)) * 20.0f;
        this.minecraft.player.field_1617 = 1.0f;
        GL11.glTranslatef(0.0f, this.minecraft.player.standingEyeHeight, 0.0f);
        EntityRenderDispatcher.INSTANCE.field_2497 = 180.0f;
        EntityRenderDispatcher.INSTANCE.method_1920(this.minecraft.player, 0.0, 0.0, 0.0, 0.0f, 1.0f);
        this.minecraft.player.field_1617 = 0.0f;
        this.minecraft.player.field_1012 = f2;
        this.minecraft.player.yaw = f3;
        this.minecraft.player.pitch = f4;
        GL11.glPopMatrix();
        RenderHelper.disableLighting();
        GL11.glDisable(32826);
    }

    protected void buttonClicked(Button button) {
        if (button.id == 0) {
            this.minecraft.openScreen(new AchievementsScreen(this.minecraft.statManager));
        }
        if (button.id == 1) {
            this.minecraft.openScreen(new StatsScreen(this, this.minecraft.statManager));
        }
    }
}