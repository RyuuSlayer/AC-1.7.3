package io.github.ryuu.adventurecraft.mixin.client.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import io.github.ryuu.adventurecraft.scripting.ScriptUIContainer;
import io.github.ryuu.adventurecraft.util.DebugMode;
import io.github.ryuu.adventurecraft.util.TerrainImage;
import io.github.ryuu.adventurecraft.util.Version;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatMessage;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.render.RenderHelper;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemInstance;
import net.minecraft.tile.Tile;
import net.minecraft.tile.material.Material;
import net.minecraft.util.maths.MathsHelper;
import org.lwjgl.opengl.GL11;

public class MixinOverlay extends DrawableHelper {
    private static ItemRenderer itemRenderer = new ItemRenderer();
    private List chatMessages = new ArrayList();
    private Random rand = new Random();
    private Minecraft minecraft;
    public String field_2541 = null;
    private int field_2548 = 0;
    private String jukeboxMessage = "";
    private int jukeboxMessageTime = 0;
    private boolean field_2551 = false;
    public float field_2542;
    float field_2543 = 1.0f;
    public ScriptUIContainer scriptUI;
    public boolean hudEnabled = true;

    public MixinOverlay(Minecraft minecraft) {
        this.minecraft = minecraft;
        this.scriptUI = new ScriptUIContainer(0.0f, 0.0f, null);
    }

    public void render(float f, boolean flag, int i, int j) {
        float f1;
        ScreenScaler scaledresolution = new ScreenScaler(this.minecraft.options, this.minecraft.actualWidth, this.minecraft.actualHeight);
        int scaledWidth = scaledresolution.getScaledWidth();
        int scaledHeight = scaledresolution.getScaledHeight();
        TextRenderer fontrenderer = this.minecraft.textRenderer;
        this.minecraft.gameRenderer.method_1843();
        GL11.glEnable(3042);
        if (Minecraft.isFancyGraphicsEnabled()) {
            this.method_1945(this.minecraft.player.getBrightnessAtEyes(f), scaledWidth, scaledHeight);
        }
        ItemInstance itemstack = this.minecraft.player.inventory.getArmourItem(3);
        if (!this.minecraft.options.thirdPerson && !this.minecraft.cameraActive && itemstack != null && itemstack.itemId == Tile.PUMPKIN.id) {
            this.method_1947(scaledWidth, scaledHeight);
        }
        if (this.minecraft.level != null && !this.minecraft.level.properties.overlay.isEmpty()) {
            this.renderOverlay(scaledWidth, scaledHeight, this.minecraft.level.properties.overlay);
        }
        if ((f1 = this.minecraft.player.field_505 + (this.minecraft.player.field_504 - this.minecraft.player.field_505) * f) > 0.0f) {
            this.method_1951(f1, scaledWidth, scaledHeight);
        }
        if (this.hudEnabled) {
            boolean flag1;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/gui/gui.png"));
            PlayerInventory inventoryplayer = this.minecraft.player.inventory;
            this.zOffset = -90.0f;
            this.blit(scaledWidth / 2 - 91, scaledHeight - 22, 0, 0, 182, 22);
            this.blit(scaledWidth / 2 - 91 - 1 + inventoryplayer.offhandItem * 20, scaledHeight - 22 - 1, 24, 22, 48, 22);
            this.blit(scaledWidth / 2 - 91 - 1 + inventoryplayer.selectedHotbarSlot * 20, scaledHeight - 22 - 1, 0, 22, 24, 22);
            GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/gui/icons.png"));
            GL11.glEnable(3042);
            GL11.glBlendFunc(775, 769);
            this.blit(scaledWidth / 2 - 7, scaledHeight / 2 - 7, 0, 0, 16, 16);
            GL11.glDisable(3042);
            boolean bl = flag1 = this.minecraft.player.field_1613 / 3 % 2 == 1;
            if (this.minecraft.player.field_1613 < 10) {
                flag1 = false;
            }
            int health = this.minecraft.player.health;
            int prevHealth = this.minecraft.player.field_1037;
            this.rand.setSeed(this.field_2548 * 312871);
            if (this.minecraft.interactionManager.method_1722()) {
                int armorValue = this.minecraft.player.method_141();
                for (int index = 0; index < 10; ++index) {
                    int yPos = scaledHeight - 32;
                    if (armorValue > 0) {
                        int j5 = scaledWidth / 2 + 91 - index * 8 - 9;
                        if (index * 2 + 1 < armorValue) {
                            this.blit(j5, yPos, 34, 9, 9, 9);
                        }
                        if (index * 2 + 1 == armorValue) {
                            this.blit(j5, yPos, 25, 9, 9, 9);
                        }
                        if (index * 2 + 1 > armorValue) {
                            this.blit(j5, yPos, 16, 9, 9, 9);
                        }
                    }
                    int xPos = scaledWidth / 2 - 91 + index * 8;
                    if (health <= 8) {
                        yPos += this.rand.nextInt(2);
                    }
                    for (int healthRow = 0; healthRow <= (this.minecraft.player.maxHealth - 1) / 40; ++healthRow) {
                        if ((index + 1 + healthRow * 10) * 4 <= this.minecraft.player.maxHealth) {
                            int k5 = 0;
                            if (flag1) {
                                k5 = 1;
                            }
                            this.blit(xPos, yPos, 16 + k5 * 9, 0, 9, 9);
                            if (flag1) {
                                if (index * 4 + 3 + healthRow * 40 < prevHealth) {
                                    this.blit(xPos, yPos, 70, 0, 9, 9);
                                } else if (index * 4 + 3 + healthRow * 40 == prevHealth) {
                                    this.blit(xPos, yPos, 105, 0, 9, 9);
                                } else if (index * 4 + 2 + healthRow * 40 == prevHealth) {
                                    this.blit(xPos, yPos, 79, 0, 9, 9);
                                } else if (index * 4 + 1 + healthRow * 40 == prevHealth) {
                                    this.blit(xPos, yPos, 114, 0, 9, 9);
                                }
                            }
                            if (index * 4 + 3 + healthRow * 40 < health) {
                                this.blit(xPos, yPos, 52, 0, 9, 9);
                            } else if (index * 4 + 3 + healthRow * 40 == health) {
                                this.blit(xPos, yPos, 87, 0, 9, 9);
                            } else if (index * 4 + 2 + healthRow * 40 == health) {
                                this.blit(xPos, yPos, 61, 0, 9, 9);
                            } else if (index * 4 + 1 + healthRow * 40 == health) {
                                this.blit(xPos, yPos, 96, 0, 9, 9);
                            }
                        }
                        yPos -= 9;
                    }
                }
            }
            if (this.minecraft.player.isInFluid(Material.WATER)) {
                int yOffset = -9 * ((this.minecraft.player.maxHealth - 1) / 40);
                int k2 = (int)Math.ceil((double)(this.minecraft.player.air - 2) * 10.0 / 300.0);
                int l3 = (int)Math.ceil((double)this.minecraft.player.air * 10.0 / 300.0) - k2;
                for (int l5 = 0; l5 < k2 + l3; ++l5) {
                    if (l5 < k2) {
                        this.blit(scaledWidth / 2 - 91 + l5 * 8, scaledHeight - 32 - 9 + yOffset, 16, 18, 9, 9);
                        continue;
                    }
                    this.blit(scaledWidth / 2 - 91 + l5 * 8, scaledHeight - 32 - 9 + yOffset, 25, 18, 9, 9);
                }
            }
        }
        GL11.glDisable(3042);
        if (this.hudEnabled) {
            GL11.glEnable(32826);
            GL11.glPushMatrix();
            GL11.glRotatef(120.0f, 1.0f, 0.0f, 0.0f);
            RenderHelper.enableLighting();
            GL11.glPopMatrix();
            for (int l1 = 0; l1 < 9; ++l1) {
                int i3 = scaledWidth / 2 - 90 + l1 * 20 + 2;
                int i4 = scaledHeight - 16 - 3;
                this.method_1948(l1, i3, i4, f);
            }
            RenderHelper.disableLighting();
            GL11.glDisable(32826);
        }
        if (this.minecraft.player.getSleepTimer() > 0) {
            GL11.glDisable(2929);
            GL11.glDisable(3008);
            int i2 = this.minecraft.player.getSleepTimer();
            float f3 = (float)i2 / 100.0f;
            if (f3 > 1.0f) {
                f3 = 1.0f - (float)(i2 - 100) / 10.0f;
            }
            int j4 = (int)(220.0f * f3) << 24 | 0x101020;
            this.fill(0, 0, scaledWidth, scaledHeight, j4);
            GL11.glEnable(3008);
            GL11.glEnable(2929);
        }
        if (this.minecraft.options.debugHud) {
            GL11.glPushMatrix();
            if (Minecraft.field_2772 > 0L) {
                GL11.glTranslatef(0.0f, 32.0f, 0.0f);
            }
            fontrenderer.drawTextWithShadow("Minecraft Beta 1.7.3 (" + this.minecraft.fpsDebugString + ")", 2, 2, 0xFFFFFF);
            fontrenderer.drawTextWithShadow(this.minecraft.getDebugFirstLine(), 2, 12, 0xFFFFFF);
            fontrenderer.drawTextWithShadow(this.minecraft.getDebugSecondLine(), 2, 22, 0xFFFFFF);
            fontrenderer.drawTextWithShadow(this.minecraft.getDebugThirdLine(), 2, 32, 0xFFFFFF);
            fontrenderer.drawTextWithShadow(this.minecraft.getDebugFourthLine(), 2, 42, 0xFFFFFF);
            long l2 = Runtime.getRuntime().maxMemory();
            long l4 = Runtime.getRuntime().totalMemory();
            long l6 = Runtime.getRuntime().freeMemory();
            long l7 = l4 - l6;
            String s = "Used memory: " + l7 * 100L / l2 + "% (" + l7 / 1024L / 1024L + "MB) of " + l2 / 1024L / 1024L + "MB";
            this.drawTextWithShadow(fontrenderer, s, scaledWidth - fontrenderer.getTextWidth(s) - 2, 2, 0xE0E0E0);
            s = "Allocated memory: " + l4 * 100L / l2 + "% (" + l4 / 1024L / 1024L + "MB)";
            this.drawTextWithShadow(fontrenderer, s, scaledWidth - fontrenderer.getTextWidth(s) - 2, 12, 0xE0E0E0);
            this.drawTextWithShadow(fontrenderer, "x: " + this.minecraft.player.x, 2, 64, 0xE0E0E0);
            this.drawTextWithShadow(fontrenderer, "y: " + this.minecraft.player.y, 2, 72, 0xE0E0E0);
            this.drawTextWithShadow(fontrenderer, "z: " + this.minecraft.player.z, 2, 80, 0xE0E0E0);
            this.drawTextWithShadow(fontrenderer, "f: " + (MathsHelper.floor((double)(this.minecraft.player.yaw * 4.0f / 360.0f) + 0.5) & 3), 2, 88, 0xE0E0E0);
            this.drawTextWithShadow(fontrenderer, String.format("Use Terrain Images: %b", new Object[]{this.minecraft.level.properties.useImages}), 2, 96, 0xE0E0E0);
            this.drawTextWithShadow(fontrenderer, String.format("Collide X: %d Z: %d", new Object[]{this.minecraft.player.collisionX, this.minecraft.player.collisionZ}), 2, 104, 0xE0E0E0);
            if (this.minecraft.level.properties.useImages) {
                int x = (int)this.minecraft.player.x;
                int z = (int)this.minecraft.player.z;
                int terrainHeight = TerrainImage.getTerrainHeight(x, z);
                int waterHeight = TerrainImage.getWaterHeight(x, z);
                double temperature = TerrainImage.getTerrainTemperature(x, z);
                double humidity = TerrainImage.getTerrainHumidity(x, z);
                this.drawTextWithShadow(fontrenderer, String.format("T: %d W: %d Temp: %.2f Humid: %.2f", new Object[]{terrainHeight, waterHeight, temperature, humidity}), 2, 112, 0xE0E0E0);
            }
            GL11.glPopMatrix();
        } else {
            fontrenderer.drawTextWithShadow(Version.shortVersion, 2, 2, 0xFFFFFF);
            int offset = 12;
            if (DebugMode.active) {
                fontrenderer.drawTextWithShadow("Debug Active", 2, offset, 0xFFFFFF);
                offset += 10;
            }
            if (DebugMode.levelEditing) {
                fontrenderer.drawTextWithShadow("Map Editing", 2, offset, 0xFFFFFF);
            }
        }
        if (this.jukeboxMessageTime > 0) {
            float f2 = (float)this.jukeboxMessageTime - f;
            int j3 = (int)(f2 * 256.0f / 20.0f);
            if (j3 > 255) {
                j3 = 255;
            }
            if (j3 > 0) {
                GL11.glPushMatrix();
                GL11.glTranslatef((float)(scaledWidth / 2), (float)(scaledHeight - 48), 0.0f);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                int k4 = 0xFFFFFF;
                if (this.field_2551) {
                    k4 = Color.HSBtoRGB(f2 / 50.0f, 0.7f, 0.6f) & 0xFFFFFF;
                }
                fontrenderer.drawTextWithoutShadow(this.jukeboxMessage, -fontrenderer.getTextWidth(this.jukeboxMessage) / 2, -4, k4 + (j3 << 24));
                GL11.glDisable(3042);
                GL11.glPopMatrix();
            }
        }
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3008);
        GL11.glDisable(2929);
        this.scriptUI.render(fontrenderer, this.minecraft.textureManager, f);
        GL11.glEnable(2929);
        int byte0 = 10;
        boolean flag2 = false;
        if (this.minecraft.currentScreen instanceof ChatScreen) {
            byte0 = 20;
            flag2 = true;
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, (float)(scaledHeight - 48), 0.0f);
        for (int i5 = 0; i5 < this.chatMessages.size() && i5 < byte0; ++i5) {
            if (((ChatMessage)this.chatMessages.get(i5)).field_2470 >= 200 && !flag2) continue;
            double d = (double)((ChatMessage)this.chatMessages.get(i5)).field_2470 / 200.0;
            d = 1.0 - d;
            if ((d *= 10.0) < 0.0) {
                d = 0.0;
            }
            if (d > 1.0) {
                d = 1.0;
            }
            d *= d;
            int j6 = (int)(255.0 * d);
            if (flag2) {
                j6 = 255;
            }
            if (j6 <= 0) continue;
            int byte1 = 2;
            int k6 = -i5 * 9;
            String s1 = ((ChatMessage)this.chatMessages.get(i5)).field_2469;
            this.fill(byte1, k6 - 1, byte1 + 320, k6 + 8, j6 / 2 << 24);
            GL11.glEnable(3042);
            fontrenderer.drawTextWithShadow(s1, byte1, k6, 0xFFFFFF + (j6 << 24));
        }
        GL11.glPopMatrix();
        GL11.glEnable(3008);
        GL11.glDisable(3042);
    }

    private void method_1947(int i, int j) {
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3008);
        GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("%blur%/misc/pumpkinblur.png"));
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start();
        tessellator.vertex(0.0, j, -90.0, 0.0, 1.0);
        tessellator.vertex(i, j, -90.0, 1.0, 1.0);
        tessellator.vertex(i, 0.0, -90.0, 1.0, 0.0);
        tessellator.vertex(0.0, 0.0, -90.0, 0.0, 0.0);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void method_1945(float f, int i, int j) {
        if ((f = 1.0f - f) < 0.0f) {
            f = 0.0f;
        }
        if (f > 1.0f) {
            f = 1.0f;
        }
        this.field_2543 = (float)((double)this.field_2543 + (double)(f - this.field_2543) * 0.01);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(0, 769);
        GL11.glColor4f(this.field_2543, this.field_2543, this.field_2543, 1.0f);
        GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("%blur%/misc/vignette.png"));
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start();
        tessellator.vertex(0.0, j, -90.0, 0.0, 1.0);
        tessellator.vertex(i, j, -90.0, 1.0, 1.0);
        tessellator.vertex(i, 0.0, -90.0, 1.0, 0.0);
        tessellator.vertex(0.0, 0.0, -90.0, 0.0, 0.0);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glBlendFunc(770, 771);
    }

    private void method_1951(float f, int i, int j) {
        if (f < 1.0f) {
            f *= f;
            f *= f;
            f = f * 0.8f + 0.2f;
        }
        GL11.glDisable(3008);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, f);
        GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/terrain.png"));
        float f1 = (float)(Tile.PORTAL.tex % 16) / 16.0f;
        float f2 = (float)(Tile.PORTAL.tex / 16) / 16.0f;
        float f3 = (float)(Tile.PORTAL.tex % 16 + 1) / 16.0f;
        float f4 = (float)(Tile.PORTAL.tex / 16 + 1) / 16.0f;
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start();
        tessellator.vertex(0.0, j, -90.0, f1, f4);
        tessellator.vertex(i, j, -90.0, f3, f4);
        tessellator.vertex(i, 0.0, -90.0, f3, f2);
        tessellator.vertex(0.0, 0.0, -90.0, f1, f2);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderOverlay(int i, int j, String overlay) {
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(3008);
        GL11.glBindTexture(3553, this.minecraft.textureManager.getTextureId("/overlays/" + overlay));
        Tessellator tessellator = Tessellator.INSTANCE;
        tessellator.start();
        tessellator.vertex(0.0, j, -90.0, 0.0, 1.0);
        tessellator.vertex(i, j, -90.0, 1.0, 1.0);
        tessellator.vertex(i, 0.0, -90.0, 1.0, 0.0);
        tessellator.vertex(0.0, 0.0, -90.0, 0.0, 0.0);
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(2929);
        GL11.glEnable(3008);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void method_1948(int i, int j, int k, float f) {
        ItemInstance itemstack = this.minecraft.player.inventory.main[i];
        if (itemstack == null) {
            return;
        }
        float f1 = (float)itemstack.cooldown - f;
        if (f1 > 0.0f) {
            GL11.glPushMatrix();
            float f2 = 1.0f + f1 / 5.0f;
            GL11.glTranslatef((float)(j + 8), (float)(k + 12), 0.0f);
            GL11.glScalef(1.0f / f2, (f2 + 1.0f) / 2.0f, 1.0f);
            GL11.glTranslatef((float)(-(j + 8)), (float)(-(k + 12)), 0.0f);
        }
        itemRenderer.renderItemInstance(this.minecraft.textRenderer, this.minecraft.textureManager, itemstack, j, k);
        if (f1 > 0.0f) {
            GL11.glPopMatrix();
        }
        itemRenderer.method_1488(this.minecraft.textRenderer, this.minecraft.textureManager, itemstack, j, k);
    }

    public void method_1944() {
        if (this.jukeboxMessageTime > 0) {
            --this.jukeboxMessageTime;
        }
        ++this.field_2548;
        for (int i = 0; i < this.chatMessages.size(); ++i) {
            ++((ChatMessage)this.chatMessages.get(i)).field_2470;
        }
        this.scriptUI.onUpdate();
    }

    public void method_1950() {
        this.chatMessages.clear();
    }

    public void addChatMessage(String s) {
        while (this.minecraft.textRenderer.getTextWidth(s) > 320) {
            int i;
            for (i = 1; i < s.length() && this.minecraft.textRenderer.getTextWidth(s.substring(0, i + 1)) <= 320; ++i) {
            }
            this.addChatMessage(s.substring(0, i));
            s = s.substring(i);
        }
        this.chatMessages.add(0, (Object)new ChatMessage(s));
        while (this.chatMessages.size() > 50) {
            this.chatMessages.remove(this.chatMessages.size() - 1);
        }
    }

    public void method_1952(String s) {
        this.jukeboxMessage = "Now playing: " + s;
        this.jukeboxMessageTime = 60;
        this.field_2551 = true;
    }

    public void method_1953(String s) {
        TranslationStorage stringtranslate = TranslationStorage.getInstance();
        String s1 = stringtranslate.translate(s);
        this.addChatMessage(s1);
    }
}